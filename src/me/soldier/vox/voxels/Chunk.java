package me.soldier.vox.voxels;

import java.util.LinkedList;
import java.util.List;

import me.soldier.graphics.*;
import me.soldier.math.*;
import me.soldier.util.*;

/**
 * Created by Thomas on 21 janv. 2016
 */
public class Chunk
{
	public static final int CHUNK_SIZE = 8;
	private Voxel[][][] voxels = new Voxel[CHUNK_SIZE][World.WORLD_HEIGHT][CHUNK_SIZE];
	private Model mesh;
	private List<Voxel> modified;
	
	public Chunk()
	{
		modified = new LinkedList<Voxel>();
		for (int x = 0; x < voxels.length; x++)
		{
			for (int y = 0; y < voxels[x].length; y++)
			{
				for (int z = 0; z < voxels[x][y].length; z++)
				{
					voxels[x][y][z] = new Voxel();
				}
			}
		}
	}

	public Voxel getIntersectedVoxel(RayCaster rayCaster, Vector3f origin, Vector3f offset)
	{
		Voxel intersected = null;
		Vector3f lastPos = null;
		for (int x = 0; x < voxels.length; x++)
		{
			for (int y = 0; y < voxels[x].length; y++)
			{
				for (int z = 0; z < voxels[x][y].length; z++)
				{
					if (voxels[x][y][z].isOpaque() && voxels[x][y][z].isVisible())
					{
						Vector3f pso = new Vector3f(x * Voxel.VOXEL_SIZE+Voxel.VOXEL_SIZE/2, y * Voxel.VOXEL_SIZE+Voxel.VOXEL_SIZE/2, z * Voxel.VOXEL_SIZE+Voxel.VOXEL_SIZE/2);
						if (rayCaster.collideWithVoxel(Vector3f.Add(offset, pso), Voxel.VOXEL_SIZE, origin))
						{
							//If no intersection has been detected yet, or if there's an intersection closer to the camera than the last one
							//TODO verify accuracy 
							Vector3f currentPos = new Vector3f(x, y, z);
							if (intersected == null || (lastPos == null || Vector3f.Sub(origin, lastPos).length() > Vector3f.Sub(origin, currentPos).length()))
							{
								intersected = voxels[x][y][z];
								lastPos = currentPos;
							}
						}
					}
				}
			}
		} 
		return intersected;
	}

	public void generateFloor(double[] noiseMap, int i, int j)
	{
		int sq = (int) Math.sqrt(noiseMap.length);
		for (int x = 0; x < voxels.length; x++)
		{
			for (int z = 0; z < voxels[x][0].length; z++)
			{
				int y = (int) ((noiseMap[(x + i) * sq + (z + j)] + 1) * 10);
				if (y < 0)
					y = 0;
				if (y >= World.WORLD_HEIGHT)
					y = World.WORLD_HEIGHT - 1;
				voxels[x][y][z].setType(VoxelType.GRASS);
				for (int k = 0; k < y; k++)
				{
					voxels[x][k][z].setType(VoxelType.STONE);
				}
			}
		}
		cull();
		mesh = Mesher.Greedy(this);
	}

	public void cull()
	{
		boolean visible = true;
		for (int x = 0; x < voxels.length; x++)
		{
			for (int y = 0; y < voxels[x].length; y++)
			{
				for (int z = 0; z < voxels[x][y].length; z++)
				{
					visible = true;
					if (x > 0 && x + 1 < voxels.length && voxels[x + 1][y][z].isOpaque() && voxels[x - 1][y][z].isOpaque())
					{
						if (y > 0 && y + 1 < voxels.length && voxels[x][y + 1][z].isOpaque() && voxels[x][y - 1][z].isOpaque())
						{
							if (z > 0 && z + 1 < voxels.length && voxels[x][y][z + 1].isOpaque() && voxels[x][y][z - 1].isOpaque())
							{
								visible = false;
							}
						}
					}
					voxels[x][y][z].setVisible(visible);
				}
			}
		}
	}

	public Voxel getAt(int x, int y, int z)
	{
		return voxels[x][y][z];
	}
	
	public void setAt(int x, int y, int z, VoxelType type)
	{
		voxels[x][y][z].setType(type);
	}

	public Voxel[][][] getVoxels()
	{
		return voxels;
	}

	public void setVoxels(Voxel[][][] voxels)
	{
		this.voxels = voxels;
	}

	public Model getMesh()
	{
		return mesh;
	}

	public void setMesh(Model mesh)
	{
		this.mesh = mesh;
	}

	/**
	 * @return the modified
	 */
	public List<Voxel> getModified()
	{
		return modified;
	}

	/**
	 * @param modified the modified to set
	 */
	public void setModified(List<Voxel> modified)
	{
		this.modified = modified;
	}

}
