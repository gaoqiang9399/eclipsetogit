package app.config;

import org.directwebremoting.servlet.DwrServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import cn.mftcc.util.StringUtil;
import config.YmlConfig;

@Configuration
public class DwrConfig {
	 @Autowired
	 public YmlConfig ymlConfig;
	 @Bean  
     public ServletRegistrationBean dwrServletRegistration() {  
        ServletRegistrationBean registration =  new ServletRegistrationBean(new DwrServlet());
		registration.setLoadOnStartup(3);
		registration.addUrlMappings("/dwr/*");
		registration.addInitParameter("crossDomainSessionSecurity", "false");
		registration.addInitParameter("allowScriptTagRemoting", "true");
		registration.addInitParameter("classes", "java.lang.Object");
		registration.addInitParameter("activeReverseAjaxEnabled", "true");
		registration.addInitParameter("initApplicationScopeCreatorsAtStartup", "true");
		registration.addInitParameter("maxWaitAfterWrite", "3000");
		registration.addInitParameter("debug", "debug");
		registration.addInitParameter("logLevel", "WARN");
		//解决映射后dwr访问不到的问题
		String webPath = ymlConfig.getWebservice().get("webPath");
		if(StringUtil.isNotEmpty(webPath)){
			if(!"/".equals(webPath.substring(0, 1))){
				webPath = "/"+webPath;
			}
		}
		registration.addInitParameter("overridePath", webPath+"/dwr");
		
        return registration;  
     } 
}
