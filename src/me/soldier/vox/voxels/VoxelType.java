package me.soldier.vox.voxels;

import me.soldier.math.*;

/**
 * Created by Thomas on 19 janv. 2016
 */
public enum VoxelType
{
	GRASS(new Vector3f(0, 0.545f, 0)), STONE(new Vector3f(0.5f, 0.5f, 0.5f)), AIR(new Vector3f());

	Vector3f color;

	private VoxelType(Vector3f color)
	{
		this.color = color;
	}
	
	public Vector3f getColor() {
		return color;
	}
}
