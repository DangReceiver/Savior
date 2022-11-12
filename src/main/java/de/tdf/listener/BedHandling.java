package de.tdf.listener;

import de.tdf.language.Language;
import io.papermc.paper.event.player.PlayerBedFailEnterEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BedHandling implements Listener {

	@EventHandler
	public void handle(PlayerBedFailEnterEvent e) {
		Component msg = e.getMessage();
		e.setMessage(Component.text(Language.PRE + msg));
	}

	@EventHandler
	public void handle(PlayerBedEnterEvent e) {
		Player p = e.getPlayer();
		PlayerBedEnterEvent.BedEnterResult br = e.getBedEnterResult();

		if (br == PlayerBedEnterEvent.BedEnterResult.OK && !p.hasPotionEffect(PotionEffectType.DARKNESS))
			p.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS,
					Integer.MAX_VALUE, 4, false, false, false));
	}

	@EventHandler
	public void handle(PlayerBedLeaveEvent e) {
		Player p = e.getPlayer();
		p.removePotionEffect(PotionEffectType.DARKNESS);
	}
}
