package de.tdf.cmd.user;

import de.tdf.obj.PC;
import de.tdf.language.Language;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Pronouns implements CommandExecutor {

    public static List<String>
            // en
            they = new ArrayList<>(Arrays.asList("they", "them", "their", "theirs", "themselves")),
            name = new ArrayList<>(Arrays.asList("@pn", "@pn", "@pn's", "@pn's", "@pn")),
            he = new ArrayList<>(Arrays.asList("he", "him", "his", "his", "himself")),
            she = new ArrayList<>(Arrays.asList("she", "her", "her", "hers", "herself")),
            it = new ArrayList<>(Arrays.asList("it", "it", "its", "its", "itself")),
    // de
    dey = new ArrayList<>(Arrays.asList("dey", "denen", "denen", "deren", "deren")),
            er = new ArrayList<>(Arrays.asList("he", "him", "his", "his", "himself")),
            sie = new ArrayList<>(Arrays.asList("she", "her", "her", "hers", "herself")),
            es = new ArrayList<>(Arrays.asList("it", "it", "its", "its", "itself"));

    public static List<ArrayList<List<String>>> lists = new ArrayList<>();
    public static ArrayList<List<String>> en = new ArrayList<>(Arrays.asList(they, name, she, he, it)),
            de = new ArrayList<>(Arrays.asList(dey, name, sie, er, es));

    public static void setup() {
        lists.add(en);
        lists.add(de);
//        lists.add(others);
    }

    public int langToInt(File lang) {
        return switch (lang.getName().split(".yml")[0]) {
            case "en" -> 0;
            case "de" -> 1;
            default -> -1;
        };
    }

    @Override
    public boolean onCommand(CommandSender sen, Command cmd, String lab, String[] args) {

        if (!(sen instanceof Player p)) {
            sen.sendMessage(Language.PRE + Language.getMessage(Language.getServerLang(), "no_player"));
            return false;
        }

        PC pc = PC.loadConfig(p);
        File f = Language.getLang(p);

        if (args.length == 0) {
            Inventory i = Bukkit.createInventory(null, 5 * 9,
                    Language.getMessage(f, "pronouns_title"));
            i.setItem(i.getSize() / 2 - 9, new ItemStack(Material.WRITTEN_BOOK));
            i.setItem(i.getSize() / 2 - 3, new ItemStack(Material.PLAYER_HEAD));
            i.setItem(i.getSize() / 2 + 3, new ItemStack(Material.PINK_TULIP));
            i.setItem(i.getSize() / 2 + 8, new ItemStack(Material.BLUE_CANDLE));
            i.setItem(i.getSize() / 2 + 10, new ItemStack(Material.CARROT));
            p.openInventory(i);
            return false;
        }

        if (args.length != 1) {
            p.sendMessage(Language.PRE + String.format(Language.getMessage(f, "arg_length_desired"), 1));
            return false;
        }

        int input;
        try {
            input = Integer.parseInt(args[0]);

        } catch (NumberFormatException e) {
            p.sendMessage(Language.PRE + Language.getMessage(f, "invalid_input"));
            return false;
        }

        if (pc.getPronouns() == input) {
            p.sendMessage(Language.PRE + Language.getMessage(f, "value_already_set"));
            return false;
        }

        pc.setPronouns(input);
        pc.savePCon();

        p.sendMessage(Language.PRE + Language.getMessage(f, "pronouns_example_pre"));
        p.sendMessage("§7" + replaceText(Language.getMessage(f, "pronouns_example_sentence_"
                + new Random().nextInt(3)), pc));

        return false;
    }

    public String replaceText(String text, PC pc) {
        int pn = pc.getPronouns();
        File lang = Language.getLang(pc.getOfflinePlayer().getPlayer());

        List<String> current = lists.get(langToInt(lang)).get(pn);

        String pName = pc.getOfflinePlayer().getName(),
                verb = !isSingular(pn) ? Language.getMessage(lang, "be_plural")
                        : Language.getMessage(lang, "be_singular");

        Bukkit.getConsoleSender().sendMessage(ChatColor.stripColor(text) + "\n");

        for (int i = 0; i < current.size(); i++)
            text = text.replaceAll("%pv", verb);
        Bukkit.getConsoleSender().sendMessage(ChatColor.stripColor(text) + "\n");

        text = text.replaceAll("%pn", pName);
        Bukkit.getConsoleSender().sendMessage(ChatColor.stripColor(text) + "\n");

        for (int i = 0; i < current.size(); i++)
            text = text.replaceAll("%p" + i, current.get(i));
        Bukkit.getConsoleSender().sendMessage(ChatColor.stripColor(text) + "\n");

        text = text.replaceAll("@pn", pName);

        return text;
    }

    public boolean isSingular(int list) {
        return (list != 0);
    }

    public boolean isDynamic(int list) {
        return list == 4;
    }
}
