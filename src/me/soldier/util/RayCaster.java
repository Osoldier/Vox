package me.soldier.util;

import me.soldier.math.*;
import me.soldier.vox.core.*;

/**
 * Created by Thomas on 19 janv. 2016
 */
public class RayCaster
{

	private Vector3f currentRay;

	private ProjectionMatrix projectionMatrix;
	private ViewMatrix viewMatrix;
	private float mouseX, mouseY;

	public RayCaster(ProjectionMatrix projectionMatrix, ViewMatrix viewMatrix)
	{
		this.viewMatrix = viewMatrix;
		this.projectionMatrix = projectionMatrix;
	}

	/**
	 * Updates the current ray
	 * 
	 * @param x
	 *            the mouse position x coordinate
	 * @param y
	 *            the mouse position y coordinate
	 */
	public void Cast(float x, float y)
	{
		this.mouseX = x;
		this.mouseY = y;
		currentRay = computeRay();
	}

	/**
	 * Calculates the ray
	 * 
	 * @return Vector representing the ray
	 */
	private Vector3f computeRay()
	{
		Vector2f normalizedCoords = getNormalizedCoords(mouseX, mouseY);
		Vector4f clipCoords = new Vector4f(normalizedCoords.x, -normalizedCoords.y, -1f, 1f);
		Vector4f eyeCoords = toCamera(clipCoords);
		Vector3f worldRay = toModel(eyeCoords);
		return worldRay;
	}

	/**
	 * Gets the model position by inverting the view transformation
	 * 
	 * @param viewCoords
	 *            the ray view coordinate
	 * @return Vector representing the actual ray
	 */
	private Vector3f toModel(Vector4f viewCoords)
	{
		Matrix4f invertedView = Matrix4f.invert(viewMatrix, null);
		Vector4f rayWorld = Matrix4f.transform(invertedView, viewCoords, null);
		Vector3f mouseRay = new Vector3f(rayWorld.x, rayWorld.y, rayWorld.z);
		mouseRay.normalize();
		return mouseRay;
	}

	/**
	 * Gets the view position by inverting the projection transformation
	 * 
	 * @param clipCoords
	 *            the ray vector pointing toward -z from mouse (x,y)
	 * @return Vector representing the ray view coordinate
	 */
	private Vector4f toCamera(Vector4f clipCoords)
	{
		Matrix4f invertedProjection = Matrix4f.invert(projectionMatrix, null);
		Vector4f eyeCoords = Matrix4f.transform(invertedProjection, clipCoords, null);
		return new Vector4f(eyeCoords.x, eyeCoords.y, -1f, 0f);
	}

	/**
	 * Normalize mouse position
	 * 
	 * @param x
	 *            absolute mouse x
	 * @param y
	 *            absolute mouse y
	 * @return Vector representing the mouse normalized coords
	 */
	private Vector2f getNormalizedCoords(float x, float y)
	{
		return new Vector2f((2f * x) / Main.pix_width - 1f, (2f * y) / Main.pix_height -1f);
	}

	/**
	 * Check if the current vector intersect with bounding sphere
	 * 
	 * @param c
	 *            center of the sphere
	 * @param o
	 *            origin of the vector (camera position)
	 * @param r
	 *            radius
	 * @return true if there is one or more intersections, false otherwise
	 */
	public boolean collideWithSphere(Vector3f c, Vector3f o, float r)
	{
		float delta = -1;
		Vector3f diff = Vector3f.Sub(c, o);
		diff.y = -diff.y;
		float b = Vector3f.Dot(currentRay, diff);
		float lengthSquared = diff.length() * diff.length();
		float radiusSquared = r * r;
		delta = radiusSquared + (b * b) - lengthSquared;
		return delta >= 0;
	}

	/**
	 * Check if the current ray intersects with a voxel
	 * 
	 * @param position
	 *            position of the voxel to test
	 * @param size
	 *            size of the voxel
	 * @param origin
	 *            origin of the ray
	 * @return true if there is an intersection, false otherwise
	 */
	public boolean collideWithVoxel(Vector3f position, int size, Vector3f origin)
	{
		Vector3f MinBox = Vector3f.Sub(position, new Vector3f(size / 2, size / 2, size / 2));
		Vector3f MaxBox = Vector3f.Add(position, new Vector3f(size / 2, size / 2, size / 2));
		float tmin, tmax, tymin, tymax, tzmin, tzmax;
		if (currentRay.x >= 0)
		{
			tmin = (MinBox.x - origin.x) / currentRay.x;
			tmax = (MaxBox.x - origin.x) / currentRay.x;
		} else
		{
			tmax = (MinBox.x - origin.x) / currentRay.x;
			tmin = (MaxBox.x - origin.x) / currentRay.x;
		}
		if (currentRay.y >= 0)
		{
			tymin = (MinBox.y - origin.y) / currentRay.y;
			tymax = (MaxBox.y - origin.y) / currentRay.y;
		} else
		{
			tymax = (MinBox.y - origin.y) / currentRay.y;
			tymin = (MaxBox.y - origin.y) / currentRay.y;
		}
		if (tmin > tymax || tymin > tmax)
			return false;
		if(tymin > tmin)
			tmin = tymin;
		if(tymax < tmax)
			tmax = tymax;
		if(currentRay.z >= 0) {
			tzmin = (MinBox.z - origin.z) / currentRay.z;
			tzmax = (MaxBox.z - origin.z) / currentRay.z;
		} else {
			tzmax = (MinBox.z - origin.z) / currentRay.z;
			tzmin = (MaxBox.z - origin.z) / currentRay.z;
		}
		if(tmin > tzmax || tzmin > tmax)
			return false;
		if(tzmin > tmin)
			tmin = tzmin;
		if(tzmax < tmax)
			tmax = tzmax;
		return tmin < tmax;
	}

	public Vector3f getCurrentRay()
	{
		return currentRay;
	}
}
