package skadistats.clarity.decoder.s2.field;

import skadistats.clarity.decoder.s2.S2UnpackerFactory;
import skadistats.clarity.model.FieldPath;
import skadistats.clarity.model.s2.S2FieldPath;
import skadistats.clarity.model.s2.S2ModifiableFieldPath;
import skadistats.clarity.model.state.ArrayEntityState;

import java.util.List;

public class SimpleField extends Field {

    private final UnpackerCursorDelegate unpackerCursorDelegate;
    private final FieldSetterCursorDelegate fieldSetterCursorDelegate;

    public SimpleField(FieldProperties properties) {
        super(properties);
        unpackerCursorDelegate = UnpackerCursorDelegate.create(
                S2UnpackerFactory.createUnpacker(properties, properties.getType().getBaseType())
        );
        fieldSetterCursorDelegate = FieldSetterCursorDelegate.create(
                accessor -> accessor::set
        );
    }

    @Override
    public UnpackerCursorDelegate getUnpackerCursorDelegate() {
        return unpackerCursorDelegate;
    }

    @Override
    public FieldSetterCursorDelegate getFieldSetterCursorDelegate() {
        return fieldSetterCursorDelegate;
    }

    @Override
    public void accumulateName(S2FieldPath fp, int pos, List<String> parts) {
        assert fp.last() == pos;
        addBasePropertyName(parts);
    }

    @Override
    public Field getFieldForFieldPath(S2FieldPath fp, int pos) {
        assert fp.last() == pos;
        return this;
    }

    @Override
    public FieldType getTypeForFieldPath(S2FieldPath fp, int pos) {
        assert fp.last() == pos;
        return properties.getType();
    }

    @Override
    public Object getValueForFieldPath(S2FieldPath fp, int pos, ArrayEntityState state) {
        assert fp.last() == pos;
        return state.get(fp.get(pos));
    }

    @Override
    public void setValueForFieldPath(S2FieldPath fp, int pos, ArrayEntityState state, Object value) {
        assert fp.last() == pos;
        state.set(fp.get(pos), value);
    }

    @Override
    public S2FieldPath getFieldPathForName(S2ModifiableFieldPath fp, String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void collectFieldPaths(S2ModifiableFieldPath fp, List<FieldPath> entries, ArrayEntityState state) {
        entries.add(fp.yield());
    }
}
