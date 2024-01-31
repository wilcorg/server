package org.gogame.server.service;

import lombok.RequiredArgsConstructor;
import org.gogame.server.domain.entities.enums.UserLobbyState;
import org.gogame.server.repositories.TokenRepository;
import org.gogame.server.repositories.UserLobbyRepository;
import org.gogame.server.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LobbyService {

    private final TokenRepository tokenRepo;
    private final UserLobbyRepository userLobbyRepo;
    private final UserRepository userRepo;

    public void updateLobbyState(Long userId) {
        try {
            var user = userLobbyRepo.findByUserId(userId);

            if (tokenRepo.findAllValidTokensByUser(userId).isEmpty()) {
                user.setUserLobbyState(UserLobbyState.OFFLINE);
            } else {
                user.setUserLobbyState(UserLobbyState.ONLINE);
            }
            userLobbyRepo.save(user);

        } catch (Exception e) {
            System.out.println("WARN: LobbyService: provided user not found in lobby");
        }

    }

}
