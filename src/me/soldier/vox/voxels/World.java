package me.soldier.vox.voxels;

/**
 * Created by Thomas on 21 janv. 2016
 */
public class World
{
	private Chunk[][][] chunks;

	public World(int w, int h, int d)
	{
		chunks = new Chunk[w][h][d];
		for(int x = 0; x < w;x++) {
			for(int y = 0; y < h;y++) {
				for(int z = 0; z < d;z++) {
					chunks[x][y][z] = new Chunk();
				}
			}
		}
	}
	
	public Chunk[][][] getChunks()
	{
		return chunks;
	}
}
