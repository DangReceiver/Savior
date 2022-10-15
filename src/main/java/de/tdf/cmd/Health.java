package de.tdf.cmd;

import de.tdf.language.Language;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Health implements CommandExecutor {
	@Override
	public boolean onCommand(final CommandSender sen, final Command cmd, final String label, final String[] args) {
		if (!(sen instanceof Player p)) {
			sen.sendMessage(Language.PRE + Language.getMessage(Language.getServerLang(), "no_player"));
			return false;
		}

		int i = -1;
		if (args.length >= 1) {
			try {
				i = Integer.parseInt(args[0]);

			} catch (NumberFormatException e) {
				p.sendMessage(Language.PRE + String.format(Language.getMessage(Language.getLang(p),
						"argument_specifics"), 0, "Integer"));
				return true;
			}

			if (i > 2048 || i < 1) {
				p.sendMessage(Language.PRE + String.format(Language.getMessage(Language.getLang(p),
						"argument_range"), 0, 1, 2048));
				return true;
			}

		} else if (args.length <= 2) {
			if (args[0].equalsIgnoreCase("h") || args[0].equalsIgnoreCase("help")) {
				p.sendMessage(Language.PRE + String.format(Language.getMessage(Language.getLang(p),
						"usage"), cmd, "<amount> [<boolean: heal>] [<Player>]"));
				return true;
			}

			p.sendMessage(Language.PRE + String.format(Language.getMessage(Language.getLang(p),
					"health_updated"), p.getMaxHealth(), i));
			p.setMaxHealth(i);

			if (args.length == 2 && Boolean.parseBoolean(args[1]))
				p.setHealth(p.getMaxHealth());

		} else if (args.length == 3) {

			try {
				final Player tar = Bukkit.getPlayer(args[2]);

				p.sendMessage(Language.PRE + String.format(Language.getMessage(Language.getLang(p),
						"change_attribute"), "" + tar.getMaxHealth(), "" + i));
				tar.sendMessage(Language.PRE + String.format(Language.getMessage(Language.getLang(tar),
						"change_attribute_target"), "" + tar.getMaxHealth(), "" + i));

				tar.setMaxHealth(i);
				if (Boolean.parseBoolean(args[1]))
					tar.setHealth(tar.getMaxHealth());

			} catch (NumberFormatException e2) {
				p.sendMessage(Language.PRE + String.format(Language.getMessage(Language.getLang(p),
						"argument_specifics"), 0, "Boolean"));
				return true;
			}

		} else
			p.sendMessage(Language.PRE + String.format(Language.getMessage(Language.getLang(p),
					"arg_length_desired"), "1 - 3"));
		return false;
	}
}
