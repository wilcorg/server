package org.gogame.server.repositories;

import org.gogame.server.domain.entities.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TestDataUtil {

    public static UserEntity createTestUserEntityA() {
        return UserEntity.builder()
                .user_id(1L)
                .nickname("romka")
                .password_hash("qwertyuiop")
                .email("romka@romka.romka")
                .join_date(Timestamp.valueOf(LocalDateTime.of(2024, 1, 12, 18, 33, 11)))
                .build();
    }

    public static UserEntity createTestUserEntityB() {
        return UserEntity.builder()
                .user_id(2L)
                .nickname("romka2")
                .password_hash("qwertyuiop2")
                .email("romka2@romka.romka")
                .join_date(Timestamp.valueOf(LocalDateTime.of(2024, 1, 12, 18, 33, 12)))
                .build();
    }

    public static UserEntity createTestUserEntityC() {
        return UserEntity.builder()
                .user_id(3L)
                .nickname("romka3")
                .password_hash("qwertyuiop3")
                .email("romka3@romka.romka")
                .join_date(Timestamp.valueOf(LocalDateTime.of(2024, 1, 12, 18, 33, 12)))
                .build();
    }

    public static GameEntity createTestGameEntityA(final UserRepository userRepo) {
        var userA = createTestUserEntityA();
        var userB = createTestUserEntityB();

        if (userRepo.findById(userA.getUser_id()).isEmpty()) {
            userRepo.save(userA);
        }
        if (userRepo.findById(userB.getUser_id()).isEmpty()) {
            userRepo.save(userB);
        }

        return GameEntity.builder()
                .game_id(1L)
                .user_black(userA)
                .user_white(userB)
                .winner(userA)
                .build();
    }

    public static GameEntity createTestGameEntityB(final UserRepository userRepo) {
        var userA = createTestUserEntityA();
        var userB = createTestUserEntityB();

        if (userRepo.findById(userA.getUser_id()).isEmpty()) {
            userRepo.save(userA);
        }
        if (userRepo.findById(userB.getUser_id()).isEmpty()) {
            userRepo.save(userB);
        }

        return GameEntity.builder()
                .game_id(2L)
                .user_black(userA)
                .user_white(userB)
                .winner(userB)
                .build();
    }

    public static GameEntity createTestGameEntityC(final UserRepository userRepo) {
        var userB = createTestUserEntityB();
        var userC = createTestUserEntityC();

        if (userRepo.findById(userB.getUser_id()).isEmpty()) {
            userRepo.save(userB);
        }
        if (userRepo.findById(userC.getUser_id()).isEmpty()) {
            userRepo.save(userC);
        }

        return GameEntity.builder()
                .game_id(3L)
                .user_black(userC)
                .user_white(userB)
                .winner(userC)
                .build();
    }

    public static GameJournalEntity createTestGameJournalEntityA(final GameRepository gameRepo,
                                                                 final UserRepository userRepo) {
        var gameEntity = createTestGameEntityA(userRepo);
        if (gameRepo.findById(gameEntity.getGame_id()).isEmpty()) {
            gameRepo.save(gameEntity);
        }

        return GameJournalEntity.builder()
                .turn_id(1L)
                .game(gameEntity)
                .actionType(GameActionType.MOVE)
                .turn_x(1)
                .turn_x(2)
                .turn_date(Timestamp.valueOf(LocalDateTime.of(2024, 1, 12, 23, 27, 18)))
                .build();
    }

    public static GameJournalEntity createTestGameJournalEntityB(final GameRepository gameRepo,
                                                                 final UserRepository userRepo) {
        var gameEntity = createTestGameEntityB(userRepo);
        if (gameRepo.findById(gameEntity.getGame_id()).isEmpty()) {
            gameRepo.save(gameEntity);
        }

        return GameJournalEntity.builder()
                .game(gameEntity)
                .actionType(GameActionType.STOP_REQ)
                .turn_date(Timestamp.valueOf(LocalDateTime.of(2024, 1, 12, 23, 27, 18)))
                .build();
    }

    public static GameJournalEntity createTestGameJournalEntityC(final GameRepository gameRepo,
                                                                 final UserRepository userRepo) {
        var gameEntity = createTestGameEntityC(userRepo);
        if (gameRepo.findById(gameEntity.getGame_id()).isEmpty()) {
            gameRepo.save(gameEntity);
        }

        return GameJournalEntity.builder()
                .game(gameEntity)
                .actionType(GameActionType.LEAVE)
                .turn_date(Timestamp.valueOf(LocalDateTime.of(2024, 1, 12, 23, 27, 18)))
                .build();
    }

    public static LeaderboardEntity createTestLeaderboardEntityA(final UserRepository userRepo) {

        var userA = createTestUserEntityA();

        if (userRepo.findById(userA.getUser_id()).isEmpty()) {
            userRepo.save(userA);
        }

        return LeaderboardEntity.builder()
                .user_pos(userA.getUser_id())
                .user_id(userA)
                .score(666L)
                .build();
    }

    public static LeaderboardEntity createTestLeaderboardEntityB(final UserRepository userRepo) {

        var userB = createTestUserEntityB();

        if (userRepo.findById(userB.getUser_id()).isEmpty()) {
            userRepo.save(userB);
        }

        return LeaderboardEntity.builder()
                .user_pos(userB.getUser_id())
                .user_id(userB)
                .score(145L)
                .build();
    }

    public static LeaderboardEntity createTestLeaderboardEntityC(final UserRepository userRepo) {

        var userC = createTestUserEntityC();

        if (userRepo.findById(userC.getUser_id()).isEmpty()) {
            userRepo.save(userC);
        }

        return LeaderboardEntity.builder()
                .user_pos(userC.getUser_id())
                .user_id(userC)
                .score(110L)
                .build();
    }

    public static MessageEntity createTestMessageEntityA(final GameRepository gameRepo,
                                                         final UserRepository userRepo) {

        var gameEntityA = createTestGameEntityA(userRepo);

        if (gameRepo.findById(gameEntityA.getGame_id()).isEmpty()) {
            gameRepo.save(gameEntityA);
        }

        return MessageEntity.builder()
                .message_id(1L)
                .game_id(gameEntityA.getGame_id())
                .user_rx(gameEntityA.getUser_white())
                .user_tx(gameEntityA.getUser_black())
                .text("tests are boring")
                .build();
    }

    public static MessageEntity createTestMessageEntityB(final GameRepository gameRepo,
                                                         final UserRepository userRepo) {

        var gameEntityB = createTestGameEntityB(userRepo);

        if (gameRepo.findById(gameEntityB.getGame_id()).isEmpty()) {
            gameRepo.save(gameEntityB);
        }

        return MessageEntity.builder()
                .message_id(2L)
                .game_id(gameEntityB.getGame_id())
                .user_rx(gameEntityB.getUser_white())
                .user_tx(gameEntityB.getUser_black())
                .text("very boring")
                .build();
    }

    public static MessageEntity createTestMessageEntityC(final GameRepository gameRepo,
                                                         final UserRepository userRepo) {

        var gameEntityC = createTestGameEntityC(userRepo);

        if (gameRepo.findById(gameEntityC.getGame_id()).isEmpty()) {
            gameRepo.save(gameEntityC);
        }

        return MessageEntity.builder()
                .message_id(3L)
                .game_id(gameEntityC.getGame_id())
                .user_rx(gameEntityC.getUser_white())
                .user_tx(gameEntityC.getUser_black())
                .text("very very boring")
                .build();
    }

    public static UserAvatarEntity createTestUserAvatarEntityA(final UserRepository userRepo) {

        var userA = createTestUserEntityA();
        var image = new byte[]{0x1};

        if (userRepo.findById(userA.getUser_id()).isEmpty()) {
            userRepo.save(userA);
        }

        return UserAvatarEntity.builder()
                .user_avatar_id(1L)
                .user_id(userA)
                .avatar_png(image)
                .build();
    }

    public static UserAvatarEntity createTestUserAvatarEntityB(final UserRepository userRepo) {

        var userB = createTestUserEntityB();

        if (userRepo.findById(userB.getUser_id()).isEmpty()) {
            userRepo.save(userB);
        }

        return UserAvatarEntity.builder()
                .user_avatar_id(2L)
                .user_id(userB)
                .avatar_png(new byte[]{0x1})
                .build();
    }
    
    public static UserAvatarEntity createTestUserAvatarEntityC(final UserRepository userRepo) {

        var userC = createTestUserEntityC();

        if (userRepo.findById(userC.getUser_id()).isEmpty()) {
            userRepo.save(userC);
        }

        return UserAvatarEntity.builder()
                .user_avatar_id(3L)
                .user_id(userC)
                .avatar_png(new byte[]{0x1})
                .build();
    }
}
