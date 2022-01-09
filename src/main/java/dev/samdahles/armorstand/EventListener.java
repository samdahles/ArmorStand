package dev.samdahles.armorstand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;

public class EventListener implements Listener {
	public EventListener() {};

	@EventHandler
	public void onEntitySpawn(EntitySpawnEvent event) {
		if(event.getEntity().getType() == EntityType.ARMOR_STAND) {
			ArmorStand armorStand = (ArmorStand) event.getEntity();
			armorStand.setArms(true);
			Pose.getDefault().get(0).applyTo(armorStand);
		}
	}
	
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if(event.getEntity() instanceof ArmorStand) {
			ArmorStand armorStand = (ArmorStand) event.getEntity();
			if(event.getDamager() instanceof Player) {
				Player player = (Player) event.getDamager();
				if(player.getInventory().getItemInMainHand().getType() == Material.AIR) {
					new Pose(armorStand).next().applyTo(armorStand);
					event.setCancelled(true);
				}
			}
		}

	}
}
