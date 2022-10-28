package de.tdf.cmd;

import de.tdf.language.Language;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Fly implements CommandExecutor {
	public static List<String> fly;

	@Override
	public boolean onCommand(final CommandSender sen, final Command cmd, final String lab, final String[] args) {
		if (!(sen instanceof Player p)) {
			sen.sendMessage(Language.PRE + Language.getMessage(Language.getServerLang(), "no_player"));
			return false;
		}

		if (p.hasPermission("Savior.fly.self")) {
			if (args.length == 0) {

				if (!Fly.fly.contains(p.getName())) {
					Fly.fly.add(p.getName());

					p.sendMessage(Language.PRE + Language.getMessage(Language.getLang(p), "flight_updated"));
					p.setAllowFlight(true);
					p.teleport(p.getLocation().clone().add(0, 0.001, 0));
					p.setFlying(true);

				} else if (Fly.fly.contains(p.getName())) {
					Fly.fly.remove(p.getName());

					p.sendMessage(Language.PRE + Language.getMessage(Language.getLang(p), "flight_updated"));
					p.setAllowFlight(false);
					p.setFlying(false);
				}
			} else if (args.length == 1) {
				if (!p.hasPermission("Savior.fly.others")) {
					p.sendMessage(Language.PRE + String.format(Language.getMessage(Language.getLang(p),
							"insufficient_permission"), "Savior.fly.others"));
					return false;
				}

				final Player t = Bukkit.getPlayer(args[0]);

				if (t == null) {
					p.sendMessage(Language.PRE + Language.getMessage(Language.getLang(p), "target_invalid"));
					return false;
				}

				if (!Fly.fly.contains(t.getName())) {
					Fly.fly.add(t.getName());

					t.setAllowFlight(true);
					p.teleport(p.getLocation().clone().add(0, 0.001, 0));
					t.setFlying(true);

					t.sendMessage(Language.PRE + Language.getMessage(Language.getLang(t), "flight_updated"));
					p.sendMessage(Language.PRE + Language.getMessage(Language.getLang(p), "flight_updated_target"));

				} else if (Fly.fly.contains(t.getName())) {
					Fly.fly.remove(t.getName());

					t.sendMessage(Language.PRE + Language.getMessage(Language.getLang(t), "flight_updated"));
					p.sendMessage(Language.PRE + Language.getMessage(Language.getLang(p), "flight_updated_target"));

					t.setAllowFlight(false);
					t.setFlying(false);
				}

			} else {
				p.sendMessage(Language.PRE + Language.getMessage(Language.getLang(p), "arg_length"));
				p.sendMessage(Language.PRE + String.format(Language.getMessage(Language.getLang(p), "usage"),
						"/fly <player name>"));
			}
		} else
			p.sendMessage(Language.PRE + Language.getMessage(Language.getLang(p), "insufficient_permission"));
		return false;
	}

	static {
		Fly.fly = new ArrayList<>();
	}
}
