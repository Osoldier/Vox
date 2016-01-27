package me.soldier.vox.rendering;

import static me.soldier.vox.voxels.Chunk.*;
import static me.soldier.vox.voxels.Voxel.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import me.soldier.math.*;
import me.soldier.vox.voxels.*;

/**
 * Created by Thomas on 21 janv. 2016
 */
public class VoxelRenderer
{
	private VoxelShader shader;

	public VoxelRenderer()
	{
		shader = new VoxelShader();
	}

	public void Prepare(World w, ViewMatrix pov, ProjectionMatrix proj, boolean wireframe)
	{
		if (!wireframe)
		{
			glEnable(GL_CULL_FACE);
		} else {
			glDisable(GL_CULL_FACE);
		}
		glPolygonMode(GL_FRONT_AND_BACK, wireframe ? GL_LINE : GL_FILL);
		// Set shader variables
		shader.setSun(w.getSun());
		shader.setVwMat(pov);
		shader.setPrMat(proj);
		shader.loadFrameUniforms();
		shader.start();
	}

	private Vector3f position = new Vector3f();

	public void Render(Chunk c, int x, int z)
	{
		position.x = x * CHUNK_SIZE * VOXEL_SIZE;
		position.z = z * CHUNK_SIZE * VOXEL_SIZE;
		shader.getMlMat().Transform(position, 0, 0, 0, Vector3f.oneVec);
		shader.loadUniforms();
		glBindVertexArray(c.getMesh().getVaoID());
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		glDrawElements(GL_TRIANGLES, c.getMesh().getVertexCount(), GL_UNSIGNED_INT, 0);
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
		glBindVertexArray(0);
	}

	public void Clean()
	{
		shader.stop();
	}
}
