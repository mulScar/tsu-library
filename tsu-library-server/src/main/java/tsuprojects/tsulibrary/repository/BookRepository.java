package tsuprojects.tsulibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tsuprojects.tsulibrary.domain.BookEntity;

import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, UUID> {

    boolean existsByInventoryNumberOrTitle(String inventoryNumber, String title);
}
