package org.gogame.server.repositories;

import org.gogame.server.domain.entities.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TestDataUtil {

    public static UserEntity createTestUserEntityA() {
        return UserEntity.builder()
                .userId(1L)
                .nickname("romka")
                .passwordHashSha512("qwertyuiop")
                .email("romka@romka.romka")
                .joinDate(Timestamp.valueOf(LocalDateTime.of(2024, 1, 12, 18, 33, 11)))
                .build();
    }

    public static UserEntity createTestUserEntityB() {
        return UserEntity.builder()
                .userId(2L)
                .nickname("romka2")
                .passwordHashSha512("qwertyuiop2")
                .email("romka2@romka.romka")
                .joinDate(Timestamp.valueOf(LocalDateTime.of(2024, 1, 12, 18, 33, 12)))
                .build();
    }

    public static UserEntity createTestUserEntityC() {
        return UserEntity.builder()
                .userId(3L)
                .nickname("romka3")
                .passwordHashSha512("qwertyuiop3")
                .email("romka3@romka.romka")
                .joinDate(Timestamp.valueOf(LocalDateTime.of(2024, 1, 12, 18, 33, 12)))
                .build();
    }

    public static GameEntity createTestGameEntityA(final UserRepository userRepo) {
        var userA = createTestUserEntityA();
        var userB = createTestUserEntityB();

        if (userRepo.findById(userA.getUserId()).isEmpty()) {
            userRepo.save(userA);
        }
        if (userRepo.findById(userB.getUserId()).isEmpty()) {
            userRepo.save(userB);
        }

        return GameEntity.builder()
                .gameId(1L)
                .userBlack(userA)
                .userWhite(userB)
                .winner(userA)
                .build();
    }

    public static GameEntity createTestGameEntityB(final UserRepository userRepo) {
        var userA = createTestUserEntityA();
        var userB = createTestUserEntityB();

        if (userRepo.findById(userA.getUserId()).isEmpty()) {
            userRepo.save(userA);
        }
        if (userRepo.findById(userB.getUserId()).isEmpty()) {
            userRepo.save(userB);
        }

        return GameEntity.builder()
                .gameId(2L)
                .userBlack(userA)
                .userWhite(userB)
                .winner(userB)
                .build();
    }

    public static GameEntity createTestGameEntityC(final UserRepository userRepo) {
        var userB = createTestUserEntityB();
        var userC = createTestUserEntityC();

        if (userRepo.findById(userB.getUserId()).isEmpty()) {
            userRepo.save(userB);
        }
        if (userRepo.findById(userC.getUserId()).isEmpty()) {
            userRepo.save(userC);
        }

        return GameEntity.builder()
                .gameId(3L)
                .userBlack(userC)
                .userWhite(userB)
                .winner(userC)
                .build();
    }

    public static GameJournalEntity createTestGameJournalEntityA(final GameRepository gameRepo,
                                                                 final UserRepository userRepo) {
        var gameEntity = createTestGameEntityA(userRepo);
        if (gameRepo.findById(gameEntity.getGameId()).isEmpty()) {
            gameRepo.save(gameEntity);
        }

        return GameJournalEntity.builder()
                .turn_id(1L)
                .game(gameEntity)
                .actionType(GameActionType.MOVE)
                .turnX(1)
                .turnY(2)
                .turnDate(Timestamp.valueOf(LocalDateTime.of(2024, 1, 12, 23, 27, 18)))
                .build();
    }

    public static GameJournalEntity createTestGameJournalEntityB(final GameRepository gameRepo,
                                                                 final UserRepository userRepo) {
        var gameEntity = createTestGameEntityB(userRepo);
        if (gameRepo.findById(gameEntity.getGameId()).isEmpty()) {
            gameRepo.save(gameEntity);
        }

        return GameJournalEntity.builder()
                .game(gameEntity)
                .actionType(GameActionType.STOP_REQ)
                .turnDate(Timestamp.valueOf(LocalDateTime.of(2024, 1, 12, 23, 27, 18)))
                .build();
    }

    public static GameJournalEntity createTestGameJournalEntityC(final GameRepository gameRepo,
                                                                 final UserRepository userRepo) {
        var gameEntity = createTestGameEntityC(userRepo);
        if (gameRepo.findById(gameEntity.getGameId()).isEmpty()) {
            gameRepo.save(gameEntity);
        }

        return GameJournalEntity.builder()
                .game(gameEntity)
                .actionType(GameActionType.LEAVE)
                .turnDate(Timestamp.valueOf(LocalDateTime.of(2024, 1, 12, 23, 27, 18)))
                .build();
    }

    public static LeaderboardEntity createTestLeaderboardEntityA(final UserRepository userRepo) {

        var userA = createTestUserEntityA();

        if (userRepo.findById(userA.getUserId()).isEmpty()) {
            userRepo.save(userA);
        }

        return LeaderboardEntity.builder()
                .userPos(userA.getUserId())
                .userId(userA)
                .score(666L)
                .build();
    }

    public static LeaderboardEntity createTestLeaderboardEntityB(final UserRepository userRepo) {

        var userB = createTestUserEntityB();

        if (userRepo.findById(userB.getUserId()).isEmpty()) {
            userRepo.save(userB);
        }

        return LeaderboardEntity.builder()
                .userPos(userB.getUserId())
                .userId(userB)
                .score(145L)
                .build();
    }

    public static LeaderboardEntity createTestLeaderboardEntityC(final UserRepository userRepo) {

        var userC = createTestUserEntityC();

        if (userRepo.findById(userC.getUserId()).isEmpty()) {
            userRepo.save(userC);
        }

        return LeaderboardEntity.builder()
                .userPos(userC.getUserId())
                .userId(userC)
                .score(110L)
                .build();
    }

    public static MessageEntity createTestMessageEntityA(final GameRepository gameRepo,
                                                         final UserRepository userRepo) {

        var gameEntityA = createTestGameEntityA(userRepo);

        if (gameRepo.findById(gameEntityA.getGameId()).isEmpty()) {
            gameRepo.save(gameEntityA);
        }

        return MessageEntity.builder()
                .messageId(1L)
                .gameId(gameEntityA.getGameId())
                .userRx(gameEntityA.getUserWhite())
                .userTx(gameEntityA.getUserBlack())
                .text("tests are boring")
                .build();
    }

    public static MessageEntity createTestMessageEntityB(final GameRepository gameRepo,
                                                         final UserRepository userRepo) {

        var gameEntityB = createTestGameEntityB(userRepo);

        if (gameRepo.findById(gameEntityB.getGameId()).isEmpty()) {
            gameRepo.save(gameEntityB);
        }

        return MessageEntity.builder()
                .messageId(2L)
                .gameId(gameEntityB.getGameId())
                .userRx(gameEntityB.getUserWhite())
                .userTx(gameEntityB.getUserBlack())
                .text("very boring")
                .build();
    }

    public static MessageEntity createTestMessageEntityC(final GameRepository gameRepo,
                                                         final UserRepository userRepo) {

        var gameEntityC = createTestGameEntityC(userRepo);

        if (gameRepo.findById(gameEntityC.getGameId()).isEmpty()) {
            gameRepo.save(gameEntityC);
        }

        return MessageEntity.builder()
                .messageId(3L)
                .gameId(gameEntityC.getGameId())
                .userRx(gameEntityC.getUserWhite())
                .userTx(gameEntityC.getUserBlack())
                .text("very very boring")
                .build();
    }

    public static UserAvatarEntity createTestUserAvatarEntityA(final UserRepository userRepo) {

        var userA = createTestUserEntityA();
        var image = new byte[]{0x1};

        if (userRepo.findById(userA.getUserId()).isEmpty()) {
            userRepo.save(userA);
        }

        return UserAvatarEntity.builder()
                .user_avatar_id(1L)
                .userId(userA)
                .avatarPng(image)
                .build();
    }

    public static UserAvatarEntity createTestUserAvatarEntityB(final UserRepository userRepo) {

        var userB = createTestUserEntityB();

        if (userRepo.findById(userB.getUserId()).isEmpty()) {
            userRepo.save(userB);
        }

        return UserAvatarEntity.builder()
                .user_avatar_id(2L)
                .userId(userB)
                .avatarPng(new byte[]{0x1})
                .build();
    }
    
    public static UserAvatarEntity createTestUserAvatarEntityC(final UserRepository userRepo) {

        var userC = createTestUserEntityC();

        if (userRepo.findById(userC.getUserId()).isEmpty()) {
            userRepo.save(userC);
        }

        return UserAvatarEntity.builder()
                .user_avatar_id(3L)
                .userId(userC)
                .avatarPng(new byte[]{0x1})
                .build();
    }
}
