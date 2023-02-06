package de.tdf.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class AddItemCooldown implements Listener {

    @EventHandler
    public void handle(PlayerItemConsumeEvent e) {
        ItemStack i = e.getItem();
        Player p = e.getPlayer();

        switch (i.getType()) {
            case GOLDEN_APPLE -> p.setCooldown(i.getType(), 4);
            case ENCHANTED_GOLDEN_APPLE -> p.setCooldown(i.getType(), 12);
            case POTION -> p.setCooldown(i.getType(), 3);
            case CROSSBOW, FLINT_AND_STEEL -> p.setCooldown(i.getType(), 1);
            case ENDER_EYE-> p.setCooldown(i.getType(), 2);

        }
    }
}
