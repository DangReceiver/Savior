package de.tdf.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class SpawnProtection implements Listener {

	@EventHandler
	public void handle(EntityDamageEvent e) {
		Entity en = e.getEntity();

		if (en.getWorld() == Bukkit.getWorld("Spawn")) {
			if (e instanceof EntityDamageByEntityEvent ev)
				if (ev.getDamager().hasPermission("Savior.SpawnProtection.Bypass")) return;

			e.setCancelled(true);
		}
	}
}
