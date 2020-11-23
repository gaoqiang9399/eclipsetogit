package  app.component.frontview.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
* Title: MfFrontAppSettingBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Jul 20 11:25:24 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfAppUpdateSettingFeign {
	/**
	 * 创建json文件并发布app更新压缩包
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/mfAppUpdateSetting/createJsonFile")
	public Map<String, Object> createJsonFile(@RequestBody String param) throws Exception;
	/**
	 * 获取历史版本号
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/mfAppUpdateSetting/getHistoryVersion")
	public Map<String, Object> getHistoryVersion() throws Exception;
}
