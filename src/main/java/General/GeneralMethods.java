package General;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Vector;

import Main.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

/**
 * Class of general methods that can be used everywhere
 * 
 * @author ResurrectAjax
 * */
public class GeneralMethods {
	
	/**
	 * turn text into hover text
	 * @param string base text to put hover text on
	 * @param hover the hover text
	 * @param command command that will be activated on click... <i>Nullable</i>
	 * @param color the color of the base text and hover text
	 * @return String message
	 * */
	public TextComponent createHoverText(String string, String hover, String command, ChatColor color) {
		TextComponent message = new TextComponent(string);
		
		message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(color + hover)));
		message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
		
		message.setBold(true);
		message.setColor(color);
		
		
		return message;
	}
		
	/**
	 * check if a {@link String} is a valid date
	 * @param date {@link String} to check
	 * @return {@link Boolean} isValid
	 * */
	public boolean isValidDate(String date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(ChatColor.stripColor(date).trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
	}
	
	/**
	 * check if {@link String} is an Integer
	 * @param input {@link String} to check
	 * @return {@link Boolean} isInteger
	 * */
	public static boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
		}
		catch(Exception ex) {
			return false;
		}
		return true;
	}
	
	/**
	 * gets all numeric values from {@link String}
	 * @param input {@link String} to retreive values from
	 * @return array of {@link Integer} values
	 * */
	public static Integer[] getIntFromString(String input) {
		String nStr = ChatColor.stripColor(input);
		String[] splitStr = nStr.split("[\\D]");
		Integer[] splitNumbers = new Integer[splitStr.length];
		for(int i = 0; i < splitNumbers.length; i++) {
			splitNumbers[i] = Integer.parseInt(splitStr[i]);
		}
		return splitNumbers;
	}
	
	/**
	 * replaces the color coded text with a colored text
	 * @param msg {@link String} to format
	 * @return colored text
	 * */
	public static String format(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
	
	/**
	 * replaces text with special strings (%Player%, %Date%) with correct values
	 * @param input {@link String} to convert
	 * @param special special charactered {@link String}
	 * @param value {@link String} to replace value with
	 * @return formatted {@link String}
	 * */
 	public static String format(String input, String special, String value) {
 		String newStr = input;
		if(input.contains(special)) {
			newStr = input.replaceAll(special, value);
		}
 		return ChatColor.translateAlternateColorCodes('&', newStr);
	}
 	
 	/**
 	 * {@link List} for formatted {@link String}s
 	 * */
 	public static List<String> FORMATS = new ArrayList<String>(Arrays.asList(
 			"%Player%",
			"%Date%",
			"%TimeLeft%",
			"%ID%"
 			));
	/**
	 * replaces text with special strings (%Player%, %Date%) with correct values
	 * @param input {@link String} to convert
	 * @param value {@link String} to replace value with
	 * @return formatted {@link String}
	 * */
 	public static String format(String input, String value) {
 		String newStr = input;
 		for(String format : FORMATS) {
 			if(input.contains(format)) {
 				newStr = input.replaceAll(format, value);
 			}	
 		}
 		return ChatColor.translateAlternateColorCodes('&', newStr);
	}
 	
 	/**
 	 * Get the bad syntax message of syntax
 	 * @param syntax {@link String} syntax
 	 * @return {@link String} formatted syntax message
 	 * */
 	public static String getBadSyntaxMessage(String syntax) {
 		return GeneralMethods.format(Main.getInstance().getLanguage().getString("Command.Error.BadSyntax.Message"), "%Syntax%", syntax);
 	}
 	
 	/**
 	 * checks if a location is within the bounds of 2 other locations
 	 * @param loc {@link Location} to check
 	 * @param bound1 first bound
 	 * @param bound2 second bound
 	 * @return {@link Boolean} isInBounds
 	 * */
 	public boolean isInBounds(Location loc, Location bound1, Location bound2) {
		
		double posXmax = 0, posZmax = 0, posXmin = 0, posZmin = 0;
		if(bound1.getBlockX() > bound2.getBlockX()) {
			posXmax = bound1.getBlockX();
			posXmin = bound2.getBlockX();
		}
		else {
			posXmax = bound2.getBlockX();
			posXmin = bound1.getBlockX();
		}
		if(bound1.getBlockZ() > bound2.getBlockZ()) {
			posZmax = bound1.getBlockZ();
			posZmin = bound2.getBlockZ();
		}
		else {
			posZmax = bound2.getBlockZ();
			posZmin = bound1.getBlockZ();
		}
		
		Vector vector = new org.bukkit.util.Vector(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()),
				vector1 = new org.bukkit.util.Vector(posXmin, 0, posZmin),
				vector2 = new org.bukkit.util.Vector(posXmax, 255, posZmax);
		
		if(vector.isInAABB(vector1, vector2)) {
       		return true;
		}
		
    	return false;
 	}
 	
 	/**
 	 * gets a list of all the {@link PotionEffect}s specified in a configuration section
 	 * @param section {@link ConfigurationSection} where {@link PotionEffect}s are listed
 	 * @return {@link List} of {@link PotionEffect}
 	 * */
 	public List<PotionEffect> getPotionEffects(ConfigurationSection section) {
		List<PotionEffect> potionEffects = new ArrayList<PotionEffect>();
		for(String effect : section.getKeys(false)) {
			int amplifier = section.getInt(effect + ".Amplifier"),
				duration = convertHoursMinutesSecondsToSeconds(section.getString(effect + ".Duration"))*20;
			
			PotionType type = PotionType.valueOf(effect);
			potionEffects.add(new PotionEffect(type.getEffectType(), duration, amplifier-1));
		}
		return potionEffects;
	}
 	
 	/**
 	 * converts a {@link String} in format(hh:mm:ss) to seconds
 	 * @param input {@link String} entered in format to convert
 	 * @return int total in seconds
 	 * */
 	public static int convertHoursMinutesSecondsToSeconds(String input) {
		HashMap<String, Integer> hourminsec = new HashMap<String, Integer>();
		String[] numbers = input.split("\\D");
		String[] letters = input.replaceAll("\\d", "").split("");
		for(int i = 0; i < letters.length; i++) {
			hourminsec.put(letters[i], Integer.parseInt(numbers[i]));
		}
		
		Integer hours = hourminsec.get("h"), 
				minutes = hourminsec.get("m"), 
				seconds = hourminsec.get("s");
		
		if(hours == null && minutes == null && seconds == null) {
			throw new IllegalArgumentException("Please use the right time formats(h|m|s): config.yml");
		}
		else {
			if(hours == null) {
				hours = 0;
			}
			if(minutes == null) {
				minutes = 0;
			}
			if(seconds == null) {
				seconds = 0;
			}	
		}

		int totalseconds = (hours*3600) + (minutes*60) + seconds;
		return totalseconds;
	}
 	
 	/**
 	 * gets the head of a {@link Player} as an ItemStack
 	 * @param uuid {@link UUID} of the {@link Player}
 	 * @param displayName {@link String} that will be the {@link ItemStack}s display name
 	 * @param lore lore {@link List}
 	 * @return {@link ItemStack} playerhead
 	 * */
 	public static ItemStack getPlayerHead(UUID uuid, String displayName, List<String> lore) {
		boolean isNewVersion = Arrays.stream(Material.values())
				.map(Material::name).collect(Collectors.toList()).contains("PLAYER_HEAD");
		
		Material type = Material.matchMaterial(isNewVersion ? "PLAYER_HEAD" : "SKULL_ITEM");
		ItemStack item = new ItemStack(type, 1);
		
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setOwningPlayer(Bukkit.getOfflinePlayer(uuid));
		
		meta.setDisplayName(format(displayName));
		
		meta.setLore(lore);
		
		item.setItemMeta(meta);
		
		return item;
	}
	
 	/**
 	 * gets the head of a player as an {@link ItemStack}, can only be used for page monitoring in gui's
 	 * @param name the name of the head you want('MHF_ArrowLeft' and 'MHF_ArrowRight')
 	 * @param page page number as an int
 	 * @return {@link ItemStack} playerhead
 	 * */
	public static ItemStack getPlayerHead(String name, int page) {
		boolean isNewVersion = Arrays.stream(Material.values())
				.map(Material::name).collect(Collectors.toList()).contains("PLAYER_HEAD");
		
		Material type = Material.matchMaterial(isNewVersion ? "PLAYER_HEAD" : "SKULL_ITEM");
		ItemStack item = new ItemStack(type, 1);
		
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setOwningPlayer(Bukkit.getOfflinePlayer(name));
		
		List<String> lore = new ArrayList<String>();
		switch(name) {
			case "MHF_ArrowLeft":
				meta.setDisplayName(format("&6&lBack"));
				lore.add(format("&7Go to the previous page"));
				break;
			case "MHF_ArrowRight":
				meta.setDisplayName(format("&6&lNext"));
				lore.add(format("&7Go to the next page"));
				break;
		}
		lore.add(format("&7" + page));
		meta.setLore(lore);
		
		item.setItemMeta(meta);
		
		return item;
	}
}
