package de.tdf.listener;

import de.tdf.PC;
import de.tdf.savior.Savior;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import javax.swing.*;
import java.awt.*;

public class SpawnButtonPush implements Listener {

	@EventHandler
	public void handle(PlayerInteractEvent e) {

		Player p = e.getPlayer();
		Block cb = e.getClickedBlock();

		if (cb == null) return;
		if (cb.getWorld() != Bukkit.getWorld("Spawn")) return;

		if (!cb.getType().toString().contains("BUTTON")) return;
		if (!cb.equals(Savior.getSafeSpawnLocation().getBlock())) return;

		PC pc = PC.loadConfig(p);

		if (pc.hasLogoutLocation()) p.teleport(pc.getLogoutLocation());
		else p.teleport(new Location(Bukkit.getWorld("world"), 0, 64, 0));

		int i = 0;
		de.tdf.methods.Sound.oneByOne(p, Sound.ENTITY_PLAYER_BREATH, 4,
				1.2f, 0.2f, false, 0.4f, 40, i);

		p.setGameMode(GameMode.SURVIVAL);
	}
}
