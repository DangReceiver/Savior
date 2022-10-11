package de.tdf.listener;

import de.tdf.savior.Savior;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.awt.*;

public class EntityDamageDisplay implements Listener {

	@EventHandler
	public void handle(EntityDamageEvent ev) {
		if (ev.isCancelled()) return;

		Entity e = ev.getEntity();
		if (!(e instanceof Damageable en)) return;

		Location l = e.getLocation();
		ArmorStand as = (ArmorStand) l.getWorld().spawnEntity(l.clone().add(0, 0.65, 0), EntityType.ARMOR_STAND);

		as.setMarker(true);
		as.setInvulnerable(true);
		as.setInvisible(true);
		as.setCustomNameVisible(true);
		as.setCustomName(getPercentageColor(damagePercentage(en.getMaxHealth(),
				ev.getDamage())) + "" + ((double) Math.round(ev.getDamage() * 100) / 100));
		as.setSmall(true);
		as.setHelmet(new ItemStack(Material.RED_TULIP));

		int i = 0;
		Bukkit.getScheduler().runTaskLater(Savior.getSavior(), () -> tpAs(as, i), 4);
	}

	public ChatColor getPercentageColor(double percent) {
		double value = (percent * 1.2 <= 1 ? percent * 1.2 : 1) * 255;

		int r = (int) value, g = 255 - (int) value;

		return net.md_5.bungee.api.ChatColor.of(new Color(r, g, 0));
	}

	/**
	 * Deprecated, since returned values are inaccurate.
	 */
	@Deprecated
	public double damagePercentage(double maxHealth, double damage) {
		double percent = damage / maxHealth * 100;
		percent = Math.round(percent);

		return percent / 100;
	}

	public void tpAs(ArmorStand as, int i) {
		int copy = i + 1;

		if (copy >= 18) {
			Bukkit.getScheduler().runTaskLater(Savior.getSavior(), () -> {
				if (!as.isDead()) as.remove();
			}, 20);
			return;
		}

		if (!as.isDead()) as.teleport(as.getLocation().add(0, 0.175, 0));
		Bukkit.getScheduler().runTaskLater(Savior.getSavior(), () -> tpAs(as, copy), 1);
	}
}
