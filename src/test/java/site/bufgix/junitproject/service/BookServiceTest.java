package site.bufgix.junitproject.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import site.bufgix.junitproject.domain.BookRepository;
import site.bufgix.junitproject.util.MailSenderStub;
import site.bufgix.junitproject.web.dto.BookRespDto;
import site.bufgix.junitproject.web.dto.BookSaveReqDto;

@DataJpaTest
public class BookServiceTest {

    @Autowired
    private BookRepository bookRepository;

    // 문제점 > 서비스만 테스트 하고 싶은데 레포지토리 레이어가 함께 테스트 된다는 것
    @Test
    public void insert_book_test() {
        // given
        BookSaveReqDto dto = new BookSaveReqDto();
        dto.setTitle("testTitle");
        dto.setAuthor("testAuthor");

        // stub
        MailSenderStub mailSenderStub = new MailSenderStub();

        // when
        BookService bookService = new BookService(bookRepository, mailSenderStub);
        BookRespDto bookRespDto = bookService.insert_book(dto);

        // then
        assertEquals(dto.getTitle(), bookRespDto.getTitle());
        assertEquals(dto.getAuthor(), bookRespDto.getAuthor());
    }
}
