package kteproject.ktemanhunt.Functions;

import kteproject.ktemanhunt.main;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.atomic.AtomicInteger;

public class AutoStart implements Listener {
    private final main plugin;
    private static BukkitRunnable autostartTask;
    private static AtomicInteger players = new AtomicInteger(0);
    private static AtomicInteger time = new AtomicInteger(0);

    public AutoStart(main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void join(PlayerJoinEvent event) {
        updatePlayerCount();

        if (!StartGame.match) {
            if (plugin.getConfig().getBoolean("configurate.auto-start")) {
                if (players.get() >= plugin.getConfig().getInt("configurate.auto-start-count")) {
                    if (autostartTask == null) {
                        startCountdown();
                    }
                }
            }
        }
    }

    @EventHandler
    private void leave(PlayerQuitEvent event) {
        updatePlayerCount();

        if (!StartGame.match) {
            if (plugin.getConfig().getBoolean("configurate.auto-start")) {
                if (players.get() <= plugin.getConfig().getInt("configurate.auto-start-count")) {
                    stopCountdown();
                }
            }
        }
    }

    private void updatePlayerCount() {
        players.set(Bukkit.getOnlinePlayers().size());
    }

    private void startCountdown() {
        autostartTask = new BukkitRunnable() {
            @Override
            public void run() {
                int remainingTime = time.decrementAndGet();
                if (remainingTime <= 0) {
                    StartGame.start(plugin);
                    cancel();
                }

                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.translateAlternateColorCodes('&', remainingTime + " " + MessagesConfig.get().getString("autostart-message"))));
                }
            }
        };
        autostartTask.runTaskTimer(plugin, 20L, 20L);
        time.set(plugin.getConfig().getInt("configurate.auto-start-time"));
    }

    private void stopCountdown() {
        if (autostartTask != null) {
            autostartTask.cancel();
            autostartTask = null;
        }
        time.set(plugin.getConfig().getInt("configurate.auto-start-time"));
    }
}