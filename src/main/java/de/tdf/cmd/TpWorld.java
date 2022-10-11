package de.tdf.cmd;

import de.tdf.language.Language;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TpWorld implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sen, Command cmd, String lab, String[] args) {

		if (!sen.hasPermission("Savior.TpWorld")) {
			sen.sendMessage(Language.PRE + String.format(Language.getMessage(Language.getServerLang(),
					"no_permission"), "Savior.TpWorld"));
			return true;
		}

		if (sen instanceof Player p) {

			if (args.length <= 0) {
				sen.sendMessage(Language.PRE + Language.getMessage(Language.getServerLang(), "arg_length"));
				return true;
			}

			String s = args[0];

			for (int i = 1; i < args.length; i++)
				s = s + "_" + args[i];

			if (Bukkit.getWorld(s) != null) {
				p.teleport(new Location(Bukkit.getWorld(s), 0, 72, 0));
				p.playSound(p.getLocation(), Sound.ENTITY_SHULKER_SHOOT, 0.25f, 1.1f);

			} else
				sen.sendMessage(Language.PRE + Language.getMessage(Language.getServerLang(), "invalid_world"));
		}
		return false;
	}
}
