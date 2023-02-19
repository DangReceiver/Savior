package de.tdf.cmd;

import de.tdf.obj.PC;
import de.tdf.language.Language;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import java.util.ArrayList;
import java.util.List;

public class Build implements CommandExecutor, Listener {

	@Override
	public boolean onCommand(CommandSender sen, Command cmd, String lab, String[] args) {

		if (!(sen instanceof Player p)) {
			sen.sendMessage(Language.getMessage(Language.getServerLang(), "no_player"));
			return false;
		}

		if (!p.hasPermission("Savior.Spawn")) {
			p.sendMessage(Language.getMessage(Language.getLang(p), "no_player"));
			return false;
		}

		PC pc = PC.loadConfig(p);

		if (p.getWorld() != Bukkit.getWorld("Spawn")) {
			p.sendMessage(Language.PRE + Language.getMessage(Language.getLang(p), "build_prohibited_world"));
			return false;
		}

		if (!pc.getBuild()) {
			pc.setBuild(true);

			p.setFlying(true);
			p.teleport(p.getLocation().add(0, 0.001, 0));
			p.setGameMode(p.getGameMode() == GameMode.ADVENTURE ? GameMode.SURVIVAL : GameMode.CREATIVE);

			// Build Saving may get customizable
			pc.setSaveBuild(false);

			List<Double> list = new ArrayList<>();
			list.add(p.getHealth());
			list.add((double) p.getSaturation());
			list.add((double) p.getExp());
			pc.setSaveBuildNumbers(list);

			pc.setSaveBuildInventory(p.getInventory());
			pc.savePCon();

		} else {
			disableBuild(p, pc);
		}

		return false;
	}

	public static void disableBuild(Player p, PC pc) {
		pc.setBuild(false);
		p.setFlying(false);

		if (pc.getForceGameModeBuild()) p.setGameMode(GameMode.CREATIVE);
		p.setGameMode(p.getGameMode() == GameMode.SURVIVAL ? GameMode.ADVENTURE : GameMode.SURVIVAL);

		// Build Saving may get customizable
		// pc.getSaveBuild()

		List<Double> list = pc.getSaveBuildNumbers();
		p.setHealth(list.get(0));

		float saturation = 1, exp = 1;
		try {
			saturation = Float.parseFloat(list.get(1) + "");
			exp = Float.parseFloat(list.get(2) + "");

		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		p.setSaturation(saturation);
		p.setExp(exp);

		p.getInventory().setContents(pc.getSaveBuildInventory().getContents());
		pc.savePCon();
	}


	@EventHandler
	public void handle(PlayerChangedWorldEvent e) {
		Player p = e.getPlayer();
		PC pc = PC.loadConfig(p);

		if (pc.getBuild() && p.getWorld() != Bukkit.getWorld("Spawn")) {
			pc.setBuild(false);
			pc.savePCon();

			p.setFlying(false);
			p.setGameMode(p.getGameMode() == GameMode.SURVIVAL ? GameMode.CREATIVE : GameMode.SURVIVAL);
		}

	}
}
