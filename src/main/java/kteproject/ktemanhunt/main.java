package kteproject.ktemanhunt;

import kteproject.ktemanhunt.Command.Command;
import kteproject.ktemanhunt.Functions.AutoStart;
import kteproject.ktemanhunt.Functions.MessagesConfig;
import kteproject.ktemanhunt.Functions.StartGame;
import kteproject.ktemanhunt.Listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.plugin.java.JavaPlugin;

public final class main extends JavaPlugin {

    public boolean inGame;

    @Override
    public void onEnable() {
        System.out.println(ChatColor.GOLD + "[KteProject]" + ChatColor.GREEN + "Plugin Enabled!");
        System.out.println("Thanks for using our plugin!!");

        StartGame.SpeedRunnerTeam.clear();
        StartGame.HunterTeam.clear();

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new Placeholder().register();
        } else {
            getLogger().warning(ChatColor.RED +"PlaceholderAPI not found. Some features do not work!");
        }

        World world = Bukkit.getWorld("world");
        WorldBorder worldBorder = world.getWorldBorder();
        worldBorder.setSize(32);
        worldBorder.setCenter(0, 0);
        worldBorder.setDamageAmount(5);
        worldBorder.setDamageBuffer(2);

        StartGame.match = false;

        //Messages.yml
        MessagesConfig.setup();

        MessagesConfig.get().addDefault("prefix","&6[KteRising] &r");
        MessagesConfig.get().addDefault("title.start-game.title","&6GAME STARTED");
        MessagesConfig.get().addDefault("title.game-over-hunter.title","&6GAME OVER!");
        MessagesConfig.get().addDefault("title.game-over-hunter.sub","&6Hunters won!");
        MessagesConfig.get().addDefault("title.game-over-speedrunner.title","&6GAME OVER!");
        MessagesConfig.get().addDefault("title.game-over-speedrunner.sub","&6SpeedRunners won!");
        MessagesConfig.get().addDefault("title.eliminated.title","&cYou Are Eliminated!");
        MessagesConfig.get().addDefault("title.eliminated.sub","&6Another Time..");
        MessagesConfig.get().addDefault("title.hunter-select","&6You are the HUNTER!");
        MessagesConfig.get().addDefault("title.speedrunner-select","&6You are the SPEEDRUNNER!");
        MessagesConfig.get().addDefault("autostart-message","&6Starting...");
        MessagesConfig.get().addDefault("message.command-title","&8&m-----------&6KteManhunt-----------");
        MessagesConfig.get().addDefault("message.dont-permission","&4You do''nt have permission.");
        MessagesConfig.get().addDefault("message.already-started","&4Game already started.");
        MessagesConfig.get().addDefault("message.plugin-reload","&aPlugin Reloaded");
        MessagesConfig.get().addDefault("message.command-game-started","&4Game already started.");
        MessagesConfig.get().addDefault("message.speedrunner-died","&adied!");

        MessagesConfig.get().options().copyDefaults(true);
        MessagesConfig.save();

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        //Commands
        getCommand("ktemanhunt").setExecutor(new Command(this));

        //Listeners
        Bukkit.getPluginManager().registerEvents(new AutoStart(this), this);
        Bukkit.getPluginManager().registerEvents(new NightVision(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuit(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDamage(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeath(this),this);
        Bukkit.getPluginManager().registerEvents(new StartGame(this),this);
        Bukkit.getPluginManager().registerEvents(new PiglinPearlBoost(this),this);
        Bukkit.getPluginManager().registerEvents(new Compass(this),this);

    }

    @Override
    public void onDisable() {
        System.out.println(ChatColor.GOLD + "[KteProject]" + ChatColor.RED + "Plugin Disabled!");
    }
}
