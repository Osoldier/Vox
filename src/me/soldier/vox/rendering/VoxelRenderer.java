package me.soldier.vox.rendering;

import static me.soldier.vox.voxels.Chunk.CHUNK_SIZE;
import static me.soldier.vox.voxels.Voxel.VOXEL_SIZE;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import me.soldier.graphics.Model;
import me.soldier.math.ProjectionMatrix;
import me.soldier.math.Vector3f;
import me.soldier.math.ViewMatrix;
import me.soldier.vox.voxels.Chunk;

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

	public void Prepare(ViewMatrix pov, ProjectionMatrix proj)
	{
		glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
		// Set shader variables
		shader.setVwMat(pov);
		shader.setPrMat(proj);
		shader.loadFrameUniforms();
		shader.start();
	}

	private Vector3f position = new Vector3f();
	public void Render(Chunk c, int x, int y, int z)
	{
		position.x = x * CHUNK_SIZE * VOXEL_SIZE;
		position.y = y * CHUNK_SIZE * VOXEL_SIZE;
		position.z = z * CHUNK_SIZE * VOXEL_SIZE;
		shader.getMlMat().Transform(position, 0, 0, 0, Vector3f.oneVec);
		shader.loadUniforms();
		for (Model m : c.getMeshes())
		{
			glBindVertexArray(m.getVaoID());
			glEnableVertexAttribArray(0);
			glEnableVertexAttribArray(1);
			glEnableVertexAttribArray(2);
			glDrawElements(GL_TRIANGLES, m.getVertexCount(), GL_UNSIGNED_INT, 0);
			glDisableVertexAttribArray(0);
			glDisableVertexAttribArray(1);
			glDisableVertexAttribArray(2);
			glBindVertexArray(0);
		}
	}
	
	public void Clean() {
		shader.stop();
	}
}
