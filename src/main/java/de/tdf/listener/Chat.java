package de.tdf.listener;

import de.tdf.PC;
import de.tdf.language.Language;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;

public class Chat implements Listener {

	@EventHandler
	public void handle(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		PC pc = PC.loadConfig(p);

		String f = "‖ <counter_link><player_color><player_name><link> <message_color><message>  <suffix>";
		e.setFormat(f);

		List<Integer> dmc = pc.getDefaultMessageColor();

		f = f.replaceAll("<player_name>", p.getName()).replaceAll("<player_color>", pc.getPlayerColor());
		f = f.replaceAll("<counter_link>", pc.getCounterLink()).replaceAll("<link>", pc.getLink());
		f = f.replaceAll("<message_color>", "" + Language.colorFromRGB(dmc.get(0), dmc.get(1), dmc.get(2)))
				.replaceAll("<message>", e.getMessage());
		e.setFormat(f.replaceAll("<suffix>", Language.PRE));
	}
}
