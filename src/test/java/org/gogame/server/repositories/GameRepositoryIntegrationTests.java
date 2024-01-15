package org.gogame.server.repositories;

import org.gogame.server.domain.entities.GameEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
        GameEntity gameEntityA = TestDataUtil.createTestGameEntityA(userRepo);
        gameRepo.save(gameEntityA);
        Optional<GameEntity> result = gameRepo.findById(gameEntityA.getGameId());
        assertThat(result).isPresent();
        assertThat(result).contains(gameEntityA);
    }

    @Test
    public void testThatMultipleGamesCanBeCreatedAndRecalled() {
        GameEntity gameEntityA = TestDataUtil.createTestGameEntityA(userRepo);
        gameRepo.save(gameEntityA);
        GameEntity gameEntityB = TestDataUtil.createTestGameEntityB(userRepo);
        gameRepo.save(gameEntityB);
        GameEntity gameEntityC = TestDataUtil.createTestGameEntityC(userRepo);
        gameRepo.save(gameEntityC);

        Iterable<GameEntity> result = gameRepo.findAll();
        Iterable<GameEntity> expected = new ArrayList<>(Arrays.asList(gameEntityA, gameEntityB, gameEntityC));
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    public void testThatGameCanBeUpdated() {
        GameEntity gameEntityA = TestDataUtil.createTestGameEntityA(userRepo);
        gameRepo.save(gameEntityA);
        gameEntityA.setWinner(gameEntityA.getUserWhite());
        gameRepo.save(gameEntityA);
        Optional<GameEntity> result = gameRepo.findById(gameEntityA.getGameId());
        assertThat(result).isPresent();
        assertThat(result).contains(gameEntityA);
    }

    @Test
    public void testThatGameCanBeDeleted() {
        GameEntity gameEntityA = TestDataUtil.createTestGameEntityA(userRepo);
        gameRepo.save(gameEntityA);
        gameRepo.deleteById(gameEntityA.getGameId());
        Optional<GameEntity> result = gameRepo.findById(gameEntityA.getGameId());
        assertThat(result).isEmpty();
    }
}
