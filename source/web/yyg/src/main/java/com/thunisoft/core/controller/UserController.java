package com.thunisoft.core.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.thunisoft.core.exception.OperationException;
import org.coonchen.fk.annotation.ValidatorAnnotation;
import org.coonchen.fk.controller.BasicController;
import org.coonchen.fk.log.LogFactory;
import org.coonchen.fk.util.MD5Utils;
import org.coonchen.fk.util.Tools;
import org.coonchen.fk.web.page.PageBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thunisoft.core.bean.User;
import com.thunisoft.core.service.UserService;
import com.thunisoft.core.utils.CommonUtil;

@Controller
public class UserController extends BasicController {

	private LogFactory logFactory  = LogFactory.getInstance(UserController.class);

	@Resource
	private UserService userService;

	/*
	 * @Author coonchen
	 * @Description 查询列表
	 * @Date 2018/11/22
	 * @Param [modelMap]
	 * @return java.lang.Object
	 **/
	@RequestMapping("admin/user/list.html")
	public Object list(ModelMap modelMap) {
		//取得页面参数
		String nickmobile = paramString("nickmobile");
		//取得分页参数
		int pageon = paramInt("pageon", 1);
		int rownum = paramInt("rownum", 10);
		
		//设置分页对象
		PageBean pageBean = new PageBean(pageon, rownum);
		//设置查询参数
		Map<String, Object> mapParam = new HashMap<String, Object>();
		mapParam.put("nickmobile", nickmobile);
		mapParam.put("orderby", "addtime");
		mapParam.put("sort", "desc");
		
		//执行查询操作
		Map<String, Object> map = userService.selectPageList(mapParam, pageBean);
		
		//返回查询结果
		modelMap.putAll(map);
		
		//跳转到页面
		return "admin/user/list";
	}
	@RequestMapping("admin/user/toEdit.html")
	public Object toEdit(ModelMap modelMap) {
		//取得页面参数
		int userid = paramInt("userid", 0);
		
		if (userid != 0) {
			//执行查询操作
			User user = userService.selectByPrimaryKey(userid);
			//返回查询结果
			modelMap.put("user", user);
		}
		//跳转到页面
		return "admin/user/edit";
	}

	@RequestMapping("admin/user/toView.html")
	public Object toView(ModelMap modelMap) {
		//取得页面参数
		int userid = paramInt("id", 0);
		
		if (userid != 0) {
			//执行查询操作
			User user = userService.selectByPrimaryKey(userid);
			//返回查询结果
			modelMap.put("user", user);
		}
		//跳转到页面
		return "admin/user/view";
	}

	@RequestMapping("admin/user/toPwd.html")
	public Object toPwd(ModelMap modelMap) {
		//取得页面参数
		int id = paramInt("id", 0);
		//返回页面参数
		modelMap.put("id", id);
		//跳转到页面
		return "admin/user/pwd";
	}

	@RequestMapping("admin/user/save.do")
	@ResponseBody
	@ValidatorAnnotation(name = "mobile", explain = "手机号", rule = "required&number&size:11")
	@ValidatorAnnotation(name = "nickname", explain = "用户昵称", rule = "required&maxsize:16&minsize:2")
	public Object save(ModelMap modelMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		int userid = paramInt("userid", 0);
		String mobile = paramString("mobile");
		String nickname = paramString("nickname");

		User user = new User();
		user.setMobile(mobile);
		if (userid == 0) {
			List<User> lstUser = userService.selectSelective(user);
			if (lstUser != null && !lstUser.isEmpty()) {
				map.put("success", false);
				map.put("msg", "该手机号码已经注册");
				return map;
			}
		}

		String password = "123456";
		user.setUserid(userid);
		user.setNickname(nickname);
		String ipaddress = Tools.getOnlineip(getRequest());
		user.setIpaddress(ipaddress);
		if (userid == 0) {
			String securekey = Tools.getCharacterAndNumber(3);
			String encodePassword = MD5Utils.getMd5(MD5Utils.getMd5(password) + securekey);
			user.setSecurekey(securekey);
			user.setPassword(encodePassword);
			user.setAddtime(CommonUtil.sysdateInt()); // 注册时间
			user.setLastlogintime(0);
			user.setLogintime(0); // 登录次数
			user.setSugid(1);
			user.setVisable((short) 0);
		}
		int iR = userService.updateByPrimaryKeySelective(user);

		if (iR > 0)
			map.put("success", true);
		else {
			map.put("success", false);
			map.put("msg", "登录失败");
		}
		return map;
	}

	@RequestMapping("admin/user/del.do")
	@ResponseBody
	@ValidatorAnnotation(name = "ids", explain = "用户ID", rule = "required")
	public Object del(ModelMap modelMap) {
		String userids = paramString("ids");
		int iR = 0;
		if (userids.indexOf(",") == -1) {
			iR = userService.deleteByPrimaryKey(Integer.parseInt(userids));
		} else {
			iR = userService.deleteByPrimaryKeys(userids.split(","));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);
		if (iR <= 0) {
			map.put("success", false);
			map.put("msg", "删除失败");
		}
		return map;
	}

	@RequestMapping("admin/user/able.do")
	@ResponseBody
	@ValidatorAnnotation(name = "id", explain = "用户ID", rule = "required")
	@ValidatorAnnotation(name = "able", explain = "可用标识", rule = "required&number")
	public Object able() {
		int userid = paramInt("id");
		short able = paramShort("able");
		User record = new User();
		record.setUserid(userid);
		record.setVisable((short) (able == 1 ? 0 : 1));
		int iR = userService.updateByPrimaryKeySelective(record);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);
		if (iR <= 0) {
			map.put("success", false);
			map.put("msg", "更新失败");
		}
		return map;
	}

	@RequestMapping("admin/user/pwd.do")
	@ResponseBody
	@ValidatorAnnotation(name = "id", explain = "用户ID", rule = "required&number")
	@ValidatorAnnotation(name = "oldpwd", explain = "原密码", rule = "required&minsize6&maxsize:16")
	@ValidatorAnnotation(name = "newpwd", explain = "新密码", rule = "required&minsize6&maxsize:16")
	public Object pwd() {
		Map<String, Object> map = new HashMap<String, Object>();
		String oldpwd = paramString("oldpwd");
		String newpwd = paramString("newpwd");
		int id = paramInt("id");
		// 判断旧密码是否正确
		User user = userService.selectByPrimaryKey(id);
		if (user == null) {
			throw new OperationException("未取到该用户信息");
		}
		String encodePassword = MD5Utils.getMd5(MD5Utils.getMd5(oldpwd) + user.getSecurekey());
		if (!encodePassword.equals(user.getPassword())) {
			map.put("success", false);
			map.put("msg", "原密码输入错误");
			return map;
		}

		User record = new User();
		record.setUserid(id);
		String newEncodePassword = MD5Utils.getMd5(MD5Utils.getMd5(newpwd) + user.getSecurekey());
		record.setPassword(newEncodePassword);
		int iR = userService.updateByPrimaryKeySelective(record);
		map.put("success", true);
		if (iR <= 0) {
			map.put("success", false);
			map.put("msg", "修改失败");
		}
		return map;
	}
}
