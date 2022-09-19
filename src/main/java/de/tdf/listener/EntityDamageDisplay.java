package de.tdf.listener;

import de.tdf.language.Language;
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
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.awt.*;

public class EntityDamageDisplay implements Listener {

	@EventHandler
	public void handle(EntityDamageEvent ev) {
		Entity e = ev.getEntity();
		Damageable en = (Damageable) e;
		Location l = e.getLocation();

		ArmorStand as = (ArmorStand) l.getWorld().spawnEntity(l, EntityType.ARMOR_STAND);
		Bukkit.getScheduler().runTaskLater(Savior.getSavior(), as::remove, 2 * 20);

		as.setMarker(true);
		as.setInvulnerable(true);
		as.setInvisible(true);
		as.setCustomNameVisible(true);
		as.setCustomName(getPercentageColor(damagePercentage(en.getMaxHealth(), ev.getDamage())) + ""
				+ damagePercentage(en.getMaxHealth(), ev.getDamage()) + "  /  " + ev.getDamage());
		as.setSmall(true);
		as.setHelmet(new ItemStack(Material.RED_TULIP));

		int i = 0;
		tpAs(as, i);

		ChatColor color = getPercentageColor(damagePercentage(en.getMaxHealth(), ev.getDamage()));
		Bukkit.broadcastMessage("R: " + color.getColor().getRed()
				+ "\n G: " + color.getColor().getRed() + "\n B: " + color.getColor().getRed());

//		if (e instanceof EntityDamageByEntityEvent event) {
//
//		}
	}

	public ChatColor getPercentageColor(double percent) {
		double toHex = percent * 7.65;

		double r = 0, g = 0;
		try {
			r = Double.parseDouble((-255 + toHex * 4 <= 255 ? toHex * 4 : 255) + "");
			g = Double.parseDouble((toHex * 2 <= 255 ? toHex * 2 : 255) + "");
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}

		Bukkit.broadcastMessage("§eR: " + r + " G: " + g);
		return net.md_5.bungee.api.ChatColor.of(new Color((int) r, (int) g, 0));
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

		if (copy >= 22) {
			Bukkit.getScheduler().runTaskLater(Savior.getSavior(), () -> {
				if (!as.isDead()) as.remove();
			}, 15);
			return;
		}

		if (!as.isDead()) as.teleport(as.getLocation().add(0, 0.15, 0));
		Bukkit.getScheduler().runTaskLater(Savior.getSavior(), () -> tpAs(as, i), 1);
	}
}
