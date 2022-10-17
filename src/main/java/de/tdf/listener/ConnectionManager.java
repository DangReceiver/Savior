package de.tdf.listener;

import de.tdf.PC;
import de.tdf.language.Language;
import de.tdf.savior.Savior;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.util.Random;

public class ConnectionManager implements Listener {

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

		p.sendTitle(Language.PRE, String.format(Language.getMessage(lf, "welcome_back"), p.getName()), 30, 50, 50);
		p.sendMessage(Language.PRE + Language.getMessage(lf, "join_teleport_spawn"));

		timeToBroadcastBar(p);

		Bukkit.getScheduler().runTaskLaterAsynchronously(Savior.getSavior(), () -> {
			p.sendMessage(Language.PRE + Language.getMessage(lf, "login_location_broadcast_warning"));

			Bukkit.getScheduler().runTaskLaterAsynchronously(Savior.getSavior(), () -> {
				Location lol = pc.getLogoutLocation();

				if (pc.hasLogoutLocation() && p.isOnline())
					Language.broadcastArg("login_location_broadcast", p.getName(),
							lol.getBlockX() + "", lol.getBlockY() + "", lol.getBlockZ() + "");
			}, 20 * 20);
		}, 6 * 20);
	}

	public void timeToBroadcastBar(Player p) {
		BossBar b = Bukkit.createBossBar(Language.getMessage(Language.getLang(p),
						Language.colorFromRGB(180, 150, 20) + "spawn_location_bossbar"),
				BarColor.BLUE, BarStyle.SOLID);

		b.setProgress(0);
		b.setVisible(true);
		b.addPlayer(p);
		b.addFlag(BarFlag.CREATE_FOG);

		Bukkit.getScheduler().runTaskLater(Savior.getSavior(), () -> loadBossBar(b), 30);
	}

	public void loadBossBar(BossBar b) {
		if (b.getProgress() + 0.0325 >= 1) {
			dischargeBossBar(b);
			return;
		}

		b.setProgress(b.getProgress() + 0.0325);
		Bukkit.getScheduler().runTaskLater(Savior.getSavior(), () -> loadBossBar(b), 3);
	}

	public void dischargeBossBar(BossBar b) {
		if (b.getProgress() - 0.005 <= 0) {
			b.removeAll();
			return;
		}

		if (b.getProgress() <= 0.76 && b.getProgress() >= 0.74) {
			b.setColor(BarColor.GREEN);
		} else if (b.getProgress() <= 0.56 && b.getProgress() >= 0.54) {
			b.setColor(BarColor.YELLOW);
		} else if (b.getProgress() <= 0.36 && b.getProgress() >= 0.34) {
			b.setColor(BarColor.RED);
		} else if (b.getProgress() <= 0.16 && b.getProgress() >= 0.14) {
			b.setColor(BarColor.PURPLE);
		}

		b.setProgress(b.getProgress() - 0.005);
		Bukkit.getScheduler().runTaskLater(Savior.getSavior(), () -> dischargeBossBar(b), 2);
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
