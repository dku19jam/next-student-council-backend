package java.com.dku.council.domain.post.repository.impl;

import com.dku.council.domain.post.repository.impl.ViewCountRedisRepository;
import com.dku.council.util.base.AbstractContainerRedisTest;
import com.dku.council.util.test.FullIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@FullIntegrationTest
class ViewCountRedisRepositoryTest extends AbstractContainerRedisTest {

    @Autowired
    private ViewCountRedisRepository repository;


    @Test
    @DisplayName("조회수 카운팅 캐시에 잘 입력되는가?")
    void addPostLike() {
        // given
        String userIdentifier = "User";
        Instant now = Instant.now();
        Duration expiresAfter = Duration.of(100, ChronoUnit.MINUTES);

        // when
        repository.put(10L, userIdentifier, expiresAfter, now);

        // then
        boolean result = repository.isAlreadyContains(10L, userIdentifier, now);
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("유저가 이미 조회한 적 있는지? - 없는 경우")
    void isAlreadyContainsNoCached() {
        // given
        String userIdentifier = "User";
        Instant now = Instant.now();

        // when
        boolean result = repository.isAlreadyContains(10L, userIdentifier, now);

        // then
        assertThat(result).isEqualTo(false);
    }

    @Test
    @DisplayName("유저가 이미 조회한 적 있는지? - 있는 경우")
    void isAlreadyContainsCached() {
        // given
        String userIdentifier = "User";
        Instant now = Instant.now();

        // when
        repository.put(10L, userIdentifier, Duration.of(10, ChronoUnit.MINUTES), now);
        boolean result = repository.isAlreadyContains(10L, userIdentifier, now);

        // then
        assertThat(result).isEqualTo(true);
    }

    @Test
    @DisplayName("유저가 이미 조회한 적 있는지? - 있지만 만료된 경우")
    void isAlreadyContainsExpired() {
        // given
        String userIdentifier = "User";
        Instant now = Instant.now();
        Duration expiresAfter = Duration.of(100, ChronoUnit.MINUTES);

        // when
        repository.put(10L, userIdentifier, expiresAfter, now);
        boolean result = repository.isAlreadyContains(10L, userIdentifier, now.plus(expiresAfter).plusSeconds(60));

        // then
        assertThat(result).isEqualTo(false);
    }
}