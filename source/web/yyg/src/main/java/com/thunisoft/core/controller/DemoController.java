package com.thunisoft.core.controller;

import com.thunisoft.core.bean.User;
import com.thunisoft.core.utils.CommonUtil;
import org.coonchen.fk.annotation.ValidatorAnnotation;
import org.coonchen.fk.controller.BasicController;
import org.coonchen.fk.utils.MD5Util;
import org.coonchen.fk.utils.Tool;
import org.coonchen.fk.web.page.PageBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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
	
	@RequestMapping("admin/demo/error.html")
	public Object error() {
		return "admin/demo/error";
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
	
}
