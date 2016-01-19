package me.soldier.util;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;

public class FileReader
{

	public static List<String> readFile(String paths)
	{
		Path path = Paths.get(paths);
		try
		{
			return Files.readAllLines(path, StandardCharsets.UTF_8);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

}
