package de.tdf.listener;

import io.papermc.paper.event.player.PlayerItemCooldownEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ItemCooldown implements Listener {

    @EventHandler
    public void handle(PlayerItemCooldownEvent e) {
        Player p = e.getPlayer();
//        Material m = e.getType();
        int cd = e.getCooldown();

        int levelFraction = p.getLevel() / 100;
        cd = (int) (cd * 2.5 - levelFraction);

        e.setCooldown(Math.max(cd, 0));
    }
}
