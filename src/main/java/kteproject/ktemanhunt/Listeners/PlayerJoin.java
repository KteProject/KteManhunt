package kteproject.ktemanhunt.Listeners;

import kteproject.ktemanhunt.Managers.GameSystem;
import kteproject.ktemanhunt.Managers.MessagesConfig;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffectType;

import static kteproject.ktemanhunt.Managers.GameSystem.hunters;
import static kteproject.ktemanhunt.Managers.GameSystem.match;

public class PlayerJoin implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.removePotionEffect(PotionEffectType.SLOW);
        player.removePotionEffect(PotionEffectType.BLINDNESS);
        if (match) {
            if(GameSystem.leavedPlayers.get(player.getUniqueId()) != null) {
                player.setGameMode(GameMode.SURVIVAL);
                player.teleport(GameSystem.leavedPlayers.get(player.getUniqueId()));
                player.sendTitle(
                        MessagesConfig.getMessage("titles.rejoin.title"),
                        MessagesConfig.getMessage("titles.rejoin.subtitle")
                );
                hunters.add(player);
            } else {
                player.teleport(new Location(Bukkit.getWorlds().get(0), 0, 150, 0));
                player.setGameMode(GameMode.SPECTATOR);
                player.sendTitle(
                        MessagesConfig.getMessage("titles.eliminated.title"),
                        MessagesConfig.getMessage("titles.eliminated.subtitle")
                );
            }
        } else {
            player.teleport(new Location(Bukkit.getWorlds().get(0), 0, 150, 0));
            player.getInventory().clear();
            player.setGameMode(GameMode.ADVENTURE);
        }
    }
}
