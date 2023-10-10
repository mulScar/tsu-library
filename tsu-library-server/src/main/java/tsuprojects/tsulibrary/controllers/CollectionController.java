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
import tsuprojects.tsulibrary.data.dto.CollectionDto;
import tsuprojects.tsulibrary.data.requests.RequestCollection;
import tsuprojects.tsulibrary.domain.BookEntity;
import tsuprojects.tsulibrary.domain.CollectionEntity;
import tsuprojects.tsulibrary.service.BookService;
import tsuprojects.tsulibrary.service.CollectionService;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/collections")
@Tag(name = "Collection Controller", description = "Controller for managing collections")
public class CollectionController {

    @NonNull
    private final CollectionService collectionService;

    @NonNull
    private final BookService bookService;

    @NonNull
    private final ModelMapper modelMapper;

    public CollectionController(
            @NonNull CollectionService collectionService,
            @NonNull BookService bookService, @NonNull ModelMapper modelMapper) {
        this.collectionService = collectionService;
        this.bookService = bookService;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "Get set of all collections")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<CollectionDto> getAllCollections() {
        List<CollectionEntity> collections = collectionService.findAll();
        return collections.stream()
                .map(collectionEntity -> modelMapper.map(collectionEntity, CollectionDto.class))
                .collect(Collectors.toSet());
    }

    @Operation(summary = "Get collection by ID")
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionDto getCollectionById(@PathVariable UUID id) {
        CollectionEntity collection = collectionService.findById(id);
        return modelMapper.map(collection, CollectionDto.class);
    }

    @Operation(summary = "Get list of all books by collection ID")
    @GetMapping(path = "/{id}/books", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<BookDto> getAllBooksByCollectionId(
            @RequestParam("page") Integer page,
            @RequestParam("limit") Integer limit,
            @PathVariable UUID id) {
        Page<BookEntity> books = bookService.findAllByCollectionId(id, PageRequest.of(page, limit));
        return books.map(book -> modelMapper.map(book, BookDto.class));
    }

    @Operation(summary = "Create new collection")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionDto createCollection(@RequestBody RequestCollection requestCollection) {
        CollectionEntity collection = collectionService.create(requestCollection);
        return modelMapper.map(collection, CollectionDto.class);
    }

    @Operation(summary = "Update collection by ID")
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionDto updateCollection(@PathVariable UUID id, @RequestBody RequestCollection requestCollection) {
        CollectionEntity collection = collectionService.update(id, requestCollection);
        return modelMapper.map(collection, CollectionDto.class);
    }

    @Operation(summary = "Delete collection by ID")
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteCollection(@PathVariable UUID id) {
        collectionService.delete(id);
        return ResponseEntity.ok("Collection id=" + id + " successful deleted");
    }
}
