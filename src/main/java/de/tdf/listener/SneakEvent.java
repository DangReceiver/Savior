package de.tdf.listener;

import de.tdf.language.Language;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class SneakEvent implements Listener {

	@EventHandler
	public void handle(PlayerToggleSneakEvent e) {
		Player p = e.getPlayer();
		if (p.isSneaking()) return;

		if (p.getInventory().getItemInMainHand().getType() == Material.FIREWORK_ROCKET ||
				p.getInventory().getItemInOffHand().getType() == Material.FIREWORK_ROCKET) {
			if (p.getGameMode() != GameMode.CREATIVE)
				p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE, 0.1f, 1.65f);

			p.sendActionBar(new TextComponent(Language.PRE +
					Language.getMessage(Language.getLang(p), "firework_jump_hint")));
		}
	}
}
