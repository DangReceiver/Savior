package de.tdf.cmd;

import de.tdf.language.Language;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

public class Gm implements CommandExecutor {

	@Override
	public boolean onCommand(final CommandSender sen, final Command cmd, final String label, final String[] args) {

		if (!(sen instanceof Player p)) {
			sen.sendMessage(Language.PRE + Language.getMessage(Language.getServerLang(), "no_player"));
			return true;
		}

		File l = Language.getLang(p);
		if (!p.hasPermission("Savior.Gamemode.Self")) {
			p.sendMessage(Language.PRE + String.format(Language.getMessage(l, "insufficient_permission"),
					"Savior.GameMode.Self"));
			return true;
		}

		final GameMode bg = p.getGameMode();

		if (args.length == 2) {
			final Player t = Bukkit.getPlayer(args[1]);

			if (t == null) {
				p.sendMessage(Language.PRE + Language.getMessage(l, "target_invalid"));
				return false;
			}

			if (args[0].equals("0") || args[0].equalsIgnoreCase("SURVIVAL"))
				t.setGameMode(GameMode.SURVIVAL);
			else if (args[0].equals("1") || args[0].equalsIgnoreCase("CREATIVE"))
				t.setGameMode(GameMode.CREATIVE);

			else if (args[0].equals("3") || args[0].equalsIgnoreCase("SPECTATOR"))
				t.setGameMode(GameMode.SPECTATOR);
			else if (args[0].equals("2") || args[0].equalsIgnoreCase("ADVENTURE"))
				t.setGameMode(GameMode.ADVENTURE);

			else {
				p.sendMessage(Language.PRE + String.format(Language.getMessage(l, "usage"), cmd));
				return false;
			}

			p.sendMessage(Language.PRE + String.format(Language.getMessage(l, "change_attribute_target"),
					t.getName(), bg.toString().toLowerCase(), t.getGameMode().toString().toLowerCase()));
			t.sendActionBar(Language.PRE + String.format(Language.getMessage(l, "change_attribute"),
					bg.toString().toLowerCase(), t.getGameMode().toString().toLowerCase()));
			return true;

		} else if (args.length == 0) {
			if (!p.isSneaking() && p.isFlying()) {

				if (p.getGameMode() == GameMode.ADVENTURE)
					p.setGameMode(GameMode.SURVIVAL);
				else if (p.getGameMode() == GameMode.SURVIVAL)
					p.setGameMode(GameMode.CREATIVE);

				else if (p.getGameMode() == GameMode.CREATIVE)
					p.setGameMode(GameMode.SPECTATOR);
				else if (p.getGameMode() == GameMode.SPECTATOR)
					p.setGameMode(GameMode.CREATIVE);

			} else if (p.isSneaking() && p.isFlying())
				if (p.getGameMode() == GameMode.SURVIVAL)
					p.setGameMode(GameMode.CREATIVE);
				else if (p.getGameMode() == GameMode.CREATIVE)
					p.setGameMode(GameMode.SPECTATOR);

				else if (p.getGameMode() == GameMode.SPECTATOR)
					p.setGameMode(GameMode.CREATIVE);
				else if (p.getGameMode() == GameMode.SPECTATOR)
					p.setGameMode(GameMode.SURVIVAL);

				else if (p.getGameMode() == GameMode.SURVIVAL)
					p.setGameMode(GameMode.CREATIVE);
				else if (p.getGameMode() == GameMode.CREATIVE)
					p.setGameMode(GameMode.SURVIVAL);

				else if (p.getGameMode() == GameMode.SPECTATOR)
					p.setGameMode(GameMode.SURVIVAL);
				else if (p.getGameMode() == GameMode.ADVENTURE)
					p.setGameMode(GameMode.SURVIVAL);

			p.sendMessage(Language.PRE + String.format(Language.getMessage(l, "change_attribute"),
					bg.toString().toLowerCase(), p.getGameMode().toString().toLowerCase()));

		} else if (args.length == 1) {

			if (args[0].equals("0") || args[0].equalsIgnoreCase("SURVIVAL"))
				p.setGameMode(GameMode.SURVIVAL);
			else if (args[0].equals("1") || args[0].equalsIgnoreCase("CREATIVE"))
				p.setGameMode(GameMode.CREATIVE);

			else if (args[0].equals("3") || args[0].equalsIgnoreCase("SPECTATOR"))
				p.setGameMode(GameMode.SPECTATOR);
			else if (args[0].equals("2") || args[0].equalsIgnoreCase("ADVENTURE"))
				p.setGameMode(GameMode.ADVENTURE);

			else {
				p.sendMessage(Language.PRE + String.format(Language.getMessage(l, "usage"), cmd));
				return false;
			}

			p.sendMessage(Language.PRE + String.format(Language.getMessage(l, "change_attribute"),
					bg.toString().toLowerCase(), p.getGameMode().toString().toLowerCase()));
		} else
			p.sendMessage(Language.PRE + Language.getMessage(l, "arg_length_desired"));
		return false;
	}
}
