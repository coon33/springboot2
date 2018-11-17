package org.coonchen.fk.web.view;


import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
public class ViewResolverFreeMarker extends FreeMarkerViewResolver{
	
	
	
	
/*	@Bean
	public FreeMarkerViewResolver freeMarkerViewResolver() {
		FreeMarkerViewResolver resolver = super.freeMarkerViewResolver();
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
		return resolver;
	}*/
}
