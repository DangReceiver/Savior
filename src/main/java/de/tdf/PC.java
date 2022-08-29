package de.tdf;

import com.google.errorprone.annotations.Var;
import de.tdf.savior.Savior;
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
	YamlConfiguration config;
	File file;

	public PC(final OfflinePlayer p) {
		final String uuid = p.getUniqueId().toString();
		this.file = new File("plugins/Savior/players/" + uuid + ".yml");
		this.config = YamlConfiguration.loadConfiguration(this.file);
	}

	public YamlConfiguration getConfig() {
		return config;
	}

	public File getFile() {
		return this.file;
	}

	public static PC loadConfig(final OfflinePlayer p) {
		if (!hasConfig(p)) {
			return createConfig(p);
		}
		return new PC(p);
	}

	public static boolean hasConfig(final OfflinePlayer p) {
		final String uuid = p.getUniqueId().toString();
		return new File("plugins/Savior/players/" + uuid + ".yml").exists();
	}

	public static PC createConfig(final OfflinePlayer p) {
		final String uuid = p.getUniqueId().toString();
		final File file = new File("plugins/Savior/players/" + uuid + ".yml");

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				if (!Savior.createFolder("plugins/Savior/players")) {
					try {
						file.createNewFile();
					} catch (IOException ignored) {
					}
				}
			}
		}
		return new PC(p);
	}

	public ConfigurationSection getConfigurationSection(final String string) {
		return this.config.getConfigurationSection(string);
	}

	public void setTrue(final String string) {
		this.config.set(string, (Object) true);
	}

	public void setFalse(final String string) {
		this.config.set(string, (Object) false);
	}

	public void setNull(final String string) {
		this.config.set(string, (Object) null);
	}

	public boolean isSet(final String string) {
		return this.config.isSet(string);
	}

	public boolean getBoolean(final String string) {
		return this.config.getBoolean(string);
	}

	public boolean hasWorldID() {
		return this.config.isSet("pWorld.OwningID");
	}

	public int getWorldID() {
		return this.config.getInt("pWorld.OwningID");
	}

	public void setWorldID(final int newWID) {
		this.config.set("pWorld.OwningID", (Object) newWID);
	}

	public boolean createdBefore() {
		return this.config.getBoolean("pWorld.createdBefore");
	}

	public void set(final String string, final Object arg1) {
		this.config.set(string, arg1);
	}

	public String getString(final String string) {
		return this.config.getString(string);
	}

	public List<String> getStringList(final String string) {
		return (List<String>) this.config.getStringList(string);
	}

	public double getTokens() {
		return this.config.isSet("Player.Stats.Tokens") ? this.config.getDouble("Player.Stats.Tokens") : 0.0;
	}

	public Location getLocation(final String loc) {
		return this.config.isSet(loc) ? this.config.getLocation(loc) : null;
	}

	public float getFloat(final String string) {
		return (float) this.config.getDouble(string);
	}

	public int getInt(final String string) {
		return this.config.getInt(string);
	}

	public long getLong(final String string) {
		return this.config.getLong(string);
	}

	public Set<String> getKeys(final boolean deep) {
		return (Set<String>) this.config.getKeys(deep);
	}

	public double getDouble(final String string) {
		return this.config.getDouble(string);
	}

	public int getSafeInt(final String string) {
		return config.isSet(string) ? config.getInt(string) : 0;
	}

	public void addTokens(int i) {
		i += getTokens();
		if (i < 0) i = 0;
		this.config.set("Player.Stats.Tokens", i);
	}

	public void removeTokens(int i) {
		i -= getTokens();
		if (i < 0) i = 0;
		this.config.set("Player.Stats.Tokens", i);
	}

	public void setTokens(double i) {
		i = Math.round(i * 1000);
		this.config.set("Player.Stats.Tokens", i / 1000);
		this.savePCon();
	}

	public ItemStack getItemStack(final String s) {
		return config.isSet(s) ? config.getItemStack(s) : new ItemStack(Material.AIR);
	}

	public boolean setDefaultMessageColor(int r, int g, int b) {
		if (r > 255 || r < 0 || g > 255 || g < 0 || b > 255 || b < 0) return false;

		List<Integer> rgb = new ArrayList<>(Arrays.asList(r, g, b));
		config.set("Customization.ChatColor", rgb);
		return true;
	}

	public List<Integer> getDefaultMessageColor() {
		return config.isSet("Customization.ChatColor") ? config.getIntegerList("Customization.ChatColor") :
				Arrays.asList(210, 210, 210);
	}

	public boolean setDefaultMessageColor(List<Integer> rgb) {
		if (rgb.size() != 2) return false;
		if (rgb.get(0) > 255 || rgb.get(0) < 0 || rgb.get(1) > 255 || rgb.get(1) < 0 || rgb.get(2) > 255 || rgb.get(2) < 0)
			return false;

		config.set("Customization.ChatColor", rgb);
		return true;
	}

//	public String getDefaultMessageColor() {
//		return config.isSet("Customization.ChatColor") ? config.getString("Customization.ChatColor") : "§7";
//	}

	public String getPlayerColor() {
		return config.isSet("Customization.Color") ? config.getString("Customization.Color") : "§7";
	}

	public String getCounterLink() {
		return config.isSet("Customization.Link.Counter") ? config.getString("Customization.Link.Counter") : "§8";
	}

	public String getLink() {
		return config.isSet("Customization.Link.Default") ? config.getString("Customization.Link.Default") : "§8:";
	}

	public List<String> getLinks() {
		return config.isSet("Customization.Link") ? new ArrayList<>(Arrays.asList(getCounterLink(), getLink()))
				: new ArrayList<>(Arrays.asList("", "§8:"));
	}

	public void savePCon() {
		try {
			config.save(this.file);
		} catch (IOException ignored) {
		}
	}

	public void savePConErrorFree() {
		try {
			this.config.save(this.file);
		} catch (IOException ignored) {
		}
	}
}
