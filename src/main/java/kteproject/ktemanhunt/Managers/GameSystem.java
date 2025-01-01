package kteproject.ktemanhunt.Managers;

import kteproject.ktemanhunt.KteManhunt;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;

public class GameSystem implements Listener {

    public static boolean match;
    public static int time;
    public static boolean huntersRunning;
    public static ArrayList<Player> speedrunners = new ArrayList<>();
    public static ArrayList<Player> hunters = new ArrayList<>();

    public static void init() {
        match = false;
        time = 0;
        huntersRunning = false;
        speedrunners.clear();
        hunters.clear();
        for(World world : Bukkit.getWorlds()) {
            WorldBorder worldBorder = world.getWorldBorder();
            worldBorder.setSize(KteManhunt.getConfiguration().getInt("configurations.lobby-world-border"));
            worldBorder.setCenter(0, 0);
            worldBorder.setDamageAmount(5);
            worldBorder.setDamageBuffer(2);
        }
    }

    public static void startGame(Plugin plugin) {
        if(match) return;
        match = true;
        if(Bukkit.getOnlinePlayers().size() <= 5) {
            setSpeedRunners(1);
        } else if(Bukkit.getOnlinePlayers().size() <= 10) {
            setSpeedRunners(2);
        } else {
            setSpeedRunners(3);
        }
        for(Player p : Bukkit.getOnlinePlayers()) {
            p.setHealth(20);
            p.setFoodLevel(20);
            p.getInventory().clear();
            p.setGameMode(GameMode.SURVIVAL);
        }
        for(Player p : hunters) {
            Compass.giveCompass(p);
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE,255));
            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE,255));
            p.sendTitle(
                    MessagesConfig.getMessage("titles.game-start.hunters.title"),
                    MessagesConfig.getMessage("titles.game-start.hunters.subtitle")
            );
        }
        for (Player p : speedrunners) {
            p.sendTitle(
                    MessagesConfig.getMessage("titles.game-start.speedrunners.title"),
                    MessagesConfig.getMessage("titles.game-start.speedrunners.subtitle")
            );
        }
        for(World world : Bukkit.getWorlds()) {
            WorldBorder worldBorder = world.getWorldBorder();
            worldBorder.setSize(KteManhunt.getConfiguration().getInt("configurations.game-world-border"));
            worldBorder.setCenter(0, 0);
            worldBorder.setDamageAmount(5);
            worldBorder.setDamageBuffer(2);
        }
        startTime(plugin);
    }

    public static void setSpeedRunners(int speedRunnerCount) {
        for (int i = 0; i < speedRunnerCount; i++) {
            Player selectedPlayer = new ArrayList<>(Bukkit.getOnlinePlayers()).get(i);
            speedrunners.add(selectedPlayer);
        }
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!speedrunners.contains(p)) {
                hunters.add(p);
            }
        }
    }

    public static void checkLive() {
        if (speedrunners.isEmpty()) {
            time = KteManhunt.getConfiguration().getInt("configurations.game-finish-time");
        }
    }

    public static void startTime(Plugin plugin) {
        time = KteManhunt.getConfiguration().getInt("configurations.freeze-time");
        new BukkitRunnable() {
            @Override
            public void run() {
                if(!match) this.cancel();
                if(huntersRunning) {
                    time++;
                    if(time >= KteManhunt.getConfiguration().getInt("configurations.game-finish-time")) {
                        for(Player p : Bukkit.getOnlinePlayers()) {
                            p.sendTitle(
                                    MessagesConfig.getMessage("titles.finished-game.winner-hunters.title"),
                                    MessagesConfig.getMessage("titles.finished-game.winner-hunters.subtitle"),
                                    1,
                                    200,
                                    1
                            );
                        }
                        if(KteManhunt.getConfiguration().getBoolean("configurations.stop-server")) {
                            BukkitScheduler scheduler = Bukkit.getScheduler();
                            scheduler.runTaskLater(plugin, () -> {
                                plugin.getServer().shutdown();
                            }, 200L);
                        }
                    }
                } else {
                    if(KteManhunt.getConfiguration().getBoolean("configurations.announce-freeze-time.enabled")) {
                        String message = KteManhunt.getConfiguration().getString("configurations.announce-freeze-time." + time);
                        if(message != null) {
                            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
                        }
                    }
                    time--;
                    if(time == 0) {
                        huntersRunning = true;
                        for(Player player : hunters) {
                            player.removePotionEffect(PotionEffectType.BLINDNESS);
                            player.removePotionEffect(PotionEffectType.SLOW);
                        }
                        for(Player player: Bukkit.getOnlinePlayers()) {
                            player.sendTitle(
                                    MessagesConfig.getMessage("titles.hunters-started-game.title"),
                                    MessagesConfig.getMessage("titles.hunters-started-game.subtitle")
                            );
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }
}
