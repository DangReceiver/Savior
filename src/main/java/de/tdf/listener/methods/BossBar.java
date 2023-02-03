package de.tdf.listener.methods;

import de.tdf.savior.Savior;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.Player;

public class BossBar {

	public static void timeToBroadcastBar(Player p, String name) {
		org.bukkit.boss.BossBar b = Bukkit.createBossBar(name, BarColor.BLUE, BarStyle.SOLID);

		b.setProgress(0);
		b.setVisible(true);
		b.addPlayer(p);
		b.addFlag(BarFlag.CREATE_FOG);

		Bukkit.getScheduler().runTaskLater(Savior.getSavior(), () -> loadBossBar(b), 30);
	}

	public static void loadBossBar(org.bukkit.boss.BossBar b) {
		if (b.getProgress() + 0.0325 >= 1) {
			dischargeBossBar(b);
			return;
		}

		b.setProgress(b.getProgress() + 0.0325);
		Bukkit.getScheduler().runTaskLater(Savior.getSavior(), () -> loadBossBar(b), 3);
	}

	public static void dischargeBossBar(org.bukkit.boss.BossBar b) {
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

}
