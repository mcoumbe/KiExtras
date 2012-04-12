package uk.co.kicraft.extras.boatsonwater;

import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleCreateEvent;

import uk.co.kicraft.extras.KiExtras;

public class BoatsOnWaterListener implements Listener {

	private KiExtras plugin;
	private Logger log = Logger.getLogger("Minecraft");
	
	public BoatsOnWaterListener(KiExtras plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onVehicleCreate(VehicleCreateEvent event)
	{
		
		Vehicle vehicle = event.getVehicle();
		Block block = vehicle.getLocation().getBlock();
		
		Block placement = new Location(block.getWorld(), block.getX(), block.getY()-1, block.getZ()).getBlock();
		
		log.info("Location Block  = " + block);
		
		if(vehicle instanceof Boat) {
			Boat boat = (Boat) vehicle;			
			if(!placement.isLiquid()) {
				boat.remove();
				log.info("Block not allowed");
			} else {
				log.info("Block allowed");
			}
		}
		
	}
	
}
