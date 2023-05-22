package link.yauritux.port.input.dto.request;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 *
 * @param brand brand of the phone (e.g., Samsung, Apple, Motorola, etc).
 * @param deviceName phone's device name / model (e.g., Apple iPhone13, Motorola Nexus 9, etc).
 */
public record PhoneRegistrationRequest(String brand, String deviceName) {}
