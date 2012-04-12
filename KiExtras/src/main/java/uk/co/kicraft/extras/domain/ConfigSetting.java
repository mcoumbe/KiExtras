package uk.co.kicraft.extras.domain;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigSetting {

	private String name;
	private FileConfiguration config;
	private File configFile;
	
	public ConfigSetting(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FileConfiguration getConfig() {
		return config;
	}

	public void setConfig(FileConfiguration config) {
		this.config = config;
	}

	public File getConfigFile() {
		return configFile;
	}

	public void setConfigFile(File configFile) {
		this.configFile = configFile;
	}
	
	
	
}
