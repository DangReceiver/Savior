package de.tdf.cmd;

import de.tdf.language.Language;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Heal implements CommandExecutor {

	@Override
	public boolean onCommand(final CommandSender send, final Command cmd, final String label, final String[] args) {
		if (!send.hasPermission("Savior.Heal.Self")) {
			send.sendMessage(Language.PRE + String.format(Language.getMessage(Language.getServerLang(),
					"insufficient_permission"), "Savior.Heal.Self"));
			return false;
		}

		if (args.length == 0) {

			if (!(send instanceof Player p)) {
				send.sendMessage(Language.PRE + Language.getMessage(Language.getServerLang(), "no_player"));
				return false;
			}

			p.setHealth(p.getMaxHealth());
			p.setFoodLevel(20);
			p.setSaturation(20.0f);
			p.setFireTicks(0);

			p.sendTitle(Language.PRE, Language.getMessage(Language.getLang(p), "healed_title"),
					8, 15, 30);

		} else if (args.length == 1) {

			if (send.hasPermission("Savior.Heal.All") && args[0].equals("*")) {
				for (final Player ap : Bukkit.getOnlinePlayers()) {

					ap.setHealth(ap.getMaxHealth());
					ap.setFoodLevel(20);
					ap.setSaturation(15.0f);
					ap.setFireTicks(0);

					ap.sendTitle(Language.PRE, Language.getMessage(Language.getLang(ap),
							"healed_title"), 8, 15, 30);
				}

				send.sendMessage(Language.PRE, Language.getMessage(Language.getServerLang(), "healed_everyone"));

			} else if (send.hasPermission("Savior.Heal.Others")) {
				final Player t = Bukkit.getPlayer(args[0]);

				if (t == null) {
					send.sendMessage(Language.PRE, Language.getMessage(Language.getServerLang(), "target_invalid"));
					return false;
				}

				t.setHealth(t.getMaxHealth());
				t.setFoodLevel(20);
				t.setSaturation(15.0f);
				t.setFireTicks(0);

				t.sendTitle(Language.PRE, Language.getMessage(Language.getLang(t), "healed_title"),
						8, 15, 30);
				send.sendMessage(Language.PRE + Language.getMessage(Language.getServerLang(), "healed_successfully"));

			} else
				send.sendMessage(Language.PRE + String.format(Language.getMessage(Language.getServerLang(),
						"insufficient_permission"), "Savior.Heal.Others"));

		} else {
			send.sendMessage(Language.PRE + String.format(Language.getMessage(Language.getServerLang(),
					"arg_length_desired"), "0 - 1"));
			return false;
		}
		return false;
	}
}

