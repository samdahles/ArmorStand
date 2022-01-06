package dev.samdahles.armorstand;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.EulerAngle;

public class EventListener implements Listener {
	public EventListener() {};

	@EventHandler
	public void onEntitySpawn(EntitySpawnEvent event) {
		if(event.getEntity().getType() == EntityType.ARMOR_STAND) {
			ArmorStand armorStand = (ArmorStand) event.getEntity();
			armorStand.setArms(true);
			Pose.DEFAULT_POSES.get(0).applyTo(armorStand);
		}
	}
	
	@EventHandler
    public void onEntityClick(PlayerInteractEntityEvent event) {

        Player player = event.getPlayer();
    	player.sendMessage("Clicked 1");
        if (event.getRightClicked() instanceof ArmorStand) {
        	player.sendMessage("Clicked 2");
        	ArmorStand armorStand = (ArmorStand) event.getRightClicked();
        	Pose.nextPose(new Pose(armorStand)).applyTo(armorStand);
        }
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		// Check if player is looking at armorstand
	}
	
	
}
