package de.tdf.listener;

import de.tdf.language.Language;
import de.tdf.savior.Savior;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;

public class EffectHeadBottle implements Listener {

	@EventHandler
	public void handle(EntityPotionEffectEvent e) {
		Entity en = e.getEntity();
		if (!(en instanceof Player p)) return;

		if (e.getAction() == EntityPotionEffectEvent.Action.ADDED) {
			ItemStack i = new ItemStack(Material.POTION);
			PotionMeta pm = (PotionMeta) i.getItemMeta();

			pm.addEnchant(Enchantment.VANISHING_CURSE, 10, true);
			pm.addEnchant(Enchantment.BINDING_CURSE, 10, true);
			pm.addItemFlags(ItemFlag.HIDE_ENCHANTS);

			pm.displayName(Component.text(Language.colorFromRGB(20, 160, 255) +
					String.format(Language.getMessage(Language.getLang(p), "active_potion_effect"), e.getNewEffect())));
			pm.setColor(Color.fromRGB(200, 200, 200));

			ItemStack h = p.getInventory().getHelmet();
			p.getInventory().setHelmet(i);
			i.setItemMeta(pm);

			Bukkit.getScheduler().runTaskLater(Savior.getSavior(), () -> {
				if (p.getInventory().getHelmet() != h || p.getInventory().getHelmet() == i)
					p.getInventory().setHelmet(h);
			}, 50);
		}
	}
}
