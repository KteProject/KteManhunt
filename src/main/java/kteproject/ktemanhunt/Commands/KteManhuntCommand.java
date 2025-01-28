package kteproject.ktemanhunt.Commands;

import kteproject.ktemanhunt.KteManhunt;
import kteproject.ktemanhunt.Managers.AutoStart;
import kteproject.ktemanhunt.Managers.GameSystem;
import kteproject.ktemanhunt.Managers.MessagesConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KteManhuntCommand implements CommandExecutor {
    private final KteManhunt plugin;

    public KteManhuntCommand(KteManhunt plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(args.length == 0) {
            if(!sender.hasPermission("ktemanhunt.command.start") || !sender.hasPermission("ktemanhunt.command.reload") || !sender.hasPermission("ktemanhunt.command.mode") || !sender.hasPermission("ktemanhunt.command.skip")) {
                sender.sendMessage(MessagesConfig.getMessage("command-messages.havent-permission"));
                return true;
            }

            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&8-----------------&aKteManhunt&8-----------------"));
            sender.sendMessage("");
            if(sender.hasPermission("ktemanhunt.command.start")) {sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"  &a/ktemanhunt start &7- &aStart the game"));}
            if(sender.hasPermission("ktemanhunt.command.reload")) {sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"  &a/ktemanhunt reload &7- &aReload this plugin"));}
            if(sender.hasPermission("ktemanhunt.command.mode")) {sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"  &a/ktemanhunt mode <mode> &7- &aReload this plugin"));}
            if(sender.hasPermission("ktemanhunt.command.skip")) {sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"  &a/ktemanhunt skip &7- &aSkip the auto start time"));}
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
        } else if(args[0].equals("mode")) {
            if(!sender.hasPermission("ktemanhunt.command.mode")) {
                sender.sendMessage(MessagesConfig.getMessage("command-messages.havent-permission"));
                return true;
            }

            if(args.length == 1) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&8-----------------&aKteManhunt&8-----------------"));
                sender.sendMessage("");
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"  &ago-nether &7 - &aIf the speedrunners go to nether, the game ends"));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"  &ago-end &7 - &aIf the speedrunners go to end, the game ends"));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"  &akill-dragon &7 - &aIf the speedrunners kills the Ender Dragon, the game ends"));
                sender.sendMessage("");
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&8-----------------&aKteManhunt&8-----------------"));
            } else if(args.length == 2) {
                if(args[1].equals("go-nether")) {
                    for(Player player : Bukkit.getOnlinePlayers()) {
                        String title = MessagesConfig.getMessage("titles.changed-mode.title").replace("%mode", "Go Nether");
                        String sub = MessagesConfig.getMessage("titles.changed-mode.subtitle").replace("%mode", "Go Nether");

                        player.sendTitle(title,sub);
                    }
                    GameSystem.mode = "go-nether";
                }
                else if(args[1].equals("go-end")) {
                    for(Player player : Bukkit.getOnlinePlayers()) {
                        String title = MessagesConfig.getMessage("titles.changed-mode.title").replace("%mode", "Go End");
                        String sub = MessagesConfig.getMessage("titles.changed-mode.subtitle").replace("%mode", "Go End");

                        player.sendTitle(title,sub);
                    }
                    GameSystem.mode = "go-end";
                }
                else if(args[1].equals("kill-dragon")) {
                    for(Player player : Bukkit.getOnlinePlayers()) {
                        String title = MessagesConfig.getMessage("titles.changed-mode.title").replace("%mode", "Kill Dragon");
                        String sub = MessagesConfig.getMessage("titles.changed-mode.subtitle").replace("%mode", "Kill Dragon");

                        player.sendTitle(title,sub);
                    }
                    GameSystem.mode = "kill-dragon";
                }
                else {
                    sender.sendMessage(MessagesConfig.getMessage("command-messages.notfound-mode"));
                }
            }
        } else if(args[0].equals("skip")) {
            if(!sender.hasPermission("ktemanhunt.command.skip")) { sender.sendMessage(MessagesConfig.getMessage("command-messages.havent-permission")); return true; }
            if(GameSystem.match) {sender.sendMessage(MessagesConfig.getMessage("command-messages.already-started-game")); return true;}

            AutoStart.time = 3;

            for(Player player : Bukkit.getOnlinePlayers()) {
                String title = MessagesConfig.getMessage("titles.skip.title");
                String sub = MessagesConfig.getMessage("titles.skip.subtitle");

                player.sendTitle(title,sub);
            }

        }

        return true;
    }
}
