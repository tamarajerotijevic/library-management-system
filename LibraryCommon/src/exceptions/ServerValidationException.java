package exceptions;

public class ServerValidationException extends Exception {

    public ServerValidationException() {
    }

    public ServerValidationException(String message) {
        super(message);
    }
}
