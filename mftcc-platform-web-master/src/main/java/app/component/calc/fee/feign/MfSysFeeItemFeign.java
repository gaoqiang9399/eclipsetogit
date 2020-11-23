package  app.component.calc.fee.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.calc.fee.entity.MfSysFeeItem;
import app.util.toolkit.Ipage;

/**
* Title: MfSysFeeItemBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Fri May 20 18:04:05 CST 2016
**/

@FeignClient("mftcc-platform-factor")
public interface MfSysFeeItemFeign {
	
	@RequestMapping(value = "/mfSysFeeItem/insert")
	public void insert(@RequestBody MfSysFeeItem mfSysFeeItem) throws Exception;
	
	@RequestMapping(value = "/mfSysFeeItem/delete")
	public void delete(@RequestBody MfSysFeeItem mfSysFeeItem) throws Exception;
	
	@RequestMapping(value = "/mfSysFeeItem/update")
	public void update(@RequestBody MfSysFeeItem mfSysFeeItem) throws Exception;
	
	@RequestMapping(value = "/mfSysFeeItem/getById")
	public MfSysFeeItem getById(@RequestBody MfSysFeeItem mfSysFeeItem) throws Exception;
	
	@RequestMapping(value = "/mfSysFeeItem/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfSysFeeItem") MfSysFeeItem mfSysFeeItem) throws Exception;

	@RequestMapping(value = "/mfSysFeeItem/getAll")
	public List<MfSysFeeItem> getAll(@RequestBody MfSysFeeItem mfSysFeeItem)throws Exception;

	/**
	 * 
	 * 方法描述： 删除产品下的费用配置以及节点上的费用配置
	 * @param mfSysFeeItem
	 * @throws Exception
	 * void
	 * @author zhs
	 * @date 2017-7-8 下午6:53:13
	 */
	@RequestMapping(value = "/mfSysFeeItem/deleteFeeAndNodeFee")
	public void deleteFeeAndNodeFee(@RequestBody MfSysFeeItem mfSysFeeItem) throws Exception;
	/**
	 * 更新之后的属性获取
	 * @param mfSysFeeItem
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mfSysFeeItem/getByIdUpdated")
	public MfSysFeeItem getByIdUpdated(@RequestBody MfSysFeeItem mfSysFeeItem)  throws Exception;

	@RequestMapping(value = "/mfSysFeeItem/updateSort")
	public void updateSort(@RequestBody String feeStdNo,@RequestParam("itemIds")  String[] itemIds) throws Exception;
	
	@RequestMapping(value = "/mfSysFeeItem/insertForPrdct")
	public Map<String, Object> insertForPrdct(@RequestBody Map<String, Object> parmMap) throws Exception;

	@RequestMapping(value = "/mfSysFeeItem/updateForPrdct")
	public Map<String, Object> updateForPrdct(@RequestBody Map<String, Object> parmMap) throws Exception;


	
}
