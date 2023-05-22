package link.yauritux.port.input.dto.request;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 *
 * Represents a booking entry request payload as requested by the system/user.
 *
 * @param phoneId id of the phone as registered in the system.
 * @param userId id of the user as registered in the system.
 */
public record BookingEntryRequest(String phoneId, String userId) {
}
