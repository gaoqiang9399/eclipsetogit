/**
 * 
 */
package app.component.app.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.app.entity.MfBusDocSupplement;
import app.util.toolkit.Ipage;

/**
 * @author LiJuntao
 *
 */
@FeignClient("mftcc-platform-factor")
public interface MfBusDocSupplementFeign {


	@RequestMapping(value = "/mfBusDocSupplement/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage)throws Exception;
	
	@RequestMapping(value = "/mfBusDocSupplement/getById")
	public MfBusDocSupplement getById(@RequestBody MfBusDocSupplement mfBusDocSupplement) throws Exception;

}
