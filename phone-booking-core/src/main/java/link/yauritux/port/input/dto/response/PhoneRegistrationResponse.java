package link.yauritux.port.input.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 *
 * A response object type returned by the phone registration service.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Data
public class PhoneRegistrationResponse {
    /**
     * phone's id as generated by the repository system (e.g., database primary key auto generation)
     */
    private String id;

    private String brand;
    private String deviceName;
    private String technology;
    private String _2gBands;
    private String _3gBands;
    private String _4gBands;

    /**
     * an error message contains explanation about the error if the phone registration is failed.
     */
    private String errorMessage;
}