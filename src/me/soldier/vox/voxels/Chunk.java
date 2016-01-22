package me.soldier.vox.voxels;

import me.soldier.graphics.*;
import me.soldier.util.*;

/**
 * Created by Thomas on 21 janv. 2016
 */
public class Chunk
{
	public static final int CHUNK_SIZE = 16;
	private Voxel[][][] voxels = new Voxel[CHUNK_SIZE][World.WORLD_HEIGHT][CHUNK_SIZE];
	private Model mesh;

	public Chunk()
	{
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

	public void generateFloor(double[] noiseMap, int i, int j)
	{
		for (int x = 0; x < voxels.length; x++)
		{
			for (int z = 0; z < voxels[x][0].length; z++)
			{
				int y = (int) ((noiseMap[(x+i) * 256 + (z+j)] + 1) * 10);
				if (y < 0)
					y = 0;
				if(y >= World.WORLD_HEIGHT) y = World.WORLD_HEIGHT-1;
				voxels[x][y][z].setType(VoxelType.GRASS);
				for (int k = 0; k < y; k++) {
					voxels[x][k][z].setType(VoxelType.STONE);
				}
			}
		}
		cull();
		mesh = Mesher.Greedy(this);
	}

	public void cull()
	{
		for (int x = 0; x < voxels.length; x++)
		{
			for (int y = 0; y < voxels[x].length; y++)
			{
				for (int z = 0; z < voxels[x][y].length; z++)
				{
					if (x > 0 && x + 1 < voxels.length && voxels[x+1][y][z].isOpaque()&& voxels[x-1][y][z].isOpaque())
					{
						if (y > 0 && y + 1 < voxels.length && voxels[x][y+1][z].isOpaque()&& voxels[x][y-1][z].isOpaque())
						{
							if (z > 0 && z + 1 < voxels.length && voxels[x][y][z+1].isOpaque() && voxels[x][y][z-1].isOpaque())
							{
								voxels[x][y][z].setVisible(false);
							}
						}
					}
				}
			}
		}
	}

	public Voxel getAt(int x, int y, int z)
	{
		return voxels[x][y][z];
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

}
