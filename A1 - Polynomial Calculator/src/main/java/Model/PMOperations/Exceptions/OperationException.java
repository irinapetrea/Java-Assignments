package Model.PMOperations.Exceptions;

public class OperationException extends Exception {
    public OperationException(String msg) {
        super(msg);
    }

    public OperationException() {
        super("Operation Exception");
    }
}
