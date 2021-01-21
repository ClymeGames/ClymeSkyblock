package solutions.misi.clymeskyblockcore;

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
import solutions.misi.clymeskyblockcore.commands.donator.*;
import solutions.misi.clymeskyblockcore.commands.message.MessageCommand;
import solutions.misi.clymeskyblockcore.commands.message.MsgToggleCommand;
import solutions.misi.clymeskyblockcore.commands.message.ReplyCommand;
import solutions.misi.clymeskyblockcore.commands.message.SocialSpyCommand;
import solutions.misi.clymeskyblockcore.commands.staff.*;
import solutions.misi.clymeskyblockcore.commands.teleport.*;
import solutions.misi.clymeskyblockcore.commands.warps.*;
import solutions.misi.clymeskyblockcore.data.DataManager;
import solutions.misi.clymeskyblockcore.data.vault.economy.ClymeEconomy;
import solutions.misi.clymeskyblockcore.events.*;
import solutions.misi.clymeskyblockcore.gui.DisposalGUI;
import solutions.misi.clymeskyblockcore.gui.HomeGUI;
import solutions.misi.clymeskyblockcore.gui.islandmenu.IslandCreationGUI;
import solutions.misi.clymeskyblockcore.gui.islandmenu.IslandGUI;
import solutions.misi.clymeskyblockcore.gui.islandmenu.IslandMembersGUI;
import solutions.misi.clymeskyblockcore.gui.menu.MenuGUI;
import solutions.misi.clymeskyblockcore.gui.shop.MinionShopGUI;
import solutions.misi.clymeskyblockcore.gui.staffpanel.StaffpanelDurationGUI;
import solutions.misi.clymeskyblockcore.gui.staffpanel.StaffpanelGUI;
import solutions.misi.clymeskyblockcore.gui.staffpanel.StaffpanelInventoryInspectorGUI;
import solutions.misi.clymeskyblockcore.gui.staffpanel.StaffpanelPlayerGUI;
import solutions.misi.clymeskyblockcore.invites.InviteManager;
import solutions.misi.clymeskyblockcore.leaderboards.PlaytimeLeaderboard;
import solutions.misi.clymeskyblockcore.leaderboards.SugarcaneLeaderboard;
import solutions.misi.clymeskyblockcore.player.PlayersHandler;
import solutions.misi.clymeskyblockcore.security.CombatLog;
import solutions.misi.clymeskyblockcore.security.CommandHandler;
import solutions.misi.clymeskyblockcore.security.Screenshare;
import solutions.misi.clymeskyblockcore.utils.*;

public class ClymeSkyblockCore extends JavaPlugin {

    @Getter @Setter private boolean restarting;

    //> Classes
    @Getter private static ClymeSkyblockCore instance;
    @Getter private ClymeMessage clymeMessage;
    @Getter private HikariDataSource dataSource;
    @Getter @Setter private JedisPool jedisPool;

    @Getter private DataManager dataManager;
    @Getter private CommandHandler commandHandler;
    @Getter private PlayersHandler playersHandler;
    @Getter private TimeUtil timeUtil;
    @Getter private Screenshare screenshare;
    @Getter private CommandsUtil commandUtil;
    @Getter private CombatLog combatLog;
    @Getter private ExperienceUtils experienceUtils;
    @Getter private PlaytimeLeaderboard playtimeLeaderboard;
    @Getter private SugarcaneLeaderboard sugarcaneLeaderboard;
    @Getter private NumberFormatter numberFormatter;
    @Getter private InviteManager inviteManager;

    @Getter private IslandGUI islandGUI;
    @Getter private IslandMembersGUI islandMembersGUI;
    @Getter private IslandCreationGUI islandCreationGUI;
    @Getter private StaffpanelGUI staffpanelGUI;
    @Getter private StaffpanelPlayerGUI staffpanelPlayerGUI;
    @Getter private StaffpanelDurationGUI staffpanelDurationGUI;
    @Getter private StaffpanelInventoryInspectorGUI staffpanelInventoryInspectorGUI;
    @Getter private MinionShopGUI minionShopGUI;
    @Getter private MenuGUI menuGUI;
    @Getter private HomeGUI homeGUI;
    @Getter private DisposalGUI disposalGUI;

    @Getter private Economy economy;
    @Getter private Permission permission;
    @Getter private Chat chat;

    @Override
    public void onLoad() {
        setRestarting(true);
        loadClasses();
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        registerEvents();
        registerGUIs();
        loadCommands();
        getDataManager().initializeDatabases();

        setupEconomy();
        setupPermission();
        setupChat();

        playtimeLeaderboard.startCalculation();
        sugarcaneLeaderboard.startCalculation();

        clymeMessage.startAnnouncements();

        setRestarting(false);
    }

    @Override
    public void onDisable() {
        setRestarting(true);
        getDataManager().closeDatabases();
    }

