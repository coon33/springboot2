package com.allwell.core.exception;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {


	/**
	 * 全局异常捕捉处理
	 * @param ex
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(value = Exception.class)
	public Map errorHandler(Exception ex) {

		LoggerFactory.getLogger(GlobalExceptionHandler.class).error(ex.getMessage());

		Map map = new HashMap();
		map.put("code", 500);
		map.put("msg", ex.getMessage());
		return map;
	}

	/**
	 * 拦截捕捉自定义异常 OperationException.class
	 * @param ex
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(value = OperationException.class)
	public Map myErrorHandler(OperationException ex) {

		LoggerFactory.getLogger(GlobalExceptionHandler.class).error(ex.getMsg());

		Map map = new HashMap();
		map.put("code", ex.getCode());
		map.put("msg", ex.getMsg());
		return map;
	}

/*	@ExceptionHandler(value = OperationException.class)
	public ModelAndView myErrorHandler(OperationException ex) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("error");
		modelAndView.addObject("code", ex.getCode());
		modelAndView.addObject("msg", ex.getMsg());
		return modelAndView;
	}*/

}
