package Commands;

import java.util.List;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import General.GeneralMethods;
import Interfaces.ChildCommand;
import Interfaces.ParentCommand;
import Main.Main;
import Persistency.UserMapping;

/**
 * Class for handling the profanity command
 * 
 * @author ResurrectAjax
 * */
public class Profanity extends ChildCommand{
	
	private Main main;
	/**
	 * Constructor<br>
	 * @param main instance of the {@link Main.Main} class
	 * */
	public Profanity(Main main) {
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
		return "profanity";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/multichat profanity";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Toggles the profanity filter";
	}

	@Override
	public String[] getArguments(UUID uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ParentCommand> getSubCommands() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void perform(Player player, String[] args) {
		UserMapping userMapping = main.getUserMapping();
		FileConfiguration lang = main.getLanguage();
		
		switch(args.length) {
		case 1:
			String channelName = userMapping.getChannel(player.getUniqueId());
			boolean useFilter = userMapping.getFilter(player.getUniqueId());
			
			userMapping.setUser(player.getUniqueId(), channelName, !useFilter);
			
			if(useFilter) player.sendMessage(GeneralMethods.format(lang.getString("Command.Profanity.ToggleOff.Message")));
			else player.sendMessage(GeneralMethods.format(lang.getString("Command.Profanity.ToggleOn.Message")));
			
			break;
		default:
			player.sendMessage(GeneralMethods.getBadSyntaxMessage(getSyntax()));
			break;
		}
		
		
		
	}

}
