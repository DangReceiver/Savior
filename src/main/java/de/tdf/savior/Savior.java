package de.tdf.savior;

import de.tdf.listener.FireWorkJump;
import de.tdf.listener.PlayerConnection;
import de.tdf.language.Language;
import de.tdf.listener.SneakEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Savior extends JavaPlugin {

    private static Savior savior;

    @Override
    public void onEnable() {
        savior = this;
        ConsoleCommandSender cs = Bukkit.getConsoleSender();

        if (!Language.updateServerLang() && !Language.updateServerLang()) {
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        if (!Language.loadMessages()) {
            cs.sendMessage("Could not load plugin messages!");
            return;
        }

        Bukkit.getPluginManager().registerEvents(new PlayerConnection(), this);
        Bukkit.getPluginManager().registerEvents(new FireWorkJump(), this);
        Bukkit.getPluginManager().registerEvents(new SneakEvent(), this);

        for (Player ap : Bukkit.getOnlinePlayers())
            Language.setLang(ap, Language.getLangFile("en"));

        Language.broadcast("plugin_loaded");
    }

    @Override
    public void onDisable() {
        savior = null;

        for (Player ap : Bukkit.getOnlinePlayers())
            Language.removePlayer(ap);

        ConsoleCommandSender cs = Bukkit.getConsoleSender();
        Language.broadcast("plugin_unloaded");
    }

    public static Plugin getSavior() {
        return savior;
    }
}
