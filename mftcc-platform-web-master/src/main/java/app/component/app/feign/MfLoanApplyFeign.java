/**
 * Copyright (C)  版权所有
 * 文件名： MfLoanApplyBo.java
 * 包名： app.component.app.bo
 * 说明：中汇-北京-个贷业务逻辑接口
 * @author YuShuai
 * @date 2017-5-31 下午8:29:00
 * @version V1.0
 */ 
package app.component.app.feign;
import java.util.List;
import java.util.Map;

import app.component.cus.entity.MfCusPersBaseInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.app.entity.MfBusApply;
import app.component.calc.fee.entity.MfSysFeeItem;
import app.component.cus.entity.MfCusCorpBaseInfo;
import app.component.cus.entity.MfCusCustomer;
import app.component.doc.entity.DocBizManage;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.entity.MfBusPact;
import app.component.prdct.entity.MfKindForm;
import app.component.wkf.entity.Result;


/**
 * 类名： MfLoanApplyBo
 * 描述：个贷业务业务逻辑层
 * @author YuShuai
 * @date 2017-5-31 下午2:34:06
 * 
 *
 */
@FeignClient("mftcc-platform-factor")
public interface MfLoanApplyFeign {

	/**
	 * 方法描述： 进件插入客户和业务信息
	 * @param mfCusCustomer
	 * @param mfBusApply
	 * @return
	 * String
	 * @author YuShuai
	 * @param mfCusPersBaseInfo 
	 * @date 2017-5-31 下午3:06:33
	 */
	@RequestMapping(value = "/mfLoanApply/insertApplyForm")
	public String insertApplyForm(@RequestBody MfCusCustomer mfCusCustomer,@RequestParam("mfCusPersBaseInfo")  MfCusPersBaseInfo mfCusPersBaseInfo,@RequestParam("mfBusApply")  MfBusApply mfBusApply) throws Exception;

	

	/**
	 * 方法描述： 更新申请信息并提交流程
	 * @param mfBusApply
	 * @param temporaryStorage 1暂存, 0保存并提交
	 * @return
	 * @throws Exception
	 * Result
	 * @author YuShuai
	 * @date 2017-5-31 下午3:52:58
	 */
	@RequestMapping(value = "/mfLoanApply/updateAndCommit")
	public Result updateAndCommit(@RequestBody MfBusApply mfBusApply, @RequestParam("temporaryStorage") String temporaryStorage) throws Exception;

	/**
	 * 方法描述： 更新分单信息并且提交流程
	 * @param mfBusApply
	 * @return
	 * @throws Exception
	 * Result
	 * @author YuShuai
	 * @date 2017-5-31 下午4:11:12
	 */
	@RequestMapping(value = "/mfLoanApply/updatePolicyAndCommit")
	public Result updatePolicyAndCommit(@RequestBody MfBusApply mfBusApply)throws Exception;

	/**
	 * 方法描述： 尽调报告的校验
	 * @param mfBusApply
	 * @return
	 * @throws Exception
	 * String
	 * @author YuShuai
	 * @param nextUser 下一岗位人员
	 * @date 2017-5-31 下午4:32:23
	 */
	@RequestMapping(value = "/mfLoanApply/doConfirmTuningReport")
	public Map<String, Object> doConfirmTuningReport(@RequestBody Map<String, Object> map)throws Exception;

	/**
	 * 方法描述：移动端调用资方
	 * @param mfBusApply
	 * @param map
	 * @param channelType
	 * @return
	 * @throws Exception
	 * String
	 * @author zhang_dlei
	 * @date 2017-5-31 下午4:32:23
	 */
	@RequestMapping(value = "/mfLoanApply/doConfirmTuningReportForApp")
	public Map<String, Object> doConfirmTuningReportForApp(@RequestBody MfBusApply mfBusApply,@RequestParam("map")  Map<String, Object> map,@RequestParam("channelType") String channelType)throws Exception;

	/**
	 * 方法描述： 校验是否上传了要件并返回结果
	 * @param docBizManage
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author YuShuai
	 * @param cusNo 
	 * @date 2017-5-31 下午4:49:28
	 */
	@RequestMapping(value = "/mfLoanApply/checkIfUploadFile")
	public Map<String, Object> checkIfUploadFile(@RequestBody DocBizManage docBizManage,@RequestParam("cusNo")  String cusNo)throws Exception;
	
