package org.gogame.server.repositories;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.gogame.server.domain.entities.*;
import org.gogame.server.domain.entities.dto.UserLoginDto;
import org.gogame.server.domain.entities.dto.UserRegisterDto;
import org.springframework.test.web.servlet.MvcResult;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TestData {
    public static class UserEntityUtils {

        public static UserEntity createA() {
            return UserEntity.builder()
                    .nickname("romka")
                    .passwordHash("qwertyuiop")
                    .email("romka@romka.romka")
                    .joinDate(Timestamp.valueOf(LocalDateTime.of(2024, 1, 12, 18, 33, 11)))
                    .build();
        }

        public static UserEntity createB() {
            return UserEntity.builder()
                    .nickname("romka2")
                    .passwordHash("qwertyuiop2")
                    .email("romka2@romka.romka")
                    .joinDate(Timestamp.valueOf(LocalDateTime.of(2024, 1, 12, 18, 33, 12)))
                    .build();
        }

        public static UserEntity createC() {
            return UserEntity.builder()
                    .nickname("romka3")
                    .passwordHash("qwertyuiop3")
                    .email("romka3@romka.romka")
                    .joinDate(Timestamp.valueOf(LocalDateTime.of(2024, 1, 12, 18, 33, 12)))
                    .build();
        }
    }

    public static class GameEntityUtils {

        public static GameEntity createA(final UserRepository userRepo) {
            var userW = UserEntityUtils.createA();
            var userB = UserEntityUtils.createB();

            if (userRepo.findByNickname(userW.getNickname()).isEmpty()) {
                userRepo.save(userW);
            } else {
                userW = userRepo.findByNickname(userW.getNickname()).get();
            }

            if (userRepo.findByNickname(userB.getNickname()).isEmpty()) {
                userRepo.save(userB);
            } else {
                userB = userRepo.findByNickname(userB.getNickname()).get();
            }

            return GameEntity.builder()
                    .userWhite(userW)
                    .userBlack(userB)
                    .winner(userW)
                    .build();
        }

        public static GameEntity createB(final UserRepository userRepo) {
            var userW = UserEntityUtils.createA();
            var userB = UserEntityUtils.createB();

            if (userRepo.findByNickname(userW.getNickname()).isEmpty()) {
                userRepo.save(userW);
            } else {
                userW = userRepo.findByNickname(userW.getNickname()).get();
            }

            if (userRepo.findByNickname(userB.getNickname()).isEmpty()) {
                userRepo.save(userB);
            } else {
                userB = userRepo.findByNickname(userB.getNickname()).get();
            }

            return GameEntity.builder()
                    .userWhite(userW)
                    .userBlack(userB)
                    .winner(userW)
                    .build();
        }

        public static GameEntity createC(final UserRepository userRepo) {
            var userW = UserEntityUtils.createB();
            var userB = UserEntityUtils.createC();

            if (userRepo.findByNickname(userW.getNickname()).isEmpty()) {
                userRepo.save(userW);
            } else {
                userW = userRepo.findByNickname(userW.getNickname()).get();
            }

            if (userRepo.findByNickname(userB.getNickname()).isEmpty()) {
                userRepo.save(userB);
            } else {
                userB = userRepo.findByNickname(userB.getNickname()).get();
            }

            return GameEntity.builder()
                    .userWhite(userW)
                    .userBlack(userB)
                    .winner(userB)
                    .build();
        }
    }

    public static class GameJournalUtils {

        public static GameJournalEntity createA(final GameRepository gameRepo,
                                                final UserRepository userRepo) {
            var gameEntity = GameEntityUtils.createA(userRepo);
            gameRepo.save(gameEntity);

            return GameJournalEntity.builder()
                    .game(gameEntity)
                    .action(GameAction.MOVE)
                    .author(gameEntity.getUserWhite())
                    .turnX(1)
                    .turnY(2)
                    .turnDate(Timestamp.valueOf(LocalDateTime.of(2024, 1, 12, 23, 27, 18)))
                    .build();
        }

        public static GameJournalEntity createB(final GameRepository gameRepo,
                                                final UserRepository userRepo) {
            var gameEntity = GameEntityUtils.createB(userRepo);
            gameRepo.save(gameEntity);

            return GameJournalEntity.builder()
                    .game(gameEntity)
                    .action(GameAction.STOP_REQ)
                    .author(gameEntity.getUserBlack())
                    .turnDate(Timestamp.valueOf(LocalDateTime.of(2024, 1, 12, 23, 27, 18)))
                    .build();
        }

        public static GameJournalEntity createC(final GameRepository gameRepo,
                                                final UserRepository userRepo) {
            var gameEntity = GameEntityUtils.createC(userRepo);
            gameRepo.save(gameEntity);

            return GameJournalEntity.builder()
                    .game(gameEntity)
                    .action(GameAction.LEAVE)
                    .author(gameEntity.getUserWhite())
                    .turnDate(Timestamp.valueOf(LocalDateTime.of(2024, 1, 12, 23, 27, 18)))
                    .build();
        }
    }

    public static class LeaderboardUtils {

        public static LeaderboardEntity createA(final UserRepository userRepo) {

            var userA = UserEntityUtils.createA();

            if (userRepo.findByNickname(userA.getNickname()).isEmpty()) {
                userRepo.save(userA);
            }

            return LeaderboardEntity.builder()
                    .userId(userA.getUserId())
                    .score(666L)
                    .build();
        }

        public static LeaderboardEntity createB(final UserRepository userRepo) {

            var userB = UserEntityUtils.createB();

            if (userRepo.findByNickname(userB.getNickname()).isEmpty()) {
                userRepo.save(userB);
            }

            return LeaderboardEntity.builder()
                    .userId(userB.getUserId())
                    .score(145L)
                    .build();
        }

        public static LeaderboardEntity createC(final UserRepository userRepo) {

            var userC = UserEntityUtils.createC();

            if (userRepo.findByNickname(userC.getNickname()).isEmpty()) {
                userRepo.save(userC);
            }

            return LeaderboardEntity.builder()
                    .userId(userC.getUserId())
                    .score(110L)
                    .build();
        }
    }

    public static class MessageUtils {

        public static MessageEntity createA(final GameRepository gameRepo,
                                            final UserRepository userRepo) {

            var gameEntity = GameEntityUtils.createA(userRepo);
            gameRepo.save(gameEntity);

            return MessageEntity.builder()
                    .game(gameEntity)
                    .author(gameEntity.getUserWhite())
                    .content("tests are boring")
                    .build();
        }

        public static MessageEntity createB(final GameRepository gameRepo,
                                            final UserRepository userRepo) {

            var gameEntity = GameEntityUtils.createB(userRepo);
            gameRepo.save(gameEntity);

            return MessageEntity.builder()
                    .game(gameEntity)
                    .author(gameEntity.getUserWhite())
                    .content("very boring")
                    .build();
        }

        public static MessageEntity createC(final GameRepository gameRepo,
                                            final UserRepository userRepo) {

            var gameEntity = GameEntityUtils.createC(userRepo);
            gameRepo.save(gameEntity);

            return MessageEntity.builder()
                    .game(gameEntity)
                    .author(gameEntity.getUserWhite())
                    .content("very very boring")
                    .build();
        }
    }

    public static class RegisterDtoUtils {

        public static UserRegisterDto createA() {
            return UserRegisterDto.builder()
                    .nickname("romka")
                    .password("qwertyuiop")
                    .email("romka@romka.romka")
                    .build();
        }

        public static UserRegisterDto createB() {
            return UserRegisterDto.builder()
                    .nickname("romka2")
                    .password("qwertyuiop2")
                    .email("romka2@romka.romka")
                    .build();
        }

        public static UserRegisterDto createC() {
            return UserRegisterDto.builder()
                    .nickname("romka3")
                    .password("qwertyuiop3")
                    .email("romka3@romka.romka")
                    .build();
        }
    }

    public static class LoginDtoUtils {

        public static UserLoginDto createA() {
            return UserLoginDto.builder()
                    .nickname("romka")
                    .password("qwertyuiop")
                    .build();
        }

        public static UserLoginDto createB() {
            return UserLoginDto.builder()
                    .nickname("romka2")
                    .password("qwertyuiop2")
                    .build();
        }

        public static UserLoginDto createC() {
            return UserLoginDto.builder()
                    .nickname("romka3")
                    .password("qwertyuiop3")
                    .build();
        }

        public static UserLoginDto createUnknownUser() {
            return UserLoginDto.builder()
                    .nickname("c#")
                    .password("microsoft")
                    .build();
        }
    }

    public static String getJwtToken(MvcResult mvcResult) {
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode;
        try {
            String responseContent = mvcResult.getResponse().getContentAsString();
            jsonNode = objectMapper.readTree(responseContent);
        } catch (Exception e) {
            System.err.println("Unable do decode JSON");
            return "";
        }
        return "Bearer " + jsonNode.get("token").asText();

    }
}