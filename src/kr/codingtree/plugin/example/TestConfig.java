package kr.codingtree.plugin.example;

import kr.codingtree.fieldconfig.ConfigFieldExclude;
import kr.codingtree.fieldconfig.ConfigFieldName;
import kr.codingtree.fieldconfig.ConfigFieldSerializer;
import kr.codingtree.fieldconfig.FieldConfig;
import kr.codingtree.fieldconfig.serializer.ItemCodeSerializer;
import kr.codingtree.fieldconfig.serializer.LocationSerializer;
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
