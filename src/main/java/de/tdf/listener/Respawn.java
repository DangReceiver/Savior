package de.tdf.listener;

import de.tdf.language.Language;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.io.File;

public class Respawn implements Listener {

	@EventHandler
	public void handle(PlayerRespawnEvent e) {
		Player p = e.getPlayer();

		File l = Language.getLang(p);
		p.sendTitle(Language.getMessage(l, "you_died_title"), Language.getMessage(l,
				"you_died_subtitle"), 20, 60, 40);
	}
}
