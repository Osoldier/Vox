package me.soldier.vox.console.commands;

import me.soldier.vox.console.Command;
import me.soldier.vox.core.Engine;

/**
 * Created by Thomas on 29 janv. 2016
 */
public class WireframeCommand extends Command
{

	public WireframeCommand()
	{
		this.setName("wireframe");
	}
	
	@Override
	public String getHelp()
	{
		return "toggle wireframe";
	}

	@Override
	public String execute(String[] args, Engine e)
	{
		e.setWireframe(!e.isWireframe());
		return "Wireframe toggled";
	}
}
