package link.yauritux.domain.entity;

import link.yauritux.exception.UserRegistrationFailedException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 *
 * Represents a User business entity.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {

    private String id;
    private String name;

    /**
     * This method will validate all entries necessary before user is created/registered into the system.
     *
     * @throws UserRegistrationFailedException will be thrown by the system
     * in case one or more business validations failed to be fulfilled.
     */
    public void validateEntity() throws UserRegistrationFailedException {
        if (name == null || name.trim().equals("")) {
            throw new UserRegistrationFailedException("name must not be empty!");
        }
    }
}
