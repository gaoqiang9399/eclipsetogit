package  app.component.cus.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.entity.MfCusPersonFlowAssetsInfo;
import app.util.toolkit.Ipage;

/**
* Title: MfCusPersonFlowAssetsInfoBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Wed Sep 13 18:38:37 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusPersonFlowAssetsInfoFeign {
	@RequestMapping("/mfCusPersonFlowAssetsInfo/insert")
	public void insert(@RequestBody MfCusPersonFlowAssetsInfo mfCusPersonFlowAssetsInfo) throws Exception;
	
	@RequestMapping("/mfCusPersonFlowAssetsInfo/insertForApp")
	public MfCusPersonFlowAssetsInfo insertForApp(@RequestBody MfCusPersonFlowAssetsInfo mfCusPersonFlowAssetsInfo) throws Exception;
	
	@RequestMapping("/mfCusPersonFlowAssetsInfo/delete")
	public void delete(@RequestBody MfCusPersonFlowAssetsInfo mfCusPersonFlowAssetsInfo) throws Exception;
	
	@RequestMapping("/mfCusPersonFlowAssetsInfo/update")
	public void update(@RequestBody MfCusPersonFlowAssetsInfo mfCusPersonFlowAssetsInfo) throws Exception;
	
	@RequestMapping("/mfCusPersonFlowAssetsInfo/getById")
	public MfCusPersonFlowAssetsInfo getById(@RequestBody MfCusPersonFlowAssetsInfo mfCusPersonFlowAssetsInfo) throws Exception;
	
	@RequestMapping("/mfCusPersonFlowAssetsInfo/getMfCusPersonFlowAssetsInfoList")
	public List<MfCusPersonFlowAssetsInfo> getMfCusPersonFlowAssetsInfoList(@RequestBody MfCusPersonFlowAssetsInfo mfCusPersonFlowAssetsInfo) throws Exception;
	
	@RequestMapping("/mfCusPersonFlowAssetsInfo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
}
