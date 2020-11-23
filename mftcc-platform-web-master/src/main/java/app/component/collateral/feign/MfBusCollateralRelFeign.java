package  app.component.collateral.feign;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.assure.entity.MfAssureInfo;
import app.component.collateral.entity.EvalInfo;
import app.component.collateral.entity.KeepInfo;
import app.component.collateral.entity.MfBusCollateralRel;
import app.component.collateral.entity.PledgeBaseInfo;
import app.util.toolkit.Ipage;
import net.sf.json.JSONArray;

/**
* Title: MfBusCollateralRelBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Wed Apr 12 14:37:16 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfBusCollateralRelFeign {
	
	@RequestMapping(value = "/mfBusCollateralRel/insert")
	public void insert(@RequestBody MfBusCollateralRel mfBusCollateralRel) throws Exception;
	
	@RequestMapping(value = "/mfBusCollateralRel/delete")
	public void delete(@RequestBody MfBusCollateralRel mfBusCollateralRel) throws ServiceException;
	
	@RequestMapping(value = "/mfBusCollateralRel/update")
	public void update(@RequestBody MfBusCollateralRel mfBusCollateralRel) throws ServiceException;
	
	@RequestMapping(value = "/mfBusCollateralRel/getById")
	public MfBusCollateralRel getById(@RequestBody MfBusCollateralRel mfBusCollateralRel) throws ServiceException;
	@RequestMapping(value = "/mfBusCollateralRel/getMfBusCollateralRelById")
	public MfBusCollateralRel getMfBusCollateralRelById(@RequestBody MfBusCollateralRel mfBusCollateralRel) throws ServiceException;

	/**
	 * 
	 * 方法描述： 更新担保信息
	 * @param mfBusCollateralRel
	 * @return
	 * @throws ServiceException
	 * MfBusCollateralRel
	 * @author 沈浩兵
	 * @date 2017-6-29 下午8:25:29
	 */
	@RequestMapping(value = "/mfBusCollateralRel/getCollateralByUpdate")
	public MfBusCollateralRel getCollateralByUpdate(@RequestBody MfBusCollateralRel mfBusCollateralRel) throws ServiceException;
	/**
	 * 
	 * 方法描述： 获得数据库存的值，未经处理的
	 * @param mfBusCollateralRel
	 * @return
	 * @throws ServiceException
	 * MfBusCollateralRel
	 * @author 沈浩兵
	 * @date 2017-6-14 下午4:29:06
	 */
	@RequestMapping(value = "/mfBusCollateralRel/getOriginalById")
	public MfBusCollateralRel getOriginalById(@RequestBody MfBusCollateralRel mfBusCollateralRel) throws ServiceException;
	
	@RequestMapping(value = "/mfBusCollateralRel/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfBusCollateralRel") MfBusCollateralRel mfBusCollateralRel) throws ServiceException;
	/**
	 * 
	 * 方法描述： 获得押品主体信息，根据传入的调用入口参数，
	 * 如果是流程节点入口，根据押品是否全部入库判断是否提交流程
	 * 如果是业务入库，返回信息跳转押品详情页面。
	 * @param dataMap
	 * @return
	 * @throws ServiceException
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-4-17 上午11:42:22
	 */
	@RequestMapping(value = "/mfBusCollateralRel/getBusCollateralDetailById")
	public Map<String,Object> getBusCollateralDetailById(@RequestBody Map<String,Object> dataMap) throws ServiceException;
	/**
	 * 方法描述： 获得引用的押品是否全部入库
	 * 1全部入库0存在未入库押品
	 * @param mfBusCollateralRel
	 * @return
	 * @throws ServiceException
	 * String
	 * @author 沈浩兵
	 * @date 2017-4-17 下午1:40:40
	 */
	@RequestMapping(value = "/mfBusCollateralRel/getAllCollaInstockflag")
	public String getAllCollaInstockflag(@RequestBody MfBusCollateralRel mfBusCollateralRel)  throws ServiceException;
	/**
	 * 
	 * 方法描述： 获得引用的押品是否全部出库
	 * @param mfBusCollateralRel
	 * @return
	 * @throws ServiceException
	 * String
	 * @author 沈浩兵
	 * @date 2017-4-17 下午4:09:25
	 */
	@RequestMapping(value = "/mfBusCollateralRel/getAllCollaOutstockflag")
	public String getAllCollaOutstockflag(@RequestBody MfBusCollateralRel mfBusCollateralRel)  throws ServiceException;
	/**
	 * 
	 * 方法描述： 获得押品是否全部出库,全部出库更新担保信息
	 * @param mfBusCollateralRel
	 * @return
	 * @throws ServiceException
	 * String
	 * @author 沈浩兵
	 * @date 2017-5-24 下午2:16:57
	 */
	@RequestMapping(value = "/mfBusCollateralRel/getAllCollaOutstockflagAndUpdate")
	public String getAllCollaOutstockflagAndUpdate(@RequestBody MfBusCollateralRel mfBusCollateralRel)  throws ServiceException;
	/**
	 * 
	 * 方法描述：  获得引用的押品是否全部入库,如果全部入库提交业务流程
	 * @param mfBusCollateralRel
	 * @return
	 * @throws ServiceException
	 * String
	 * @author 沈浩兵
	 * @date 2017-4-17 下午2:24:59
	 */
	@RequestMapping(value = "/mfBusCollateralRel/getInstockFlagAndSubmit")
	public Map<String,Object> getInstockFlagAndSubmit(@RequestBody MfBusCollateralRel mfBusCollateralRel)  throws ServiceException;
	/**
	 * 
	 * 方法描述： 业务流程中登记押品信息保存
	 * @param dataMap
	 * @throws ServiceException
	 * void
	 * @author 沈浩兵
	 * @date 2017-4-11 下午5:29:01
	 */
	@RequestMapping(value = "/mfBusCollateralRel/insertCollateral")
	public Map<String, Object> insertCollateral (@RequestBody Map<String,Object> dataMap) throws Exception;
	/**
	*@Description:
	*@Author: lyb
	*@date: 2018/9/11
	*/
	@RequestMapping(value = "/mfBusCollateralRel/insertHistory")
	public Map<String, Object> insertHistory (@RequestBody Map<String,Object> dataMap) throws Exception;
	/**
	 * 
	 * 方法描述： 押品置换信息保存
	 * @param dataMap
	 * @return
	 * @throws ServiceException
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-4-14 上午11:13:31
	 */
	@RequestMapping(value = "/mfBusCollateralRel/replaceCollateral")
	public Map<String, Object> replaceCollateral (@RequestBody Map<String,Object> dataMap) throws ServiceException;
	
	/**
	 * 
	 * 方法描述：  批量入库或出库保存
	 * @param dataMap
	 * @throws ServiceException
	 * void
	 * @author 沈浩兵
	 * @date 2017-4-17 下午5:10:11
	 */
	@RequestMapping(value = "/mfBusCollateralRel/inOrOutStockCollateralBatch")
	public KeepInfo inOrOutStockCollateralBatch(@RequestBody Map<String,Object> dataMap) throws ServiceException;
	/**
	 * 
	 * 方法描述： 解除押品
	 * @param dataMap
	 * @throws ServiceException
	 * void
	 * @author 沈浩兵
	 * @date 2017-5-2 上午10:17:19
	 */
	@RequestMapping(value = "/mfBusCollateralRel/deleteCollateralRel")
	public void deleteCollateralRel(@RequestBody Map<String,Object> dataMap) throws Exception;
	/**
	 * 
	 * 方法描述： 
	 * @param mfBusCollateralRel
	 * @return
	 * @throws ServiceException
	 * MfBusCollateralRel
	 * @author 沈浩兵
	 * @date 2017-5-12 上午11:06:02
	 */
	@RequestMapping(value = "/mfBusCollateralRel/getCollateralRelStsById")
	public MfBusCollateralRel getCollateralRelStsById(@RequestBody MfBusCollateralRel mfBusCollateralRel) throws ServiceException;

	/**
	 * 方法描述： 业务流中添加押品评估信息
	 * @param dataMap
	 * @return
	 * @throws ServiceException
	 * Map<String,Object>
	 * @author 谢静霞
	 * @date 2017-5-24 上午11:16:36
	 */
	@RequestMapping(value = "/mfBusCollateralRel/insertCollateralAndEval")
	public Map<String, Object> insertCollateralAndEval (@RequestBody Map<String,Object> dataMap) throws ServiceException;
	/**
	 * 
	 * 方法描述： 获得担保信息的操作痕迹，是否进行转让、折让、争议、反转让等
	 * @param mfBusCollateralRel
	 * @return
	 * @throws ServiceException
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-5-23 下午5:51:09
	 */
	@RequestMapping(value = "/mfBusCollateralRel/getCollateralRelTrace")
	public Map<String,Object> getCollateralRelTrace(@RequestBody MfBusCollateralRel mfBusCollateralRel) throws ServiceException;

	/**
	 * 方法描述： 获取公证处表单还是列表
	 * @param appId
	 * @return
	 * @throws ServiceException
	 * Map<String,Object>
	 * @author YuShuai
	 * @date 2017-6-3 下午3:16:24
	 */
	@RequestMapping(value = "/mfBusCollateralRel/getPleInfoListOrForm")
	public Map<String, Object> getPleInfoListOrForm(@RequestBody String appId)throws ServiceException;

	/**
	 * 方法描述： 获取权证处表单还是列表 
	 * @param appId
	 * @return
	 * @throws ServiceException
	 * Map<String,Object>
	 * @author YuShuai
	 * @date 2017-6-3 下午3:25:55
	 */
	@RequestMapping(value = "/mfBusCollateralRel/getWarrInfoListOrForm")
	public Map<String, Object> getWarrInfoListOrForm(@RequestBody String appId)throws ServiceException;
	
	/**
	 * 方法描述： 根据押品编号获取申请号
	 * @param collateralId
	 * @return
	 * @throws ServiceException
	 * String
	 * @author HGX
	 * @date 2017-6-7 下午3:25:55
	 */
	@RequestMapping(value = "/mfBusCollateralRel/getAppIdByCollateralId")
	public String getAppIdByCollateralId(@RequestBody String collateralId) throws ServiceException;
	/**
	 * 
	 * 方法描述： 动产质押担保信息整体状态
	 * @param mfBusCollateralRel
	 * @return
	 * @throws ServiceException
	 * MfBusCollateralRel
	 * @author 沈浩兵
	 * @date 2017-6-20 下午4:29:04
	 */
	@RequestMapping(value = "/mfBusCollateralRel/getCollateralRelStsForMoveable")
	public MfBusCollateralRel getCollateralRelStsForMoveable(@RequestBody MfBusCollateralRel mfBusCollateralRel) throws ServiceException;
	/**
	 * 
	 * 方法描述： 获得担保信息是否填写评估信息
	 * @param mfBusCollateralRel
	 * @return
	 * @throws ServiceException
	 * String
	 * @author 沈浩兵
	 * @date 2017-6-26 下午11:45:28
	 */
	@RequestMapping(value = "/mfBusCollateralRel/getEvalFlag")
	public String getEvalFlag(@RequestBody MfBusCollateralRel mfBusCollateralRel)  throws ServiceException;
	
	/**
	 * 
	 * 方法描述： 根据业务模式获得押品类别
	 * @param busModel
	 * @return
	 * @throws ServiceException
	 * JSONArray
	 * @author 沈浩兵
	 * @date 2017-6-29 下午5:03:00
	 */
	@RequestMapping(value = "/mfBusCollateralRel/getCollClassByBusModel")
	public JSONArray getCollClassByBusModel(@RequestParam("busModel") String busModel) throws ServiceException;

	/**
	 * 
	 * 方法描述：获取押品登记表单初始化信息 
	 * @param cusNo
	 * @param appId
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author zhs
	 * @param vouTypeDef 
	 * @date 2017-7-20 下午3:47:48
	 */
	@RequestMapping(value = "/mfBusCollateralRel/getCollateralFormInfo")
	public Map<String, Object> getCollateralFormInfo(@RequestParam("cusNo") String cusNo,@RequestParam("appId")  String appId,@RequestParam("entrFlag") String entrFlag,@RequestParam("entrance") String entrance,@RequestParam("vouTypeDef")  String vouTypeDef,@RequestParam("brNo")  String brNo,@RequestParam("opNo")  String opNo)  throws Exception;
	
	@RequestMapping("/mfBusCollateralRel/getReceivablesFormInfo")
	Map<String, Object> getReceivablesFormInfo(@RequestParam("cusNo") String cusNo,@RequestParam("appId") String appId,@RequestParam("entrFlag") String entrFlag,@RequestParam("entrance") String entrance) throws Exception;
	/**
	 * 
	 * 方法描述： 获得业务中使用的押品，过滤掉当前场景已使用的押品
	 * @param appId 业务申请号
	 * @param pledgeNoStr 要过滤的押品编号，多个押品编号字符串
	 * @return
	 * @throws Exception
	 * Map<String, Object>
	 * @author 沈浩兵
	 * @date 2017-8-15 上午11:49:38
	 */
	@RequestMapping(value = "/mfBusCollateralRel/getBussPledgeData")
	public Map<String, Object> getBussPledgeData(@RequestParam("appId") String appId,@RequestParam("pledgeNoStr") String pledgeNoStr) throws Exception;

	/**
	 * 
	 * 方法描述： 主担保方式是信用担保时，跳过担保登记业务节点 
	 * @param appId
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author zhs
	 * @date 2017-9-12 下午2:12:15
	 */
	@RequestMapping(value = "/mfBusCollateralRel/submitBussProcess")
	public Map<String, Object> submitBussProcess(@RequestParam("appId") String appId,@RequestParam("opNo") String opNo) throws Exception;
	/**
	 * 
	 * 方法描述： 开启入库审批流程或出库审批流程
	 * @param relId
	 * @param keepInfo
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-9-26 下午8:05:57
	 */
	@RequestMapping(value = "/mfBusCollateralRel/submitInOrOutStockProcess")
	public void submitInOrOutStockProcess(@RequestParam("relId") String relId,@RequestBody KeepInfo keepInfo) throws Exception;
	/**
	 * 
	 * 方法描述： 根据出入库状态，设置入库出库按钮是否可操作
	 * 出入库审批中时，展示“出库中”或“入库中”，且不可编辑
	 * @param appId
	 * @param pledgeId
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-9-27 上午10:54:45
	 */
	@RequestMapping(value = "/mfBusCollateralRel/getInOutStockDisabledFlag")
	public Map<String, Object> getInOutStockDisabledFlag(@RequestParam("appId") String appId,@RequestParam("pledgeId") String pledgeId) throws Exception;
	
	/**
	 * 
	 * 方法描述： 根据申请号获取保证人列表
	 * @param appId
	 * @return
	 * @throws ServiceException
	 * List<MfAssureInfo>
	 * @author lwq
	 * @date 2017-10-14 上午10:49:08
	 */
	@RequestMapping(value = "/mfBusCollateralRel/getAssureListByAppid")
	public List<MfAssureInfo> getAssureListByAppid(@RequestParam("appId") String appId)throws Exception;
	/**
	 * 
	 * 方法描述： 获得业务关联的押品是否进行了押品评估
	 * @param appId
	 * @param pledgeNo
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2017-10-19 下午8:02:56
	 */
	@RequestMapping(value = "/mfBusCollateralRel/getPledgeEvalFlag")
	public String getPledgeEvalFlag(@RequestParam("appId") String appId,@RequestParam("pledgeNo") String pledgeNo) throws Exception;
	
	/**
	 * @Description: 页面担保金额展示
	 * 展示规则:
	 * 参数为1时：显示担保累加金额
	 * 参数为2时：客户录入的担保累加总额>贷款金额，显示贷款金额，<=贷款金额，显示担保累加金额
	 * 参数为3时，展示规则同2，只是贷款金额取的是批复金额（合同金额），在业务审批过程中修改了申请金额的情况下，取最终的合同金额展示
	 * @param collateralAmt
	 * @param appId
	 * @return
	 * @author: 李伟
	 * @date: 2017-11-22 上午10:50:40
	 */
	@RequestMapping(value = "/mfBusCollateralRel/getAssureAmtShow")
	public Double getAssureAmtShow(@RequestParam("collateralAmt") Double collateralAmt,@RequestParam("appId") String appId,@RequestParam("collateralType") String collateralType) throws Exception;
	
	/**
	 * 方法描述：插入押品价值和数量预警信息 
	 * @param pledgeBaseInfo
	 * @throws Exception
	 * void
	 * @author YuShuai
	 * @date 2017-11-20 下午7:18:16
	 */
	@RequestMapping(value = "/mfBusCollateralRel/insertPledgeWarnMsg")
	public void insertPledgeWarnMsg(@RequestBody PledgeBaseInfo pledgeBaseInfo) throws Exception;
	
	/**
	 * 方法描述：插入押品价值和数量预警信息 (移动端)
	 * @param pledgeBaseInfo
	 * @throws Exception
	 * void
	 * @author YuShuai
	 * @date 2017-11-20 下午7:18:16
	 */
	@RequestMapping(value = "/mfBusCollateralRel/insertPledgeWarnMsgForApp")
	public void insertPledgeWarnMsgForApp(@RequestBody PledgeBaseInfo pledgeBaseInfo) throws Exception;
	/**
	 * 
	 * 方法描述： 根据业务编号更新担保总额和担保比例
	 * @param appId
	 * @throws ServiceException
	 * void
	 * @author 沈浩兵
	 * @date 2017-12-21 下午3:51:42
	 */
	@RequestMapping(value = "/mfBusCollateralRel/updateCollateralAmtByAppId")
	public void updateCollateralAmtByAppId(@RequestBody String appId) throws Exception;


	/**
	 * 方法描述： 获取押品关联列表
	 * @param mfBusCollateralRel
	 * @return
	 * @throws Exception
	 * List<MfBusCollateralRel>
	 * @author YuShuai
	 * @date 2017-12-27 上午10:16:50
	 */
	@RequestMapping(value = "/mfBusCollateralRel/getCollateralRelList")
	public List<MfBusCollateralRel> getCollateralRelList(
			MfBusCollateralRel mfBusCollateralRel)throws Exception;
	/**
	 * 
	 * 方法描述： 业务流程节点中批量入库或出库保存
	 * @param keepInfo
	 * @return
	 * @throws ServiceException
	 * KeepInfo
	 * @author 沈浩兵
	 * @date 2018-1-3 下午2:20:52
	 */
	@RequestMapping(value = "/mfBusCollateralRel/inOrOutStockBatchForBuss")
	public KeepInfo inOrOutStockBatchForBuss(@RequestBody KeepInfo keepInfo) throws ServiceException;
	
	/**
	 * 方法描述： 根据申请号得到押品信息
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mfBusCollateralRel/getPledgeBaseInfoListByAppId")
	public List<PledgeBaseInfo> getPledgeBaseInfoListByAppId(@RequestBody String appId) throws Exception;

	/**
	 * 方法描述：获取信息采集表单内容（应收账款、租赁物信息采集）
	 * @param cusNo
	 * @param appId
	 * @param entrFlag
	 * @param entrance
	 * @param vouType
	 * @param infoType
	 * @return
	 */
	@RequestMapping(value = "/mfBusCollateralRel/getInfoCollectFormInfo")
	public Map<String,Object> getInfoCollectFormInfo(@RequestParam("cusNo") String cusNo, @RequestParam("appId")  String appId, @RequestParam("entrFlag")  String entrFlag, @RequestParam("entrance")  String entrance, @RequestParam("vouType")  String vouType, @RequestParam("infoType")  String infoType) throws Exception;

	/**
	 * 获取担保比例
	 * @param mfBusCollateralRel
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mfBusCollateralRel/getCollateralRate")
	public double getCollateralRate(@RequestBody MfBusCollateralRel mfBusCollateralRel) throws  Exception;

	/**
	 *
	 * 方法描述： 获得业务中使用的押品，过滤掉当前场景已使用的押品
	 * @param appId 业务申请号
	 * @param pledgeNoStr 要过滤的押品编号，多个押品编号字符串,关联类型
	 * @return
	 * @throws Exception
	 * Map<String, Object>
	 * @author 段泽宇
	 * @date 2018-8-17 上午11:49:38
	 */
	@RequestMapping(value = "/mfBusCollateralRel/getBussPledgeDataByCollateralTypeAjax")
    public Map<String,Object> getBussPledgeDataByCollateralTypeAjax(@RequestParam("appId") String appId,@RequestParam("pledgeNoStr") String pledgeNoStr,@RequestParam("collateralType") String collateralType) throws  Exception;
	
	/**
	 * 方法描述： 授信跳过担保
	 * @param appId
	 * @param regNo
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 仇招
	 * @date 2018年8月30日 上午10:47:14
	 */
	@RequestMapping(value = "/mfBusCollateralRel/submitCreditProcessAjax")
	public Map<String, Object> submitCreditProcessAjax(@RequestParam("appId") String appId,@RequestParam("regNo") String regNo) throws  Exception;
	/**
	 * 方法描述： 根据业务编号查询所有关联担保信息
	 * @param mfBusCollateralRel
	 * @return
	 * @throws Exception
	 * List<MfBusCollateralRel>
	 * @author 仇招
	 * @date 2018年8月30日 下午6:43:03
	 */
	@RequestMapping(value = "/mfBusCollateralRel/getCollateralInfoListAjax")
	public List<MfBusCollateralRel> getCollateralInfoListAjax(@RequestBody MfBusCollateralRel mfBusCollateralRel) throws  Exception;


	@RequestMapping(value = "/mfBusCollateralRel/findByPageAppId")
	public Ipage findByPageAppId(@RequestBody Ipage ipage) throws ServiceException;

	/**
	 *@描述 校验是否可以跳过担保信息环节
	 *@参数 appId
	 *@返回值
	 *@创建人  shenhaobing
	 *@创建时间  2019/3/12
	 *@修改人和其它信息
	 */
	@RequestMapping(value = "/mfBusCollateralRel/getAllowSkipPledgeRegFlag")
	public Map<String, Object> getAllowSkipPledgeRegFlag(@RequestParam("appId") String appId,@RequestParam("entrance") String entrance) throws Exception;
}
