package  app.component.pledge.feign;

import app.util.toolkit.Ipage;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.pledge.entity.MfHighGuaranteeContract;
import app.component.pledge.entity.MfHighGuaranteeContractSub;
import app.component.wkf.entity.Result;

/**
* Title: MfHighGuaranteeContractBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Jun 21 09:31:02 CST 2018
**/

@FeignClient("mftcc-platform-factor")
public interface MfHighGuaranteeContractFeign {
	
	@RequestMapping(value = "/mfHighGuaranteeContract/insert")
	public MfHighGuaranteeContract insert(@RequestBody MfHighGuaranteeContract mfHighGuaranteeContract) throws Exception;
	
	@RequestMapping(value = "/mfHighGuaranteeContract/delete")
	public Map<String,Object> delete(@RequestBody MfHighGuaranteeContract mfHighGuaranteeContract) throws Exception;
	
	@RequestMapping(value = "/mfHighGuaranteeContract/update")
	public void update(@RequestBody MfHighGuaranteeContract mfHighGuaranteeContract) throws Exception;
	
	@RequestMapping(value = "/mfHighGuaranteeContract/getById")
	public MfHighGuaranteeContract getById(@RequestBody MfHighGuaranteeContract mfHighGuaranteeContract) throws Exception;
	
	@RequestMapping(value = "/mfHighGuaranteeContract/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/mfHighGuaranteeContract/findList")
	public List<MfHighGuaranteeContract> findList(@RequestBody MfHighGuaranteeContract mfHighGuaranteeContract) throws Exception;

	@RequestMapping(value = "/mfHighGuaranteeContract/refreshHighGrtContract")
	public MfHighGuaranteeContract refreshHighGrtContract(@RequestBody MfHighGuaranteeContract mfHighGuaranteeContract) throws Exception;

	@RequestMapping(value = "/mfHighGuaranteeContract/commit")
	public MfHighGuaranteeContract commit(@RequestBody MfHighGuaranteeContract mfHighGuaranteeContract) throws Exception;

	@RequestMapping(value = "/mfHighGuaranteeContract/insertCollateral")
	public Map<String,Object> insertCollateral (@RequestBody Map<String, Object> dataMap) throws Exception;

	@RequestMapping(value = "/mfHighGuaranteeContract/checkLimit")
	public Map<String,Object> checkLimit (@RequestParam("appId") String appId,@RequestParam("useAmt") Double useAmt) throws Exception;
}
