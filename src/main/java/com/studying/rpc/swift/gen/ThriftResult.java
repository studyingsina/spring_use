package com.studying.rpc.swift.gen;

import com.facebook.swift.codec.*;

public enum ThriftResult {
    SUCCESS(0), SERVER_UNWORKING(1), NO_CONTENT(2), PARAMETER_ERROR(3), EXCEPTION(4), INDEX_ERROR(5), UNKNOWN_ERROR(6), DATA_NOT_COMPLETE
            (7), INNER_ERROR(8);

    private final int value;

    ThriftResult(int value) {
        this.value = value;
    }

    @ThriftEnumValue
    public int getValue() {
        return value;
    }
}
