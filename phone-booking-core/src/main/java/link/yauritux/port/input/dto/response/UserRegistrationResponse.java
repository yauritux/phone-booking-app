package link.yauritux.port.input.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 *
 * @param id represents user id that is unique
 * @param name represents user name
 * @param errorMessage error message if any
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserRegistrationResponse(String id, String name, String errorMessage) {

    public UserRegistrationResponse(String id, String name) {
        this(id, name, null);
    }
}
