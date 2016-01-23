package me.soldier.vox.voxels;

import me.soldier.graphics.*;
import me.soldier.math.*;
import me.soldier.util.*;

/**
 * Created by Thomas on 21 janv. 2016
 */
public class World
{
	private Chunk[][] chunks;
	private int w, d;
	public static int WORLD_HEIGHT = 256;
	private Light sun;

	public World(int w, int d)
	{
		this.w = w;
		this.d = d;
		double[] noiseMap = Noise.perlinNoise(w*Chunk.CHUNK_SIZE, d*Chunk.CHUNK_SIZE, 10);
		chunks = new Chunk[w][d];
		long time = System.currentTimeMillis();
		setSun(new Light(new Vector3f(0, 4096, 0), Vector3f.oneVec));
		for (int x = 0; x < w; x++)
		{
			for (int z = 0; z < d; z++)
			{
				chunks[x][z] = new Chunk();
				chunks[x][z].generateFloor(noiseMap, x*Chunk.CHUNK_SIZE, z*Chunk.CHUNK_SIZE);
			}
		}
		System.out.println(System.currentTimeMillis() - time);
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
}
