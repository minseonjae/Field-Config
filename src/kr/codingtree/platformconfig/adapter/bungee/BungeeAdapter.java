package kr.codingtree.platformconfig.adapter.bungee;

import kr.codingtree.platformconfig.adapter.PlatformAdapter;
import lombok.SneakyThrows;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BungeeAdapter extends PlatformAdapter {

    private Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load("");

    public BungeeAdapter(File file) {
        super(file);
    }

    @SneakyThrows(Exception.class)
    private boolean createFile() {
        if (file.exists()) return false;

        file.getParentFile().mkdirs();

        file.createNewFile();

        return true;
    }

    @Override
    @SneakyThrows(Exception.class)
    public void load() {
        if (file.exists()) config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
    }

    @Override
    @SneakyThrows(Exception.class)
    public void save() {
        createFile();
        ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
    }

    @Override
    public String saveToString() {
        return null;
    }

    @Override
    public void copyDefaults(Object plugin, String resourcePath) {
        if (plugin instanceof Plugin) {
            Configuration defaultConfig = ConfigurationProvider.getProvider(YamlConfiguration.class).load(((Plugin) plugin).getResourceAsStream(resourcePath));

            for (String key : defaultConfig.getKeys()) {
                if (isSet(key)) continue;
                config.set(key, defaultConfig.get(key));
            }
        }
    }

    @Override
    public void clear() {
        for (String key : config.getKeys()) {
            config.set(key, null);
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
        return config.get(path) != null;
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
        return new ArrayList(config.getKeys());
    }

    @Override
    public List<String> getKeys(String path) {
        return new ArrayList(config.getSection(path).getKeys());
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
        return config.getCharList(path);
    }

    @Override
    public List<Integer> getIntegerList(String path) {
        return config.getIntList(path);
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