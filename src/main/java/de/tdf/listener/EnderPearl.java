package de.tdf.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;

public class EnderPearl implements Listener {

    @EventHandler
    public void handle(PlayerInteractEvent e) {
        if (e.isCancelled()) return;

        Player p = e.getPlayer();
        ItemStack i = e.getItem();
        Action a = e.getAction();

        if (!(i != null && i.getType() == Material.ENDER_PEARL)) return;
        if (!(a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK)) return;

        if (p.isSneaking())
            p.setCooldown(i.getType(), 3);
        else
            p.setCooldown(i.getType(), 7);
    }

    @EventHandler
    public void handle(ProjectileLaunchEvent ev) {
        if (ev.isCancelled()) return;

        Projectile e = ev.getEntity();
        ProjectileSource s = e.getShooter();

        if (!(s instanceof Player p)) return;
        if (p.isFlying()) e.setVelocity(e.getVelocity().multiply(2));

        if (p.isSneaking()) e.setVelocity(e.getVelocity().multiply(0.65));
        else e.setVelocity(e.getVelocity().multiply(1.25));
    }
}
