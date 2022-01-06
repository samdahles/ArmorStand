package dev.samdahles.armorstand;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.EulerAngle;

import java.util.Collection;
import java.util.logging.Logger;

public class Core extends JavaPlugin {
	public static Logger logger = Bukkit.getLogger();
	public static EventListener eventListener;
	@Override
	public void onEnable() {
		Core.logger.info("Setting up event handlers for ArmorStand.");
		Core.eventListener = new EventListener();
		Bukkit.getServer().getPluginManager().registerEvents(Core.eventListener, this);
		Core.logger.info("Done.");
		
		for(Pose pose : Pose.DEFAULT_POSES) {
			Collection<EulerAngle> angles = pose.toMap().values();
			String as = new String();
			for(EulerAngle angle : angles) {
				as += "[" + angle.getX() + " " + angle.getY() + " " + angle.getZ() + "] ";
			}
			Core.logger.info(as);
		}
		
		if(Pose.DEFAULT_POSES.get(0).toMap().values().equals(Pose.DEFAULT_POSES.get(1).toMap().values())) {
			Core.logger.warning("Values are the same :(");
		}
		
		
	}
	
}