    private void loadClasses() {
        instance = this;
        clymeMessage = new ClymeMessage();
        dataSource = new HikariDataSource();
        dataManager = new DataManager();
        commandHandler = new CommandHandler();
        playersHandler = new PlayersHandler();
        timeUtil = new TimeUtil();
        screenshare = new Screenshare();
        commandUtil = new CommandsUtil();
        combatLog = new CombatLog();
        experienceUtils = new ExperienceUtils();
        playtimeLeaderboard = new PlaytimeLeaderboard();
        sugarcaneLeaderboard = new SugarcaneLeaderboard();
        numberFormatter = new NumberFormatter();
        inviteManager = new InviteManager();

        islandGUI = new IslandGUI();
        islandMembersGUI = new IslandMembersGUI();
        islandCreationGUI = new IslandCreationGUI();
        staffpanelGUI = new StaffpanelGUI();
        staffpanelPlayerGUI = new StaffpanelPlayerGUI();
        staffpanelDurationGUI = new StaffpanelDurationGUI();
        staffpanelInventoryInspectorGUI = new StaffpanelInventoryInspectorGUI();
        minionShopGUI = new MinionShopGUI();
        menuGUI = new MenuGUI();
        homeGUI = new HomeGUI();
        disposalGUI = new DisposalGUI();
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new Aliases(), this);

