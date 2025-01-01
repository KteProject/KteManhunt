package kteproject.ktemanhunt.Managers;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.Objects;

public class MessagesConfig {
    private static File messageFile;
    private static YamlConfiguration messageConfig;

    public static void setup(Plugin plugin) {
        messageFile = new File(plugin.getDataFolder(), "messages.yml");

        if(!messageFile.exists()) {
            plugin.getLogger().info("[Loader] messages.yml not found, creating...");
            plugin.saveResource("messages.yml",false);
        }

        messageConfig = YamlConfiguration.loadConfiguration(messageFile);
    }

    public static void reload() { messageConfig = YamlConfiguration.loadConfiguration(messageFile); }

    public static YamlConfiguration getConfig() {
        return messageConfig;
    }

    public static String getMessage(String message) {
        if(getConfig().getString(message) != null) {
            return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString(message))).replace("%prefix%",ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("prefix"))));
        } else {
            return ChatColor.RED + "Message not founded in messages.yml (" + message + ")";
        }
    }
}
