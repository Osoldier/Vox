package me.soldier.vox.console;

/**
 * Created by Thomas on 24 janv. 2016
 */
public abstract class Command
{
	private String name = "help";
	public abstract String getHelp();
	public abstract String execute(String[] args);
	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
}
