package org.gogame.server.service;

import lombok.RequiredArgsConstructor;
import org.gogame.server.domain.entities.TokenEntity;
import org.gogame.server.domain.entities.UserBioEntity;
import org.gogame.server.auth.AuthResponseDto;
import org.gogame.server.domain.entities.UserEntity;
import org.gogame.server.auth.UserLoginDto;
import org.gogame.server.auth.UserRegisterDto;
import org.gogame.server.mappers.Mapper;
import org.gogame.server.repositories.TokenRepository;
import org.gogame.server.repositories.UserBioRepository;
import org.gogame.server.repositories.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepo;
    private final UserBioRepository userBioRepo;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final Mapper<UserRegisterDto, UserEntity> userRegisterMapper;
    private final TokenRepository tokenRepo;

    public AuthResponseDto register(UserRegisterDto dto) throws SQLException {
        UserEntity registeredUser = userRegisterMapper.mapTo(dto);
        try {
            registeredUser = userRepo.save(registeredUser);
            userBioRepo.save(UserBioEntity.builder().userId(registeredUser.getUserId()).bio("").build());

        } catch (DataIntegrityViolationException ex) {
            throw new SQLException("User with this nickname already exists");
        }

        var jwtToken = jwtService.generateToken(registeredUser);
        saveUserToken(registeredUser, jwtToken);

        return AuthResponseDto.builder()
                .userId(registeredUser.getUserId())
                .token(jwtToken)
                .build();
    }

    public AuthResponseDto authenticate(UserLoginDto dto) throws SQLException {
        UserEntity loggedUser;
        try {

            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dto.getNickname(),
                            dto.getPassword()
                    )
            );
            loggedUser = userRepo.findByNickname(dto.getNickname()).orElseThrow();
        } catch (AuthenticationException | NullPointerException ex) {
            throw new SQLException("Invalid credentials");
        }

        var jwtToken = jwtService.generateToken(loggedUser);
        revokeAllUserTokens(loggedUser);
        saveUserToken(loggedUser, jwtToken);

        return AuthResponseDto.builder()
                .userId(loggedUser.getUserId())
                .token(jwtToken)
                .build();
    }

    private void saveUserToken(UserEntity user, String jwtToken) {
        var token = TokenEntity.builder()
                        .user(user)
                        .token(jwtToken)
                        .expired(false)
                        .revoked(false)
                        .build();
        tokenRepo.save(token);
    }

    private void revokeAllUserTokens(UserEntity user) {
        var validUserTokens = tokenRepo.findAllValidTokensByUser(user.getUserId());
        if (validUserTokens.isEmpty()) {
            return;
        }
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepo.saveAll(validUserTokens);
    }
}
