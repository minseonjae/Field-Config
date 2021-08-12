package kr.codingtree.fieldconfig.serializer;

import kr.codingtree.fieldconfig.ConfigFieldSerializer.ConfigSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationSerializer implements ConfigSerializer<Location> {
    @Override
    public String serializer(Location value) {
        return value.getWorld().getName() + ", " + value.getX() + ", " + value.getY() + ", " + value.getZ() + ", " + value.getYaw() + ", " + value.getPitch();
    }
    @Override
    public Location deserializer(String value) {
        String[] split = value.split(", ");

        World world = Bukkit.getWorld(split[0]);
        double x = Double.parseDouble(split[1]);
        double y = Double.parseDouble(split[2]);
        double z = Double.parseDouble(split[3]);
        float yaw = Float.parseFloat(split[4]);
        float pitch = Float.parseFloat(split[5]);

        return new Location(world, x, y, z, yaw, pitch);
    }
}
