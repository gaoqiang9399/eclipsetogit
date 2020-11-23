package  app.component.calcinterface;

import java.util.List;
import java.util.Map;

import app.component.prdct.entity.MfKindNodeFee;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.calc.core.entity.MfBusOverBaseRecord;
import app.component.calc.fee.entity.MfBusAppFee;
import app.component.calc.fee.entity.MfBusFeeDetail;
import app.component.calc.fee.entity.MfSysFeeItem;
import app.component.pact.entity.MfBusPact;
import app.util.toolkit.Ipage;


/**
* Title: calcinterface.java
* Description:
* @author:cuizk@dhcc.com.cn
* @Tue May 24 08:57:26 CST 2016
**/

@FeignClient("mftcc-platform-factor")
public interface CalcInterfaceFeign {
	@RequestMapping(value = "/calcInterface/getFeeItemList")
	public List<MfSysFeeItem> getFeeItemList(@RequestBody String feeStdNo) throws Exception;
	/**
	 * @author czk
	 * @Description: 获取某业务相关的费用。只传appId或pactId，则获得该业务相关所有费用项，再传takeNode，则获取该业务某节点的费用项
	 * date 2016-7-28
	 * @param appId
	 * @param pactId
	 * @param takeNode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/calcInterface/getFeeItemList")
	public List<MfBusAppFee> getFeeItemList(@RequestBody String appId,@RequestParam("pactId") String pactId,@RequestParam("takeNode") String takeNode) throws Exception;

	/**
	 * @author czk
	 * @Description: 新增申请后，向业务费用表中插入记录。
	 * date 2016-8-4
	 * @param feeStdNo
	 * @throws Exception
	 */
	@RequestMapping(value = "/calcInterface/insertBusAppFee")
	public void insertBusAppFee(@RequestBody String appId,@RequestParam("feeStdNo") String feeStdNo) throws Exception;
	
	@RequestMapping(value = "/calcInterface/updateBusAppFee")
	public void updateBusAppFee(@RequestBody List<MfBusAppFee> mfBusAppFeeList) throws Exception;
	
	/**
	 * @author czk
	 * @Description: 计算某业务某节点的计算后的应收费用。
	 * date 2016-8-30
	 * @param pactId
	 * @param takeNode 收费节点
	 * @return 计算好的值放在字段rateScale中，小数点后保留2位。
	 * @throws Exception
	 */
	@RequestMapping(value = "/calcInterface/getBusFeeList")
	public List<MfBusAppFee> getBusFeeList(@RequestBody String pactId,@RequestParam("takeNode") String takeNode) throws Exception;
	
	/**
	 * @author czk
	 * @Description: 在生成合同时，根据appId，更新mf_bus_app_fee表中的pactId
	 * mf_bus_app_fee是业务-费用关联表，业务刚申请时只和appId关联了，当生成合同时，需要将pactId更新上
	 * date 2016-11-19
	 */
	@RequestMapping(value = "/calcInterface/updateBusAppFeePactId")
	public void updateBusAppFeePactId(@RequestBody String appId,@RequestParam("pactId") String pactId) throws Exception;
	
	@RequestMapping(value = "/calcInterface/insertBusFeeDetail")
	public void insertBusFeeDetail(@RequestBody MfBusFeeDetail mfBusFeeDetail) throws Exception;
	
	@RequestMapping(value = "/calcInterface/deleteMfBusAppFee")
	public void deleteMfBusAppFee(@RequestBody MfBusAppFee mfBusAppFee) throws Exception;
	
	@RequestMapping(value = "/calcInterface/deleteMfBusFeeDetail")
	public void deleteMfBusFeeDetail(@RequestBody MfBusFeeDetail mfBusFeeDetail) throws Exception;
	/**
	 * 
	 * 方法描述： 
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-3-31 下午5:35:21
	 */
	@RequestMapping(value = "/calcInterface/deleteFeeItem")
	public void deleteFeeItem(@RequestBody MfSysFeeItem mfSysFeeItem) throws Exception;
	
