package kteproject.ktemanhunt.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import static kteproject.ktemanhunt.Managers.GameSystem.*;

public class PlayerLeave implements Listener {
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        if(match) {
            Player player = event.getPlayer();
            if(speedrunners.contains(player)) {
                speedrunners.remove(player);
                checkLive();
            } else if(hunters.contains(player)) {
                hunters.remove(player);
                checkLive();
            }
        }
    }
}
