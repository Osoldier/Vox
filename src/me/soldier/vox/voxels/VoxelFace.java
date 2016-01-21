package me.soldier.vox.voxels;

/**
 * Created by Thomas on 19 janv. 2016
 */
public class VoxelFace
{
	//Reserved for optimization
	public static final short NORTH = 0xAA;
	public static final short SOUTH = 0xAB;
	public static final short WEST = 0xAC;
	public static final short EAST = 0xAD;
	public static final short TOP = 0xAE;
	public static final short BOTTOM = 0xAF;
	
	private boolean visible;
	
	public VoxelFace(short type)
	{
		
	}

	public boolean isVisible()
	{
		return true;
	}

	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}
}
