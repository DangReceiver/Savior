package de.tdf.cmd.manage;

import de.tdf.Holo;
import de.tdf.language.Language;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Hologram implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sen, Command cmd, String lab, String[] args) {

		if (!(sen instanceof Player p)) {
			sen.sendMessage(Language.getMessage(Language.getServerLang(), "no_player"));
			return false;
		}

		if (!p.hasPermission("Savior.Hologram")) {
			p.sendMessage(Language.getMessage(Language.getLang(p), "insufficient_permission"));
			return false;
		}

		if (args.length >= 3) {
			// Does not make sense currently lol
			ArmorStand as = (ArmorStand) p.getWorld().spawnEntity(p.getLocation(), EntityType.ARMOR_STAND);

			UUID uuid = as.getUniqueId();
			Holo h = Holo.loadConfig(uuid);

			if (args[0].equalsIgnoreCase("create")) {
				h.setDefault();
				h.setUp(args[1], args.toString().replace(args[1] + " ",
								"").replaceAll("&", "§"));

			} else if (args[0].equalsIgnoreCase("remove")) {
				as.remove();
				h.remove();

			} else if (args[0].equalsIgnoreCase("move")) {


			} else if (args[0].equalsIgnoreCase("switch")) {


			} else if (args[0].equalsIgnoreCase("rename")) {
				h.rename(args[1]);

			} else if (args[0].equalsIgnoreCase("reset")) {
				h.setDefault();

			} else if (args[0].equalsIgnoreCase("addLine")) {


			} else if (args[0].equalsIgnoreCase("setLine")) {


			} else if (args[0].equalsIgnoreCase("removeLine")) {


			}
			h.savePCon();
		}

		return false;
	}
}
