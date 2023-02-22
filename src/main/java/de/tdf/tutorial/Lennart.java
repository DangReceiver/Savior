package de.tdf.tutorial;

import de.tdf.language.Language;
import de.tdf.language.ReadOut;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public class Lennart {

    private Villager lennart;
    private Location l;
    private Player p;

    public Lennart(Location loc, @Nullable Villager v) {
        l = loc;
        lennart = v == null ? (Villager) l.getWorld()
                .spawnEntity(l, EntityType.VILLAGER) : v;
    }

    public void setUp(Player player, @Nullable Location loc, @Nullable Villager pLennart) {
        p = player;

        if (lennart != pLennart && pLennart != null) lennart.remove();
        if (l != loc && loc != null) lennart.teleport(loc);

        l = (loc == null ? l : loc);
        lennart = (pLennart == null ? lennart : pLennart);

        p.sendMessage(Language.PRE + Language.getMessage(Language.getLang(p), "tutorial_lennart_spawn_success"));
    }

    public Villager getLennart() {
        return lennart;
    }

    public void setPlayer(Player player) {
        p = player;
    }

    public void setLocation(Location loc) {
        l = loc;
    }

    public void setLennart(Villager pLennart) {
        if (lennart != null) removeLennart();
        lennart = pLennart;
    }

    public void removeLennart() {
        lennart.remove();
    }

    public void standard() {
        lennart.setProfession(Villager.Profession.FARMER);
        lennart.setTarget(p);
        lennart.setAdult();
        lennart.setCollidable(false);
        lennart.setGravity(false);
        lennart.setCustomNameVisible(true);
        lennart.setCustomName(Language.colorFromRGB(20, 240, 40) + "§oLennart");
        lennart.setInvulnerable(true);
        lennart.setSilent(true);
        lennart.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,
                Integer.MAX_VALUE, 3, false, false, false));
    }

    public void spawn(@Nullable Location loc) {
        if (l == null) setLocation(loc);
        if (l == null) throw new RuntimeException(Language.getMessage(
                Language.getServerLang(), "tutorial_lennart_spawn_no_location"));

        if (lennart != null) removeLennart();
        lennart = (Villager) l.getWorld().spawnEntity(l, EntityType.VILLAGER);
        standard();
    }
}
