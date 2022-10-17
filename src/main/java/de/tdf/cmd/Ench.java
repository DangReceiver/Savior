package de.tdf.cmd;

import de.tdf.language.Language;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.StringUtil;

import java.util.*;

public class Ench implements CommandExecutor, TabCompleter {

	private static final List<String> CMD;

	@Override
	public boolean onCommand(final CommandSender sen, final Command command, final String label, final String[] args) {

		if (!(sen instanceof Player p)) {
			sen.sendMessage(Language.PRE + Language.getMessage(Language.getServerLang(), "no_player"));
			return false;
		}

		if (!p.hasPermission("Savior.Ench")) {
			p.sendMessage(Language.PRE + String.format(Language.getMessage(Language.getLang(p),
					"insufficient_permission"), "Savior.Ench"));
			return false;
		}

		if (args.length == 2) {
			final ItemMeta m = p.getInventory().getItemInMainHand().getItemMeta();
			int level;

			try {
				level = Integer.parseInt(args[1]);

				if (level < 1 || level >= 16001) {
					if (level != 0) {
						p.sendMessage(Language.PRE + String.format(Language.getMessage(Language.getLang(p),
								"argument_integer_range"), "1 → 16.000"));
						return true;
					}

					m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					p.sendMessage(Language.PRE + Language.getMessage(Language.getLang(p), "ench_level_zero_hide_enchants"));
				}

			} catch (NumberFormatException e) {
				p.sendMessage(Language.PRE + String.format(Language.getMessage(Language.getLang(p),
						"argument_specifics"), 1, "integer"));
				return true;
			}

			final String s = args[0].toUpperCase();
			if (Enchantment.getByName(s) != null) {

				final Enchantment ench = Enchantment.getByName(s);
				final ItemStack item = p.getInventory().getItemInMainHand();

				m.addEnchant(ench, level, true);
				p.sendMessage(Language.PRE + String.format(Language.getMessage(Language.getLang(p),
						"ench_added"), args[0], level));
				item.setItemMeta(m);

				p.getInventory().setItemInMainHand(item);
				return true;
			}
			p.sendMessage(Language.PRE + Language.getMessage(Language.getLang(p), "ench_result_invalid"));

		} else
			p.sendMessage(Language.PRE + Language.getMessage(Language.getLang(p), "arg_length"));
		return false;
	}

	public List<String> onTabComplete(final CommandSender sen, final Command cmd, final String lab, final String[] args) {
		final List<String> com = new ArrayList<String>(), cmd2 = new ArrayList<String>();
		if (args.length == 1) {
			com.clear();
			for (final Enchantment e : Enchantment.values())
				if (e.getName().contains(args[0]))
					com.add(e.getName());
			StringUtil.copyPartialMatches(args[0], cmd2, (Collection) com);
		} else if (args.length == 2) {

			for (final String s : CMD)
				if (s.contains(args[1]))
					com.add(s);
			StringUtil.copyPartialMatches(args[0], Ench.CMD, (Collection) com);
		}
		Collections.sort(com);
		return com;
	}

	static {
		CMD = Arrays.asList("0", "1", "2", "3", "4", "5", "8", "10", "15", "20", "25", "30", "50",
				"75", "100", "150", "250", "500", "1000", "2500", "8000", "12000", "16000");
	}
}
