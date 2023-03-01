package de.tdf.cmd.op.util;

import de.tdf.language.Language;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class TicksPerSecond implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sen, Command cmd, String label, String[] args) {
        double[] tps = Bukkit.getServer().getTPS();
        File lf = Language.getServerLang();

        double t0 = Math.round((1000 * tps[0])),
                t1 = Math.round((1000 * tps[1])), t2 = Math.round((1000 * tps[2]));

        if (sen instanceof Player p) lf = Language.getLang(p);
        sen.sendMessage(Language.PRE + String.format(Language.getMessage(lf, "tps"),
                t0 / 1000, t1 / 1000, t2 / 1000));

        return false;
    }
}
