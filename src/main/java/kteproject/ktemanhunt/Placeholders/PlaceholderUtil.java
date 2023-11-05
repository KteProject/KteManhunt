package kteproject.ktemanhunt.Placeholders;

import org.bukkit.OfflinePlayer;
import kteproject.ktemanhunt.Functions.StartGame;

public class PlaceholderUtil {

    public static String processPlaceholder(OfflinePlayer player, String placeholder) {
        switch (placeholder.toLowerCase()) {
            case "player":
                return player.getName();
            case "hunters":
                int hunters = StartGame.HunterTeam.size();
                return String.valueOf(hunters);
            case "speedrunners":
                int speedrunners = StartGame.SpeedRunnerTeam.size();
                return String.valueOf(speedrunners);
            case "player_hunter_or_speedrunner":
                if(StartGame.SpeedRunnerTeam.contains(player)) {
                    return "Speedrunner";
                }
                if(StartGame.HunterTeam.contains(player)) {
                    return "Hunter";
                }
                return "Nothing";
            default:
                return null;
        }
    }
}