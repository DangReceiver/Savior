package de.tdf.worlds;

import de.tdf.language.Language;
import de.tdf.obj.PC;
import de.tdf.savior.Savior;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
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

		@SuppressWarnings ("deprecation")
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

		if (! cb.getType().toString().contains("BUTTON")) return;
		if (! cb.equals(getSafeSpawnLocation().getBlock())) return;

		PC pc = PC.loadConfig(p);

		if (pc.hasLogoutLocation()) p.teleport(pc.getLogoutLocation());
		else p.teleport(new Location(Bukkit.getWorld("world"), 0, 64, 0));

		int i = 0;
		de.tdf.listener.methodPorting.Sound.oneByOne(p, Sound.ENTITY_PLAYER_BREATH, 4,
				1.2f, 0.2f, false, 0.4f, 40, i);

		p.setGameMode(GameMode.SURVIVAL);
	}

	public static Location getSafeSpawnLocation() {
		FileConfiguration c = Savior.getSavior().getConfig();
		Location spawnLoc = c.getLocation("Locations.Spawn");

		if (spawnLoc == null) {
			spawnLoc = new Location(Bukkit.getWorld("Spawn"), 0.5, 64.02, 0.5);

			c.set("Locations.Spawn", spawnLoc);
			Savior.getSavior().saveConfig();
		}

		isSpawnSafe();

		return spawnLoc;
	}

	public static boolean isSpawnSafe() {
		boolean b = true;
		if (! SpawnGen.checkExists()) b = false;

		Location c = getSafeSpawnLocation().clone();
		ConsoleCommandSender cs = Bukkit.getConsoleSender();

		if (c.add(0, - 1, 0).getBlock().getType() == Material.AIR) {
			b = false;

			c.add(0, - 1, 0).getBlock().setType(Material.BEDROCK);
			Bukkit.getScheduler().runTaskLater(Savior.getSavior(), () -> cs.sendMessage(
					Language.PRE + Language.getMessage(Language.getServerLang(),
							"spawn_repair_missing_block_fix")), 1);

		}

		if (! c.getWorld().getGameRuleValue(GameRule.FALL_DAMAGE)) {
			b = false;

			c.getWorld().setGameRule(GameRule.FALL_DAMAGE, false);
			Bukkit.getScheduler().runTaskLater(Savior.getSavior(), () -> cs.sendMessage(
					Language.PRE + Language.getMessage(Language.getServerLang(),
							"spawn_repair_fall_damage_fix")), 1);
		}

		if (! b)
			cs.sendMessage(Language.PRE + Language.getMessage(Language.getServerLang(),
					"spawn_repair_fixes"));

		return b;
	}
}
