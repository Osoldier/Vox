package me.soldier.vox.console;

import me.soldier.vox.core.*;
import me.soldier.vox.voxels.*;

/**
 * Created by Thomas on 24 janv. 2016
 */
public class GenerateCommand extends Command
{
	
	private Engine e;
	
	public GenerateCommand(Engine e)
	{
		this.e = e;
		this.setName("generate");
	}

	@Override
	public String getHelp()
	{
		return "Generates a new world, usage: generate [width] [height]";
	}

	@Override
	public String execute(String[] args)
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
			} catch (NumberFormatException e){
				//No code
			}
		}
		return getHelp();
	}

}
