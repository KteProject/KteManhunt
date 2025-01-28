package kteproject.ktemanhunt.Listeners;

import kteproject.ktemanhunt.KteManhunt;
import kteproject.ktemanhunt.Managers.GameSystem;
import kteproject.ktemanhunt.Managers.MessagesConfig;
import kteproject.ktemanhunt.Managers.PlayerStats;
import kteproject.ktemanhunt.Managers.Rewards;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.scheduler.BukkitScheduler;

public class PlayerChangeWorldEvent implements Listener {

    private final KteManhunt plugin;

    public PlayerChangeWorldEvent(KteManhunt plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        if (GameSystem.mode.equals("go-nether")) {
            if (GameSystem.speedrunners.contains(player)) {
                if (player.getWorld().getName().equals("world_nether")) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.sendTitle(
                                MessagesConfig.getMessage("titles.finished-game.winner-speedrunners.title"),
                                MessagesConfig.getMessage("titles.finished-game.winner-speedrunners.subtitle"),
                                1,
                                400,
                                1
                        );
                    }
                    Rewards.finishedGame("speedrunners-win");
                    for(Player speedrunners : GameSystem.speedrunners) {
                        PlayerStats.addWin(speedrunners);
                    }
                    if (KteManhunt.getConfiguration().getBoolean("configurations.stop-server")) {
                        BukkitScheduler scheduler = Bukkit.getScheduler();
                        scheduler.runTaskLater(plugin, () -> {
                            plugin.getServer().shutdown();
                        }, 20L * KteManhunt.getConfiguration().getInt("configurations.stop-server-time"));
                    }
                }
            }
        }
        if (GameSystem.mode.equals("go-end")) {
            if (GameSystem.speedrunners.contains(player)) {
                if (player.getWorld().getName().equals("world_the_end")) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.sendTitle(
                                MessagesConfig.getMessage("titles.finished-game.winner-speedrunners.title"),
                                MessagesConfig.getMessage("titles.finished-game.winner-speedrunners.subtitle"),
                                1,
                                400,
                                1
                        );
                    }
                    Rewards.finishedGame("speedrunners-win");
                    for(Player speedrunners : GameSystem.speedrunners) {
                        PlayerStats.addWin(speedrunners);
                    }
                    if (KteManhunt.getConfiguration().getBoolean("configurations.stop-server")) {
                        BukkitScheduler scheduler = Bukkit.getScheduler();
                        scheduler.runTaskLater(plugin, () -> {
                            plugin.getServer().shutdown();
                        }, 20L * KteManhunt.getConfiguration().getInt("configurations.stop-server-time"));
                    }
                }
            }
        }
    }
}
