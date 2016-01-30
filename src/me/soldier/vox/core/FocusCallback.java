package me.soldier.vox.core;

import org.lwjgl.glfw.GLFWWindowFocusCallback;
import org.lwjgl.opengl.GL11;

/**
 * Created by Thomas on 30 janv. 2016
 */
public class FocusCallback extends GLFWWindowFocusCallback
{

	public static boolean FOCUSED = false;
	
	@Override
	public void invoke(long window, int focused)
	{
		FOCUSED = focused == GL11.GL_TRUE;
	}

}
