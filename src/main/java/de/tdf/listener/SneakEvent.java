package de.tdf.listener;

import de.tdf.language.Language;
import de.tdf.savior.Savior;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.PlayerInventory;

public class SneakEvent implements Listener {

    @EventHandler
    public void handle(PlayerToggleSneakEvent e) {
        Player p = e.getPlayer();

        if (p.isSneaking()) {
            sendEnderPearlActionBar(p);
            return;
        }

        if (p.getInventory().getItemInMainHand().getType() == Material.FIREWORK_ROCKET ||
                p.getInventory().getItemInOffHand().getType() == Material.FIREWORK_ROCKET) {
            if (p.getGameMode() != GameMode.CREATIVE)
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE, 0.1f, 1.65f);

            p.sendActionBar(Component.text(Language.PRE +
                    Language.getMessage(Language.getLang(p), "firework_jump_hint")));
        }
    }

    public void sendEnderPearlActionBar(Player p) {
        PlayerInventory in = p.getInventory();
        if (!p.isSneaking() || in.getItemInMainHand().getType() != Material.ENDER_PEARL
                && in.getItemInOffHand().getType() != Material.ENDER_PEARL) return;

        p.sendActionBar(Component.text(Language.PRE +
                Language.getMessage(Language.getLang(p), "ender_pearl_sneak_hint")));

        Bukkit.getScheduler().runTaskLater(Savior.getSavior(), () -> sendEnderPearlActionBar(p), 30);
    }
}
