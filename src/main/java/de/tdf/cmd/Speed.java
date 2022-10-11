package de.tdf.cmd;

import de.tdf.language.Language;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

public class Speed implements CommandExecutor {

	@Override
	public boolean onCommand(final CommandSender sen, final Command command, final String label, final String[] args) {

		if (!(sen instanceof Player p)) {
			sen.sendMessage(Language.PRE + Language.getMessage(Language.getServerLang(), "no_player"));
			return true;
		}

		File l = Language.getLang(p);
		if (args.length < 1 || args.length > 2) {
			p.sendMessage(Language.PRE + String.format(Language.getMessage(l, "arg_length_desired"), 1));
			return false;
		}

		if (!p.hasPermission("Savior.Speed.Self")) {
			p.sendMessage(Language.PRE + String.format(Language.getMessage(l,
					"insufficient_permission"), "Savior.Speed.Self"));
			return true;
		}

		try {
			Player t = p;

			if (args.length == 2) {
				if (!p.hasPermission("Savior.Speed.Others")) {
					p.sendMessage(Language.PRE + String.format(Language.getMessage(l,
							"insufficient_permission"), "Savior.Speed.Others"));
					return true;
				}

				t = Bukkit.getPlayer(args[1]);
				if (t == null) {
					p.sendMessage(Language.PRE + Language.getMessage(l, "target_invalid"));
					return false;
				}
			}


			final float ns = Float.parseFloat(args[0]);
			if (ns < 0.0f || ns > 1.0f) {
				p.sendMessage(Language.PRE + String.format(Language.getMessage(l, "arg_specifics"), "#0: float (0 - 1)"));
				return false;
			}

			final String fs = p.getFlySpeed() + "", ws = p.getWalkSpeed() + "";
			if (p.isFlying()) {
				p.setFlySpeed(ns);
				p.sendMessage(Language.PRE + String.format(Language.getMessage(l, "speed_updated_flight"), fs, ns));

			} else {
				p.setWalkSpeed(ns);
				p.sendMessage(Language.PRE + String.format(Language.getMessage(l, "speed_updated_walk"), ws, ns));
			}

		} catch (NumberFormatException e) {
			p.sendMessage(Language.PRE + String.format(Language.getMessage(l, "arg_specifics"), 1, "float (0 - 2)"));
			return true;
		}

		return false;
	}
}
