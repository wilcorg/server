package org.gogame.server.service;

import lombok.RequiredArgsConstructor;
import org.gogame.server.domain.entities.*;
import org.gogame.server.auth.AuthResponseDto;
import org.gogame.server.auth.UserLoginDto;
import org.gogame.server.auth.UserRegisterDto;
import org.gogame.server.domain.entities.enums.UserLobbyState;
import org.gogame.server.mappers.Mapper;
import org.gogame.server.repositories.*;
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
    private final UserLobbyRepository userLobbyRepo;
    private final UserStatsRepository userStatsRepo;
    private final TokenRepository tokenRepo;
    private final LobbyService lobbyService;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final Mapper<UserRegisterDto, UserEntity> userRegisterMapper;

    public AuthResponseDto register(UserRegisterDto dto) throws SQLException {
        UserEntity registeredUser = userRegisterMapper.mapTo(dto);
        try {
            registeredUser = userRepo.save(registeredUser);
            userBioRepo.save(UserBioEntity.builder().user(registeredUser).bio("").build());
            userLobbyRepo.save(UserLobbyEntity.builder().userId(registeredUser.getUserId()).userLobbyState(UserLobbyState.ONLINE).build());
            userStatsRepo.save(UserStatsEntity.builder().user(registeredUser).gamePlayed(0L).gameWon(0L).gameLost(0L).build());

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

        lobbyService.updateLobbyState(loggedUser.getUserId());
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
