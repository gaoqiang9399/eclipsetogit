/**
 * 
 */
package app.component.app.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.util.toolkit.Ipage;

/**
 * @author QiuZhao
 *
 */
@FeignClient("mftcc-platform-factor")
public interface MfBusStageSearchFeign {


	@RequestMapping(value = "/mfBusStageSearch/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage)throws Exception;

}
