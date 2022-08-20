package site.bufgix.junitproject.domain;

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
        System.out.println("insert_test excute");
    }
    // 책 목록보기

    // 책 한건보기

    // 책 수정

    // 책 삭제

}
