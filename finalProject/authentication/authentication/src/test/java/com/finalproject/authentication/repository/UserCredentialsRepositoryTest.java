package com.finalproject.authentication.repository;

import com.finalproject.authentication.model.UserCredentials;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class UserCredentialsRepositoryTest {

    @Autowired
    private UserCredentialsRepository userCredentialsRepository;
    private UserCredentials userCredentials1;
    private UserCredentials userCredentials2;

    @BeforeEach
    void init() {
        userCredentials1 = new UserCredentials();
        userCredentials1.setUsername("johndoe");
        userCredentials1.setPassword("abc");
        userCredentials1.setCorrelationId("1");
        userCredentials2 = new UserCredentials();
        userCredentials2.setUsername("johndoe");
        userCredentials2.setPassword("abc");
        userCredentials2.setCorrelationId("2");
    }

    @Test
    void userCredentialsRepository_getAll_returnsModeThanOneUserCredentials() {

        userCredentialsRepository.save(userCredentials1);
        userCredentialsRepository.save(userCredentials2);
        List<UserCredentials> userCredentials = userCredentialsRepository.findAll();

        assertThat(userCredentials).isNotNull();
        assertThat(userCredentials.size()).isEqualTo(2);

    }

    @Test
    void userCredentialsRepository_getById_returnsOneUserCredentials() {

        UserCredentials savedUserCredentials = userCredentialsRepository.save(userCredentials1);
        Optional<UserCredentials> foundUserCredentials = userCredentialsRepository.findById(savedUserCredentials.getId());

        assertThat(foundUserCredentials).isNotNull();
    }

    @Test
    void userCredentialsRepository_create_returnsOneUserCredentials() {

        UserCredentials savedUserCredentials = userCredentialsRepository.save(userCredentials1);
        Optional<UserCredentials> fetchedUserCredentials = userCredentialsRepository.findById(savedUserCredentials.getId());

        assertThat(fetchedUserCredentials).isPresent();
        assertThat(userCredentialsRepository.findById(fetchedUserCredentials.get().getId())).isNotNull();
    }

    @Test
    void userCredentialsRepository_update_returnsOneUserCredentials() {

        UserCredentials savedUserCredentials = userCredentialsRepository.save(userCredentials1);
        Optional<UserCredentials> foundUserCredentials = userCredentialsRepository.findById(savedUserCredentials.getId());
        foundUserCredentials.get().setUsername("newUsername");
        UserCredentials updatedRole = userCredentialsRepository.save(foundUserCredentials.get());

        assertThat(updatedRole.getUsername()).isEqualTo("newUsername");
    }

    @Test
    void userCredentialsRepository_delete_returnsNothing() {

        UserCredentials savedUserCredentials = userCredentialsRepository.save(userCredentials1);
        userCredentialsRepository.deleteById(savedUserCredentials.getId());
        Optional<UserCredentials> foundUserCredentials = userCredentialsRepository.findById(savedUserCredentials.getId());

        assertThat(foundUserCredentials).isEmpty();
    }

}
