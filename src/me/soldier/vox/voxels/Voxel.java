package me.soldier.vox.voxels;

import me.soldier.math.*;

/**
 * Created by Thomas on 19 janv. 2016
 */
public class Voxel
{
	public static final int VOXEL_SIZE = 16;
	private VoxelType type;
	private boolean visible = true, opaque = true;
	private Vector3f color;
	
	public Voxel()
	{
		this(VoxelType.AIR);
	}

	public Voxel(VoxelType type)
	{
		this.setType(type);
	}
	
	public boolean canBeMerged(Voxel v) {
		return this.type == v.type || !v.isVisible() || !this.isVisible();
	}
	
	public VoxelType getType()
	{
		return type;
	}

	public void setType(VoxelType type)
	{
		this.type = type;
		setOpaque(type != VoxelType.AIR);
		this.setColor(type.getColor());
	}

	public boolean isVisible()
	{
		return visible;
	}

	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

	public boolean isOpaque()
	{
		return opaque;
	}

	public void setOpaque(boolean opaque)
	{
		this.opaque = opaque;
	}

	public Vector3f getColor()
	{
		return color;
	}

	public void setColor(Vector3f color)
	{
		this.color = color;
	}
}
