package skadistats.clarity.io.s2.field.impl;

import skadistats.clarity.io.Util;
import skadistats.clarity.io.s2.Field;
import skadistats.clarity.io.s2.FieldType;
import skadistats.clarity.io.s2.DecoderProperties;
import skadistats.clarity.model.state.ArrayEntityState;

public class ArrayField extends Field {

    private final Field elementField;
    private final int length;

    public ArrayField(FieldType fieldType, Field elementField, int length) {
        super(fieldType, DecoderProperties.DEFAULT);
        this.elementField = elementField;
        this.length = length;
    }

    @Override
    public String getChildNameSegment(int idx) {
        return Util.arrayIdxToString(idx);
    }

    @Override
    public Field getChild(int idx) {
        return elementField;
    }

    @Override
    public Integer getChildIndex(String name) {
        return Util.stringToArrayIdx(name);
    }

    @Override
    public void ensureArrayEntityStateCapacity(ArrayEntityState state, int capacity) {
        state.capacity(length);
    }

    @Override
    public Object getArrayEntityState(ArrayEntityState state, int idx) {
        return state.sub(idx).length();
    }

}
