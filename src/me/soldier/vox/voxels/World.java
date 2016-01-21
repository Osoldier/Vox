package me.soldier.vox.voxels;

/**
 * Created by Thomas on 21 janv. 2016
 */
public class World
{
	private Chunk[][][] chunks;
	private int w, h, d;

	public World(int w, int h, int d)
	{
		this.w = w;
		this.h = h;
		this.d = d;
		chunks = new Chunk[w][h][d];
		for(int x = 0; x < w;x++) {
			for(int y = 0; y < h;y++) {
				for(int z = 0; z < d;z++) {
					chunks[x][y][z] = new Chunk();
				}
			}
		}
		CheckVisibility();
	}
	
	public void CheckVisibility() {
		for(int x = 0; x < w;x++) {
			for(int y = 0; y < h;y++) {
				for(int z = 0; z < d;z++) {
					
				}
			}
		}
	}
	
	public Chunk[][][] getChunks()
	{
		return chunks;
	}
}
