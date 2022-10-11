package de.tdf.cmd;

import de.tdf.language.Language;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TicksPerSecond implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sen, Command cmd, String label, String[] args) {

		sen.sendMessage(Language.PRE + Bukkit.getServer().getTPS());

		return false;
	}
}
