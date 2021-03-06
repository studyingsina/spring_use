package com.studying.rpc.swift.gen;

import com.facebook.swift.codec.ThriftField;
import com.facebook.swift.codec.ThriftField.Requiredness;
import com.facebook.swift.codec.ThriftStruct;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

import static com.google.common.base.MoreObjects.toStringHelper;

@ThriftStruct("ResultSetStr")
public final class ResultSetStr
{
    public ResultSetStr() {
    }

    private ThriftResult result;

    @ThriftField(value=1, name="result", requiredness=Requiredness.NONE)
    public ThriftResult getResult() { return result; }

    @ThriftField
    public void setResult(final ThriftResult result) { this.result = result; }

    private Set<String> value;

    @ThriftField(value=2, name="value", requiredness=Requiredness.NONE)
    public Set<String> getValue() { return value; }

    @ThriftField
    public void setValue(final Set<String> value) { this.value = value; }

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

        ResultSetStr other = (ResultSetStr)o;

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
