package tsuprojects.tsulibrary.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tsuprojects.tsulibrary.data.requests.RequestBook;
import tsuprojects.tsulibrary.domain.AuthorEntity;
import tsuprojects.tsulibrary.domain.BookEntity;
import tsuprojects.tsulibrary.domain.CollectionEntity;
import tsuprojects.tsulibrary.exception.BadRequestException;
import tsuprojects.tsulibrary.exception.NotFoundException;
import tsuprojects.tsulibrary.repository.BookRepository;
import tsuprojects.tsulibrary.service.AuthorService;
import tsuprojects.tsulibrary.service.BookService;
import tsuprojects.tsulibrary.service.CollectionService;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

    @NonNull
    private final AuthorService authorService;

    @NonNull
    private final CollectionService collectionService;

    @NonNull
    private final BookRepository bookRepository;

    @NonNull
    private final ModelMapper modelMapper;

    public BookServiceImpl(
            @NonNull AuthorService authorService,
            @NonNull CollectionService collectionService,
            @NonNull BookRepository bookRepository,
            @NonNull ModelMapper modelMapper) {
        this.authorService = authorService;
        this.collectionService = collectionService;
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<BookEntity> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    public Page<BookEntity> findAllByCollectionId(UUID collectionId, Pageable pageable) {
        return bookRepository.findAllByCollectionId(collectionId, pageable);
    }

    @Override
    public BookEntity findById(UUID id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Book by id= " + id + " is not found in DB");
                    return new NotFoundException("Book is not found");
                });
    }

    @Override
    public BookEntity create(RequestBook requestBook) {
        boolean isBookExist = bookRepository.existsByInventoryNumberOrTitle(
                requestBook.getInventoryNumber(),
                requestBook.getTitle());
        if (isBookExist) {
            throw new BadRequestException("Book inventoryNumber=" + requestBook.getInventoryNumber()
                    + " title=" + requestBook.getTitle() + "\" already exist");
        }

        BookEntity book = modelMapper.map(requestBook, BookEntity.class);
        List<AuthorEntity> authors = authorService.findAllByIds(requestBook.getAuthorsId());
        book.addAuthors(authors);

        Set<UUID> collectionsId = requestBook.getCollectionsId();
        if (collectionsId == null || collectionsId.isEmpty()) {
            CollectionEntity defaultCollection = collectionService.getDefaultCollection();
            book.addCollection(defaultCollection);
        } else {
            List<CollectionEntity> collections = collectionService.findAllByIds(collectionsId);
            book.addCollections(collections);
        }

        bookRepository.save(book);
        log.info("Book id=" + book.getId() + " successfully created");
        return book;
    }

    @Override
    @Transactional
    public BookEntity update(UUID id, RequestBook requestBook) {
        BookEntity book = findById(id);
        modelMapper.map(requestBook, book);
        bookRepository.save(book);
        log.info("Book id=" + id + " successfully updated");
        return book;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        BookEntity book = findById(id);
        bookRepository.delete(book);
        log.info("Book id=" + id + " successfully deleted");
    }
}
