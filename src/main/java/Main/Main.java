package Main;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.Yaml;

import General.GeneralMethods;
import Interfaces.ParentCommand;
import Listeners.ChatListener;
import Listeners.JoinListener;
import Managers.CommandManager;
import Managers.ConfigManager;
import Managers.FileManager;
import Persistency.UserMapping;
import SQL.Database;
import SQL.MysqlMain;

/**
 * Main class
 * 
 * @author ResurrectAjax
 * */
public class Main extends JavaPlugin{
	private static Main INSTANCE;
	
	private Database db;
	private CommandManager commandManager;
	private FileManager fileManager;
	private FileConfiguration config, language, profanity;
	
	private UserMapping userMapping;
	
	/**
	 * Static method to get the {@link Main} instance
	 * @return {@link Main} instance
	 * */
	public static Main getInstance() {
		return INSTANCE;
	}
	
	/**
	 * Enable plugin and load files/commands
	 * */
	public void onEnable() {
		
		loadFiles();
		loadListeners();
		
		
		TabCompletion tabCompleter = new TabCompletion(this);
		//set the tabCompleter
		for(ParentCommand command : commandManager.getCommands()) {
			getCommand(command.getName()).setTabCompleter(tabCompleter);
		}
	}
	
	/**
	 * Load all the classes that implement {@link Listener}
	 * */
	private void loadListeners() {
		getServer().getPluginManager().registerEvents(new JoinListener(this), this);
		getServer().getPluginManager().registerEvents(new ChatListener(this), this);
	}
	
	/**
	 * Handle command execution
	 * @param sender {@link CommandSender} who sent the command
	 * @param cmd {@link Command} sent command
	 * @param label {@link String} label of the command
	 * @param args {@link String}[] arguments
	 * */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player)sender;
			
			//check all the base commands in this plugin
			if(commandManager.getCommandByName(cmd.getName()) != null) {
				ParentCommand command = commandManager.getCommandByName(cmd.getName());
				runCommand(command, player, args);
			}
		}
		else {
			sender.sendMessage(GeneralMethods.format(language.getString("Command.Error.ByConsole.Message")));
		}
		return true;
	}
	
	/**
	 * Iterate over all commands and subcommands to find the right command to execute
	 * @param command {@link ParentCommand} where the method runs from
	 * @param player {@link Player} who sent the command
	 * @param args {@link String}[] arguments given with the command
	 * */
	private void runCommand(ParentCommand command, Player player, String[] args) {
		for(String arg : args) {
			String permissionNode = command.getPermissionNode();
			String noPermission = GeneralMethods.format(language.getString("Command.Error.NoPermission.Message"));
			
			if(permissionNode != null && !player.hasPermission(permissionNode)) {
				player.sendMessage(noPermission);
				return;
			}
			if(command.getSubCommands() == null || command.getSubCommands().isEmpty()) {
				command.perform(player, args);
				return;
			}
			
			for(ParentCommand subcommand : command.getSubCommands()) {
				if(subcommand.getName().equalsIgnoreCase(arg)) {
					runCommand(subcommand, player, args);
					return;
				}
			}
		}
		command.perform(player, args);
	}
	
	/**
	 * Get the command manager
	 * @return {@link CommandManager} manager
	 * */
	public CommandManager getCommandManager() {
		return commandManager;
	}

	/**
	 * Get the file manager
	 * @return {@link FileManager} manager
	 * */
	public FileManager getFileManager() {
		return fileManager;
	}

	/**
	 * Get the config file
	 * @return {@link FileConfiguration} config
	 * */
	public FileConfiguration getConfig() {
		return config;
	}

	/**
	 * Get the language file
	 * @return {@link FileConfiguration} language
	 * */
	public FileConfiguration getLanguage() {
		return language;
	}
	
	/**
	 * Get the profanity file
	 * @return {@link FileConfiguration} profanity
	 * */
	public FileConfiguration getProfanityConfig() {
		return profanity;
	}
	
	/**
	 * Get the database
	 * @return {@link Database} database
	 * */
	public Database getDatabase() {
		return db;
	}

	/**
	 * Get the user mapping
	 * @return {@link UserMapping} mapping
	 * */
	public UserMapping getUserMapping() {
		return userMapping;
	}
	
	/**
	 * Reload the {@link Yaml} files
	 * */
	public void reload() {
        fileManager.loadFiles();
        config = fileManager.getConfig("config.yml");
        language = fileManager.getConfig("language.yml");
        profanity = fileManager.getConfig("profanity.yml");
        
        for(Player player : Bukkit.getOnlinePlayers()) {
        	String userChannel = userMapping.getChannel(player.getUniqueId());
    		if(userChannel != null && ConfigManager.getChannelNames().contains(userChannel)) continue;
			userMapping.setUser(player.getUniqueId(), ConfigManager.getDefaultChannel().toLowerCase(), true);
        }
    }

	/**
	 * Load the {@link Yaml} files and classes
	 * */
	private void loadFiles() {
		INSTANCE = this;
		
		//load files
		fileManager = new FileManager(this);
        fileManager.loadFiles();
        config = fileManager.getConfig("config.yml");
        language = fileManager.getConfig("language.yml");
        profanity = fileManager.getConfig("profanity.yml");
        //files
        
		//load database
		this.db = new MysqlMain(this);
		this.db.load();
		//database
		
		userMapping = new UserMapping(this.db);
		
		//load classes
		commandManager = new CommandManager(this);
		//classes
	}
}
