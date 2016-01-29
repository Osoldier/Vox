package me.soldier.vox.filemanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import me.soldier.math.Vector3f;
import me.soldier.util.Mesher;
import me.soldier.vox.core.Engine;
import me.soldier.vox.voxels.Chunk;
import me.soldier.vox.voxels.VoxelType;
import me.soldier.vox.voxels.World;

/**
 * Created by Thomas on 29 janv. 2016
 */
public class FileLoader
{

	private Engine engine;

	public FileLoader(Engine engine)
	{
		this.engine = engine;
	}

	public World loadFile(String path)
	{
		File file = new File(path);
		FileInputStream fis;
		World loaded = null;
		try
		{
			fis = new FileInputStream(file);
			int version = fis.read();
			System.out.println("File version: " + version);
			if (version == 0)
			{
				byte[] position = new byte[12];
				fis.read(position);
				int x = ByteBuffer.wrap(position).getInt(0);
				int y = ByteBuffer.wrap(position).getInt(4);
				int z = ByteBuffer.wrap(position).getInt(8);
				System.out.println("Position: " + "(" + x + " " + y + " " + z + ")");
				byte[] size = new byte[8];
				fis.read(size);
				int w = ByteBuffer.wrap(size).getInt(0);
				int h = ByteBuffer.wrap(size).getInt(4);
				System.out.println("World Size: " + "(" + w + " " + h + ")");
				byte[] seed = new byte[8];
				fis.read(seed);
				long wseed = ByteBuffer.wrap(seed).getLong(0);
				System.out.println("Seed: " + wseed);
				engine.getPov().position = new Vector3f(x, y, z);
				loaded = new World(w, h, wseed);
				SetVoxels(fis, loaded);
			}
			fis.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return loaded;
	}

	private void SetVoxels(FileInputStream fis, World w)
	{
		try
		{
			int B = fis.available() / 14;
			System.out.println("Number of modifed voxels: " + B);
			for (int i = 0; i < B; i++)
			{
				byte[] voxeldata = new byte[14];
				fis.read(voxeldata);
				int x = ByteBuffer.wrap(voxeldata).getInt(0);
				int y = ByteBuffer.wrap(voxeldata).getInt(4);
				int z = ByteBuffer.wrap(voxeldata).getInt(8);
				short id = ByteBuffer.wrap(voxeldata).getShort(12);
				Chunk c = w.getChunks()[x / Chunk.CHUNK_SIZE][z / Chunk.CHUNK_SIZE];
				int rx = (int) (x - Math.floor(x / Chunk.CHUNK_SIZE) * Chunk.CHUNK_SIZE), rz = (int) (z - Math.floor(z / Chunk.CHUNK_SIZE) * Chunk.CHUNK_SIZE);
				c.setAt(rx, y, rz, VoxelType.getbyID(id));
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		for (int i = 0; i < w.getWidth(); i++)
		{
			for (int j = 0; j < w.getDepth(); j++)
			{
				Chunk c = w.getChunks()[i][j];
				c.cull();
				c.setMesh(Mesher.Greedy(c));
			}
		}
	}
}
