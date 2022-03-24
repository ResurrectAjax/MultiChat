package Persistency;

import java.util.HashMap;
import java.util.UUID;

import SQL.Database;

/**
 * Class for loading data from the {@link Database}
 * 
 * @author ResurrectAjax
 * */
public class UserMapping {
	private Database db;
	private HashMap<UUID, String> userPreferences;
	private HashMap<UUID, Boolean> filter;
	
	/**
	 * Constructor<br>
	 * Loads all the data from the {@link Database} into {@link HashMap}
	 * @param db instance of the {@link SQL.Database} class
	 * */
	public UserMapping(Database db) {
		this.db = db;
		
		loadAllUserChannels();
		loadAllFilters();
	}
	
	/**
	 * Get all users' filter preferences
	 * @return {@link HashMap} of all the users' filter preferences
	 * */
	public HashMap<UUID, Boolean> getAllFilters() {
		return filter;
	}
	
	/**
	 * Get the filter preference of a specific user
	 * @param uuid {@link UUID} of the user
	 * @return true/false
	 * */
	public boolean getFilter(UUID uuid) {
		return getAllFilters().get(uuid);
	}
	
	/**
	 * Load all the users' filter preferences from the {@link Database}
	 * */
	private void loadAllFilters() {
		this.filter = db.getAllFilters();
	}

	/**
	 * Load all the users' current channels from the {@link Database}
	 * */
	private void loadAllUserChannels() {
		this.userPreferences = db.getAllUserChannels();
	}
	
	/**
	 * Get all the users' current channels
	 * @return {@link HashMap} of all the users and their current channels
	 * */
	public HashMap<UUID, String> getAllUserChannels() {
		return this.userPreferences;
	}
	
	/**
	 * Get the user's current channel
	 * @param uuid {@link UUID} of the user
	 * @return channel
	 * */
	public String getChannel(UUID uuid) {
		return getAllUserChannels().get(uuid);
	}
	
	/**
	 * Set the user's channel and filter preference
	 * @param uuid {@link UUID} of user
	 * @param channel {@link String} channel
	 * @param profanityFilter user's filter preference
	 * */
	public void setUser(UUID uuid, String channel, boolean profanityFilter) {
		if(getChannel(uuid) == null) db.setUser(uuid, channel, profanityFilter);	
		else db.updateUser(uuid, channel, profanityFilter);
		
		getAllUserChannels().put(uuid, channel);
		getAllFilters().put(uuid, profanityFilter);
	}
	
	
	
	
}
