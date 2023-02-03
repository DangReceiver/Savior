package de.tdf.cmd;

import de.tdf.language.Language;
import de.tdf.savior.Savior;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

public class Info implements CommandExecutor {

	@Override
	public boolean onCommand(final CommandSender sen, final Command cmd, final String label, final String[] args) {

		final Runtime r = Runtime.getRuntime();
		final int s = 1048576;
		final long m = r.maxMemory() / s - r.freeMemory() / s;

		Player p = (Player) sen;
		File l = sen != null ? Language.getLang(p) : Language.getServerLang();

		sen.sendMessage("§0 \n §0 ");

		sen.sendMessage(Language.PRE + String.format(Language.getMessage(l, "info_author"), "TearsDontFall"));
		sen.sendMessage(String.format(Language.getMessage(l, "info_version"), Savior.version));

		if ((Bukkit.getServer().getVersion().contains("1.19")) && sen instanceof Player)
			sen.sendMessage(String.format(Language.getMessage(l, "info_ping"), pingColor(p) + p.getPing()));

		sen.sendMessage(String.format(Language.getMessage(l, "info_ram_usage"),
				m, r.maxMemory() / s, r.freeMemory() / s));
		sen.sendMessage("§0 \n §0 ");
		return true;
	}

	public static String pingColor(Player sen) {
		int ping = sen.getPing();

		String c = "4";
		if (ping <= 10)
			c = "b";

		else if (ping <= 20)
			c = "a";
		else if (ping <= 50)
			c = "e";

		else if (ping <= 100)
			c = "6";
		else if (ping <= 250)
			c = "c";

		return c + ping;
	}
}
