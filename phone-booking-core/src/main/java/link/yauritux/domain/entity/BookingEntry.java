package link.yauritux.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BookingEntry {

    private String id;
    private LocalDateTime timestamp;
    private String phoneId;
    private String userId;
    private BookingStatus status;

    public enum BookingStatus {
        BOOKED,
        RETURNED
    }
}
