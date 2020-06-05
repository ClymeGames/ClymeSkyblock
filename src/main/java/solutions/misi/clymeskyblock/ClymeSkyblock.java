package solutions.misi.clymeskyblock;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import solutions.misi.clymeskyblock.commands.SpawnCommand;
import solutions.misi.clymeskyblock.events.CreatureSpawnListener;
import solutions.misi.clymeskyblock.events.PlayerCommandPreprocessListener;
import solutions.misi.clymeskyblock.guis.IslandGUI;
import solutions.misi.clymeskyblock.guis.SpawnerValuesGUI;
import solutions.misi.clymeskyblock.utils.Messages;

public class ClymeSkyblock extends JavaPlugin {

    @Getter private static ClymeSkyblock instance;
    @Getter private Messages messages;
    @Getter private IslandGUI islandGUI;
    @Getter private SpawnerValuesGUI spawnerValuesGUI;

    @Override
    public void onEnable() {
        loadClasses();
        loadFiles();
        registerEvents();
        registerGUIs();
        loadCommands();
    }

    @Override
    public void onDisable() {

    }

    private void loadClasses() {
        instance = this;
        messages = new Messages();
        islandGUI = new IslandGUI();
        spawnerValuesGUI = new SpawnerValuesGUI();
    }

    private void loadFiles() {
        saveDefaultConfig();
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new CreatureSpawnListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerCommandPreprocessListener(), this);
    }

    private void registerGUIs() {
        Bukkit.getPluginManager().registerEvents(new IslandGUI(), this);
        Bukkit.getPluginManager().registerEvents(new SpawnerValuesGUI(), this);
    }


    private void loadCommands() {
        SpawnCommand spawnCommand = new SpawnCommand();
        getCommand("spawn").setExecutor(spawnCommand);
    }
}