package kteproject.ktemanhunt.Listeners;

import kteproject.ktemanhunt.KteManhunt;
import kteproject.ktemanhunt.Managers.GameSystem;
import kteproject.ktemanhunt.Managers.MessagesConfig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerToPlayerDamage implements Listener {
    @EventHandler
    public void EntityToEntityListener(EntityDamageByEntityEvent event) {
        if(KteManhunt.getConfiguration().getBoolean("configurations.can-damage-teammate")) {
            return;
        }
        if(event.getEntity() instanceof Player && event.getDamager() instanceof Player) {

            Player damager = (Player) event.getDamager();
            Player player = (Player) event.getEntity();

            if(GameSystem.hunters.contains(damager) && GameSystem.hunters.contains(player)) {
                event.setCancelled(true);
                damager.sendMessage(MessagesConfig.getMessage("other.cant-damage-teammate"));
            } else if(GameSystem.speedrunners.contains(damager) && GameSystem.speedrunners.contains(player)) {
                event.setCancelled(true);
                damager.sendMessage(MessagesConfig.getMessage("other.cant-damage-teammate"));
            }


        }
    }
}
