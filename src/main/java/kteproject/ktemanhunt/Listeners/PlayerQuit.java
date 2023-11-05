package kteproject.ktemanhunt.Listeners;

import kteproject.ktemanhunt.Functions.MessagesConfig;
import kteproject.ktemanhunt.Functions.StartGame;
import kteproject.ktemanhunt.main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitScheduler;

public class PlayerQuit implements Listener {
    private final main plugin;

    public PlayerQuit(main plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    private void PlayerLeave(PlayerQuitEvent event) {
        event.getPlayer().damage(999999999);
        if(StartGame.SpeedRunnerTeam.contains(event.getPlayer())) {
            if(StartGame.SpeedRunnerTeam.isEmpty()) {
                String title = ChatColor.translateAlternateColorCodes('&', MessagesConfig.get().getString("title.game-over-speedrunner.title"));
                String subtitle = ChatColor.translateAlternateColorCodes('&', MessagesConfig.get().getString("title.eliminated.sub"));
                for(Player player : Bukkit.getOnlinePlayers()) {
                    player.sendTitle(title, subtitle);
                }
                if(plugin.getConfig().getBoolean("configurate.server-stop")) {
                    BukkitScheduler scheduler = Bukkit.getScheduler();
                    scheduler.runTaskLater(plugin, () -> {
                        plugin.getServer().shutdown();
                    }, 200L);
                }

            }
        }
    }



}
