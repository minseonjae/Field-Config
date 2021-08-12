package kr.codingtree.platformconfig.adapter.bukkit;

import kr.codingtree.platformconfig.adapter.PlatformAdapter;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BukkitAdapter extends PlatformAdapter {

    private YamlConfiguration config = new YamlConfiguration();

    public BukkitAdapter(File file) {
        super(file);
    }

    @Override
    @SneakyThrows(Exception.class)
    public void load() {
        if (file.exists()) config.load(file);
    }

    @Override
    @SneakyThrows(Exception.class)
    public void save() {
        config.save(file);
    }

    @Override
    public String saveToString() {
        return config.saveToString();
    }

    @Override
    @SneakyThrows(Exception.class)
    public void copyDefaults(Object plugin, String resourcePath) {
        if (plugin instanceof JavaPlugin) {
            @Cleanup InputStreamReader isr = new InputStreamReader(((JavaPlugin) plugin).getResource(resourcePath), "UTF-8");
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(isr);

            for (String path : defaultConfig.getRoot().getKeys(false)) {
                if (isSet(path)) continue;

                set(path, defaultConfig.get(path));
            }
        }
    }

    public void clear() {
        for (String path : config.getRoot().getKeys(false)) {
            config.set(path, null);
        }
    }

    @Override
    public void addDefault(String path, Object value) {
        if (isSet(path)) return;
        config.set(path, value);
    }

    @Override
    public Object getDefault(String path) {
        return config.get(path);
    }

    @Override
    public boolean isSet(String path) {
        return config.isSet(path);
    }

    @Override
    public void set(String path, Object value) {
        config.set(path, value);
    }

    @Override
    public Object get(String path) {
        return config.get(path);
    }

    @Override
    public List<String> getKeys() {
        return new ArrayList(config.getKeys(false));
    }

    @Override
    public List<String> getKeys(String path) {
        return new ArrayList(config.getConfigurationSection(path).getKeys(false));
    }

    @Override
    public String getString(String path) {
        return config.getString(path);
    }

    @Override
    public String getString(String path, String def) {
        return config.getString(path, def);
    }

    @Override
    public boolean getBoolean(String path) {
        return config.getBoolean(path);
    }

    @Override
    public boolean getBoolean(String path, boolean def) {
        return config.getBoolean(path, def);
    }

    @Override
    public int getInt(String path) {
        return config.getInt(path);
    }

    @Override
    public int getInt(String path, int def) {
        return config.getInt(path, def);
    }

    @Override
    public long getLong(String path) {
        return config.getLong(path);
    }

    @Override
    public long getLong(String path, long def) {
        return config.getLong(path, def);
    }

    @Override
    public double getDouble(String path) {
        return config.getDouble(path);
    }

    @Override
    public double getDouble(String path, double def) {
        return config.getDouble(path, def);
    }

    @Override
    public List<?> getList(String path) {
        return config.getList(path);
    }

    @Override
    public List<?> getList(String path, List<?> def) {
        return config.getList(path, def);
    }

    @Override
    public List<String> getStringList(String path) {
        return config.getStringList(path);
    }

    @Override
    public List<Boolean> getBooleanList(String path) {
        return config.getBooleanList(path);
    }

    @Override
    public List<Byte> getByteList(String path) {
        return config.getByteList(path);
    }

    @Override
    public List<Character> getCharacterList(String path) {
        return config.getCharacterList(path);
    }

    @Override
    public List<Integer> getIntegerList(String path) {
        return config.getIntegerList(path);
    }

    @Override
    public List<Float> getFloatList(String path) {
        return config.getFloatList(path);
    }

    @Override
    public List<Double> getDoubleList(String path) {
        return config.getDoubleList(path);
    }

    @Override
    public List<Short> getShortList(String path) {
        return config.getShortList(path);
    }

    @Override
    public List<Long> getLongList(String path) {
        return config.getLongList(path);
    }
}
