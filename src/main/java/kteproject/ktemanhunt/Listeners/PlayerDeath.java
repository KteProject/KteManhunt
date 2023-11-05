package kteproject.ktemanhunt.Listeners;

import kteproject.ktemanhunt.Functions.MessagesConfig;
import kteproject.ktemanhunt.Functions.StartGame;
import kteproject.ktemanhunt.main;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitScheduler;

public class PlayerDeath implements Listener {
    private final main plugin;

    public PlayerDeath(main plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    private void PlayerDeath(EntityDeathEvent event) {

        Entity player = event.getEntity();
        if (player instanceof Player) {
            if(StartGame.SpeedRunnerTeam.contains(player)) {
                ((Player) player).setGameMode(GameMode.SPECTATOR);
                for (Player loopplayer : Bukkit.getOnlinePlayers()) {
                    loopplayer.sendMessage(ChatColor.translateAlternateColorCodes('&',player.getName() + " " + MessagesConfig.get().getString("message.speedrunner-died")));
                }
                String title = ChatColor.translateAlternateColorCodes('&', MessagesConfig.get().getString("title.eliminated.title"));
                String subtitle = ChatColor.translateAlternateColorCodes('&', MessagesConfig.get().getString("title.eliminated.sub"));
                ((Player) player).sendTitle(title,subtitle);

                StartGame.SpeedRunnerTeam.remove(player);
                if(StartGame.SpeedRunnerTeam.isEmpty()) {
                    for (Player loopplayer : Bukkit.getOnlinePlayers()) {
                        String title2 = ChatColor.translateAlternateColorCodes('&', MessagesConfig.get().getString("title.game-over-hunter.title"));
                        String subtitle2 = ChatColor.translateAlternateColorCodes('&', MessagesConfig.get().getString("title.game-over-hunter.sub"));
                        loopplayer.sendTitle(title2, subtitle2);

                        Location playerLocation = player.getLocation();
                        World world = playerLocation.getWorld();
                        Firework firework = world.spawn(playerLocation, Firework.class);
                        FireworkMeta fireworkMeta = firework.getFireworkMeta();
                        fireworkMeta.addEffect(FireworkEffect.builder().withColor(Color.RED).flicker(true).build());
                        fireworkMeta.setPower(2);
                        firework.setFireworkMeta(fireworkMeta);

                    }
                    if(plugin.getConfig().getBoolean("configurate.server-stop")) {
                        BukkitScheduler scheduler = Bukkit.getScheduler();
                        scheduler.runTaskLater(plugin, () -> {
                            plugin.getServer().shutdown();
                        }, 200L);
                    }
                }
            }
        }
    }
}
