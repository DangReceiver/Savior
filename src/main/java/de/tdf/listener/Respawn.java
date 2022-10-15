package de.tdf.listener;

import de.tdf.PC;
import de.tdf.language.Language;
import de.tdf.savior.Savior;
import org.bukkit.Location;
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

		e.setRespawnLocation(Savior.getSafeSpawnLocation());

		PC pcd = PC.loadConfig(p);
		if (!pcd.hasDeathLocation()) {
			p.sendMessage(Language.PRE + Language.getMessage(Language.getLang(p), "no_death_location"));
			return;
		}

		pcd.setDeathLocation(null);
		pcd.setRespawnRequired(false);
		pcd.savePCon();
	}
}
