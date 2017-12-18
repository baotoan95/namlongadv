package net.namlongadv.utils;

public class FileUtils {
	public static String getExtensions(String path) {
		return path.substring(path.lastIndexOf(".") + 1, path.length());
	}
}
