package de.tdf.listener;

import de.tdf.language.Language;
import de.tdf.savior.Savior;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class StartToSprint implements Listener {

	@EventHandler
	public void handle(PlayerToggleSprintEvent e) {
		Player p = e.getPlayer();
		if (p.isSprinting()) return;

		if (new Random().nextInt(16) == 0) p.addPotionEffect(
				new PotionEffect(PotionEffectType.SPEED, 50, 2, false, false, false));

	}
}
