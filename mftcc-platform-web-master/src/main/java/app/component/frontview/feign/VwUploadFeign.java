package app.component.frontview.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartFile;

/**
 * 前端设置,图片上传及预览
 */
@FeignClient(name =  "mftcc-platform-fileService")
public interface VwUploadFeign {
	/**
	 *
	 * @param file 上传的文件
	 * @param folder 轮播图:banner,友情链接:link,新闻:news,网站设置:set
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	@RequestLine("POST /vwUpload/upLoadFile")
	@Headers("Content-Type: multipart/form-data")
	String uploadFile(@Param("upload") MultipartFile file, @Param("folder") String folder, @Param("fileName") String fileName);



	class MultipartFormCofig {
		@Autowired
		private ObjectFactory<HttpMessageConverters> messageConverters;

		@Bean
		public Encoder encoder() {
			return new SpringFormEncoder();
		}
	}
}



