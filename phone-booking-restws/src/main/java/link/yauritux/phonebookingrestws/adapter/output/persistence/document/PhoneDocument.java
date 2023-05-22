package link.yauritux.phonebookingrestws.adapter.output.persistence.document;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 *
 * Represents a Phone document MongoDB collection.
 */
@Document(value = "phones")
@Data
@Builder
public class PhoneDocument {

    @Id
    private String id;
    private String brand;
    private String deviceName;
    private String technology;
    private String _2gBands;
    private String _3gBands;
    private String _4gBands;
    private boolean available;
}
