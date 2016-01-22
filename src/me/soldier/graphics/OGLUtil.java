package me.soldier.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.*;
import java.util.*;

/**
 * Created by Thomas on 19 janv. 2016
 */
public class OGLUtil
{

	public static Model createMeshVAO(float[] pos, float[] normals, float[] color, int[] indices)
	{
		int vaoID = generateVAO();
		setAttributeData(0, 3, pos);
		setAttributeData(1, 3, normals);
		setAttributeData(2, 3, color);
		setIndicesData(indices);
		releaseVAO();
		return new Model(vaoID, indices.length);
	}

	public static int createQuadVAO(float[] pos)
	{
		int vaoID = generateVAO();
		setAttributeData(0, 2, pos);
		setAttributeData(2, 2, new float[] { 0, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0 });
		releaseVAO();
		return vaoID;
	}

	public static ByteBuffer createByteBuffer(byte[] array)
	{
		ByteBuffer result = ByteBuffer.allocateDirect(array.length).order(ByteOrder.nativeOrder());
		result.put(array).flip();
		return result;
	}

	public static FloatBuffer createFloatBuffer(float[] array)
	{
		FloatBuffer result = ByteBuffer.allocateDirect(array.length << 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
		result.put(array).flip();
		return result;
	}

	public static IntBuffer createIntBuffer(int[] array)
	{
		IntBuffer result = ByteBuffer.allocateDirect(array.length << 2).order(ByteOrder.nativeOrder()).asIntBuffer();
		result.put(array).flip();
		return result;
	}

	public static float[] toFloatArray(ArrayList<Float> e)
	{
		float[] floatArray = new float[e.size()];
		int i = 0;

		for (Float f : e)
		{
			floatArray[i++] = (f != null ? f : Float.NaN);
		}

		return floatArray;
	}

	private static int generateVAO()
	{
		int vaoID = glGenVertexArrays();
		glBindVertexArray(vaoID);
		return vaoID;
	}

	private static void setAttributeData(int attributeNumber, int size, float[] data)
	{
		int vboID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, createFloatBuffer(data), GL_STATIC_DRAW);
		glVertexAttribPointer(attributeNumber, size, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	private static void setIndicesData(int[] indices)
	{
		int vboID = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboID);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, createIntBuffer(indices), GL_STATIC_DRAW);
	}

	private static void releaseVAO()
	{
		glBindVertexArray(0);
	}
}
