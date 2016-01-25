package me.soldier.vox.console.commands;

import me.soldier.vox.console.*;
import me.soldier.vox.core.*;
import me.soldier.vox.voxels.*;

/**
 * Created by Thomas on 24 janv. 2016
 */
public class GenerateCommand extends Command
{
	
	
	public GenerateCommand()
	{
		this.setName("generate");
	}

	@Override
	public String getHelp()
	{
		return "Generates a new world, usage: generate [width] [height]";
	}

	@Override
	public String execute(String[] args, final Engine e)
	{
		if(args != null && args.length >= 2) {
			final int width, height;
			try
			{
				width = Integer.parseInt(args[0]);
				height = Integer.parseInt(args[1]);
				e.getTodoList().add(new Runnable()
				{
					public void run()
					{
						e.setWorld(new World(width, height));
					}
				});
				return width+" x "+height+" world generated !";
			} catch (NumberFormatException e1){
				//No code
			}
		}
		return getHelp();
	}

}
