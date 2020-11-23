package  app.component.pledge.feign;

import app.component.pledge.entity.MfHighGuaranteeContract;
import app.component.pledge.entity.MfHighGuaranteeContractSub;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
* Title: MfHighGuaranteeContractSubBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Jun 21 09:40:09 CST 2018
**/

@FeignClient("mftcc-platform-factor")
public interface MfHighGuaranteeContractSubFeign {
	
	@RequestMapping(value = "/mfHighGuaranteeContractSub/insert")
	public void insert(@RequestBody MfHighGuaranteeContractSub mfHighGuaranteeContractSub) throws Exception;
	
	@RequestMapping(value = "/mfHighGuaranteeContractSub/delete")
	public void delete(@RequestBody MfHighGuaranteeContractSub mfHighGuaranteeContractSub) throws Exception;
	
	@RequestMapping(value = "/mfHighGuaranteeContractSub/update")
	public void update(@RequestBody MfHighGuaranteeContractSub mfHighGuaranteeContractSub) throws Exception;
	
	@RequestMapping(value = "/mfHighGuaranteeContractSub/getById")
	public MfHighGuaranteeContractSub getById(@RequestBody MfHighGuaranteeContractSub mfHighGuaranteeContractSub) throws Exception;
	
	@RequestMapping(value = "/mfHighGuaranteeContractSub/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	@RequestMapping(value = "/mfHighGuaranteeContractSub/getDetailRelList")
	public List<MfHighGuaranteeContractSub> getDetailRelList(@RequestBody MfHighGuaranteeContractSub mfHighGuaranteeContractSub) throws Exception;

	@RequestMapping(value = "/mfHighGuaranteeContractSub/getSubList")
	public List<Map<String,Object>> getSubList(@RequestBody MfHighGuaranteeContractSub mfHighGuaranteeContractSub) throws  Exception;

	@RequestMapping(value = "/mfHighGuaranteeContractSub/insertSub")
	public Map<String, Object> insertSub (@RequestBody Map<String,Object> dataMap) throws Exception;

	@RequestMapping(value = "/mfHighGuaranteeContractSub/deleteSubRel")
	public Map<String, Object> deleteSubRel (@RequestBody String subId) throws Exception;
}
