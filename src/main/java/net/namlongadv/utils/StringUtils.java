package net.namlongadv.utils;

import java.text.Normalizer;
import java.util.Date;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
	
	public static String convertStringIgnoreUtf8(String str) {
		if(str == null) return null;
		try {
			String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
			Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
			return pattern.matcher(temp).replaceAll("").toLowerCase().replaceAll("đ", "d");
		} catch (Exception e) {
			log.error("Error: {}", e);
		}
		return "";
	}
}
