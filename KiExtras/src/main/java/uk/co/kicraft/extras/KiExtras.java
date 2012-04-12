package uk.co.kicraft.extras;

/*
 This file is part of KiExtras

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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import uk.co.kicraft.extras.boatsonwater.BoatsOnWater;
import uk.co.kicraft.extras.boatsonwater.BoatsOnWaterListener;
import uk.co.kicraft.extras.domain.ConfigSetting;
import uk.co.kicraft.extras.nofall.NoFall;
import uk.co.kicraft.extras.nofall.NoFallListener;
import uk.co.kicraft.extras.nohunger.NoHunger;
import uk.co.kicraft.extras.nohunger.NoHungerListener;

/*
 * KiExtras - Consists of various functionality that we use on our factions server
 * BoatsOnWater - Stops boats being placed on anything but static liquid
 * NoFall - Permissions based fall damage mitigation
 * NoHunger - Permissions based hunger disabler
 * Bounties
 */

public class KiExtras extends JavaPlugin {

	public Location playerLoc;
	
	// Class Executors
	private KiExtrasCommandExecutor kiextras;
	
	private boolean debug = false;
	
	private ConfigSetting bountiesConfigration = new ConfigSetting("bounties.yml");	
	
	private List<String> activePlugins = new ArrayList<String>();

    public static Economy economy = null;

	Logger log = Logger.getLogger("Minecraft");// Define your logger

	public void onDisable() {
		PluginManager pm = this.getServer().getPluginManager();
		log.info("Disabled message here, shown in console on startup");
		HandlerList.unregisterAll(this);
	}

	public void onEnable() {
		log.info("Enabling KiExtras Plugin");
		PluginManager pm = this.getServer().getPluginManager();
		if(!(new File(getDataFolder(), "config.yml").exists())) {
			getConfig().options().copyDefaults(true);
			saveConfig();
		}		
		this.debug = getConfig().getBoolean("debug");
		activePlugins = new ArrayList<String>();
		kiextras = new KiExtrasCommandExecutor(this);
		getCommand("kiextras").setExecutor(kiextras);

		if (getConfig().getConfigurationSection("boatsonwater").getBoolean("enabled")) {
			activePlugins.add(BoatsOnWater.NAME);
			pm.registerEvents(new BoatsOnWaterListener(this), this);
		}
		if (getConfig().getConfigurationSection("nofall").getBoolean("enabled")) {
			activePlugins.add(NoFall.NAME);
			pm.registerEvents(new NoFallListener(this), this);
		}
		if (getConfig().getConfigurationSection("nohunger").getBoolean("enabled")) {
			activePlugins.add(NoHunger.NAME);
			pm.registerEvents(new NoHungerListener(this), this);
		}

		log.info("KiExtras Enabled Plugins: " + activePlugins);
	}
	
	public void reloadCustomConfig(ConfigSetting cs)
	{
	    if (cs.getConfig() == null) {
	    cs.setConfigFile(new File(getDataFolder(), cs.getName()));
	    }
	    cs.setConfig(YamlConfiguration.loadConfiguration(cs.getConfigFile()));
	 
	    // Look for defaults in the jar
	    InputStream defConfigStream = getResource(cs.getName());
	    if (defConfigStream != null) {
	        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	        cs.getConfig().setDefaults(defConfig);
	    }
	}

	public FileConfiguration getCustomConfig(ConfigSetting cs)
	{
	    if (cs.getConfig() == null) {
	        reloadCustomConfig(cs);
	    }
	    return cs.getConfig();
	}

	public void saveCustomConfig(ConfigSetting cs)
	{
	    if (cs.getConfig() == null || cs.getConfigFile() == null) {
	    return;
	    }
	    try {
	    	cs.getConfig().save(cs.getConfigFile());
	    } catch (IOException ex) {
	        Logger.getLogger(JavaPlugin.class.getName()).log(Level.SEVERE, "Could not save config to " + cs.getConfigFile(), ex);
	    }
	}	
	

    private boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }
    
    public Economy getEconomy() {
    	return economy;
	}
	
	public void debug(String msg) {
		if(this.debug) log.info(msg);
	}
	
	public PluginManager getPluginManager() {
		return this.getServer().getPluginManager();
	}

	public List<String> getActivePlugins() {
		return activePlugins;
	}

	public ConfigSetting getBountiesConfigration() {
		return bountiesConfigration;
	}

	public final static String LOG_NAME = ChatColor.GOLD + "[KiExtras] " + ChatColor.WHITE;
	
}
