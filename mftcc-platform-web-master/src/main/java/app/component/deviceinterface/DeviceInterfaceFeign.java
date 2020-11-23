package  app.component.deviceinterface;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
/**
* Title: deviceinterface.java
* Description:
* @author:mahao@dhcc.com.cn
* @Tue Jan 16 17:29:55 CST 2018
**/

@FeignClient("mftcc-platform-factor")
public interface DeviceInterfaceFeign {
	/**
	 * 判断备案信息用是否有改uuid信息
	 * @param uuid
	 * @return true 有
	 * @author MaHao
	 * @throws Exception 
	 * @date 2018-1-16 下午5:32:32
	 */
	@RequestMapping(value = "/deviceInterface/isHasUuid")
	public boolean isHasUuid(@RequestBody String uuid) throws Exception ;
}


