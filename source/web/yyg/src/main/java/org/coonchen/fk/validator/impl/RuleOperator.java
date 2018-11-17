package org.coonchen.fk.validator.impl;


import java.util.regex.Matcher;

public abstract class RuleOperator {

	protected String ruleKey;

	public String getRuleKey() {
		return ruleKey;
	}

	public void setRuleKey(String ruleKey) {
		this.ruleKey = ruleKey;
	}

	public RuleOperator(String rule) {
		this.ruleKey = rule;
	}

	public abstract String validator(Object obj);

	public abstract String validator(Object obj, Matcher matcher);
}
