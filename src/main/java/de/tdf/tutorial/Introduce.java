package de.tdf.tutorial;

import de.tdf.language.Language;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Introduce implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sen, Command cmd, String lab, String[] args) {

        if (!(sen instanceof Player p)) {
            sen.sendMessage(Language.PRE + Language.getMessage(Language.getServerLang(), "no_player"));
            return false;
        }

        File l = Language.getLang(p);
        if (!sen.hasPermission("Savior.ArrowsInBody")) {
            p.sendMessage(Language.PRE + String.format(Language.getMessage(l, "insufficient_permission"),
                    "Savior.ArrowsInBody"));
            return false;
        }

        if (args.length < 1 || args.length > 2) {
            p.sendMessage(Language.PRE + String.format(Language.getMessage(l, "arg_length_range"), 1, 2));
            return false;
        }

        if (args[0].equalsIgnoreCase("Summon")) {
            Lennart lennart = new Lennart(p.getLocation(), null);
            lennart.spawn(null);
            lennart.setUp(p, null, lennart.getLennart());
        }

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(CommandSender sen, Command cmd, String lab, String[] args) {
        List<String> l = new ArrayList<>(Arrays.asList("Summon", "Introduction", "Pronouns", "Language", "Features"));

        if (args.length == 1) {
            int i = 0;
            for (int c = 0; c <= args[0].length(); c++)
                for (String s : l) if (s.charAt(c) != args[0].charAt(c)) l.remove(((i++) - 1));
            return l;
        }

        l = new ArrayList<>(Arrays.asList("Chat", "CM", "ItemCooldown", "CreeperPrime",
                "Death", "Elytra", "EnderPearl", "EntitySpawning", "FireWork"));
        if (args[0].isEmpty()) return l;

        int i = 0;
        for (int c = 0; c <= args[1].length(); c++)
            for (String s : l) if (s.charAt(c) != args[1].charAt(c)) l.remove(((i++) - 1));

        return l;
    }
}
