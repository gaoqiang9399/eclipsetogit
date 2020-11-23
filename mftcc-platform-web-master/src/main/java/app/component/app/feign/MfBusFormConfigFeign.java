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

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.core.domain.screen.FormData;

import app.component.app.entity.MfBusFormConfig;
import app.component.calc.fee.entity.MfSysFeeItem;
import app.component.common.BizPubParm.WKF_NODE;



/**
 * 类名： 
 * 描述：
 * @author YuShuai
 * @date 2017-5-31 下午2:34:06
 * 
 *
 */
@FeignClient("mftcc-platform-factor")
public interface MfBusFormConfigFeign {

	/**
	 * 方法描述： 根据产品种类和阶段获取表单列表
	 * @param kindNo
	 * @param stage
	 * @return
	 * @throws Exception
	 * List<FormData>
	 * @author YuShuai
	 * @date 2017-6-5 下午4:59:47
	 */
	@RequestMapping(value = "/mfBusFormConfig/getInputBusCommonForm")
	public List<String> getInputBusCommonForm(@RequestBody String kindNo ,@RequestParam("formStage") String formStage,@RequestParam("cusType") String cusType) throws Exception;

	/**
	 * 方法描述： 插入公共的业务数据
	 * @param ajaxData
	 * @return
	 * @throws Exception
	 * String
	 * @author YuShuai
	 * @param mfBusSysFeeList 
	 * @date 2017-6-6 上午10:38:47
	 */
	@RequestMapping(value = "/mfBusFormConfig/insertInputCommonForm")
	public Map<String, String> insertInputCommonForm(@RequestBody String ajaxData,@RequestParam("kindNo") String kindNo ,@RequestParam("formStage") String formStage,@RequestParam("cusType") String cusType,@RequestParam("mfBusSysFeeList") List<MfSysFeeItem> mfBusSysFeeList)throws Exception;
	
	/**
	 * 方法描述： 获取客户的表单
	 * @param map
	 * @return
	 * @throws Exception
	 * String
	 * @author YuShuai
	 * @date 2017-6-6 下午9:41:51
	 */
	@RequestMapping(value = "/mfBusFormConfig/getInputCusEntryForm")
	public String getInputCusEntryForm(@RequestBody String kindNo,@RequestParam("formStage")  String formStage,@RequestParam("cusType")  String cusType) throws Exception;

	/**
	 * 方法描述： 初始化表单
	 * @param map  必须传的参数  kindNo: 产品种类  ,stage:阶段（mf_bus_form_config.form_stage）  ,
	 * @return
	 * @throws Exception
	 * FormData
	 * @author YuShuai
	 * @date 2017-6-7 下午4:47:32
	 *  当前获取表单功能不再使用，改为使用{@link PrdctInterface#getFormId}获取表单 wangchao 2017-07-17
	 */

	@RequestMapping(value = "/mfBusFormConfig/getInitForm")
	public FormData getInitForm(@RequestBody String kindNo,@RequestParam("formStage")  String formStage,@RequestParam("cusType")  String cusType)throws Exception;
	
	/**
	 * 方法描述： 获取要出的表单的列表
	 * @param kindNo
	 * @param formStage
	 * @param cusType
	 * @return
	 * @throws Exception
	 * List<MfBusFormConfig>
	 * @author YuShuai
	 * @date 2017-6-7 下午9:08:06
	 */
	@RequestMapping(value = "/mfBusFormConfig/getInputFormList")
	public List<MfBusFormConfig> getInputFormList(@RequestBody String kindNo ,@RequestParam("formStage") String formStage,@RequestParam("cusType") String cusType) throws Exception;

	/**
	 * 初始化审批动态表单
	 * @param appId 申请号
	 * @param fincId 借据号
	 * @param node 审批编号，{@link WKF_NODE#apply_approval}, {@link WKF_NODE#contract_approval}, {@link WKF_NODE#putout_approval}选其一
	 * @param defaultFormId 默认表单编号，用于功能初期无基础配置时防报错
	 * @return
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-6-16 下午8:51:30
	 */

	@RequestMapping(value = "/mfBusFormConfig/getAuditForm")
	public FormData getAuditForm(@RequestBody String appId,@RequestParam("fincId")  String fincId,@RequestParam("node")  WKF_NODE node,@RequestParam("defaultFormId")  String defaultFormId, @RequestParam("regNo") String regNo) throws Exception;
}
