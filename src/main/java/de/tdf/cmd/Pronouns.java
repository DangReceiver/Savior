package de.tdf.cmd;

import de.tdf.PC;
import de.tdf.language.Language;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Pronouns implements CommandExecutor {

	public static List<String> they = new ArrayList<>(Arrays.asList("they", "them", "their", "theirs", "themselves")),
			name = new ArrayList<>(Arrays.asList("@pn", "@pn", "@pn's", "@pn")),
			he = new ArrayList<>(Arrays.asList("he", "him", "his", "himself")),
			she = new ArrayList<>(Arrays.asList("she", "her", "hers", "herself")),
			it = new ArrayList<>(Arrays.asList("it", "it", "its", "its", "itself"));

	public static List[] lists = new List[]{they, she, he, it, name};

	@Override
	public boolean onCommand(CommandSender sen, Command cmd, String lab, String[] args) {

		if (!(sen instanceof Player p)) {
			sen.sendMessage(Language.PRE + Language.getMessage(Language.getServerLang(), "no_player"));
			return false;
		}

		PC pc = PC.loadConfig(p);

		File f = Language.getLang(p);
		if (args.length != 2) {
			p.sendMessage(Language.PRE + Language.getMessage(f, "argument_length"));
			return false;
		}

		int input;
		try {
			input = Integer.parseInt(args[0]);

		} catch (NumberFormatException e) {
			p.sendMessage(Language.PRE + Language.getMessage(f, "invalid_input"));
			return false;
		}

		if (pc.getPronouns() == input) {
			p.sendMessage(Language.PRE + Language.getMessage(f, "value_already_set"));
			return false;
		}

		pc.setPronouns(input);
		pc.savePCon();

		p.sendMessage(Language.PRE + Language.getMessage(f, "pronouns_example_pre"));
		p.sendMessage("§7" + replaceText(Language.getMessage(f, "pronouns_example_sentence_"
				+ new Random().nextInt(3)), pc));

		return false;
	}

	public String replaceText(String text, PC pc) {
		int pn = pc.getPronouns();
		List current = lists[pn];

		String pName = pc.getOfflinePlayer().getName();
		text = text.replaceAll("%pn", pName);

		for (int i = 0; i <= 4; i++)
			text = text.replaceAll("%p" + i, current.get(i).toString());

		if (isDynamic(pn))
			text = text.replaceAll("@pn", pName);

		return text;
	}

	public boolean isDynamic(int list) {
		return list == 4;
	}
}
