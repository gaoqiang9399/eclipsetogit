package app.component.frontview.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * 前端设置,图片删除
 */
@FeignClient("mftcc-platform-fileService")
public interface VwImageFeign {

	/**
	 * 删除图片预览
	 * @param filePath：vw/news/20180101/131231231231.jpg
	 * @return
	 */
	@RequestMapping("/vwImage/deleteImage")
	Map<String ,Object> deleteImage(@RequestParam("filePath") String filePath);
}



