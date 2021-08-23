# Field-Config

Bukkit & BungeeCord Config Library

###__Required List__ _(already included)_
__- Platform-Config__\
__- Spigot__\
__- BungeeCord__\
__- lombok__

## Example
### TestPlugin.java
``` Java
public class TestPlugin extends JavaPlugin {

    private TestConfig config = new TestConfig(getDataFolder(), "config.yml");

    @Override
    public void onEnable() {
        config.load();
        config.save();
    }

}
```
### TestConfig.java
``` Java
public class TestConfig extends FieldConfig {

    public String test1 = "111";
    
    @ConfigFieldExclude
    public String test2 = "222";
    
    @ConfigFieldName("ConfigTest")
    public String test3 = "333";
    
    @ConfigFieldSerializer(LocationSerializer.class)
    public Location test4 = new Location(Bukkit.getWorld("world"), 0, 0, 0, 0, 0);
    
    @ConfigFieldSerializer(ItemCodeSerializer.class)
    public ItemStack test5 = new ItemStack(1, (short) 2);
    
}
```
### Saved config.yml

``` yaml
test1: '111'
ConfigTest: '333'
test4: 'world, 0, 0, 0, 0, 0'
test5: '1:2'
```


### Reverse loading of config.yml will change the value of the variable.
