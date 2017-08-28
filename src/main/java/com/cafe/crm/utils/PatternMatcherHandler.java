package com.cafe.crm.utils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternMatcherHandler {

	private static Pattern pattern;
	private static Matcher matcher;

	public static boolean matchPhone(String incomeString) {
		pattern = Pattern.compile(PatternStorage.PHONE);
		matcher = pattern.matcher(incomeString);
		return matcher.find();
	}

	public static boolean matchEmail(String incomeString) {
		pattern = Pattern.compile(PatternStorage.EMAIL);
		matcher = pattern.matcher(incomeString);
		return matcher.find();
	}
}
