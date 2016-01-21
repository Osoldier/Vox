package me.soldier.util;

import static me.soldier.vox.voxels.Chunk.CHUNK_SIZE;
import static me.soldier.vox.voxels.Voxel.VOXEL_SIZE;

import java.util.List;

import me.soldier.graphics.Model;
import me.soldier.graphics.OGLUtil;
import me.soldier.math.Vector3f;
import me.soldier.vox.voxels.Chunk;
import me.soldier.vox.voxels.Voxel;

/**
 * Created by Thomas on 19 janv. 2016
 */
public abstract class Mesher
{
	/**
	 * Greedy Mesher algorithm, made with <a href="http://0fps.net/2012/06/30/meshing-in-a-minecraft-game/">This article</a>
	 * @param c Chunk to mesh
	 * @param holder list of models to hold the quads
	 */
	public static void Greedy(Chunk c, List<Model> holder)
	{
		// Position of the new quad's bottom left corner
		int[] position = new int[3];
		// Allows us to select a new voxel to merge depending on the dimension
		// we're evaluating
		int[] offset = new int[3];
		//Positioning offsets
		int[] xOffset = new int[3];
		int[] yOffset = new int[3];
		int u, v;

		// Size squared because we'll iterate once per direction (6 times)
		Voxel[] mask = new Voxel[Chunk.CHUNK_SIZE * Chunk.CHUNK_SIZE];
		//the mask index
		int n;
		// Buffer variables
		Voxel tmp1, tmp2;
		// Quad size, used for tiling textures
		int width, height;

		boolean back = true, b = false;
		while (b != back)
		{
			// d = x or y or z
			for (int d = 0; d < 3; d++)
			{
				position[0] = position[1] = position[2] = 0;
				offset[0] = offset[1] = offset[2] = 0;
				offset[d] = 1;
				position[d] = -1;
				u = (d + 1) % 3;
				v = (d + 2) % 3;

				while (position[d] < CHUNK_SIZE)
				{
					n = 0;

					for (position[v] = 0; position[v] < CHUNK_SIZE; position[v]++)
					{
						for (position[u] = 0; position[u] < CHUNK_SIZE; position[u]++)
						{
							tmp1 = (position[d] >= 0) ? c.getAt(position[0], position[1], position[2]) : null;
							tmp2 = (position[d] < CHUNK_SIZE - 1) ? c.getAt(position[0] + offset[0], position[1] + offset[1], position[2] + offset[2]) : null;
							// if the two voxels can be merged, we add null to
							// the mesh mask otherwise we add the actual voxel.
							mask[n++] = (tmp1 != null && tmp2 != null && tmp1.canBeMerged(tmp2)) ? null : (back ? tmp1 : tmp2);
						}
					}
					// We increment the current voxel (face) to compute
					position[d]++;
					n = 0;

					for (int j = 0; j < CHUNK_SIZE; j++)
					{
						int i = 0;
						while (i < CHUNK_SIZE)
						{
							if (mask[n] != null)
							{
								for (width = 1; (i + width) < CHUNK_SIZE && mask[n + width] != null && mask[n + width].canBeMerged(mask[n]); width++)
								{
									// No code, the point of this loop is to get
									// the right width value for the mesh
								}

								outerloop: for (height = 1; j + height < CHUNK_SIZE; height++)
								{
									for (int k = 0; k < width; k++)
									{
										//calculate the right height value for the given width
										if (mask[n + k + height * CHUNK_SIZE] == null || !(mask[n + k + height * CHUNK_SIZE].canBeMerged(mask[n])))
										{
											break outerloop;
										}
									}
								}
								//if the voxel isn't visible we don't create a mesh for it
								if (mask[n].isOpaque() && mask[n].isVisible())
								{
									//Relative positions, will be multiplied to make a voxel later
									position[u] = i;
									position[v] = j;

									xOffset[0] = xOffset[1] = xOffset[2] = 0;
									xOffset[u] = width;

									yOffset[0] = yOffset[1] = yOffset[2] = 0;
									yOffset[v] = height;

									CreateQuad(
											new Vector3f(position[0], position[1], position[2]),
											new Vector3f(position[0] + xOffset[0], position[1] + xOffset[1], position[2] + xOffset[2]),
											new Vector3f(position[0] + xOffset[0] + yOffset[0], position[1] + xOffset[1] + yOffset[1], position[2] + xOffset[2] + yOffset[2]),
											new Vector3f(position[0] + yOffset[0], position[1] + yOffset[1], position[2] + yOffset[2]),
											back, width, height, holder);
								}

								// Reset mask and increase counters
								for (int l = 0; l < height; l++)
								{
									for (int k = 0; k < width; k++)
									{
										mask[n + k + l * CHUNK_SIZE] = null;
									}
								}
								//go to the next "line"
								i += width;
								n += width;
							} else
							{
								//go to the next quad
								i++;
								n++;
							}
						}
					}
				}
			}
			back = back & b;
			b = !b;
		}
	}

	private static void CreateQuad(Vector3f bl, Vector3f tl, Vector3f tr, Vector3f br, boolean back, int width, int height, List<Model> holder)
	{
		//the positions of the actual quad
		float[] positions = new float[] {
				bl.x * VOXEL_SIZE, bl.y * VOXEL_SIZE, bl.z * VOXEL_SIZE,
				br.x * VOXEL_SIZE, br.y * VOXEL_SIZE, br.z * VOXEL_SIZE,
				tl.x * VOXEL_SIZE, tl.y * VOXEL_SIZE, tl.z * VOXEL_SIZE,
				tr.x * VOXEL_SIZE, tr.y * VOXEL_SIZE, tr.z * VOXEL_SIZE,
		};
		//Multiply to use tiling
		float[] texCoords = new float[] {
				0, 1,
				1*width, 1*height,
				0, 0,
				1*width, 0
		};
		//the indices order changes if the voxel is looking backward, so culling don't discard it
		int[] indices = (back ? new int[] { 2, 0, 1, 1, 3, 2 } : new int[] { 2, 3, 1, 1, 0, 2 });
		holder.add(OGLUtil.createMeshVAO(positions, positions, texCoords, indices));
	}
}
