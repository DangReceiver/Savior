package de.tdf.savior;

import de.tdf.cmd.*;
import de.tdf.listener.*;
import de.tdf.language.Language;
import de.tdf.worlds.Spawn;
import org.bukkit.*;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public final class Savior extends JavaPlugin {

	private static Savior savior;
	public static String version;

	@Override
	public void onEnable() {
		savior = this;
		version = getVersion();

		ConsoleCommandSender cs = Bukkit.getConsoleSender();
		Bukkit.getWorld("world").setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);

		if (!Spawn.checkExists()) {
			cs.sendMessage(Language.PRE + "The world \"Spawn\" could not be loaded. The plugin will be disabled.");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}

		if (Language.updateServerLang()) if (Language.updateServerLang()) {
			cs.sendMessage(Language.PRE + "The server language could not be estimated. Stopping plugin load process.");
			return;
		}

		Language.loadMessages();

		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new ConnectionManager(), this);
		pm.registerEvents(new FireWorkJump(), this);
		pm.registerEvents(new SneakEvent(), this);
		pm.registerEvents(new Chat(), this);
		pm.registerEvents(new Death(), this);
		pm.registerEvents(new StartToSprint(), this);
		pm.registerEvents(new EffectHeadBottle(), this);
		pm.registerEvents(new PreDeath(), this);
		pm.registerEvents(new Respawn(), this);
		pm.registerEvents(new Teleport(), this);
		pm.registerEvents(new SpawnButtonPush(), this);
		pm.registerEvents(new EntityDamageDisplay(), this);
		pm.registerEvents(new TreeCutDown(), this);
		pm.registerEvents(new Doors(), this);
		pm.registerEvents(new CreeperActivateCreeper(), this);
		pm.registerEvents(new SpawnProtection(), this);
//		pm.registerEvents(new ToSaviorCommand(), this);

		Objects.requireNonNull(getCommand("SetLanguage")).setExecutor(new SetLanguage());
		Objects.requireNonNull(getCommand("SetLanguage")).setTabCompleter(new SetLanguage());
		Objects.requireNonNull(getCommand("TicksPerSecond")).setExecutor(new TicksPerSecond());
		Objects.requireNonNull(getCommand("ArrowsInBody")).setExecutor(new ArrowsInBody());
		Objects.requireNonNull(getCommand("ClearChat")).setExecutor(new ClearChat());
		Objects.requireNonNull(getCommand("Ench")).setExecutor(new Ench());
		Objects.requireNonNull(getCommand("Fly")).setExecutor(new Fly());
		Objects.requireNonNull(getCommand("Gm")).setExecutor(new Gm());
		Objects.requireNonNull(getCommand("Heal")).setExecutor(new Heal());
		Objects.requireNonNull(getCommand("Info")).setExecutor(new Info());
		Objects.requireNonNull(getCommand("Invsee")).setExecutor(new Invsee());
		Objects.requireNonNull(getCommand("Speed")).setExecutor(new Speed());
		Objects.requireNonNull(getCommand("TpExact")).setExecutor(new TpExact());
		Objects.requireNonNull(getCommand("TpWorld")).setExecutor(new TpWorld());

		for (Player ap : Bukkit.getOnlinePlayers())
			Language.setLang(ap, Language.getLangFile("en"));


		for (Entity as : Bukkit.getWorld("world").getEntities())
			if (as instanceof ArmorStand) as.remove();
		for (Entity as : Bukkit.getWorld("Spawn").getEntities())
			if (as instanceof ArmorStand) as.remove();
	}

	@Override
	public void onDisable() {
		for (Player ap : Bukkit.getOnlinePlayers())
			Language.removePlayer(ap);

		savior = null;
	}

	public synchronized String getVersion() {
		String v = null;
		try {
			Properties p = new Properties();
			InputStream is = getClass().getResourceAsStream("/META-INF/maven/de.tdf/Savior/pom.properties");
			if (is != null) {
				p.load(is);
				v = p.getProperty("version", "");
			}
		} catch (Exception ignored) {
		}
		if (v == null) {
			Package ap = getClass().getPackage();
			if (ap != null) {
				v = ap.getImplementationVersion();
				if (v == null)
					v = ap.getSpecificationVersion();
			}
		}
		return v;
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

	public static Location getSafeSpawnLocation() {
		FileConfiguration c = Savior.getSavior().getConfig();
		Location spawnLoc = c.getLocation("Locations.Spawn");

		if (spawnLoc == null) {
			spawnLoc = new Location(Bukkit.getWorld("Spawn"), 0, 64.02, 0);

			c.set("Locations.Spawn", spawnLoc);
			Savior.getSavior().saveConfig();
		}
		return spawnLoc;
	}
}

