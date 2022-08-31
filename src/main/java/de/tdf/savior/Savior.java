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

		if (Language.updateServerLang()) {
			cs.sendMessage("hi1");
			if (Language.updateServerLang()) {
				cs.sendMessage("hi2");
				return;
			}
		}
		Language.loadMessages();

		Bukkit.getPluginManager().registerEvents(new PlayerConnection(), this);
		Bukkit.getPluginManager().registerEvents(new FireWorkJump(), this);
		Bukkit.getPluginManager().registerEvents(new SneakEvent(), this);
		Bukkit.getPluginManager().registerEvents(new Chat(), this);
		Bukkit.getPluginManager().registerEvents(new Death(), this);
		Bukkit.getPluginManager().registerEvents(new StartToSprint(), this);
		Bukkit.getPluginManager().registerEvents(new EffectHeadBottle(), this);

		Objects.requireNonNull(getCommand("SetLanguage")).setExecutor(new SetLanguage());
		Objects.requireNonNull(getCommand("SetLanguage")).setTabCompleter(new SetLanguage());

		for (Player ap : Bukkit.getOnlinePlayers())
			Language.setLang(ap, Language.getLangFile("en"));
	}

	@Override
	public void onDisable() {
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
			System.out.println(Language.getMessage(Language.getServerLang(), "warn") + Language.getMessage(Language.getServerLang(), "file_creation_exists"));
			return false;
		}

		if (!f1.mkdir()) {
			System.out.println(Language.getMessage(Language.getServerLang(), "error") + Language.getMessage(Language.getServerLang(), "file_creation_error"));
			return false;
		}
		return true;
	}
}
