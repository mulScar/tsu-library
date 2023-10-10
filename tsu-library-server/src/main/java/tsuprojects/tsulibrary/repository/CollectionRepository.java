package tsuprojects.tsulibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tsuprojects.tsulibrary.domain.CollectionEntity;

import java.util.UUID;

@Repository
public interface CollectionRepository extends JpaRepository<CollectionEntity, UUID> {

    boolean existsByName(String name);

    CollectionEntity getByName(String name);
}
