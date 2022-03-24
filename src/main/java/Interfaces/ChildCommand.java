package Interfaces;

import org.bukkit.entity.Player;

/**
 * Abstract class for commands that don't have any specific code
 * This is the frame for all the child command classes
 * @author ResurrectAjax
 * */
public abstract class ChildCommand extends ParentCommand{
	@Override
	public abstract void perform(Player player, String[] args);
}
