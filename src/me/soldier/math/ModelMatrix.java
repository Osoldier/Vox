package me.soldier.math;

/**
 * Created by Thomas on 19 janv. 2016
 */
public class ModelMatrix extends Matrix4f
{

	public ModelMatrix()
	{
		this.Identity();
	}

	public void Transform(Vector3f position, float anglex, float angley, float anglez, Vector3f scale)
	{
		this.Identity();
		this.translate(position);
		if (anglex != 0)
			this.Rotate(anglex, 1, 0, 0);
		if (angley != 0)
			this.Rotate(angley, 0, 1, 0);
		if (anglez != 0)
			this.Rotate(anglez, 0, 0, 1);
		this.Scale(scale);
	}

	public void Rotate(float angle, float x, float y, float z)
	{
		this.multiply(rotate(angle, x, y, z));
	}

	public void Translate(Vector3f position)
	{
		translate(position);
	}

	public void Scale(Vector3f scale)
	{
		this.multiply(scale(scale));
	}

}
