package Commands.Admin;

import java.util.List;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import General.GeneralMethods;
import Interfaces.ChildCommand;
import Interfaces.ParentCommand;
import Main.Main;

/**
 * Class for handling the reload command
 * 
 * @author ResurrectAjax
 * */
public class Reload extends ChildCommand{
	private Main main;
	
	/**
	 * Constructor<br>
	 * @param main instance of the {@link Main.Main} class
	 * */
	public Reload(Main main) {
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
		return "reload";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/multichat admin reload";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "reloads the config files";
	}

	@Override
	public String[] getArguments(UUID uuid) {
		return null;
	}

	@Override
	public List<ParentCommand> getSubCommands() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void perform(Player player, String[] args) {
		FileConfiguration lang = main.getLanguage();
		
		main.reload();
		player.sendMessage(GeneralMethods.format(lang.getString("Command.Reload.Message")));
		
	}
	
}
