package Interfaces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import General.GeneralMethods;

/**
 * Abstract class for commands that don't have any specific code
 * This is the frame for all the parent command classes
 * @author ResurrectAjax
 * */
public abstract class ParentCommand {
	/**
	 * Get the permission node of the command
	 * @return permission node
	 * */
	public abstract String getPermissionNode();
	
	/**
	 * Get the name of the command
	 * @return name
	 * */
	public abstract String getName();
	
	/**
	 * Get the syntax of the command should be used
	 * @return syntax
	 * */
	public abstract String getSyntax();
	
	/**
	 * Get the description of the command
	 * @return description
	 * */
	public abstract String getDescription();
	
	/**
	 * Get the arguments of the command
	 * @param uuid {@link UUID} of the player who sent the command
	 * @return {@link String}[] args
	 * */
	public String[] getArguments(UUID uuid) {
		List<String> arguments = new ArrayList<String>();
		
		Player player = Bukkit.getPlayer(uuid);
		for(int i = 0; i < getSubCommands().size(); i++) {
			String permission = getSubCommands().get(i).getPermissionNode();
			if(permission != null && !player.hasPermission(permission)) continue;
			arguments.add(getSubCommands().get(i).getName());
		}
		return arguments.toArray(new String[arguments.size()]);	
	}
	
	
	/**
	 * Get all the subcommands of the command
	 * @return {@link List} of ParentCommand classes
	 * */
	public abstract List<ParentCommand> getSubCommands();
	
	/**
	 * Standard method for executing code
	 * @param player {@link Player} who sent the command
	 * @param args array of arguments the player sent
	 * */
	public void perform(Player player, String[] args) {
		switch(args.length) {
		case 1:
			if(!Arrays.asList(getArguments(player.getUniqueId())).contains(args[0].toLowerCase())) player.sendMessage(GeneralMethods.getBadSyntaxMessage(getSyntax()));
			break;
		default:
			player.sendMessage(GeneralMethods.getBadSyntaxMessage(getSyntax()));
			break;
		}
	}
}
