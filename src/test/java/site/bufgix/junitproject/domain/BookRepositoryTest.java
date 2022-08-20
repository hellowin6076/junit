package site.bufgix.junitproject.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired // DI
    private BookRepository bookRepository;

    // 책 등록
    @Test
    public void insert_test() {
        // given(데이터 준비)
        String title = "junit5";
        String author = "메타코딩";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();

        // when테스트 실행()
        Book bookPS = bookRepository.save(book);

        // then(검증)
        assertEquals(title, bookPS.getTitle());
        assertEquals(author, bookPS.getAuthor());
    }
    // 책 목록보기

    // 책 한건보기

    // 책 수정

    // 책 삭제

}
