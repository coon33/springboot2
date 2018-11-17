package org.coonchen.fk.validator;


import java.util.regex.Pattern;

import org.slf4j.LoggerFactory;

public abstract class RegexExecutor {
	private Pattern pattern;
	public RegexExecutor(String rule) {
		pattern = RuleRecorder.mapStringPattern.get(rule);
		validateRegex(rule);
	}
	
	private void validateRegex(String regex) {
		if(pattern==null || regex==null || "".equals(regex))
			LoggerFactory.getLogger(RegexExecutor.class).error("validator regex is error");
	}
	
	public boolean validator(Object val) {
		if(val==null || "".equals(val.toString())) return true;
		return pattern.matcher(val.toString()).find();
	}
}
