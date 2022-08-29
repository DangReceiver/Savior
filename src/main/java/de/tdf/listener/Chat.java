package de.tdf.listener;

import de.tdf.PC;
import de.tdf.language.Language;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Chat implements Listener {

	@EventHandler
	public void handle(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		p.sendMessage(e.getFormat());

		String f = "<counter_link><player_color><player_name><link> <message_color><message>\t<suffix>";
		e.setFormat(f);

		p.sendMessage(e.getFormat());
		PC pc = PC.loadConfig(p);

		f = f.replaceAll("<player_name>", p.getName()).replaceAll("<player_color>", pc.getPlayerColor());
		f = f.replaceAll("<counter_link>", pc.getCounterLink()).replaceAll("<link>", pc.getLink());
		f = f.replaceAll("<message_color>", pc.getDefaultMessageColor()).replaceAll("<message>", e.getMessage());
		e.setFormat(f.replaceAll("<suffix>", Language.PRE));

		p.sendMessage(e.getFormat());
	}
}
