package kr.codingtree.plugin.example;

import net.md_5.bungee.api.plugin.Plugin;

public class TestGPlugin extends Plugin {
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