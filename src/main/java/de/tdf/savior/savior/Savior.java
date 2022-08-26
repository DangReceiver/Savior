package de.tdf.savior.savior;

import de.tdf.de.tdf.listener.Join;
import de.tdf.language.Language;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Savior extends JavaPlugin {

    private static Savior savior;

    @Override
    public void onEnable() {
        ConsoleCommandSender cs = Bukkit.getConsoleSender();
        Language.broadcast("loaded_successfully");

        Bukkit.getPluginManager().registerEvents(new Join(), this);

        savior = this;
    }

    @Override
    public void onDisable() {
        ConsoleCommandSender cs = Bukkit.getConsoleSender();
        Language.broadcast("loaded_successfully");

        savior = null;
    }

    public static Plugin getSavior() {
        return savior;
    }
}
