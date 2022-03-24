package Commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Commands.Admin.AdminCommand;
import Interfaces.ParentCommand;
import Main.Main;

/**
 * Class for handling the multichat command
 * 
 * @author ResurrectAjax
 * */
public class MultiChatCommand extends ParentCommand{
	private List<ParentCommand> subcommands;
	
	/**
	 * Constructor<br>
	 * @param main instance of the {@link Main.Main} class
	 * */
	public MultiChatCommand(Main main) {
		subcommands = new ArrayList<ParentCommand>(Arrays.asList(
				new Channel(main),
				new Profanity(main),
				new AdminCommand(main)
				));
	}
	
	@Override
	public String getPermissionNode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "multichat";
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "/multichat <subcommand>";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Runs the multichat command";
	}

	@Override
	public List<ParentCommand> getSubCommands() {
		// TODO Auto-generated method stub
		return subcommands;
	}

}
