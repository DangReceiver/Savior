package de.tdf.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

//public class Health implements CommandExecutor {
//	@Override
//	public boolean onCommand(final CommandSender send, final Command cmd, final String label, final String[] args) {
//		if (send instanceof Player) {
//			final Player p = (Player) send;
//			if (args.length >= 1 && args.length <= 2) {
//				if (args[0].equalsIgnoreCase("h") || args[0].equalsIgnoreCase("help")) {
//					Eng.argsUsage(send, "/heath <amount> [<heal>] [<Player>]", true);
//					return true;
//				}
//				int i = -1;
//				try {
//					i = Integer.parseInt(args[0]);
//				} catch (NumberFormatException e) {
//					Eng.entryType(send, "0", "Integer");
//					return true;
//				}
//				if (i > 2048) {
//					Eng.numbHighLow(send, "0", "Integer", "2048", false);
//					return true;
//				}
//				if (i < 1) {
//					Eng.numbHighLow(send, "0", "Integer", "0", true);
//					return true;
//				}
//				Eng.atChange(send, "action", "§5Max Health", true, "" + p.getMaxHealth(), "" + i, true, false);
//				p.setMaxHealth((double) i);
//				if (args.length >= 2 && Boolean.parseBoolean(args[1])) {
//					p.setHealth(p.getMaxHealth());
//				}
//			} else {
//				if (args.length == 3) {
//					try {
//						final int i = Integer.parseInt(args[0]);
//						if (i > 2048) {
//							Eng.numbHighLow(send, "0", "Integer", "2048", false);
//							return true;
//						}
//						if (i < 1) {
//							Eng.numbHighLow(send, "0", "Integer", "1", true);
//							return true;
//						}
//						final Player tar = Bukkit.getPlayer(args[2]);
//						Eng.atChange((CommandSender) tar, "chat", "§5Max Health", true, "" + tar.getMaxHealth(), "" + i, true, false);
//						Eng.atChange(send, "action", "§5Max Health", true, "" + tar.getMaxHealth(), "" + i, true, true);
//						tar.setMaxHealth((double) i);
//						if (args.length >= 2) {
//							tar.setHealth(tar.getMaxHealth());
//						}
//						return false;
//					} catch (NumberFormatException e2) {
//						Eng.entryType(send, "0", "Boolean");
//						return true;
//					}
//				}
//				Eng.tryHelp(send, cmd, true);
//			}
//		}
//		return false;
//	}
//}
