package me.soldier.vox.core;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created by Thomas on 19 janv. 2016
 */
public class OSCompatibility
{
	private static boolean Linux, OSX, Windows;

	public static void Init()
	{
		String system = System.getProperty("os.name").toLowerCase();
		if (system.indexOf("mac") >= 0)
		{
			OSX = true;
		} else if(system.indexOf("nux") >= 0 | system.indexOf("nix") >= 0 | system.indexOf("aix") >= 0) {
			Linux = true;
		} else if(system.indexOf("win") >= 0) {
			Windows = true;
		}
	}

	public static void GLFWSpecifics()
	{
		if (OSX)
		{
			glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
			glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
			glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
			glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		}
	}

	public static void OpenGLSpecifics()
	{
	}
}