        Bukkit.getPluginManager().registerEvents(new PlayerCommandPreprocessListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new AsyncPlayerChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerMoveListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerToggleFlightListener(), this);
        Bukkit.getPluginManager().registerEvents(new EntityDamageByEntityListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerTeleportListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerRespawnListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new SignChangeListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractAtEntityListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerPortalListener(), this);
    }

    private void registerGUIs() {
        Bukkit.getPluginManager().registerEvents(new IslandGUI(), this);
        Bukkit.getPluginManager().registerEvents(new IslandMembersGUI(), this);
        Bukkit.getPluginManager().registerEvents(new IslandCreationGUI(), this);
        Bukkit.getPluginManager().registerEvents(new StaffpanelGUI(), this);
        Bukkit.getPluginManager().registerEvents(new StaffpanelPlayerGUI(), this);
        Bukkit.getPluginManager().registerEvents(new StaffpanelDurationGUI(), this);
        Bukkit.getPluginManager().registerEvents(new StaffpanelInventoryInspectorGUI(), this);
        Bukkit.getPluginManager().registerEvents(new MinionShopGUI(), this);
        Bukkit.getPluginManager().registerEvents(new MenuGUI(), this);
        Bukkit.getPluginManager().registerEvents(new HomeGUI(), this);
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

        MinionsCommand minionsCommand = new MinionsCommand();
        getCommand("minions").setExecutor(minionsCommand);

        MenuCommand menuCommand = new MenuCommand();
        getCommand("menu").setExecutor(menuCommand);

        CrateCommand crateCommand = new CrateCommand();
        getCommand("crate").setExecutor(crateCommand);

        FarmingCommand farmingCommand = new FarmingCommand();
        getCommand("farming").setExecutor(farmingCommand);

        MessageCommand messageCommand = new MessageCommand();
        getCommand("message").setExecutor(messageCommand);

        ReplyCommand replyCommand = new ReplyCommand();
        getCommand("reply").setExecutor(replyCommand);

        TpaCommand tpaCommand = new TpaCommand();
        getCommand("tpa").setExecutor(tpaCommand);

        TpacceptCommand tpacceptCommand = new TpacceptCommand();
        getCommand("tpaccept").setExecutor(tpacceptCommand);

        TpdenyCommand tpdenyCommand = new TpdenyCommand();
        getCommand("tpdeny").setExecutor(tpdenyCommand);

        TpahereCommand tpahereCommand = new TpahereCommand();
        getCommand("tpahere").setExecutor(tpahereCommand);

        HomeCommand homeCommand = new HomeCommand();
        getCommand("home").setExecutor(homeCommand);

        GamemodeCommand gamemodeCommand = new GamemodeCommand();
        getCommand("gamemode").setExecutor(gamemodeCommand);

        PvPCommand pvpCommand = new PvPCommand();
        getCommand("pvp").setExecutor(pvpCommand);

        DayCommand dayCommand = new DayCommand();
        getCommand("day").setExecutor(dayCommand);

        NightCommand nightCommand = new NightCommand();
        getCommand("night").setExecutor(nightCommand);

        SunCommand sunCommand = new SunCommand();
        getCommand("sun").setExecutor(sunCommand);

        RainCommand rainCommand = new RainCommand();
        getCommand("rain").setExecutor(rainCommand);

        ClymeCrystalCommand clymeCrystalCommand = new ClymeCrystalCommand();
        getCommand("clymecrystal").setExecutor(clymeCrystalCommand);

        CosmeticCommand cosmeticCommand = new CosmeticCommand();
        getCommand("cosmetic").setExecutor(cosmeticCommand);

        BaltopCommand baltopCommand = new BaltopCommand();
        getCommand("baltop").setExecutor(baltopCommand);

        DonationCommand donationCommand = new DonationCommand();
        getCommand("donation").setExecutor(donationCommand);

        PayoutsCommand payoutsCommand = new PayoutsCommand();
        getCommand("payouts").setExecutor(payoutsCommand);

        TpCommand tpCommand = new TpCommand();
        getCommand("tp").setExecutor(tpCommand);

        TphereCommand tphereCommand = new TphereCommand();
        getCommand("tphere").setExecutor(tphereCommand);

        FlyCommand flyCommand = new FlyCommand();
        getCommand("fly").setExecutor(flyCommand);

        CraftCommand craftCommand = new CraftCommand();
        getCommand("craft").setExecutor(craftCommand);

        AnvilCommand anvilCommand = new AnvilCommand();
        getCommand("anvil").setExecutor(anvilCommand);

        EnderchestCommand enderchestCommand = new EnderchestCommand();
        getCommand("enderchest").setExecutor(enderchestCommand);

        NearCommand nearCommand = new NearCommand();
        getCommand("near").setExecutor(nearCommand);

        BlocksCommand blocksCommand = new BlocksCommand();
        getCommand("blocks").setExecutor(blocksCommand);

        DisposalCommand disposalCommand = new DisposalCommand();
        getCommand("disposal").setExecutor(disposalCommand);

        FeedCommand feedCommand = new FeedCommand();
        getCommand("feed").setExecutor(feedCommand);

        NightvisionCommand nightvisionCommand = new NightvisionCommand();
        getCommand("nightvision").setExecutor(nightvisionCommand);

        FixCommand fixCommand = new FixCommand();
        getCommand("fix").setExecutor(fixCommand);

        SpeedCommand speedCommand = new SpeedCommand();
        getCommand("speed").setExecutor(speedCommand);

        TpToggleCommand tpToggleCommand = new TpToggleCommand();
        getCommand("tptoggle").setExecutor(tpToggleCommand);

        MsgToggleCommand msgToggleCommand = new MsgToggleCommand();
        getCommand("msgtoggle").setExecutor(msgToggleCommand);

        RenameCommand renameCommand = new RenameCommand();
        getCommand("rename").setExecutor(renameCommand);

        HealCommand healCommand = new HealCommand();
        getCommand("heal").setExecutor(healCommand);

        ClearinventoryCommand clearinventoryCommand = new ClearinventoryCommand();
        getCommand("clearinventory").setExecutor(clearinventoryCommand);

        CartographytableCommand cartographytableCommand = new CartographytableCommand();
        getCommand("cartographytable").setExecutor(cartographytableCommand);

        GrindstoneCommand grindstoneCommand = new GrindstoneCommand();
        getCommand("grindstone").setExecutor(grindstoneCommand);

        LoomCommand loomCommand = new LoomCommand();
        getCommand("loom").setExecutor(loomCommand);

        SmithingtableCommand smithingtableCommand = new SmithingtableCommand();
        getCommand("smithingtable").setExecutor(smithingtableCommand);

        StonecutterCommand stonecutterCommand = new StonecutterCommand();
        getCommand("stonecutter").setExecutor(stonecutterCommand);

        PlayertimeCommand playertimeCommand = new PlayertimeCommand();
        getCommand("playertime").setExecutor(playertimeCommand);

        XpbottleCommand xpbottleCommand = new XpbottleCommand();
        getCommand("xpbottle").setExecutor(xpbottleCommand);

        BroadcastCommand broadcastCommand = new BroadcastCommand();
        getCommand("broadcast").setExecutor(broadcastCommand);

        VanishCommand vanishCommand = new VanishCommand();
        getCommand("vanish").setExecutor(vanishCommand);

        UpdateCommand updateCommand = new UpdateCommand();
        getCommand("update").setExecutor(updateCommand);

        SocialSpyCommand socialSpyCommand = new SocialSpyCommand();
        getCommand("socialspy").setExecutor(socialSpyCommand);

        InviteCommand inviteCommand = new InviteCommand();
        getCommand("invite").setExecutor(inviteCommand);

        RulesCommand rulesCommand = new RulesCommand();
        getCommand("rules").setExecutor(rulesCommand);

        StaffCommand staffCommand = new StaffCommand();
        getCommand("staff").setExecutor(staffCommand);

        NetherCommand netherCommand = new NetherCommand();
        getCommand("nether").setExecutor(netherCommand);

        ReloadPlayer reloadPlayerCommand = new ReloadPlayer();
        getCommand("reloadplayer").setExecutor(reloadPlayerCommand);

        NicknameCommand nicknameCommand = new NicknameCommand();
        getCommand("nickname").setExecutor(nicknameCommand);

        RealnameCommand realnameCommand = new RealnameCommand();
        getCommand("realname").setExecutor(realnameCommand);

        GodCommand godCommand = new GodCommand();
        getCommand("god").setExecutor(godCommand);

        BackCommand backCommand = new BackCommand();
        getCommand("back").setExecutor(backCommand);

        EnchantCommand enchantCommand = new EnchantCommand();
        getCommand("enchant").setExecutor(enchantCommand);

        SugarcaneCommand sugarcaneCommand = new SugarcaneCommand();
        getCommand("sugarcane").setExecutor(sugarcaneCommand);
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