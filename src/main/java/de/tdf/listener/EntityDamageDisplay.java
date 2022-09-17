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

public class EntityDamageDisplay implements Listener {

	@EventHandler
	public void handle(EntityDamageEvent ev) {
		Entity e = ev.getEntity();
		Damageable en = (Damageable) e;
		Location l = e.getLocation();

		ArmorStand as = (ArmorStand) l.getWorld().spawnEntity(l, EntityType.ARMOR_STAND);
		Bukkit.getScheduler().runTaskLater(Savior.getSavior(), as::remove, 2 * 20);

		as.setMarker(true);
		as.setCustomNameVisible(true);
		as.setCustomName("§" + getPercentageColor(damagePercentage(en.getMaxHealth(), ev.getDamage())) + ev.getDamage());
		as.setSmall(true);
		as.setHelmet(new ItemStack(Material.GOLDEN_SWORD));

		int i = 0;
		tpAs(as, i);

		if (e instanceof EntityDamageByEntityEvent event) {

		}
	}

	public ChatColor getPercentageColor(double percent) {
		double toHex = percent * 7.65;

		double r = -255 + percent * 4 <= 255 ? percent * 4 : 255,
				g = percent * 2 <= 255 ? percent * 2 : 255;

		return Language.colorFromRGB((int) r, (int) g, 0);
	}

	/**
	 * Deprecated, since returned values are inaccurate.
	 */
	@Deprecated
	public double damagePercentage(double maxHealth, double damage) {
		double percent = 100 * damage / maxHealth;
		return Math.round(percent) / 100f;
	}

	public void tpAs(ArmorStand as, int i) {
		if (i++ >= 25) return;
		if (!as.isDead()) as.teleport(as.getLocation().add(0, 0.1, 0));

		tpAs(as, i);
	}
}
