package kteproject.ktemanhunt.Commands;

import kteproject.ktemanhunt.KteManhunt;
import kteproject.ktemanhunt.Managers.GameSystem;
import kteproject.ktemanhunt.Managers.MessagesConfig;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class KteManhuntCommand implements CommandExecutor {
    private final KteManhunt plugin;

    public KteManhuntCommand(KteManhunt plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(args.length == 0) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&8-----------------&aKteManhunt&8-----------------"));
            sender.sendMessage("");
            if(sender.hasPermission("ktemanhunt.command.start")) {sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"  &a/ktemanhunt start &7- &aStart the game"));}
            if(sender.hasPermission("ktemanhunt.command.reload")) {sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"  &a/ktemanhunt reload &7- &aReload this plugin"));}
            sender.sendMessage("");
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&8-----------------&aKteManhunt&8-----------------"));
            return true;
        } else if(args[0].equals("start")) {
            if(!sender.hasPermission("ktemanhunt.command.start")) {
                sender.sendMessage(MessagesConfig.getMessage("command-messages.havent-permission"));
                return true;
            }
            if(!GameSystem.match) {
                sender.sendMessage(MessagesConfig.getMessage("command-messages.start-game"));
                GameSystem.startGame(plugin);
                return true;
            } else {
                sender.sendMessage(MessagesConfig.getMessage("command-messages.already-started-game"));
                return true;
            }
        } else if(args[0].equals("reload")) {
            if(!sender.hasPermission("ktemanhunt.command.reload")) {
                sender.sendMessage(MessagesConfig.getMessage("command-messages.havent-permission"));
                return true;
            }
            plugin.reloadConfig();
            MessagesConfig.reload();
            sender.sendMessage(MessagesConfig.getMessage("command-messages.plugin-reloaded"));
        }

        return true;
    }
}
