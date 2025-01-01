package kteproject.ktemanhunt.Listeners;

import kteproject.ktemanhunt.KteManhunt;
import kteproject.ktemanhunt.Managers.Compass;
import kteproject.ktemanhunt.Managers.MessagesConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import static kteproject.ktemanhunt.Managers.GameSystem.*;

public class PlayerDeath implements Listener {
    private final KteManhunt plugin;

    public PlayerDeath(KteManhunt plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Location deathLocation = event.getEntity().getLocation();
        if (match) {
            Player player = (Player) event.getEntity();

            if (speedrunners.contains(player)) {
                speedrunners.remove(player);
                player.sendTitle(
                        MessagesConfig.getMessage("titles.death.speedrunners.title"),
                        MessagesConfig.getMessage("titles.death.speedrunners.subtitle")
                );
                player.setGameMode(GameMode.SPECTATOR);
                player.teleport(deathLocation);
                checkLive();
            } else if (hunters.contains(player)) {
                player.setGameMode(GameMode.SPECTATOR);
                player.teleport(deathLocation);
                player.sendTitle(
                        MessagesConfig.getMessage("titles.death.hunters.title"),
                        MessagesConfig.getMessage("titles.death.hunter.subtitle")
                );

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Location spawnLocation = Bukkit.getWorlds().get(0).getSpawnLocation();
                        player.teleport(spawnLocation);
                        player.setGameMode(GameMode.SURVIVAL);
                        Compass.giveCompass(player);
                    }
                }.runTaskLater(plugin, 20L * 3);
            }
        }
    }
}
