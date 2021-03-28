package com.micro.standard.module.common.enums;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class AppEnums {

	@Getter
	@AllArgsConstructor
	public enum YesOrNo {
		YES("Y", "是"), NO("N", "否");

		public static final Set<String> CODES = Arrays.stream(YesOrNo.values()).map(YesOrNo::getCode)
				.collect(Collectors.toSet());

		private String code;
		private String name;

		public static boolean valid(String code) {
			return CODES.contains(code);
		}

		public static boolean validOrNull(String code) {
			return StringUtils.isBlank(code) || CODES.contains(code);
		}

		public static boolean equals(YesOrNo enums, String code) {
			return StringUtils.equalsIgnoreCase(enums.getCode(), code);
		}

		public static boolean isYes(String code) {
			return StringUtils.equalsIgnoreCase(YES.getCode(), code);
		}

		public static boolean isN(String code) {
			return StringUtils.equalsIgnoreCase(NO.getCode(), code);
		}
	}

	@Getter
	@AllArgsConstructor
	public enum Gender {
		M("M", "男"), F("F", "女");

		public static final Set<String> CODES = Arrays.stream(Gender.values()).map(Gender::getCode)
				.collect(Collectors.toSet());

		private String code;
		private String name;

		public static boolean valid(String code) {
			return CODES.contains(code);
		}

		public static boolean validOrNull(String code) {
			return StringUtils.isBlank(code) || CODES.contains(code);
		}

		public static boolean equals(Gender enums, String code) {
			return StringUtils.equalsIgnoreCase(enums.getCode(), code);
		}
	}

}
