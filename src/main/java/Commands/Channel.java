package Commands;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import General.GeneralMethods;
import Interfaces.ChildCommand;
import Interfaces.ParentCommand;
import Main.Main;
import Managers.ConfigManager;
import Persistency.UserMapping;

/**
 * Class for handling the channel command
 * 
 * @author ResurrectAjax
 * */
public class Channel extends ChildCommand{
	
	protected Main main;
	
	/**
	 * Constructor<br>
	 * @param main instance of the {@link Main.Main} class
	 * */
	public Channel(Main main) {
		this.main = main;
	}
	
	public String getPermissionNode() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return "channel";
	}

	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/multichat channel <channel>";
	}

	public String getDescription() {
		// TODO Auto-generated method stub
		return "Switch to a different channel";
	}

	public String[] getArguments(UUID uuid) {
		String[] args = new String[ConfigManager.getChannelNames().size()];
		
		int countChannels = 0;
		for(String channel : ConfigManager.getChannelNames()) {
			args[countChannels] = channel.toLowerCase();
			countChannels++;
		}
		return args;
	}

	public List<ParentCommand> getSubCommands() {
		// TODO Auto-generated method stub
		return null;
	}

	public void perform(Player player, String[] args) {
		FileConfiguration lang = main.getLanguage();
		if(args.length != 2) {
			player.sendMessage(GeneralMethods.getBadSyntaxMessage(getSyntax()));
			return;
		}
		if(!checkChannel(player, args[1].toLowerCase())) return;
		
		UserMapping userMapping = main.getUserMapping();
		boolean profanityFilter = userMapping.getFilter(player.getUniqueId());
		
		userMapping.setUser(player.getUniqueId(), args[1].toLowerCase(), profanityFilter);
		player.sendMessage(GeneralMethods.format(lang.getString("Command.Channel.ChangedChannel.Message"), "%Channel%", args[1].toLowerCase()));
		
	}
	
	/**
	 * Check if the given channel name exists
	 * @param player {@link Player} who sends command
	 * @param channel {@link String} to check
	 * */
	protected boolean checkChannel(Player player, String channel) {
		FileConfiguration lang = main.getLanguage();
		if(!Arrays.asList(getArguments(player.getUniqueId())).contains(channel.toLowerCase())) {
			player.sendMessage(GeneralMethods.format(lang.getString("Command.Channel.NotExist.Message"), "%Channel%", channel.toLowerCase()));
			return false;
		}
		return true;
	}

}
