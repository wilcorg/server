package org.gogame.server.service;

import lombok.RequiredArgsConstructor;
import org.gogame.server.domain.entities.GameEntity;
import org.gogame.server.domain.entities.UserEntity;
import org.gogame.server.domain.entities.UserGameInviteEntity;
import org.gogame.server.domain.entities.dto.user.UserInviteDto;
import org.gogame.server.repositories.GameRepository;
import org.gogame.server.repositories.UserGameInviteRepository;
import org.gogame.server.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepo;
    private final UserRepository userRepo;
    private final UserGameInviteRepository userGameInviteRepo;
    private final ModelMapper modelMapper;

    private Random random;

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

        boolean colorPicker = nextBoolean();

        var game = GameEntity.builder()
                .userBlack(colorPicker ? sender : receiver)
                .userWhite(colorPicker ? receiver : sender)
                .build();

        try {
            gameRepo.save(game);
        } catch (DataIntegrityViolationException e) {
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

    private boolean nextBoolean() {
        if (random == null) {
            random = new Random();
        }
        return random.nextBoolean();
    }

}
