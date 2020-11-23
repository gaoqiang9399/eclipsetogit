package  app.component.collateral.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.collateral.entity.MfReceivablesDisputedApp;
import app.component.collateral.entity.MfReceivablesDisputedAppHis;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

/**
* Title: MfReceivablesDisputedAppBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Mon May 15 18:27:45 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfReceivablesDisputedAppFeign {
	
	@RequestMapping(value = "/mfReceivablesDisputedApp/insert")
	public MfReceivablesDisputedApp insert(@RequestBody MfReceivablesDisputedApp mfReceivablesDisputedApp) throws Exception;
	
	@RequestMapping(value = "/mfReceivablesDisputedApp/delete")
	public void delete(@RequestBody MfReceivablesDisputedApp mfReceivablesDisputedApp) throws Exception;
	
	@RequestMapping(value = "/mfReceivablesDisputedApp/update")
	public void update(@RequestBody MfReceivablesDisputedApp mfReceivablesDisputedApp) throws Exception;
	
	@RequestMapping(value = "/mfReceivablesDisputedApp/getById")
	public MfReceivablesDisputedApp getById(@RequestBody MfReceivablesDisputedApp mfReceivablesDisputedApp) throws Exception;
	
	@RequestMapping(value = "/mfReceivablesDisputedApp/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfReceivablesDisputedApp") MfReceivablesDisputedApp mfReceivablesDisputedApp) throws Exception;
	/**
	 * 
	 * 方法描述： 获得争议历史
	 * @param mfReceivablesDisputedApp
	 * @return
	 * @throws Exception
	 * List<MfReceivablesDisputedApp>
	 * @author 沈浩兵
	 * @date 2017-5-23 下午5:06:47
	 */
	@RequestMapping(value = "/mfReceivablesDisputedApp/getReceDisputedList")
	public List<MfReceivablesDisputedApp> getReceDisputedList(@RequestBody MfReceivablesDisputedApp mfReceivablesDisputedApp) throws Exception;
	
	@RequestMapping(value = "/mfReceivablesDisputedApp/doCommit")
	public Result doCommit(@RequestBody String taskId,@RequestParam("appId")   String appId,@RequestParam("disputedAppId") String disputedAppId,@RequestParam("opinionType")  String opinionType,@RequestParam("approvalOpinion") String approvalOpinion,@RequestParam("transition")  String transition,@RequestParam("opNo")  String opNo,@RequestParam("nextUser") String nextUser,@RequestParam("receivablesDisputedApp")  MfReceivablesDisputedApp receivablesDisputedApp,@RequestParam("disputedAppHis") MfReceivablesDisputedAppHis disputedAppHis,@RequestParam("dataMap")  Map<String, Object> dataMap) throws Exception;
}
