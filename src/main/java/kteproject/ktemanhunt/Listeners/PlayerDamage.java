package kteproject.ktemanhunt.Listeners;

import kteproject.ktemanhunt.Managers.GameSystem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerDamage implements Listener {

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {

        if(!GameSystem.match) {
            event.setCancelled(true);
        } else {
            if(event.getEntity() instanceof Player) {
                if (GameSystem.hunters.contains(event.getEntity()) && !GameSystem.huntersRunning) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
