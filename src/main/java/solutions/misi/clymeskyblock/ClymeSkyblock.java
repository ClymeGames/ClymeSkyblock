package solutions.misi.clymeskyblock;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import solutions.misi.clymeskyblock.events.CreatureSpawnListener;

public class ClymeSkyblock extends JavaPlugin {

    @Getter
    private static ClymeSkyblock instance;

    @Override
    public void onEnable() {
        instance = this;

        loadClasses();
        loadFiles();
        registerEvents();
        loadCommands();
    }

    @Override
    public void onDisable() {

    }

    private void loadClasses() {

    }

    private void loadFiles() {

    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new CreatureSpawnListener(), this);
    }

    private void loadCommands() {

    }
}