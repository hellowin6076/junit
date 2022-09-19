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
import site.bufgix.junitproject.web.dto.BookRespDto;
import site.bufgix.junitproject.web.dto.BookSaveReqDto;

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
        return new BookRespDto().toDto(bookPS);
    }

    // 책 목록보기
    public List<BookRespDto> list() {
        return bookRepository.findAll().stream()
                .map(new BookRespDto()::toDto)
                .collect(Collectors.toList());
    }

    // 책 한건보기
    public BookRespDto search(Long id) {
        Optional<Book> bookOP = bookRepository.findById(id);
        if (bookOP.isPresent()) { // 찾았다면
            return new BookRespDto().toDto(bookOP.get());
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
    public void update_book(Long id, BookRespDto dto) { // id로 찾고 title, author를 수정
        Optional<Book> bookOP = bookRepository.findById(id);
        if (bookOP.isPresent()) {
            Book bookPS = bookOP.get();
            bookPS.update(dto.getTitle(), dto.getAuthor());
        } else {
            throw new RuntimeException("해당 아이디를 찾을 수 없습니다.");
        }
    }// 메서드 종료시에 더티체킹(flush)으로 업데이트
}
