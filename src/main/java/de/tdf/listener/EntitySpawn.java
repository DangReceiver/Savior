package de.tdf.listener;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.Random;

public class EntitySpawn implements Listener {

    @EventHandler
    public void handle(EntitySpawnEvent e) {
        EntityType et = e.getEntityType();
        Damageable en = (Damageable) e.getEntity();

        en.setCustomNameVisible(true);
        en.setCustomName("§" + getHealthColor(en.getHealth()));

        Random r = new Random();
        int i = r.nextInt(10);

        if (i == 0 && et == EntityType.SKELETON) {
            en.getWorld().spawnEntity(en.getLocation(), EntityType.STRIDER);
            en.remove();

        } else if (i >= 8) {
            en.getWorld().spawnEntity(en.getLocation(), et);

            if (r.nextInt(10) == 0)
                en.getWorld().spawnEntity(en.getLocation(), et);
        }
    }

    public char getHealthColor(double d) {
        if (d <= 10)
            return 'a';

        else if (d <= 20)
            return '2';

        else if (d <= 30)
            return 'e';

        else if (d <= 40)
            return '6';

        else if (d <= 50)
            return 'c';

        else if (d <= 60)
            return '4';

        else return '6';
    }
}