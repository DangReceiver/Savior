package de.tdf.cmd;

import de.tdf.language.Language;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Tps implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sen, Command cmd, String label, String[] args) {

		sen.sendMessage(Language.PRE + String.format(Language.getMessage(Language.getServerLang(),
				"tps_to_complete"), Bukkit.getServer().getTPS()));

		return false;
	}
}
