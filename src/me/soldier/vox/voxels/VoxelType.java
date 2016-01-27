package me.soldier.vox.voxels;

import me.soldier.math.*;

/**
 * Created by Thomas on 19 janv. 2016
 */
public enum VoxelType
{
	GRASS(new Vector3f(0, 0.545f, 0), 6), STONE(new Vector3f(0.5f, 0.5f, 0.5f), 10), AIR(new Vector3f(), -1), WOOD(new Vector3f(0.87f, 0.72f, 0.53f), 8);

	Vector3f color;
	int resistance;

	private VoxelType(Vector3f color, int resistance)
	{
		this.color = color;
		this.resistance = resistance;
	}
	
	public Vector3f getColor() {
		return color;
	}

	public int getResistance()
	{
		return resistance;
	}
}
