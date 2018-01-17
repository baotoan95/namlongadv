package net.namlongadv.utils;

import java.util.Date;

public class StringUtils {
	public static String standardize(String str) {
        str = str.trim();
        str = str.replaceAll("\\s+", " ");
        return str;
    }
	
	public static String randomCode() {
		String code = String.valueOf(new Date().getTime());
		return code.substring(9, code.length());
	}
}
