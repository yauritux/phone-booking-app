package link.yauritux.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 *
 * Represents a phone booking line entry, i.e. phone's that is booked by a user.
 */
@Data
@Builder
public class BookingEntry {

    /**
     * A booking entry ID that is unique as generated by the system.
     */
    private String id;
    /**
     * Date and time of a booking transaction.
     */
    private LocalDateTime timestamp;
    /**
     * id of the phone as registered in the system.
     */
    private String phoneId;
    /**
     * id of the user that booked the phone.
     */
    private String userId;
    /**
     * status of booking, either <code>BOOKED</code> which means phone is booked by a User,
     * or <code>RETURNED</code> which means phone is returned by the user.
     */
    private BookingStatus status;

    public enum BookingStatus {
        BOOKED,
        RETURNED
    }
}
