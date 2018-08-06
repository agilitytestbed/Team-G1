package nl.utwente.ing.exception;

public class NoSuchIdForTransactionException extends RuntimeException {

    private static final String message = "This ID doesn't correspond with any transaction from the service: ";

    public NoSuchIdForTransactionException(Long Id) {
        super(message + Id);
    }

    public NoSuchIdForTransactionException(String message) {
        super(message);
    }

    public NoSuchIdForTransactionException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchIdForTransactionException(Throwable cause) {
        super(cause);
    }
}
