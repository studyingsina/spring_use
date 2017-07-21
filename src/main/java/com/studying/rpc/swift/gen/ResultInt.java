package com.studying.rpc.swift.gen;

import com.facebook.swift.codec.*;
import com.facebook.swift.codec.ThriftField.Requiredness;

import java.util.*;

import static com.google.common.base.MoreObjects.toStringHelper;

@ThriftStruct("ResultInt")
public final class ResultInt
{
    public ResultInt() {
    }

    private ThriftResult result;

    @ThriftField(value=1, name="result", requiredness=Requiredness.NONE)
    public ThriftResult getResult() { return result; }

    @ThriftField
    public void setResult(final ThriftResult result) { this.result = result; }

    private int value;

    @ThriftField(value=2, name="value", requiredness=Requiredness.NONE)
    public int getValue() { return value; }

    @ThriftField
    public void setValue(final int value) { this.value = value; }

    @Override
    public String toString()
    {
        return toStringHelper(this)
            .add("result", result)
            .add("value", value)
            .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ResultInt other = (ResultInt)o;

        return
            Objects.equals(result, other.result) &&
            Objects.equals(value, other.value);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(new Object[] {
            result,
            value
        });
    }
}
