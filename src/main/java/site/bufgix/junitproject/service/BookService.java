package site.bufgix.junitproject.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.bufgix.junitproject.domain.Book;
import site.bufgix.junitproject.domain.BookRepository;
import site.bufgix.junitproject.web.dto.BookRespDto;
import site.bufgix.junitproject.web.dto.BookSaveReqDto;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;

    // 책 등록
    @Transactional(rollbackFor = RuntimeException.class)
    public BookRespDto insert_book(BookSaveReqDto dto) {
        Book bookPS = bookRepository.save(dto.toEntity());
        return new BookRespDto().toDto(bookPS);
    }

    // 책 목록보기
    public List<BookRespDto> list() {
        return bookRepository.findAll().stream()
                .map(new BookRespDto()::toDto)
                .collect(Collectors.toList());
    }
    // 책 한건보기

    // 책 삭제

    // 책 수정

}
