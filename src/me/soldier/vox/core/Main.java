package me.soldier.vox.core;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.*;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

/**
 * Created by Thomas on 19 janv. 2016
 */
public class Main
{

	public static int width = 1200;
	public static int height = width * 9 / 16;

	public static int pix_width = 800;
	public static int pix_height = 450;

	public static boolean running = false;

	public static long window;
	private GLFWKeyCallback keyCallback;
	private GLFWWindowSizeCallback sizeCallback;
	private GLFWMouseButtonCallback mouseCallback;

	private Engine game;

	public void start()
	{
		running = true;
		OSCompatibility.Init();
		run();
	}

	private void init()
	{
		if (glfwInit() != GL_TRUE)
		{
			System.err.println("Could not initialize GLFW!");
			return;
		}

		glfwWindowHint(GLFW_SAMPLES, 4);
		OSCompatibility.GLFWSpecifics();
		window = glfwCreateWindow(width, height, "Voxel Engine", NULL, NULL);
		if (window == NULL)
		{
			System.err.println("Could not create GLFW window!");
			return;
		}
		//Compatibility for screen with 1:X | X =/= 1 ratio
		ByteBuffer FBW = BufferUtils.createByteBuffer(4), FBH = BufferUtils.createByteBuffer(4);
		glfwGetFramebufferSize(window, FBW, FBH);
		pix_width = FBW.getInt(0);
		pix_height = FBH.getInt(0);
		
		InitWindow();
		InitGL();
	}

	public void run()
	{
		init();
		game = new Engine();

		long lastTime = System.nanoTime();
		double delta = 0.0;
		//Amout of nanoseconds in 1/60th of a second
		double ns = 1000000000.0 / 60.0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while (running)
		{
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			//if delta >= than one then the amount of time >= 1/60th of a second
			if (delta >= 1.0)
			{
				update();
				updates++;
				delta--;
			}
			render();
			frames++;
			//If a second has passed, we print the stats
			if (System.currentTimeMillis() - timer > 1000)
			{
				timer += 1000;
				System.out.println(updates + " ups, " + frames + " fps");
				updates = 0;
				frames = 0;
			}
			if (glfwWindowShouldClose(window) == GL_TRUE)
				running = false;
		}
		keyCallback.release();
		sizeCallback.release();
		mouseCallback.release();
		glfwDestroyWindow(window);
		glfwTerminate();
	}

	private void render()
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		game.Render();
		int error = glGetError();
		if (error != GL_NO_ERROR)
			System.out.println("Error " + error);
		glfwSwapBuffers(window);
	}

	private void update()
	{
		game.Update();
		glfwPollEvents();
	}

	private void InitGL()
	{
		GL.createCapabilities();
		System.out.println("OpenGL: " + glGetString(GL_VERSION));
		glEnable(GL13.GL_MULTISAMPLE);
		glEnable(GL_DEPTH_TEST);
		glViewport(0, 0, pix_width, pix_height);
	}

	private void InitWindow()
	{

		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		//Center window on the screen
		glfwSetWindowPos(window, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);
		//Set window events callbacks
		glfwSetKeyCallback(window, keyCallback = new Input());
		glfwSetWindowSizeCallback(window, sizeCallback = new ResizeHandler());
		glfwSetMouseButtonCallback(window, mouseCallback = new MouseHandler());
		
		glfwMakeContextCurrent(window);
		glfwSwapInterval(0);
		glfwShowWindow(window);
	}

	public static void main(String[] args)
	{
		new Main().start();
	}
}
