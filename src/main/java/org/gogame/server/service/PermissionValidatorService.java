package org.gogame.server.service;

import lombok.RequiredArgsConstructor;
import org.gogame.server.domain.entities.GameEntity;
import org.gogame.server.domain.entities.UserEntity;
import org.gogame.server.domain.entities.enums.Role;
import org.gogame.server.repositories.GameRepository;
import org.gogame.server.repositories.UserRepository;
import org.hibernate.JDBCException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionValidatorService {

    private final JwtService jwtService;

    private final UserRepository userRepo;

    private final GameRepository gameRepo;

    public boolean validateUserId(Long userId, String token) {
        var username = jwtService.extractUsername(token.substring(7));  // remove "Bearer " from token

        UserEntity user;
        try {
            user = userRepo.findById(userId).orElseThrow();
        } catch (NullPointerException | JDBCException ex) {
            return false;
        }
        return user.getNickname().equals(username);
    }

    public boolean validateAdmin(String token) {
        UserEntity user;
        try {
            user = userRepo.findByNickname(jwtService.extractUsername(token.substring(7))).orElseThrow();
        } catch (Exception e) {
            return false;
        }

        return user.getRole().equals(Role.ADMIN);
    }

    public boolean validateGameId(Long gameId, String token) {
        var username = jwtService.extractUsername(token.substring(7));  // remove "Bearer " from token

        UserEntity user;
        GameEntity game;
        try {
            user = userRepo.findByNickname(username).orElseThrow();
            game = gameRepo.findCurrentGame(user.getUserId()).orElseThrow();

        } catch (NullPointerException | JDBCException ex) {
            return false;
        }
        return game.getGameId().equals(gameId);
    }
}
