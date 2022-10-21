package de.tdf.listener;

import de.tdf.language.Language;
import net.kyori.adventure.text.Component;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class Teleport implements Listener {

	@EventHandler
	public void handle(PlayerTeleportEvent e) {
		Player p = e.getPlayer();
		if (p.getLastDamageCause() == null) return;

		if (!(e.getCause() == PlayerTeleportEvent.TeleportCause.SPECTATE &&
				p.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.VOID)) return;

		p.sendActionBar(Component.text(Language.PRE + Language.getMessage(Language.getLang(p),
				"death_action_cancel_teleport")));

		int i = 0;
		de.tdf.methods.Sound.oneByOne(p, Sound.BLOCK_NOTE_BLOCK_BASS, 2,
				0.5f, 0f, true, 0.65f, 6, i);
		e.setCancelled(true);
	}
}
