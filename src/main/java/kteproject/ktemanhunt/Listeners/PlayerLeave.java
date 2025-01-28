package kteproject.ktemanhunt.Listeners;

import kteproject.ktemanhunt.KteManhunt;
import kteproject.ktemanhunt.Managers.GameSystem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import static kteproject.ktemanhunt.Managers.GameSystem.*;

public class PlayerLeave implements Listener {

    private final KteManhunt plugin;

    public PlayerLeave(KteManhunt plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        if(match) {
            Player player = event.getPlayer();
            if(speedrunners.contains(player)) {
                speedrunners.remove(player);
                checkLive(plugin);
            } else if(hunters.contains(player)) {
                hunters.remove(player);
                GameSystem.leavedPlayers.put(event.getPlayer().getUniqueId(), event.getPlayer().getLocation());
                checkLive(plugin);
            }
        }
    }
}
