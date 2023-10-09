package tsuprojects.tsulibrary.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Entity(name = BookEntity.ENTITY_NAME)
@Table(name = "BOOKS")
public class BookEntity {

    public static final String ENTITY_NAME = "Book";

    @Id
    @Column(columnDefinition = "uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "inventory_number", unique = true)
    private String inventoryNumber;

    @Column(name = "b_code", unique = true)
    private Integer bCode;

    @Column(name = "title", unique = true)
    private String title;

    @Column(name = "parts_quantity")
    private String partsQuantity;

    @Column(name = "publication_place")
    private String publicationPlace;

    @Column(name = "publishing_house")
    private String publishingHouse;

    @Column(name = "publishing_year")
    private Integer publishingYear;

    @Column(name = "language")
    private String language;

    @Column(name = "subjects")
    private String subjects;

    @Column(name = "publication_type")
    private String publicationType;

    @Column(name = "autograph")
    private String autograph;

    @Column(name = "note")
    private String note;

    @Column(name = "bookplate")
    private String bookplate;

    @Column(name = "stamp")
    private String stamp;

    @Column(name = "label")
    private String label;

    @Column(name = "binding")
    private String binding;

    @Column(name = "description")
    private String description;

    @ManyToMany()
    @JoinTable(name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<AuthorEntity> authors;

    public void addAuthor(AuthorEntity author) {
        if (authors == null) {
            authors = new HashSet<>();
        }
        authors.add(author);
    }

    public void addAuthors(List<AuthorEntity> authors) {
        for (AuthorEntity author : authors) {
            addAuthor(author);
        }
    }

    public void removeAuthor(AuthorEntity author) {
        if (authors != null) {
            authors.remove(author);
        }
    }
}
