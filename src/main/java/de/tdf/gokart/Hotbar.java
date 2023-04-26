package de.tdf.gokart;

import de.tdf.language.Language;
import io.papermc.paper.event.player.PlayerInventorySlotChangeEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedMainHandEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class Hotbar implements Listener {

	@EventHandler
	public void handle(PlayerInventorySlotChangeEvent e) {
		Player p = e.getPlayer();
		int rawSlot = e.getRawSlot(),
				slot = e.getSlot();
		int difference = rawSlot - slot;

		p.sendMessage(Language.PRE + String.format("raw: %s; slot: %s | difference: %s", rawSlot, slot, difference));
	}
}
