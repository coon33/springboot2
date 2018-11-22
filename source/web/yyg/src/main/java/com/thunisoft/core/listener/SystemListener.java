package com.thunisoft.core.listener;

import org.coonchen.fk.web.page.PageStyle;
import org.coonchen.fk.web.page.PageStyleBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
@Component
@Order(value=1)
public class SystemListener implements CommandLineRunner {
	
	@Override
	public void run(String... arg0) throws Exception {
		//启用后台验证框架
		//ValidatorExecutor.startup(DemoRule.class);
		//启用缓存
		//CacheManager.getInstance();
		//初始化后台分页样式
		PageStyleBean pcont = new PageStyleBean();
		pcont.setStartPage("<div class=\"dataTables_paginate\">");
		pcont.setFirstPage("<a class=\"paginate_button\" href=\"~content_mark\">首页</a>");
		pcont.setPreviousPage("<a class=\"paginate_button\" href=\"~content_mark\">上一页</a>");
		pcont.setOtherPage("<a class=\"paginate_button\" href=\"~content_mark\">~p</a>");
		pcont.setCurrentPage("<a class=\"paginate_button current\" href=\"javascript:;\">~p</a>");
		pcont.setNextPage("<a class=\"paginate_button\" href=\"~content_mark\">下一页</a>");
		pcont.setLastPage("<a class=\"paginate_button\" href=\"~content_mark\">尾页</a>");
		pcont.setEndPage("</div>");
		PageStyle.importPageStyle("admin", pcont);
	}
}
