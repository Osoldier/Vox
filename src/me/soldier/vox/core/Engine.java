package me.soldier.vox.core;

import static org.lwjgl.glfw.GLFW.*;

import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;

import me.soldier.util.RayCaster;
import me.soldier.vox.rendering.Renderer;
import me.soldier.vox.voxels.World;

/**
 * Created by Thomas on 19 janv. 2016
 */
public class Engine
{

	private Camera pov;
	private RayCaster rayCaster;
	private Renderer renderer;
	private World world;

	public Engine()
	{
		renderer = new Renderer(Main.pix_width, Main.pix_height);
		pov = new Camera(0, 0, 400);
		world = new World(4, 2, 4);
	}

	public void Render()
	{
		pov.lookThrough();
		renderer.RenderScene(world, pov);
	}

	public static DoubleBuffer x = BufferUtils.createDoubleBuffer(1);
	public static DoubleBuffer y = BufferUtils.createDoubleBuffer(1);
	double newX, newY, prevX, prevY;
	private float speed = 1f;

	public void Update()
	{
		glfwGetCursorPos(Main.window, x, y);
		if (Input.isKeyDown(GLFW_KEY_W))
		{
			pov.Forward(speed );
		}
		if (Input.isKeyDown(GLFW_KEY_S))
		{
			pov.Backwards(speed);
		}
		if (Input.isKeyDown(GLFW_KEY_A))
		{
			pov.Left(speed);
		}
		if (Input.isKeyDown(GLFW_KEY_D))
		{
			pov.Right(speed);
		}
		if (Input.isKeyDown(GLFW_KEY_LEFT_SHIFT))
		{
			pov.Up(speed);
		}
		if (Input.isKeyDown(GLFW_KEY_LEFT_CONTROL))
		{
			pov.Down(speed);
		}

		if (MouseHandler.isButtonDown(0))
		{

			newX = x.get(0);
			newY = y.get(0);

			double deltaX = newX - prevX;
			double deltaY = newY - prevY;

			pov.yaw((float) (deltaX) * 0.1f);
			pov.pitch((float) (deltaY) * 0.1f);

			prevX = newX;
			prevY = newY;
		} else
		{

			prevX = x.get(0);
			prevY = y.get(0);
		}
	}
}
