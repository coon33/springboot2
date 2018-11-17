package org.coonchen.fk.validator;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.LoggerFactory;


public class RegexFactory {
	private static Map<String, RegexExecutor> regexFactory = new ConcurrentHashMap<>();

	public static RegexExecutor getRegexExecutor(String rule) {
		try {
			RegexExecutor regexExecutor = regexFactory.get(rule);
			if (regexExecutor != null)
				return regexExecutor;
			regexExecutor = new RegexExecutor(rule) {};
			regexFactory.put(rule, regexExecutor);
			return regexExecutor;
		} catch (Exception e) {
			LoggerFactory.getLogger(RegexFactory.class).error(e.getMessage());
			return null;
		}
	}
}
