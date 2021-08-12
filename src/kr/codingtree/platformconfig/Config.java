package kr.codingtree.platformconfig;

import kr.codingtree.platformconfig.adapter.PlatformAdapter;
import kr.codingtree.platformconfig.adapter.bukkit.BukkitAdapter;
import kr.codingtree.platformconfig.adapter.bungee.BungeeAdapter;
import lombok.Getter;

import java.io.File;
import java.util.List;

public class Config {

    @Getter
    protected File file;

    protected PlatformAdapter config;

    public Config(File file) {
        this.file = file;
        try {
            if (isClass("net.md_5.bungee.BungeeCord")) config = new BungeeAdapter(file);
            else if (isClass("org.bukkit.Bukkit")) config = new BukkitAdapter(file);
            else throw new Exception("Could not setup!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Config(File path, String name) {
        this(new File(path, name));
    }

    public Config load() {
        config.load();
        return this;
    }

    public Config save() {
        config.save();
        return this;
    }

    public String saveToString() {
        return config.saveToString();
    }

    public void copyDefaults(Object plugin) {
        copyDefaults(plugin, file.getName());
    }

    public void copyDefaults(Object plugin, String resourcePath) {
        config.copyDefaults(plugin, resourcePath);
    }

    public void clear() {
        config.clear();
    }

    public void addDefault(String path, Object value) {
        config.addDefault(path, value);
    }

    public Object getDefault(String path) {
        return config.getDefault(path);
    }

    public boolean isSet(String path) {
        return config.isSet(path);
    }

    public void set(String path, Object value) {
        config.set(path, value);
    }

    public Object get(String path) {
        return config.get(path);
    }

    public List<String> getKeys() {
        return  config.getKeys();
    }

    public List<String> getKeys(String path) {
        return config.getKeys(path);
    }


    public String getString(String path) {
        return config.getString(path);
    }


    public String getString(String path, String def) {
        return config.getString(path, def);
    }

    public boolean getBoolean(String path) {
        return config.getBoolean(path);
    }

    public boolean getBoolean(String path, boolean def) {
        return config.getBoolean(path, def);
    }

    public int getInt(String path) {
        return config.getInt(path);
    }

    public int getInt(String path, int def) {
        return config.getInt(path, def);
    }

    public long getLong(String path) {
        return config.getLong(path);
    }

    public long getLong(String path, long def) {
        return config.getLong(path, def);
    }

    public double getDouble(String path) {
        return config.getDouble(path);
    }

    public double getDouble(String path, double def) {
        return config.getDouble(path, def);
    }

    public List<?> getList(String path) {
        return config.getList(path);
    }

    public List<?> getList(String path, List<?> def) {
        return config.getList(path, def);
    }

    public List<String> getStringList(String path) {
        return config.getStringList(path);
    }

    public List<Boolean> getBooleanList(String path) {
        return config.getBooleanList(path);
    }

    public List<Byte> getByteList(String path) {
        return config.getByteList(path);
    }

    public List<Character> getCharacterList(String path) {
        return config.getCharacterList(path);
    }

    public List<Integer> getIntegerList(String path) {
        return config.getIntegerList(path);
    }

    public List<Float> getFloatList(String path) {
        return config.getFloatList(path);
    }

    public List<Double> getDoubleList(String path) {
        return config.getDoubleList(path);
    }

    public List<Short> getShortList(String path) {
        return null;
    }

    public List<Long> getLongList(String path) {
        return null;
    }

    private boolean isClass(String path) {
        try {
            Class.forName(path);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
