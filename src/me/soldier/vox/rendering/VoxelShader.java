package me.soldier.vox.rendering;

import me.soldier.graphics.Shader;
import me.soldier.math.ModelMatrix;
import me.soldier.math.ProjectionMatrix;
import me.soldier.math.ViewMatrix;

/**
 * Created by Thomas on 21 janv. 2016
 */
public class VoxelShader extends Shader
{
	private static final String VERT_FILE = "src/res/shaders/voxel.vert";
	private static final String FRAG_FILE = "src/res/shaders/voxel.frag";
	
	//Uniform locations
	private int vwMatLoc, prMatLoc, mlMatLoc;
	//Uniform variables
	private ProjectionMatrix prMat;
	private ViewMatrix vwMat;
	private ModelMatrix mlMat;
	
	public VoxelShader()
	{
		super(VERT_FILE, FRAG_FILE);
		this.mlMat = new ModelMatrix();
		this.vwMatLoc = this.getUniformLocation("vw_mat");
		this.prMatLoc = this.getUniformLocation("pr_mat");
		this.mlMatLoc = this.getUniformLocation("ml_mat");
	}

	public void loadUniforms()
	{
		this.setUniform(mlMatLoc, mlMat);
	}

	public void loadFrameUniforms()
	{
		this.setUniform(vwMatLoc, vwMat);
		this.setUniform(prMatLoc, prMat);
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

}
