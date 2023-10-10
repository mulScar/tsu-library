package tsuprojects.tsulibrary.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity(name = CollectionEntity.ENTITY_NAME)
@Table(name = "COLLECTION")
public class CollectionEntity {

    public static final String ENTITY_NAME = "Collection";

    @Id
    @Column(columnDefinition = "uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "name", unique = true)
    private String name;
}
