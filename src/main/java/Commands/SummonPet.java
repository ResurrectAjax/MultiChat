package Commands;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import Interfaces.CommandInterface;
import Main.Main;

public class SummonPet extends CommandInterface{
	
	public SummonPet(Main main) {
		
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "multichat";
	}

	@Override
	public String getSyntax() {
		
		// TODO Auto-generated method stub
		return "summonpet <type>";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "summons a custom mob";
	}

	@Override
	public String[] getArguments(UUID uuid) {
		List<String> argList = new ArrayList<String>();
		for(EntityType type : EntityType.values()) {
			if(type.isAlive()) {
				argList.add(type.name());
			}
		}
		return argList.toArray(new String[argList.size()]);
	}

	@Override
	public List<CommandInterface> getSubCommands() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void perform(Player player, String[] args) {
		
		switch(args.length) {
		case 1:
			
			break;
		default:
			player.sendMessage(getSyntax());
			break;
		}
		
	}

	@Override
	public String getPermissionNode() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
