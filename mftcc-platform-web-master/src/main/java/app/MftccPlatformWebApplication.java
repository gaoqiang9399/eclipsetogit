package app;

import com.core.struts.SystemData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;



@EnableEurekaClient
@EnableCircuitBreaker
@SpringBootApplication
@ComponentScan(basePackages = { "config","app", "com.core.formbean.screentool" })
@EnableAutoConfiguration
@EnableFeignClients
@EnableDiscoveryClient
@EnableWebMvc
public class MftccPlatformWebApplication extends SpringBootServletInitializer{
	private static Logger logger = LoggerFactory.getLogger(MftccPlatformWebApplication.class);
	public static void main(String[] args) {
		try {
			SystemData.loadPath();
		} catch (Exception e) {
			logger.error("加载表单路径失败",e);
		}
		SpringApplication.run(MftccPlatformWebApplication.class, args);
	}
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(this.getClass());
    }
}
