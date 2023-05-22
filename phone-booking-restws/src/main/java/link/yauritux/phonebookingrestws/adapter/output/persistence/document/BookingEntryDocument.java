package link.yauritux.phonebookingrestws.adapter.output.persistence.document;

import link.yauritux.domain.entity.BookingEntry;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 *
 * Represents a booking entry document MongoDB collection.
 */
@Document(value = "booking_entries")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BookingEntryDocument {

    @Id
    private String id;

    private LocalDateTime timestamp;
    private String phoneId;
    private String userId;
    private BookingEntry.BookingStatus status;
}
