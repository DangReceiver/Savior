package de.tdf.language;

import de.tdf.savior.Savior;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.List;

public class Language {

	// ⌈⌋ | ⌜⌟

	public static String PRE = "§8⌜" + colorFromRGB(245, 25, 125) + "Savior§8⌟ §7";

	public static File sLang;
	private static final File df = Savior.getSavior().getDataFolder();

	private static final Map<Player, File> settings = new HashMap<>();
	private static final Map<File, Map<String, String>> messages = new HashMap<>();

	public static net.md_5.bungee.api.ChatColor colorFromRGB(int r, int g, int b) {
		return net.md_5.bungee.api.ChatColor.of(new Color(r, g, b));
	}

	public static boolean isLangFile(String lang) {
		return new File(df + "/language", lang + ".yml").exists();
	}

	public static File getLangFile(String lang) {
		return new File(df + "/language", lang + ".yml");
	}

	public static boolean validString(File lang, String key) {
		return messages.get(lang) != null && messages.get(lang).get(key) != null;
	}

	public static String getMessage(File f, String key) {
		return validString(f, key) ? messages.get(f).get(key)
				: validString(getServerLang(), key) ? messages.get(getServerLang()).get(key)
				: validString(f, "string_not_found")
				? String.format(messages.get(f).get("string_not_found"), key) :
				String.format(messages.get(getServerLang()).get("string_not_found"), key);
	}

	public static String getMessage(String fName, String key) {
		File lf = getLangFile(fName);
		return messages.get(lf) != null && messages.get(lf).get(key) != null ? messages.get(lf).get(key) :
				validString(getServerLang(), key) ? messages.get(getServerLang()).get(key) :
						validString(lf, "string_not_found") ? messages.get(lf).get("string_not_found") :
								String.format(messages.get(getServerLang()).get("string_not_found"), key);
	}

	public static File getServerLang() {
		return sLang;
	}

	public static List<File> getLanguages() {
		List<File> lf = new ArrayList<>();

		if (new File(df + "/language").listFiles() == null) return null;
		Collections.addAll(lf, new File(df + "/language").listFiles());

		return lf;
	}

	public static void broadcast(String key) {
		Bukkit.getConsoleSender().sendMessage(getMessage(getServerLang(), "info") +
				getMessage(getServerLang(), key));

		for (Player ap : Bukkit.getOnlinePlayers())
			ap.sendMessage(PRE + getMessage(getLang(ap), key));
	}

	public static void broadcastArg(String key, String... arg) {
		Bukkit.getConsoleSender().sendMessage(getMessage(getServerLang(), "info") +
				String.format(getMessage(getServerLang(), key), arg));

		for (Player ap : Bukkit.getOnlinePlayers()) {
			String format = "";

			for (String ts : arg) format = String.format(getMessage(getLang(ap), key), ts);
			ap.sendMessage(PRE + format);
		}
	}

	public static boolean hasLang(Player p) {
		return settings.get(p) != null;
	}

	public static File getLang(Player p) {
		return !settings.isEmpty() && settings.get(p) != null ? settings.get(p) : getServerLang();
	}

	public static boolean setServerLang(File language) {
		Plugin s = Savior.getSavior();
		FileConfiguration c = s.getConfig();

		List<String> langFiles = new ArrayList<>();
		File folder = new File(df + "/language");

		if (folder.listFiles() == null) return false;
		for (File file : folder.listFiles())
			langFiles.add(file.getName());

		if (language == null) {

			if (!c.isSet("server_language")) {
				c.set("server_language", "en");
				s.saveConfig();
			}

			if (!langFiles.contains(c.getString("server_language") + ".yml")) return false;
			sLang = new File(df + "/language", c.getString("server_language") + ".yml");

		} else if (langFiles.contains(language.getName())) sLang = language;

		return sLang.exists();
	}

	public static void setLang(Player p, File file) {
		removePlayer(p);

		if (!file.exists()) {
			File lang = getLangFile("en");
			settings.put(p, lang);

		} else
			settings.put(p, file);
	}

	public static void removePlayer(Player p) {
		settings.remove(p);
	}

	public static boolean updateServerLang() {
		FileConfiguration c = Savior.getSavior().getConfig();
		File sl = getLangFile(c.getString("server_language"));

		if (sl == null) {
			c.set("server_language", "en");
			Savior.getSavior().saveConfig();

			sLang = getLangFile(c.getString("server_language"));
			return true;
		}

		sLang = sl;
		return false;
	}

	public static File langFolder() {
		Plugin s = Savior.getSavior();
		File langFolder = new File(s.getDataFolder() + "/language");

		if (!langFolder.exists()) {
			s.getDataFolder().mkdir();
			langFolder.mkdir();
		}

		return langFolder;
	}

	public static void loadResources(Plugin sa, File langF) {
		List<String> resources = new ArrayList<>(Arrays.asList("de.yml", "en.yml"));

		for (String s : resources) {

			File rf = new File(langF, s);
			Path target = rf.toPath();
			InputStream in = sa.getResource(s);

			ConsoleCommandSender cs = Bukkit.getConsoleSender();
			File sl = Language.getServerLang();

			if (in == null) {
				Bukkit.getScheduler().runTaskLater(Savior.getSavior(), () -> cs.sendMessage(Language.getMessage(sl,
						"info") + Language.getMessage(sl, "invalid_language_resource")), 1);
				return;
			}

			try {
				if (rf.length() < sa.getResource(s).available() &&
						sa.getResource(s).available() - rf.length() >= 25) {
					if (rf.delete()) Files.copy(in, target);

					Bukkit.getScheduler().runTaskLater(Savior.getSavior(), () -> cs.sendMessage(Language.getMessage(sl,
							"info") + String.format(Language.getMessage(sl, "replacing_lang_file"),
							rf.getName().split(".")[0])), 1);
					continue;
				}

				Files.copy(in, target);
				Bukkit.getScheduler().runTaskLater(Savior.getSavior(), () -> cs.sendMessage(Language.getMessage(sl,
						"info") + Language.getMessage(sl, "creating_lang_resource")), 1);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void loadCustomLanguages(Plugin s, File langF) {
		for (File file : langF.listFiles()) {
			if (s.getResource(file.getName()) != null) loadResources(s, new File(s.getDataFolder() + "/language"));

			Map<String, String> lm = new HashMap<>();
			FileConfiguration lang = YamlConfiguration.loadConfiguration(file);

			for (String key : lang.getKeys(false)) {
				for (String messName : lang.getConfigurationSection(key).getKeys(false)) {

					String message = ChatColor.translateAlternateColorCodes('§',
							lang.getString(key + "." + messName));
					lm.put(messName, message);
				}
			}

			messages.put(file, lm);
			if (!isValidLang(file)) continue;

			Bukkit.getConsoleSender().sendMessage(PRE +
					String.format(getMessage(file, "language_loaded"), file.getName()));
		}
	}

	public static void loadMessages() {
		Plugin s = Savior.getSavior();
		File langF = langFolder();

		loadResources(s, langF);
		loadCustomLanguages(s, langF);

		if (getServerLang() == null) setServerLang(new File(langF, "en.yml"));
	}

	public static boolean isValidLang(File lang) {
		if (!validString(lang, "string_not_found")) {
			Bukkit.getConsoleSender().sendMessage(PRE + String.format(
					"The language %s could not be loaded. There might be missing Strings!", lang.getName()));
			return false;
		}
		return true;
	}

	public static boolean compareLanguages(File main, File comp) {
		long ms = main.getTotalSpace(), cs = comp.getTotalSpace();
		return ((ms - cs) <= 250);
	}
}
