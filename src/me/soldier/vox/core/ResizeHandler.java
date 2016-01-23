package me.soldier.vox.core;

import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL11;

/**
 * Created by Thomas on 19 janv. 2016
 */
public class ResizeHandler extends GLFWWindowSizeCallback {

	public ResizeHandler() {
		
	}
	
	@Override
	public void invoke(long window, int width, int height) {
		Main.width = width;
		Main.height = height;
		GL11.glViewport(0, 0, width, height);
	}
}
