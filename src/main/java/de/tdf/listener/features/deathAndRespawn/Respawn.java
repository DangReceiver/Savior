package de.tdf.listener.features.deathAndRespawn;

import de.tdf.obj.PC;
import de.tdf.language.Language;
import de.tdf.savior.Savior;
import de.tdf.worlds.SpawnWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.io.File;

public class Respawn implements Listener {

    @EventHandler
    public void handle(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        e.setRespawnLocation(SpawnWorld.getSafeSpawnLocation());

        File l = Language.getLang(p);
        p.sendTitle(Language.colorFromRGB(220, 10, 30) + Language.getMessage(l, "you_died_title"), Language.getMessage(l,
                "you_died_subtitle"), 20, 60, 40);

        PC pcd = PC.loadConfig(p);
        if (!pcd.hasDeathLocation()) {
            p.sendMessage(Language.PRE + Language.getMessage(Language.getLang(p), "no_death_location"));
            return;
        }

        if (pcd.getRespawnRequired()) pcd.setRespawnRequired(false);

        pcd.setDeathLocation(null);
        pcd.setDead(false);
        pcd.savePCon();
    }
}
