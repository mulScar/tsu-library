package tsuprojects.tsulibrary.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tsuprojects.tsulibrary.data.requests.RequestCollection;
import tsuprojects.tsulibrary.domain.CollectionEntity;
import tsuprojects.tsulibrary.exception.BadRequestException;
import tsuprojects.tsulibrary.exception.NotFoundException;
import tsuprojects.tsulibrary.repository.CollectionRepository;
import tsuprojects.tsulibrary.service.CollectionService;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
public class CollectionServiceImpl implements CollectionService {

    @NonNull
    private final CollectionRepository collectionRepository;

    @NonNull
    private final ModelMapper modelMapper;

    public CollectionServiceImpl(
            @NonNull CollectionRepository collectionRepository,
            @NonNull ModelMapper modelMapper) {
        this.collectionRepository = collectionRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CollectionEntity> findAll() {
        return collectionRepository.findAll();
    }

    @Override
    public List<CollectionEntity> findAllByIds(Set<UUID> ids) {
        return collectionRepository.findAllById(ids);
    }

    @Override
    public CollectionEntity findById(UUID id) {
        return collectionRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Collection by id= " + id + " is not found in DB");
                    return new NotFoundException("Collection is not found");
                });
    }

    @Override
    public CollectionEntity create(RequestCollection requestCollection) {
        boolean isCollectionExist = collectionRepository.existsByName(requestCollection.getName());
        if (isCollectionExist) {
            throw new BadRequestException("Collection name=" + requestCollection.getName() + " already exist");
        }
        CollectionEntity collection = modelMapper.map(requestCollection, CollectionEntity.class);
        collectionRepository.save(collection);
        log.info("Collection id=" + collection.getId() + " successfully created");
        return collection;
    }

    @Override
    @Transactional
    public CollectionEntity update(UUID id, RequestCollection requestCollection) {
        CollectionEntity collection = findById(id);
        modelMapper.map(requestCollection, collection);
        collectionRepository.save(collection);
        log.info("Collection id=" + id + " successfully updated");
        return collection;
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        CollectionEntity collection = findById(id);
        collectionRepository.delete(collection);
        log.info("Collection id=" + id + " successfully deleted");
    }

    @Override
    public CollectionEntity getDefaultCollection() {
        CollectionEntity defaultCollection = collectionRepository.getByName(DEFAULT_COLLECTION);
        if (defaultCollection == null) {
            defaultCollection = new CollectionEntity();
            defaultCollection.setName(DEFAULT_COLLECTION);
            collectionRepository.save(defaultCollection);
            log.info("Default collection id=" + defaultCollection.getId() + " successfully created");
        }
        return defaultCollection;
    }
}
