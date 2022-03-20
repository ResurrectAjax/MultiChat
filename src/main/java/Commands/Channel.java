package Commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import Interfaces.CommandInterface;
import Main.Main;

public class Channel extends CommandInterface{
	
	private ArrayList<CommandInterface> subcommands = new ArrayList<CommandInterface>(Arrays.asList(
			
			));
	
	private Main main;
	public Channel(Main main) {
		this.main = main;
	}
	
	@Override
	public String getPermissionNode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "channel";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/multichat channel <channel>";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Switch to a different channel";
	}

	@Override
	public String[] getArguments(UUID uuid) {
		FileConfiguration config = main.getConfig();
		
		ConfigurationSection section = config.getConfigurationSection("MultiChat.Channels");
		String[] args = new String[section.getKeys(false).size() + getSubCommands().size()];
		
		int countChannels = 0;
		for(String channel : section.getKeys(false)) {
			args[countChannels] = channel;
			countChannels++;
		}
		for(int i = section.getKeys(false).size(); i < args.length; i++) {
			args[i] = getSubCommands().get(i).getName();
		}
		return args;
	}

	@Override
	public List<CommandInterface> getSubCommands() {
		// TODO Auto-generated method stub
		return subcommands;
	}

	@Override
	public void perform(Player player, String[] args) {
		// TODO Auto-generated method stub
		
	}

}
