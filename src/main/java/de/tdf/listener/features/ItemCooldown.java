package de.tdf.listener.features;

import io.papermc.paper.event.player.PlayerItemCooldownEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

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

    @EventHandler
    public void handle(PlayerItemConsumeEvent e) {
        ItemStack i = e.getItem();
        Player p = e.getPlayer();

        switch (i.getType()) {
            case GOLDEN_APPLE -> p.setCooldown(i.getType(), 4);
            case ENCHANTED_GOLDEN_APPLE -> p.setCooldown(i.getType(), 12);
            case POTION -> p.setCooldown(i.getType(), 3);
            case CROSSBOW, FLINT_AND_STEEL -> p.setCooldown(i.getType(), 1);
            case ENDER_EYE -> p.setCooldown(i.getType(), 2);

        }
    }
}
