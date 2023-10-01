package com.wefox.payment.enums;

public enum PaymentType {
    OFFLINE("offline"),
    ONLINE("online");

    private final String name;

    PaymentType(String name) {
        this.name = name;
    }

    public boolean equalsName(String otherName) {
        return name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}
