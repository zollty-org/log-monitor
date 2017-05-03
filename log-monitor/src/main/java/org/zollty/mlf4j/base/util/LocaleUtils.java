/* 
 * == Mlf4j (Monitoring Logging Facade for Java) ==
 * ============为监控而生的通用日志工具库===========
 * Version 1.0
 * 
 * Licensed under the Apache License, Version 2.0 (the "License").
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
package org.zollty.mlf4j.base.util;

import java.util.Locale;

import org.zollty.framework.util.MvcUtils;


public class LocaleUtils {

	private final static Locale DEFAULT_LOCALE = Locale.US;

	public final static String IETF_SEPARATOR = "-";

	public final static String SEPARATOR = "_";

	public final static String EMPTY_STRING = "";

	public static Locale toLocale(String language) {
		if (MvcUtils.StringUtil.isNotEmpty(language)) {
			return langToLocale(language, SEPARATOR);
		}
		return DEFAULT_LOCALE;
	}

	public static Locale langToLocale(String lang, String separator) {
		if (MvcUtils.StringUtil.isNullOrEmpty(lang)) {
			return DEFAULT_LOCALE;
		}
		String language = EMPTY_STRING;
		String country = EMPTY_STRING;
		String variant = EMPTY_STRING;

		int i1 = lang.indexOf(separator);
		if (i1 < 0) {
			language = lang;
		} else {
			language = lang.substring(0, i1);
			++i1;
			int i2 = lang.indexOf(separator, i1);
			if (i2 < 0) {
				country = lang.substring(i1);
			} else {
				country = lang.substring(i1, i2);
				variant = lang.substring(i2 + 1);
			}
		}

		if (language.length() == 2) {
			language = language.toLowerCase();
		} else {
			language = EMPTY_STRING;
		}

		if (country.length() == 2) {
			country = country.toUpperCase();
		} else {
			country = EMPTY_STRING;
		}

		if ((variant.length() > 0)
				&& ((language.length() == 2) || (country.length() == 2))) {
			variant = variant.toUpperCase();
		} else {
			variant = EMPTY_STRING;
		}

		return new Locale(language, country, variant);
	}
}
