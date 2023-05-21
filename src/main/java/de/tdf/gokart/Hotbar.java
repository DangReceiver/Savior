package de.tdf.gokart;

import de.tdf.language.Language;
import io.papermc.paper.event.player.PlayerInventorySlotChangeEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedMainHandEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class Hotbar implements Listener {

	@EventHandler
	public void handle(PlayerItemHeldEvent e) {
		Player p = e.getPlayer();
		int newSlot = e.getNewSlot();
		int previousSlot = e.getPreviousSlot();
		int difference = previousSlot - newSlot;

//		p.sendMessage(Language.PRE + String.format("raw: %s; slot: %s | difference: %s", newSlot, previousSlot, difference));
	}
}
