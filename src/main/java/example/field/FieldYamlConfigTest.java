package example.field;

import kr.codingtree.fieldconfig.FieldConfig;
import kr.codingtree.platformconfig.YamlConfig;

public class FieldYamlConfigTest {

    public static void main(String[] args) {
        Test.map1.put("a", "aa");
        Test.map1.put("b", "bb");
        Test.map1.put("c", "cc");
        Test.map1.put("d", "dd");

        FieldConfig fieldConfig = new FieldConfig("src/main/java/example/field/config.yml", Test.class, YamlConfig.class);

        fieldConfig.load();

        System.out.println("========yaml");
        System.out.println("a : " + Test.a);
        System.out.println("b : " + Test.b);
        System.out.println("c : " + Test.c);
        System.out.println("list : " + Test.list);
        System.out.println("map1 : " + Test.map1);
        System.out.println("map2 : " + Test.map2);
        System.out.println("맵1 : " + fieldConfig.config.getMap("맵1"));
    }
}
