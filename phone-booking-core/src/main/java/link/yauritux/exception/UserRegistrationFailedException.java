package link.yauritux.exception;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 */
public class UserRegistrationFailedException extends RuntimeException {

    public UserRegistrationFailedException(String message) {
        super(message);
    }
}
