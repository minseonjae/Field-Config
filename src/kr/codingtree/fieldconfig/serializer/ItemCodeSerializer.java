package kr.codingtree.fieldconfig.serializer;

import kr.codingtree.fieldconfig.ConfigFieldSerializer.ConfigSerializer;
import org.bukkit.inventory.ItemStack;

public class ItemCodeSerializer implements ConfigSerializer<ItemStack> {
    @Override
    public String serializer(ItemStack value) {
        return value.getTypeId() + (value.getDurability() != 0 ? ":" + value.getDurability() : "");
    }

    @Override
    public ItemStack deserializer(String value) {
        try {
            if (value.contains(":")) {
                String[] split = value.split(":");
                return new ItemStack(Integer.parseInt(split[0]), 1, Short.parseShort(split[1]));
            } else return new ItemStack(Integer.parseInt(value));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
