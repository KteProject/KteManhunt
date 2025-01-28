package kteproject.ktemanhunt.Util;

import kteproject.ktemanhunt.Managers.GameSystem;
import kteproject.ktemanhunt.Managers.MessagesConfig;
import kteproject.ktemanhunt.Managers.PlayerStats;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;

import java.util.Objects;

public class PlaceHoldersUtil {
    public static String processPlaceholder(OfflinePlayer player, String placeholder) {
        switch (placeholder.toLowerCase()) {
            case "match":
                return String.valueOf(GameSystem.match);
            case "playercount":
                return Integer.toString(GameSystem.speedrunners.size() + GameSystem.hunters.size());
            case "speedrunners_count":
                return Integer.toString(GameSystem.speedrunners.size());
            case "hunters_count":
                return Integer.toString(GameSystem.hunters.size());
            case "player":
                return player.getName();
            case "time":
                int totalSeconds = GameSystem.time;
                int minutes = totalSeconds / 60;
                int seconds = totalSeconds % 60;
                return minutes + ":" + String.format("%02d", seconds);
            case "hunters_or_speedrunners":
                if(GameSystem.speedrunners.contains(player)) {
                    return ChatColor.translateAlternateColorCodes('&', MessagesConfig.getMessage("placeholders.huntersorspeedrunners.speedrunner"));
                } else if(GameSystem.hunters.contains(player)) {
                    return ChatColor.translateAlternateColorCodes('&', MessagesConfig.getMessage("placeholders.huntersorspeedrunners.hunter"));
                } else if(!GameSystem.match){
                    return ChatColor.translateAlternateColorCodes('&', MessagesConfig.getMessage("placeholders.huntersorspeedrunners.matchnotstarted"));
                } else {
                    return ChatColor.translateAlternateColorCodes('&', MessagesConfig.getMessage("placeholders.huntersorspeedrunners.died"));
                }
            case "mode":
                return GameSystem.mode.replace("go-nether", ChatColor.translateAlternateColorCodes('&', MessagesConfig.getMessage("placeholders.mode.go-nether"))).replace("go-end", ChatColor.translateAlternateColorCodes('&', MessagesConfig.getMessage("placeholders.mode.go-end"))).replace("kill-dragon", ChatColor.translateAlternateColorCodes('&', MessagesConfig.getMessage("placeholders.mode.kill-dragon")));
            case "win":
                return Integer.toString(PlayerStats.getWin(Objects.requireNonNull(player.getPlayer())));
            case "death":
                return Integer.toString(PlayerStats.getDeath(Objects.requireNonNull(player.getPlayer())));
            case "kill":
                return Integer.toString(PlayerStats.getKill(Objects.requireNonNull(player.getPlayer())));
            default:
                return null;
        }
    }
}
