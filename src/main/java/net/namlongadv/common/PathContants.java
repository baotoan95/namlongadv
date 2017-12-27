package net.namlongadv.common;

import java.io.File;

public class PathContants {
	private static String rootPath = System.getProperty("catalina.home");

	public static final String UPLOAD_PATH = rootPath + File.separator + "temp";
}
