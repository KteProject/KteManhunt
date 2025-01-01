package kteproject.ktemanhunt.Listeners;

import kteproject.ktemanhunt.Managers.MessagesConfig;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffectType;

import static kteproject.ktemanhunt.Managers.GameSystem.match;

public class PlayerJoin implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.removePotionEffect(PotionEffectType.SLOW);
        player.removePotionEffect(PotionEffectType.BLINDNESS);
        if (match) {
            player.setGameMode(GameMode.SPECTATOR);
            player.sendTitle(
                    MessagesConfig.getMessage("titles.eliminated.title"),
                    MessagesConfig.getMessage("titles.eliminated.subtitle")
            );
        } else {
            player.teleport(new Location(Bukkit.getWorlds().get(0), 0, 150, 0));
            player.getInventory().clear();
            player.setGameMode(GameMode.ADVENTURE);
        }
    }
}
