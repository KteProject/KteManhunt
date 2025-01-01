package kteproject.ktemanhunt.Managers;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;

import java.util.ArrayList;

public class Compass implements Listener {

    public static void giveCompass(Player player) {
        ItemStack compass = new ItemStack(Material.COMPASS);
        CompassMeta compassMeta = (CompassMeta) compass.getItemMeta();
        if (compassMeta != null) {
            Player nearestSpeedrunner = getNearestSpeedrunner(player);
            if (nearestSpeedrunner != null) {
                compassMeta.setDisplayName(ChatColor.RED + "-");
                compassMeta.setLodestone(player.getLocation());
            } else {
                compassMeta.setDisplayName(ChatColor.RED + "-");
                compassMeta.setLodestone(player.getLocation());
            }
            compass.setItemMeta(compassMeta);
        }
        player.getInventory().addItem(compass);
    }

    @EventHandler
    public void onCompassRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType() != Material.COMPASS) {
            return;
        }

        CompassMeta compassMeta = (CompassMeta) item.getItemMeta();
        if (compassMeta == null) {
            return;
        }

        Player nearestSpeedrunner = getNearestSpeedrunner(player);
        if (nearestSpeedrunner == null) {
            compassMeta.setDisplayName(MessagesConfig.getMessage("compass.compass-name-but-havent-speedrunner"));
            item.setItemMeta(compassMeta);
            return;
        }

        if (!player.getWorld().equals(nearestSpeedrunner.getWorld())) {
            Location lastKnownLocation = nearestSpeedrunner.getLocation();
            compassMeta.setLodestone(lastKnownLocation);
            compassMeta.setLodestoneTracked(false);
            compassMeta.setDisplayName(MessagesConfig.getMessage("compass.compass-name-but-havent-speedrunner"));
        } else {
            compassMeta.setLodestone(nearestSpeedrunner.getLocation());
            compassMeta.setLodestoneTracked(false);
            String nearestSpeedrunnerName = nearestSpeedrunner.getName();
            int distance = (int) player.getLocation().distance(nearestSpeedrunner.getLocation());
            compassMeta.setDisplayName(MessagesConfig.getMessage("compass.compass-name").replace("%player%",nearestSpeedrunnerName).replace("%distance%",String.valueOf(distance)));
        }

        item.setItemMeta(compassMeta);
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        ItemStack item = event.getItemDrop().getItemStack();

        if (item.getType() == Material.COMPASS && item.getItemMeta() != null && item.getItemMeta() instanceof CompassMeta) {
            CompassMeta compassMeta = (CompassMeta) item.getItemMeta();
            if (compassMeta.hasLodestone()) {
                event.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onItemSpawnEvent(ItemSpawnEvent event) {
        ItemStack item = event.getEntity().getItemStack();

        if (item.getType() == Material.COMPASS && item.getItemMeta() != null && item.getItemMeta() instanceof CompassMeta) {
            CompassMeta compassMeta = (CompassMeta) item.getItemMeta();
            if (compassMeta.hasLodestone()) {
                event.setCancelled(true);
            }
        }
    }



    private static Player getNearestSpeedrunner(Player player) {
        ArrayList<Player> speedrunners = GameSystem.speedrunners;
        Player nearest = null;
        double nearestDistance = Double.MAX_VALUE;

        for (Player speedrunner : speedrunners) {
            if (speedrunner == null || !speedrunner.isOnline()) {
                continue;
            }

            if (!player.getWorld().equals(speedrunner.getWorld())) {
                continue;
            }

            double distance = player.getLocation().distance(speedrunner.getLocation());
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearest = speedrunner;
            }
        }
        return nearest;
    }
}

