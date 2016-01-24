package me.soldier.vox.core;

import static org.lwjgl.glfw.GLFW.*;

import java.nio.*;
import java.util.concurrent.*;

import me.soldier.util.*;
import me.soldier.vox.console.*;
import me.soldier.vox.rendering.*;
import me.soldier.vox.voxels.*;

import org.lwjgl.*;

/**
 * Created by Thomas on 19 janv. 2016
 */
public class Engine
{

	private Camera pov;
	private RayCaster rayCaster;
	private Renderer renderer;
	private World world;
	private FrmConsole console;
	private BlockingQueue<Runnable> todoList;

	public Engine()
	{
		renderer = new Renderer(Main.pix_width, Main.pix_height);
		pov = new Camera(1920, 200, 1920);
		this.rayCaster = new RayCaster(renderer.getPerspective(), pov.vw_matrix);
		setTodoList(new LinkedBlockingQueue<Runnable>());
		console = new FrmConsole(this);
	}

	public void Render()
	{
		pov.lookThrough();
		if (world != null)
			renderer.RenderScene(world, pov);
	}

	public static DoubleBuffer x = BufferUtils.createDoubleBuffer(1);
	public static DoubleBuffer y = BufferUtils.createDoubleBuffer(1);
	double newX, newY, prevX, prevY;
	private float speed = 1f;

	public void Update()
	{
		try
		{
			if (!todoList.isEmpty())
			{
				todoList.take().run();
			}
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		glfwGetCursorPos(Main.window, x, y);
		rayCaster.Cast((float) x.get(0), (float) y.get(0));
		if (Input.isKeyDown(GLFW_KEY_W) || Input.isKeyDown(GLFW_KEY_Z))
		{
			pov.Forward(speed);
		}
		if (Input.isKeyDown(GLFW_KEY_S))
		{
			pov.Backwards(speed);
		}
		if (Input.isKeyDown(GLFW_KEY_A) || Input.isKeyDown(GLFW_KEY_Q))
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

	public void CleanUp()
	{
		console.dispose();
	}
	
	public Camera getPov()
	{
		return pov;
	}

	public void setPov(Camera pov)
	{
		this.pov = pov;
	}

	public Renderer getRenderer()
	{
		return renderer;
	}

	public void setRenderer(Renderer renderer)
	{
		this.renderer = renderer;
	}

	public World getWorld()
	{
		return world;
	}

	public void setWorld(World world)
	{
		this.world = world;
	}

	public BlockingQueue<Runnable> getTodoList()
	{
		return todoList;
	}

	public void setTodoList(BlockingQueue<Runnable> todoList)
	{
		this.todoList = todoList;
	}
}
