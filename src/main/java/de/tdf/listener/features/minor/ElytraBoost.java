package de.tdf.listener;

import com.destroystokyo.paper.event.player.PlayerElytraBoostEvent;
import de.tdf.language.Language;
import de.tdf.listener.methodPorting.Sound;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.Random;

public class ElytraBoost implements Listener {

    @EventHandler
    public void handle(PlayerElytraBoostEvent e) {
        Player p = e.getPlayer();
        Firework fw = e.getFirework();
        FireworkMeta fwm = fw.getFireworkMeta();

        if (new Random().nextInt(32) != 0) return;

        fwm.setPower(fwm.getPower() * ((int) new Random().nextInt(30) / 10));
        p.sendTitle("", Language.colorFromRGB(210, 200, 200) + Language.getMessage(
                Language.getLang(p), "elytra_boost_whoa"), 5, 20, 40);

        int i = 0;
        Sound.oneByOne(p, org.bukkit.Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 3,
                1.2f, 0.1f, true, 0.65f, 5, i);

    }
}
