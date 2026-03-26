package exceptions;

public class ClientValidationException extends Exception {

    public ClientValidationException() {
    }

    public ClientValidationException(String message) {
        super(message);
    }
}
