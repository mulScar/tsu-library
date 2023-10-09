package tsuprojects.tsulibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tsuprojects.tsulibrary.domain.AuthorEntity;

import java.util.UUID;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, UUID> {

    @Query("SELECT CASE when count(author) > 0 then TRUE else FALSE end " +
            "FROM " + AuthorEntity.ENTITY_NAME + " as author " +
            "WHERE author.firstName = :firstName " +
            "AND author.lastName = :lastName ")
    boolean existsByFullName(@Param("firstName") String firstName, @Param("lastName") String lastName);
}
