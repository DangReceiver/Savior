package de.tdf.listener;

import de.tdf.language.Language;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class Teleport implements Listener {

	@EventHandler
	public void handle(PlayerTeleportEvent e) {
		Player p = e.getPlayer();

		if (e.getCause() == PlayerTeleportEvent.TeleportCause.SPECTATE &&
				p.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.VOID) {

			p.sendActionBar(Language.PRE + Language.getMessage(Language.getLang(p),
					"player_action_spectate_teleport_cancel_death"));
			e.setCancelled(true);
		}
	}
}
