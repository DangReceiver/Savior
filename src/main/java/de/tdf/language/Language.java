package de.tdf.language;

import de.tdf.savior.savior.Savior;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Language {

    public static String PRE;

    public static File sLang;
    private static File df = Savior.getSavior().getDataFolder();

    private static Map<Player, File> settings = new HashMap<>();
    private static Map<File, Map<String, String>> messages = new HashMap<>();


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
        return messages.get(f) != null && messages.get(f).get(key) != null ? messages.get(f).get(key)
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

        if (new File(df + "/lang").listFiles() == null) return null;
        for (File f : new File(df + "/lang").listFiles()) lf.add(f);

        return lf;
    }

    public static void broadcast(String key) {
        Bukkit.getConsoleSender().sendMessage(getMessage(getServerLang(), "info") +
                String.format(getMessage(getServerLang(), "broadcast"), key));

        for (Player ap : Bukkit.getOnlinePlayers())
            ap.sendMessage(getMessage(getLang(ap), "prefix") + getMessage(getLang(ap), key));
    }

    public static void broadcastArg(String key, String... arg) {
        Bukkit.getConsoleSender().sendMessage(getMessage(getServerLang(), "info") +
                String.format(getMessage(getServerLang(), "broadcast"), key));

        for (Player ap : Bukkit.getOnlinePlayers()) {
            String format = "";

            for (String ts : arg) format = String.format(getMessage(getLang(ap), key), ts);
            ap.sendMessage(getMessage(getLang(ap), "prefix") + format);
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
        File folder = new File(df + "/lang");

        if (folder.listFiles() == null) return false;
        for (File file : folder.listFiles())
            langFiles.add(file.getName());

        if (language == null) {

            if (!c.isSet("ServerLang")) {
                c.set("ServerLang", "en");
                s.saveConfig();
            }

            if (!langFiles.contains(c.getString("ServerLang") + ".yml")) return false;
            sLang = new File(df + "/lang", c.getString("ServerLang") + ".yml");

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


    public static boolean loadMessages() {

        return false;
    }



}
