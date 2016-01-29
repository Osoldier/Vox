package me.soldier.vox.filemanager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import me.soldier.vox.core.Engine;
import me.soldier.vox.voxels.Chunk;
import me.soldier.vox.voxels.Voxel;

/**
 * Created by Thomas on 28 janv. 2016
 */
public class FileSaver
{

	public static final String FILEPATH = System.getProperty("user.home") + File.separator + "Vox" + File.separator;
	private Engine engine;
	private final byte version = 0x0;

	public FileSaver(Engine engine)
	{
		this.engine = engine;
	}

	public void writeFile(String path, String name)
	{
		File filePath = new File(path);
		if (!filePath.exists())
			filePath.mkdirs();
		File file = new File(path + File.separator + name + ".ves");
		byte[] header = ByteBuffer.allocate(1).put(version).array();
		byte[] position = ByteBuffer.allocate(12).putInt((int) engine.getPov().position.x).putInt((int) engine.getPov().position.y).putInt((int) engine.getPov().position.z).array();
		byte[] size = ByteBuffer.allocate(8).putInt(engine.getWorld().getWidth()).putInt(engine.getWorld().getDepth()).array();
		byte[] seed = ByteBuffer.allocate(8).putLong(engine.getWorld().getSeed()).array();
		FileOutputStream fos;
		try
		{
			fos = new FileOutputStream(file);
			fos.write(header, 0, header.length);
			fos.write(position, 0, position.length);
			fos.write(size, 0, size.length);
			fos.write(seed, 0, seed.length);
			for(int x = 0; x < engine.getWorld().getWidth();x++) {
				for(int z = 0; z < engine.getWorld().getDepth();z++) {
					for(int vx = 0; vx < engine.getWorld().getChunks()[x][z].getVoxels().length;vx++) {
						for(int vy = 0; vy < engine.getWorld().getChunks()[x][z].getVoxels()[vx].length;vy++) {
							for(int vz = 0; vz < engine.getWorld().getChunks()[x][z].getVoxels()[vx][vy].length;vz++) {
								Voxel v = engine.getWorld().getChunks()[x][z].getVoxels()[vx][vy][vz];
								if(engine.getWorld().getChunks()[x][z].getModified().contains(v)) {
									byte[] voxel = ByteBuffer.allocate(14).putInt(x*Chunk.CHUNK_SIZE+vx).putInt(vy).putInt(z*Chunk.CHUNK_SIZE+vz).putShort(v.getType().getID()).array();
									fos.write(voxel, 0, voxel.length);
								}
							}
						}
					}
				}
			}
			fos.flush();
			fos.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}

}
