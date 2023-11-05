package kteproject.ktemanhunt.Listeners;

import kteproject.ktemanhunt.main;
import kteproject.ktemanhunt.Functions.StartGame;
import org.bukkit.entity.Player;
import org.bukkit.entity.Entity;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerDamage implements Listener {
    private final main plugin;

    public PlayerDamage(main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void PlayerAnyDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Player) {
            if (!StartGame.match) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    private void PlayerTeamDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Player attacker = (Player) event.getDamager();
            Player victim = (Player) event.getEntity();

            if (StartGame.HunterTeam.contains(attacker) && StartGame.HunterTeam.contains(victim)) {
                event.setCancelled(true);
            }

            if (StartGame.SpeedRunnerTeam.contains(attacker) && StartGame.SpeedRunnerTeam.contains(victim)) {
                event.setCancelled(true);
            }
        }
    }
}