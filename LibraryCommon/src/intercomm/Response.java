package intercomm;

import java.io.Serializable;

public class Response implements Serializable {

    private Object result;
    private Operation operation;
    private Exception exception;

    public Response() {
    }

    public Response(Object result, Operation operation, Exception exception) {
        this.result = result;
        this.operation = operation;
        this.exception = exception;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
