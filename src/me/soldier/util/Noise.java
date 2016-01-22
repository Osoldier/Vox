package me.soldier.util;

public class Noise
{
	public static double[] perlinNoise(int width, int height, double exponent)
	{
		int[] p = new int[width * height];
		double[] result = new double[width * height];
		for (int i = 0; i < p.length / 2; i++)
			p[i] = p[i + p.length / 2] = (int) (Math.random() * p.length / 2);// permutation[i];

		for (int i = 0; i < width; i++)
		{
			for (int j = 0; j < height; j++)
			{
				double x = i * exponent / width; // FIND RELATIVE X,Y,Z
				double y = j * exponent / height; // OF POINT IN CUBE.
				int X = (int) Math.floor(x) & 255; // FIND UNIT CUBE THAT
				int Y = (int) Math.floor(y) & 255; // CONTAINS POINT.
				int Z = 0;
				x -= Math.floor(x); // FIND RELATIVE X,Y,Z
				y -= Math.floor(y); // OF POINT IN CUBE.
				double u = fade(x); // COMPUTE FADE CURVES
				double v = fade(y); // FOR EACH OF X,Y,Z.
				double w = fade(Z);
				int A = p[X] + Y, AA = p[A] + Z, AB = p[A + 1] + Z, // HASH
																	// COORDINATES
																	// OF
				B = p[X + 1] + Y, BA = p[B] + Z, BB = p[B + 1] + Z; // THE 8
																	// CUBE
																	// CORNERS,

				result[j + i * width] = lerp(w, lerp(v, lerp(u, grad(p[AA], x, y, Z), // AND
																						// ADD
						grad(p[BA], x - 1, y, Z)), // BLENDED
						lerp(u, grad(p[AB], x, y - 1, Z), // RESULTS
								grad(p[BB], x - 1, y - 1, Z))),// FROM 8
						lerp(v, lerp(u, grad(p[AA + 1], x, y, Z - 1), // CORNERS
								grad(p[BA + 1], x - 1, y, Z - 1)), // OF CUBE
								lerp(u, grad(p[AB + 1], x, y - 1, Z - 1), grad(p[BB + 1], x - 1, y - 1, Z - 1))));
			}
		}
		return result;
	}

	public static double[] whiteNoise(int width, int height)
	{
		double[] result = new double[width * height];
		for (int i = 0; i < width * height; i++)
			result[i] = Math.random();
		return result;
	}

	private static double fade(double t)
	{
		return t * t * t * (t * (t * 6 - 15) + 10);
	}

	private static double lerp(double t, double a, double b)
	{
		return a + t * (b - a);
	}

	private static double grad(int hash, double x, double y, double z)
	{
		int h = hash & 15; // CONVERT LO 4 BITS OF HASH CODE
		double u = h < 8 ? x : y, // INTO 12 GRADIENT DIRECTIONS.
		v = h < 4 ? y : h == 12 || h == 14 ? x : z;
		return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
	}

}