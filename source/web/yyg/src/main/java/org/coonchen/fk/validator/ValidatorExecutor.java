package org.coonchen.fk.validator;


import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.coonchen.fk.utils.ConvertUtil;
import org.coonchen.fk.utils.ScannerUtil;
import org.coonchen.fk.validator.impl.GroupRuleOperator;
import org.coonchen.fk.validator.impl.RuleOperator;

public class ValidatorExecutor {
	
	static {
		startup(RuleOperator.class);
	}
	
	public static void startup(Class clazz) {
		List<Class> classes = ScannerUtil.getAllClassByClass(clazz);
		if(classes!=null && !classes.isEmpty()) {
			for (Class c : classes) {
				try {
					if(((c.getModifiers()&1024)!=1024)&&((c.getModifiers()&16)!=16)&&((c.getModifiers()&512)!=512)&&RuleOperator.class.isAssignableFrom(c)) {
						c.newInstance();
					}
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static String validator(Object value,String explain,String rules) {
		String error = null;
		if(!ConvertUtil.isEmpty(rules)){
			for(String rule : rules.split("&")){
				RuleOperator ruleExecutor = (RuleOperator)RuleRecorder.mapStringOperator.get(rule);
				if(ruleExecutor!=null){
					error = ruleExecutor.validator(value);
				}else{
					for(Entry<Pattern,GroupRuleOperator> entry : RuleRecorder.mapGroupOperator.entrySet()){
						Matcher matcher = entry.getKey().matcher(rule);
						if(matcher.find()){
							error = entry.getValue().validator(value,matcher);
						}
					}
				}
				if(error!=null)return (explain+error);
			}
		}
		return null;
	}

}
