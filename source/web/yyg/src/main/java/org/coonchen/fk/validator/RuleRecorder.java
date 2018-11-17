package org.coonchen.fk.validator;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.coonchen.fk.validator.impl.GroupRuleOperator;
import org.coonchen.fk.validator.impl.StringRuleOperator;




public class RuleRecorder {
	public static Map<String,Pattern> mapStringPattern = new HashMap<>();//正则表达式

	public static Map<Pattern,GroupRuleOperator> mapGroupOperator = new HashMap<>();
	public static Map<String,StringRuleOperator> mapStringOperator = new HashMap<>();
}
