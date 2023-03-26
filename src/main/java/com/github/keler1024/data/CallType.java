package com.github.keler1024.data;

import java.util.Objects;
import java.util.stream.Stream;

public enum CallType {
    OUTGOING("01"), INCOMING("02");

    private final String code;

    CallType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static CallType of(String code) {
        Objects.requireNonNull(code);
        return Stream.of(CallType.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public String toString() {
        return "CallType{" +
                "code='" + code + '\'' +
                '}';
    }
}
