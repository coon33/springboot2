package com.thunisoft.core.controller;

import org.coonchen.fk.controller.BasicController;
import org.coonchen.fk.log.LogFactory;
import org.coonchen.fk.util.FtpUtils;
import org.coonchen.fk.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName DemoController
 * @Description Demo类
 * @Author chenwei
 * @Date 2018/11/22
 */
@Controller
public class DemoController extends BasicController {
	
	private LogFactory logFactory  = LogFactory.getInstance(DemoController.class);
	
	@RequestMapping("admin/demo/toOneUpload.html")
	public Object toOneUpload() {
		return "admin/demo/oneUpload";
	}
	
	@RequestMapping("admin/demo/oneUpload.do")
	@ResponseBody
	public Object oneUpload(@RequestParam("file") MultipartFile file) {
		String nickname = paramString("nickname");
		try {
			byte[] fileByte = file.getBytes();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("admin/demo/toMoreUpload.html")
	public Object toMoreUpload() {
		return "admin/demo/moreUpload";
	}
	
	@RequestMapping("admin/demo/moreUpload.do")
	@ResponseBody
	public Object moreUpload(HttpServletRequest request) {
		
		String nickname = paramString("nickname");
		List<MultipartFile> files = ((MultipartHttpServletRequest) request)
				.getFiles("file");

		
		Map<String,Object> map = new HashMap<>();
		map.put("success",true);
		map.put("msg","ok");
		return map;
	}
	
	@RequestMapping("admin/demo/toFtpUpload.html")
	public Object toFtpUpload() {
		return "admin/demo/moreUpload";
	}
	
	@RequestMapping("admin/demo/ftpUpload.do")
	@ResponseBody
	public Object ftpUpload(@RequestParam("file") MultipartFile file) {
		Map<String,Object> map = new HashMap<>();
		InputStream inputStream = null;
		try {
			inputStream = file.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		FtpUtils ftpUtil = new FtpUtils();
		ftpUtil.setFtpValues("218.61.208.7", "Adley", "anywide", "/", 21, false, 0,true);
		String statusMsg = ftpUtil.connectToFtpServer();
		if(StringUtils.isNotEmpty(statusMsg)){
			logFactory.error(statusMsg);
			map.put("success",false);
			map.put("msg",statusMsg);
			return map;
		}
		boolean upload = ftpUtil.put(inputStream,"c:/", false, "image");
		if(!upload){
			logFactory.error("上传失败");
			map.put("success",false);
			map.put("msg",statusMsg);
			return map;
		}
		
		map.put("success",true);
		map.put("msg","");
		return map;
	}
}
