package site.bufgix.junitproject.web;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import site.bufgix.junitproject.domain.Book;
import site.bufgix.junitproject.domain.BookRepository;
import site.bufgix.junitproject.web.dto.request.BookSaveReqDto;

// 통합 테스트(C, S, R)
// 컨트롤러만 테스트 하는 것이 아님
@ActiveProfiles("dev")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BookApiControllerTest {

    @Autowired
    private TestRestTemplate rt;

    @Autowired // DI
    private BookRepository bookRepository;

    private static ObjectMapper om;
    private static HttpHeaders headers;

    @BeforeAll
    public static void init() {
        om = new ObjectMapper();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @BeforeEach // 각 테스트 시작전에 한번씩 실행
    public void set_data() {
        String title = "junit";
        String author = "메타코딩";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();
        bookRepository.save(book);
    }

    @Sql("classpath:db/tableInit.sql")
    @Test
    public void updateBook_test() throws Exception {
        // given
        Integer id = 1;
        BookSaveReqDto bookSaveReqDto = new BookSaveReqDto();
        bookSaveReqDto.setTitle("update title");
        bookSaveReqDto.setAuthor("update author");

        String body = om.writeValueAsString(bookSaveReqDto);

        // when
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = rt.exchange("/api/v1/book/" + id, HttpMethod.PUT, request, String.class);

        // System.out.println("updateBook_test() : " + response.getStatusCodeValue());
        // System.out.println("updateBook_test() : " + response.getBody());

        // then
        DocumentContext dc = JsonPath.parse(response.getBody());
        String title = dc.read("$.body.title");

        assertThat(title).isEqualTo("update title");
    }

    @Sql("classpath:db/tableInit.sql")
    @Test
    public void deleteBook_test() {
        // given
        Integer id = 1;
        // when
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = rt.exchange("/api/v1/book/" + id, HttpMethod.DELETE, request, String.class);

        // then
        DocumentContext dc = JsonPath.parse(response.getBody());
        Integer code = dc.read("$.code");

        assertThat(code).isEqualTo(1);
    }

    @Sql("classpath:db/tableInit.sql")
    @Test
    public void getBookSearch_test() {
        // given
        Integer id = 1;
        // when
        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = rt.exchange("/api/v1/book/" + id, HttpMethod.GET, request, String.class);

        // then
        DocumentContext dc = JsonPath.parse(response.getBody());
        Integer code = dc.read("$.code");
        String title = dc.read("$.body.title");

        assertThat(code).isEqualTo(1);
        assertThat(title).isEqualTo("junit");
    }

    @Sql("classpath:db/tableInit.sql")
    @Test
    public void getBookList_test() {
        // given

        // when
        HttpEntity<String> request = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = rt.exchange("/api/v1/book", HttpMethod.GET, request, String.class);
        // then
        DocumentContext dc = JsonPath.parse(response.getBody());
        Integer code = dc.read("$.code");
        String title = dc.read("$.body.items[0].title");

        assertThat(code).isEqualTo(1);
        assertThat(title).isEqualTo("junit");
    }

    @Test
    public void saveBook_test() throws Exception {
        // given
        BookSaveReqDto bookSaveReqDto = new BookSaveReqDto();
        bookSaveReqDto.setTitle("test title");
        bookSaveReqDto.setAuthor("test author");

        String body = om.writeValueAsString(bookSaveReqDto);

        // when
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = rt.exchange("/api/v1/book", HttpMethod.POST, request, String.class);

        // then
        DocumentContext dc = JsonPath.parse(response.getBody());
        String title = dc.read("$.body.title");
        String author = dc.read("$.body.author");

        assertThat(title).isEqualTo("test title");
        assertThat(author).isEqualTo("test author");

    }
}
