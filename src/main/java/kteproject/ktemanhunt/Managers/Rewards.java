package kteproject.ktemanhunt.Managers;

import kteproject.ktemanhunt.KteManhunt;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Rewards {
    public static void finishedGame(String winner) {
        if(!KteManhunt.getConfiguration().getBoolean("rewards.enabled")) return;
        switch (winner) {
            case "hunters-win":
                for (Player player : GameSystem.hunters) {
                    for (String command : KteManhunt.getConfiguration().getStringList("rewards.hunters-win")) {
                        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command.replace("%player%", player.getName()));
                    }
                }
                break;
            case "speedrunners-win":
                for (Player player : GameSystem.speedrunners) {
                    for (String command : KteManhunt.getConfiguration().getStringList("rewards.speedrunners-win")) {
                        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command.replace("%player%", player.getName()));
                    }
                }
                break;
        }

    }

    public static void killPlayer(Player player) {
        if(!KteManhunt.getConfiguration().getBoolean("rewards.enabled")) return;

        for (String command : KteManhunt.getConfiguration().getStringList("rewards.kill-player")) {
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command.replace("%player%", player.getName()));
        }

    }
}
