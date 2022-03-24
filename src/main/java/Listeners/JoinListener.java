package Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import Main.Main;
import Managers.ConfigManager;
import Persistency.UserMapping;

/**
 * Listener for handling joining players
 * 
 * @author ResurrectAjax
 * */
public class JoinListener implements Listener{

	private Main main;
	/**
	 * Constructor<br>
	 * @param main instance of the {@link Main.Main} class
	 * */
	public JoinListener(Main main) {
		this.main = main;
	}
	
	/**
	 * Code run when {@link PlayerJoinEvent} is called
	 * @param event {@link PlayerJoinEvent}
	 * */
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		UserMapping userMapping = main.getUserMapping();
		
		Player player = event.getPlayer();
		
		String userChannel = userMapping.getChannel(player.getUniqueId());
		if(userChannel != null && ConfigManager.getChannelNames().contains(userChannel)) return;
		userMapping.setUser(player.getUniqueId(), ConfigManager.getDefaultChannel().toLowerCase(), true);
	}
}
