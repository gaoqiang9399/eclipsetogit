package app.component.prdct.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.prdct.entity.MfKindNode;
import app.component.prdct.entity.MfSysKind;
import app.component.sys.entity.SysOrg;
import app.util.toolkit.Ipage;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: MfSysKindBo.java Description:
 *
 * @author:kaifa@dhcc.com.cn
 * @Mon May 23 10:52:43 CST 2016
 **/

@FeignClient("mftcc-platform-factor")
public interface MfSysKindFeign {

	@RequestMapping(value = "/mfSysKind/insert", method = RequestMethod.POST)
	public MfSysKind insert(@RequestBody MfSysKind mfSysKind) throws Exception ;
	@RequestMapping(value = "/mfSysKind/copyKind", method = RequestMethod.POST)
	public MfSysKind copyKind(@RequestParam("srcKindNo") String srcKindNo, @RequestBody MfSysKind mfSysKind) throws Exception ;

	@RequestMapping(value = "/mfSysKind/delete", method = RequestMethod.POST)
	public void delete(@RequestBody MfSysKind mfSysKind) throws Exception ;

	@RequestMapping(value = "/mfSysKind/update", method = RequestMethod.POST)
	public void update(@RequestBody MfSysKind mfSysKind) throws Exception ;

	/**
	 *
	 * 方法描述： 更新产品关联的审批流程，业务审批流程、合同审批流程、放款审批流程
	 *
	 * @param dataMap
	 * @throws Exception
	 *             void
	 * @author 沈浩兵
	 * @date 2017-3-28 上午11:14:25
	 */
	@RequestMapping(value = "/mfSysKind/updateWkf", method = RequestMethod.POST)
	public void updateWkf(@RequestBody Map<String, Object> dataMap) throws Exception ;

	/**
	 *
	 * 方法描述： 更新产品核算参数设置
	 *
	 * @param mfSysKind
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @author zhs
	 * @date 2017-7-11 上午9:57:49
	 */
	@RequestMapping(value = "/mfSysKind/updateKindCalcConfig", method = RequestMethod.POST)
	public Map<String, Object> updateKindCalcConfig(@RequestBody MfSysKind mfSysKind) throws Exception ;

	@RequestMapping(value = "/mfSysKind/getById", method = RequestMethod.POST)
	public MfSysKind getById(@RequestBody MfSysKind mfSysKind) throws Exception ;

	@RequestMapping(value = "/mfSysKind/findByPage", method = RequestMethod.POST)
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception ;

	@RequestMapping(value = "/mfSysKind/findByPageForConfig", method = RequestMethod.POST)
	public Ipage findByPageForConfig(@RequestBody Ipage ipage, @RequestParam("mfSysKind") MfSysKind mfSysKind) throws Exception ;

	@RequestMapping(value = "/mfSysKind/getSysKindList", method = RequestMethod.POST)
	public List<MfSysKind> getSysKindList(@RequestBody MfSysKind mfSysKind) throws Exception ;

	@RequestMapping(value = "/mfSysKind/getPrdctListByPage", method = RequestMethod.POST)
	public Ipage getPrdctListByPage(@RequestBody Ipage ipage, @RequestParam("mfSysKind") MfSysKind mfSysKind) throws Exception ;

	/**
	 *
	 * 方法描述： 根据产品编号获得产品下配置的费用项
	 *
	 * @param mfSysKindList
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-3-29 下午3:49:14
	 */
	@RequestMapping(value = "/mfSysKind/getFeeItemMap", method = RequestMethod.POST)
	public Map<String, Object> getFeeItemMap(@RequestBody List<MfSysKind> mfSysKindList) throws Exception ;

	/**
	 *
	 * 方法描述： 根据业务模式获得产品种类
	 *
	 * @param mfSysKind
	 * @return
	 * @throws Exception
	 *             List<MfSysKind>
	 * @author 沈浩兵
	 * @date 2017-4-20 下午3:07:40
	 */
	@RequestMapping(value = "/mfSysKind/getSysKindListByBusModel", method = RequestMethod.POST)
	public List<MfSysKind> getSysKindListByBusModel(@RequestBody Map<String, Object> dataMap) throws Exception ;

	/**
	 *
	 * 方法描述：获取产品下的要件配置
	 *
	 * @param mfSysKindList
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @author zhs
	 * @date 2017-6-22 上午10:35:37
	 */
	@RequestMapping(value = "/mfSysKind/getDocConfigMap", method = RequestMethod.POST)
	public Map<String, Object> getDocConfigMap(@RequestBody List<MfSysKind> mfSysKindList) throws Exception ;

	/**
	 *
	 * 方法描述： 根据产品下的流程编号获取业务流程节点信息
	 *
	 * @param mfSysKindList
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @author zhs
	 * @date 2017-7-2 上午10:49:24
	 */
	@RequestMapping(value = "/mfSysKind/getBusWkfMap", method = RequestMethod.POST)
	public Map<String, Object> getBusWkfMap(@RequestBody List<MfSysKind> mfSysKindList) throws Exception ;

	/**
	 * 方法描述： 根据当前操作员获取可以操作的产品种类列表
	 *
	 * @param user
	 * @return
	 * @throws Exception
	 *             List<MfSysKind>
	 * @author YuShuai
	 * @date 2017-7-2 下午5:41:14
	 */
	@RequestMapping(value = "/mfSysKind/getSysKindListByUser", method = RequestMethod.POST)
	public List<MfSysKind> getSysKindListByUser(@RequestBody JSONObject user) throws Exception ;

