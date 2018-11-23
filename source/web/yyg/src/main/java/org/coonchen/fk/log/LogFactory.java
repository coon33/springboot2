package org.coonchen.fk.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName LogFactory
 * @Description 日志工厂类
 * @Author coonchen
 * @Date 2018/11/23
 */
public class LogFactory {
	
	public static LogFactory logFactory = new LogFactory();
	
	private static Logger logger;
	
	public static LogFactory getInstance(Class clazz){
		if(logger==null)
			logger = LoggerFactory.getLogger(clazz);
		return logFactory;
	}
	
	public void error(String msg){
		logger.error(msg);
	}
	
	public void warn(String msg){
		logger.warn(msg);
	}
	
	public void info(String msg){
		logger.info(msg);
	}
	
	public void debug(String msg){
		logger.debug(msg);
	}
	
	public void trace(String msg){
		logger.trace(msg);
	}
}
