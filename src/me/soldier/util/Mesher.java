package me.soldier.util;

import static me.soldier.vox.voxels.Voxel.*;

import java.util.*;

import me.soldier.graphics.*;
import me.soldier.math.*;
import me.soldier.vox.voxels.*;

/**
 * Created by Thomas on 19 janv. 2016
 */
public abstract class Mesher
{
	/**
	 * Greedy Mesher algorithm, made with <a
	 * href="http://0fps.net/2012/06/30/meshing-in-a-minecraft-game/">This
	 * article</a>
	 * 
	 * @param c
	 *            Chunk to mesh
	 * @param holder
	 *            list of models to hold the quads
	 */
	public static Model Greedy(Chunk c)
	{
		// Position of the new quad's bottom left corner
		int[] position = new int[3];
		// Allows us to select a new voxel to merge depending on the dimension
		// we're evaluating
		int[] offset = new int[3];
		// Positioning offsets
		int[] xOffset = new int[3];
		int[] yOffset = new int[3];
		int u, v;

		List<float[]> floatdata = new LinkedList<float[]>();
		List<int[]> indices = new LinkedList<int[]>();
		int[] dimentions = new int[]{Chunk.CHUNK_SIZE, World.WORLD_HEIGHT, Chunk.CHUNK_SIZE};
		Voxel[] mask;
		// the mask index
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
				// we'll iterate once per direction (6 times)
				mask = new Voxel[dimentions[u] * dimentions[v]];

				while (position[d] < dimentions[d])
				{
					n = 0;
					for (position[v] = 0; position[v] < dimentions[v]; position[v]++)
					{
						for (position[u] = 0; position[u] < dimentions[u]; position[u]++)
						{
							tmp1 = (position[d] >= 0) ? c.getAt(position[0], position[1], position[2]) : null;

							tmp2 = (position[d] < dimentions[d] - 1) ? c.getAt(position[0] + offset[0], position[1] + offset[1], position[2] + offset[2]) : null;
							// if the two voxels can be merged, we add null to
							// the mesh mask otherwise we add the actual voxel.
							mask[n++] = (tmp1 != null && tmp2 != null && tmp1.canBeMerged(tmp2)) ? null : (back ? tmp1 : tmp2);
						}
					}
					// We increment the current voxel (face) to compute
					position[d]++;
					n = 0;

					for (int j = 0; j < dimentions[v]; j++)
					{
						int i = 0;
						while (i < dimentions[u])
						{
							if (mask[n] != null)
							{
								for (width = 1; (i + width) < dimentions[u] && mask[n + width] != null && mask[n + width].canBeMerged(mask[n]); width++)
								{
									// No code, the point of this loop is to get
									// the right width value for the mesh
								}

								outerloop: for (height = 1; j + height < dimentions[v]; height++)
								{
									for (int k = 0; k < width; k++)
									{
										// calculate the right height value for
										// the given width
										if (mask[n + k + height * dimentions[u]] == null || !(mask[n + k + height * dimentions[u]].canBeMerged(mask[n])))
										{
											break outerloop;
										}
									}
								}
								// if the voxel isn't visible we don't create a
								// mesh for it
								if (mask[n].isOpaque() && mask[n].isVisible())
								{
									// Relative positions, will be multiplied to
									// make a voxel later
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
											back, width, height, mask[n].getColor(), floatdata, indices);
								}

								// Reset mask and increase counters
								for (int l = 0; l < height; l++)
								{
									for (int k = 0; k < width; k++)
									{
										mask[n + k + l *dimentions[u]] = null;
									}
								}
								// go to the next "line"
								i += width;
								n += width;
							} else
							{
								// go to the next quad
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
		float[] vertices = new float[floatdata.size() * 12 / 2];
		float[] colors = new float[floatdata.size() * 12 / 2];
		int[] finalIndices = new int[indices.size() * 6];

		for (int k = 0; k < floatdata.size(); k++)
		{
			if (k % 2 == 0)
			{
				for (int i = 0; i < floatdata.get(k).length; i++)
				{
					vertices[k / 2 * 12 + i] = floatdata.get(k)[i];
				}
			} else
			{
				for (int i = 0; i < floatdata.get(k).length; i++)
				{
					colors[k / 2 * 12 + i] = floatdata.get(k)[i];
				}
			}
		}

		for (int j = 0; j < indices.size(); j++)
		{
			finalIndices[j * 6] = indices.get(j)[0];
			finalIndices[j * 6 + 1] = indices.get(j)[1];
			finalIndices[j * 6 + 2] = indices.get(j)[2];
			finalIndices[j * 6 + 3] = indices.get(j)[3];
			finalIndices[j * 6 + 4] = indices.get(j)[4];
			finalIndices[j * 6 + 5] = indices.get(j)[5];
		}
		return OGLUtil.createMeshVAO(vertices, vertices, colors, finalIndices);
	}

	private static void CreateQuad(Vector3f bl, Vector3f tl, Vector3f tr, Vector3f br, boolean back, int width, int height, Vector3f color, List<float[]> floatdata, List<int[]> ind)
	{
		// the positions of the actual quad
		float[] positions = new float[] {
				bl.x * VOXEL_SIZE, bl.y * VOXEL_SIZE, bl.z * VOXEL_SIZE,
				br.x * VOXEL_SIZE, br.y * VOXEL_SIZE, br.z * VOXEL_SIZE,
				tl.x * VOXEL_SIZE, tl.y * VOXEL_SIZE, tl.z * VOXEL_SIZE,
				tr.x * VOXEL_SIZE, tr.y * VOXEL_SIZE, tr.z * VOXEL_SIZE,
		};
		// the indices order changes if the voxel is looking backward, so
		// culling don't discard it
		int os = 4 * ind.size();
		int[] indices = (!back ? new int[] { 2 + os, 0 + os, 1 + os, 1 + os, 3 + os, 2 + os } : new int[] { 2 + os, 3 + os, 1 + os, 1 + os, 0 + os, 2 + os });
		floatdata.add(positions);
		floatdata.add(new float[] {
				color.x, color.y, color.z,
				color.x, color.y, color.z,
				color.x, color.y, color.z,
				color.x, color.y, color.z });
		ind.add(indices);
	}
}
