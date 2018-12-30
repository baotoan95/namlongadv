package net.namlongadv.constant;

import java.io.File;

public class PathContants {
	private PathContants() {
		// Prevent create this instance
	}
	
	public static final String ROOT_PATH = System.getProperty("catalina.home");
	public static final String UPLOAD_PATH = ROOT_PATH + File.separator + "temp";
	
	public static final String ADVERT = "adv";
	public static final String ADVERT_LIST = "advs";
	
}
