package me.soldier.graphics;

/**
 * Created by Thomas on 19 janv. 2016
 */
public class Model
{
	private int vaoID, vertexCount;

	public Model(int vaoID, int vertexCount)
	{
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
	}

	public int getVaoID()
	{
		return vaoID;
	}

	public void setVaoID(int vaoID)
	{
		this.vaoID = vaoID;
	}

	public int getVertexCount()
	{
		return vertexCount;
	}

	public void setVertexCount(int vertexCount)
	{
		this.vertexCount = vertexCount;
	}

}
