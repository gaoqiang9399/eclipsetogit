package app.component.oa.fulltopart.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;

import app.base.User;
import app.component.common.BizPubParm;
import app.component.oa.feign.MfOaFormConfigFeign;
import app.component.oa.fulltopart.entity.MfOaFullToPart;
import app.component.oa.fulltopart.entity.MfOaFullToPartHis;
import app.component.oa.fulltopart.feign.MfOaFullToPartFeign;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.tech.upload.FeignSpringFormEncoder;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.WaterIdUtil;
import net.sf.json.JSONArray;

@Controller
@RequestMapping("/mfOaFullToPart")
public class MfOaFullToPartController extends BaseFormBean {
	@Autowired
	private MfOaFullToPartFeign mfOaFullToPartFeign;
	@Autowired
	private MfOaFormConfigFeign mfOaFormConfigFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage() throws Exception {
		return "/component/oa/fulltopart/MfOaFullToPart_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
			String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfOaFullToPart mfOaFullToPart = new MfOaFullToPart();
		try {
			mfOaFullToPart.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfOaFullToPart.setCriteriaList(mfOaFullToPart, ajaxData);// 我的筛选
			mfOaFullToPart.setCustomSorts(ajaxData);// 自定义排序
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);// 异步传页面翻页尺寸
			ipage.setParams(this.setIpageParams("mfOaFullToPart", mfOaFullToPart));
			ipage = mfOaFullToPartFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formfulltopart0002 = formService
					.getFormData(mfOaFormConfigFeign.getFormByAction("MfOaFullToPartAction", BizPubParm.SHOW_TYPE1));
			getFormValue(formfulltopart0002, getMapByJson(ajaxData));
			if (this.validateFormData(formfulltopart0002)) {
				MfOaFullToPart mfOaFullToPart = new MfOaFullToPart();
				setObjValue(formfulltopart0002, mfOaFullToPart);
				mfOaFullToPart.setFullToPartId(WaterIdUtil.getWaterId());
				mfOaFullToPartFeign.insert(mfOaFullToPart);
				mfOaFullToPart = mfOaFullToPartFeign.submitProcess(mfOaFullToPart);
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("userRole", mfOaFullToPart.getApproveNodeName());
				paramMap.put("opNo", mfOaFullToPart.getApprovePartName());
				dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
				dataMap.put("flag", "success");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 打开全职转兼职申请审批页面
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 仇招
	 * @date 2017-12-12下午20:09:27
	 */
	@RequestMapping("/getViewPoint")
	public String getViewPoint(Model model, String fullToPartId, String hideOpinionType, String taskId,
			String activityType) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		request.setAttribute("ifBizManger", "3");
		MfOaFullToPart mfOaFullToPart = new MfOaFullToPart();
		mfOaFullToPart.setFullToPartId(fullToPartId);
		mfOaFullToPart = mfOaFullToPartFeign.getById(mfOaFullToPart);
		mfOaFullToPart.setApprovePartNo(null);
		mfOaFullToPart.setApprovePartName(null);
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(fullToPartId, null);// 当前审批节点task
		// 初始化基本信息表单、工作经历表单
		FormData formFullToPartApprove = formService.getFormData("fulltopart0003");
		// 实体对象放到表单对象中
		getObjValue(formFullToPartApprove, mfOaFullToPart);
		// 处理审批意见类型
		Map<String, String> opinionTypeMap = new HashMap<String, String>();
		opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
		String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号
		opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
		opinionTypeMap.put("nodeNo", nodeNo);// 当前节点编号
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), opinionTypeMap);
		this.changeFormProperty(formFullToPartApprove, "opinionType", "optionArray", opinionTypeList);
		// 获得当前审批岗位前面审批过得岗位信息
		JSONArray befNodesjsonArray = new JSONArray();
		befNodesjsonArray = wkfInterfaceFeign.getBefNodes1(taskId,User.getRegNo(request));
		request.setAttribute("befNodesjsonArray", befNodesjsonArray);
		model.addAttribute("processId", BizPubParm.FULL_TO_PART_NO);
		model.addAttribute("formFullToPartApprove", formFullToPartApprove);
		model.addAttribute("mfOaFullToPart", mfOaFullToPart);
		model.addAttribute("fullToPartId", fullToPartId);
		model.addAttribute("query", "");
		return "/component/oa/fulltopart/WkfFullToPartViewPoint";
	}

	/**
	 * 
	 * 方法描述： 审批意见保存提交
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 仇招
	 * @date 2017-12-13 上午10:09:47
	 */
	@RequestMapping("/submitUpdateAjax")
	@ResponseBody
	public Map<String, Object> submitUpdateAjax(String ajaxData, String appNo, String taskId, String transition,
			String nextUser) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> formDataMap = getMapByJson(ajaxData);
		// 初始化基本信息表单、工作经历表单
		FormData formFullToPartApprove = formService.getFormData("fulltopart0003");
		getFormValue(formFullToPartApprove, formDataMap);
		MfOaFullToPart mfOaFullToPart = new MfOaFullToPart();
		MfOaFullToPartHis mfOaFullToPartHis = new MfOaFullToPartHis();
		setObjValue(formFullToPartApprove, mfOaFullToPart);
		setObjValue(formFullToPartApprove, mfOaFullToPartHis);
		Result res;
		try {
			new FeignSpringFormEncoder().addParamsToBaseDomain(mfOaFullToPart);
			new FeignSpringFormEncoder().addParamsToBaseDomain(mfOaFullToPartHis);
			formDataMap.put("mfOaFullToPart", mfOaFullToPart);
			formDataMap.put("mfOaFullToPartHis", mfOaFullToPartHis);
			res = mfOaFullToPartFeign.doCommit(taskId, transition, nextUser,
					formDataMap);
			 dataMap = new HashMap<String, Object>();
			if (res.isSuccess()) {
				dataMap.put("flag", "success");
				if (res.isEndSts()) {
					dataMap.put("msg", res.getMsg());
				} else {
					dataMap.put("msg", res.getMsg());
				}
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SUBMIT.getMessage());
		}
		return dataMap;
	}

	/**
	 * 新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/input")
	public String input(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formfulltopart0002 = formService
				.getFormData(mfOaFormConfigFeign.getFormByAction("MfOaFullToPartAction", BizPubParm.SHOW_TYPE1));
		MfOaFullToPart mfOaFullToPart = new MfOaFullToPart();
		mfOaFullToPart.setOpNo(User.getRegNo(request));
		mfOaFullToPart.setOpName(User.getRegName(request));
		mfOaFullToPart.setRegTime(DateUtil.getShowDateTime(DateUtil.getDateTime()));
		getObjValue(formfulltopart0002, mfOaFullToPart);
		model.addAttribute("processId", BizPubParm.FULL_TO_PART_NO);
		model.addAttribute("formfulltopart0002", formfulltopart0002);
		model.addAttribute("query", "");
		return "/component/oa/fulltopart/MfOaFullToPart_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model,String fullToPartId,String entryFlag) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		MfOaFullToPart mfOaFullToPart = new MfOaFullToPart();
		mfOaFullToPart.setFullToPartId(fullToPartId);
		mfOaFullToPart = mfOaFullToPartFeign.getById(mfOaFullToPart);
		mfOaFullToPart.setRegTime(DateUtil.getShowDateTime(mfOaFullToPart.getRegTime()));
		FormData formfulltopart0001 = formService
				.getFormData(mfOaFormConfigFeign.getFormByAction("MfOaFullToPartAction", BizPubParm.SHOW_TYPE2));
		getObjValue(formfulltopart0001, mfOaFullToPart);
		model.addAttribute("formfulltopart0001", formfulltopart0001);
		model.addAttribute("fullToPartId", fullToPartId);
		model.addAttribute("mfOaFullToPart", mfOaFullToPart);
		model.addAttribute("entryFlag", entryFlag);
		model.addAttribute("query", "");
		return "/component/oa/fulltopart/MfOaFullToPart_Detail";
	}

	/**
	 * 
	 * 方法描述： 提交审批流程
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 仇招
	 * @date 2017-12-13 上午10:09:47
	 */
	@RequestMapping("/submitProcessAjax")
	@ResponseBody
	public Map<String, Object> submitProcessAjax(String fullToPartId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfOaFullToPart mfOaFullToPart = new MfOaFullToPart();
		mfOaFullToPart.setFullToPartId(fullToPartId);
		mfOaFullToPart = mfOaFullToPartFeign.getById(mfOaFullToPart);
		try {
			mfOaFullToPart = mfOaFullToPartFeign.submitProcess(mfOaFullToPart);
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("userRole", mfOaFullToPart.getApproveNodeName());
			paramMap.put("opNo", mfOaFullToPart.getApprovePartName());
			dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
			dataMap.put("flag", "success");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SUBMIT.getMessage());
		}
		return dataMap;
	}
}
