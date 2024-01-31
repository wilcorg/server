package org.gogame.server.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.gogame.server.repositories.TokenRepository;
import org.gogame.server.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepo;
    private final LobbyService lobbyService;
    private final JwtService jwtService;
    private final UserRepository userRepo;


    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        jwt = authHeader.substring(7);
        String username = jwtService.extractUsername(jwt);
        Long user_id = userRepo.findByNickname(username).orElseThrow(null).getUserId();
        var user = userRepo.findById(user_id).orElseThrow(null);

        var storedToken = tokenRepo.findByToken(jwt).orElseThrow(null);

        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepo.save(storedToken);
            SecurityContextHolder.clearContext();
        }
        lobbyService.updateLobbyState(user.getUserId());
    }
}
