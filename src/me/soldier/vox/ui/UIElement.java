package me.soldier.vox.ui;

import me.soldier.graphics.Texture;
import me.soldier.math.Vector2f;

/**
 * Created by Thomas on 30 janv. 2016
 */
public class UIElement
{
	private int mesh;
	private Texture texture;
	private int size;
	private Vector2f position;

	public int getMesh()
	{
		return mesh;
	}

	public void setMesh(int mesh)
	{
		this.mesh = mesh;
	}

	public Texture getTexture()
	{
		return texture;
	}

	public void setTexture(Texture texture)
	{
		this.texture = texture;
	}

	public int getSize()
	{
		return size;
	}

	public void setSize(int size)
	{
		this.size = size;
	}

	public Vector2f getPosition()
	{
		return position;
	}

	public void setPosition(Vector2f position)
	{
		this.position = position;
	}
}
