package me.soldier.vox.rendering;

import me.soldier.math.*;

/**
 * Created by Thomas on 19 janv. 2016
 */
public class Renderer
{
	private final float fov = 70f, znear = 0.1f, zfar = 1000f;
	private ProjectionMatrix perspective, orthographic;
	
	public Renderer(int width, int height)
	{
		this.perspective = new ProjectionMatrix(fov, (float)width/(float)height, znear, zfar);
		this.orthographic = new ProjectionMatrix(0, width, height, 0, -1, 1);
	}
	
}
