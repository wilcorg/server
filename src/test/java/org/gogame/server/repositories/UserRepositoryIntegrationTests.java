package org.gogame.server.repositories;

import org.gogame.server.domain.entities.UserEntity;
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
public class UserRepositoryIntegrationTests {

    private final UserRepository underTest;

    @Autowired
    public UserRepositoryIntegrationTests(UserRepository userRepository) {
        this.underTest = userRepository;
    }

    @Test
    public void testThatUserCanBeCreatedAndRecalled() {
        UserEntity userEntityA = TestDataUtil.createTestUserEntityA();
        underTest.save(userEntityA);
        Optional<UserEntity> result = underTest.findById(userEntityA.getUserId());
        assertThat(result).isPresent();
        assertThat(result).contains(userEntityA);
    }

    @Test
    public void testThatMultipleUsersCanBeCreatedAndRecalled() {
        UserEntity userEntityA = TestDataUtil.createTestUserEntityA();
        underTest.save(userEntityA);
        UserEntity userEntityB = TestDataUtil.createTestUserEntityB();
        underTest.save(userEntityB);
        UserEntity userEntityC = TestDataUtil.createTestUserEntityC();
        underTest.save(userEntityC);

        Iterable<UserEntity> result = underTest.findAll();
        Iterable<UserEntity> expected = new ArrayList<>(Arrays.asList(userEntityA, userEntityB, userEntityC));
        assertThat(result).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    public void testThatUserCanBeUpdated() {
        UserEntity userEntityA = TestDataUtil.createTestUserEntityA();
        underTest.save(userEntityA);
        userEntityA.setNickname("UPDATED");
        underTest.save(userEntityA);
        Optional<UserEntity> result = underTest.findById(userEntityA.getUserId());
        assertThat(result).isPresent();
        assertThat(result).contains(userEntityA);
    }

    @Test
    public void testThatUserCanBeDeleted() {
        UserEntity userEntityA = TestDataUtil.createTestUserEntityA();
        underTest.save(userEntityA);
        underTest.deleteById(userEntityA.getUserId());
        Optional<UserEntity> result = underTest.findById(userEntityA.getUserId());
        assertThat(result).isEmpty();
    }
}
