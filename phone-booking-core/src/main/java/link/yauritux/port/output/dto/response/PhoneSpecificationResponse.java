package link.yauritux.port.output.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 *
 * This class used to encapsulate response from the upstream service (e.g., FONOAPI).
 * We need that kind of upstream service to get a phone specification details.
 *
 * @param brand
 * @param deviceName
 * @param technology
 * @param _2gBands
 * @param _3gBands
 * @param _4gBands
 */
public record PhoneSpecificationResponse(
        @JsonProperty("Brand") String brand,
        @JsonProperty("DeviceName") String deviceName,
        String technology,
        @JsonProperty("_2g_bands") String _2gBands,
        @JsonProperty("_3g_bands") String _3gBands,
        @JsonProperty("_3g_bands") String _4gBands) {
}
