package de.tdf.listener;

import de.tdf.language.Language;
import net.kyori.adventure.text.Component;
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
		e.setCancelled(true);
	}
}
