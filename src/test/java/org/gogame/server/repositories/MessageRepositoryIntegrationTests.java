package org.gogame.server.repositories;

import org.gogame.server.domain.entities.MessageEntity;
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
public class MessageRepositoryIntegrationTests {

    private final GameRepository gameRepo;

    private final UserRepository userRepo;

    private final MessageRepository messageRepo;

    @Autowired
    public MessageRepositoryIntegrationTests(GameRepository gameRepo,
                                             UserRepository userRepo,
                                             MessageRepository messageRepo) {
        this.gameRepo = gameRepo;
        this.userRepo = userRepo;
        this.messageRepo = messageRepo;
    }

    @Test
    public void testThatMessageCanBeCreatedAndRecalled() {
        MessageEntity messageEntityA = TestDataUtil.createTestMessageEntityA(gameRepo, userRepo);
        messageRepo.save(messageEntityA);
        Optional<MessageEntity> result = messageRepo.findById(messageEntityA.getGame_id());
        assertThat(result).isPresent();
        assertThat(result).contains(messageEntityA);
    }

    @Test
    public void testThatMultipleMessagesCanBeCreatedAndRecalled() {
        MessageEntity messageEntityA = TestDataUtil.createTestMessageEntityA(gameRepo, userRepo);
        messageRepo.save(messageEntityA);
        MessageEntity messageEntityB = TestDataUtil.createTestMessageEntityB(gameRepo, userRepo);
        messageRepo.save(messageEntityB);
        MessageEntity messageEntityC = TestDataUtil.createTestMessageEntityC(gameRepo, userRepo);
        messageRepo.save(messageEntityC);

        Iterable<MessageEntity> result = messageRepo.findAll();
        Iterable<MessageEntity> expected = new ArrayList<>(Arrays.asList(messageEntityA, messageEntityB, messageEntityC));
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }
}
