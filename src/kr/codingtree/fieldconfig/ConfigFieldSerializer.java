package kr.codingtree.fieldconfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigFieldSerializer {

    Class<? extends ConfigSerializer> value();

    interface ConfigSerializer<T> {
        String serializer(T value);
        T deserializer(String value);
    }
}
