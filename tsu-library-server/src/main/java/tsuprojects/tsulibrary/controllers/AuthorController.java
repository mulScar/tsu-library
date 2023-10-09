package tsuprojects.tsulibrary.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import tsuprojects.tsulibrary.data.dto.AuthorDto;
import tsuprojects.tsulibrary.data.requests.RequestAuthor;
import tsuprojects.tsulibrary.domain.AuthorEntity;
import tsuprojects.tsulibrary.service.AuthorService;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/authors")
@Tag(name = "Author Controller", description = "Controller for managing authors")
public class AuthorController {

    @NonNull
    private final AuthorService authorService;

    @NonNull
    private final ModelMapper modelMapper;

    public AuthorController(
            @NonNull AuthorService authorService,
            @NonNull ModelMapper modelMapper) {
        this.authorService = authorService;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "Get list of all authors")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<AuthorDto> getAllAuthors() {
        List<AuthorEntity> authors = authorService.findAll();
        return authors.stream()
                .map(authorEntity -> modelMapper.map(authorEntity, AuthorDto.class))
                .collect(Collectors.toSet());
    }

    @Operation(summary = "Get author by ID")
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthorDto getAuthorById(@PathVariable UUID id) {
        AuthorEntity author = authorService.findById(id);
        return modelMapper.map(author, AuthorDto.class);
    }

    @Operation(summary = "Create new author")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthorDto createAuthor(@RequestBody RequestAuthor requestAuthor) {
        AuthorEntity author = authorService.create(requestAuthor);
        return modelMapper.map(author, AuthorDto.class);
    }

    @Operation(summary = "Update author by ID")
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthorDto updateAuthor(@PathVariable UUID id, @RequestBody RequestAuthor requestAuthor) {
        AuthorEntity author = authorService.update(id, requestAuthor);
        return modelMapper.map(author, AuthorDto.class);
    }

    @Operation(summary = "Delete author by ID")
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteAuthor(@PathVariable UUID id) {
        authorService.delete(id);
        return ResponseEntity.ok("Author id=" + id + " successful deleted");
    }
}
