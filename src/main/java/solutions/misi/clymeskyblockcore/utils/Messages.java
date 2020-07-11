package solutions.misi.clymeskyblockcore.utils;

import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import solutions.misi.clymeskyblockcore.ClymeSkyblockCore;

import java.io.File;

public class Messages {

    private final FileConfiguration messagesFileCfg;

    @Getter private final String prefix;
    @Getter private final String commandSpam;
    @Getter private final String noPermission;

    @SneakyThrows
    public Messages() {
        File messagesFile = new File(ClymeSkyblockCore.getInstance().getDataFolder(), "messages.yml");
        messagesFileCfg = new YamlConfiguration();
        if(!messagesFile.exists()) ClymeSkyblockCore.getInstance().saveResource("messages.yml", false);
        messagesFileCfg.load(messagesFile);

        //> Messages
        prefix = getFormattedMessage("prefix");
        commandSpam = prefix + getFormattedMessage("command-spam");
        noPermission = prefix + getFormattedMessage("no-permission");
    }

    private String getFormattedMessage(String path) {
        return ClymeChat.format(messagesFileCfg.getString(path).replace("%success%", ClymeChat.SUCCESS()).replace("%info%", ClymeChat.INFO()).replace("%error%", ClymeChat.ERROR()));
    }
}