package de.tdf.cmd;

import de.tdf.language.Language;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.io.File;

public class TpExact implements CommandExecutor {

	@Override
	public boolean onCommand(final CommandSender sen, final Command cmd, final String lab, final String[] args) {
		if (!(sen instanceof Player p)) {
			sen.sendMessage(Language.PRE + Language.getMessage(Language.getServerLang(), "no_player"));
			return true;
		}

		File l = Language.getLang(p);
		if (!p.hasPermission("Savior.TpExact")) {
			p.sendMessage(Language.PRE + String.format(Language.getMessage(l, "insufficient_permissions"), "Savior.TpExact"));
			return true;
		}

		if (args.length > 1) {
			p.sendMessage(Language.PRE + String.format(Language.getMessage(l, "usage"), cmd + " [<boolean: yaw/pitch correction>]"));
			return true;
		}

		Location loc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());

		if(args.length == 0) loc.add(0.5, 0.0, 0.5);

		loc.setPitch((float) Math.round(loc.getPitch()));
		loc.setYaw((float) Math.round(loc.getYaw()));

		p.teleport(loc);
		p.sendActionBar(Component.text(Language.PRE + Language.getMessage(l, "tp_exact_successful")));
		p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 0.5f, 0.5f);
		return false;
	}
}
