package tsuprojects.tsulibrary.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tsuprojects.tsulibrary.domain.BookEntity;

import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, UUID> {

    boolean existsByInventoryNumberOrTitle(String inventoryNumber, String title);

    @Query(value = "SELECT book FROM " + BookEntity.ENTITY_NAME + " as book " +
            "JOIN FETCH book.authors author " +
            "JOIN FETCH book.collections collection",
            countQuery = "SELECT COUNT(book) FROM " + BookEntity.ENTITY_NAME + " as book ")
    Page<BookEntity> findAll(Pageable pageable);

    @Query(value = "SELECT book FROM " + BookEntity.ENTITY_NAME + " as book " +
            "JOIN FETCH book.authors author " +
            "JOIN FETCH book.collections collection " +
            "WHERE collection.id = :collectionId",
            countQuery = "SELECT COUNT(book) FROM " + BookEntity.ENTITY_NAME + " as book " +
                    "JOIN book.collections collection " +
                    "WHERE collection.id = :collectionId")
    Page<BookEntity> findAllByCollectionId(@Param("collectionId") UUID collectionId, Pageable pageable);
}
