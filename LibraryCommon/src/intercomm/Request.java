package intercomm;

import java.io.Serializable;

public class Request implements Serializable {

    private Object argument;
    private Operation operation;

    public Request() {
    }

    public Request(Object argument, Operation operation) {
        this.argument = argument;
        this.operation = operation;
    }

    public Object getArgument() {
        return argument;
    }

    public void setArgument(Object argument) {
        this.argument = argument;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }
}
