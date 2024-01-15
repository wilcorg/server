package org.gogame.server.repositories;

import org.gogame.server.domain.entities.GameActionType;
import org.gogame.server.domain.entities.GameJournalEntity;
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
public class GameJournalRepositoryIntegrationTests {

    private final GameRepository gameRepo;

    private final UserRepository userRepo;

    private final GameJournalRepository gameJournalRepo;
    @Autowired
    public GameJournalRepositoryIntegrationTests(GameRepository gameRepo,
                                                 UserRepository userRepo,
                                                 GameJournalRepository gameJournalRepo) {
        this.gameRepo = gameRepo;
        this.userRepo = userRepo;
        this.gameJournalRepo = gameJournalRepo;
    }

    @Test
    public void testThatGameJournalCanBeCreatedAndRecalled() {
        GameJournalEntity gameJournalEntityA = TestDataUtil.createTestGameJournalEntityA(gameRepo, userRepo);
        gameJournalRepo.save(gameJournalEntityA);
        Optional<GameJournalEntity> result = gameJournalRepo.findById(gameJournalEntityA.getTurn_id());
        assertThat(result).isPresent();
        assertThat(result).contains(gameJournalEntityA);
    }

    @Test
    public void testThatMultipleGameJournalsCanBeCreatedAndRecalled() {
        GameJournalEntity gameJournalEntityA = TestDataUtil.createTestGameJournalEntityA(gameRepo, userRepo);
        gameJournalRepo.save(gameJournalEntityA);
        GameJournalEntity gameJournalEntityB = TestDataUtil.createTestGameJournalEntityB(gameRepo, userRepo);
        gameJournalRepo.save(gameJournalEntityB);
        GameJournalEntity gameJournalEntityC = TestDataUtil.createTestGameJournalEntityC(gameRepo, userRepo);
        gameJournalRepo.save(gameJournalEntityC);

        Iterable<GameJournalEntity> result = gameJournalRepo.findAll();
        Iterable<GameJournalEntity> expected = new ArrayList<>(Arrays.asList(gameJournalEntityA, gameJournalEntityB, gameJournalEntityC));
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    public void testThatGameJournalCanBeUpdated() {
        GameJournalEntity gameJournalEntityA = TestDataUtil.createTestGameJournalEntityA(gameRepo, userRepo);
        gameJournalRepo.save(gameJournalEntityA);
        gameJournalEntityA.setActionType(GameActionType.STOP_REQ);
        gameJournalRepo.save(gameJournalEntityA);
        Optional<GameJournalEntity> result = gameJournalRepo.findById(gameJournalEntityA.getTurn_id());
        assertThat(result).isPresent();
        assertThat(result).contains(gameJournalEntityA);
    }

    @Test
    public void testThatGameJournalCanBeDeleted() {
        GameJournalEntity gameJournalEntityA = TestDataUtil.createTestGameJournalEntityA(gameRepo, userRepo);
        gameJournalRepo.save(gameJournalEntityA);
        gameJournalRepo.deleteById(gameJournalEntityA.getTurn_id());
        Optional<GameJournalEntity> result = gameJournalRepo.findById(gameJournalEntityA.getTurn_id());
        assertThat(result).isEmpty();
    }
}