	/**
	 * 方法描述： 通过appId或者wkfId获取
	 * @param mfBusApply
	 * @return
	 * @throws Exception
	 * MfBusApply
	 * @author YuShuai
	 * @date 2017-5-17 下午8:33:21
	 */
	@RequestMapping(value = "/mfLoanApply/getByIdOrAppId")
	public MfBusApply getByIdOrAppId(@RequestBody MfBusApply mfBusApply) throws Exception;
	/**
	 * 方法描述： 提交流程
	 * @param mfBusApply
	 * @return
	 * @throws Exception
	 * Result
	 * @author YuShuai
	 * @date 2017-5-31 下午5:03:50
	 */
	@RequestMapping(value = "/mfLoanApply/doCommit")
	public Result doCommit(@RequestBody MfBusApply mfBusApply)throws Exception;

	/**
	 * 方法描述： 业务提交审批流程
	 * @param appId
	 * @return
	 * @throws Exception
	 * MfBusApply
	 * @author YuShuai
	 * @date 2017-5-31 下午5:13:33
	 */
	@RequestMapping(value = "/mfLoanApply/submitProcess")
	public MfBusApply submitProcess(@RequestBody String appId,@RequestParam("regNo") String regNo, @RequestParam("regName") String regName, @RequestParam("orgNo") String orgNo)throws Exception;

	/**
	 * 方法描述：校验是否录入了评估信息和面签 
	 * @param mfBusPact
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author YuShuai
	 * @date 2017-5-31 下午5:21:43
	 */
	@RequestMapping(value = "/mfLoanApply/doFundConfirm")
	public Map<String, Object> doFundConfirm(@RequestBody MfBusPact mfBusPact)throws Exception;

	/**
	 * 方法描述： 放款处理合同数据
	 * @param mfBusPact
	 * @return
	 * @throws Exception
	 * MfBusPact
	 * @author YuShuai
	 * @date 2017-5-31 下午5:29:52
	 */
	@RequestMapping(value = "/mfLoanApply/processDataForPact")
	public MfBusPact processDataForPact(@RequestBody MfBusPact mfBusPact)throws Exception;


	/**
	 * 方法描述： 插入放款数据并提交
	 * @param mfBusFincApp
	 * @return
	 * @throws Exception
	 * Result
	 * @author YuShuai
	 * @param isNewBank 
	 * @date 2017-5-31 下午6:04:05
	 */
	@RequestMapping(value = "/mfLoanApply/insertFincAndCommit")
	public Result insertFincAndCommit(@RequestBody MfBusFincApp mfBusFincApp,@RequestParam("isNewBank")  String isNewBank)throws Exception;

	/**
	 * 方法描述： 合同面签更新及流程的提交
	 * @param mfBusPact
	 * @return
	 * @throws Exception
	 * Result
	 * @author YuShuai
	 * @date 2017-5-31 下午7:01:24
	 */
	@RequestMapping(value = "/mfLoanApply/updatePactAndCommit")
	public Result updatePactAndCommit(@RequestBody MfBusPact mfBusPact)throws Exception;

	/**
	 * 方法描述： 放款审批开始流程
	 * @param appId
	 * @param mfBusFincApp
	 * @return
	 * @throws Exception
	 * MfBusFincApp
	 * @author YuShuai
	 * @date 2017-5-31 下午7:18:30
	 */
	@RequestMapping(value = "/mfLoanApply/doInprocess")
	public MfBusFincApp doInprocess(@RequestBody String appId,@RequestParam("mfBusFincApp")  MfBusFincApp mfBusFincApp)throws Exception;

	/**
	 * 方法描述： 放款确认并生成还款计划
	 * @param mfBusFincApp
	 * @return
	 * @throws Exception
	 * Result
	 * @author YuShuai
	 * @date 2017-5-31 下午7:36:03
	 */
	@RequestMapping(value = "/mfLoanApply/updateProcessAndPlan")
	public Result updateProcessAndPlan(@RequestBody MfBusFincApp mfBusFincApp)throws Exception;

	/**
	 * 方法描述： 通过放款编号或者流程编号获取放款信息
	 * @param mfBusFincApp
	 * @return
	 * @throws ServiceException
	 * MfBusFincApp
	 * @author YuShuai
	 * @date 2017-5-19 上午11:29:33
	 */
	@RequestMapping(value = "/mfLoanApply/getByFincIdOrwkfId")
	public MfBusFincApp getByFincIdOrwkfId(@RequestBody MfBusFincApp mfBusFincApp)throws ServiceException;

	
	/**
	 * 方法描述： 保存机构确认信息并提交下一步
	 * @param mfBusApply
	 * @return
	 * @throws Exception
	 * Result
	 * @author 谢静霞
	 * @date 2017-6-2 下午2:35:00
	 */
	@RequestMapping(value = "/mfLoanApply/updateRegConfirmAnddoCommit")
	public Result updateRegConfirmAnddoCommit(@RequestBody MfBusApply mfBusApply) throws Exception;

