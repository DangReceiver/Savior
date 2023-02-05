package de.tdf.listener;

import de.tdf.PC;
import de.tdf.cmd.manage.Build;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class WorldChanged implements Listener {

	@EventHandler
	public void handle(PlayerChangedWorldEvent e) {
		Player p = e.getPlayer();

		if (p.getWorld() == Bukkit.getWorld("Spawn")) {
			p.setGameMode(GameMode.ADVENTURE);

		} else {
			Build.disableBuild(p, PC.loadConfig(p));
			p.setGameMode(GameMode.SURVIVAL);

		}
	}
}
