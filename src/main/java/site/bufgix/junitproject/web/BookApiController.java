package site.bufgix.junitproject.web;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.bufgix.junitproject.service.BookService;
import site.bufgix.junitproject.web.dto.request.BookSaveReqDto;
import site.bufgix.junitproject.web.dto.response.BookListRespDto;
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
    public ResponseEntity<?> saveBook(@RequestBody @Valid BookSaveReqDto bookSaveReqDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fe : bindingResult.getFieldErrors()) {
                errorMap.put(fe.getField(), fe.getDefaultMessage());
            }
            throw new RuntimeException(errorMap.toString());
        }

        BookRespDto bookRespDto = bookService.insert_book(bookSaveReqDto);
        return new ResponseEntity<>(CommonRespDto.builder().code(1).msg("책 저장 성공").body(bookRespDto).build(),
                HttpStatus.CREATED); // 201 = insert
    };

    // 책 목록보기
    @GetMapping("/api/v1/book")
    public ResponseEntity<?> getBookList() {
        BookListRespDto bookListRespDto = bookService.list();
        return new ResponseEntity<>(CommonRespDto.builder().code(1).msg("책 목록보기 성공").body(bookListRespDto).build(),
                HttpStatus.OK); // 200 = OK
    };

    // 책 한건보기
    @GetMapping("/api/v1/book/{id}")
    public ResponseEntity<?> searchBook(@PathVariable Long id) {
        BookRespDto bookRespDto = bookService.search(id);
        return new ResponseEntity<>(CommonRespDto.builder().code(1).msg("책 찾기 성공").body(bookRespDto).build(),
                HttpStatus.OK); // 200 = OK
    };

    // 책 삭제하기
    @DeleteMapping("/api/v1/book/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        bookService.delete_book(id);
        return new ResponseEntity<>(CommonRespDto.builder().code(1).msg("책 삭제 성공").body(null).build(),
                HttpStatus.OK);
    };

    // 책 수정하기
    @PutMapping("/api/v1/book/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody @Valid BookSaveReqDto bookSaveReqDto,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fe : bindingResult.getFieldErrors()) {
                errorMap.put(fe.getField(), fe.getDefaultMessage());
            }
            throw new RuntimeException(errorMap.toString());
        }
        BookRespDto bookRespDto = bookService.update_book(id, bookSaveReqDto);
        return new ResponseEntity<>(CommonRespDto.builder().code(1).msg("책 수정 성공").body(bookRespDto).build(),
                HttpStatus.OK);
    };

}
