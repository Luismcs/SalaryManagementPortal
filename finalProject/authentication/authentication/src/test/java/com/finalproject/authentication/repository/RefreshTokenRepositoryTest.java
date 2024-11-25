package com.finalproject.authentication.repository;

import com.finalproject.authentication.model.RefreshToken;
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
public class RefreshTokenRepositoryTest {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    private RefreshToken refreshToken1;
    private RefreshToken refreshToken2;

    @BeforeEach
    void init() {
        refreshToken1 = new RefreshToken();
        refreshToken1.setRefreshToken("abcdefghijklmnopqrstuvwxyz");
        refreshToken2 = new RefreshToken();
        refreshToken2.setRefreshToken("abcdefghijklmnopqrstuvwxyz");
    }

    @Test
    void refreshTokenRepository_getAll_returnsModeThanOneRefreshToken() {

        refreshTokenRepository.save(refreshToken1);
        refreshTokenRepository.save(refreshToken2);
        List<RefreshToken> refreshTokens = refreshTokenRepository.findAll();

        assertThat(refreshTokens).isNotNull();
        assertThat(refreshTokens.size()).isEqualTo(2);

    }

    @Test
    void refreshTokenRepository_GetById_ReturnsOneRefreshToken() {

        RefreshToken savedRefreshToken = refreshTokenRepository.save(refreshToken1);
        Optional<RefreshToken> foundRefreshToken = refreshTokenRepository.findById(savedRefreshToken.getId());

        assertThat(foundRefreshToken).isNotNull();
    }

    @Test
    void refreshTokenRepository_create_returnsOneRefreshToken() {

        RefreshToken savedRefreshToken = refreshTokenRepository.save(refreshToken1);
        Optional<RefreshToken> fetchedRefreshToken = refreshTokenRepository.findById(savedRefreshToken.getId());

        assertThat(fetchedRefreshToken).isPresent();
        assertThat(refreshTokenRepository.findById(fetchedRefreshToken.get().getId())).isNotNull();
    }

    @Test
    void refreshTokenRepository_update_returnsOneRefreshToken() {

        RefreshToken savedRefreshToken = refreshTokenRepository.save(refreshToken1);
        Optional<RefreshToken> foundRefreshToken = refreshTokenRepository.findById(savedRefreshToken.getId());
        foundRefreshToken.get().setRefreshToken("123456789987654321");
        RefreshToken updatedRefreshToken = refreshTokenRepository.save(foundRefreshToken.get());

        assertThat(updatedRefreshToken.getRefreshToken()).isEqualTo("123456789987654321");
    }

    @Test
    void refreshTokenRepository_delete_returnsNothing() {

        RefreshToken savedRefreshToken = refreshTokenRepository.save(refreshToken1);
        refreshTokenRepository.deleteById(savedRefreshToken.getId());
        Optional<RefreshToken> foundRefreshToken = refreshTokenRepository.findById(savedRefreshToken.getId());

        assertThat(foundRefreshToken).isEmpty();
    }

}
