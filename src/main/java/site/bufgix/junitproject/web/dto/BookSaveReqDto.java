package site.bufgix.junitproject.web.dto;

import lombok.Setter;
import site.bufgix.junitproject.domain.Book;

@Setter // Contorller에서 Setter가 호출되면서 Dto에 값이 채워짐.
public class BookSaveReqDto {
    private String title;
    private String author;

    public Book toEntity() {
        return Book.builder()
                .title(title)
                .author(author)
                .build();
    }
}
