package tsuprojects.tsulibrary.service;

import tsuprojects.tsulibrary.data.requests.RequestAuthor;
import tsuprojects.tsulibrary.domain.AuthorEntity;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface AuthorService {

    List<AuthorEntity> findAll();

    List<AuthorEntity> findAllByIds(Set<UUID> ids);

    AuthorEntity findById(UUID id);

    AuthorEntity create(RequestAuthor requestAuthor);

    AuthorEntity update(UUID id, RequestAuthor requestAuthor);

    void delete(UUID id);
}
