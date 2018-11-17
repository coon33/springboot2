package com.allwell;

import java.util.Map;
import java.util.Map.Entry;

import org.coonchen.fk.utils.PropertiesUtil;
import org.coonchen.fk.web.page.PageDirectiveFreeMarker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

@SpringBootApplication
@ServletComponentScan(basePackages = {"com.allwell.*","org.coonchen.fk"})
public class ProjectApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}

	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// 注意这里要指向原先用main方法执行的Application启动类
		return builder.sources(ProjectApplication.class);
	}

	@Bean
	public CommandLineRunner customFreemarker(FreeMarkerViewResolver resolver) {
		return new CommandLineRunner() {
			@Override
			public void run(String... strings) throws Exception {
				resolver.getAttributesMap().put("page", new PageDirectiveFreeMarker());	
				String tagGroup = "freemarker.tag.";
				Map<String,String> mapPro = PropertiesUtil.getPropertiesGroupByTag(tagGroup);
				if(mapPro!=null && !mapPro.isEmpty()) {
					for(Entry<String, String> entry : mapPro.entrySet()) {
						try {
							Class cla = Class.forName(entry.getValue());
							resolver.getAttributesMap().put(entry.getKey().replace(tagGroup, ""), cla.newInstance());
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						} catch (InstantiationException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
					}
				}
				
			}
		};
	}
}