package example.field;

import kr.codingtree.fieldconfig.annotation.ConfigExclude;
import kr.codingtree.fieldconfig.annotation.ConfigName;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {

    public static int a = 1;

    @ConfigExclude
    public static int b = 1;

    public static String c = "a";

    @ConfigName("리스트")
    public static List<String> list = Arrays.asList("a", "b", "c");

    @ConfigName("맵1")
    public static Map<String, String> map1 = new HashMap();

    @ConfigName("맵2")
    @ConfigExclude
    public static Map<String, String> map2 = new HashMap();

}