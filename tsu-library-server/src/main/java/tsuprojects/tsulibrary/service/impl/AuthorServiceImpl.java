package tsuprojects.tsulibrary.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tsuprojects.tsulibrary.data.requests.RequestAuthor;
import tsuprojects.tsulibrary.domain.AuthorEntity;
import tsuprojects.tsulibrary.exception.BadRequestException;
import tsuprojects.tsulibrary.exception.NotFoundException;
import tsuprojects.tsulibrary.repository.AuthorRepository;
import tsuprojects.tsulibrary.service.AuthorService;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
public class AuthorServiceImpl implements AuthorService {

    @NonNull
    private final AuthorRepository authorRepository;

    @NonNull
    private final ModelMapper modelMapper;

    public AuthorServiceImpl(
            @NonNull AuthorRepository authorRepository,
            @NonNull ModelMapper modelMapper) {
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<AuthorEntity> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public List<AuthorEntity> findAllByIds(Set<UUID> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new BadRequestException("Ids can't be null or empty");
        }
        return authorRepository.findAllById(ids);
    }

    @Override
    public AuthorEntity findById(UUID id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Author by id= " + id + " is not found");
                    return new NotFoundException("Author is not found");
                });
    }

    @Override
    public AuthorEntity create(RequestAuthor requestAuthor) {
        boolean isAuthorExist = authorRepository.existsByFullName(
                requestAuthor.getFirstName(),
                requestAuthor.getLastName());
        if (isAuthorExist) {
            throw new BadRequestException("Author with name \"" + requestAuthor.getFirstName()
                    + " " + requestAuthor.getLastName() + "\" already exist");
        }

        AuthorEntity author = modelMapper.map(requestAuthor, AuthorEntity.class);
        authorRepository.save(author);
        log.info("Author id=" + author.getId() + " successfully created");
        return author;
    }

    @Override
    @Transactional
    public AuthorEntity update(UUID id, RequestAuthor requestAuthor) {
        AuthorEntity author = findById(id);
        modelMapper.map(requestAuthor, author);
        authorRepository.save(author);
        log.info("Author id=" + id + " successfully updated");
        return author;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        AuthorEntity author = findById(id);
        authorRepository.delete(author);
        log.info("Author id=" + id + " successfully deleted");
    }
}
