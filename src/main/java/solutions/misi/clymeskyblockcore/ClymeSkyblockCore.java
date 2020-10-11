package solutions.misi.clymeskyblockcore;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.Setter;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.JedisPool;
import solutions.misi.clymeskyblockcore.commands.*;
import solutions.misi.clymeskyblockcore.data.DataManager;
import solutions.misi.clymeskyblockcore.data.vault.economy.ClymeEconomy;
import solutions.misi.clymeskyblockcore.events.*;
import solutions.misi.clymeskyblockcore.gui.islandmenu.*;
import solutions.misi.clymeskyblockcore.gui.staffpanel.StaffpanelDurationGUI;
import solutions.misi.clymeskyblockcore.gui.staffpanel.StaffpanelGUI;
import solutions.misi.clymeskyblockcore.gui.staffpanel.StaffpanelInventoryInspectorGUI;
import solutions.misi.clymeskyblockcore.gui.staffpanel.StaffpanelPlayerGUI;
import solutions.misi.clymeskyblockcore.islands.ClymeIslandManager;
import solutions.misi.clymeskyblockcore.islands.events.IslandCreateListener;
import solutions.misi.clymeskyblockcore.islands.events.IslandUpgradeListener;
import solutions.misi.clymeskyblockcore.islands.settings.IslandSettings;
import solutions.misi.clymeskyblockcore.islands.settings.flags.Flags;
import solutions.misi.clymeskyblockcore.islands.settings.flags.events.CreatureSpawnFlagListener;
import solutions.misi.clymeskyblockcore.player.PlayersHandler;
import solutions.misi.clymeskyblockcore.security.CommandHandler;
import solutions.misi.clymeskyblockcore.security.Screenshare;
import solutions.misi.clymeskyblockcore.utils.ClymeMessage;
import solutions.misi.clymeskyblockcore.utils.TimeUtil;

public class ClymeSkyblockCore extends JavaPlugin {

    //> Classes
    @Getter private static ClymeSkyblockCore instance;
    @Getter private ClymeMessage clymeMessage;
    @Getter private HikariDataSource dataSource;
    @Getter @Setter private JedisPool jedisPool;

    @Getter private DataManager dataManager;
    @Getter private CommandHandler commandHandler;
    @Getter private ClymeIslandManager clymeIslandManager;
    @Getter private IslandSettings islandSettings;
    @Getter private Flags flags;
    @Getter private PlayersHandler playersHandler;
    @Getter private TimeUtil timeUtil;
    @Getter private Screenshare screenshare;

    @Getter private IslandGUI islandGUI;
    @Getter private SpawnerValuesGUI spawnerValuesGUI;
    @Getter private IslandSettingsGUI islandSettingsGUI;
    @Getter private IslandMembersGUI islandMembersGUI;
    @Getter private IslandCreationGUI islandCreationGUI;
    @Getter private StaffpanelGUI staffpanelGUI;
    @Getter private StaffpanelPlayerGUI staffpanelPlayerGUI;
    @Getter private StaffpanelDurationGUI staffpanelDurationGUI;
    @Getter private StaffpanelInventoryInspectorGUI staffpanelInventoryInspectorGUI;

    @Getter private Economy economy;
    @Getter private Permission permission;
    @Getter private Chat chat;

    @Override
    public void onLoad() {
        loadClasses();
        registerFlags();
    }

    @Override
    public void onEnable() {
        registerEvents();
        registerGUIs();
        loadCommands();
        getDataManager().initializeDatabases();

        setupEconomy();
        setupPermission();
        setupChat();
    }

    @Override
    public void onDisable() {
        getDataManager().closeDatabases();
    }

    private void loadClasses() {
        instance = this;
        clymeMessage = new ClymeMessage();
        dataSource = new HikariDataSource();
        dataManager = new DataManager();
        commandHandler = new CommandHandler();
        clymeIslandManager = new ClymeIslandManager();
        islandSettings = new IslandSettings();
        flags = new Flags();
        playersHandler = new PlayersHandler();
        timeUtil = new TimeUtil();
        screenshare = new Screenshare();

        islandGUI = new IslandGUI();
        spawnerValuesGUI = new SpawnerValuesGUI();
        islandSettingsGUI = new IslandSettingsGUI();
        islandMembersGUI = new IslandMembersGUI();
        islandCreationGUI = new IslandCreationGUI();
        staffpanelGUI = new StaffpanelGUI();
        staffpanelPlayerGUI = new StaffpanelPlayerGUI();
        staffpanelDurationGUI = new StaffpanelDurationGUI();
        staffpanelInventoryInspectorGUI = new StaffpanelInventoryInspectorGUI();
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new Aliases(), this);

        Bukkit.getPluginManager().registerEvents(new CreatureSpawnFlagListener(), this);

        Bukkit.getPluginManager().registerEvents(new PlayerCommandPreprocessListener(), this);
        Bukkit.getPluginManager().registerEvents(new IslandCreateListener(), this);
        Bukkit.getPluginManager().registerEvents(new IslandUpgradeListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new ServerListPingListener(), this);
        Bukkit.getPluginManager().registerEvents(new AsyncPlayerChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerMoveListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerToggleFlightListener(), this);
    }

    private void registerGUIs() {
        Bukkit.getPluginManager().registerEvents(new IslandGUI(), this);
        Bukkit.getPluginManager().registerEvents(new SpawnerValuesGUI(), this);
        Bukkit.getPluginManager().registerEvents(new IslandSettingsGUI(), this);
        Bukkit.getPluginManager().registerEvents(new IslandMembersGUI(), this);
        Bukkit.getPluginManager().registerEvents(new IslandCreationGUI(), this);
        Bukkit.getPluginManager().registerEvents(new StaffpanelGUI(), this);
        Bukkit.getPluginManager().registerEvents(new StaffpanelPlayerGUI(), this);
        Bukkit.getPluginManager().registerEvents(new StaffpanelDurationGUI(), this);
        Bukkit.getPluginManager().registerEvents(new StaffpanelInventoryInspectorGUI(), this);
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

        MoneyCommand moneyCommand = new MoneyCommand();
        getCommand("money").setExecutor(moneyCommand);

        PlaytimeCommand playtimeCommand = new PlaytimeCommand();
        getCommand("playtime").setExecutor(playtimeCommand);

        StaffpanelCommand staffpanelCommand = new StaffpanelCommand();
        getCommand("staffpanel").setExecutor(staffpanelCommand);
    }

    private void setupEconomy() {
        if(getServer().getPluginManager().getPlugin("Vault") == null) return;
        Bukkit.getServicesManager().register(Economy.class, new ClymeEconomy(), getServer().getPluginManager().getPlugin("Vault"), ServicePriority.Normal);
        RegisteredServiceProvider<Economy> serviceProvider = getServer().getServicesManager().getRegistration(Economy.class);
        if(serviceProvider == null) return;
        economy = serviceProvider.getProvider();
    }

    private void setupPermission() {
        if(getServer().getPluginManager().getPlugin("Vault") == null) return;
        RegisteredServiceProvider<Permission> serviceProvider = getServer().getServicesManager().getRegistration(Permission.class);
        if(serviceProvider == null) return;
        permission = serviceProvider.getProvider();
    }

    private void setupChat() {
        if(getServer().getPluginManager().getPlugin("Vault") == null) return;
        RegisteredServiceProvider<Chat> serviceProvider = getServer().getServicesManager().getRegistration(Chat.class);
        if(serviceProvider == null) return;
        chat = serviceProvider.getProvider();
    }
}