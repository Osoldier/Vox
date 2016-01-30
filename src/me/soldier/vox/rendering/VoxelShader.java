package me.soldier.vox.rendering;

import me.soldier.graphics.*;
import me.soldier.math.*;

/**
 * Created by Thomas on 21 janv. 2016
 */
public class VoxelShader extends Shader
{
	private static final String VERT_FILE = "src/res/shaders/voxel/voxel.vert";
	private static final String GEO_FILE = "src/res/shaders/voxel/voxel.geo";
	private static final String FRAG_FILE = "src/res/shaders/voxel/voxel.frag";
	
	//Uniform locations
	private int vwMatLoc, prMatLoc, mlMatLoc, lightLoc;
	//Uniform variables
	private ProjectionMatrix prMat;
	private ViewMatrix vwMat;
	private ModelMatrix mlMat;
	private Vector3f currentColor;
	private Light sun;
	
	public VoxelShader()
	{
		super(VERT_FILE, FRAG_FILE, GEO_FILE);
		this.mlMat = new ModelMatrix();
		this.vwMatLoc = this.getUniformLocation("vw_mat");
		this.prMatLoc = this.getUniformLocation("pr_mat");
		this.mlMatLoc = this.getUniformLocation("ml_mat");
		this.lightLoc = this.getUniformLocation("lightPos");
	}

	public void loadUniforms()
	{
		this.setUniform(mlMatLoc, mlMat);
	}

	public void loadFrameUniforms()
	{
		this.setUniform(vwMatLoc, vwMat);
		this.setUniform(prMatLoc, prMat);
		this.setUniform(lightLoc, sun.getPosition());
	}

	public ProjectionMatrix getPrMat()
	{
		return prMat;
	}

	public void setPrMat(ProjectionMatrix prMat)
	{
		this.prMat = prMat;
	}

	public ViewMatrix getVwMat()
	{
		return vwMat;
	}

	public void setVwMat(ViewMatrix vwMat)
	{
		this.vwMat = vwMat;
	}

	public ModelMatrix getMlMat()
	{
		return mlMat;
	}

	public void setMlMat(ModelMatrix mlMat)
	{
		this.mlMat = mlMat;
	}

	public Vector3f getCurrentColor()
	{
		return currentColor;
	}

	public void setCurrentColor(Vector3f currentColor)
	{
		this.currentColor = currentColor;
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
