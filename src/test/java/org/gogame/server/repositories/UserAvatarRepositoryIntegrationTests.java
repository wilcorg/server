package org.gogame.server.repositories;

import org.gogame.server.domain.entities.UserAvatarEntity;
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
public class UserAvatarRepositoryIntegrationTests {

    private final UserAvatarRepository userAvatarRepo;
    private final UserRepository userRepo;

    @Autowired
    public UserAvatarRepositoryIntegrationTests(UserAvatarRepository userAvatarRepo,
                                                UserRepository userRepo) {
        this.userAvatarRepo = userAvatarRepo;
        this.userRepo = userRepo;
    }

    @Test
    public void testThatUserAvatarCanBeCreatedAndRecalled() {
        UserAvatarEntity userAvatarA = TestDataUtil.createTestUserAvatarEntityA(userRepo);
        userAvatarRepo.save(userAvatarA);
        Optional<UserAvatarEntity> result = userAvatarRepo.findById(userAvatarA.getUser_avatar_id());
        assertThat(result).isPresent();
        assertThat(result).contains(userAvatarA);
    }

    @Test
    public void testThatMultipleUserAvatarsCanBeCreatedAndRecalled() {
        UserAvatarEntity userAvatarA = TestDataUtil.createTestUserAvatarEntityA(userRepo);
        userAvatarRepo.save(userAvatarA);
        UserAvatarEntity userAvatarB = TestDataUtil.createTestUserAvatarEntityB(userRepo);
        userAvatarRepo.save(userAvatarB);
        UserAvatarEntity userAvatarC = TestDataUtil.createTestUserAvatarEntityC(userRepo);
        userAvatarRepo.save(userAvatarC);

        Iterable<UserAvatarEntity> result = userAvatarRepo.findAll();
        Iterable<UserAvatarEntity> expected = new ArrayList<>(Arrays.asList(userAvatarA, userAvatarB, userAvatarC));
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    public void testThatUserAvatarCanBeUpdated() {
        UserAvatarEntity userAvatarA = TestDataUtil.createTestUserAvatarEntityA(userRepo);
        userAvatarRepo.save(userAvatarA);
        userAvatarA.setAvatar_png(new byte[]{0x2});
        userAvatarRepo.save(userAvatarA);
        Optional<UserAvatarEntity> result = userAvatarRepo.findById(userAvatarA.getUser_avatar_id());
        assertThat(result).isPresent();
        assertThat(result).contains(userAvatarA);
    }

    @Test
    public void testThatUserAvatarCanBeDeleted() {
        UserAvatarEntity userAvatarA = TestDataUtil.createTestUserAvatarEntityA(userRepo);
        userAvatarRepo.save(userAvatarA);
        userAvatarRepo.deleteById(userAvatarA.getUser_avatar_id());
        Optional<UserAvatarEntity> result = userAvatarRepo.findById(userAvatarA.getUser_avatar_id());
        assertThat(result).isEmpty();
    }

}
