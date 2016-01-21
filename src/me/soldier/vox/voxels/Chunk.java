package me.soldier.vox.voxels;

import java.util.LinkedList;
import java.util.List;

import me.soldier.graphics.Model;
import me.soldier.util.Mesher;

/**
 * Created by Thomas on 21 janv. 2016
 */
public class Chunk
{
	public static final int CHUNK_SIZE = 16;
	private Voxel[][][] voxels = new Voxel[CHUNK_SIZE][CHUNK_SIZE][CHUNK_SIZE];
	private List<Model> meshes = new LinkedList<Model>();

	public Chunk()
	{
		for (int x = 0; x < voxels.length; x++)
		{
			for (int y = 0; y < voxels[x].length; y++)
			{
				for (int z = 0; z < voxels[x][y].length; z++)
				{
					if(Math.random() > 0.9)
					{
						voxels[x][y][z] = new Voxel(VoxelType.GRASS);
					} else {
						voxels[x][y][z] = new Voxel(VoxelType.STONE);
					}
				}
			}
		}
		Mesher.Greedy(this, meshes);
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

	public List<Model> getMeshes()
	{
		return meshes;
	}

	public void setMesh(List<Model> meshes)
	{
		this.meshes = meshes;
	}

}
