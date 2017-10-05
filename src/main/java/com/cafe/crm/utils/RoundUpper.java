package com.cafe.crm.utils;

/**Класс предназначенный для округления всех необходимых значений к сущности клиента. Округление происходит по той же
 * формуле что и на странице закрытия смены. Данный функционал следует использовать, когда нужно отобразить статистику
 * по клиентам, обслуживаемым на округлённых столах*/

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
