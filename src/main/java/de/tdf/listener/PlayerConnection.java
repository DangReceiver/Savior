package de.tdf.listener;

import de.tdf.language.Language;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Random;

public class PlayerConnection implements Listener {

    @EventHandler
    public void handle(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        Language.setLang(p, Language.getLangFile("en"));

        e.setJoinMessage(null);
        Language.broadcastArg("player_join_" + new Random().nextInt(4), p.getName());
    }

    @EventHandler
    public void handle(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        e.setQuitMessage(null);

        Language.broadcastArg("player_quit_" + new Random().nextInt(4), p.getName());
        Language.removePlayer(p);
    }
}
