package tsuprojects.tsulibrary.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity(name = AuthorEntity.ENTITY_NAME)
@Table(name = "AUTHORS")
public class AuthorEntity {

    public static final String ENTITY_NAME = "Author";

    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";

    @Id
    @Column(columnDefinition = "uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
}
