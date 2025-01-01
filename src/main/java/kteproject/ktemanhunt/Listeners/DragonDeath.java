package kteproject.ktemanhunt.Listeners;

import kteproject.ktemanhunt.KteManhunt;
import kteproject.ktemanhunt.Managers.GameSystem;
import kteproject.ktemanhunt.Managers.MessagesConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.scheduler.BukkitScheduler;

public class DragonDeath implements Listener {
    private final KteManhunt plugin;

    public DragonDeath(KteManhunt plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDragonDeath(EntityDeathEvent event) {
        if (plugin.getConfig().getString("configurations.game-type").equals("kill-dragon")) {
            if(event.getEntity() instanceof EnderDragon) {
                EnderDragon dragon = (EnderDragon) event.getEntity();

                if (dragon.getKiller() != null && GameSystem.speedrunners.contains(dragon.getKiller())) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.sendTitle(
                                MessagesConfig.getMessage("titles.finished-game.winner-speedrunners.title"),
                                MessagesConfig.getMessage("titles.finished-game.winner-speedrunners.subtitle"),
                                1,
                                400,
                                1
                        );
                    }
                    if (KteManhunt.getConfiguration().getBoolean("configurations.stop-server")) {
                        BukkitScheduler scheduler = Bukkit.getScheduler();
                        scheduler.runTaskLater(plugin, () -> {
                            plugin.getServer().shutdown();
                        }, 200L);
                    }
                }
            }
        }
    }
}
