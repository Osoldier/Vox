package me.soldier.vox.rendering;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import me.soldier.math.ProjectionMatrix;
import me.soldier.math.Vector3f;
import me.soldier.vox.ui.UIElement;

/**
 * Created by Thomas on 30 janv. 2016
 */
public class UIRenderer
{
	private UIShader shader;
	
	public UIRenderer()
	{
		shader = new UIShader();
	}

	public void render(ProjectionMatrix pr, UIElement element)
	{
		glDisable(GL_CULL_FACE);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		shader.start();
		shader.setPrMatrix(pr);
		shader.loadFrameUniforms();
		shader.getMlMatrix().Transform(new Vector3f(element.getPosition().x, element.getPosition().y, 0), 0, 0, 0, new Vector3f(element.getSize(), element.getSize(), element.getSize()));
		element.getTexture().bind();
		shader.loadUniforms();
		glBindVertexArray(element.getMesh());
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(2);
		glDrawArrays(GL_TRIANGLES, 0, 6);
		glDisableVertexAttribArray(2);
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
		shader.stop();
	}
}
