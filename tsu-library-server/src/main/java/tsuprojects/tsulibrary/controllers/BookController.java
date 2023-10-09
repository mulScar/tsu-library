package tsuprojects.tsulibrary.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import tsuprojects.tsulibrary.data.dto.BookDto;
import tsuprojects.tsulibrary.data.requests.RequestBook;
import tsuprojects.tsulibrary.domain.BookEntity;
import tsuprojects.tsulibrary.service.BookService;

import java.util.UUID;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Book Controller", description = "Controller for managing books")
public class BookController {

    @NonNull
    private final BookService bookService;

    @NonNull
    private final ModelMapper modelMapper;

    public BookController(
            @NonNull BookService bookService,
            @NonNull ModelMapper modelMapper) {
        this.bookService = bookService;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "Get list of all books")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<BookDto> getAllBooks(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit) {
        Page<BookEntity> books = bookService.findAll(PageRequest.of(page, limit));
        return books.map(bookEntity -> modelMapper.map(bookEntity, BookDto.class));
    }

    @Operation(summary = "Get book by ID")
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BookDto getBookById(@PathVariable UUID id) {
        BookEntity book = bookService.findById(id);
        return modelMapper.map(book, BookDto.class);
    }

    @Operation(summary = "Create new book")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public BookDto createBook(@RequestBody RequestBook requestBook) {
        BookEntity book = bookService.create(requestBook);
        return modelMapper.map(book, BookDto.class);
    }

    @Operation(summary = "Update book by ID")
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BookDto updateBook(@PathVariable UUID id, @RequestBody RequestBook requestBook) {
        BookEntity book = bookService.update(id, requestBook);
        return modelMapper.map(book, BookDto.class);
    }

    @Operation(summary = "Delete book by ID")
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteBook(@PathVariable UUID id) {
        bookService.delete(id);
        return ResponseEntity.ok("Book id=" + id + " successful deleted");
    }
}
