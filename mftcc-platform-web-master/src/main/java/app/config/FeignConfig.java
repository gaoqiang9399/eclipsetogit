package app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;

import app.tech.upload.FeignSpringFormEncoder;
import feign.Request;
import feign.RequestInterceptor;
import feign.codec.Encoder;

@Configuration
public class FeignConfig {
	/**
	 * feign超时时间配置
	 * 
	 * @return
	 */
	@Bean
	public Request.Options feignOptions() {
		return new Request.Options(/** connectTimeoutMillis **/
				1000 * 1000, /** readTimeoutMillis **/
				1000 * 1000);
	}
	

	@Bean
    public Encoder feignEncoder() {
        return new FeignSpringFormEncoder();
    }
	
	@Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
//        	Gson gson = new Gson();
//        	gson.fromJson(new BinaryStreamReader(new ByteArrayInputStream(requestTemplate.body())), HashMap.class);
//        	GsonDecoder de = new GsonDecoder();
//        	System.out.println(requestTemplate.body());
//        	System.out.println(requestTemplate.append("seq:sss"));
        	try{
        		String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();//通过springmvc获取的请求，dwr请求自行添加
                if (sessionId != null && sessionId.trim().length()>0) {
                    requestTemplate.header("JSESSIONID", sessionId);
                }
        	}catch (IllegalStateException e){
        		//项目启动时调用feign时没有request，不操作
        		//不是http请求而调用了feign，无法加入sessionId，dwr请求自行添加
        	}
            
        };
    }
}
