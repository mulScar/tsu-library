package tsuprojects.tsulibrary.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tsuprojects.tsulibrary.data.requests.RequestBook;
import tsuprojects.tsulibrary.domain.BookEntity;

import java.util.UUID;

public interface BookService {

    Page<BookEntity> findAll(Pageable pageable);

    BookEntity findById(UUID id);

    BookEntity create(RequestBook requestBook);

    BookEntity update(UUID id, RequestBook requestBook);

    void delete(UUID id);
}
