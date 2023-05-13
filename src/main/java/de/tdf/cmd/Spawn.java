package de.tdf.cmd;

import de.tdf.language.Language;
import de.tdf.savior.Savior;
import de.tdf.worlds.SpawnWorld;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Spawn implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sen, Command cmd, String lab, String[] args) {

		if (!(sen instanceof Player p)) {
			sen.sendMessage(Language.PRE + Language.getMessage(Language.getServerLang(), "no_player"));
			return false;
		}

		if (!p.hasPermission("Savior.Spawn")) {
			p.sendMessage(Language.PRE + Language.getMessage(Language.getLang(p), "insufficient_permission"));
			return false;
		}

		if (args.length == 0) {
			p.teleport(SpawnWorld.getSafeSpawnLocation());
			p.sendActionBar(Component.text(Language.PRE + Language.getMessage(Language.getLang(p),
					"teleported_successfully")));
			p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 0.25f, 0.85f);

		} else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("set")) {

				FileConfiguration c = Savior.getSavior().getConfig();
				c.set("Locations.Spawn", p.getLocation());
				Savior.getSavior().saveConfig();

				p.sendMessage(Language.PRE + Language.getMessage(Language.getLang(p),
						"spawn_set_current_position"));
			}

		} else if (args.length == 2) {
			if (args[0].equalsIgnoreCase("set")) {

				if (args[1].equalsIgnoreCase("default")) {
					FileConfiguration c = Savior.getSavior().getConfig();

					c.set("Locations.Spawn", new Location(Bukkit.getWorld("Spawn"), 0, 64.02, 0));
					Savior.getSavior().saveConfig();

					p.sendMessage(Language.PRE + Language.getMessage(Language.getLang(p), "spawn_set_default"));
				}
			}


		} else
			p.sendMessage(Language.PRE + Language.getMessage(Language.getLang(p), "not_implemented_yet"));

		return false;
	}

	@Override
	public @Nullable List<String> onTabComplete(CommandSender sen, Command cmd, String lab, String[] args) {


		return null;
	}
}
