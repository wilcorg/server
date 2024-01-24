package org.gogame.server.repositories;

import org.gogame.server.domain.entities.*;
import org.springframework.data.util.Pair;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TestDataUtil {

    public static UserEntity createTestUserEntityA() {
        return UserEntity.builder()
                .userId(1L)
                .nickname("romka")
                .passwordHash("qwertyuiop")
                .email("romka@romka.romka")
                .joinDate(Timestamp.valueOf(LocalDateTime.of(2024, 1, 12, 18, 33, 11)))
                .build();
    }

    public static UserEntity createTestUserEntityB() {
        return UserEntity.builder()
                .userId(2L)
                .nickname("romka2")
                .passwordHash("qwertyuiop2")
                .email("romka2@romka.romka")
                .joinDate(Timestamp.valueOf(LocalDateTime.of(2024, 1, 12, 18, 33, 12)))
                .build();
    }

    public static UserEntity createTestUserEntityC() {
        return UserEntity.builder()
                .userId(3L)
                .nickname("romka3")
                .passwordHash("qwertyuiop3")
                .email("romka3@romka.romka")
                .joinDate(Timestamp.valueOf(LocalDateTime.of(2024, 1, 12, 18, 33, 12)))
                .build();
    }

    public static Pair<GameEntity, GameEntity> createTestGameEntityA(final UserRepository userRepo) {
        var userA = createTestUserEntityA();
        var userB = createTestUserEntityB();

        if (userRepo.findById(userA.getUserId()).isEmpty()) {
            userRepo.save(userA);
        }
        if (userRepo.findById(userB.getUserId()).isEmpty()) {
            userRepo.save(userB);
        }

        var gameId = 1L;

        return Pair.of(
                GameEntity.builder()
                        .gameId(gameId)
                        .userId(userA.getUserId())
                        .userColor(UserColor.BLACK)
                        .result(GameResult.WIN)
                        .build(),
                GameEntity.builder()
                        .gameId(gameId)
                        .userId(userB.getUserId())
                        .userColor(UserColor.WHITE)
                        .result(GameResult.LOSS)
                        .build()
        );
    }

    public static Pair<GameEntity, GameEntity> createTestGameEntityB(final UserRepository userRepo) {
        var userA = createTestUserEntityA();
        var userB = createTestUserEntityB();

        if (userRepo.findById(userA.getUserId()).isEmpty()) {
            userRepo.save(userA);
        }
        if (userRepo.findById(userB.getUserId()).isEmpty()) {
            userRepo.save(userB);
        }

        var gameId = 2L;

        return Pair.of(
                GameEntity.builder()
                        .gameId(gameId)
                        .userId(userA.getUserId())
                        .userColor(UserColor.BLACK)
                        .result(GameResult.LOSS)
                        .build(),
                GameEntity.builder()
                        .gameId(gameId)
                        .userId(userB.getUserId())
                        .userColor(UserColor.WHITE)
                        .result(GameResult.WIN)
                        .build()
        );
    }

    public static Pair<GameEntity, GameEntity> createTestGameEntityC(final UserRepository userRepo) {
        var userB = createTestUserEntityB();
        var userC = createTestUserEntityC();

        if (userRepo.findById(userB.getUserId()).isEmpty()) {
            userRepo.save(userB);
        }
        if (userRepo.findById(userC.getUserId()).isEmpty()) {
            userRepo.save(userC);
        }

        var gameId = 3L;

        return Pair.of(
                GameEntity.builder()
                        .gameId(gameId)
                        .userId(userC.getUserId())
                        .userColor(UserColor.BLACK)
                        .result(GameResult.WIN)
                        .build(),
                GameEntity.builder()
                        .gameId(gameId)
                        .userId(userB.getUserId())
                        .userColor(UserColor.WHITE)
                        .result(GameResult.LOSS)
                        .build()
        );
    }

    public static GameJournalEntity createTestGameJournalEntityA(final GameRepository gameRepo,
                                                                 final UserRepository userRepo) {
        var gameEntity = createTestGameEntityA(userRepo);
        if (gameRepo.findById(gameEntity.getFirst().getId()).isEmpty()) {
            gameRepo.save(gameEntity.getFirst());
            gameRepo.save(gameEntity.getSecond());
        }

        return GameJournalEntity.builder()
                .turnId(1L)
                .gameId(gameEntity.getFirst().getGameId())
                .action(GameAction.MOVE)
                .turnX(1)
                .turnY(2)
                .turnDate(Timestamp.valueOf(LocalDateTime.of(2024, 1, 12, 23, 27, 18)))
                .build();
    }

    public static GameJournalEntity createTestGameJournalEntityB(final GameRepository gameRepo,
                                                                 final UserRepository userRepo) {
        var gameEntity = createTestGameEntityB(userRepo);
        if (gameRepo.findById(gameEntity.getFirst().getId()).isEmpty()) {
            gameRepo.save(gameEntity.getFirst());
            gameRepo.save(gameEntity.getSecond());
        }

        return GameJournalEntity.builder()
                .gameId(gameEntity.getFirst().getGameId())
                .action(GameAction.STOP_REQ)
                .turnDate(Timestamp.valueOf(LocalDateTime.of(2024, 1, 12, 23, 27, 18)))
                .build();
    }

    public static GameJournalEntity createTestGameJournalEntityC(final GameRepository gameRepo,
                                                                 final UserRepository userRepo) {
        var gameEntity = createTestGameEntityC(userRepo);
        if (gameRepo.findById(gameEntity.getFirst().getId()).isEmpty()) {
            gameRepo.save(gameEntity.getFirst());
            gameRepo.save(gameEntity.getSecond());
        }

        return GameJournalEntity.builder()
                .gameId(gameEntity.getFirst().getGameId())
                .action(GameAction.LEAVE)
                .turnDate(Timestamp.valueOf(LocalDateTime.of(2024, 1, 12, 23, 27, 18)))
                .build();
    }

    public static LeaderboardEntity createTestLeaderboardEntityA(final UserRepository userRepo) {

        var userA = createTestUserEntityA();

        if (userRepo.findById(userA.getUserId()).isEmpty()) {
            userRepo.save(userA);
        }

        return LeaderboardEntity.builder()
                .userPos(1L)
                .user(userA)
                .score(666L)
                .build();
    }

    public static LeaderboardEntity createTestLeaderboardEntityB(final UserRepository userRepo) {

        var userB = createTestUserEntityB();

        if (userRepo.findById(userB.getUserId()).isEmpty()) {
            userRepo.save(userB);
        }

        return LeaderboardEntity.builder()
                .userPos(2L)
                .user(userB)
                .score(145L)
                .build();
    }

    public static LeaderboardEntity createTestLeaderboardEntityC(final UserRepository userRepo) {

        var userC = createTestUserEntityC();

        if (userRepo.findById(userC.getUserId()).isEmpty()) {
            userRepo.save(userC);
        }

        return LeaderboardEntity.builder()
                .userPos(3L)
                .user(userC)
                .score(110L)
                .build();
    }

    public static MessageEntity createTestMessageEntityA(final GameRepository gameRepo,
                                                         final UserRepository userRepo) {

        var gameEntityA = createTestGameEntityA(userRepo);

        if (gameRepo.findById(gameEntityA.getFirst().getId()).isEmpty()) {
            gameRepo.save(gameEntityA.getFirst());
            gameRepo.save(gameEntityA.getSecond());
        }

        var userBlackId = gameEntityA.getFirst().getUserColor() == UserColor.BLACK ?
                gameEntityA.getFirst().getUserId() : gameEntityA.getSecond().getUserId();

        var userBlackOpt = userRepo.findById(userBlackId);

        if (userBlackOpt.isEmpty()) {
            return null;
        }

        var userBlack = userBlackOpt.get();

        return MessageEntity.builder()
                .messageId(1L)
                .gameId(gameEntityA.getFirst().getGameId())
                .author(userBlack)
                .text("tests are boring")
                .build();
    }

    public static MessageEntity createTestMessageEntityB(final GameRepository gameRepo,
                                                         final UserRepository userRepo) {

        var gameEntityB = createTestGameEntityB(userRepo);

        if (gameRepo.findById(gameEntityB.getFirst().getId()).isEmpty()) {
            gameRepo.save(gameEntityB.getFirst());
            gameRepo.save(gameEntityB.getSecond());
        }

        var userBlackId = gameEntityB.getFirst().getUserColor() == UserColor.BLACK ?
                gameEntityB.getFirst().getUserId() : gameEntityB.getSecond().getUserId();

        var userBlackOpt = userRepo.findById(userBlackId);

        if (userBlackOpt.isEmpty()) {
            return null;
        }

        var userBlack = userBlackOpt.get();

        return MessageEntity.builder()
                .messageId(2L)
                .gameId(gameEntityB.getFirst().getGameId())
                .author(userBlack)
                .text("very boring")
                .build();
    }

    public static MessageEntity createTestMessageEntityC(final GameRepository gameRepo,
                                                         final UserRepository userRepo) {

        var gameEntityC = createTestGameEntityC(userRepo);

        if (gameRepo.findById(gameEntityC.getFirst().getId()).isEmpty()) {
            gameRepo.save(gameEntityC.getFirst());
            gameRepo.save(gameEntityC.getSecond());
        }

        var userBlackId = gameEntityC.getFirst().getUserColor() == UserColor.BLACK ?
                gameEntityC.getFirst().getUserId() : gameEntityC.getSecond().getUserId();

        var userBlackOpt = userRepo.findById(userBlackId);

        if (userBlackOpt.isEmpty()) {
            return null;
        }

        var userBlack = userBlackOpt.get();

        return MessageEntity.builder()
                .messageId(3L)
                .gameId(gameEntityC.getFirst().getGameId())
                .author(userBlack)
                .text("very very boring")
                .build();
    }
}