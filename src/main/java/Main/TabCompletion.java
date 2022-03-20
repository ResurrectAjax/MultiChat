package Main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import Interfaces.CommandInterface;
import Managers.CommandManager;

public class TabCompletion implements TabCompleter{
	private CommandManager commandManager;
	public TabCompletion(Main main) {
		commandManager = main.getCommandManager();
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		
		List<String> tabCommands = new ArrayList<String>();
		if(sender instanceof Player) {
			UUID uuid = ((Player) sender).getUniqueId();

			if(commandManager.getStringList().contains(command.getName().toLowerCase())) {
				CommandInterface commands = commandManager.getCommandByName(command.getName());
				if(args.length > 0) {
					if(commands.getArguments(uuid) != null) {
						tabCommands.addAll(Arrays.asList(commands.getArguments(uuid)));	
					}
				}
			}
			
		}
		return tabCommands;
	}
}
