package de.tdf.listener;

import de.tdf.PC;
import de.tdf.language.Language;
import de.tdf.savior.Savior;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.util.Random;

public class PlayerConnection implements Listener {

	@EventHandler
	public void handle(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		PC pc = PC.loadConfig(p);

		if (pc.getLanguageString() == null) {
			pc.setLanguage(Language.getServerLang());
			pc.savePCon();
		}
		File lf = pc.getLanguage();
		Language.setLang(p, lf);

		e.setJoinMessage(null);
		Language.broadcastArg("player_join_" + new Random().nextInt(13), p.getName());

		p.setGameMode(GameMode.ADVENTURE);
		p.teleport(Savior.getSafeSpawnLocation());

		p.sendTitle(Language.PRE, Language.getMessage(lf, "welcome_back"), 30, 50, 50);
		p.sendMessage(Language.PRE + Language.getMessage(lf, "teleport_over_world"));

		Bukkit.getScheduler().runTaskLaterAsynchronously(Savior.getSavior(), () -> {
			p.sendMessage(Language.PRE + Language.getMessage(lf, "login_location_broadcast_warning"));

			Bukkit.getScheduler().runTaskLaterAsynchronously(Savior.getSavior(), () -> {
				Location lol = pc.getLogoutLocation();

				if (pc.hasLogoutLocation())
					Language.broadcastArg("login_location_broadcast", lol.getBlockX() + "", lol.getBlockY() + "", lol.getBlockZ() + "");
			}, 20 * 20);
		}, 3 * 20);
	}

	@EventHandler
	public void handle(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		e.setQuitMessage(null);

		Location l = p.getLocation();
		PC pc = PC.loadConfig(p);

		if (!p.getWorld().getName().equalsIgnoreCase("Spawn")) {
			pc.setLogoutLocation(l);
			pc.savePCon();
		}

		Language.broadcastArg("player_quit_" + new Random().nextInt(13), p.getName());
		Language.removePlayer(p);
	}
}
