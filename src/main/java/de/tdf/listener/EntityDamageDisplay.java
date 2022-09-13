package de.tdf.listener;

import de.tdf.savior.Savior;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageDisplay implements Listener {

	@EventHandler
	public void handle(EntityDamageEvent ev) {

		Entity e = ev.getEntity();
		Location l = e.getLocation();

		ArmorStand as = (ArmorStand) l.getWorld().spawnEntity(l, EntityType.ARMOR_STAND);
		Bukkit.getScheduler().runTaskLater(Savior.getSavior(), as::remove, 2 * 20);

		as.setMarker(true);
		as.setCustomNameVisible(true);
		as.setCustomName("§c" + ev.getDamage());

		int i = 0;
		tpAs(as, i);

		if (e instanceof EntityDamageByEntityEvent event) {

		}
	}

	public void tpAs(ArmorStand as, int i) {
		if (i++ >= 25) return;
		if (!as.isDead()) as.teleport(as.getLocation().add(0, 0.1, 0));

		tpAs(as, i);
	}
}
