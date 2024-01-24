package org.gogame.server.repositories;

import org.gogame.server.domain.entities.GameEntity;
import org.gogame.server.domain.entities.GameResult;
import org.gogame.server.domain.entities.UserColor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GameRepositoryIntegrationTests {

    private final GameRepository gameRepo;

    private final UserRepository userRepo;

    @Autowired
    public GameRepositoryIntegrationTests(GameRepository gameRepo, UserRepository userRepo) {
        this.gameRepo = gameRepo;
        this.userRepo = userRepo;
    }

    @Test
    public void testThatGameCanBeCreatedAndRecalled() {
        Pair<GameEntity, GameEntity> gameEntityA = TestDataUtil.createTestGameEntityA(userRepo);
        gameRepo.save(gameEntityA.getFirst());
        gameRepo.save(gameEntityA.getSecond());

        Optional<GameEntity> resultFirst = gameRepo.findById(gameEntityA.getFirst().getId());
        Optional<GameEntity> resultSecond = gameRepo.findById(gameEntityA.getSecond().getId());

        assertThat(resultFirst).isPresent();
        assertThat(resultFirst).contains(gameEntityA.getFirst());

        assertThat(resultSecond).isPresent();
        assertThat(resultSecond).contains(gameEntityA.getSecond());

        assertThat(resultFirst.get().getGameId())
                .isEqualTo(resultSecond.get().getGameId());
    }

    @Test
    public void testThatMultipleGamesCanBeCreatedAndRecalled() {
        Pair<GameEntity, GameEntity> gameEntityA = TestDataUtil.createTestGameEntityA(userRepo);
        gameRepo.save(gameEntityA.getFirst());
        gameRepo.save(gameEntityA.getSecond());

        Pair<GameEntity, GameEntity> gameEntityB = TestDataUtil.createTestGameEntityB(userRepo);
        gameRepo.save(gameEntityB.getFirst());
        gameRepo.save(gameEntityB.getSecond());

        Pair<GameEntity, GameEntity> gameEntityC = TestDataUtil.createTestGameEntityC(userRepo);
        gameRepo.save(gameEntityC.getFirst());
        gameRepo.save(gameEntityC.getSecond());

        Iterable<GameEntity> result = gameRepo.findAll();
        Iterable<GameEntity> expected = new ArrayList<>(Arrays.asList(
                gameEntityA.getFirst(), gameEntityA.getSecond(),
                gameEntityB.getFirst(), gameEntityB.getSecond(),
                gameEntityC.getFirst(), gameEntityC.getSecond()
        ));

        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    public void testThatGameCanBeUpdated() {
        Pair<GameEntity, GameEntity> gameEntityA = TestDataUtil.createTestGameEntityA(userRepo);
        gameRepo.save(gameEntityA.getFirst());
        gameRepo.save(gameEntityA.getSecond());

        gameEntityA.getFirst().setResult(
                gameEntityA.getFirst().getUserColor() == UserColor.WHITE
                        ? GameResult.WIN : GameResult.LOSS
        );
        gameEntityA.getSecond().setResult(
                gameEntityA.getSecond().getUserColor() == UserColor.WHITE
                        ? GameResult.WIN : GameResult.LOSS
        );

        gameRepo.save(gameEntityA.getFirst());
        gameRepo.save(gameEntityA.getSecond());

        Optional<GameEntity> resultFirst = gameRepo.findById(gameEntityA.getFirst().getId());
        Optional<GameEntity> resultSecond = gameRepo.findById(gameEntityA.getSecond().getId());

        assertThat(resultFirst).isPresent();
        assertThat(resultFirst).contains(gameEntityA.getFirst());

        assertThat(resultSecond).isPresent();
        assertThat(resultSecond).contains(gameEntityA.getSecond());
    }

    @Test
    public void testThatGameCanBeDeleted() {
        Pair<GameEntity, GameEntity> gameEntityA = TestDataUtil.createTestGameEntityA(userRepo);

        gameRepo.save(gameEntityA.getFirst());
        gameRepo.save(gameEntityA.getSecond());

        gameRepo.deleteById(gameEntityA.getFirst().getId());
        gameRepo.deleteById(gameEntityA.getSecond().getId());

        Optional<GameEntity> resultFirst = gameRepo.findById(gameEntityA.getFirst().getId());
        Optional<GameEntity> resultSecond = gameRepo.findById(gameEntityA.getSecond().getId());

        assertThat(resultFirst).isEmpty();
        assertThat(resultSecond).isEmpty();
    }
}
