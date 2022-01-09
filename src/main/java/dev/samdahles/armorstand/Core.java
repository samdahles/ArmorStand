package dev.samdahles.armorstand;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.EulerAngle;

import java.util.Collection;
import java.util.logging.Logger;

public class Core extends JavaPlugin {
	public static Logger logger = Bukkit.getLogger();
	public static EventListener eventListener;
	@Override
	public void onEnable() {
		Core.eventListener = new EventListener();
		Bukkit.getServer().getPluginManager().registerEvents(Core.eventListener, this);
	}
}
