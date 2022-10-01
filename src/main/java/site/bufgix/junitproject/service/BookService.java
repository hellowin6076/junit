package site.bufgix.junitproject.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.bufgix.junitproject.domain.Book;
import site.bufgix.junitproject.domain.BookRepository;
import site.bufgix.junitproject.util.MailSender;
import site.bufgix.junitproject.web.dto.request.BookSaveReqDto;
import site.bufgix.junitproject.web.dto.response.BookListRespDto;
import site.bufgix.junitproject.web.dto.response.BookRespDto;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final MailSender mailSender;

    // 책 등록
    @Transactional(rollbackFor = RuntimeException.class)
    public BookRespDto insert_book(BookSaveReqDto dto) {
        Book bookPS = bookRepository.save(dto.toEntity());
        if (bookPS != null) {
            // 메일보내기 메서드 호출 (return ture of false)
            if (!mailSender.send()) {
                throw new RuntimeException("메일이 전송되지 않았습니다.");
            }
        }
        return bookPS.toDto();
    }

    // 책 목록보기
    public BookListRespDto list() {
        List<BookRespDto> dtos = bookRepository.findAll().stream()
                // .map((bookPS) -> new BookRespDto().toDto(bookPS))
                .map(Book::toDto)
                .collect(Collectors.toList());

        BookListRespDto bookListRespDto = BookListRespDto.builder().bookList(dtos).build();
        return bookListRespDto;
    }

    // 책 한건보기
    public BookRespDto search(Long id) {
        Optional<Book> bookOP = bookRepository.findById(id);
        if (bookOP.isPresent()) { // 찾았다면
            Book bookPS = bookOP.get();
            return bookPS.toDto();
        } else {
            throw new RuntimeException("해당 아이디를 찾을 수 없습니다.");
        }
    }

    // 책 삭제
    @Transactional(rollbackFor = RuntimeException.class)
    public void delete_book(Long id) {
        bookRepository.deleteById(id);
    }

    // 책 수정
    @Transactional(rollbackFor = RuntimeException.class)
    public BookRespDto update_book(Long id, BookSaveReqDto dto) { // id로 찾고 title, author를 수정
        Optional<Book> bookOP = bookRepository.findById(id);
        if (bookOP.isPresent()) {
            Book bookPS = bookOP.get();
            bookPS.update(dto.getTitle(), dto.getAuthor());
            return bookPS.toDto();
        } else {
            throw new RuntimeException("해당 아이디를 찾을 수 없습니다.");
        }
    }// 메서드 종료시에 더티체킹(flush)으로 업데이트
}
