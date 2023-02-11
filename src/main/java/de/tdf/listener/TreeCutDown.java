package de.tdf.listener;

import de.tdf.savior.Savior;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class TreeCutDown implements Listener {

	@EventHandler
	public void onTreeFarm(final BlockBreakEvent e) {
		if (e.isCancelled()) return;

		Block b = e.getBlock();
		Player p = e.getPlayer();

		if (!(p.isSneaking() || p.getGameMode() == GameMode.SURVIVAL || p.getGameMode() == GameMode.ADVENTURE)) return;
		ItemStack ih = p.getInventory().getItemInMainHand();

		if (!(ih.getType().toString().contains("_AXE") && b.getType().toString().contains("LOG"))) return;
		short d = 1;

		for (int t = 0; t <= 48; t++) {
			int eTimes = t;
			d = (short) (d + 3);

			if (!(e.getBlock().getLocation().add(0, (eTimes + 1), 0).getBlock().getType().equals(b.getType()))) return;
			Bukkit.getScheduler().runTaskLater(Savior.getSavior(), () -> {

				if (!p.getInventory().getItemInMainHand().getType().toString().contains("_AXE")) return;
				ih.setDurability((short) (ih.getDurability() + 1));

				if (ih.getType().getMaxDurability() > ih.getDurability()) {
					Block above = e.getBlock().getLocation().add(0.0D, (eTimes + 1), 0.0D).getBlock();

					Bukkit.getServer().getWorld("world").playSound(above.getLocation(),
							Sound.BLOCK_WOOD_BREAK, 0.4F + (eTimes / 100), 0.6F + (eTimes / 100));
					above.breakNaturally(ih);

				} else {
					ih.setAmount(0);
					p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 0.4F, 0.75F);
					return;
				}
			}, d);
		}
	}
}
