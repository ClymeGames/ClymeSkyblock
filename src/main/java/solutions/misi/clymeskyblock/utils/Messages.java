package solutions.misi.clymeskyblock.utils;

import lombok.SneakyThrows;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import solutions.misi.clymeskyblock.ClymeSkyblock;

import java.io.File;

public class Messages {

    private final FileConfiguration messagesFileCfg;

    @SneakyThrows
    public Messages() {
        File messagesFile = new File(ClymeSkyblock.getInstance().getDataFolder(), "messages.yml");
        messagesFileCfg = new YamlConfiguration();
        if(!messagesFile.exists()) ClymeSkyblock.getInstance().saveResource("messages.yml", false);
        messagesFileCfg.load(messagesFile);
    }

    public String get(String path) {
        return ChatColor.translateAlternateColorCodes('&', messagesFileCfg.getString(path));
    }
}