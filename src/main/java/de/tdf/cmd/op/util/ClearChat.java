package de.tdf.cmd;

import de.tdf.language.Language;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearChat implements CommandExecutor {

    @Override
    public boolean onCommand(final CommandSender sen, final Command command, final String label, final String[] args) {
        if (!(sen instanceof Player p)) {
            sen.sendMessage(Language.PRE + Language.getMessage(Language.getServerLang(), "not_a_player"));
            return false;
        }

        if (!p.hasPermission("Savior.ChatClear")) {
            sen.sendMessage(Language.PRE + String.format(Language.getMessage(
                    Language.getServerLang(), "insufficient_permission"), "Savior.ChatClear"));
            return false;
        }

        if (args.length != 0) {
            p.sendMessage(Language.PRE + Language.getMessage(Language.getServerLang(), "arg_length"));
            return false;
        }

        for (int times = 0; times <= 400; ++times)
            Bukkit.broadcastMessage(" \n §0» \n ");
        Language.broadcast("chat_cleared");

        return false;
    }
}
