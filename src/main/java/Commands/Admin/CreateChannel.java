package Commands.Admin;

import java.util.List;
import java.util.regex.Pattern;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import Commands.Channel;
import General.GeneralMethods;
import Interfaces.ParentCommand;
import Main.Main;
import Managers.FileManager;

public class CreateChannel extends Channel{

	public CreateChannel(Main main) {
		super(main);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void perform(Player player, String[] args) {
		FileConfiguration config = main.getConfig(), lang = main.getLanguage();
		if(args.length != 3) {
			player.sendMessage(GeneralMethods.getBadSyntaxMessage(getSyntax()));
			return;
		}
		if(!Pattern.matches("[a-zA-Z]+", args[2])) {
			String message = GeneralMethods.format(lang.getString("Command.Error.SpecialChars.Message"));
			player.sendMessage(message);
			return;
		}
		if(checkChannel(player, args[2])) {
			String message = GeneralMethods.format(lang.getString("Command.Channel.AlreadyExists.Message"));
			player.sendMessage(message);
			return;
		}
		
		config.set("MultiChat.Channels." + args[2] + ".worlds", "ALL");
		config.set("MultiChat.Channels." + args[2] + ".radius", -1);
		
		FileManager fileManager = main.getFileManager();
		fileManager.saveFile("config.yml");
		
		String message = GeneralMethods.format(lang.getString("Command.Admin.CreateChannel.Message"), "%Channel%", args[2].toLowerCase());
		player.sendMessage(message);
	}

	@Override
	public String getPermissionNode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "create";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/multichat admin create <channel>";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Create a new channel";
	}

	@Override
	public List<ParentCommand> getSubCommands() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
