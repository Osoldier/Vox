package me.soldier.vox.console;

import java.util.*;

import me.soldier.vox.console.commands.*;
import me.soldier.vox.core.*;

/**
 * Created by Thomas on 24 janv. 2016
 */
public class CommandHandler
{
	private List<Command> commands;
	private Engine engine;

	public CommandHandler(Engine e)
	{
		commands = new ArrayList<Command>();
		commands.add(new GenerateCommand());
		commands.add(new GiveCommand());
		this.engine = e;
	}

	public String OnCommandIssued(String line)
	{
		String[] parts = line.split(" ");
		String[] args = line.indexOf(" ") > 0 ? line.substring(line.indexOf(" ")+1, line.length()).split(" ") : null;
		String cmd = parts.length > 0 ? parts[0] : line;
		for (Command c : commands)
		{
			if (c.getName().equalsIgnoreCase(cmd))
			{
				return c.execute(args, engine);
			}
		}
		return "Command not found";
	}

}
