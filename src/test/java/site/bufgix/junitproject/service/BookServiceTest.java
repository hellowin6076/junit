package site.bufgix.junitproject.service;

import org.assertj.core.api.WithAssertions;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import site.bufgix.junitproject.domain.BookRepository;
import site.bufgix.junitproject.util.MailSender;
import site.bufgix.junitproject.util.MailSenderStub;
import site.bufgix.junitproject.web.dto.BookRespDto;
import site.bufgix.junitproject.web.dto.BookSaveReqDto;

@ExtendWith(MockitoExtension.class) // 가짜 메모리 환경
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private MailSender mailSender;

    @Test
    public void insert_book_test() {
        // given
        BookSaveReqDto dto = new BookSaveReqDto();
        dto.setTitle("testTitle");
        dto.setAuthor("testAuthor");

        // stub(행동정의)
        when(bookRepository.save(any())).thenReturn(dto.toEntity());
        when(mailSender.send()).thenReturn(true);

        // when
        BookRespDto bookRespDto = bookService.insert_book(dto);

        // then
        // assertEquals(dto.getTitle(), bookRespDto.getTitle());
        // assertEquals(dto.getAuthor(), bookRespDto.getAuthor());
        assertThat(dto.getTitle()).isEqualTo(bookRespDto.getTitle());
        assertThat(dto.getAuthor()).isEqualTo(bookRespDto.getAuthor());

    }
}
