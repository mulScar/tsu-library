package tsuprojects.tsulibrary.data.requests;

import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class RequestBook {

    private String inventoryNumber;
    private Integer bCode;
    private String title;
    private String partsQuantity;
    private String publicationPlace;
    private String publishingHouse;
    private Integer publishingYear;
    private String language;
    private String subjects;
    private String publicationType;
    private String autograph;
    private String note;
    private String bookplate;
    private String stamp;
    private String label;
    private String binding;
    private String description;
    private Set<UUID> authorsId;
}
