package me.soldier.vox.voxels;

import me.soldier.math.*;

/**
 * Created by Thomas on 19 janv. 2016
 */
public enum VoxelType
{
	GRASS(0, new Vector3f(0, 0.545f, 0), 6), STONE(1, new Vector3f(0.5f, 0.5f, 0.5f), 10), AIR(2, new Vector3f(), -1), WOOD(3, new Vector3f(0.87f, 0.72f, 0.53f), 8);

	Vector3f color;
	int resistance;
	short id;

	private VoxelType(int id, Vector3f color, int resistance)
	{
		this.id = (short)id;
		this.color = color;
		this.resistance = resistance;
	}

	public static VoxelType getbyID(int id)
	{
		for (VoxelType vt : VoxelType.values())
		{
			if (vt.id == id)
				return vt;
		}
		return null;
	}

	public Vector3f getColor()
	{
		return color;
	}

	public short getID()
	{
		return id;
	}

	public int getResistance()
	{
		return resistance;
	}
}
