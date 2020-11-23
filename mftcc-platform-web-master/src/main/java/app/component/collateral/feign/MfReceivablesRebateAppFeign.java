package  app.component.collateral.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.collateral.entity.MfReceivablesRebateApp;
import app.component.collateral.entity.MfReceivablesRebateAppHis;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

/**
* Title: MfReceivablesRebateAppBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Fri May 12 19:59:25 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfReceivablesRebateAppFeign {
	
	@RequestMapping(value = "/mfReceivablesRebateApp/insert")
	public MfReceivablesRebateApp insert(@RequestBody MfReceivablesRebateApp mfReceivablesRebateApp) throws Exception;
	
	@RequestMapping(value = "/mfReceivablesRebateApp/delete")
	public void delete(@RequestBody MfReceivablesRebateApp mfReceivablesRebateApp) throws Exception;
	
	@RequestMapping(value = "/mfReceivablesRebateApp/update")
	public void update(@RequestBody MfReceivablesRebateApp mfReceivablesRebateApp) throws Exception;
	/**
	 * 
	 * 方法描述： 折让确认保存
	 * @param mfReceivablesRebateApp
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-5-23 下午2:54:25
	 */
	@RequestMapping(value = "/mfReceivablesRebateApp/updateRebateAffirm")
	public void updateRebateAffirm(@RequestBody MfReceivablesRebateApp mfReceivablesRebateApp) throws Exception;
	
	@RequestMapping(value = "/mfReceivablesRebateApp/getById")
	public MfReceivablesRebateApp getById(@RequestBody MfReceivablesRebateApp mfReceivablesRebateApp) throws Exception;
	
	@RequestMapping(value = "/mfReceivablesRebateApp/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfReceivablesRebateApp") MfReceivablesRebateApp mfReceivablesRebateApp) throws Exception;
	/**
	 * 
	 * 方法描述： 初始化折让申请表单数据
	 * @param busPleId
	 * @param appId
	 * @return
	 * @throws Exception
	 * MfReceivablesRebateApp
	 * @author 沈浩兵
	 * @date 2017-5-23 下午3:04:58
	 */
	@RequestMapping(value = "/mfReceivablesRebateApp/getReceivablesRebateAppInit")
	public MfReceivablesRebateApp getReceivablesRebateAppInit(@RequestBody String busPleId,@RequestParam("appId") String appId) throws Exception;
	/**
	 * 
	 * 方法描述：  初始化折让确认表单数据
	 * @param busPleId
	 * @param appId
	 * @return
	 * @throws Exception
	 * MfReceivablesRebateApp
	 * @author 沈浩兵
	 * @date 2017-5-23 下午3:05:33
	 */
	@RequestMapping(value = "/mfReceivablesRebateApp/getRebateAffirmAppInit")
	public MfReceivablesRebateApp getRebateAffirmAppInit(@RequestBody String busPleId,@RequestParam("appId") String appId) throws Exception;
	
	@RequestMapping(value = "/mfReceivablesRebateApp/doCommit")
	public Result doCommit(@RequestBody String taskId,@RequestParam("appId")   String appId,@RequestParam("rebateAppId") String rebateAppId,@RequestParam("opinionType")  String opinionType,@RequestParam("approvalOpinion") String approvalOpinion,@RequestParam("transition")  String transition,@RequestParam("opNo")  String opNo,@RequestParam("nextUser") String nextUser,@RequestParam("receivablesRebateApp")  MfReceivablesRebateApp receivablesRebateApp,@RequestParam("receivablesRebateAppHis") MfReceivablesRebateAppHis receivablesRebateAppHis,@RequestParam("dataMap")  Map<String, Object> dataMap) throws Exception;
	/**
	 * 
	 * 方法描述： 获得折让历史
	 * @param mfReceivablesRebateApp
	 * @return
	 * @throws Exception
	 * List<MfReceivablesRebateApp>
	 * @author 沈浩兵
	 * @date 2017-5-23 下午5:00:02
	 */
	@RequestMapping(value = "/mfReceivablesRebateApp/getReceRebateList")
	public List<MfReceivablesRebateApp> getReceRebateList(@RequestBody MfReceivablesRebateApp mfReceivablesRebateApp) throws Exception;
}
