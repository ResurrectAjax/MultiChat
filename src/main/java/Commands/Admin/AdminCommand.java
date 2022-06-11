package Commands.Admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Help.HelpCommand;
import Interfaces.ParentCommand;
import Main.Main;

/**
 * Class for handling the admin command
 * 
 * @author ResurrectAjax
 * */
public class AdminCommand extends ParentCommand{
	private List<ParentCommand> subcommands;
	
	/**
	 * Constructor<br>
	 * Loads all the subcommands
	 * @param main instance of the {@link Main.Main} class
	 * */
	public AdminCommand(Main main) {
		subcommands = new ArrayList<ParentCommand>(Arrays.asList(
				new Reload(main),
				new CreateChannel(main),
				new SetChannelRadius(main),
				new SetChannelWorld(main),
				new SetDefaultChannel(main),
				new HelpCommand(main)
				));
	}
	
	@Override
	public String getPermissionNode() {
		// TODO Auto-generated method stub
		return "multichat.admin";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "admin";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/multichat admin <admincommand>";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Run an admin command";
	}

	@Override
	public List<ParentCommand> getSubCommands() {
		// TODO Auto-generated method stub
		return subcommands;
	}

}
