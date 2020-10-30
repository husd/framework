package com.husd.framework.fsm;

public enum DoorStatus {

    OPEN(0),
    CLOSE(1),
    LOCKED(2),
    UNLOCK(3);

    private int status;

    DoorStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
