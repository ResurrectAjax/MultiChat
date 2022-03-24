package Managers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import General.GeneralMethods;
import Main.Main;

/**
 * Class for retrieving data from the config.yml
 * 
 * @author ResurrectAjax
 * */
public class ConfigManager {
	
	/**
	 * Get the default channel
	 * @return channel
	 * */
	public static String getDefaultChannel() {
		FileConfiguration config = Main.getInstance().getConfig();
		ConfigurationSection channelSection = config.getConfigurationSection("MultiChat.Channels");
		
		String defaultChannel = null;
		for(String channel : channelSection.getKeys(false)) {
			Boolean isDefault = channelSection.getBoolean(channel + ".default");
			
			if(isDefault == null || isDefault == false) continue;
			defaultChannel = channel;
			
		}
		if(defaultChannel == null) {
			try {
				throw new IOException("The default channel has not been set. -> config.yml");
			}
			catch(IOException ex) {
				ex.printStackTrace();
			}
		}
		return defaultChannel;
		
	}
	
	/**
	 * Get all the channel's worlds
	 * @param player {@link Player} who sent the command
	 * @param channel channel to check
	 * @return {@link List} of all the channel's worlds
	 * */
	public static List<World> getChannelWorlds(Player player, String channel) {
		FileConfiguration config = Main.getInstance().getConfig();
		ConfigurationSection channelSection = config.getConfigurationSection("MultiChat.Channels");
		
		List<World> worldList = new ArrayList<World>();
		String world = channelSection.getString(channel + ".worlds");
		switch(world.toLowerCase()) {
		case "all":
			worldList.addAll(Bukkit.getWorlds());
			break;
		case "own":
			worldList.add(player.getWorld());
			break;
		default:
			try {
				throw new IOException("Please use the correct syntax: ALL/OWN; instead of " + world + " -> config.yml");
			}
			catch(IOException ex) {
				ex.printStackTrace();
			}
		}
		return worldList;
	}
	
	/**
	 * Get the radius of a channel
	 * @param channel channel to check
	 * @return int radius
	 * */
	public static int getChannelRadius(String channel) {
		FileConfiguration config = Main.getInstance().getConfig();
		ConfigurationSection channelSection = config.getConfigurationSection("MultiChat.Channels");
		
		String radiusStr = channelSection.getString(channel + ".radius");
		
		try {
			if(!GeneralMethods.isInteger(radiusStr)) throw new IOException("Please enter a numeric value instead of: " + radiusStr + " -> config.yml");
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
		
		return channelSection.getInt(channel + ".radius");
	}
	
	/**
	 * Get the names of all the channels
	 * @return {@link List} of channels
	 * */
	public static List<String> getChannelNames() {
		List<String> channels = new ArrayList<String>();
		
		FileConfiguration config = Main.getInstance().getConfig();
		ConfigurationSection channelSection = config.getConfigurationSection("MultiChat.Channels");
		for(String channel : channelSection.getKeys(false)) {
			channels.add(channel.toLowerCase());
		}
		
		return channels;
	}
}
