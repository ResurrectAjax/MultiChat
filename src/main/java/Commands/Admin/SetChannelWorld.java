package Commands.Admin;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import Commands.Channel;
import General.GeneralMethods;
import Interfaces.ParentCommand;
import Main.Main;
import Managers.FileManager;

/**
 * Class for handling the setworld command
 * 
 * @author ResurrectAjax
 * */
public class SetChannelWorld extends Channel{

	/**
	 * Constructor<br>
	 * @param main instance of the {@link Main.Main} class
	 * */
	public SetChannelWorld(Main main) {
		super(main);
	}
	
	@Override
	public String getPermissionNode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "setworld";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/multichat admin setworld <channel> <ALL | OWN>";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Set the world of a channel to all worlds or the player's own world";
	}

	@Override
	public List<ParentCommand> getSubCommands() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void perform(Player player, String[] args) {
		if(args.length != 4) {
			player.sendMessage(GeneralMethods.getBadSyntaxMessage(getSyntax()));
			return;
		}
		if(!checkChannel(player, args[2])) return;
		
		if(!args[3].equalsIgnoreCase("ALL") && !args[3].equalsIgnoreCase("OWN")) {
			player.sendMessage(GeneralMethods.getBadSyntaxMessage(getSyntax()));
			return;
		}
		
		FileConfiguration config = main.getConfig(), lang = main.getLanguage();
		config.set("MultiChat.Channels." + args[2] + ".worlds", args[3].toUpperCase());
		
		FileManager fileManager = main.getFileManager();
		fileManager.saveFile("config.yml");
		
		String message = GeneralMethods.format(lang.getString("Command.Admin.SetChannelWorld.Message"), "%Channel%", args[2]);
		player.sendMessage(GeneralMethods.format(message, "%World%", args[3].toUpperCase()));
		
	}

}
