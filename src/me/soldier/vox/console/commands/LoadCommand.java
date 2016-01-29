package me.soldier.vox.console.commands;

import java.io.File;

import me.soldier.vox.console.Command;
import me.soldier.vox.core.Engine;
import me.soldier.vox.filemanager.FileSaver;

/**
 * Created by Thomas on 29 janv. 2016
 */
public class LoadCommand extends Command {

	public LoadCommand()
	{
		this.setName("load");
	}
	
	@Override
	public String getHelp()
	{
		return "Loads a file, usage: load [filename]";
	}

	@Override
	public String execute(final String[] args, final Engine e)
	{
		if (args.length >= 1)
		{
			e.getTodoList().add(new Runnable()
			{	
				public void run()
				{
					e.Load(FileSaver.FILEPATH+File.separator+args[0]+".ves");
				}
			});
			return "File " + FileSaver.FILEPATH + args[0] + ".ves loaded !";
		}
		return getHelp();
	}

}
