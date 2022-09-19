package site.bufgix.junitproject.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.bufgix.junitproject.service.BookService;
import site.bufgix.junitproject.web.dto.request.BookSaveReqDto;
import site.bufgix.junitproject.web.dto.response.BookRespDto;
import site.bufgix.junitproject.web.dto.response.CommonRespDto;

@RequiredArgsConstructor
@RestController
public class BookApiController { // 컴포지션 = has관계

    private final BookService bookService;

    // 책 등록
    // key=value&key=value 가 기본 파싱
    // json으로 보낼예정
    @PostMapping("/api/v1/book")
    public ResponseEntity<?> saveBook(@RequestBody BookSaveReqDto bookSaveReqDto) {
        BookRespDto bookRespDto = bookService.insert_book(bookSaveReqDto);
        CommonRespDto<?> cmRespDto = CommonRespDto.builder().code(1).msg("책 저장 성공").body(bookRespDto).build();
        return new ResponseEntity<>(cmRespDto, HttpStatus.CREATED); // 201 = insert
    };

    // 책 목록보기
    public ResponseEntity<?> getBookList() {
        return null;
    };

    // 책 한건보기
    public ResponseEntity<?> searchBook() {
        return null;
    };

    // 책 삭제하기
    public ResponseEntity<?> deleteBook() {
        return null;
    };

    // 책 수정하기
    public ResponseEntity<?> updateBook() {
        return null;
    };

}
