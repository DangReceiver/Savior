package de.tdf.cmd;

import de.tdf.language.Language;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SetLanguage implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sen, Command cmd, String lab, String[] args) {

        if (!(sen instanceof Player p)) {
            sen.sendMessage(Language.PRE + Language.getMessage(Language.getServerLang(), "no_player"));
            return false;
        }

        File pl = Language.getLang(p);
        String pls = pl.getName().split(".yml")[0];

        if (args.length != 0 && args.length != 1) {
            p.sendMessage(Language.PRE + String.format(Language.getMessage(
                    pl, "arg_length_desired"), "0 - 1"));
            return false;
        }

        if (args.length == 0) {
            p.sendMessage(Language.PRE + String.format(Language.getMessage(pl,
                    "lang_current")), pls);
            p.sendMessage(Language.PRE + Language.getMessage(pl,
                    "lang_how_to_change"));
            return false;
        }

        String lang = args[0];
        if (Language.isLangFile(lang)) {
            File lf = Language.getLangFile(lang);

            if (!lf.exists()) {
                p.sendMessage(Language.PRE + String.format(Language.getMessage(pl, "lang_invalid"), pls));
                return true;
            }

            Language.setLang(p, lf);
        }

        p.sendMessage(Language.PRE + String.format(Language.getMessage(Language.getLang(p), "lang_updated"),
                pls, Language.getLang(p).getName().split(".yml")[0]));

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(CommandSender sen, Command cmd, String lab, String[] args) {
        List<String> tab = new ArrayList<>();

        if (args.length != 1) return null;
        if (Language.getLanguages() == null) return null;

        for (File f : Language.getLanguages())
            if (lab.isEmpty() || f.getName().charAt(0) == lab.charAt(0))
                tab.add(f.getName().split(".yml")[0]);

        return tab;
    }
}
