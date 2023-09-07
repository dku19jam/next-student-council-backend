package java.com.dku.council.domain.tag.repository;

import com.dku.council.domain.tag.model.entity.Tag;
import com.dku.council.domain.tag.repository.TagRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;


@DataJpaTest
public class TagRepositoryTest {

    @Autowired
    private TagRepository repository;

    @Test
    @DisplayName("중복 저장이 안되는가?")
    void checkDuplicate(){
        //given
        Tag test = new Tag("test");
        Tag duplicateTag = new Tag("test");

        //when
        repository.saveAndFlush(test);
        //then
        Assertions.assertThrows(DataIntegrityViolationException.class,
                () -> repository.saveAndFlush(duplicateTag));
    }

}
