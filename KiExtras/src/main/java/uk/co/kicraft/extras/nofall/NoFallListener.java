package uk.co.kicraft.extras.nofall;

import java.util.logging.Logger;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import uk.co.kicraft.extras.KiExtras;

public class NoFallListener implements Listener {

	private Logger log;
	private KiExtras plugin;

	public NoFallListener(KiExtras plugin) {
		this.plugin = plugin;
		log = Logger.getAnonymousLogger();
	}

	public boolean checkPermissions(Player p) {
		return p.hasPermission("kiextras.nofall.nodamage");
	}

	@EventHandler
	public void onPlayerDamage(EntityDamageEvent event) {
				
		// Get the entity that the event happened to
		Entity ee = event.getEntity();
		
		// Check that the entity is first a player & secondly has permissions
		if (ee instanceof Player && checkPermissions((Player) ee)) {
			Player p = (Player) ee;
			// Get the type of damage
			DamageCause type = event.getCause();
			// Check if the damage was caused by falling
			if (type == DamageCause.FALL) {
				log.info(p.getName() + " avoided fall damage");
				// Cancels the event - e.g. negates the damage event
				event.setCancelled(true);
				return;
			}		
		}
	}
}