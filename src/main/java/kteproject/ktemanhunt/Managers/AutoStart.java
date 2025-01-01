package kteproject.ktemanhunt.Managers;

import kteproject.ktemanhunt.KteManhunt;
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
    private final KteManhunt plugin;
    private static BukkitRunnable autostartTask;
    private static final AtomicInteger players = new AtomicInteger(0);
    private static final AtomicInteger time = new AtomicInteger(0);

    public AutoStart(KteManhunt plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void join(PlayerJoinEvent event) {
        updatePlayerCount();

        if (!GameSystem.match) {
            if (plugin.getConfig().getBoolean("configurations.auto-start.enabled")) {
                if (players.get() >= plugin.getConfig().getInt("configurations.auto-start.player-count")) {
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

        if (!GameSystem.match) {
            if (plugin.getConfig().getBoolean("configurations.auto-start.enabled")) {
                if (players.get() <= plugin.getConfig().getInt("configurations.auto-start.player-count")) {
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
                if(GameSystem.match) {this.cancel();}
                int remainingTime = time.decrementAndGet();
                if (remainingTime <= 0) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            GameSystem.startGame(plugin);
                        }
                    }.runTaskLater(plugin, 20L);
                    cancel();
                }

                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(MessagesConfig.getMessage("other.autostart-actionbar").replace("%seconds%",String.valueOf(remainingTime))));
                }
            }
        };
        autostartTask.runTaskTimer(plugin, 20L, 20L);
        time.set(plugin.getConfig().getInt("configurations.auto-start.duration"));
    }

    private void stopCountdown() {
        if (autostartTask != null) {
            autostartTask.cancel();
            autostartTask = null;
        }
        time.set(plugin.getConfig().getInt("configurations.auto-start.duration"));
    }
}
