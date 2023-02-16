package de.tdf.listener;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class StartToSprint implements Listener {

    @EventHandler
    public void handle(PlayerToggleSprintEvent e) {
        Player p = e.getPlayer();

        if (new Random().nextInt(41) != 0) return;
        if (p.isSprinting()) return;

        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80,
                2, false, false, false));

        int i = 0;
        de.tdf.listener.methodPorting.Sound.oneByOne(p, Sound.BLOCK_NOTE_BLOCK_GUITAR, 2,
                1f, 0.4f, true, 0.65f, 8, i);

    }
}
