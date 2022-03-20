package Main;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import General.GeneralMethods;
import Interfaces.CommandInterface;
import Managers.CommandManager;
import Managers.FileManager;
import SQL.Database;
import SQL.MysqlMain;

public class Main extends JavaPlugin{
	private static Main INSTANCE;
	
	private Database db;
	private CommandManager commandManager;
	private FileManager fileManager;
	private FileConfiguration config, language, profanity;
	
	public static Main getInstance() {
		return INSTANCE;
	}
	
	public void onEnable() {
		saveDefaultConfig();
		
		loadFiles();
		loadListeners();
	}
	
	private void loadListeners() {
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player)sender;
			
			//check all the base commands in this plugin
			for(CommandInterface command : commandManager.getCommands()) {
				if(!label.equalsIgnoreCase(command.getName())) continue;
				//check if base command has arguments
				if(command.getArguments(player.getUniqueId()) != null) {
					switch(args.length) {
						case 0:
						case 1:
							//run command if the player entered 1 argument
							command.perform(player, args);
							break;
						case 2:
						case 3:
							//check if command has subcommands
							if(command.getSubCommands() == null) command.perform(player, args);									
							else {
								for(CommandInterface subcommands : command.getSubCommands()) {
									for(int i = 0; i < args.length; i++) {
										//check if player entered the right arguments for specific subcommand
										if(!subcommands.getName().equalsIgnoreCase(args[i])) continue;
										if(subcommands.getPermissionNode() != null && !player.hasPermission(subcommands.getPermissionNode())) 
											player.sendMessage(GeneralMethods.format(language.getString("Command.Error.NoPermission.Message")));
										subcommands.perform(player, args);
									}
								}	
							}
							break;
						default:
							player.sendMessage(GeneralMethods.format(language.getString("Command.Error.NotExist.Message")));
							break;
					}
				}
				else {
					if(command.getPermissionNode() != null && !player.hasPermission(command.getPermissionNode())) continue;
					command.perform(player, args);
				}
				
			}
		}
		else {
			sender.sendMessage(GeneralMethods.format(language.getString("Command.Error.ByConsole.Message")));
		}
		return true;
	}
	
	public CommandManager getCommandManager() {
		return commandManager;
	}

	public FileManager getFileManager() {
		return fileManager;
	}

	public FileConfiguration getConfig() {
		return config;
	}

	public FileConfiguration getLanguage() {
		return language;
	}
	
	public FileConfiguration getProfanityConfig() {
		return profanity;
	}
	
	public Database getDatabase() {
		return db;
	}
	
	public void reload() {
    	List<File> fileList = new ArrayList<File>(Arrays.asList(
    			new File(this.getDataFolder(), "config.yml"),
    			new File(this.getDataFolder(), "language.yml"),
    			new File(this.getDataFolder(), "profanity.yml")
    			));
    	
    	for(int i = 0; i < fileList.size(); i++) {
        	fileManager.unloadConfig(fileList.get(i));
        	
    	}
    	
        config = this.getFileManager().getConfig(fileList.get(0)).getFileConfiguration();
        language = this.getFileManager().getConfig(fileList.get(1)).getFileConfiguration();
        profanity = this.getFileManager().getConfig(fileList.get(2)).getFileConfiguration();
    }

	private void loadFiles() {
		//load database
		this.db = new MysqlMain(this);
		this.db.load();
		//database
		
		fileManager = new FileManager(this);
        config = fileManager.getConfig(new File(this.getDataFolder(), "config.yml")).getFileConfiguration();
        language = fileManager.getConfig(new File(this.getDataFolder(), "language.yml")).getFileConfiguration();
        profanity = fileManager.getConfig(new File(this.getDataFolder(), "profanity.yml")).getFileConfiguration();
		
		commandManager = new CommandManager(this);
	}
}
