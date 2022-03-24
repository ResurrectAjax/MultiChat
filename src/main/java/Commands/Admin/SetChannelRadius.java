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
 * Class for handling the setradius command
 * 
 * @author ResurrectAjax
 * */
public class SetChannelRadius extends Channel{
	
	/**
	 * Constructor<br>
	 * @param main instance of the {@link Main.Main} class
	 * */
	public SetChannelRadius(Main main) {
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
		return "setradius";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/multichat admin setradius <channel> <radius>";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Set the radius of a channel";
	}

	@Override
	public void perform(Player player, String[] args) {
		if(args.length != 4) {
			player.sendMessage(GeneralMethods.getBadSyntaxMessage(getSyntax()));
			return;
		}
		if(!checkChannel(player, args[2])) return;
		
		if(!GeneralMethods.isInteger(args[3]) || Integer.parseInt(args[3]) < -1) {
			player.sendMessage(GeneralMethods.getBadSyntaxMessage(getSyntax()));
			return;
		}
		
		FileConfiguration config = main.getConfig(), lang = main.getLanguage();
		config.set("MultiChat.Channels." + args[2] + ".radius", Integer.parseInt(args[3]));
		
		FileManager fileManager = main.getFileManager();
		fileManager.saveFile("config.yml");
		
		String message = GeneralMethods.format(lang.getString("Command.Admin.SetChannelRadius.Message"), "%Channel%", args[2].toLowerCase());
		player.sendMessage(GeneralMethods.format(message, "%Radius%", args[3]));
	}

	@Override
	public List<ParentCommand> getSubCommands() {
		// TODO Auto-generated method stub
		return null;
	}


}
