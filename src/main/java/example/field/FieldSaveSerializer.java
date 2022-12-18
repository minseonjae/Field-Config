package example.field;

import kr.codingtree.fieldconfig.serializer.ValueSerializer;

public class FieldSaveSerializer extends ValueSerializer<FieldSave> {

    @Override
    public String serializer(FieldSave value) {
        return value.a + ", " + value.b;
    }

    @Override
    public FieldSave deserializer(String value) {
        FieldSave fs = new FieldSave();
        String[] sp = value.split(", ");

        fs.a = Integer.parseInt(sp[0]);
        fs.b = Integer.parseInt(sp[1]);

        return fs;
    }
}
