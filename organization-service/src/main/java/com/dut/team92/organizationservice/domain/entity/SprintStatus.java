package com.dut.team92.organizationservice.domain.entity;

public enum SprintStatus {
    UNSTART(1), STARTING(2), COMPLETED(3);
    private final int value;

    SprintStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
