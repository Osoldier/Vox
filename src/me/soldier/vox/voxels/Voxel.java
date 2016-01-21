package me.soldier.vox.voxels;

/**
 * Created by Thomas on 19 janv. 2016
 */
public class Voxel
{
	public static final int VOXEL_SIZE = 16;
	private VoxelType type;
	private boolean visible = true, opaque = true;
	
	//Reserved for further use
	private VoxelFace[] faces = new VoxelFace[6];
	
	public Voxel()
	{
		this(VoxelType.GRASS);
	}

	public Voxel(VoxelType type)
	{
		this.type = type;
		setOpaque(type != VoxelType.AIR);
	}
	
	public boolean canBeMerged(Voxel v) {
		return this.type == v.type || !v.isVisible() || !this.isVisible();
	}
	
	public VoxelFace getFace(short face) {
		return faces[face-VoxelFace.NORTH];
	}

	public VoxelType getType()
	{
		return type;
	}

	public void setType(VoxelType type)
	{
		this.type = type;
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
}
