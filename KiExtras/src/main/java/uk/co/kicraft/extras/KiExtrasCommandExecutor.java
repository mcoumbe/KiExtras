package uk.co.kicraft.extras;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.KickCommand;
import org.bukkit.plugin.PluginManager;

public class KiExtrasCommandExecutor implements CommandExecutor {

	private KiExtras kiExtras;

	public KiExtrasCommandExecutor(KiExtras kiExtras) {
		this.kiExtras = kiExtras;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		PluginManager pm = kiExtras.getPluginManager();

		if (cmd.getName().equalsIgnoreCase("kiextras"))
		{

			if (args.length==0) {
				return false;
			}

			if (args[0].equals("reload")) 
			{
				sender.sendMessage("Reloading KiExtras");
				pm.disablePlugin(kiExtras);
				pm.enablePlugin(kiExtras);
				sender.sendMessage("KiExtras Reloaded");
				return true;
			} 
			else if(args[0].equals("version"))
			{
				sender.sendMessage("[KiExtras] Version " + kiExtras.getDescription().getVersion());
				return true;
			}
			else if(args[0].equals("active"))
			{
				sender.sendMessage("KiExtras Enabled Plugins: " + kiExtras.getActivePlugins());
			}

			return false;
		}
		return false;
	}

}
