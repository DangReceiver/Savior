package de.tdf.savior;

import de.tdf.cmd.SetLanguage;
import de.tdf.listener.*;
import de.tdf.language.Language;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;

public final class Savior extends JavaPlugin {

	private static Savior savior;

	@Override
	public void onEnable() {
		savior = this;
		ConsoleCommandSender cs = Bukkit.getConsoleSender();

		if (Language.updateServerLang())
			if (Language.updateServerLang()) {

				return;
			}

		if (!Language.loadMessages()) {
			cs.sendMessage("Could not load plugin messages!");
			return;
		}

		Bukkit.getPluginManager().registerEvents(new PlayerConnection(), this);
		Bukkit.getPluginManager().registerEvents(new FireWorkJump(), this);
		Bukkit.getPluginManager().registerEvents(new SneakEvent(), this);
		Bukkit.getPluginManager().registerEvents(new Chat(), this);
		Bukkit.getPluginManager().registerEvents(new Death(), this);

		Objects.requireNonNull(getCommand("SetLanguage")).setExecutor(new SetLanguage());
		Objects.requireNonNull(getCommand("SetLanguage")).setTabCompleter(new SetLanguage());

		for (Player ap : Bukkit.getOnlinePlayers())
			Language.setLang(ap, Language.getLangFile("en"));

		Language.broadcast("plugin_loaded");
	}

	@Override
	public void onDisable() {
		ConsoleCommandSender cs = Bukkit.getConsoleSender();
		cs.sendMessage(Language.getMessage(Language.getServerLang(), "plugin_unloaded"));

		for (Player ap : Bukkit.getOnlinePlayers())
			Language.removePlayer(ap);

		savior = null;
	}

	public static Plugin getSavior() {
		return savior;
	}

	public static boolean createFolder(String path) {
		if (path == null)
			path = "plugins/Savior";
		File f1 = new File(path);
		if (f1.exists()) {
			System.out.println("The folder already exists.");
			return false;
		}
		if (!f1.mkdir()) {
			System.out.println("The folder could not be created.");
			return false;
		}
		return true;
	}
}
