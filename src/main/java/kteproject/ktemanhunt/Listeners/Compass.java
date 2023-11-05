package kteproject.ktemanhunt.Listeners;

import kteproject.ktemanhunt.Functions.StartGame;
import kteproject.ktemanhunt.main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class Compass implements Listener {

    private final main plugin;

    public Compass(main plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (StartGame.HunterTeam.contains(player)) {
            ItemStack compass = new ItemStack(Material.COMPASS);
            ItemMeta meta = compass.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + "Target: Nearest Speedrunner");
            compass.setItemMeta(meta);
            player.getInventory().setItemInMainHand(compass);;
        }
    }

    @EventHandler
    public void compassLeftClick(PlayerInteractEvent event) {
        Player hunter = event.getPlayer();
        if (event.getAction().toString().contains("LEFT")) {
            if (hunter.getInventory().getItemInMainHand().getType() == Material.COMPASS) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        updateCompass(hunter);
                    }
                }.runTaskTimer(plugin, 40L, 60);
            }
        }
    }

    private void updateCompass(Player hunter) {
        List<Player> SpeedRunnerTeam = StartGame.SpeedRunnerTeam;
        if (SpeedRunnerTeam.isEmpty()) {
            return;
        }

        Player closestSpeedRunner = null;
        double closestDistance = Double.MAX_VALUE;
        for (Player speedrunner : SpeedRunnerTeam) {
            double distance = speedrunner.getLocation().distance(hunter.getLocation());
            if (distance < closestDistance) {
                closestSpeedRunner = speedrunner;
                closestDistance = distance;
            }
        }

        if (closestSpeedRunner != null) {
            if(hunter.getItemInHand().getType() == Material.COMPASS) {
                ItemStack compass = new ItemStack(Material.COMPASS);
                ItemMeta meta = compass.getItemMeta();
                String targetInfo = ChatColor.GREEN + "Target: " + closestSpeedRunner.getName() + " (" + closestSpeedRunner.getLocation().getBlockX() + ", " + closestSpeedRunner.getLocation().getBlockY() + ", " + closestSpeedRunner.getLocation().getBlockZ() + ")";
                hunter.setCompassTarget(closestSpeedRunner.getLocation());
                meta.setDisplayName(targetInfo);
                compass.setItemMeta(meta);
                hunter.getInventory().setItemInMainHand(compass);
            }
        }
    }
}