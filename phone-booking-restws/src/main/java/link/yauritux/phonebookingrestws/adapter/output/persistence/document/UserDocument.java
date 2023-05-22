package link.yauritux.phonebookingrestws.adapter.output.persistence.document;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 *
 * Represents a User document MongoDB collection.
 */
@Document(value = "users")
@Data
@Builder
public class UserDocument {

    @Id
    private String id;
    private String name;
}
