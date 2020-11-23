package app.tech.upload;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import feign.Headers;

//@FeignClient(name = "dhcc-platform-file", configuration = FeignMultipartSupportConfig.class)
@FeignClient(name = "dhcc-platform-file")
public interface ImageUtilFeign {


	
	
//	@RequestLine("POST /dhcc-platform-file/imageUtil/getImageInputStream")
//	public ByteArrayInputStream getImageInputStream(@Param("srcPath") String srcPath) throws Exception;
	
	@RequestMapping(value = "/dhcc-platform-file/imageUtil/getImageInputStream")
	@Headers("Content-Type: image/jpeg")
	public byte[] getImageInputStream(@RequestParam("srcPath") String srcPath)  throws Exception;
	
}