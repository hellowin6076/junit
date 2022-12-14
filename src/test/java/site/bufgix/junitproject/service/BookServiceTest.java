package site.bufgix.junitproject.service;

import static org.assertj.core.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import site.bufgix.junitproject.domain.Book;
import site.bufgix.junitproject.domain.BookRepository;
import site.bufgix.junitproject.util.MailSender;

import site.bufgix.junitproject.web.dto.request.BookSaveReqDto;
import site.bufgix.junitproject.web.dto.response.BookListRespDto;
import site.bufgix.junitproject.web.dto.response.BookRespDto;

@ActiveProfiles("dev")
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
        assertThat(bookRespDto.getTitle()).isEqualTo(dto.getTitle());
        assertThat(bookRespDto.getAuthor()).isEqualTo(dto.getAuthor());
    }

    @Test
    public void list_test() {
        // given(파라메터로 들어올 데이터)

        // stub(가설)
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "first title", "first author"));
        books.add(new Book(2L, "second title", "second author"));
        when(bookRepository.findAll()).thenReturn(books);

        // when(실행)
        BookListRespDto bookListRespDto = bookService.list();

        // print
        // bookRespDtoList.stream().forEach((b) -> {
        // System.out.println(b.getId());
        // System.out.println(b.getTitle());
        // System.out.println("=====================");
        // });

        // then(검증)
        assertThat(bookListRespDto.getItems().get(0).getTitle()).isEqualTo("first title");
        assertThat(bookListRespDto.getItems().get(0).getAuthor()).isEqualTo("first author");
        assertThat(bookListRespDto.getItems().get(1).getTitle()).isEqualTo("second title");
        assertThat(bookListRespDto.getItems().get(1).getAuthor()).isEqualTo("second author");
    }

    @Test
    public void search_book_test() {
        // given
        Long id = 1L;

        // stub
        Book book = new Book(1L, "test book", "test author");
        Optional<Book> bookOP = Optional.of(book);
        when(bookRepository.findById(1L)).thenReturn(bookOP);

        // when
        BookRespDto bookRespDto = bookService.search(id);

        // then
        assertThat(bookRespDto.getTitle()).isEqualTo(book.getTitle());
        assertThat(bookRespDto.getAuthor()).isEqualTo(book.getAuthor());

    }

    @Test
    public void update_book_test() {
        // given
        Long id = 1L;
        BookSaveReqDto dto = new BookSaveReqDto();
        dto.setTitle("update title");
        dto.setAuthor("update author");

        // stub
        Book book = new Book(1L, "test book", "test author");
        Optional<Book> bookOP = Optional.of(book);
        when(bookRepository.findById(1L)).thenReturn(bookOP);

        // when
        BookRespDto bookRespDto = bookService.update_book(id, dto);

        // then
        assertThat(bookRespDto.getTitle()).isEqualTo(dto.getTitle());
        assertThat(bookRespDto.getAuthor()).isEqualTo(dto.getAuthor());
    }
}
