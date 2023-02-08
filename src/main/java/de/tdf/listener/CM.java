package de.tdf.listener;

import de.tdf.PC;
import de.tdf.language.Language;
import de.tdf.listener.methods.BossBar;
import de.tdf.savior.Savior;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class CM implements Listener {

    @EventHandler
    public void handle(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        PC pc = PC.loadConfig(p);

        if (pc.getLanguageString() == null) {
            pc.setLanguage(Language.getServerLang());
            pc.savePCon();
        }

        pc.setJoinTime(System.currentTimeMillis());
        pc.savePCon();

        File lf = pc.getLanguage();
        Language.setLang(p, lf);

        e.setJoinMessage(null);
        if (p.hasPlayedBefore()) Language.broadcastArg("player_join_"
                + new Random().nextInt(17), p.getName());

        p.setGameMode(GameMode.ADVENTURE);
        p.teleport(Savior.getSafeSpawnLocation());

        setupLoginLocation(p, pc);
        p.sendTitle(Language.PRE, String.format(Language.getMessage(lf, "welcome_back"), p.getName()), 30, 50, 50);
        p.sendMessage(Language.PRE + Language.getMessage(lf, "join_teleport_spawn"));

        int i = 0;
        de.tdf.listener.methods.Sound.oneByOne(p, Sound.BLOCK_NOTE_BLOCK_CHIME, 4,
                0.225f, 0.225f, true, 0.55f, 3, i);
    }

    public void setupLoginLocation(Player p, PC pc) {
        File lf = pc.getLanguage();
        BossBar.timeToBroadcastBar(p, Language.colorFromRGB(180, 150, 20) +
                Language.getMessage(Language.getLang(p), "spawn_location_bossbar"));

        Bukkit.getScheduler().runTaskLaterAsynchronously(Savior.getSavior(), () -> {
            p.sendMessage(Language.PRE + Language.getMessage(lf, "login_location_broadcast_warning"));

            Bukkit.getScheduler().runTaskLaterAsynchronously(Savior.getSavior(), () -> {
                if (!(pc.hasLogoutLocation() && p.isOnline())) return;

                Location lol = pc.getLogoutLocation();
                Language.broadcastArg("login_location_broadcast", p.getName(),
                        lol.getBlockX() + "", lol.getBlockY() + "", lol.getBlockZ() + "");

                int i = 0;
                de.tdf.listener.methods.Sound.oneByOne(p, Sound.BLOCK_NOTE_BLOCK_FLUTE, 5,
                        0.4f, 0.3f, true, 0.55f, 10, i);
            }, 20 * 20);
        }, 6 * 20);
    }

    @EventHandler
    public void handle(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        e.setQuitMessage(null);

        Location l = p.getLocation();
        PC pc = PC.loadConfig(p);

        pc.setQuitTime(System.currentTimeMillis());
        pc.updatePlayTime();

        if (pc.isDead()) {
            p.damage(p.getHealth() * p.getHealth());

            @Nullable ItemStack[] contents = p.getInventory().getContents().clone();
            ArrayList<ItemStack> con = new ArrayList<>();
            int c = 0;

            for (ItemStack i : contents)
                if (i != null) con.add(i);

            PreDeath.nobelItemDrop(p.getLocation().clone(), con, c);
            p.getInventory().clear();

            Language.broadcastArg("player_unsuitable_quit", p.getName());
            Language.broadcastArg("death_location_broadcast", l.getWorld().getName(),
                    l.getBlockX() + "", l.getBlockY() + "", l.getBlockZ() + "");
        }

        if (!p.getWorld().getName().equalsIgnoreCase("Spawn")) {
            pc.setLogoutLocation(l);
            pc.savePCon();
        }

        Language.broadcastArg("player_quit_" + new Random().nextInt(17), p.getName());
        Language.removePlayer(p);
    }
}
