package me.soldier.vox.core;

import static org.lwjgl.glfw.GLFW.*;

import java.nio.DoubleBuffer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import me.soldier.util.RayCaster;
import me.soldier.vox.console.FrmConsole;
import me.soldier.vox.filemanager.FileLoader;
import me.soldier.vox.filemanager.FileSaver;
import me.soldier.vox.rendering.Renderer;
import me.soldier.vox.ui.Crosshair;
import me.soldier.vox.voxels.VoxelType;
import me.soldier.vox.voxels.World;

import org.lwjgl.BufferUtils;

/**
 * Created by Thomas on 19 janv. 2016
 */
public class Engine
{

	private Camera pov;
	private RayCaster rayCaster;
	private Renderer renderer;
	private World world;

	private VoxelType inHand = VoxelType.STONE;
	private BlockingQueue<Runnable> todoList;
	private boolean wireframe = false;
	private Crosshair crosshair;

	private FrmConsole console;
	private FileSaver saver;
	private FileLoader loader;

	public Engine()
	{
		this.renderer = new Renderer(Main.pix_width, Main.pix_height);
		this.pov = new Camera(0, 0, 0);
		this.rayCaster = new RayCaster(renderer.getPerspective(), pov.vw_matrix);
		this.setTodoList(new LinkedBlockingQueue<Runnable>());
		this.console = new FrmConsole(this);
		this.saver = new FileSaver(this);
		this.loader = new FileLoader(this);
		this.crosshair = new Crosshair();
	}

	public void Render()
	{
		pov.lookThrough();
		if (world != null)
			renderer.RenderScene(world, pov, wireframe);
		renderer.RenderUI(crosshair);
	}

	public static DoubleBuffer x = BufferUtils.createDoubleBuffer(1);
	public static DoubleBuffer y = BufferUtils.createDoubleBuffer(1);
	double newX, newY;
	private float speed = 2f;

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
		if (world != null)
			world.Update(rayCaster, pov.position);
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

		if (!Input.isKeyDown(GLFW_KEY_LEFT_ALT) && FocusCallback.FOCUSED)
		{
			glfwSetInputMode(Main.window, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
			double deltaX = x.get(0) - Main.width / 2;
			double deltaY = y.get(0) - Main.height / 2;
			pov.yaw((float) (-deltaX) * 0.1f);
			pov.pitch((float) (-deltaY) * 0.1f);
			glfwSetCursorPos(Main.window, Main.width / 2, Main.height / 2);
		} else {
			glfwSetInputMode(Main.window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
		}
	}

	public void Save(String path, String name)
	{
		saver.writeFile(path, name);
	}

	public void Load(String path)
	{
		this.world = loader.loadFile(path);
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

	public VoxelType getInHand()
	{
		return inHand;
	}

	public void setInHand(VoxelType inHand)
	{
		this.inHand = inHand;
	}

	public boolean isWireframe()
	{
		return wireframe;
	}

	public void setWireframe(boolean wireframe)
	{
		this.wireframe = wireframe;
	}
}
