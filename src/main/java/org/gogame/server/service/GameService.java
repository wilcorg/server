package org.gogame.server.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.gogame.server.domain.entities.*;
import org.gogame.server.domain.entities.dto.user.UserInviteDto;
import org.gogame.server.repositories.GameRepository;
import org.gogame.server.repositories.GameboardRepository;
import org.gogame.server.repositories.UserGameInviteRepository;
import org.gogame.server.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepo;
    private final UserRepository userRepo;
    private final UserGameInviteRepository userGameInviteRepo;
    private final ModelMapper modelMapper;
    private final GameboardRepository gameboardRepo;
    private final ObjectMapper objectMapper;

    public UserInviteDto sendGameInvite(UserInviteDto userInviteDto) throws SQLException {

        var users = getUsers(userInviteDto);
        var sender = users.getFirst();
        var receiver = users.getSecond();

        var invite = UserGameInviteEntity.builder()
                .userSender(sender)
                .userReceiver(receiver)
                .build();

        try {
            userGameInviteRepo.save(invite);
        } catch (DataIntegrityViolationException e) {
            throw new SQLException("This game invite already exists");
        }

        return modelMapper.map(invite, UserInviteDto.class);
    }

    public UserInviteDto acceptGameInvite(UserInviteDto userInviteDto) throws SQLException {

        var users = getUsers(userInviteDto);
        var sender = users.getFirst();
        var receiver = users.getSecond();

        var game = GameEntity.builder()
                .userBlack(sender)
                .userWhite(receiver)
                .build();

        try {
            gameRepo.save(game);
            GameboardEntity gameboardEntity = GameboardEntity.builder()
                    .gameId(game.getGameId())
                    .gameboard(objectMapper.writeValueAsString(new GameboardJSON()))
                    .build();
            gameboardRepo.save(gameboardEntity);
        } catch (DataIntegrityViolationException  | JsonProcessingException e) {
            throw new SQLException("Failed to save new game");
        }

        var invite = userGameInviteRepo.findByUserIds(sender.getUserId(), receiver.getUserId()).getFirst();

        userGameInviteRepo.delete(invite);

        return modelMapper.map(invite, UserInviteDto.class);
    }

    public UserInviteDto rejectGameInvite(UserInviteDto userInviteDto) throws SQLException {

        var users = getUsers(userInviteDto);
        var sender = users.getFirst();
        var receiver = users.getSecond();

        var invite = userGameInviteRepo.findByUserIds(sender.getUserId(), receiver.getUserId()).getFirst();

        userGameInviteRepo.delete(invite);

        return modelMapper.map(invite, UserInviteDto.class);
    }

    public List<UserInviteDto> fetchGameInvite(Long userId) throws SQLException {

        var invites = userGameInviteRepo.findBySingleUserId(userId);
        List<UserInviteDto> inviteDtos = new ArrayList<>();

        for (var invite : invites) {
            inviteDtos.add(
                    UserInviteDto.builder()
                            .userSenderId(invite.getUserSender().getUserId())
                            .userReceiverId(invite.getUserReceiver().getUserId())
                            .build()
            );
        }

        return inviteDtos;
    }

    public GameEntity getCurrentGame(Long userId) {
        return gameRepo.findCurrentGame(userId);
    }


    public void setGameWinner(Long gameId, Long userId) throws SQLException {
        var game = gameRepo.findById(gameId);
        if (game.isEmpty()) {
            throw new SQLException("Game doesn't exist");
        }
        var winner = userRepo.findById(userId);
        if (winner.isEmpty()) {
            throw new SQLException("User doesn't exist");
        }
        game.get().setWinner(winner.get());
        try {
            gameRepo.save(game.get());
        } catch (DataIntegrityViolationException e) {
            throw new SQLException("Failed to set winner id");
        }
    }

    private Pair<UserEntity, UserEntity> getUsers(UserInviteDto userInviteDto) throws SQLException {
        var sender = userRepo.findById(userInviteDto.getUserSenderId());
        var receiver = userRepo.findById(userInviteDto.getUserReceiverId());

        if (sender.isEmpty()) {
            throw new SQLException("Sender doesn't exist");
        }
        if (receiver.isEmpty()) {
            throw new SQLException("Receiver doesn't exist");
        }

        return Pair.of(sender.get(), receiver.get());
    }

}
