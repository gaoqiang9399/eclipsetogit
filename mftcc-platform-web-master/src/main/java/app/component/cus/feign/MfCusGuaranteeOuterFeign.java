package  app.component.cus.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.cus.entity.MfCusGuaranteeOuter;
import app.util.toolkit.Ipage;

/**
* Title: MfCusGuaranteeOuterBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue Feb 07 10:36:26 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusGuaranteeOuterFeign {
	
	@RequestMapping("/mfCusGuaranteeOuter/insert")
	public void insert(@RequestBody MfCusGuaranteeOuter mfCusGuaranteeOuter) throws Exception;
	
	@RequestMapping("/mfCusGuaranteeOuter/insertForApp")
	public MfCusGuaranteeOuter insertForApp(@RequestBody MfCusGuaranteeOuter mfCusGuaranteeOuter) throws Exception;
	
	@RequestMapping("/mfCusGuaranteeOuter/delete")
	public void delete(@RequestBody MfCusGuaranteeOuter mfCusGuaranteeOuter) throws Exception;
	
	@RequestMapping("/mfCusGuaranteeOuter/update")
	public void update(@RequestBody MfCusGuaranteeOuter mfCusGuaranteeOuter) throws Exception;
	
	@RequestMapping("/mfCusGuaranteeOuter/getById")
	public MfCusGuaranteeOuter getById(@RequestBody MfCusGuaranteeOuter mfCusGuaranteeOuter) throws Exception;
	
	@RequestMapping("/mfCusGuaranteeOuter/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	/**
	 * 
	 * 方法描述： 获得对外担保信息列表
	 * @param mfCusGuaranteeOuter
	 * @return
	 * @throws ServiceException
	 * List<MfCusGuaranteeOuter>
	 * @author 沈浩兵
	 * @date 2017-10-11 下午6:47:17
	 */
	@RequestMapping("/mfCusGuaranteeOuter/getMfCusGuaranteeOuterList")
	public List<MfCusGuaranteeOuter> getMfCusGuaranteeOuterList(@RequestBody MfCusGuaranteeOuter mfCusGuaranteeOuter) throws Exception;
	
}
