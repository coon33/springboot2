package com.thunisoft.core.exception;

import com.thunisoft.core.controller.UserController;
import org.coonchen.fk.log.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private LogFactory logFactory  = LogFactory.getInstance(GlobalExceptionHandler.class);
	/**
	 * 全局异常捕捉处理
	 * @param ex
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(value = Exception.class)
	public Map errorHandler(Exception ex) {
		
		logFactory.error(ex.getMessage());
		
		Map<String,Object> map = new HashMap<>();
		map.put("success",false);
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
	public Map operationHandler(OperationException ex) {
		
		logFactory.error(ex.getMsg());

		Map<String,Object> map = new HashMap<>();
		map.put("success",false);
		map.put("code", ex.getCode());
		map.put("msg", ex.getMsg());
		return map;
	}
	
	/**
	 * 拦截捕捉自定义异常 MultipartException.class
	 * @param ex
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(MultipartException.class)
	public Map multipartHandle(MultipartException ex) {
		
		logFactory.error(ex.getCause().getMessage());
		
		Map<String,Object> map = new HashMap<>();
		map.put("success",false);
		map.put("code", "500");
		map.put("msg", ex.getCause().getMessage());
		
		//FIXME 前段收不到异常信息，springboot咱没提供解决方案，等待研究
		//COONCHEN 待解决
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
