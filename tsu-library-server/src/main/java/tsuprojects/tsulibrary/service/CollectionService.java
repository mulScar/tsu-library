package tsuprojects.tsulibrary.service;

import tsuprojects.tsulibrary.data.requests.RequestCollection;
import tsuprojects.tsulibrary.domain.CollectionEntity;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface CollectionService {

    String DEFAULT_COLLECTION = "DEFAULT_COLLECTION";

    List<CollectionEntity> findAll();

    List<CollectionEntity> findAllByIds(Set<UUID> ids);

    CollectionEntity findById(UUID id);

    CollectionEntity create(RequestCollection requestCollection);

    CollectionEntity update(UUID id, RequestCollection requestCollection);

    void delete(UUID id);

    CollectionEntity getDefaultCollection();
}
