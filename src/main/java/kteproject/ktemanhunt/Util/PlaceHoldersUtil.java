package kteproject.ktemanhunt.Util;

import kteproject.ktemanhunt.Managers.GameSystem;
import org.bukkit.OfflinePlayer;

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
                    return "SpeedRunners";
                } else if(GameSystem.hunters.contains(player)) {
                    return "Hunters";
                } else if(!GameSystem.match){
                    return "Match Not Started";
                } else {
                    return "Died";
                }
          default:
                return null;
        }
    }
}
