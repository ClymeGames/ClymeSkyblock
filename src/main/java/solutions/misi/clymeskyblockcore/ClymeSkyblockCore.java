package solutions.misi.clymeskyblockcore;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import solutions.misi.clymeskyblockcore.commands.SpawnCommand;
import solutions.misi.clymeskyblockcore.events.CreatureSpawnListener;
import solutions.misi.clymeskyblockcore.events.PlayerCommandPreprocessListener;
import solutions.misi.clymeskyblockcore.guis.SpawnerValuesGUI;
import solutions.misi.clymeskyblockcore.guis.islandmenu.IslandGUI;
import solutions.misi.clymeskyblockcore.islands.ClymeIslandManager;
import solutions.misi.clymeskyblockcore.islands.events.IslandCreateListener;
import solutions.misi.clymeskyblockcore.islands.settings.IslandSettings;
import solutions.misi.clymeskyblockcore.islands.settings.flags.PlayerTeleportListener;
import solutions.misi.clymeskyblockcore.security.CommandHandler;
import solutions.misi.clymeskyblockcore.utils.Messages;

public class ClymeSkyblockCore extends JavaPlugin {

    //> Classes
    @Getter private static ClymeSkyblockCore instance;
    @Getter private Messages messages;
    @Getter private IslandGUI islandGUI;
    @Getter private SpawnerValuesGUI spawnerValuesGUI;
    @Getter private CommandHandler commandHandler;
    @Getter private ClymeIslandManager clymeIslandManager;
    @Getter private IslandSettings islandSettings;

    //> Flags
    @Getter @Setter private StateFlag animalsSpawningFlag;
    @Getter @Setter private StateFlag monstersSpawningFlag;
    @Getter @Setter private StateFlag visitorsFlag;

    @Override
    public void onLoad() {
        registerFlags();
    }

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
        commandHandler = new CommandHandler();
        clymeIslandManager = new ClymeIslandManager();
        islandSettings = new IslandSettings();
    }

    private void loadFiles() {
        saveDefaultConfig();
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new CreatureSpawnListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerCommandPreprocessListener(), this);
        Bukkit.getPluginManager().registerEvents(new IslandCreateListener(), this);

        //> Flags
        Bukkit.getPluginManager().registerEvents(new CreatureSpawnListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerTeleportListener(), this);
    }

    private void registerGUIs() {
        Bukkit.getPluginManager().registerEvents(new IslandGUI(), this);
        Bukkit.getPluginManager().registerEvents(new SpawnerValuesGUI(), this);
    }

    private void registerFlags() {
        FlagRegistry flagRegistry = WorldGuard.getInstance().getFlagRegistry();

        //> Animals Spawning Flag
        StateFlag animalsSpawningFlag = new StateFlag("animals-spawning", true);
        flagRegistry.register(animalsSpawningFlag);
        setAnimalsSpawningFlag(animalsSpawningFlag);

        //> Monsters Spawning Flag
        StateFlag monstersSpawningFlag = new StateFlag("monsters-spawning", true);
        flagRegistry.register(monstersSpawningFlag);
        setMonstersSpawningFlag(monstersSpawningFlag);

        //> Visitors Flag
        StateFlag visitorsFlag = new StateFlag("visitors", true);
        flagRegistry.register(visitorsFlag);
        setVisitorsFlag(visitorsFlag);
    }

    private void loadCommands() {
        SpawnCommand spawnCommand = new SpawnCommand();
        getCommand("spawn").setExecutor(spawnCommand);
    }
}