	/**
	 * 方法描述： 根据申请号，itemno,kindno更新费用信息
	 * @param mfBusAppFeeList
	 * @throws Exception
	 * void
	 * @author YuShuai
	 * @date 2017-6-12 下午6:07:29
	 */
	@RequestMapping(value = "/calcInterface/updateAppByappId")
	public void updateAppByappId(@RequestBody List<MfBusAppFee> mfBusAppFeeList) throws Exception;
	
	/**
	 * 方法描述： 获取费用信息
	 * @param mfBusPact
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author YuShuai
	 * @date 2017-8-10 下午5:58:09
	 */
	@RequestMapping(value = "/calcInterface/getFeeInfo")
	public Map<String, Object> getFeeInfo(@RequestBody MfBusPact mfBusPact)throws Exception;
	/**
	 * 
	 * 方法描述： 查看 逾期基数情况记录表 中信息 查看该期还款  再逾期基数情况表中 最近一次的数据记录 一条
	 * @param busOverBaseRecord
	 * @return
	 * @throws Exception
	 * MfBusOverBaseRecord
	 * @author 沈浩兵
	 * @date 2017-10-16 下午4:46:09
	 */
	@RequestMapping(value = "/calcInterface/getMfBusOverBaseRecordByBean")
	public MfBusOverBaseRecord getMfBusOverBaseRecordByBean(@RequestBody MfBusOverBaseRecord busOverBaseRecord)throws Exception; 
	
	/**
	 * 
	 * 方法描述： 获取产品下的费用配置
	 * @param ipage
	 * @return
	 * @throws Exception
	 * Ipage
	 * @author zhs
	 * @date 2017-12-14 下午2:12:04
	 */
	@RequestMapping(value = "/calcInterface/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	/**
	 * 
	 * 方法描述： 保存来自表单上的费用信息，与业务关联
	 * @param appId
	 * @param parmMap
	 * @throws Exception
	 * void
	 * @author zhs
	 * @date 2017-12-14 下午6:25:42
	 */
	@RequestMapping(value = "/calcInterface/insertBusAppFormFee")
	public void insertBusAppFormFee(@RequestBody String appId,@RequestParam("parmMap")  Map<String, Object> parmMap) throws Exception;
	/**
	 * 
	 * 方法描述：获取产品
	 * @param mfSysFeeItem
	 * @return
	 * @throws Exception
	 * MfSysFeeItem
	 * @author zhs
	 * @date 2017-12-28 下午4:54:37
	 */
	@RequestMapping(value = "/calcInterface/getMfSysFeeItem")
	public MfSysFeeItem getMfSysFeeItem(@RequestBody MfSysFeeItem mfSysFeeItem)throws Exception;
	/**
	 * 
	 * 方法描述：获取还款时收取的费用项按收取顺序获取  （返回的费用项按照顺序排列）
	 * @param busAppFee
	 * @return  feeSumStr 1@2@3
	 * @throws Exception 
	 * Map<String,String>
	 * @author WD
	 * @date 2018-1-5 下午5:13:47
	 */
	@RequestMapping(value = "/calcInterface/getFeeItemInfoByBusAppFee")
	public Map<String, String> getFeeItemInfoByBusAppFee(@RequestBody MfBusAppFee busAppFee)throws Exception;
	/**
	 *
	 * 方法描述：根据节点，产品，以及费用项获取修改权限  （返回的费用项按照顺序排列）
	 * @param mfKindNodeFee
	 * @return  feeSumStr 1@2@3
	 * @throws Exception
	 * Map<String,String>
	 * @author WD
	 * @date 2018-1-5 下午5:13:47
	 */
	@RequestMapping(value = "/calcInterface/getMfKindNodeFee")
	public MfKindNodeFee getMfKindNodeFee(@RequestBody MfKindNodeFee mfKindNodeFee)throws Exception;

	/**
	*@desc 查询产品下配置的费用关联申请节点费用查询权限控制
	*@author lwq        
	*@date 2018/7/6 19:38
	*@parm [ipage]
	*@return app.util.toolkit.Ipage
	**/
	@RequestMapping(value = "/calcInterface/findFeeByPage")
	public Ipage findFeeByPage(@RequestBody Ipage ipage) throws Exception;
}


