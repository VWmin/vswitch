package com.vwmin;

public class ControllerStateException extends IllegalStateException {
    private static final long serialVersionUID = 9153954512470002631L;

    public ControllerStateException(String msg) {
        super(msg);
    }

    public ControllerStateException(Throwable t) {
        super(t);
    }
}
