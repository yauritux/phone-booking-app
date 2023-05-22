package link.yauritux.domain.entity;

import link.yauritux.exception.PhoneRegistrationFailedException;
import lombok.Builder;
import lombok.Data;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 *
 * Represents a Phone business entity.
 */
@Data
@Builder
public class Phone {

    private String id;
    private String brand;
    private String deviceName;
    private String technology;
    private String _2gBands;
    private String _3gBands;
    private String _4gBands;
    private boolean available;

    /**
     * This method will validate all entries necessary before a phone is registered into the system.
     *
     * @throws PhoneRegistrationFailedException will be thrown by the system
     * in case one or more business validations failed to be fulfilled.
     */
    public void validateEntity() throws PhoneRegistrationFailedException {
        if (brand == null || brand.trim().equals("")) {
            throw new PhoneRegistrationFailedException("brand must not be empty!");
        }
        if (deviceName == null || deviceName.trim().equals("")) {
            throw new PhoneRegistrationFailedException("device name must not be empty!");
        }
    }
}
