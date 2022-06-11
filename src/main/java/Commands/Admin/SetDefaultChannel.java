package Commands.Admin;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import Commands.Channel;
import General.GeneralMethods;
import Interfaces.ParentCommand;
import Main.Main;
import Managers.ConfigManager;
import Managers.FileManager;

/**
 * Class for handling the setdefault command
 * 
 * @author ResurrectAjax
 * */
public class SetDefaultChannel extends Channel{

	/**
	 * Constructor<br>
	 * @param main instance of the {@link Main.Main} class
	 * */
	public SetDefaultChannel(Main main) {
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
		return "setdefault";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/multichat admin setdefault <channel>";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Set the default channel";
	}

	@Override
	public List<ParentCommand> getSubCommands() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void perform(Player player, String[] args) {
		if(args.length != 3) {
			player.sendMessage(GeneralMethods.getBadSyntaxMessage(getSyntax()));
			return;
		}
		if(!checkChannel(player, args[2])) {
			FileConfiguration lang = main.getLanguage();
			player.sendMessage(GeneralMethods.format(lang.getString("Command.Channel.NotExist.Message"), "%Channel%", args[2].toLowerCase()));
			return;
		}
		
		FileConfiguration config = main.getConfig(), lang = main.getLanguage();
		if(ConfigManager.getDefaultChannel() != null) {
			config.set("MultiChat.Channels." + ConfigManager.getDefaultChannel() + ".default", false);
		}
		
		config.set("MultiChat.Channels." + args[2] + ".default", true);

		FileManager fileManager = main.getFileManager();
		fileManager.saveFile("config.yml");
		
		String message = GeneralMethods.format(lang.getString("Command.Admin.SetDefaultChannel.Message"), "%Channel%", args[2].toLowerCase());
		player.sendMessage(message);
		
	}

}
