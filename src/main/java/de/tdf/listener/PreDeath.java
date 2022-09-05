package de.tdf.listener;

import de.tdf.savior.Savior;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Particle;
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
		if (p.getLastDamageCause() == null) return;
		p.sendMessage("" + p.getLastDamageCause().getCause().name());

		if (e.getDamage() >= p.getHealth() && p.getLastDamageCause().getCause() != EntityDamageEvent.DamageCause.VOID) {
			e.setCancelled(true);

			float fs = p.getFlySpeed();
			p.setFlySpeed(0);
			p.teleport(p.getLocation().add(0, 0.001, 0));

			p.setAllowFlight(true);
			p.setFlying(true);

			p.setGameMode(GameMode.SPECTATOR);

			int i = 0;
			boost(p, i, fs);
		}
	}

	public void boost(Player p, int i, float fs) {
		if (i > 20) {
			p.setFlying(false);
			p.setAllowFlight(false);
			p.setFlySpeed(fs);

			p.setGameMode(GameMode.SURVIVAL);
//			p.setLastDamageCause(new EntityDamageEvent());       !!!!!!!!
			p.damage(p.getHealth() + 1);
			return;
		}

		Bukkit.getScheduler().runTaskLater(Savior.getSavior(), () -> {

			p.setVelocity(p.getVelocity().add(new Vector(0, 1.5, 0)));
			p.playSound(p.getLocation(), Sound.ENTITY_GHAST_SHOOT, 0.75f, 1.4f);
			p.spawnParticle(Particle.HEART, p.getLocation().add(0, -2, 0), 1);

			int temp = i + 1;
			boost(p, temp, fs);
		}, 2);
	}
}
