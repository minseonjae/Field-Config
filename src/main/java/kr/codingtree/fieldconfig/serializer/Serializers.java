package kr.codingtree.fieldconfig.serializer;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Iterator;

@UtilityClass
public class Serializers {

    private ArrayList<ValueSerializer> serializers = new ArrayList<>();

    /**
     * 값 변환기를 추가합니다.
     * @param object ValueSerializer
     * @return 정상적으로 추가 되었을 경우 True, 추가되지 않았을 경우 False
     */
    @SneakyThrows(Exception.class)
    public boolean register(Object object) {
        if (object instanceof Class) {
            object = ((Class) object).newInstance();
        }

        if (object instanceof ValueSerializer && !isRegistered(object)) {
            serializers.add((ValueSerializer) object);
        }
        return false;
    }

    /**
     * 값 변환기들 중에서 해당 변수의 값 변환기를 찾아 제거합니다.
     * Integer 값 변환기의 경우, Integer 변수를 변환할 수 있는 VauleSerializer 또는 Integer.class
     * @param object ValueSerializer, ?.class 등
     * @return 정상적으로 제거 되었을 경우 True, 제거되지 않았을 경우 False
     */
    public boolean unregister(Object object) {
        if (isRegistered(object)) {
            if (!serializers.remove(object)) {
                Iterator<ValueSerializer> iterator = serializers.iterator();

                while (iterator.hasNext()) {
                    ValueSerializer serializer = iterator.next();

                    if (serializer.equals(object)) {
                        iterator.remove();
                        return true;
                    }
                }
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 값을 변환할 수 있는 값 변환기를 가져옵니다.
     * Integer 값 변환기의 경우, Integer 변수를 변환할 수 있는 VauleSerializer 또는 Integer.class
     * @param object ValueSerializer, ?.class 등
     * @return 변환할 수 있는 값 변환기가 있을 경우 ValueSerializer, 없을 경우 null
     */
    public ValueSerializer get(Object object) {
        if (object != null) {
            for (ValueSerializer serializer : serializers) {
                if (serializer.equals(object)) {
                    return serializer;
                }
            }
        }

        return null;
    }

    /**
     * 값을 변환할 수 있는 값 변환기가 있는지 확인합니다.
     * Integer 값 변환기의 경우, Integer 변수를 변환할 수 있는 VauleSerializer 또는 Integer.class
     * @param object ValueSerializer, ?.class 등
     * @return 등록되어 있을 경우 True, 안되어 있을 경우 False
     */
    public boolean isRegistered(Object object) {
        if (object != null) {
            for (ValueSerializer serializer : serializers) {
                if (serializer.equals(object)) {
                    return true;
                }
            }
        }

        return false;
    }
}
