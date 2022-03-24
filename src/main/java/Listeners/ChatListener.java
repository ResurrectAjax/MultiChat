package Listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import Main.Main;
import Managers.ConfigManager;
import Persistency.UserMapping;

/**
 * Listener for handling chat messages
 * 
 * @author ResurrectAjax
 * */
public class ChatListener implements Listener{
	private Main main;
	
	/**
	 * Constructor<br>
	 * @param main instance of the {@link Main.Main} class
	 * */
	public ChatListener(Main main) {
		this.main = main;
	}
	
	/**
	 * Code run when {@link AsyncPlayerChatEvent} is called
	 * @param event {@link AsyncPlayerChatEvent}
	 * */
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		
		event.getRecipients().removeAll(Bukkit.getOnlinePlayers());
		
		sendMessage(player, event);
	}
	
	/**
	 * Method run when player sends a message
	 * @param player {@link Player} who sent the message
	 * @param event {@link AsyncPlayerChatEvent}
	 * */
	private void sendMessage(Player player, AsyncPlayerChatEvent event) {
		
		UserMapping userMapping = main.getUserMapping();
		String channelName = userMapping.getChannel(player.getUniqueId());
		
		int channelRadius = ConfigManager.getChannelRadius(channelName);
		for(World world : ConfigManager.getChannelWorlds(player, channelName)) {
			for(Player recipient : world.getPlayers()) {
				if(channelRadius != -1 && playerInRadiusOfPlayer(recipient, player, channelRadius)) {
					event.getRecipients().add(recipient);
				}
				else if(channelRadius == -1){
					event.getRecipients().add(recipient);	
				}
				filterWords(recipient, event);
				receiveMessage(recipient, event);
			}
		}
	}
	
	/**
	 * Method run when player receives a message
	 * @param player {@link Player} who sent the message
	 * @param event {@link AsyncPlayerChatEvent}
	 * */
	private void receiveMessage(Player receiver, AsyncPlayerChatEvent event) {
		Player sender = event.getPlayer();
		
		UserMapping userMapping = main.getUserMapping();
		String channelName = userMapping.getChannel(receiver.getUniqueId());
		int channelRadius = ConfigManager.getChannelRadius(channelName);
		if(ConfigManager.getChannelWorlds(receiver, channelName).contains(sender.getWorld())) {
			if(channelRadius != -1 && !playerInRadiusOfPlayer(sender, receiver, channelRadius)) event.getRecipients().remove(receiver);
		}
		else event.getRecipients().remove(receiver);
	}
	
	/**
	 * Check if receiver is in radius of player
	 * @param receiver {@link Player} who receives the message
	 * @param player {@link Player} who sent the message
	 * @param radius int radius in which receiver can receive the message
	 * */
	private boolean playerInRadiusOfPlayer(Player receiver, Player player, int radius) {
		if(receiver.getLocation().distance(player.getLocation()) <= radius) return true;
		return false;
	}
	
	/**
	 * Filter all bad words
	 * @param recipient {@link Player} who receives the message
	 * @param event {@link AsyncPlayerChatEvent}
	 * */
	private void filterWords(Player recipient, AsyncPlayerChatEvent event) {
		UserMapping userMapping = main.getUserMapping();
		
		if(userMapping.getFilter(recipient.getUniqueId())) {
			filterFullWord(recipient, event);
			filterWordContaining(recipient, event);
		}
	}
	
	/**
	 * Filter all bad full words (words that are profanity if nothing is added)
	 * @param recipient {@link Player} who receives the message
	 * @param event {@link AsyncPlayerChatEvent}
	 * */
	private void filterFullWord(Player recipient, AsyncPlayerChatEvent event) {
		String message = event.getMessage();
		
		FileConfiguration profanity = main.getProfanityConfig();
		List<String> wholeWords = profanity.getStringList("Banned-Words.Full-Words");
		
		List<String> words = new ArrayList<String>();
		for(String word : wholeWords) {
			words.add(word.toLowerCase());
		}
		
		String[] splitMessage = message.split("/(?=[\\s@&.?$+-]+)|(?<=[\\\\s@&.?$+-]+)g/");
		
		for(String word : splitMessage) {
			if(!words.contains(word.toLowerCase())) continue;
			if(event.getRecipients().contains(recipient)) event.getRecipients().remove(recipient);
		}
	}
	
	/**
	 * Filter all words containing a bad word (words that are profanity no matter what letters are added)
	 * @param recipient {@link Player} who receives the message
	 * @param event {@link AsyncPlayerChatEvent}
	 * */
	private void filterWordContaining(Player recipient, AsyncPlayerChatEvent event) {
		String message = event.getMessage();
		
		FileConfiguration profanity = main.getProfanityConfig();
		List<String> wholeWords = profanity.getStringList("Banned-Words.Words-Containing");
		
		List<String> words = new ArrayList<String>();
		for(String word : wholeWords) {
			words.add(word.toLowerCase());
		}
		
		String[] splitMessage = message.split("/(?=[\\s@&.?$+-]+)|(?<=[\\\\s@&.?$+-]+)g/");
		
		for(String msg : splitMessage) {
			for(String badWord : words) {
				if(!msg.toLowerCase().contains(badWord.toLowerCase())) continue;
				if(event.getRecipients().contains(recipient)) event.getRecipients().remove(recipient);
			}
		}
	}
}
