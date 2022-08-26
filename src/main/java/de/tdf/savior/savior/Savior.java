package de.tdf.savior.savior;

import de.tdf.de.tdf.listener.PlayerConnection;
import de.tdf.language.Language;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
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
        Language.broadcast("plugin_loaded");
    }

    @Override
    public void onDisable() {
        ConsoleCommandSender cs = Bukkit.getConsoleSender();
        Language.broadcast("plugin_unloaded");

        savior = null;
    }

    public static Plugin getSavior() {
        return savior;
    }
}
