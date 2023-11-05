package kteproject.ktemanhunt.Listeners;

import kteproject.ktemanhunt.main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Piglin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class PiglinPearlBoost implements Listener {
    private final main plugin;

    public PiglinPearlBoost(main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onTrade(EntityDropItemEvent event) {
        if (!(event.getEntity() instanceof Piglin))
            return;
        if (this.plugin.inGame && this.plugin.getConfig().getBoolean("configurate.piglinpearlboost")) {
            Piglin piglin = (Piglin) event.getEntity();
            Random random = new Random();
            int chance = random.nextInt(99);
            if (chance >= 10)
                return;
            Random pearlChance = new Random();
            int quantityDropped = pearlChance.nextInt(2) + 2;
            Location itemLoc = event.getItemDrop().getLocation();
            event.getItemDrop().setItemStack(new ItemStack(Material.ENDER_PEARL));
            event.getItemDrop().getItemStack().setAmount(quantityDropped);
        }
    }
}
