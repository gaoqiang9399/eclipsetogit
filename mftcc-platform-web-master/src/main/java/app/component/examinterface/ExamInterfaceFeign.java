/**
 * Copyright (@RequestBody C) DXHM 版权所有
 * 文件名： ExamInterface.java
 * 包名： app.component.examinterface
 * 说明：
 * @author 沈浩兵
 * @date 2017-2-13 上午10:48:40
 * @version V1.0
 */
package app.component.examinterface;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.core.domain.screen.OptionsList;

import app.base.ServiceException;
import app.component.calc.core.entity.MfRepayPlan;
import app.component.examine.entity.MfExamRemindConfig;
import app.component.examine.entity.MfExamineHis;
import app.component.examine.entity.MfExamineTemplateConfig;
import app.component.pact.entity.MfBusFincApp;
import app.util.toolkit.Ipage;

/**
 * 类名： ExamInterface 描述：
 * 
 * @author 沈浩兵
 * @date 2017-2-13 上午10:48:40
 *
 *
 */
@FeignClient("mftcc-platform-factor")
public interface ExamInterfaceFeign {
	/**
	 * 
	 * 方法描述： 获得可用的检查提醒配置信息
	 * 
	 * @param mfExamRemindConfig
	 * @return
	 * @throws ServiceException
	 *             List<MfExamRemindConfig>
	 * @author 沈浩兵
	 * @date 2017-2-13 上午11:11:37
	 */
	@RequestMapping("/examInterface/getMfExamRemindConfigList")
	public List<MfExamRemindConfig> getMfExamRemindConfigList(@RequestBody MfExamRemindConfig mfExamRemindConfig)
			throws ServiceException;

	/**
	 * 
	 * 方法描述： 获得匹配到的检查模板配置信息
	 * 
	 * @param mfExamineTemplateConfig
	 * @return
	 * @throws Exception
	 *             List<MfExamineTemplateConfig>
	 * @author 沈浩兵
	 * @date 2017-2-13 下午2:39:44
	 */
	@RequestMapping("/examInterface/getConfigMatchedList")
	public List<MfExamineTemplateConfig> getConfigMatchedList(
			@RequestBody MfExamineTemplateConfig mfExamineTemplateConfig) throws Exception;

	/**
	 * 
	 * 方法描述： 获得所有可用检查模型
	 * 
	 * @param mfExamineTemplateConfig
	 * @return
	 * @throws Exception
	 *             List<MfExamineTemplateConfig>
	 * @author 呼艳明
	 * @date 2017-2-13 下午2:39:44
	 */
	@RequestMapping("/examInterface/getAllExamineTemplate")
	public List<MfExamineTemplateConfig> getAllExamineTemplate() throws Exception;

	/**
	 * 
	 * 方法描述： 获得匹配到的可用的检查模型配置信息
	 * 
	 * @param mfExamineTemplateConfig
	 * @return
	 * @throws Exception
	 *             List<MfExamineTemplateConfig>
	 * @author 沈浩兵
	 * @date 2017-7-12 上午11:34:36
	 */
	@RequestMapping("/examInterface/getUseableConfigMatchedList")
	public List<MfExamineTemplateConfig> getUseableConfigMatchedList(
			@RequestBody MfExamineTemplateConfig mfExamineTemplateConfig) throws Exception;

	/**
	 * 方法描述： 根据申请信息匹配贷后检查模型， 尽调报告、业务审批中选择贷后检查方案使用
	 * 
	 * @param relId
	 * @param entFlag
	 * @return
	 * @throws Exception
	 *             List<OptionsList>
	 * @author 沈浩兵
	 * @date 2017-8-1 下午5:57:05
	 */
	@RequestMapping("/examInterface/getConfigMatchedByBussList")
	public List<OptionsList> getConfigMatchedByBussList(@RequestBody String relId,@RequestParam("entFlag")  String entFlag) throws Exception;

	/**
	 * 
	 * 方法描述： 获得借据的检查历史
	 * 
	 * @param mfExamineHis
	 * @return
	 * @throws Exception
	 *             List<MfExamineHis>
	 * @author 沈浩兵
	 * @date 2017-8-2 下午6:05:58
	 */
	@RequestMapping("/examInterface/getMfExamineHisList")
	public List<MfExamineHis> getMfExamineHisList(@RequestBody MfExamineHis mfExamineHis) throws Exception;

	/**
	 * 
	 * 方法描述： 放款复核完成，生成当前借据下次检查日期插入贷后检查历史表
	 * 
	 * @param mfBusFincApp
	 * @param mfRepayPlan
	 * @throws Exception
	 *             void
	 * @author 沈浩兵
	 * @date 2017-8-20 上午9:49:45
	 */
	@RequestMapping("/examInterface/insertExaminInfoByBuss")
	public void insertExaminInfoByBuss(@RequestBody MfBusFincApp mfBusFincApp,
			@RequestParam("mfRepayPlan") MfRepayPlan mfRepayPlan) throws Exception;

	/**
	 * 
	 * 方法描述： 获得贷后检查分页列表
	 * 
	 * @param ipage
	 * @param mfExamineHis
	 * @return
	 * @throws ServiceException
	 *             Ipage
	 * @author 沈浩兵
	 * @date 2017-10-17 下午2:39:34
	 */
	@RequestMapping("/examInterface/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("mfExamineHis") MfExamineHis mfExamineHis)
			throws ServiceException;

	/**
	 * 
	 * 方法描述： 根据贷后检查编号获得检查详情
	 * 
	 * @param mfExamineHis
	 * @return
	 * @throws ServiceException
	 *             MfExamineHis
	 * @author 沈浩兵
	 * @date 2017-10-17 下午3:10:52
	 */
	@RequestMapping("/examInterface/getMfExamineHisById")
	public MfExamineHis getMfExamineHisById(@RequestBody String examHisId) throws ServiceException;
}
