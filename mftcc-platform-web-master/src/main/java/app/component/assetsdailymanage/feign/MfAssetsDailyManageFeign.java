package app.component.assetsdailymanage.feign;

import app.component.assetsdailymanage.entity.MfAssetsDailyManage;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Title: MfAssetsManageBo.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed Sep 26 20:22:28 CST 2018
 **/
@FeignClient("mftcc-platform-factor")
public interface MfAssetsDailyManageFeign {

	@RequestMapping(value = "/mfAssetsDailyManage/insert")
	public void insert(@RequestBody MfAssetsDailyManage mfAssetsDailyManage) throws Exception ;

	@RequestMapping(value = "/mfAssetsDailyManage/delete")
	public void delete(@RequestBody MfAssetsDailyManage mfAssetsDailyManage) throws Exception ;


	@RequestMapping(value = "/mfAssetsDailyManage/update")
	public void update(@RequestBody MfAssetsDailyManage mfAssetsDailyManage) throws Exception ;

	@RequestMapping(value = "/mfAssetsDailyManage/getById")
	public MfAssetsDailyManage getById(@RequestBody MfAssetsDailyManage mfAssetsDailyManage) throws Exception ;

	@RequestMapping(value = "/mfAssetsDailyManage/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping("/mfAssetsDailyManage/getListByAssetsManageId")
	public List<MfAssetsDailyManage> getListByAssetsManageId(@RequestBody MfAssetsDailyManage mfAssetsDailyManage) throws Exception ;

}
