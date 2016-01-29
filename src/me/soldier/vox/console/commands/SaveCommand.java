package me.soldier.vox.console.commands;

import me.soldier.vox.console.Command;
import me.soldier.vox.core.Engine;
import me.soldier.vox.filemanager.FileSaver;

/**
 * Created by Thomas on 29 janv. 2016
 */
public class SaveCommand extends Command
{

	public SaveCommand()
	{
		this.setName("save");
	}

	@Override
	public String getHelp()
	{
		return "Saves the current world on a file, usage: save [filename]";
	}

	@Override
	public String execute(String[] args, Engine e)
	{
		if (args.length >= 1)
		{
			e.Save(FileSaver.FILEPATH, args[0]);
			return "File saved to " + FileSaver.FILEPATH + args[0] + ".ves";
		}
		return getHelp();
	}

}
