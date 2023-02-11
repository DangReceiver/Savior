package de.tdf.savior;

import de.tdf.cmd.*;
import de.tdf.cmd.manage.Build;
import de.tdf.cmd.manage.Hologram;
import de.tdf.language.*;
import de.tdf.listener.*;
import org.bukkit.*;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
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
    public static boolean DoubleDoorsPermission = false;

    @Override
    public void onEnable() {
        savior = this;
        version = getVersion();

        ConsoleCommandSender cs = Bukkit.getConsoleSender();
        World w = Bukkit.getWorld("world");

        if (w != null) w.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, false);

        if (!de.tdf.worlds.Spawn.checkExists()) {
            cs.sendMessage(Language.PRE + Language.getMessage(Language.getServerLang(), "spawn_world_invalid"));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        if (Language.updateServerLang()) if (Language.updateServerLang()) {
            saveConfig();
            cs.sendMessage(Language.PRE + "The server language could not be estimated. Stopping plugin load process.");
            return;
        }

        Language.loadMessages();

        File file = new File("plugins/Savior/Settings.yml");
        YamlConfiguration settings = YamlConfiguration.loadConfiguration(file);

        DoubleDoorsPermission = settings.isSet("Settings.RequirePermission.DoorClick")
                && settings.getBoolean("Settings.RequirePermission.DoorClick");

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new CM(), this);
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
        pm.registerEvents(new CreeperPrimeCreeper(), this);
        pm.registerEvents(new SpawnProtection(), this);
        pm.registerEvents(new ElytraBoost(), this);
        pm.registerEvents(new BedHandling(), this);
        pm.registerEvents(new Build(), this);
        pm.registerEvents(new WorldChanged(), this);
        pm.registerEvents(new ItemCooldown(), this);
        pm.registerEvents(new AddItemCooldown(), this);
        pm.registerEvents(new EnderPearl(), this);
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
        Objects.requireNonNull(getCommand("Health")).setExecutor(new Health());
        Objects.requireNonNull(getCommand("Build")).setExecutor(new Build());
        Objects.requireNonNull(getCommand("Hologram")).setExecutor(new Hologram());
        Objects.requireNonNull(getCommand("Spawn")).setExecutor(new de.tdf.cmd.manage.Spawn());
        Objects.requireNonNull(getCommand("Spawn")).setTabCompleter(new de.tdf.cmd.manage.Spawn());
        Objects.requireNonNull(getCommand("Pronouns")).setExecutor(new Pronouns());

        for (Player ap : Bukkit.getOnlinePlayers())
            Language.setLang(ap, Language.getLangFile("en"));

        for (Entity as : Objects.requireNonNull(w).getEntities())
            if (as instanceof ArmorStand && as.getCustomName() != null) as.remove();
        for (Entity as : Objects.requireNonNull(Bukkit.getWorld("Spawn")).getEntities())
            if (as instanceof ArmorStand && as.getCustomName() != null) as.remove();
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

