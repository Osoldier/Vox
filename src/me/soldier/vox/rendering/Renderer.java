package me.soldier.vox.rendering;

import me.soldier.math.ProjectionMatrix;
import me.soldier.vox.core.Camera;
import me.soldier.vox.ui.UIElement;
import me.soldier.vox.voxels.World;

/**
 * Created by Thomas on 19 janv. 2016
 */
public class Renderer
{
	private final float fov = 70f, znear = 0.1f, zfar = 10000f;
	private ProjectionMatrix perspective, orthographic;
	
	private VoxelRenderer voxelRenderer;
	private UIRenderer uiRenderer;
	
	public Renderer(int width, int height)
	{
		this.perspective = new ProjectionMatrix(fov, (float) width / (float) height, znear, zfar);
		this.orthographic = new ProjectionMatrix(0, width, height, 0, 0, 1);
		this.voxelRenderer = new VoxelRenderer();
		this.uiRenderer = new UIRenderer();
	}

	public void RenderScene(World w, Camera pov, boolean wireframe)
	{
		voxelRenderer.Prepare(w, pov.vw_matrix, perspective, wireframe);
		for (int x = 0; x < w.getChunks().length; x++)
		{
			for (int z = 0; z < w.getChunks()[x].length; z++)
			{
				voxelRenderer.Render(w.getChunks()[x][z], x, z);
			}
		}
		voxelRenderer.Clean();
	}
	
	public void RenderUI(UIElement element) {
		uiRenderer.render(orthographic, element);
	}

	public ProjectionMatrix getPerspective()
	{
		return perspective;
	}

	public void setPerspective(ProjectionMatrix perspective)
	{
		this.perspective = perspective;
	}

	public ProjectionMatrix getOrthographic()
	{
		return orthographic;
	}

	public void setOrthographic(ProjectionMatrix orthographic)
	{
		this.orthographic = orthographic;
	}

}
