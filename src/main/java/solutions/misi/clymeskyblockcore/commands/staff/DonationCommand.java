package solutions.misi.clymeskyblockcore.commands.staff;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;
import solutions.misi.clymeskyblockcore.player.ClymePlayer;
import solutions.misi.clymeskyblockcore.utils.ClymeChatColor;

public class DonationCommand implements CommandExecutor {

    //> Usage: /donation <player>

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer((((Player) sender).getPlayer()));
            clymePlayer.sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getNoPermission());
            return false;
        }

        if(args.length != 1) {
            //> Wrong usage
            Bukkit.getConsoleSender().sendMessage(ClymeSkyblockCore.getInstance().getClymeMessage().getPrefix() + ClymeChatColor.ERROR() + "Wrong usage! Please use " + ClymeChatColor.SECONDARY() + "/donation <player>" + ClymeChatColor.ERROR() + "!");
            return false;
        }

        String targetName = args[0];

        for(Player player : Bukkit.getOnlinePlayers()) {
            ClymePlayer clymePlayer = ClymeSkyblockCore.getInstance().getPlayersHandler().getClymePlayer(player);
            player.sendTitle("§8§l[§5§l!§8§l] §d§lDONATION", "§7( §d" + targetName + " §7donated, so you got effects! )", 20, 80, 2);
            player.sendActionBar("§5§kii§r §dSupport the server at §nstore.clyme.games§r §5§kii");
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 3));
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 200, 1));
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 200, 3));
            clymePlayer.sendMessage("     &f** &5" + targetName + " &dhas &5donated &dso everyone gets effects! &f**");
            clymePlayer.sendMessage("    &7&o( TIP: They &f&odonated &7at our shop &d&ohttp://store.clyme.games&7&o! )");
        }

        return true;
    }
}
