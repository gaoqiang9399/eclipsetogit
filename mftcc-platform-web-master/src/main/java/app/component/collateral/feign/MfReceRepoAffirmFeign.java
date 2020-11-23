package  app.component.collateral.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.collateral.entity.MfReceRepoAffirm;
import app.util.toolkit.Ipage;

/**
* Title: MfReceRepoAffirmBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Fri May 12 15:06:35 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfReceRepoAffirmFeign {
	
	@RequestMapping(value = "/mfReceRepoAffirm/insert")
	public void insert(@RequestBody MfReceRepoAffirm mfReceRepoAffirm) throws Exception;
	
	@RequestMapping(value = "/mfReceRepoAffirm/delete")
	public void delete(@RequestBody MfReceRepoAffirm mfReceRepoAffirm) throws Exception;
	
	@RequestMapping(value = "/mfReceRepoAffirm/update")
	public void update(@RequestBody MfReceRepoAffirm mfReceRepoAffirm) throws Exception;
	
	@RequestMapping(value = "/mfReceRepoAffirm/getById")
	public MfReceRepoAffirm getById(@RequestBody MfReceRepoAffirm mfReceRepoAffirm) throws Exception;
	
	@RequestMapping(value = "/mfReceRepoAffirm/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfReceRepoAffirm") MfReceRepoAffirm mfReceRepoAffirm) throws Exception;
	/**
	 * 
	 * 方法描述： 获得新增应收账款反转让确认初始化数据
	 * @param busPleId
	 * @param appId
	 * @return
	 * @throws Exception
	 * MfReceRepoAffirm
	 * @author 沈浩兵
	 * @date 2017-5-12 下午3:19:10
	 */
	@RequestMapping(value = "/mfReceRepoAffirm/getReceRepoAffirmInit")
	public MfReceRepoAffirm getReceRepoAffirmInit(@RequestBody String busPleId,@RequestParam("appId") String appId) throws Exception;
	
}
