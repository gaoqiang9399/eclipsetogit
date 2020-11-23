package app.component.oa.opencount.controller;

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
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;

import app.base.User;
import app.component.common.EntityUtil;
import app.component.oa.opencount.entity.MfOaOpening;
import app.component.oa.opencount.feign.MfOaOpeningFeign;
import app.component.wkf.AppConstant;
import app.component.wkf.entity.Result;
import app.component.wkf.feign.TaskFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;

/**
 * Title: MfOaOpeningAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Sat Jun 17 15:31:41 CST 2017 ounttransForm" theme="simple" name="operform"
 *      action="MfOaCounttransActionAjax_updateAjaxByOne.action">
 *      <dhcc:bootstarpTag property="formopencount0003" mode="query"/>
 **/
@Controller
@RequestMapping(value = "/mfOaOpening")
public class MfOaOpeningController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfOaOpeningFeign mfOaOpeningFeign;
	@Autowired
	private TaskFeign taskFeign;

	/**
	 * 确认开户处理
	 * 
	 */
	/**
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/updateAjaxByOne")
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formopencount0005 = new FormService().getFormData("opencount0005");
		getFormValue(formopencount0005, getMapByJson(ajaxData));
		MfOaOpening mfOaOpeningJsp = new MfOaOpening();
		setObjValue(formopencount0005, mfOaOpeningJsp);
		MfOaOpening mfOaOpening = mfOaOpeningFeign.getById(mfOaOpeningJsp);
		if (mfOaOpening != null) {
			try {
				mfOaOpening = (MfOaOpening) EntityUtil.reflectionSetVal(mfOaOpening, mfOaOpeningJsp,
						getMapByJson(ajaxData));
				mfOaOpeningFeign.updateOpen(mfOaOpening);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw e;
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", "编号不存在,保存失败");
		}
		return dataMap;
	}

	/**
	 * 确认开户
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/confirmOpen")
	public String confirmOpen(String badgeNo, Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formopencount0005 = new FormService().getFormData("opencount0005");
		getFormValue(formopencount0005);
		MfOaOpening mfOaOpening = new MfOaOpening();
		mfOaOpening.setBadgeNo(badgeNo);
		mfOaOpening = mfOaOpeningFeign.getById(mfOaOpening);
		// 设置申请时间显示方式
		mfOaOpening.setApplyTime(DateUtil.getShowDateTime(mfOaOpening.getApplyTime()));
		getObjValue(formopencount0005, mfOaOpening);
		model.addAttribute("formopencount0005", formopencount0005);
		model.addAttribute("mfOaOpening", mfOaOpening);
		model.addAttribute("badgeNo", badgeNo);
		model.addAttribute("query", "");
		return "/component/oa/opencount/MfOaOpening_Confirm";
	}

	// 处理董事长审批
	@ResponseBody
	@RequestMapping("/submitForUpdateByChairman")
	public Map<String, Object> submitForUpdateByChairman(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formopencount0004 = new FormService().getFormData("opencount0004");
			getFormValue(formopencount0004, getMapByJson(ajaxData));
			MfOaOpening mfOaOpening = new MfOaOpening();
			setObjValue(formopencount0004, mfOaOpening);
			String opinionType = mfOaOpening.getOpinionType();
			String appNo = mfOaOpening.getAppNo();
			mfOaOpening.setBadgeNo(appNo);
			String taskId = mfOaOpening.getTaskId();
			Map<String, Object> thisMap = new HashMap<String, Object>();
			thisMap.put("isChairman", mfOaOpening.getIsChairman());
			taskFeign.setVariables(taskId, thisMap);
			String transition = null;
			if (AppConstant.OPINION_TYPE_ARREE.equals(opinionType)) {// 同意
				transition = taskFeign.getTransitionsStr(taskId);
				/* appAuth.setAppSts(BizPubParm.APP_STS_PASS); */
			} else if (AppConstant.OPINION_TYPE_REFUSE.equals(opinionType)) {// 不同意（否决
				transition = taskFeign.getTransitionsStr(taskId);
			}else {
			}
			Result res = mfOaOpeningFeign.updateForSubmit(taskId, appNo, mfOaOpening.getOpinionType(),
					mfOaOpening.getApprovalOpinion(), transition, User.getRegNo(this.getHttpRequest()), "", mfOaOpening,
					mfOaOpening.getBadgeNodeType());
			dataMap.put("flag", "success");
			dataMap.put("msg", res.getMsg());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}

	// 处理没有选择董事长的审批提交操作
	// 处理下滑式的处理
	@ResponseBody
	@RequestMapping("/submitForUpdate")
	public Map<String, Object> submitForUpdate(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData badge0003 = new FormService().getFormData("badge0003");
			getFormValue(badge0003, getMapByJson(ajaxData));
			MfOaOpening mfOaOpening = new MfOaOpening();
			setObjValue(badge0003, mfOaOpening);
			String opinionType = mfOaOpening.getOpinionType();
			String appNo = mfOaOpening.getAppNo();
			mfOaOpening.setBadgeNo(appNo);
			String taskId = mfOaOpening.getTaskId();
			String transition = null;
			// mfOaOpening = mfOaOpeningFeign.getById(mfOaOpening);
			if (AppConstant.OPINION_TYPE_ARREE.equals(opinionType)) {// 同意
				transition = taskFeign.getTransitionsStr(taskId);
				/* appAuth.setAppSts(BizPubParm.APP_STS_PASS); */
			} else if (AppConstant.OPINION_TYPE_REFUSE.equals(opinionType)) {// 不同意（否决
				transition = taskFeign.getTransitionsStr(taskId);
			}else {
			}
			Result res = mfOaOpeningFeign.updateForSubmit(taskId, appNo, mfOaOpening.getOpinionType(),
					mfOaOpening.getApprovalOpinion(), transition, User.getRegNo(this.getHttpRequest()), "", mfOaOpening,
					mfOaOpening.getBadgeNodeType());

			dataMap.put("flag", "success");
			dataMap.put("msg", res.getMsg());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 列表打开页面请求
	 * 
	 * @param model
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "/component/oa/opencount/MfOaOpening_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/findByPageAjax")
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfOaOpening mfOaOpening = new MfOaOpening();
		try {
			mfOaOpening.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfOaOpening.setCriteriaList(mfOaOpening, ajaxData);// 我的筛选
			// mfOaOpening.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfOaOpening,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfOaOpening", mfOaOpening));
			ipage = mfOaOpeningFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/insertAjax")
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formopencount0002 = new FormService().getFormData("opencount0002");
			getFormValue(formopencount0002, getMapByJson(ajaxData));
			if (this.validateFormData(formopencount0002)) {
				MfOaOpening mfOaOpening = new MfOaOpening();
				setObjValue(formopencount0002, mfOaOpening);
				mfOaOpening = mfOaOpeningFeign.insert(mfOaOpening);
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("userRole", mfOaOpening.getApprovalNodeName());
				paramMap.put("opNo", mfOaOpening.getApprovePartName());
				dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
				dataMap.put("flag", "success");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX更新保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/updateAjax")
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfOaOpening mfOaOpening = new MfOaOpening();
			FormData formopencount0002 = new FormService().getFormData("opencount0002");
			getFormValue(formopencount0002, getMapByJson(ajaxData));
			if (this.validateFormData(formopencount0002)) {
				mfOaOpening = new MfOaOpening();
				setObjValue(formopencount0002, mfOaOpening);
				mfOaOpeningFeign.update(mfOaOpening);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX获取查看
	 * 
	 * @param badgeNo
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getByIdAjax")
	public Map<String, Object> getByIdAjax(String badgeNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formopencount0002 = new FormService().getFormData("opencount0002");
		MfOaOpening mfOaOpening = new MfOaOpening();
		mfOaOpening.setBadgeNo(badgeNo);
		mfOaOpening = mfOaOpeningFeign.getById(mfOaOpening);
		// 对申请时间进行格式化
		mfOaOpening.setApplyTime(DateUtil.getShowDateTime(mfOaOpening.getApplyTime()));
		getObjValue(formopencount0002, mfOaOpening, formData);
		if (mfOaOpening != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}

	/**
	 * Ajax异步删除
	 * 
	 * @param badgeNo
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/deleteAjax")
	public Map<String, Object> deleteAjax(String badgeNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfOaOpening mfOaOpening = new MfOaOpening();
		mfOaOpening.setBadgeNo(badgeNo);
		try {
			mfOaOpeningFeign.delete(mfOaOpening);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * 新增页面
	 * 
	 * @param model
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/input")
	public String input(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formopencount0002 = new FormService().getFormData("opencount0002");
		MfOaOpening mfOaOpening = new MfOaOpening();
		// 初始化数据
		mfOaOpening.setApplyTime(DateUtil.getShowDateTime(DateUtil.getDateTime()));
		mfOaOpening.setCust((User.getRegName(ActionContext.getActionContext().getRequest())));
		getObjValue(formopencount0002, mfOaOpening);
		model.addAttribute("formopencount0002", formopencount0002);
		model.addAttribute("mfOaOpening", mfOaOpening);
		model.addAttribute("query", "");
		return "/component/oa/opencount/MfOaOpening_Insert";
	}

	/**
	 * 查询
	 * 
	 * @param badgeNo
	 * @param model
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(String badgeNo, Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formopencount0003 = new FormService().getFormData("opencount0003");
		getFormValue(formopencount0003);
		MfOaOpening mfOaOpening = new MfOaOpening();
		mfOaOpening.setBadgeNo(badgeNo);
		mfOaOpening = mfOaOpeningFeign.getById(mfOaOpening);
		mfOaOpening.setApplyTime(DateUtil.getShowDateTime(mfOaOpening.getApplyTime()));
		getObjValue(formopencount0003, mfOaOpening);
		model.addAttribute("formopencount0003", formopencount0003);
		model.addAttribute("mfOaOpening", mfOaOpening);
		model.addAttribute("query", "");
		return "/component/oa/opencount/MfOaOpening_Detail";
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/validateInsert")
	public void validateInsert() throws Exception {
		ActionContext.initialize(request, response);
		FormData formopencount0002 = new FormService().getFormData("opencount0002");
		getFormValue(formopencount0002);
		boolean validateFlag = this.validateFormData(formopencount0002);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/validateUpdate")
	public void validateUpdate() throws Exception {
		ActionContext.initialize(request, response);
		FormData formopencount0002 = new FormService().getFormData("opencount0002");
		getFormValue(formopencount0002);
		boolean validateFlag = this.validateFormData(formopencount0002);
	}

}