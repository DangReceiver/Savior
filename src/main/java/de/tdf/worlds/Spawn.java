package de.tdf.worlds;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Random;

public class Spawn extends ChunkGenerator {

	@SuppressWarnings("deprecation")
	@Override
	public @NotNull ChunkGenerator.ChunkData generateChunkData(@NotNull World world, @NotNull Random random, int chunkX, int chunkZ,
															   @NotNull ChunkGenerator.BiomeGrid biome) {

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
			creator.generator(new Spawn());
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
