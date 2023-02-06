package de.tdf;

import de.tdf.language.Language;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Holo {
    YamlConfiguration c;
    File f;
    ArmorStand as;
    UUID uuid;
    String name;

    public Holo(final UUID pUuid) {
        as = (ArmorStand) Bukkit.getEntity(uuid = pUuid);
        name = getName();

        this.f = new File("plugins/Savior/holograms.yml");
        this.c = YamlConfiguration.loadConfiguration(this.f);
    }

    public YamlConfiguration getConfig() {
        return c;
    }

    public File getFile() {
        return this.f;
    }

    public ArmorStand getArmorStand() {
        return as;
    }

    public void loadFrom(Player p, String pName) {
        name = pName;
        uuid = getUuidFrom(p);
        as = (ArmorStand) Bukkit.getEntity(uuid);
    }

    public UUID getUuid() {
        return UUID.fromString(getString(as.getWorld().getName() + "-" + name + ".uuid"));
    }

    public UUID getUuidFrom(Player p) {
        return UUID.fromString(getString(p.getWorld().getName() + "-" + name + ".uuid"));
    }

    public void remove() {
        as.remove();
        c.set(as.getWorld().getName() + "-" + name , as);
    }

    public void setUp(String pName, String pDisplay) {
        String s = as.getWorld().getName() + "-" + name + ".";
        rename(pName);

        if (pDisplay == null) pDisplay = "default display";

        c.set(s + ".uuid", as.getUniqueId());
        c.set(s + ".display", pDisplay);
        c.set(s + ".date", System.currentTimeMillis());
        c.set(s + ".location", as.getLocation());
    }

    public void setDefault() {
        as.setMarker(true);
        as.setSmall(true);
        as.setInvulnerable(true);

        as.setCustomName("default display");
        as.setCustomNameVisible(true);
    }

    public String getName() {
        return name != null ? name : "default name";
    }

    public void rename(String pName) {
        name = pName;
    }

    public static Holo loadConfig(final UUID pUuid) {
        if (!hasConfig(pUuid))
            return createConfig(pUuid);
        return new Holo(pUuid);
    }

    public static boolean hasConfig(final UUID pUuid) {
        return new File("plugins/Savior/holograms.yml").exists();
    }

    public static Holo createConfig(final UUID pUuid) {
        final File f = new File("plugins/Savior/holograms.yml");

        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                try {
                    f.createNewFile();
                } catch (IOException ignored) {
                }
            }
        }
        return new Holo(pUuid);
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

    public void set(final String string, final Object arg1) {
        this.c.set(string, arg1);
    }

    public String getString(final String string) {
        return this.c.getString(string);
    }

    public List<String> getStringList(final String string) {
        return (List<String>) this.c.getStringList(string);
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

    public ItemStack getItemStack(final String s) {
        return c.isSet(s) ? c.getItemStack(s) : new ItemStack(Material.AIR);
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
