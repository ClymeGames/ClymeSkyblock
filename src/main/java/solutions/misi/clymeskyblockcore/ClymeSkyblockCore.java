package solutions.misi.clymeskyblockcore;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import solutions.misi.clymeskyblockcore.commands.SpawnCommand;
import solutions.misi.clymeskyblockcore.events.PlayerCommandPreprocessListener;
import solutions.misi.clymeskyblockcore.guis.islandmenu.IslandGUI;
import solutions.misi.clymeskyblockcore.guis.islandmenu.IslandMembersGUI;
import solutions.misi.clymeskyblockcore.guis.islandmenu.IslandSettingsGUI;
import solutions.misi.clymeskyblockcore.guis.islandmenu.SpawnerValuesGUI;
import solutions.misi.clymeskyblockcore.islands.ClymeIslandManager;
import solutions.misi.clymeskyblockcore.islands.events.IslandCreateListener;
import solutions.misi.clymeskyblockcore.islands.events.IslandUpgradeListener;
import solutions.misi.clymeskyblockcore.islands.settings.IslandSettings;
import solutions.misi.clymeskyblockcore.islands.settings.flags.Flags;
import solutions.misi.clymeskyblockcore.islands.settings.flags.events.CreatureSpawnFlagListener;
import solutions.misi.clymeskyblockcore.security.CommandHandler;
import solutions.misi.clymeskyblockcore.utils.Messages;

public class ClymeSkyblockCore extends JavaPlugin {

    //> Classes
    @Getter private static ClymeSkyblockCore instance;
    @Getter private Messages messages;
    @Getter private CommandHandler commandHandler;
    @Getter private ClymeIslandManager clymeIslandManager;
    @Getter private IslandSettings islandSettings;
    @Getter private Flags flags;

    @Getter private IslandGUI islandGUI;
    @Getter private SpawnerValuesGUI spawnerValuesGUI;
    @Getter private IslandSettingsGUI islandSettingsGUI;
    @Getter private IslandMembersGUI islandMembersGUI;

    @Override
    public void onLoad() {
        loadClasses();
        registerFlags();
    }

    @Override
    public void onEnable() {
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
        commandHandler = new CommandHandler();
        clymeIslandManager = new ClymeIslandManager();
        islandSettings = new IslandSettings();
        flags = new Flags();

        islandGUI = new IslandGUI();
        spawnerValuesGUI = new SpawnerValuesGUI();
        islandSettingsGUI = new IslandSettingsGUI();
        islandMembersGUI = new IslandMembersGUI();
    }

    private void loadFiles() {
        saveDefaultConfig();
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new PlayerCommandPreprocessListener(), this);
        Bukkit.getPluginManager().registerEvents(new IslandCreateListener(), this);
        Bukkit.getPluginManager().registerEvents(new IslandUpgradeListener(), this);

        //> Flags
        Bukkit.getPluginManager().registerEvents(new CreatureSpawnFlagListener(), this);
    }

    private void registerGUIs() {
        Bukkit.getPluginManager().registerEvents(new IslandGUI(), this);
        Bukkit.getPluginManager().registerEvents(new SpawnerValuesGUI(), this);
        Bukkit.getPluginManager().registerEvents(new IslandSettingsGUI(), this);
        Bukkit.getPluginManager().registerEvents(new IslandMembersGUI(), this);
    }

    private void registerFlags() {
        FlagRegistry flagRegistry = WorldGuard.getInstance().getFlagRegistry();

        //> Animals Spawning Flag
        StateFlag animalsSpawningFlag = new StateFlag("animals-spawning", true);
        flagRegistry.register(animalsSpawningFlag);
        getFlags().setAnimalsSpawningFlag(animalsSpawningFlag);

        //> Monsters Spawning Flag
        StateFlag monstersSpawningFlag = new StateFlag("monsters-spawning", true);
        flagRegistry.register(monstersSpawningFlag);
        getFlags().setMonstersSpawningFlag(monstersSpawningFlag);
    }

    private void loadCommands() {
        SpawnCommand spawnCommand = new SpawnCommand();
        getCommand("spawn").setExecutor(spawnCommand);
    }
}