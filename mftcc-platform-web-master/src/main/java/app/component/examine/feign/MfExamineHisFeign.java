package app.component.examine.feign;

import java.util.List;
import java.util.Map;

import app.component.examine.entity.MfExaminePact;
import com.core.domain.screen.FormData;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.core.domain.screen.OptionsList;

import app.base.ServiceException;
import app.component.calc.core.entity.MfRepayPlan;
import app.component.examine.entity.MfExamineHis;
import app.component.pact.entity.MfBusFincApp;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

/**
 * Title: MfExamineHisBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Feb 13 16:22:29 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface MfExamineHisFeign {
	@RequestMapping("/mfExamineHis/insert")
	public void insert(@RequestBody MfExamineHis mfExamineHis) throws ServiceException;
	@RequestMapping("/mfExamineHis/insertExaminePact")
	public void insertExaminePact(@RequestBody MfExamineHis mfExamineHis) throws ServiceException;

	@RequestMapping("/mfExamineHis/delete")
	public void delete(@RequestBody MfExamineHis mfExamineHis) throws ServiceException;

	@RequestMapping("/mfExamineHis/update")
	public void update(@RequestBody MfExamineHis mfExamineHis) throws ServiceException;
	@RequestMapping("/mfExamineHis/updateSavePdf")
	public void updateSavePdf(@RequestBody MfExamineHis mfExamineHis) throws ServiceException;

	/**
	 * 
	 * 方法描述： 提交贷后检查到审批
	 * 
	 * @param mfExamineHis
	 * @throws ServiceException
	 *             void
	 * @author 沈浩兵
	 * @date 2017-2-14 下午5:17:45
	 */
	@RequestMapping("/mfExamineHis/submitProcess")
	public MfExamineHis submitProcess(@RequestParam("examHisId") String examHisId,@RequestParam("entrance") String entrance,@RequestParam("regNo") String regNo, @RequestParam("regName") String regName, @RequestParam("orgNo") String orgNo,@RequestParam("nextUser") String nextUser) throws ServiceException;

	@RequestMapping("/mfExamineHis/doCommit")
	public Result doCommit(@RequestParam("taskId") String taskId, @RequestParam("examHisId") String examHisId, @RequestParam("opinionType") String opinionType,
			@RequestParam("approvalOpinion") String approvalOpinion, @RequestParam("transition") String transition, @RequestParam("opNo") String opNo,
			@RequestParam("nextUser") String nextUser,@RequestBody Map<String, Object> dataMap) throws ServiceException;

	@RequestMapping("/mfExamineHis/getById")
	public MfExamineHis getById(@RequestBody MfExamineHis mfExamineHis) throws ServiceException;

	@RequestMapping("/mfExamineHis/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfExamineHis") MfExamineHis mfExamineHis) throws ServiceException;

	@RequestMapping("/mfExamineHis/findByPageByApp")
	public Ipage findByPageByApp(@RequestBody Ipage ipage) throws ServiceException;


	/**
	 * 
	 * 方法描述： 获得检查历史
	 * 
	 * @param mfExamineHis
	 * @return
	 * @throws ServiceException
	 *             List<MfExamineHis>
	 * @author 沈浩兵
	 * @date 2017-2-14 下午2:36:31
	 */
	@RequestMapping("/mfExamineHis/getMfExamineHisList")
	public List<MfExamineHis> getMfExamineHisList(@RequestBody MfExamineHis mfExamineHis) throws ServiceException;

	/**
	 * 
	 * 方法描述： 获得检查卡信息
	 * 
	 * @param examHisId
	 * @param templateId
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-7-26 下午11:21:01
	 */
	@RequestMapping("/mfExamineHis/getListData")
	public Map<String, Object> getListData(@RequestParam("examHisId") String examHisId,@RequestParam("templateId") String templateId) throws Exception;

	/**
	 * 
	 * 方法描述： 保存检查卡
	 * 
	 * @param dataMap
	 * @throws ServiceException
	 *             void
	 * @author 沈浩兵
	 * @date 2017-7-27 上午10:36:39
	 */
	@RequestMapping("/mfExamineHis/examCardSave")
	public void examCardSave(@RequestBody Map<String, Object> dataMap) throws Exception;

	/**
	 * 
	 * 方法描述： 初始化打开贷后检查所需要数据
	 * 
	 * @param fincId
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-7-27 下午12:07:31
	 */
	@RequestMapping("/mfExamineHis/initInputData")
	public Map<String, Object> initInputData(@RequestParam("cusNo") String cusNo,@RequestParam("pactId") String pactId,@RequestParam("fincId") String fincId) throws Exception;

	@RequestMapping("/mfExamineHis/initInputData4Risk")
	public Map<String, Object> initInputData4Risk(@RequestParam("cusNo") String cusNo,@RequestParam("fincId") String fincId) throws Exception;
	/**
	 * 
	 * 方法描述： 获得是否存在检查
	 * 
	 * @param pactId
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-7-28 下午4:36:29
	 */
	@RequestMapping("/mfExamineHis/getExamineResultFlag")
	public Map<String, Object> getExamineResultFlag(@RequestParam("fincId") String fincId,@RequestParam("cusNo") String cusNo,@RequestParam("pactId") String pactId) throws Exception;

	/**
	 * 
	 * 方法描述： 检查历史超链获得检查详情
	 * 
	 * @param fincId
	 * @param examHisId
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-7-29 下午7:49:18
	 */
	@RequestMapping("/mfExamineHis/getExamineDetailByList")
	public Map<String, Object> getExamineDetailByList(@RequestParam("fincId") String fincId,@RequestParam("examHisId") String examHisId) throws Exception;

	/**
	 * 
	 * 方法描述：根据申请信息匹配贷后检查模型， 尽调报告、业务审批中选择贷后检查方案使用
	 * 
	 * @param pactId
	 * @param entFlag
	 * @return
	 * @throws Exception
	 *             List<OptionsList>
	 * @author 沈浩兵
	 * @date 2017-8-1 下午7:41:40
	 */
	@RequestMapping("/mfExamineHis/getConfigMatchedByBussList")
	public List<OptionsList> getConfigMatchedByBussList(@RequestParam("pactId") String pactId,@RequestParam("entFlag") String entFlag) throws Exception;

	@RequestMapping("/mfExamineHis/getConfigMatchedByCusList")
	public List<OptionsList> getConfigMatchedByCusList(@RequestParam("cusNo")  String cusNo) throws Exception;
	/**
	 * 
	 * 方法描述： 手动指派贷后检查任务
	 * 
	 * @param assignType
	 *            指派类型 single单个指派 batch批量指派
	 * @param fincIdStr
	 *            指派的借据号，一个或多个
	 * @param executorsStr
	 *            检查任务指派给的执行人
	 * @throws Exception
	 *             void
	 * @author 沈浩兵
	 * @date 2017-8-2 下午3:33:14
	 */
	@RequestMapping("/mfExamineHis/submitAssignExamineTask")
	public Map<String, Object> submitAssignExamineTask(@RequestParam("assignType") String assignType,@RequestParam("fincIdStr")  String fincIdStr,@RequestParam("executorsStr") String executorsStr,@RequestParam("cusNoStr") String cusNoStr,@RequestParam("orgNo") String orgNo,@RequestParam("regName") String regName)
			throws Exception;

	/**
	 * 
	 * 方法描述： 放款复核完成，生成当前借据下次检查日期插入贷后检查历史表
	 * 
	 * @param mfBusFincApp
	 * @param mfRepayPlan
	 * @throws Exception
	 *             void
	 * @author 沈浩兵
	 * @date 2017-8-20 上午9:52:57
	 */
	@RequestMapping("/mfExamineHis/insertExaminInfoByBuss")
	public void insertExaminInfoByBuss(@RequestBody MfBusFincApp mfBusFincApp,@RequestParam("mfRepayPlan") MfRepayPlan mfRepayPlan) throws Exception;

	/**
	 * 
	 * 方法描述： 获得检查模板保存情况
	 * 
	 * @param examHisId
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-8-28 上午11:57:52
	 */
	@RequestMapping("/mfExamineHis/getSaveTemplateInfo")
	public Map<String, Object> getSaveTemplateInfo(@RequestParam("examHisId") String examHisId,@RequestParam("templateId") String templateId) throws Exception;

	/**
	 * 
	 * 方法描述： 检查是否存在正在进行的贷后检查
	 * 
	 * @param fincId
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-9-4 下午5:14:21
	 */
	@RequestMapping("/mfExamineHis/getCheckExistExaminingFlag")
	public String getCheckExistExaminingFlag(@RequestParam("fincId") String fincId,@RequestParam("cusNo") String cusNo,@RequestParam("entrance") String entrance,@RequestParam("pactId")String pactId) throws Exception;

	/**
	 * 
	 * 方法描述： 获得已提交审批的贷后检查历史
	 * 
	 * @param mfExamineHis
	 * @return
	 * @throws ServiceException
	 *             List<MfExamineHis>
	 * @author 沈浩兵
	 * @date 2017-10-9 下午2:46:18
	 */
	@RequestMapping("/mfExamineHis/getMfExamineHisSubmitList")
	public List<MfExamineHis> getMfExamineHisSubmitList(@RequestBody MfExamineHis mfExamineHis) throws Exception;

	/**
	 * @Description 新贷后检查任务指派（包含单笔和批量）
	 * @Author zhaomingguang
	 * @DateTime 2019/9/23 9:13
	 * @Param [paraMap]
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	@RequestMapping("/mfExamineHis/submitAssignExamineTaskNew")
    Map<String,Object> submitAssignExamineTaskNew(Map<String, Object> paraMap)throws Exception;

	/**
	 * @Description 获取贷后检查登记操作时页面表单数据内容
	 * @Author zhaomingguang
	 * @DateTime 2019/9/23 19:54
	 * @Param 
	 * @return 
	 */
	@RequestMapping("/mfExamineHis/getExamHisFormData")
	Map<String, Object> getExamHisFormData(Map<String,String> paraMap)throws Exception;

	/**
	 * @Description 获取新贷后检查结论表单的数据内容
	 * @Author zhaomingguang
	 * @DateTime 2019/9/24 16:40
	 * @Param 
	 * @return 
	 */
	@RequestMapping("/mfExamineHis/getBaseExaminfoNewData")
    Map<String,Object> getBaseExaminfoNewData(MfExamineHis mfExamineHis)throws Exception;

	/**
	 * @Description 获取贷后检查任务页面的显示数据
	 * @Author zhaomingguang
	 * @DateTime 2019/9/26 11:07
	 * @Param 
	 * @return 
	 */
	@RequestMapping("/mfExamineHis/getExamineHisDataByPageAjax")
    Ipage getExamineHisDataByPageAjax(Ipage ipage)throws Exception;

	/**
	 * @Description 判断该贷后检查任务是否已经被操作
	 * @Author zhaomingguang
	 * @DateTime 2019/9/27 16:32
	 * @Param 
	 * @return 
	 */
	@RequestMapping("/mfExamineHis/getCheckExistExaminingFlagNew")
	String getCheckExistExaminingFlagNew(MfExamineHis mfExamineHis)throws Exception;

	/**
	 * 保后跟踪时系统自动调用三方接口，若有新增工商处罚、法律诉讼（个人及公司）、同盾保后分值超过20分，则内部分级自动分级为预警
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/mfExamineHis/checkCallThreeChange")
	Boolean checkCallThreeChange(@RequestBody Map<String, String> dataMap)throws Exception;
	/**
	 * 保后跟踪时系统自动调用三方接口，若有新增工商处罚、法律诉讼（个人及公司）、同盾保后分值超过20分，则内部分级自动分级为预警
	 * @param dataMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/mfExamineHis/autoLevel")
	Map<String,Object> autoLevel(@RequestBody Map<String, String> dataMap)throws Exception;
	/**
	 * 保后跟踪时获取在保的情况
	 * @param mfExaminePact
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/mfExamineHis/getExaminePactList")
	List<MfExaminePact> getExaminePactList(@RequestBody MfExaminePact mfExaminePact)throws Exception;

}
