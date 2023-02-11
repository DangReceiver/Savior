package de.tdf.cmd;

import de.tdf.language.Language;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.io.File;

public class Invsee implements CommandExecutor {

	@Override
	public boolean onCommand(final CommandSender sen, final Command command, final String label, final String[] args) {

		if (!(sen instanceof Player p)) {
			sen.sendMessage(Language.PRE + Language.getMessage(Language.getServerLang(), "no_player"));
			return false;
		}

		File l = Language.getLang(p);
		if (!p.hasPermission("Savior.Invsee")) {
			p.sendMessage(Language.PRE + String.format(Language.getMessage(l,
					"insufficient_permission"), "Savior.Invsee"));
			return false;
		}

		if (args.length != 1) {
			p.sendMessage(Language.PRE + String.format(Language.getMessage(l, "arg_length_desired"), 1));
			return false;
		}

		Player t = Bukkit.getPlayer(args[0]);
		p.closeInventory();

		if (t == null) {
			p.sendMessage(Language.PRE + Language.getMessage(l, "target_invalid"));
			return true;
		}

		p.sendMessage(Language.PRE + String.format(Language.getMessage(l, "invsee"), t.getName()));
		p.openInventory(t.getInventory());
		return false;
	}
}
