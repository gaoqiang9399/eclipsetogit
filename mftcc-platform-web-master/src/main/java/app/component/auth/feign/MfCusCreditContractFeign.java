package  app.component.auth.feign;

import java.util.List;
import java.util.Map;

import app.component.pss.utils.PssEnumBean;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.auth.entity.MfCusCreditApproveInfo;
import app.component.auth.entity.MfCusCreditContract;
import app.util.toolkit.Ipage;

/**
* Title: MfCusCreditContractBo.java
* Description:授信协议业务控制
* @author:LJW
* @Tue Mar 07 15:39:22 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfCusCreditContractFeign {
	
	@RequestMapping(value = "/mfCusCreditContract/insert")
	public void insert(@RequestBody MfCusCreditContract mfCusCreditContract) throws ServiceException;
	/**
	 * 
	 * 方法描述： 授信审批结束生产授信合同信息
	 * @param mfCusCreditContract
	 * @throws ServiceException
	 * void
	 * @author 沈浩兵
	 * @date 2017-6-27 下午10:42:45
	 */
	@RequestMapping(value = "/mfCusCreditContract/insertApproveEnd")
	public void insertApproveEnd(@RequestBody String creditType,@RequestParam("mfCusCreditApproveInfo") MfCusCreditApproveInfo mfCusCreditApproveInfo) throws ServiceException;
	
	@RequestMapping(value = "/mfCusCreditContract/delete")
	public void delete(@RequestBody MfCusCreditContract mfCusCreditContract) throws ServiceException;
	
	@RequestMapping(value = "/mfCusCreditContract/update")
	public void update(@RequestBody MfCusCreditContract mfCusCreditContract) throws ServiceException;
	
	@RequestMapping(value = "/mfCusCreditContract/updateIsValidByOldPactNo")
	public void updateIsValidByOldPactNo(@RequestBody MfCusCreditContract mfCusCreditContract) throws ServiceException;
	
	@RequestMapping(value = "/mfCusCreditContract/getById")
	public MfCusCreditContract getById(@RequestBody MfCusCreditContract mfCusCreditContract) throws ServiceException;
	
	@RequestMapping(value = "/mfCusCreditContract/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfCusCreditContract") MfCusCreditContract mfCusCreditContract) throws ServiceException;

	@RequestMapping(value = "/mfCusCreditContract/findStampContract")
	public Ipage findStampContract(@RequestBody Ipage ipage) throws ServiceException;
	
	/**
	 * 根据授信申请id查询授信协议信息
	 * @author LJW
	 * date 2017-3-7
	 */
	@RequestMapping(value = "/mfCusCreditContract/getByCreditAppId")
	public MfCusCreditContract getByCreditAppId(@RequestBody MfCusCreditContract mfCusCreditContract) throws ServiceException;
	
	/**
	 * 
	 * 方法描述： 授信不经过审批直接提交至授信协议阶段数据处理
	 * parm wkfAppId
	 * @throws Exception
	 * void
	 * @author zhs
	 * @date 2017-8-25 上午9:24:07
	 */
	@RequestMapping(value = "/mfCusCreditContract/submitCreditContract")
	public void submitCreditContract(@RequestBody MfCusCreditContract mfCusCreditContract) throws Exception;
	
	@RequestMapping(value = "/mfCusCreditContract/getMfCusCreditContractList")
	public List<MfCusCreditContract> getMfCusCreditContractList(@RequestBody MfCusCreditContract mfCusCreditContract) throws ServiceException;
	
	/**
	 * 
	 * 方法描述： 验证申请金额是否大于最高额合同额度、资金机构总额、资金机构项目额度
	 * 先校验是否大于最高额合同额度
	 * 不大于最高额合同额度情况下再去是否大于校验资金机构总额、资金机构项目额度
	 * @param creditPactId
	 * @param creditProjectId
	 * @param cusNoFund
	 * @param appAmt
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2018年4月24日 上午9:39:04
	 */
	@RequestMapping(value = "/mfCusCreditContract/checkCreditPactAmt")
	public Map<String, Object> checkCreditPactAmt(@RequestParam("creditPactId") String creditPactId,@RequestParam("creditProjectId") String creditProjectId,@RequestParam("cusNoFund") String cusNoFund,@RequestParam("appAmt") Double appAmt) throws Exception;
	
	/**
	 * 方法描述：根据客户号查询最新的授信合同
	 * @param cusNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mfCusCreditContract/getNewestCusCreditContrac")
	public MfCusCreditContract getNewestCusCreditContrac(@RequestParam("cusNo") String cusNo) throws Exception;
	/**
	 * 方法描述：授信 合同签约保存
	 * @param mfCusCreditContract
	 * @param wkfAppId
	 * @param commitType
	 * @throws Exception
	 *Map<String, Object>
	 * @author YuShuai
	 * @date 2018年6月27日 下午8:59:07
	 */
	@RequestMapping(value = "/mfCusCreditContract/submitContractSign")
	public Map<String, Object> submitContractSign(@RequestBody MfCusCreditContract mfCusCreditContract, @RequestParam("wkfAppId")  String wkfAppId, @RequestParam("commitType")  String commitType,@RequestParam("firstApprovalUser") String firstApprovalUser, @RequestParam("temporaryStorage") String temporaryStorage)throws Exception;
	/**
	 * 方法描述： 
	 * @param mfCusCreditContract
	 * @return
	 * @throws Exception
	 * MfCusCreditContract
	 * @author YuShuai
	 * @date 2018年7月11日 下午8:29:03
	 */
	@RequestMapping(value = "/mfCusCreditContract/getLstContractByCreditAppId")
	public MfCusCreditContract getLstContractByCreditAppId(@RequestBody MfCusCreditContract mfCusCreditContract)throws Exception;
	
	/**
	 * 根据客户号和授信模式查询客户是否授信
	 * 
	 * @return
	 * @throws Exception String
	 * @author 段泽宇
	 * @date 2018-7-16 下午22:31:16
	 */
	@RequestMapping(value = "/mfCusCreditContract/checkCreditByCusNo")
	public List<MfCusCreditContract> checkCreditByCusNo(@RequestBody MfCusCreditContract mfCusCreditContract)throws Exception;
	/**
	 * 方法描述： 根据客户号获取最新授信合同
	 * @param mfCusCreditContract
	 * @return
	 * @throws Exception
	 * MfCusCreditContract
	 * @author 仇招
	 * @date 2018年7月19日 下午10:48:12
	 */
	@RequestMapping(value = "/mfCusCreditContract/getByCusNo")
	public MfCusCreditContract getByCusNo(@RequestBody MfCusCreditContract mfCusCreditContract)throws Exception;

	@RequestMapping(value = "/mfCusCreditContract/getNewestCusCreditContracByCusNoAndCreditModel")
	public MfCusCreditContract getNewestCusCreditContracByCusNoAndCreditModel(@RequestBody MfCusCreditContract mfCusCreditContract)throws Exception;
	/**
	 * 方法描述：查询授信合同到期
	 * @param ipage
	 * @return Ipage
	 * @throws Exception
	 */
	@RequestMapping(value = "/mfCusCreditContract/getCreditExpireByPage")
	public Ipage getCreditExpireByPage(@RequestBody Ipage ipage) throws Exception;

    @RequestMapping(value = "/mfCusCreditContract/getCusAndProjectCreditContractListByCusNo")
    public List<MfCusCreditContract> getCusAndProjectCreditContractListByCusNo(@RequestBody MfCusCreditContract mfCusCreditContract) throws Exception;

	/**
	 * 方法描述：根据客户和授信流程查询有效合同
	 * @param ipage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mfCusCreditContract/getContractListByPage")
	public Ipage  getContractListByPage(@RequestBody Ipage ipage) throws Exception;

    @RequestMapping(value = "/mfCusCreditContract/insertInvalid")
    public Map<String,Object> insertInvalid(@RequestBody MfCusCreditContract mfCusCreditContract) throws Exception;
}
