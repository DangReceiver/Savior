package de.tdf.listener;

import de.tdf.savior.Savior;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.util.Vector;

public class FireWorkJump implements Listener {

    @EventHandler
    public void handle(PlayerInteractEvent e) {
        if (e.isCancelled()) return;

        Player p = e.getPlayer();
        ItemStack i = e.getItem();
        Material m = i.getType();

        if (m == Material.FIREWORK_ROCKET && p.isSneaking()) {

            if (e.getClickedBlock() == null) return;
            FireworkMeta fwm = (FireworkMeta) i.getItemMeta();

            if (i.getAmount() >= 1) i.setAmount(i.getAmount() - 1);
            else i.setType(Material.AIR);

            p.setVelocity(p.getVelocity().add(new Vector(0, fwm.getPower() * 0.125, 0)));
            Bukkit.getScheduler().runTaskLater(Savior.getSavior(), () -> {
                p.setVelocity(p.getVelocity().add(new Vector(0, fwm.getPower() * 0.125, 0)));

                Bukkit.getScheduler().runTaskLater(Savior.getSavior(), () ->
                        p.setVelocity(p.getVelocity().add(new Vector(0, fwm.getPower() * 0.125, 0))), 2);
            }, 2);
        }
    }
}