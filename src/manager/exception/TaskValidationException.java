package manager.exception;

public class TaskValidationException extends RuntimeException {
    public TaskValidationException() {
    }

    public TaskValidationException(String message) {
        super(message);
    }

    public TaskValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskValidationException(Throwable cause) {
        super(cause);
    }

    public TaskValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
