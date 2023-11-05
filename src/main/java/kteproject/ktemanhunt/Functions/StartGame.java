package kteproject.ktemanhunt.Functions;

import kteproject.ktemanhunt.main;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.ChatColor;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import java.util.ArrayList;
import java.util.List;

public class StartGame implements Listener {

    //Values

    public static boolean match;
    public static int seconds;
    private final main plugin;
    public StartGame(main plugin) {
        this.plugin = plugin;
    }

    public static List<Player> SpeedRunnerTeam = new ArrayList<>();
    public static List<Player> HunterTeam = new ArrayList<>();



    public static void start(Plugin plugin) {
        if (match) {
            return;
        }



        List<Player> onlinePlayers = new ArrayList<>(Bukkit.getOnlinePlayers());

        int totalPlayers = onlinePlayers.size();


        if (totalPlayers >= 10 && totalPlayers <= 25) {
            int speedRunnerCount = 1;
            int hunterCount = totalPlayers - speedRunnerCount;

            match = true;

            for (int i = 0; i < totalPlayers; i++) {
                Player player = onlinePlayers.get(i);

                if (i < speedRunnerCount) {
                    SpeedRunnerTeam.add(player);
                    String title = ChatColor.translateAlternateColorCodes('&', MessagesConfig.get().getString("title.start-game.title"));
                    String subtitle = ChatColor.translateAlternateColorCodes('&', MessagesConfig.get().getString("title.speedrunner-select"));
                    player.sendTitle(title, subtitle);
                } else {
                    HunterTeam.add(player);
                    String title = ChatColor.translateAlternateColorCodes('&', MessagesConfig.get().getString("title.start-game.title"));
                    String subtitle = ChatColor.translateAlternateColorCodes('&', MessagesConfig.get().getString("title.hunter-select"));
                    player.sendTitle(title, subtitle);
                }

                player.getInventory().clear();
                player.setHealth(20);
                player.setFoodLevel(20);
                player.setGameMode(GameMode.SURVIVAL);
            }

            World world = Bukkit.getWorld("world");
            WorldBorder worldBorder = world.getWorldBorder();
            worldBorder.setSize(30000000);
            worldBorder.setCenter(0, 0);
            worldBorder.setDamageAmount(5);
            worldBorder.setDamageBuffer(2);

        } else if(totalPlayers >= 25 && totalPlayers <= 40){
            int speedRunnerCount = 2;
            int hunterCount = totalPlayers - speedRunnerCount;

            match = true;

            for (int i = 0; i < totalPlayers; i++) {
                Player player = onlinePlayers.get(i);

                if (i < speedRunnerCount) {
                    SpeedRunnerTeam.add(player);
                    String title = ChatColor.translateAlternateColorCodes('&', MessagesConfig.get().getString("title.start-game.title"));
                    String subtitle = ChatColor.translateAlternateColorCodes('&', MessagesConfig.get().getString("title.speedrunner-select"));
                    player.sendTitle(title, subtitle);
                } else {
                    HunterTeam.add(player);
                    String title = ChatColor.translateAlternateColorCodes('&', MessagesConfig.get().getString("title.start-game.title"));
                    String subtitle = ChatColor.translateAlternateColorCodes('&', MessagesConfig.get().getString("title.hunter-select"));
                    player.sendTitle(title, subtitle);
                }

                player.getInventory().clear();
                player.setHealth(20);
                player.setFoodLevel(20);
                player.setGameMode(GameMode.SURVIVAL);
            }

            World world = Bukkit.getWorld("world");
            WorldBorder worldBorder = world.getWorldBorder();
            worldBorder.setSize(30000000);
            worldBorder.setCenter(0, 0);
            worldBorder.setDamageAmount(5);
            worldBorder.setDamageBuffer(2);

        } else if(totalPlayers >= 40 && totalPlayers <= 50) {
            int speedRunnerCount = 3;
            int hunterCount = totalPlayers - speedRunnerCount;

            match = true;

            for (int i = 0; i < totalPlayers; i++) {
                Player player = onlinePlayers.get(i);

                if (i < speedRunnerCount) {
                    SpeedRunnerTeam.add(player);
                    String title = ChatColor.translateAlternateColorCodes('&', MessagesConfig.get().getString("title.start-game.title"));
                    String subtitle = ChatColor.translateAlternateColorCodes('&', MessagesConfig.get().getString("title.speedrunner-select"));
                    player.sendTitle(title, subtitle);
                } else {
                    HunterTeam.add(player);
                    String title = ChatColor.translateAlternateColorCodes('&', MessagesConfig.get().getString("title.start-game.title"));
                    String subtitle = ChatColor.translateAlternateColorCodes('&', MessagesConfig.get().getString("title.hunter-select"));
                    player.sendTitle(title, subtitle);
                }

                player.getInventory().clear();
                player.setHealth(20);
                player.setFoodLevel(20);
                player.setGameMode(GameMode.SURVIVAL);
            }

            World world = Bukkit.getWorld("world");
            WorldBorder worldBorder = world.getWorldBorder();
            worldBorder.setSize(30000000);
            worldBorder.setCenter(0, 0);
            worldBorder.setDamageAmount(5);
            worldBorder.setDamageBuffer(2);
        }

        for (Player hunter : HunterTeam) {
            ItemStack compass = new ItemStack(Material.COMPASS);
            ItemMeta meta = compass.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + "Target: Nearest Speedrunner");
            compass.setItemMeta(meta);
            hunter.getInventory().setItemInMainHand(compass);
        }

        if(match == true) {
            for (Player hunter : HunterTeam) {

                hunter.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, plugin.getConfig().getInt("configurate.freeze-time")* 20, 255));
                hunter.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, plugin.getConfig().getInt("configurate.freeze-time")* 20, 255));
                hunter.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, plugin.getConfig().getInt("configurate.freeze-time") * 20, 128));
                hunter.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, plugin.getConfig().getInt("configurate.freeze-time") * 20, 255));
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity() instanceof EnderDragon) {
            EnderDragon dragon = (EnderDragon) event.getEntity();

            if (dragon.getKiller() != null && SpeedRunnerTeam.contains(dragon.getKiller())) {
                String title = ChatColor.translateAlternateColorCodes('&', MessagesConfig.get().getString("title.game-over-speedrunner.title"));
                String subtitle = ChatColor.translateAlternateColorCodes('&', MessagesConfig.get().getString("title.game-over-speedrunner.sub"));
                for(Player player : Bukkit.getOnlinePlayers()) {
                    player.sendTitle(title, subtitle);

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
    @EventHandler
    public void HuntersRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        if(HunterTeam.contains(player)) {
            ItemStack compass = new ItemStack(Material.COMPASS);

            player.getInventory().addItem(compass);
        }
    }

}



