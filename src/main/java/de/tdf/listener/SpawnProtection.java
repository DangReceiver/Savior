package de.tdf.listener;

import de.tdf.language.Language;
import net.kyori.adventure.text.Component;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class SpawnProtection implements Listener {

	@EventHandler
	public void handle(EntityDamageEvent e) {

		if (!(e.getEntity() instanceof Player p)) return;
		if (!p.getWorld().getName().contains("Spawn")) return;

		if (e instanceof EntityDamageByEntityEvent ev)
			if (ev.getDamager().hasPermission("Savior.SpawnProtection.Bypass")) return;

		e.setCancelled(true);
		p.sendActionBar(Component.text(Language.PRE + Language.getMessage(Language.getLang(p), "spawn_action_cancelled")));

		int i = 0;
		de.tdf.listener.methods.Sound.oneByOne(p, Sound.BLOCK_NOTE_BLOCK_BASS, 2,
				0.5f, 0f, true, 0.65f, 6, i);
	}
}
