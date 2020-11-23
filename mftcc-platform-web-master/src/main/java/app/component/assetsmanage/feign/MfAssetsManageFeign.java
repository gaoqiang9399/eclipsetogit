package  app.component.assetsmanage.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.assetsmanage.entity.MfAssetsManage;
import app.util.toolkit.Ipage;

/**
* Title: MfAssetsManageBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Wed Sep 26 20:22:28 CST 2018
**/
@FeignClient("mftcc-platform-factor")
public interface MfAssetsManageFeign {
	
	@RequestMapping(value = "/mfAssetsManage/insert")
	public void insert(@RequestBody MfAssetsManage mfAssetsManage) throws Exception ;

	@RequestMapping(value = "/mfAssetsManage/delete")
	public void delete(@RequestBody MfAssetsManage mfAssetsManage) throws Exception ;
	

	@RequestMapping(value = "/mfAssetsManage/update")
	public void update(@RequestBody MfAssetsManage mfAssetsManage) throws Exception ;

	@RequestMapping(value = "/mfAssetsManage/getById")
	public MfAssetsManage getById(@RequestBody MfAssetsManage mfAssetsManage) throws Exception ;

	@RequestMapping(value = "/mfAssetsManage/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
}
