package  app.component.cus.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.entity.MfCusPersonAssetsInfo;
import app.util.toolkit.Ipage;

/**
* Title: MfCusPersonAssetsInfoBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue Apr 11 09:11:53 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusPersonAssetsInfoFeign {
	
	@RequestMapping("/mfCusPersonAssetsInfo/insert")
	public void insert(@RequestBody MfCusPersonAssetsInfo mfCusPersonAssetsInfo) throws Exception;
	
	@RequestMapping("/mfCusPersonAssetsInfo/insertForApp")
	public MfCusPersonAssetsInfo insertForApp(@RequestBody MfCusPersonAssetsInfo mfCusPersonAssetsInfo) throws Exception;
	
	@RequestMapping("/mfCusPersonAssetsInfo/delete")
	public void delete(@RequestBody MfCusPersonAssetsInfo mfCusPersonAssetsInfo) throws Exception;
	
	@RequestMapping("/mfCusPersonAssetsInfo/update")
	public void update(@RequestBody MfCusPersonAssetsInfo mfCusPersonAssetsInfo) throws Exception;
	
	@RequestMapping("/mfCusPersonAssetsInfo/getById")
	public MfCusPersonAssetsInfo getById(@RequestBody MfCusPersonAssetsInfo mfCusPersonAssetsInfo) throws Exception;
	
	@RequestMapping("/mfCusPersonAssetsInfo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
	@RequestMapping("/mfCusPersonAssetsInfo/getCusPerAssetList")
	public List<MfCusPersonAssetsInfo> getCusPerAssetList(@RequestBody MfCusPersonAssetsInfo mfCusPersonAssetsInfo)throws Exception;
	
}
