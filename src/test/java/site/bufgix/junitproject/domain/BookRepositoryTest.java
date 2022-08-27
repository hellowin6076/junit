package site.bufgix.junitproject.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest // DB와 관련된 컴포넌트만 메모리에 로딩
public class BookRepositoryTest {

    @Autowired // DI
    private BookRepository bookRepository;

    // @BeforeAll //테스트 시작전에 한번만 실행
    @BeforeEach // 각 테스트 시작전에 한번씩 실행
    public void set_data() {
        String title = "junit5";
        String author = "메타코딩";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();
        bookRepository.save(book);
    }

    // 책 등록
    @Test
    public void insert_test() {
        // given(데이터 준비)
        String title = "sample";
        String author = "abcde1";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();

        // when테스트 실행()
        Book bookPS = bookRepository.save(book);

        // then(검증)
        assertEquals(title, bookPS.getTitle());
        assertEquals(author, bookPS.getAuthor());
    } // 트랜잭션 종료 (저장된 데이터 초기화) -> 아래에서 트랜잭션 종료 안되게 해야된다.

    // 책 목록보기
    @Test
    public void list_test() {
        // given
        String title = "junit5";
        String author = "메타코딩";
        // when
        List<Book> booksPS = bookRepository.findAll();

        // then
        assertEquals(title, booksPS.get(0).getTitle());
        assertEquals(author, booksPS.get(0).getAuthor());
    }

    // 책 한건보기
    @Sql("classpath:db/tableInit.sql")
    @Test
    public void search_test() {
        // given
        String title = "junit5";
        String author = "메타코딩";

        // when
        Book bookPS = bookRepository.findById(1L).get();

        // then
        assertEquals(title, bookPS.getTitle());
        assertEquals(author, bookPS.getAuthor());

    }

    // 책 삭제
    @Sql("classpath:db/tableInit.sql")
    @Test
    public void delete_test() {
        // given
        Long id = 1L;

        // when
        bookRepository.deleteById(id);
        // then
        assertFalse(bookRepository.findById(id).isPresent());
    }
    // 책 수정

}
