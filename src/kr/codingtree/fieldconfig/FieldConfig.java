package kr.codingtree.fieldconfig;

import kr.codingtree.platformconfig.Config;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.File;
import java.lang.reflect.Field;

public class FieldConfig {
    public FieldConfig(File file) {
        config = new Config(file);
    }
    public FieldConfig(File file, String name) {
        config = new Config(file, name);
    }

    @Getter
    private Config config;

    @SneakyThrows(Exception.class)
    public FieldConfig load() {
        config.load();

        for (Field field : getClass().getDeclaredFields()) {
            if (field.getAnnotation(ConfigFieldExclude.class) == null)  {
                field.setAccessible(true);

                ConfigFieldName fieldName = field.getAnnotation(ConfigFieldName.class);

                Object value = config.get(fieldName != null ? fieldName.value() : field.getName());

                if (value != null) {
                    ConfigFieldSerializer serializer = field.getAnnotation(ConfigFieldSerializer.class);
                    if (serializer != null) {
                        value = serializer.value().newInstance().deserializer(value.toString());
                    }
                }
                field.set(this, value);
            }
        }
        return this;
    }
    @SneakyThrows(Exception.class)
    public FieldConfig save() {
        config.load();

        for (Field field : getClass().getDeclaredFields()) {
            if (field.getAnnotation(ConfigFieldExclude.class) == null)  {
                field.setAccessible(true);

                Object value = field.get(this);

                if (value != null) {
                    ConfigFieldSerializer serializer = field.getAnnotation(ConfigFieldSerializer.class);
                    if (serializer != null) {
                        value = serializer.value().newInstance().serializer(value.toString());
                    }
                }

                ConfigFieldName fieldName = field.getAnnotation(ConfigFieldName.class);
                config.set(fieldName != null ? fieldName.value() : field.getName(), value);
            }
        }
        config.save();
        return this;
    }
}
