package app.component.interfacesinterface;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("mftcc-platform-factor")
public interface MobileServiceUpdateInterfaceFeign {
	/**
	 * 检查是否更新并返回需要更新的版本
	 * @return
	 */
	@RequestMapping(value = "/mobileServiceUpdateInterface/checkHotUpdate")
	public Map<String, Object> checkHotUpdate(@RequestBody String version) throws Exception;
	/**
	 * 移动端版本强制更新（c端/b端/pad）
	 * result=1 需要强制更新
	 * @param origin
	 * @param version
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mobileServiceUpdateInterface/checkVersion")
		Map<String, Object> checkVersion(@RequestBody String origin,@RequestParam("version") String version) throws Exception;
}
