# Field-Config

![snakeyaml](https://img.shields.io/badge/snakeyaml-1.30-GREEN?style=for-the-badge)
![gson](https://img.shields.io/badge/gson-2.9.0-GREEN?style=for-the-badge)
![lombok](https://img.shields.io/badge/lombok-1.18.24-GREEN?style=for-the-badge)

![platform-config](https://img.shields.io/badge/Platform%20Config-0.0.3-GREEN?style=for-the-badge)

Class안에 있는 모든 변수를 Config에 저장합니다.

#
## 예제
### Test.java 
``` Java
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
```
###
## 예제 (yaml config)
### FieldYamlConfigTest.java
``` Java
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

        System.out.println("========json");
        System.out.println("a : " + Test.a);
        System.out.println("b : " + Test.b);
        System.out.println("c : " + Test.c);
        System.out.println("list : " + Test.list);
        System.out.println("map1 : " + Test.map1);
        System.out.println("map2 : " + Test.map2);
        System.out.println("맵1 : " + fieldConfig.config.getMap("맵1"));
    }
}
```

### 결과 콘솔
```text
========yaml
a : 1
b : 1
c : a
list : [a, b, c]
map1 : {a=aa, b=bb, c=cc, d=dd}
map2 : {}
맵1 : {a=aa, b=bb, c=cc, d=dd}
```

### 저장된 config.yml

``` yaml
a: 1
c: a
리스트:
- a
- b
- c
맵1:
  a: aa
  b: bb
  c: cc
  d: dd

```

###
## 예제 (json config)
### FieldYamlConfigTest.java
``` Java
package example.field;

import kr.codingtree.fieldconfig.FieldConfig;
import kr.codingtree.platformconfig.JsonConfig;

public class FieldJsonConfigTest {

    public static void main(String[] args) {
        Test.map1.put("a", "aa");
        Test.map1.put("b", "bb");
        Test.map1.put("c", "cc");
        Test.map1.put("d", "dd");

        FieldConfig fieldConfig = new FieldConfig("src/main/java/example/field/config.json", Test.class, JsonConfig.class);

        fieldConfig.load();

        System.out.println("========json");
        System.out.println("a : " + Test.a);
        System.out.println("b : " + Test.b);
        System.out.println("c : " + Test.c);
        System.out.println("list : " + Test.list);
        System.out.println("map1 : " + Test.map1);
        System.out.println("map2 : " + Test.map2);
        System.out.println("맵1 : " + fieldConfig.config.getMap("맵1"));
    }

}

```

### 결과 콘솔
```text
========json
a : 1
b : 1
c : a
list : [a, b, c]
map1 : {a=aa, b=bb, c=cc, d=dd}
map2 : {}
맵1 : {a=aa, b=bb, c=cc, d=dd}
```

### 저장된 config.json

``` json
{
  "a": 1.0,
  "c": "a",
  "리스트": [
    "a",
    "b",
    "c"
  ],
  "맵1": {
    "a": "aa",
    "b": "bb",
    "c": "cc",
    "d": "dd"
  }
}
```
