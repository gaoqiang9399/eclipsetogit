package app.config;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.google.common.collect.Maps;
import com.zhuozhengsoft.pageoffice.poserver.AdminSeal;
import com.zhuozhengsoft.pageoffice.poserver.Server;

import app.base.filter.CommonFilter;
import app.base.interceptor.AuthenticationInterceptor;
import app.base.interceptor.PreInter;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "app", "com.core.formbean" })
public class WebConfig extends WebMvcConfigurerAdapter {

	@Bean
	public ServletRegistrationBean servletPoserverBean() {
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new Server(), "/poserver");// ServletName默认值为首字母小写，即myServlet
		servletRegistrationBean.addUrlMappings("/poserver.zz");
		servletRegistrationBean.addUrlMappings("/poserver.do");
		servletRegistrationBean.addUrlMappings("/pageoffice.cab");
		servletRegistrationBean.addUrlMappings("/popdf.cab");
		servletRegistrationBean.addUrlMappings("/sealsetup.exe");
		servletRegistrationBean.addUrlMappings("/posetup.exe");
		servletRegistrationBean.setName("poserver");
		return servletRegistrationBean;
	}

	@Bean
	public ServletRegistrationBean servletAdminsealBean() {
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new AdminSeal(), "/adminseal");// ServletName默认值为首字母小写，即myServlet
		servletRegistrationBean.addUrlMappings("/adminseal.do");
		servletRegistrationBean.addUrlMappings("/loginseal.do");
		servletRegistrationBean.addUrlMappings("/sealimage.do");
		servletRegistrationBean.setName("adminseal");
		return servletRegistrationBean;
	}

	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {

		return new EmbeddedServletContainerCustomizer() {
			@Override
			public void customize(ConfigurableEmbeddedServletContainer container) {
				ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/error.jsp");
				ErrorPage error403Page = new ErrorPage(HttpStatus.FORBIDDEN, "/error.jsp");
				ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/error.jsp");
				ErrorPage error405Page = new ErrorPage(HttpStatus.METHOD_NOT_ALLOWED, "/error.jsp");
				ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error.jsp");
				container.addErrorPages(error401Page);
				container.addErrorPages(error403Page);
				container.addErrorPages(error404Page);
				container.addErrorPages(error405Page);
				container.addErrorPages(error500Page);
			}

		};
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new AuthenticationInterceptor()).addPathPatterns("/**")
				.excludePathPatterns("/sysLogin/login").excludePathPatterns("/*/exception")
				.excludePathPatterns("/**/*.js").excludePathPatterns("/**/*.css").excludePathPatterns("/**/*.png")
				.excludePathPatterns("/**/*.gif").excludePathPatterns("/**/*.jpg");
		registry.addInterceptor(new PreInter()).addPathPatterns("/**").excludePathPatterns("/**/*.js")
				.excludePathPatterns("/**/*.css").excludePathPatterns("/**/*.png").excludePathPatterns("/**/*.gif")
				.excludePathPatterns("/**/*.jpg").excludePathPatterns("/**/sysLogin/logout*");
		super.addInterceptors(registry);
	}

	@Bean
	public FilterRegistrationBean encodingFilterRegistration() {
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding("UTF-8");
		encodingFilter.setForceEncoding(true);
		FilterRegistrationBean registration = new FilterRegistrationBean(encodingFilter);
		registration.setName("encodingFilter");
		registration.addUrlPatterns("/*");
		registration.setAsyncSupported(true);
		registration.setOrder(1);
		return registration;
	}

	@Bean
	public ViewResolver getViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/");
		resolver.setSuffix(".jsp");
		resolver.setContentType("text/html;charset=UTF-8");
		resolver.setRequestContextAttribute("request");
		return resolver;
	}

	@Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	@Bean
	public FilterRegistrationBean commonFilterRegistrationBean() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(new CommonFilter());
		filterRegistrationBean.setOrder(5);
		filterRegistrationBean.setEnabled(true);
		filterRegistrationBean.addUrlPatterns("/*");
		Map<String, String> initParameters = Maps.newHashMap();
		initParameters.put("excludes", "/favicon.ico,/img/*,/js/*,/css/*");
		initParameters.put("isIncludeRichText", "true");
		filterRegistrationBean.setInitParameters(initParameters);
		return filterRegistrationBean;
	}
	@Bean
	public ServletRegistrationBean getAuthServlet(){
		com.pro.servlet.ActivationServlet	 activationServlet = new com.pro.servlet.ActivationServlet();
		ServletRegistrationBean registrationBean = new ServletRegistrationBean();
		registrationBean.setServlet(activationServlet);
		List<String> urlMappings=new ArrayList<String>();
		urlMappings.add("/ActivationServlet");////访问，可以添加多个
		registrationBean.setUrlMappings(urlMappings);
		registrationBean.setLoadOnStartup(1);
		return registrationBean;
	}
	@Bean
	public ServletListenerRegistrationBean<EventListener> getAuthListener(){
		ServletListenerRegistrationBean<EventListener> registrationBean = new ServletListenerRegistrationBean<>();
		registrationBean.setListener(new com.pro.listener.StartListener());
		registrationBean.setOrder(1);
		return registrationBean;
	}
}
