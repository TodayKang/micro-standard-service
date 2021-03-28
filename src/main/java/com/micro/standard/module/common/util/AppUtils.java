package com.micro.standard.module.common.util;

public class AppUtils {

	/**
	 * 判断2个整型是否相等
	 * 
	 * @param cs1
	 * @param cs2
	 * @return
	 */
	public static boolean equal(Integer cs1, Integer cs2) {
		if (cs1 == null || cs2 == null) {
			return cs1 == null && cs2 == null;
		}

		return cs1.equals(cs2);
	}

	/**
	 * 判断2个长整型是否相等
	 * 
	 * @param cs1
	 * @param cs2
	 * @return
	 */
	public static boolean equal(Long cs1, Long cs2) {
		if (cs1 == null || cs2 == null) {
			return cs1 == null && cs2 == null;
		}

		return cs1.equals(cs2);
	}

}
