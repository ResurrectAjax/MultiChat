package Managers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Commands.MultiChatCommand;
import Interfaces.ParentCommand;
import Main.Main;
/**
 * Manages all the base commands
 * @author ResurrectAjax
 * */
public class CommandManager {
	private List<ParentCommand> commands = new ArrayList<ParentCommand>();
	
	/**
	 * Constructor of {@link CommandManager}<br>
	 * Loads all the base commands
	 * @param main instance of the {@link Main.Main} class
	 * */
	public CommandManager(Main main) {
		commands = new ArrayList<ParentCommand>(Arrays.asList(
				new MultiChatCommand(main)
				));
	}
	
	/**
	 * Gets a {@link List} of all the base commands
	 * @return {@link List} of all the base commands
	 * */
	public List<ParentCommand> getCommands() {
		return commands;
	}
	
	/**
	 * Gets a {@link List} of all the base command names
	 * @return {@link List} of all the command names
	 * */
	public List<String> getStringList() {
		List<String> commandStrings = new ArrayList<String>();
		for(ParentCommand command : commands) {
			commandStrings.add(command.getName().toLowerCase());
		}
		return commandStrings;
	}
	
	/**
	 * Gets the base command by name
	 * @param name name of the command
	 * @return instance of {@link ParentCommand}
	 * */
	public ParentCommand getCommandByName(String name) {
		for(ParentCommand command : commands) {
			if(getStringList().contains(name.toLowerCase())) {
				if(command.getName().equalsIgnoreCase(name)) {
					return command;
				}	
			}
			else {
				if(command.getSubCommands() != null) {
					for(ParentCommand subcommands : command.getSubCommands()) {
						if(subcommands.getName().equalsIgnoreCase(name)) {
							return subcommands;
						}
					}	
				}
			}
		}	
		
		return null;
	}
}
