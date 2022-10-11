package de.tdf.cmd;

import de.tdf.language.Language;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

public class ArrowsInBody implements CommandExecutor {

	@Override
	public boolean onCommand(final CommandSender sen, final Command cmd, final String lab, final String[] args) {

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

		if (args.length == 0) {
			p.sendMessage(Language.PRE + String.format(Language.getMessage(l, "change_attribute"), p.getArrowsInBody(), "0"));
			p.setArrowsInBody(0);

		} else if (args.length == 1) {

			try {
				final int a = Integer.parseInt(args[0]);
				p.sendMessage(Language.PRE + String.format(Language.getMessage(l, "change_attribute"), p.getArrowsInBody(), a));
				p.setArrowsInBody(a);

			} catch (NumberFormatException e) {
				final Player t = Bukkit.getPlayer(args[0]);

				if (t == null) {
					p.sendMessage(Language.PRE + Language.getMessage(l, "arg_invalid"));
					p.sendMessage(Language.PRE + Language.getMessage(l, "usage"));
					return true;
				}

				p.sendMessage(Language.PRE + String.format(Language.getMessage(l, "change_attribute"), p.getArrowsInBody(), "0"));
				t.sendMessage(Language.PRE + String.format(Language.getMessage(l, "change_attribute_target"), t.getArrowsInBody(), "0"));
				t.setArrowsInBody(0);
			}

		} else if (args.length == 2) {
			final Player t2 = Bukkit.getPlayer(args[0]);

			if (t2 == null) {
				p.sendMessage(Language.PRE + Language.getMessage(l, "target_invalid"));
				return false;
			}

			try {
				final int a = Integer.parseInt(args[1]);
				p.sendMessage(Language.PRE + String.format(Language.getMessage(l, "change_attribute"), t2.getArrowsInBody(), a));
				t2.sendMessage(Language.PRE + String.format(Language.getMessage(l, "change_attribute_target"), t2.getArrowsInBody(), a));

				t2.setArrowsInBody(a);
				return false;

			} catch (NumberFormatException e) {
				p.sendMessage(Language.PRE + Language.getMessage(l, "arg_invalid"));
				return true;
			}
		}
		return false;
	}
}
