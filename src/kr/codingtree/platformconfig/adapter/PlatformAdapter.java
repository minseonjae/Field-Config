package kr.codingtree.platformconfig.adapter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.util.List;

@RequiredArgsConstructor
public abstract class PlatformAdapter {

    @Getter
    protected final File file;

    public abstract void load();
    public abstract void save();

    public abstract String saveToString();

    public abstract void copyDefaults(Object plugin, String resourcePath);
    public abstract void clear();

    public abstract void addDefault(String path, Object value);
    public abstract Object getDefault(String path);
    public abstract boolean isSet(String path);
    public abstract void set(String path, Object value);
    public abstract Object get(String path);
    public abstract List<String> getKeys();
    public abstract List<String> getKeys(String path);
    public abstract String getString(String path);
    public abstract String getString(String path, String def);
    public abstract boolean getBoolean(String path);
    public abstract boolean getBoolean(String path, boolean def);
    public abstract int getInt(String path);
    public abstract int getInt(String path, int def);
    public abstract long getLong(String path);
    public abstract long getLong(String path, long def);
    public abstract double getDouble(String path);
    public abstract double getDouble(String path, double def);
    public abstract List<?> getList(String path);
    public abstract List<?> getList(String path, List<?> def);
    public abstract List<String> getStringList(String path);
    public abstract List<Boolean> getBooleanList(String path);
    public abstract List<Byte> getByteList(String path);
    public abstract List<Character> getCharacterList(String path);
    public abstract List<Integer> getIntegerList(String path);
    public abstract List<Float> getFloatList(String path);
    public abstract List<Double> getDoubleList(String path);
    public abstract List<Short> getShortList(String path);
    public abstract List<Long> getLongList(String path);

}
