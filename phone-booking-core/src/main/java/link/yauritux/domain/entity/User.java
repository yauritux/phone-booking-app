package link.yauritux.domain.entity;

import link.yauritux.exception.UserRegistrationFailedException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {

    private String id;
    private String name;

    public void validateEntity() throws UserRegistrationFailedException {
        if (name == null || name.trim().equals("")) {
            throw new UserRegistrationFailedException("name must not be empty!");
        }
    }
}
