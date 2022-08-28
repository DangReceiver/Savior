package de.tdf.listener;

import de.tdf.language.Language;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Random;

public class Death implements Listener {

	@EventHandler
	public void handle(PlayerDeathEvent e) {
		Player p = e.getPlayer();
		Block block = p.getLocation().getBlock();

		e.setDeathMessage(null);

		Language.broadcastArg("player_death_" + new Random().nextInt(4), p.getName());
		p.sendMessage(Language.PRE + String.format(Language.getMessage(Language.getLang(p),
				"player_death_info"), block.getX(), block.getY(), block.getZ()));
	}
}
