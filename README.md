# Field-Config

Bukkit & BungeeCord Config Library

### __Required List__ _(already included)_
__- Platform-Config__\
__- Spigot__\
__- BungeeCord__\
__- lombok__

## Example
### TestPlugin.java __(Bukkit related get must create class within onEnable)__
``` Java
public class TestPlugin extends JavaPlugin {

    private static TestConfig config;

    @Override
    public void onEnable() {
        config = (TestConfig) new TestConfig(getDataFolder(), "config.yml").save();

        System.out.println(config.test1);
        System.out.println(config.test2);
        System.out.println(config.test3);
        System.out.println(config.test4.toString());
        System.out.println(config.test5.toString());
    }
    
}
```
### TestConfig.java
``` Java
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.io.File;

public class TestConfig extends FieldConfig {

    public TestConfig(File path, String name) {
        super(path, name);
    }

    public String test1 = "111";

    @ConfigFieldExclude
    public String test2 = "222";

    @ConfigFieldName("ConfigTest")
    public String test3 = "333";

    @ConfigFieldSerializer(LocationSerializer.class)
    public Location test4 = new Location(Bukkit.getWorld("world"), 0, 0, 0, 0, 0);

    @ConfigFieldSerializer(ItemCodeSerializer.class)
    public ItemStack test5 = new ItemStack(1, 1, (short) 2);
}
```

### Result Console
```text
111
222
333
Location{world=CraftWorld{name=world},x=0.0,y=0.0,z=0.0,pitch=0.0,yaw=0.0}
ItemStack{STONE x 1}
```

### Saved config.yml

``` yaml
test1: '111'
ConfigTest: '333'
test4: 'world, 0, 0, 0, 0, 0'
test5: '1:2'
```


### Reverse loading of config.yml will change the value of the variable.
