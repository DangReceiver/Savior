package de.tdf.methods;

import de.tdf.savior.Savior;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Sound {

	public static void oneByOne(Player p, org.bukkit.Sound s, int times, float startAtPitch, float pitch, boolean up,
								float volume, long delay, int current) {

		float offset = pitch * -(up ? 2 * pitch * (times + 1) : 0);
		p.playSound(p.getLocation(), s, volume, (startAtPitch + offset >= 2 ? 2 : startAtPitch + offset));

		if (times >= current) return;
		Bukkit.getScheduler().runTaskLater(Savior.getSavior(), () ->
				oneByOne(p, s, times, startAtPitch, pitch, up, volume, delay, current + 1), delay);
	}
}
