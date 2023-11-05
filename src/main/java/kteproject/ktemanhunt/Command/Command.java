package kteproject.ktemanhunt.Command;

import kteproject.ktemanhunt.Functions.MessagesConfig;
import kteproject.ktemanhunt.Functions.StartGame;
import kteproject.ktemanhunt.main;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.ChatColor;

public class Command implements CommandExecutor {
    private final main plugin;


    public Command(main plugin) {
        this.plugin = plugin;

    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String s, String[] args) {
        if (sender.hasPermission("ktemanhunt.start") && sender.hasPermission("ktemanhunt.reload") && sender.hasPermission("ktemanhunt.compass")) {


            if (args.length == 0) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', MessagesConfig.get().getString("message.command-title")));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
                if (sender.hasPermission("ktemanhunt.start")) {sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6  /ktemanhunt start &7- &fStart the Game"));}
                if (sender.hasPermission("ktemanhunt.reload")) {sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6  /ktemanhunt reload &7- &fReload the Plugin"));}
                if (sender.hasPermission("ktemanhunt.compass")) {sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6  /ktemanhunt compass &7- &fSelects Player"));}
                return true;
            }

            String subCommand = args[0].toLowerCase();

            if (subCommand.equals("start")) {
                if (!sender.hasPermission("ktemanhunt.start")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', MessagesConfig.get().getString("message.dont-permission")));
                    return true;
                }
                if (StartGame.match) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', MessagesConfig.get().getString("messages.already-started")));
                    return true;
                }

                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', MessagesConfig.get().getString("message.command-game-started")));
                StartGame.start(plugin);
            }
            if (subCommand.equals("reload")) {
                MessagesConfig.reload();
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', MessagesConfig.get().getString("message.plugin-reload")));
            }
        }
        return true;
    }
}





