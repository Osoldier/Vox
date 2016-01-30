package me.soldier.vox.rendering;

import me.soldier.graphics.Shader;
import me.soldier.math.ModelMatrix;
import me.soldier.math.ProjectionMatrix;

/**
 * Created by Thomas on 30 janv. 2016
 */
public class UIShader extends Shader
{
	
	private static final String VERT_FILE = "src/res/shaders/ui/ui.vert";
	private static final String FRAG_FILE = "src/res/shaders/ui/ui.frag";
	
	private ModelMatrix mlMatrix;
	private ProjectionMatrix prMatrix;

	private int mlLoc, prLoc;
	
	public UIShader()
	{
		super(VERT_FILE, FRAG_FILE);
		this.mlMatrix = new ModelMatrix();
		this.mlLoc = this.getUniformLocation("ml_mat");
		this.prLoc = this.getUniformLocation("pr_mat");
	}

	@Override
	public void loadUniforms()
	{
		this.setUniform(mlLoc, mlMatrix);
	}

	@Override
	public void loadFrameUniforms()
	{
		this.setUniform(prLoc, prMatrix);
	}

	public ModelMatrix getMlMatrix()
	{
		return mlMatrix;
	}

	public void setMlMatrix(ModelMatrix mlMatrix)
	{
		this.mlMatrix = mlMatrix;
	}

	public ProjectionMatrix getPrMatrix()
	{
		return prMatrix;
	}

	public void setPrMatrix(ProjectionMatrix prMatrix)
	{
		this.prMatrix = prMatrix;
	}

}
