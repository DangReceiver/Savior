package de.tdf.de.tdf.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Join implements Listener {

    @EventHandler
    public void handle(PlayerJoinEvent e) {

        e.setJoinMessage("Hallo, dies ist ein Test");

    }
}
