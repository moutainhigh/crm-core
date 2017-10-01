package com.cafe.crm.utils;

/**
 * Created by User on 28.09.2017.
 */
public class RoundUpper {
	public static double roundDouble(double var) {
		double result;
		Long varLong = (long) var;
		Long remainder = varLong % 100;
		if (remainder > 50) {
			if (remainder >= 75) {
				result = (double) (varLong - remainder) + 100;
			} else {
				result = (double) (varLong - remainder) + 50;
			}
		} else if (remainder < 50) {
			if (remainder >= 25) {
				result = (double) (varLong - remainder) + 50;
			} else {
				result = (double) (varLong - remainder);
			}
		} else {
			result = (double) varLong;
		}
		return result;
	}
}