	/**
	 *
	 * 方法描述： 获取产品下的审批流程信息
	 *
	 * @param mfSysKindList
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @author zhs
	 * @date 2017-7-3 下午2:39:10
	 */
	@RequestMapping(value = "/mfSysKind/getApprovalWkfMap", method = RequestMethod.POST)
	public Map<String, Object> getApprovalWkfMap(@RequestBody List<MfSysKind> mfSysKindList) throws Exception ;

	/**
	 *
	 * 方法描述： 获取节点下的配置信息
	 *
	 * @param mfSysKind
	 * @param mfKindNode
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @author zhs
	 * @date 2017-7-7 下午4:33:24
	 */
	@RequestMapping(value = "/mfSysKind/getNodeConfigMap", method = RequestMethod.POST)
	public Map<String, Object> getNodeConfigMap(@RequestBody MfSysKind mfSysKind, @RequestParam("mfKindNode") MfKindNode mfKindNode)
			throws Exception ;

	/**
	 * @Description:获取部门信息
	 * @param orgLisg
	 * @param mfSysKind
	 * @return
	 * @author: 李伟
	 * @date: 2017-7-7 上午10:31:29
	 */
	@RequestMapping(value = "/mfSysKind/getBrNoName", method = RequestMethod.POST)
	public String getBrNoName(@RequestBody List<SysOrg> orgLisg, @RequestParam("mfSysKind") MfSysKind mfSysKind) throws Exception ;

	@RequestMapping(value = "/mfSysKind/getCalcConfigMap", method = RequestMethod.POST)
	public Map<String, Object> getCalcConfigMap(@RequestBody List<MfSysKind> mfSysKindList) throws Exception ;

	/**
	 *
	 * 方法描述： 获取产品配置页面的产品列表信息
	 *
	 * @param mfSysKind
	 * @return
	 * @throws Exception
	 *             List<MfSysKind>
	 * @author zhs
	 * @date 2017-7-10 下午5:18:01
	 */
	@RequestMapping(value = "/mfSysKind/getKindListForConfig", method = RequestMethod.POST)
	public List<MfSysKind> getKindListForConfig(@RequestBody MfSysKind mfSysKind) throws Exception ;

	/**
	 * 方法描述： 获取该产品种类下的贷款用途
	 *
	 * @param kindNo
	 * @return
	 * @throws Exception
	 *             JSONArray
	 * @author YuShuai
	 * @date 2017-7-17 下午5:06:32
	 */
	@RequestMapping(value = "/mfSysKind/getFincUse", method = RequestMethod.POST)
	public JSONArray getFincUse(@RequestBody String kindNo) throws Exception ;

	/**
	 * 方法描述： 获取启用的产品所对应的业务模式编号
	 *
	 * @return
	 * @throws Exception
	 *             List<String>
	 * @author YuShuai
	 * @date 2017-8-11 上午10:55:12
	 */
	@RequestMapping(value = "/mfSysKind/getUsedBusModel", method = RequestMethod.POST)
	public List<String> getUsedBusModel() throws Exception ;


	/**
	 *
	 * 方法描述：获取还款顺序
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author WD
	 * @date 2018-2-27 下午2:28:48
	 */
	@RequestMapping(value = "/mfSysKind/getRepaymentOrderTypeList", method = RequestMethod.POST)
	public Map<String, Object> getRepaymentOrderTypeList()throws Exception;

	@RequestMapping(value = "/mfSysKind/getWorkflowId", method = RequestMethod.POST)
	public String getWorkflowId(@RequestParam("appWorkflowNo") String appWorkflowNo) throws Exception;
	/**
	 *
	 * 方法描述： 产品设置页面产品列表
	 * @param mfSysKind
	 * @return
	 * @throws Exception
	 * List<MfSysKind>
	 * @author zhs
	 * @date 2018年5月4日 下午4:14:19
	 */
	@RequestMapping(value = "/mfSysKind/getSysKindListForPrdct", method = RequestMethod.POST)
	public List<MfSysKind> getSysKindListForPrdct(@RequestBody MfSysKind mfSysKind) throws Exception;
	/**
	 *
	 * 方法描述： 根据产品编号和配置类型获取产品的配置信息
	 * @param kindNo
	 * @param configType
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author zhs
	 * @date 2018年5月4日 下午4:29:24
	 */
	@RequestMapping(value = "/mfSysKind/getConfigByKindNo", method = RequestMethod.POST)
	public Map<String, Object> getConfigByKindNo(@RequestParam("kindNo") String kindNo, @RequestParam("configType") String configType)  throws Exception;

	/**
	 *
	 * 方法描述：获取公共的配置信息
	 * @param configType
	 * @return
	 * Map<String,Object>
	 * @author zhs
	 * @date 2018年5月5日 上午9:04:13
	 */
	@RequestMapping(value = "/mfSysKind/getKindPubConfig", method = RequestMethod.POST)
	public Map<String, Object> getKindPubConfig(@RequestParam("configType") String configType) throws Exception;


	@RequestMapping(value = "/mfSysKind/getCreditConfigByKindNo",  method = RequestMethod.POST)
	public Map<String, Object> getCreditConfigByKindNo(@RequestParam("kindNo") String kindNo,@RequestParam("creditModel") String creditModel, @RequestParam("configType") String configType)  throws Exception;

}