	/**
	 * 方法描述：提交下一流程并指派下一节点的操作员 
	 * @param mfBusApply
	 * @param opNo
	 * @return
	 * @throws Exception
	 * Result
	 * @author 谢静霞
	 * @date 2017-6-3 下午3:53:42
	 */
	@RequestMapping(value = "/mfLoanApply/doCommitAndUpdateOpNO")
	public Result doCommitAndUpdateOpNO(@RequestBody MfBusApply mfBusApply,@RequestParam("opNo")  String opNo) throws Exception;

	/**
	 * 方法描述： 插入业务方法
	 * @param mfBusApply
	 * @throws Exception
	 * void
	 * @author YuShuai
	 * @param mfBusAppFeeList 
	 * @date 2017-6-6 上午11:24:57
	 */
	@RequestMapping(value = "/mfLoanApply/insertCommonApp")
	public MfBusApply insertCommonApp(@RequestBody MfBusApply mfBusApply, @RequestParam("mfBusSysFeeList")List<MfSysFeeItem> mfBusSysFeeList)throws Exception;

	/**
	 * 方法描述： 查询客户的资料是否都完整
	 * @param mfBusApply
	 * @return
	 * @throws Exception
	 * List<Map<String,String>>
	 * @author YuShuai
	 * @date 2017-6-13 下午3:37:57
	 */
	@RequestMapping(value = "/mfLoanApply/getDataComplete")
	public List<Map<String, String>> getDataComplete(@RequestBody MfBusApply mfBusApply)throws Exception;

	/**
	 * 方法描述： 校验是否录入了全部的信息
	 * @param mfBusApply
	 * @return
	 * @throws Exception
	 * String
	 * @author YuShuai
	 * @date 2017-6-15 上午11:45:07
	 */
	@RequestMapping(value = "/mfLoanApply/checkIfComplete")
	public String checkIfComplete(@RequestBody MfBusApply mfBusApply)throws Exception;

	/**
	 * 方法描述： 处理插入时候的数据
	 * @param mfBusApply
	 * @return
	 * @throws Exception
	 * MfBusApply
	 * @author YuShuai
	 * @date 2017-6-19 下午4:43:00
	 */
	@RequestMapping(value = "/mfLoanApply/disProcessDataForApply")
	public MfBusApply disProcessDataForApply(@RequestBody MfBusApply mfBusApply)throws Exception;
	
	/**
	 * 方法描述： 处理申请的数据
	 * @param mfBusApply
	 * @return
	 * @throws Exception
	 * MfBusApply
	 * @author YuShuai
	 * @date 2017-5-31 下午3:43:29
	 */
	@RequestMapping(value = "/mfLoanApply/processDataForApply")
	public MfBusApply processDataForApply(@RequestBody MfBusApply mfBusApply) throws Exception;



	/**
	 * 方法描述： 根据配置的表单封装相应的数据
	 * @param mfKindForm
	 * @return
	 * @throws Exception
	 * Object
	 * @author YuShuai
	 * @date 2017-7-1 下午4:43:21
	 */
	@RequestMapping(value = "/mfLoanApply/getInitFormObj")
	public Object getInitFormObj(@RequestBody MfKindForm mfKindForm,@RequestParam("kindNo") String kindNo)throws Exception;


	/**
	 * 方法描述： 插入多表数据
	 * @param mfCusCustomer
	 * @param mfCusPersBaseInfo
	 * @param mfCusCorpBaseInfo
	 * @param mfBusApply
	 * @return
	 * Map<String,String>
	 * @author YuShuai
	 * @date 2017-7-2 上午11:33:03
	 */
	@RequestMapping(value = "/mfLoanApply/insertMultipleForms")
	public Map<String, String> insertMultipleForms(MfCusCustomer mfCusCustomer,
@RequestParam("mfCusPersBaseInfo") 			MfCusPersBaseInfo mfCusPersBaseInfo,
@RequestParam("mfCusCorpBaseInfo") 			MfCusCorpBaseInfo mfCusCorpBaseInfo,@RequestParam("mfBusApply")  MfBusApply mfBusApply)throws Exception;



	/**
	 * 方法描述： 返回风险拦截项的结果
	 * @param appId
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author YuShuai
	 * @date 2017-10-18 下午3:27:11
	 */
	@RequestMapping(value = "/mfLoanApply/getRiskItemResultAjax")
	public Map<String, Object> getRiskItemResultAjax(@RequestBody String appId,@RequestParam("regNo") String regNo)throws Exception;

	@RequestMapping(value = "/mfLoanApply/getAuthority")
    String getAuthority(@RequestBody MfBusApply mfBusApply)throws Exception;
}
