package de.tdf;

import com.google.errorprone.annotations.Var;
import de.tdf.language.Language;
import de.tdf.savior.Savior;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class PC {
	YamlConfiguration c;
	File f;

	public PC(final OfflinePlayer p) {
		final String uuid = p.getUniqueId().toString();
		this.f = new File("plugins/Savior/players/" + uuid + ".yml");
		this.c = YamlConfiguration.loadConfiguration(this.f);
	}

	public YamlConfiguration getConfig() {
		return c;
	}

	public File getFile() {
		return this.f;
	}

	public static PC loadConfig(final OfflinePlayer p) {
		if (!hasConfig(p)) {
			return createConfig(p);
		}
		return new PC(p);
	}

	public static boolean hasConfig(final OfflinePlayer p) {
		return new File("plugins/Savior/players/" + p.getUniqueId().toString() + ".yml").exists();
	}

	public static PC createConfig(final OfflinePlayer p) {
		final File f = new File("plugins/Savior/players/" + p.getUniqueId().toString() + ".yml");

		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				if (!Savior.createFolder("plugins/Savior/players")) {
					try {
						f.createNewFile();
					} catch (IOException ignored) {
					}
				}
			}
		}
		return new PC(p);
	}

	public ConfigurationSection getConfigurationSection(final String string) {
		return this.c.getConfigurationSection(string);
	}

	public void setTrue(final String string) {
		this.c.set(string, (Object) true);
	}

	public void setFalse(final String string) {
		this.c.set(string, (Object) false);
	}

	public void setNull(final String string) {
		this.c.set(string, (Object) null);
	}

	public boolean isSet(final String string) {
		return this.c.isSet(string);
	}

	public boolean getBoolean(final String string) {
		return this.c.getBoolean(string);
	}

	public boolean hasWorldID() {
		return this.c.isSet("pWorld.OwningID");
	}

	public int getWorldID() {
		return this.c.getInt("pWorld.OwningID");
	}

	public void setWorldID(final int newWID) {
		this.c.set("pWorld.OwningID", (Object) newWID);
	}

	public boolean createdBefore() {
		return this.c.getBoolean("pWorld.createdBefore");
	}

	public void set(final String string, final Object arg1) {
		this.c.set(string, arg1);
	}

	public String getString(final String string) {
		return this.c.getString(string);
	}

	public List<String> getStringList(final String string) {
		return (List<String>) this.c.getStringList(string);
	}

	public double getTokens() {
		return this.c.isSet("Player.Stats.Tokens") ? this.c.getDouble("Player.Stats.Tokens") : 0.0;
	}

	public Location getLocation(final String loc) {
		return this.c.isSet(loc) ? this.c.getLocation(loc) : null;
	}

	public float getFloat(final String string) {
		return (float) this.c.getDouble(string);
	}

	public int getInt(final String string) {
		return this.c.getInt(string);
	}

	public long getLong(final String string) {
		return this.c.getLong(string);
	}

	public Set<String> getKeys(final boolean deep) {
		return (Set<String>) this.c.getKeys(deep);
	}

	public double getDouble(final String string) {
		return this.c.getDouble(string);
	}

	public int getSafeInt(final String string) {
		return c.isSet(string) ? c.getInt(string) : 0;
	}

	public void addTokens(int i) {
		i += getTokens();
		if (i < 0) i = 0;
		this.c.set("Player.Stats.Tokens", i);
	}

	public void removeTokens(int i) {
		i -= getTokens();
		if (i < 0) i = 0;
		this.c.set("Player.Stats.Tokens", i);
	}

	public void setTokens(double i) {
		i = Math.round(i * 1000);
		this.c.set("Player.Stats.Tokens", i / 1000);
		this.savePCon();
	}

	public ItemStack getItemStack(final String s) {
		return c.isSet(s) ? c.getItemStack(s) : new ItemStack(Material.AIR);
	}

	public boolean setDefaultMessageColor(int r, int g, int b) {
		if (r > 255 || r < 0 || g > 255 || g < 0 || b > 255 || b < 0) return false;

		c.set("Customization.Chat.ChatColor", new ArrayList<>(Arrays.asList(r, g, b)));
		return true;
	}

	public boolean setDefaultMessageColor(List<Integer> rgb) {
		if (rgb.size() != 2 || rgb.get(0) > 255 || rgb.get(0) < 0 || rgb.get(1) > 255 || rgb.get(1) < 0 || rgb.get(2) > 255 || rgb.get(2) < 0)
			return false;

		c.set("Customization.Chat.ChatColor", rgb);
		return true;
	}

	public List<Integer> getDefaultMessageColor() {
		return c.isSet("Customization.Chat.ChatColor") ? c.getIntegerList("Customization.Chat.ChatColor") :
				Arrays.asList(210, 210, 210);
	}

//	public String getDefaultMessageColor() {
//		return c.isSet("Customization.Chat.ChatColor") ? c.getString("Customization.Chat.ChatColor") : "§7";
//	}

	public String getLanguageString() {
		return c.getString("Customization.Language");
	}

	public File getLanguage() {
		return Language.getLangFile(getLanguageString() != null ? getLanguageString() : Language.getServerLang().getName().split(".")[0]);
	}

	public boolean setLanguage(File f) {
		if (Language.isValidLang(f)) {
			c.set("Customization.Language", f.getName().split("\\.")[0]);
			return true;
		}
		return false;
	}

	public String getPlayerColor() {
		return c.isSet("Customization.Chat.Color") ? c.getString("Customization.Chat.Color") : "§7";
	}

	public String getCounterLink() {
		return c.isSet("Customization.Chat.Link.Counter") ? c.getString("Customization.Chat.Link.Counter") : "§8";
	}

	public String getLink() {
		return c.isSet("Customization.Chat.Link.Default") ? c.getString("Customization.Chat.Link.Default") : "§8:";
	}

	public List<String> getLinks() {
		return c.isSet("Customization.Chat.Link") ? new ArrayList<>(Arrays.asList(getCounterLink(), getLink()))
				: new ArrayList<>(Arrays.asList("", "§8:"));
	}

	public void savePCon() {
		try {
			c.save(this.f);
		} catch (IOException e) {
			Bukkit.getConsoleSender().sendMessage(Language.getMessage(Language.getServerLang(), "info")
					+ Language.getMessage(Language.getServerLang(), "player_config_saving_error"));
		}
	}

	public void savePConErrorFree() {
		try {
			this.c.save(this.f);
		} catch (IOException ignored) {
		}
	}
}
