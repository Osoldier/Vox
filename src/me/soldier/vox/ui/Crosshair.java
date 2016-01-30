package me.soldier.vox.ui;

import me.soldier.graphics.OGLUtil;
import me.soldier.graphics.Texture;
import me.soldier.math.Vector2f;
import me.soldier.vox.core.Main;

/**
 * Created by Thomas on 30 janv. 2016
 */
public class Crosshair extends UIElement
{	
	public Crosshair()
	{
		setMesh(OGLUtil.createUIVAO());
		this.setTexture(new Texture("res/textures/aim.png", 0));
		this.setSize(16);
		this.setPosition(new Vector2f(Main.width/2-this.getSize()/2, Main.height/2-this.getSize()/2)); 
	}
}
