package  app.component.cus.feign;

import app.component.cus.entity.MfCusType;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
* Title: MfCusTypeBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Mon Jun 05 17:06:11 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusTypeFeign {
	
	@RequestMapping(value = "/mfCusType/insert")
	public Map<String, Object> insert(@RequestBody MfCusType mfCusType) throws Exception;
	
	@RequestMapping(value = "/mfCusType/delete")
	public void delete(@RequestBody MfCusType mfCusType) throws Exception;
	
	@RequestMapping(value = "/mfCusType/update")
	public void update(@RequestBody MfCusType mfCusType) throws Exception;
	
	@RequestMapping(value = "/mfCusType/getById")
	public MfCusType getById(@RequestBody MfCusType mfCusType) throws Exception;
	
	@RequestMapping(value = "/mfCusType/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
	@RequestMapping(value = "/mfCusType/getAllList")
	public List<MfCusType> getAllList(@RequestBody MfCusType mfCusType) throws Exception;
	/**
	 * 不属于这个小类的客户类型
	 * @param mfCusType
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mfCusType/getAllListNotThisBaseType")
	public List<MfCusType> getAllListNotThisBaseType(@RequestBody MfCusType mfCusType)  throws Exception;
	
	@RequestMapping(value = "/mfCusType/getByBaseType")
	public MfCusType getByBaseType(@RequestBody MfCusType mfCusType) throws Exception;
	
	@RequestMapping(value = "/mfCusType/getListByBaseType")
	public List<MfCusType> getListByBaseType(@RequestBody MfCusType mfCusType) throws Exception;


	@RequestMapping(value = "/mfCusType/getUrlByCusType")
	public MfCusType getUrlByCusType(@RequestParam("cusNo") String cusNo,@RequestParam("cusBaseType") String cusBaseType,@RequestParam("parms") String parms) throws Exception;
}
