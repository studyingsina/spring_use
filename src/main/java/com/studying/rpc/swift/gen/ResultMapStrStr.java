package com.studying.rpc.swift.gen;

import com.facebook.swift.codec.ThriftField;
import com.facebook.swift.codec.ThriftField.Requiredness;
import com.facebook.swift.codec.ThriftStruct;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

import static com.google.common.base.MoreObjects.toStringHelper;

@ThriftStruct("ResultMapStrStr")
public final class ResultMapStrStr
{
    public ResultMapStrStr() {
    }

    private ThriftResult result;

    @ThriftField(value=1, name="result", requiredness=Requiredness.NONE)
    public ThriftResult getResult() { return result; }

    @ThriftField
    public void setResult(final ThriftResult result) { this.result = result; }

    private Map<String, String> value;

    @ThriftField(value=2, name="value", requiredness=Requiredness.NONE)
    public Map<String, String> getValue() { return value; }

    @ThriftField
    public void setValue(final Map<String, String> value) { this.value = value; }

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

        ResultMapStrStr other = (ResultMapStrStr)o;

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
