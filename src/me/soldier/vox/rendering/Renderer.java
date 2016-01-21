package me.soldier.vox.rendering;

import me.soldier.math.ProjectionMatrix;
import me.soldier.vox.core.Camera;
import me.soldier.vox.voxels.World;

/**
 * Created by Thomas on 19 janv. 2016
 */
public class Renderer
{
	private final float fov = 70f, znear = 0.1f, zfar = 1000f;
	private ProjectionMatrix perspective, orthographic;
	private VoxelRenderer voxelRenderer;

	public Renderer(int width, int height)
	{
		this.perspective = new ProjectionMatrix(fov, (float) width / (float) height, znear, zfar);
		this.orthographic = new ProjectionMatrix(0, width, height, 0, -1, 1);
		voxelRenderer = new VoxelRenderer();
	}

	public void RenderScene(World w, Camera pov)
	{
		voxelRenderer.Prepare(pov.vw_matrix, perspective);
		for (int x = 0; x < w.getChunks().length; x++)
		{
			for (int y = 0; y < w.getChunks()[x].length; y++)
			{
				for (int z = 0; z < w.getChunks()[x][y].length; z++)
				{
					voxelRenderer.Render(w.getChunks()[x][y][z], x, y, z);
				}
			}
		}
	}

}
