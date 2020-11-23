package  app.component.collateralinterface;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.assure.entity.MfAssureInfo;
import app.component.collateral.entity.EvalInfo;
import app.component.collateral.entity.KeepInfo;
import app.component.collateral.entity.MfBusCollateralDetailRel;
import app.component.collateral.entity.MfBusCollateralRel;
import app.component.collateral.entity.MfCollateralClass;
import app.component.collateral.entity.MfCollateralTable;
import app.component.collateral.entity.MfPledgeStatus;
import app.component.collateral.entity.MfReceivablesTransferInfo;
import app.component.collateral.entity.PledgeBaseInfo;
import app.component.collateral.entity.PledgeBaseInfoBill;
import app.util.toolkit.Ipage;


/**
* Title: collateralinterface.java
* Description:查询客户名下押品
* @author:huyanming@dhcc.com.cn
* @Wed Mar 29 17:52:35 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface CollateralInterfaceFeign {
	/**
	* Title: collateralinterface.java
	* Description:查询客户名下押品
	* @author:huyanming@dhcc.com.cn
	* @Wed Mar 29 17:52:35 CST 2017
	**/
	@RequestMapping(value = "/collateralInterface/relateCollateral")
	public void relateCollateral(@RequestBody String collateralId);
	/**
	* Title: collateralinterface.java
	* Description:查询客户名下押品
	* @author:huyanming@dhcc.com.cn
	* @Wed Mar 29 17:52:35 CST 2017
	**/
	@RequestMapping(value = "/collateralInterface/releaseCollateral")
	public void releaseCollateral(@RequestBody String collateralId);
	/**
	* Title: collateralinterface.java
	* Description:修改押品状态为（已抵押、已质押、已转让）
	* @param dataMap(appId,keepStatus(2：已抵押、3：已质押、4：已转让))
	* @author:hgx
	* @date 2017-5-5 16:30:31
	**/
	@RequestMapping(value = "/collateralInterface/UpPledImpTranStatus")
	public void UpPledImpTranStatus(@RequestBody Map<String,Object> dataMap) throws Exception;
	/**
	* Title: collateralinterface.java
	* Description:更新修改押品
	* @param 
	* @author:ywh
	* @date 2017-7-5 16:30:31
	**/
	@RequestMapping(value = "/collateralInterface/update")
	public void update(@RequestBody MfBusCollateralRel mfBusCollateralRel) throws Exception;
	/**
	* Title: collateralinterface.java
	* Description:获取押品
	* @param 
	* @author:ywh
	* @date 2017-7-5 16:30:31
	**/
	@RequestMapping(value = "/collateralInterface/getMfBusCollateralRelById")
	public MfBusCollateralRel getMfBusCollateralRelById(@RequestBody MfBusCollateralRel mfBusCollateralRel) throws Exception;
	/**
	* Title: collateralinterface.java
	* Description:入库押品
	* @author:huyanming@dhcc.com.cn
	* @Wed Mar 29 17:52:35 CST 2017
	**/
	@RequestMapping(value = "/collateralInterface/instockCollateral")
	public void instockCollateral(@RequestBody List<KeepInfo> keepInfoList,@RequestParam("opNo") String opNo) throws Exception;
	/**
	* Title: collateralinterface.java
	* Description:修改押品状态为（折让）
	* @param PledgeBaseInfo
	* @author:hgx
	* @date 2017-5-5 16:30:31
	**/
	@RequestMapping(value = "/collateralInterface/discounting")
	public void discounting(@RequestBody PledgeBaseInfo pledgeBaseInfo) throws Exception;
	/**
	* Title: collateralinterface.java
	* Description:修改押品状态为（冻结）
	* @param PledgeBaseInfo
	* @author:hgx
	* @date 2017-5-5 16:30:31
	**/
	@RequestMapping(value = "/collateralInterface/freezing")
	public void freezing(@RequestBody PledgeBaseInfo pledgeBaseInfo) throws Exception;
	/**
	* Title: collateralinterface.java
	* Description:修改押品状态为（解冻）
	* @param PledgeBaseInfo
	* @author:hgx
	* @date 2017-5-5 16:30:31
	**/
	@RequestMapping(value = "/collateralInterface/relieving")
	public void relieving(@RequestBody PledgeBaseInfo pledgeBaseInfo) throws Exception;
	/**
	* Title: collateralinterface.java
	* Description:修改押品状态为（反转让）
	* @param PledgeBaseInfo
	* @author:hgx
	* @date 2017-5-5 16:30:31
	**/
	@RequestMapping(value = "/collateralInterface/disTransfering")
	public void disTransfering(@RequestBody PledgeBaseInfo pledgeBaseInfo) throws Exception;
	/**
	* Title: collateralinterface.java
	* Description:出库押品
	* @author:huyanming@dhcc.com.cn
	* @Wed Mar 29 17:52:35 CST 2017
	**/
	@RequestMapping(value = "/collateralInterface/outstockCollateral")
	public void outstockCollateral(@RequestBody List<KeepInfo> keepInfoList,@RequestParam("opNo") String opNo) throws Exception;
	/**
	* Title: collateralinterface.java
	* Description:处置押品
	* @author:huyanming@dhcc.com.cn
	* @Wed Mar 29 17:52:35 CST 2017
	**/
	@RequestMapping(value = "/collateralInterface/handleCollateral")
	public void handleCollateral(@RequestBody List<KeepInfo> keepInfoList) throws Exception;
	/**
	 * 
	 * 方法描述： 获得业务押品汇总信息
	 * @param mfBusCollateralRel
	 * @return
	 * @throws Exception
	 * MfBusCollateralRel
	 * @author 沈浩兵
	 * @date 2017-4-12 下午3:47:51
	 */
	@RequestMapping(value = "/collateralInterface/getBusCollateralRel")
	public MfBusCollateralRel getBusCollateralRel(@RequestBody MfBusCollateralRel mfBusCollateralRel)  throws Exception;
	/**
	 * 
	 * 方法描述： 获得业务押品汇总信息
	 * @param mfBusCollateralRel
	 * @return
	 * @throws Exception
	 * MfBusCollateralRel
	 * @author 沈浩兵
	 * @date 2017-4-12 下午3:47:51
	 */
	@RequestMapping(value = "/collateralInterface/getBusCollateralRelByAppId")
	public Map<String, Object> getBusCollateralRelByAppId(@RequestBody MfBusCollateralRel mfBusCollateralRel)  throws Exception;
	/**
	 * 
	 * 方法描述： 根据客户号获得客户下的押品
	 * @param pledgeBaseInfo
	 * @return
	 * @throws Exception
	 * List<PledgeBaseInfo>
	 * @author 沈浩兵
	 * @date 2017-5-4 上午8:56:31
	 */
	@RequestMapping(value = "/collateralInterface/getPledgeListByCusNo")
	public List<PledgeBaseInfo> getPledgeListByCusNo(@RequestBody PledgeBaseInfo pledgeBaseInfo) throws Exception;
	/**
	 * 
	 * 方法描述： 根据业务更新押品信息（押品状态改为已使用）
	 * @param dataMap（）
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-5-4 下午2:58:56
	 */
	@RequestMapping(value = "/collateralInterface/updatePledgeInfoByBuss")
	public void updatePledgeInfoByBuss(@RequestBody Map<String,Object> dataMap) throws Exception;
	/**
	 * 方法描述： 获取押品的公证和权证信息
	 * @param parMap
	 * @return
	 * @throws Exception
	 * String
	 * @author YuShuai
	 * @date 2017-5-18 上午11:58:44
	 */
	@RequestMapping(value = "/collateralInterface/getEvalCnt")
	public String getEvalCnt(@RequestBody Map<String, String> parMap) throws Exception;
	
	@RequestMapping(value = "/collateralInterface/insertCollateral")
	public Map<String, Object> insertCollateral (@RequestBody Map<String,Object> dataMap,@RequestParam("pledgeBaseInfo") PledgeBaseInfo pledgeBaseInfo) throws Exception;
	
	
	/**
	 * 方法描述：  根据业务编号获得关联的押品信息
	 * @param relId
	 * @return
	 * @throws ServiceException
	 * List<MfBusCollateralDetailRel>
	 * @author YuShuai
	 * @date 2017-6-2 下午6:54:27
	 */
	@RequestMapping(value = "/collateralInterface/getCollateralDetailRelList")
	public List<MfBusCollateralDetailRel> getCollateralDetailRelList(@RequestBody String relId) throws ServiceException;
	/**
	 * 方法描述：  押品信息总量
	 * @return
	 * @throws ServiceException
	 * List<MfBusCollateralDetailRel>
	 * @author QiZhao
	 * @date 2017-07-31 下午3:29:27
	 */
	@RequestMapping(value = "/collateralInterface/getCollateralDetailRel")
	public List<MfBusCollateralDetailRel> getCollateralDetailRel() throws ServiceException;
	
	/**
	 * 方法描述： 根据押品实体获取押品基本信息
	 * @param pledgeBase
	 * @return
	 * @throws ServiceException
	 * PledgeBase
	 * @author YuShuai
	 * @date 2017-6-2 下午6:58:43
	 */
	@RequestMapping(value = "/collateralInterface/getById")
	public PledgeBaseInfo getById(@RequestBody PledgeBaseInfo pledgeBaseInfo) throws ServiceException;
	
	/**
	 * 方法描述： 根据申请号获取该笔业务关联的所有押品未录入现状的列表
	 * @param appId
	 * @return
	 * @throws ServiceException
	 * List<MfPledgeStatus>
	 * @author YuShuai
	 * @date 2017-6-13 下午7:09:58
	 */
	@RequestMapping(value = "/collateralInterface/getPleStatusList")
	public List<MfPledgeStatus> getPleStatusList(@RequestBody String appId)throws ServiceException;
		
	/**
	 * 方法描述： 处理和业务关联的押品那些没有录入单项详细信息
	 * @param pledgeBaseInfo
	 * @param appId
	 * @param tableName
	 * @return
	 * @throws ServiceException
	 * List<PledgeBaseInfo>
	 * @author YuShuai
	 * @date 2017-6-15 上午9:56:07
	 */
	@RequestMapping(value = "/collateralInterface/getPledgeListByAppInfo")
	public List<PledgeBaseInfo> getPledgeListByAppInfo(
@RequestParam("pledgeBaseInfo") 			PledgeBaseInfo pledgeBaseInfo,@RequestParam("appId")  String appId,@RequestParam("tableName") String tableName)throws ServiceException;
	
	/**
	 * 
	 * 方法描述： 根据业务编号获得业务关联的货物明细
	 * @param appId
	 * @return
	 * @throws ServiceException
	 * List<PledgeBaseInfoBill>
	 * @author 沈浩兵
	 * @date 2017-6-17 下午5:40:35
	 */
	@RequestMapping(value = "/collateralInterface/getPledgeBaseInfoBillById")
	public List<PledgeBaseInfoBill> getPledgeBaseInfoBillById(@RequestBody String appId) throws ServiceException;
	/**
	 * 根据押品编号获取所有的清单明细
	 * @param pledgeNo 押品编号
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/collateralInterface/getPledgeDetailListByPledgeNo")
	public List<PledgeBaseInfoBill> getPledgeDetailListByPledgeNo(@RequestBody String pledgeNo)throws  ServiceException;
	
	
	
	/**
	 * 
	 * 方法描述： 新增押品动态表单信息
	 * @param mfCollateralTable
	 * @throws Exception
	 * void
	 * @author zhs
	 * @date 2017-7-22 下午12:32:16
	 */
	@RequestMapping(value = "/collateralInterface/insertMfCollateralTable")
	public void insertMfCollateralTable(@RequestBody MfCollateralTable mfCollateralTable) throws Exception;
	/**
	 * 
	 * 方法描述： 新增押品业务关联信息
	 * @param busCollateralRel
	 * @throws Exception
	 * void
	 * @author zhs
	 * @date 2017-7-22 下午12:32:56
	 */
	@RequestMapping(value = "/collateralInterface/insertMfBusCollateralRel")
	public void insertMfBusCollateralRel(@RequestBody MfBusCollateralRel busCollateralRel) throws Exception;
	/**
	 * 
	 * 方法描述： 押品主体信息和押品清单关联新增
	 * @param collateralDetailRel
	 * @throws Exception
	 * void
	 * @author zhs
	 * @date 2017-7-22 下午3:01:30
	 */
	@RequestMapping(value = "/collateralInterface/insertCollateralDetailRel")
	public void insertCollateralDetailRel(@RequestBody MfBusCollateralDetailRel collateralDetailRel)throws Exception;

	/**
	 * 保理业务流程中业务提交应收账款转让审批
	 * @param appId
	 * @return
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-7-28 下午6:27:24
	 */
	@RequestMapping(value = "/collateralInterface/submitProcess")
	public MfReceivablesTransferInfo submitProcess(@RequestBody String appId,@RequestParam("regNo") String regNo) throws Exception;

	/**
	 * 获取类别编号
	 * @param mfCollateralClass
	 * @return
	 * @throws Exception
	 * @author lzs
	 * @date 2017-8-7 上午午10:27:24
	 */ 
	@RequestMapping(value = "/collateralInterface/getCollateralClassById")
	public MfCollateralClass getCollateralClassById(@RequestBody  MfCollateralClass mfCollateralClass) throws Exception;
	/**
	 * 
	 * 方法描述： 根据押品业务关联编号获得应收账款转让状态
	 * @param appId
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2017-8-9 下午5:06:18
	 */
	@RequestMapping(value = "/collateralInterface/getReceivablesTransferSts")
	public String getReceivablesTransferSts(@RequestBody String appId) throws Exception;
	/**
	 * 
	 * 方法描述： 根据押品号获取评估信息
	 * @param evalbean
	 * @return
	 * @throws Exception
	 * List<EvalInfo>
	 * @author lzshuai
	 * @date 2017-8-12 下午4:42:03
	 */
	@RequestMapping(value = "/collateralInterface/getEvalInfoList")
	public List<EvalInfo> getEvalInfoList(@RequestBody EvalInfo evalbean) throws Exception;
	/**
	 * 根据申请号查询 押品评估
	 * @param appId
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/collateralInterface/getEvalInfosByAppId")
	public List<EvalInfo>  getEvalInfosByAppId(@RequestBody String appId) throws Exception;
	
	/**
	 * @Description:获取押品类别的分页数据 
	 * @param ipg
	 * @param pledgeBaseInfo
	 * @return
	 * @throws Exception
	 * @author: 李伟
	 * @date: 2017-8-28 下午10:23:43
	 */
	@RequestMapping(value = "/collateralInterface/findByPageMfCollateralClass")
	public Ipage findByPageMfCollateralClass(@RequestBody Ipage ipg,@RequestParam("mfCollateralClass") MfCollateralClass mfCollateralClass) throws Exception;
	/**
	 * 
	 * 方法描述： 根据业务编号获得关联的押品信息，并检查应收账款到期日不能大于当前日期加上融资期限的日期
	 * @param appId
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2017-8-31 下午3:37:38
	 */
	@RequestMapping(value = "/collateralInterface/checkPledgeExpireByPleNo")
	public String checkPledgeExpireByPleNo(@RequestBody String appId) throws Exception;
	/**
	 * 
	 * 方法描述： 业务否决，修改押品的引用标识为未引用，修改押品状态为登记状态
	 * @param relId
	 * @param bussType 正常业务business、授信业务credit、展期业务extension
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-9-23 下午4:48:07
	 */
	@RequestMapping(value = "/collateralInterface/updatePledgeStsByRelId")
	public void updatePledgeStsByRelId(@RequestBody String relId,@RequestParam("bussType") String bussType) throws Exception;
	/**
	 * 
	 * 方法描述： 放款审批和入库审批合并，放款审批完成根据选择是否同意入库标识业务关联押品入库
	 * @param relId
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-9-25 下午6:56:00
	 */
	@RequestMapping(value = "/collateralInterface/updatePledgeInstockByBuss")
	public void updatePledgeInstockByBuss(@RequestBody String relId) throws Exception;
	/**
	 * 方法描述：根据从合同编号,Id获取押品明细
	 * @param mfBusCollateralDetailRel
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/collateralInterface/getMfBusCollateralDetailRelById")
	public MfBusCollateralDetailRel getMfBusCollateralDetailRelById(@RequestBody MfBusCollateralDetailRel mfBusCollateralDetailRel)throws Exception;
	
	/**
	 * 
	 * 方法描述： 根据申请号获取保证列表
	 * @param appId
	 * @return
	 * @throws Exception
	 * List<MfAssureInfo>
	 * @author lwq
	 * @date 2017-10-14 上午10:53:05
	 */
	@RequestMapping(value = "/collateralInterface/getAssureListByAppid")
	public List<MfAssureInfo> getAssureListByAppid(@RequestBody String appId)throws Exception;
	
	/**
	 * 方法描述： 插入评估信息
	 * @param evalInfo
	 * @throws Exception
	 * void
	 * @author YuShuai
	 * @date 2017-10-26 下午2:11:44
	 */
	@RequestMapping(value = "/collateralInterface/insertEvalInfo")
	public void insertEvalInfo(@RequestBody EvalInfo evalInfo)throws Exception;
	/**
	 * 方法描述：根据appId获取保险总额
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/collateralInterface/getInsAmt")
	public Double getInsAmt(@RequestBody String appId)throws Exception;

	/**
	 * <P>
	 * 仅用于网信:
	 * </P>
	 * 系统规则授信环节，系统计算出的授信额度未显示给 审核经理； <br>
	 * 
	 * @param appId
	 * @return
	 * @throws ServiceException
	 * @author WangChao
	 * @date 2017-11-8 上午10:04:08
	 */
	@RequestMapping(value = "/collateralInterface/getCreditQuota")
	public Double getCreditQuota(@RequestBody String appId) throws ServiceException;
	
	/**
	 * 方法描述： 删除押品关联详情表
	 * @param detailRel
	 * @throws Exception
	 * void
	 * @author YuShuai
	 * @date 2017-12-20 下午2:43:44
	 */
	@RequestMapping(value = "/collateralInterface/deleteCollateralDetailRel")
	public void deleteCollateralDetailRel(@RequestBody MfBusCollateralDetailRel detailRel) throws Exception;
	/**
	 * 方法描述： 删除押品关联表
	 * @param detailRel
	 * @throws Exception
	 * void
	 * @author YuShuai
	 * @date 2017-12-20 下午2:43:44
	 */
	@RequestMapping(value = "/collateralInterface/deleteCollateralRel")
	public void deleteCollateralRel(@RequestBody MfBusCollateralRel busCollateralRel)throws Exception;
	
	
	/**
	 * 
	 * 方法描述： 根据客户号和classId获得所有押品信息
	 * @param cusNo
	 * @param ckassId
	 * @return
	 * @throws Exception
	 * List<PledgeBaseInfo>
	 * @author 沈浩兵
	 * @date 2017-12-28 上午11:51:34
	 */
	@RequestMapping(value = "/collateralInterface/getAllPledgeListByCusNo")
	public List<PledgeBaseInfo> getAllPledgeListByCusNo(@RequestBody String cusNo,@RequestParam("classId") String classId) throws Exception;
	/**
	 * 
	 * 方法描述： 新增押品信息
	 * @param pledgeBaseInfo
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-12-28 下午7:43:16
	 */
	@RequestMapping(value = "/collateralInterface/insertPledgeInfo")
	public void insertPledgeInfo(@RequestBody PledgeBaseInfo pledgeBaseInfo) throws Exception;
	/**
	 * 
	 * 方法描述： 更新押品信息
	 * @param pledgeBaseInfo
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-12-28 下午7:44:13
	 */
	@RequestMapping(value = "/collateralInterface/updatePledgeInfo")
	public void updatePledgeInfo(@RequestBody PledgeBaseInfo pledgeBaseInfo) throws Exception;
	/**
	 * 
	 * 方法描述： 根据押品编号删除押品信息
	 * @param pledgeNo
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-12-28 下午7:44:46
	 */
	@RequestMapping(value = "/collateralInterface/deletePledgeInfo")
	public void deletePledgeInfo(@RequestBody String pledgeNo) throws Exception;
	/**
	 * 方法描述：押品到期查询
	 * @param ipage
	 * @param pledgeBaseInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/collateralInterface/getPledgeToDateByPage")
	public Ipage getPledgeToDateByPage(@RequestBody Ipage ipage,@RequestParam("pledgeBaseInfo") PledgeBaseInfo pledgeBaseInfo) throws Exception;
	
	/**
	 * 
	 * 方法描述： 根据业务编号获得关联的押品详细信息
	 * @param appId
	 * @return
	 * @throws ServiceException
	 * List<PledgeBaseInfo>
	 * @author 段泽宇
	 * @date 2018-7-4 上午10:47:25
	 */
	@RequestMapping(value = "/collateralInterface/getPledgeBaseInfoListByAppId")
	public List<PledgeBaseInfo> getPledgeBaseInfoListByAppId(@RequestParam("appId") String appId) throws Exception;

	@RequestMapping(value = "/collateralInterface/getCollateralClassJson")
	public JSONArray getCollateralClassJson() throws Exception;
}
