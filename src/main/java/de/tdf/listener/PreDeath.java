package de.tdf.listener;

import de.tdf.savior.Savior;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

public class PreDeath implements Listener {

	@EventHandler
	public void handle(EntityDamageEvent e) {

		Entity en = e.getEntity();
		if (!(en instanceof Player p)) return;

		if (e.getDamage() >= p.getHealth() && p.getKiller().getType() != EntityType.ITEM_FRAME) {
			e.setCancelled(true);

			float fs = p.getFlySpeed();
			p.setFlySpeed(0);
			p.setFlying(true);

			p.setGameMode(GameMode.SPECTATOR);

			int i = 0;
			boost(p, i, fs);
		}
	}

	public void boost(Player p, int i, float fs) {
		if (i > 15) {
			p.setFlying(false);
			p.setFlySpeed(fs);

			Entity e = p.getWorld().spawnEntity(p.getLocation().add(0, -10000, 0), EntityType.ITEM_FRAME);
			p.damage(p.getHealth() + 1, e);
			e.remove();

			p.setGameMode(GameMode.SURVIVAL);
			return;
		}

		Bukkit.getScheduler().runTaskLater(Savior.getSavior(), () -> {

			p.getVelocity().add(new Vector(0, 0.5, 0));
			p.playSound(p.getLocation(), Sound.ENTITY_GHAST_SHOOT, 0.75f, 1.4f);

			int temp = i + 1;
			boost(p, temp, fs);
		}, 2);
	}
}
