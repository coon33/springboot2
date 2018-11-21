package org.coonchen.fk.web.view.freemarker;


import org.coonchen.fk.utils.PropertiesUtil;
import org.coonchen.fk.web.page.freemarker.PageDirectiveFreeMarker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.util.Map;

public class ViewResolverFreeMarker{
	@Bean
	public CommandLineRunner customFreemarker(FreeMarkerViewResolver resolver) {
		return new CommandLineRunner() {
			@Override
			public void run(String... strings) throws Exception {
				resolver.getAttributesMap().put("page", new PageDirectiveFreeMarker());
				String tagGroup = "freemarker.tag.";
				Map<String,String> mapPro = PropertiesUtil.getPropertiesGroupByTag(tagGroup);
				if(mapPro!=null && !mapPro.isEmpty()) {
					for(Map.Entry<String, String> entry : mapPro.entrySet()) {
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
