package de.tdf.listener;

import de.tdf.PC;
import de.tdf.savior.Savior;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class PreDeath implements Listener {

	@EventHandler
	public void handle(EntityDamageEvent e) {
		Entity en = e.getEntity();

		if (!(en instanceof Player p)) return;
		if (p.getLastDamageCause() == null) return;

		if (e.getDamage() >= p.getHealth() &&
				p.getLastDamageCause().getCause() != EntityDamageEvent.DamageCause.VOID) {
			e.setCancelled(true);

			PC pc = PC.loadConfig(p);
			pc.setDead(true);
			pc.savePCon();

			Location dl = p.getLocation();
			@Nullable ItemStack[] contents = p.getInventory().getContents();

			ArrayList<ItemStack> con = new ArrayList<>();
			for (ItemStack i : contents)
				if (i != null) con.add(i);

			p.setFlySpeed(0);
			p.teleport(dl.add(0, 0.001, 0));

			p.setAllowFlight(true);
			p.setFlying(true);

			p.setGameMode(GameMode.SPECTATOR);
			p.getInventory().clear();

			int i = 0;
			deathBoost(p, i, dl, con);
		}
	}

	public static void deathBoost(Player p, int i, Location dl, ArrayList<ItemStack> con) {

		if (i > 24) {
			p.setGameMode(GameMode.SURVIVAL);
			p.setHealth(0);

			p.setFlying(false);
			p.setAllowFlight(false);
			p.setFlySpeed(0.15f);

			int c = 0;
			nobelItemDrop(dl, con, c);
			return;
		}

		Bukkit.getScheduler().runTaskLater(Savior.getSavior(), () -> {

			p.setVelocity(p.getVelocity().add(new Vector(0, 1, 0)));
			p.spawnParticle(Particle.HEART, p.getLocation().add(0, -1.5, 0), 1);
			p.playSound(p.getLocation(), Sound.ENTITY_GHAST_SHOOT, 0.5f, 1.8f);

			p.setFlySpeed(0);
			int temp = i + 1;
			deathBoost(p, temp, dl, con);
		}, 2);
	}

	public static void nobelItemDrop(Location dl, ArrayList<ItemStack> con, int c) {
		int cTemp = c + 1;
		if (con.isEmpty()) return;

		Bukkit.getScheduler().runTaskLater(Savior.getSavior(), () -> {
			Item item = dl.getWorld().dropItemNaturally(dl.clone().add(0, 0.15, 0), con.get(0));
			item.setVelocity(item.getVelocity().add(new Vector(0, 0.4, 0)));

			con.remove(0);
			nobelItemDrop(dl, con, cTemp);
		}, 2);
	}
}
