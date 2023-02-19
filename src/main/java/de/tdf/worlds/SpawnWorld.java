package de.tdf.worlds;

import de.tdf.obj.PC;
import de.tdf.savior.Savior;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.generator.ChunkGenerator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Random;

public class SpawnWorld implements Listener {

    public static class SpawnGen extends ChunkGenerator {

        @SuppressWarnings("deprecation")
        @Override
        public @NotNull ChunkGenerator.ChunkData generateChunkData(@NotNull World world, @NotNull Random random,
                                                                   int chunkX, int chunkZ, @NotNull
                                                                           ChunkGenerator.BiomeGrid biome) {

            ChunkGenerator.ChunkData chunkData = super.createChunkData(world);
            for (int x = 0; x < 24; x++)
                for (int z = 0; z < 24; z++)
                    biome.setBiome(x, z, Biome.PLAINS);

            return chunkData;
        }

        public static boolean checkExists() {
            World w = Bukkit.getWorld("Spawn");

            if (w == null) {
                WorldCreator creator = new WorldCreator("Spawn");
                creator.generator(new SpawnWorld.SpawnGen());
                creator.createWorld();

                setGameRules(Objects.requireNonNull(w = Bukkit.getWorld("Spawn")));
                return w != null;
            }
            return true;
        }

        @Nullable
        public static World getWorld() {
            return checkExists() ? Bukkit.getWorld("Spawn") : null;
        }

        public static void setGameRules(World w) {
            w.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
            w.setClearWeatherDuration(0);
            w.setWeatherDuration(Integer.MAX_VALUE);

            w.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            w.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
            w.setGameRule(GameRule.DO_MOB_SPAWNING, false);
            w.setGameRule(GameRule.RANDOM_TICK_SPEED, 0);
        }
    }

    @EventHandler
    public void handle(PlayerInteractEvent e) {

        Player p = e.getPlayer();
        Block cb = e.getClickedBlock();

        if (cb == null) return;
        if (cb.getWorld() != Bukkit.getWorld("Spawn")) return;

        if (!cb.getType().toString().contains("BUTTON")) return;
        if (!cb.equals(Savior.getSafeSpawnLocation().getBlock())) return;

        PC pc = PC.loadConfig(p);

        if (pc.hasLogoutLocation()) p.teleport(pc.getLogoutLocation());
        else p.teleport(new Location(Bukkit.getWorld("world"), 0, 64, 0));

        int i = 0;
        de.tdf.listener.methodPorting.Sound.oneByOne(p, Sound.ENTITY_PLAYER_BREATH, 4,
                1.2f, 0.2f, false, 0.4f, 40, i);

        p.setGameMode(GameMode.SURVIVAL);
    }

}
