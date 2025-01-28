package kteproject.ktemanhunt;

import kteproject.ktemanhunt.Commands.KteManhuntCommand;
import kteproject.ktemanhunt.Listeners.*;
import kteproject.ktemanhunt.Managers.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class KteManhunt extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("KteManhunt is Enabling...");

        Bukkit.getPluginManager().registerEvents(new Compass(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
        Bukkit.getPluginManager().registerEvents(new GameSystem(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDamage(), this);
        Bukkit.getPluginManager().registerEvents(new DragonDeath(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerLeave(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeath(this), this);
        Bukkit.getPluginManager().registerEvents(new AutoStart(this), this);
        Bukkit.getPluginManager().registerEvents(new CustomDeathMessage(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerChangeWorldEvent(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerToPlayerDamage(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerStats(), this);

        getLogger().info("[Loader] Events loaded.");

        getCommand("ktemanhunt").setExecutor(new KteManhuntCommand(this));
        getLogger().info("[Loader] Commands loaded.");

        GameSystem.init();
        for(World world : Bukkit.getWorlds()) {
            world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
        }

        getConfig().options().copyDefaults();
        saveDefaultConfig();
        MessagesConfig.setup(this);
        PlayerStats.setup(this);
        getLogger().info("[Loader] Configs loaded.");

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new PlaceHolder().register();
        }

        getLogger().warning("[Warner] WARNING! This plugin is BETA");
        getLogger().warning("[Warner] If found any bugs, you can send Kte Project Discord Server!");
        getLogger().info("[Loader] KteManhunt enabled");
        Logger logger = this.getLogger();

        new UpdateChecker(this, 121632).getVersion(version -> {
            String currentVersion = this.getDescription().getVersion();

            if (currentVersion.equals(version)) {
                logger.info(ChatColor.GREEN + "✔ The plugin is up-to-date! (Version: " + currentVersion + ")");
            } else {
                String updateMessage = ChatColor.DARK_RED + "" + ChatColor.RESET +
                        "\n" + ChatColor.RED + "✨ A New Update is Available! ✨" + ChatColor.RESET +
                        "\n" + ChatColor.YELLOW + "Your version: " + ChatColor.WHITE + currentVersion +
                        "\n" + ChatColor.YELLOW + "Latest version: " + ChatColor.WHITE + version +
                        "\n" + ChatColor.AQUA + "Download the latest update at:" +
                        "\n" + ChatColor.BLUE + "https://www.spigotmc.org/resources/121632/" + ChatColor.RESET +
                        "\n" + ChatColor.DARK_RED + "===============================";

                logger.warning(updateMessage);

                Bukkit.getPluginManager().registerEvents(new Listener() {
                    @EventHandler
                    public void onPlayerJoin(PlayerJoinEvent event) {
                        Player player = event.getPlayer();
                        if (player.isOp()) {
                            player.sendMessage(ChatColor.RED + "⚠ [Plugin Update Alert] ⚠");
                            player.sendMessage(updateMessage);
                        }
                    }
                }, this);
            }
        });



    }

    @Override
    public void onDisable() {
        getLogger().info("KteManhunt disabled");
    }

    public static FileConfiguration getConfiguration() {
        return Bukkit.getPluginManager().getPlugin("KteManhunt").getConfig();
    }

}
