package me.soldier.vox.console.commands;

import me.soldier.vox.console.*;
import me.soldier.vox.core.*;
import me.soldier.vox.voxels.*;

/**
 * Created by Thomas on 25 janv. 2016
 */
public class GiveCommand extends Command
{

	public GiveCommand()
	{
		this.setName("give");
	}
	
	@Override
	public String getHelp()
	{
		return "Set the current block type, usage: give [block type]";
	}

	@Override
	public String execute(String[] args, Engine e)
	{
		if(args != null && args.length >= 1) {
			try {
				VoxelType type = VoxelType.valueOf(args[0].toUpperCase());
				e.setInHand(type);
				return type.toString()+ " given !";
			} catch(IllegalArgumentException e1) {
				return "This voxel type doesn't exists";
			}
		}
		return getHelp();
	}

}
