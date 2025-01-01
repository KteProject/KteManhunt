package kteproject.ktemanhunt;

import kteproject.ktemanhunt.Commands.KteManhuntCommand;
import kteproject.ktemanhunt.Listeners.*;
import kteproject.ktemanhunt.Managers.AutoStart;
import kteproject.ktemanhunt.Managers.Compass;
import kteproject.ktemanhunt.Managers.GameSystem;
import kteproject.ktemanhunt.Managers.MessagesConfig;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class KteManhunt extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("KteManhunt is Enabling...");

        Bukkit.getPluginManager().registerEvents(new Compass(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
        Bukkit.getPluginManager().registerEvents(new GameSystem(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDamage(), this);
        Bukkit.getPluginManager().registerEvents(new DragonDeath(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerLeave(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeath(this), this);
        Bukkit.getPluginManager().registerEvents(new AutoStart(this), this);
        Bukkit.getPluginManager().registerEvents(new CustomDeathMessage(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerChangeWorldEvent(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerToPlayerDamage(), this);

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
        getLogger().info("[Loader] Configs loaded.");

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new PlaceHolder().register();
        }

        getLogger().warning("[Warner] WARNING! This plugin is BETA");
        getLogger().warning("[Warner] If found any bugs, you can send Kte Project Discord Server!");
        getLogger().info("[Loader] KteManhunt enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("KteManhunt disabled");
    }

    public static FileConfiguration getConfiguration() {
        return Bukkit.getPluginManager().getPlugin("KteManhunt").getConfig();
    }

}
