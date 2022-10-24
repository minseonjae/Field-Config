package kr.codingtree.fieldconfig;

import kr.codingtree.fieldconfig.annotation.ConfigExclude;
import kr.codingtree.fieldconfig.annotation.ConfigName;

import kr.codingtree.fieldconfig.serializer.Serializers;

import kr.codingtree.fieldconfig.serializer.ValueSerializer;
import kr.codingtree.platformconfig.FileConfig;

import lombok.SneakyThrows;

import java.io.File;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class FieldConfig {

    public File file;
    private Object saveObject;
    public FileConfig config;

    /**
     * 클래스 안에 있는 변수 값을 콘피그에 저장합니다.
     * @param fileName 저장할 콘피그 파일 이름
     * @param saveObject 콘피그에 저장할 클래스 [Class.class 또는 new Class()]
     * @param configType 콘피그 타입 [JsonConfig.class 또는 YamlConfig.class]
     */
    public FieldConfig(String fileName, Object saveObject, Class<? extends FileConfig> configType) {
        this(new File(fileName), saveObject, configType);
    }
    /**
     * 클래스 안에 있는 변수 값을 콘피그에 저장합니다.
     * @param path 저장할 콘피그 파일 위치
     * @param fileName 저장할 콘피그 파일 이름
     * @param saveObject 콘피그에 저장할 클래스 [Class.class 또는 new Class()]
     * @param configType 콘피그 타입 [JsonConfig.class 또는 YamlConfig.class]
     */
    public FieldConfig(String path, String fileName, Object saveObject, Class<? extends FileConfig> configType) {
        this(new File(path, fileName), saveObject, configType);
    }
    /**
     * 클래스 안에 있는 변수 값을 콘피그에 저장합니다.
     * @param path 저장할 콘피그 파일 위치
     * @param fileName 저장할 콘피그 파일 이름
     * @param saveObject 콘피그에 저장할 클래스 [Class.class 또는 new Class()]
     * @param configType 콘피그 타입 [JsonConfig.class 또는 YamlConfig.class]
     */
    public FieldConfig(File path, String fileName, Object saveObject, Class<? extends FileConfig> configType) {
        this(new File(path, fileName), saveObject, configType);
    }
    /**
     * 클래스 안에 있는 변수 값을 콘피그에 저장합니다.
     * @param file 저장할 콘피그 파일 [확장자명 정확히]
     * @param saveObject 콘피그에 저장할 클래스 [Class.class 또는 new Class()]
     * @param configType 콘피그 타입 [JsonConfig.class 또는 YamlConfig.class]
     */
    @SneakyThrows(Exception.class)
    public FieldConfig(File file, Object saveObject, Class<? extends FileConfig> configType) {
        this.file = file;
        this.saveObject = saveObject instanceof Class ? ((Class) saveObject).newInstance() : saveObject;
        config = configType.newInstance();

        for (Field field : this.saveObject.getClass().getDeclaredFields()) {
            if (field.getAnnotation(ConfigExclude.class) == null) {
                field.setAccessible(true);

                Object value = serializer(field, field.get(saveObject));

                if (value != null) {
                    ConfigName configName = field.getAnnotation(ConfigName.class);

                    config.addDefault(configName != null ? configName.value() : field.getName(), value);
                }
            }
        }
    }

    /**
     * Config에서 데이터를 가져와서 생성할 때 넣은 클래스 안에 변수
     * 생성할 때 넣은 클래스 안에 있는 변수들에 Config에서 불러온 데이터를 넣습니다.
     */
    @SneakyThrows(Exception.class)
    public void load() {
        config.load(file);

        for (Field field : saveObject.getClass().getDeclaredFields()) {
            if (field.getAnnotation(ConfigExclude.class) == null) {
                field.setAccessible(true);

                Object value = deserializer(field, field.get(saveObject));

                if (value != null) {
                    field.set(saveObject, value);
                }
            }
        }
    }

    /**
     * 생성할 때 넣은 클래스 안에 있는 변수들을 변환하여 file 위치에 Config를 저장합니다.
     */
    @SneakyThrows(Exception.class)
    public void save() {
        for (Field field : saveObject.getClass().getDeclaredFields()) {
            if (field.getAnnotation(ConfigExclude.class) == null) {
                field.setAccessible(true);

                Object value = serializer(field, field.get(saveObject));

                if (value != null) {
                    ConfigName configName = field.getAnnotation(ConfigName.class);

                    config.set(configName != null ? configName.value() : field.getName(), value);
                }
            }
        }

        config.save(file);
    }

    /**
     * String을 값 변환기에 넣어 변수에 데이터를 저장합니다.
     * Map타입의 경우 Map<.String, String>를 값 변환기에 넣어 Map<.?, ?>으로 변환합니다.
     * Collection타입의 Collection<.String>를 값 변환기에 넣어 Collection<.?>으로 변환합니다.
     * @param field 변수
     * @param fieldValue 변수 값
     * @return 성공적으로 변환하지 못 했을 경우에만 null
     */
    @SneakyThrows(Exception.class)
    private Object deserializer(Field field, Object fieldValue) {
        ConfigName configName = field.getAnnotation(ConfigName.class);
        String name = configName != null ? configName.value() : field.getName();

        if (fieldValue instanceof Map && field.getGenericType() instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();

            if (types.length == 2) {
                ValueSerializer keySerializer = Serializers.get(types[0]),
                        valueSerializer = Serializers.get(types[1]);

                if ((keySerializer == null && !isDefaultClass(types[0].getTypeName())) || (valueSerializer == null && !isDefaultClass(types[1].getTypeName()))) {
                    return null;
                }

                Map map = (Map) fieldValue.getClass().newInstance();
                Map<String, Object> configMap = config.getMap(name);

                for (Map.Entry<String, Object> entry : configMap.entrySet()) {
                    Object key = keySerializer != null ? keySerializer.deserializer(entry.getKey()) : entry.getKey(),
                            value = valueSerializer != null ? valueSerializer.deserializer(entry.getValue().toString()) : entry.getValue();
                    map.put(key, value);
                }

                return map;
            }
        } else if (fieldValue instanceof Collection && field.getGenericType() instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();

            if (types.length == 1) {
                if (isDefaultClass(types[0].getTypeName())) {
                    return fieldValue;
                }

                ValueSerializer serializer = Serializers.get(types[0]);

                if (serializer == null) {
                    return null;
                }

                Collection list = (Collection) fieldValue.getClass().newInstance();

                for (String value : config.getStringList(name)) {
                    list.add(serializer.deserializer(value));
                }

                return list;
            }
        } else {
            if (isDefaultClass(fieldValue.getClass().getName())) {
                if (fieldValue instanceof String) {
                    return config.getString(name);
                } else if (fieldValue instanceof Integer) {
                    return config.getInt(name);
                } else if (fieldValue instanceof Double) {
                    return config.getDouble(name);
                } else if (fieldValue instanceof Float) {
                    return config.getFloat(name);
                } else if (fieldValue instanceof Boolean) {
                    return config.getBoolean(name);
                }
            } else {
                ValueSerializer serializer = Serializers.get(fieldValue);

                return serializer != null ? serializer.serializer(fieldValue) : null;
            }
        }
        return null;
    }

    /**
     * 값 변환기에 변수를 넣어 String으로 변환합니다.
     * Map타입의 경우 제네릭 클래스로 값 변환기를 찾아 LinkedHashMap<.String, String>으로 변환합니다.
     * Collection타입의 경우에도 제네릭 클래스로 값 변환기를 찾아 ArrayList<.String>으로 변환합니다.
     * @param field 변수
     * @param fieldValue 변수 값
     * @return 성공적으로 변환하지 못 했을 경우에만 null
     */
    @SneakyThrows(Exception.class)
    private Object serializer(Field field, Object fieldValue) {
        if (fieldValue instanceof Map && field.getGenericType() instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();

            if (types.length == 2) {
                if (isDefaultClass(types[0].getTypeName()) && isDefaultClass(types[1].getTypeName())) {
                    return fieldValue;
                }

                ValueSerializer key = Serializers.get(types[0]),
                        value = Serializers.get(types[1]);

                if (key == null || value == null) {
                    return null;
                }

                Map<?, ?> map = (Map) fieldValue;
                LinkedHashMap<String, String> dataMap = new LinkedHashMap<>();

                for (Map.Entry entry : map.entrySet()) {
                    dataMap.put(key.serializer(entry.getKey()), value.serializer(entry.getValue()));
                }

                return dataMap;
            }
        } else if (fieldValue instanceof Collection && field.getGenericType() instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();

            if (types.length == 1) {
                if (isDefaultClass(types[0].getTypeName())) {
                    return fieldValue;
                }

                ValueSerializer serializer = Serializers.get(types[0]);

                if (serializer == null) {
                    return null;
                }

                Collection collection = (Collection) fieldValue;
                ArrayList<String> list = new ArrayList<>();

                for (Object value : collection) {
                    list.add(serializer.serializer(value));
                }

                return list;
            }
        } else {
            ValueSerializer serializer = Serializers.get(fieldValue);

            return serializer != null ? serializer.serializer(fieldValue) : (fieldValue != null && isDefaultClass(fieldValue.getClass().getName()) ? fieldValue : null);
        }
        return null;
    }

    private boolean isDefaultClass(String name) {
        return name.startsWith("java.lang.");
    }
}
