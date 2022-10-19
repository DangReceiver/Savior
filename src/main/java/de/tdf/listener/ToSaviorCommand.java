package de.tdf.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class ToSaviorCommand implements Listener {

	@EventHandler
	public void handle(PlayerCommandPreprocessEvent e) {

		if (e.getMessage().split(" ")[0].equalsIgnoreCase("tps"))
			e.setMessage("/Savior:Tps");
	}
}
