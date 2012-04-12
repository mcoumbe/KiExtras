package uk.co.kicraft.extras.nohunger;

/*
    This file is part of NoHunger

    Foobar is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Foobar is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import uk.co.kicraft.extras.KiExtras;

public class NoHungerListener implements Listener {

    private KiExtras plugin;
    Logger log = Logger.getLogger("Minecraft");//Define your logger

    public NoHungerListener(KiExtras plugin)
    {
        this.plugin = plugin;
    }
    
	public boolean checkPermissions(Player p) {
		return p.hasPermission("kiextras.nohunger.disablehunger");
	}
    
    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event)
    {
    	Player p = (Player) event.getEntity();
    	
    	if(checkPermissions(p))
    	{
    		p.setFoodLevel(20);
    		event.setCancelled(true);
    	}
    	
    	
    }


}
