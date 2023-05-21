package de.tdf.listener.essentials;

import de.tdf.language.Language;
import de.tdf.obj.PC;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;

public class Chat implements Listener {

	@EventHandler
	public void handle(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		PC pc = new PC(p);

		String f = "<prefix> §7‖ <counter_link><player_color><player_name><link> <message_color><message>";
		e.setFormat(f);

		List<Integer> dmc = pc.getDefaultMessageColor();
		ChatColor cc = Language.colorFromRGB(dmc.get(0), dmc.get(1), dmc.get(2));

		f = f.replaceAll("<player_name>", p.getName()).replaceAll("<player_color>", pc.getPlayerColor());
		f = f.replaceAll("<counter_link>", pc.getCounterLink()).replaceAll("<link>", pc.getLink());
		f = f.replaceAll("<message_color>", "" + cc)
				.replaceAll("<message>", e.getMessage());
		f = f.replaceAll("<prefix>", Language.PRE);

		if (p.hasPermission("Savior.Chat.Color")) f = f.replaceAll("&", "§");

		if (p.hasPermission("Savior.Chat.Tagging"))
			for (Player ap : Bukkit.getOnlinePlayers()) {
				if (f.contains(" " + ap.getName())) {
					f = f.replaceAll(" " + ap.getName(), " §" + Language.colorFromRGB(20,
							210, 80) + "§o@" + ap.getName() + cc);
					ap.playSound(ap.getLocation(), Sound.BLOCK_NOTE_BLOCK_XYLOPHONE, 0.4f, 1.1f);
				}
			}

		e.setFormat(f);
	}
}
