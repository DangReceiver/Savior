package de.tdf.listener;

import de.tdf.PC;
import de.tdf.language.Language;
import de.tdf.savior.Savior;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Random;

public class Death implements Listener {

	@EventHandler
	public void handle(PlayerDeathEvent e) {
		if (e.isCancelled()) return;

		Player p = e.getPlayer();
		Block block = p.getLocation().getBlock();

		e.setDeathMessage(null);

		Location dl = new Location(p.getWorld(), block.getX() + 0.5, block.getY(), block.getZ() + 0.5);
		Language.broadcastArg("player_death_" + new Random().nextInt(14), p.getName());

		PC pc = PC.loadConfig(p);
		pc.setDeathLocation(dl);
		pc.setRespawnRequired(true);
		pc.savePCon();

		BukkitScheduler bs = Bukkit.getScheduler();
		bs.runTaskLater(Savior.getSavior(), () -> p.sendMessage(Language.PRE + String.format(Language.getMessage(
				Language.getLang(p), "player_death_info"), dl.getX(), dl.getY(), dl.getZ())), 20);

		bs.runTaskLater(Savior.getSavior(), () -> p.sendMessage(Language.PRE + Language.getMessage(
				Language.getLang(p), "player_death_respawn_required")), 2 * 20);


		bs.runTaskLater(Savior.getSavior(), () -> {
			PC pcd = PC.loadConfig(p);

			if (pcd.getDeathLocation() != null) {
				p.sendMessage(Language.PRE + Language.getMessage(Language.getLang(p), "player_death_did_not_respawn"));
				Language.broadcastArg("death_location_broadcast", p.getName(),
						dl.getWorld().getName(), dl.getX() + "", dl.getY() + "", dl.getZ() + "");

				pcd.setDeathLocation(null);
				pcd.setRespawnRequired(false);
				pcd.savePCon();
			}
		}, 30 * 20);
	}
}
