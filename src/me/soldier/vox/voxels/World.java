package me.soldier.vox.voxels;

import me.soldier.graphics.Light;
import me.soldier.math.Vector3f;
import me.soldier.util.Mesher;
import me.soldier.util.Noise;
import me.soldier.util.RayCaster;
import me.soldier.vox.core.MouseHandler;

/**
 * Created by Thomas on 21 janv. 2016
 */
public class World
{
	public static int WORLD_HEIGHT = 256;
	private Chunk[][] chunks;
	private int width, depth;
	private Light sun;
	private long seed;

	public World(int w, int d, long seed)
	{
		this.width = w;
		this.depth = d;
		this.seed = seed;
		double[] noiseMap = Noise.perlinNoise(w * Chunk.CHUNK_SIZE, d * Chunk.CHUNK_SIZE, (w+1)/2, seed);
		chunks = new Chunk[w][d];
		long time = System.currentTimeMillis();
		setSun(new Light(new Vector3f(0, 4096, 0), Vector3f.oneVec));
		for (int x = 0; x < w; x++)
		{
			for (int z = 0; z < d; z++)
			{
				chunks[x][z] = new Chunk();
				chunks[x][z].generateFloor(noiseMap, x * Chunk.CHUNK_SIZE, z * Chunk.CHUNK_SIZE);
			}
		}
		System.out.println(System.currentTimeMillis() - time);
	}

	public void Update(RayCaster rayCaster, Vector3f origin)
	{
		Voxel current = null;
		Chunk chunk = null;
		int decal = Chunk.CHUNK_SIZE * Voxel.VOXEL_SIZE;
		outerloop: for (int x = 0; x < width; x++)
		{
			for (int z = 0; z < depth; z++)
			{
				current = chunks[x][z].getIntersectedVoxel(rayCaster, origin, new Vector3f(x * decal, 0, z * decal));
				chunk = chunks[x][z];
				if(current != null)
					break outerloop;
			}
		}
		if (current != null && MouseHandler.isButtonDown(0))
		{
			current.setResistance(current.getResistance()-1);
			if(current.getResistance() <= 0) {
				if(!chunk.getModified().contains(current))
					chunk.getModified().add(current);
				current.setType(VoxelType.AIR);
				chunk.cull();
				chunk.setMesh(Mesher.Greedy(chunk));
			}
		}
	}

	public Chunk[][] getChunks()
	{
		return chunks;
	}

	public Light getSun()
	{
		return sun;
	}

	public void setSun(Light sun)
	{
		this.sun = sun;
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public long getSeed() {
		return seed;
	}

	public void setSeed(long seed) {
		this.seed = seed;
	}
}
