package de.tdf.listener;

import de.tdf.savior.Savior;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.util.Vector;

import java.util.Random;

public class StartToSprint implements Listener {

//	@EventHandler
	public void handle(PlayerToggleSprintEvent e) {
		Player p = e.getPlayer();
		if (p.isSprinting()) return;

		Random r = new Random();
		int i = r.nextInt(4);

		Bukkit.getScheduler().runTaskLater(Savior.getSavior(), () -> {
			p.setVelocity(p.getVelocity().setX(p.getVelocity().getX() * 5).setZ(p.getVelocity().getZ() * 5).add(new Vector(0, 0.2, 0)));
			p.sendTitle("whoosh", "you were boosted", 4, 30, 10);
		}, 3);
	}
}
