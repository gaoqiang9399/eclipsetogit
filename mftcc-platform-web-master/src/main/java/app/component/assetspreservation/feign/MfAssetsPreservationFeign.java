package  app.component.assetspreservation.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.assetspreservation.entity.MfAssetsPreservation;
import app.util.toolkit.Ipage;

/**
* Title: MfAssureInfoBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Jul 20 15:11:16 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfAssetsPreservationFeign {
	
	@RequestMapping(value = "/mfAssetsPreservation/insert")
	public void insert(@RequestBody MfAssetsPreservation mfAssetsPreservation) throws Exception;

	@RequestMapping(value = "/mfAssetsPreservation/delete")
	public void delete(@RequestBody MfAssetsPreservation mfAssetsPreservation) throws Exception;
	@RequestMapping(value = "/mfAssetsPreservation/update")
	public void update(@RequestBody MfAssetsPreservation mfAssetsPreservation) throws Exception;

	@RequestMapping(value = "/mfAssetsPreservation/getById")
	public MfAssetsPreservation getById(@RequestBody MfAssetsPreservation mfAssetsPreservation) throws Exception ;

	@RequestMapping(value = "/mfAssetsPreservation/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception ;
	
	@RequestMapping(value = "/mfAssetsPreservation/getListByPledgeNo")
	public List<MfAssetsPreservation> getListByPledgeNo(@RequestBody MfAssetsPreservation mfAssetsPreservation) throws Exception ;
}
