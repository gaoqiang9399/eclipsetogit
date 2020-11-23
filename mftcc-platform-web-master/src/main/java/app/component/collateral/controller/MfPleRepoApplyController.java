package app.component.collateral.controller;

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
import app.component.collateral.entity.MfPleRepoApply;
import app.component.collateral.entity.MfPleRepoApproveHis;
import app.component.collateral.feign.MfPleRepoApplyFeign;
import app.component.collateral.feign.MfPleRepoApproveHisFeign;
import app.component.collateral.feign.MfReceivablesPledgeInfoFeign;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.common.ViewUtil;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.WaterIdUtil;

/**
 * Title: MfPleRepoApplyController.java Description:应收账款回购申请视图控制
 * 
 * @author:LJW
 * @Fri May 05 09:49:33 CST 2017
 **/
@Controller
@RequestMapping("/mfPleRepoApply")
public class MfPleRepoApplyController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfPleRepoApplyFeign mfPleRepoApplyFeign;
	@Autowired
	private MfPleRepoApproveHisFeign mfPleRepoApproveHisFeign; // 审批业务控制
	@Autowired
	private MfReceivablesPledgeInfoFeign mfReceivablesPledgeInfoFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage(Model model, String busPleId) throws Exception {
		ActionContext.initialize(request, response);
		MfPleRepoApply mfPleRepoApply = new MfPleRepoApply();
		mfPleRepoApply.setBusPleId(busPleId);
		List<MfPleRepoApply> mfPleRepoApplyList = mfPleRepoApplyFeign.getPleRepoList(mfPleRepoApply);
		if (mfPleRepoApplyList.size() > 0) {
			mfPleRepoApply = mfPleRepoApplyList.get(0);
		}
		model.addAttribute("mfPleRepoApplyList", mfPleRepoApplyList);
		model.addAttribute("mfPleRepoApply", mfPleRepoApply);
		model.addAttribute("query", "");
		return "/component/collateral/MfPleRepoApply_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/findByPageAjax")
	@ResponseBody public Map<String, Object> findByPageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
			String tableType, String busPleId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfPleRepoApply mfPleRepoApply = new MfPleRepoApply();
		try {
			mfPleRepoApply.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfPleRepoApply.setCriteriaList(mfPleRepoApply, ajaxData);// 我的筛选
			mfPleRepoApply.setBusPleId(busPleId);
			// mfPleRepoApply.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfPleRepoApply,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage.setPageSize(pageSize);// 异步传页面翻页参数
			// 自定义查询Feign方法
			ipage = mfPleRepoApplyFeign.findByPage(ipage, mfPleRepoApply);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 打开审批历史页面
	 * 
	 * @author LJW date 2017-5-5
	 */
	@RequestMapping("/creditApprovalHis")
	public String creditApprovalHis() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/collateral/repo_ApprovalHis";
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertAjax")
	@ResponseBody public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formrepoapply0002 = formService.getFormData("repoapply0002");
			getFormValue(formrepoapply0002, getMapByJson(ajaxData));
			if (this.validateFormData(formrepoapply0002)) {
				MfPleRepoApply mfPleRepoApply = new MfPleRepoApply();
				setObjValue(formrepoapply0002, mfPleRepoApply);
				mfPleRepoApply = mfPleRepoApplyFeign.insert(mfPleRepoApply);
				dataMap.put("flag", "success");
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("userRole", mfPleRepoApply.getApproveNodeName());
				paramMap.put("opNo", mfPleRepoApply.getApprovePartName());
				dataMap.put("msg", MessageEnum.SUCCEED_APPROVAL.getMessage(paramMap));
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_PROCESS_COMMIT.getMessage());
		}
		return dataMap;
	}

	/**
	 * 跳转到审批页面
	 * 
	 * @author LJW date 2017-5-5
	 */
	@RequestMapping("/repoApprove")
	public String repoApprove(Model model,String repoAppId,String taskId, String hideOpinionType,String activityType) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfPleRepoApply mfPleRepoApply = new MfPleRepoApply();
		try {
			mfPleRepoApply.setRepoAppId(repoAppId);
			mfPleRepoApply = mfPleRepoApplyFeign.getById(mfPleRepoApply);
			FormData formplerepoapprove0001 = formService.getFormData("plerepoapprove0001");
			getFormValue(formplerepoapprove0001);

			// 向应收账款审批实体中注入值 先删除，再插入
			MfPleRepoApproveHis mfPleRepoApproveHis = new MfPleRepoApproveHis();
			mfPleRepoApproveHis.setRepoAppId(repoAppId);
			mfPleRepoApproveHisFeign.delete(mfPleRepoApproveHis);
			mfPleRepoApproveHis = getMfPleRepoAppHisData(mfPleRepoApply);
			getObjValue(formplerepoapprove0001, mfPleRepoApply);
			getObjValue(formplerepoapprove0001, mfPleRepoApproveHis);

			// 处理审批意见类型
			TaskImpl taskAppro = wkfInterfaceFeign.getTask(repoAppId, taskId);// 当前审批节点task
			String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号

			Map<String, String> opinionTypeMap = new HashMap<String, String>();
			opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
			opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
			opinionTypeMap.put("nodeNo", nodeNo);// 当前节点编号
			List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,taskAppro.getCouldRollback(), opinionTypeMap);
			this.changeFormProperty(formplerepoapprove0001, "opinionType", "optionArray", opinionTypeList);


			model.addAttribute("formplerepoapprove0001", formplerepoapprove0001);
			model.addAttribute("receId", mfPleRepoApply.getBusPleId());

		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("query", "");
		return "/component/collateral/MfPleRepoApproveHis_Detail";
	}

	/**
	 * 提交审批
	 * 
	 * @author LJW date 2017-5-5
	 */
	@RequestMapping("/submitApproveAjax")
	@ResponseBody public Map<String, Object> submitApproveAjax(String ajaxData,String taskId,String id,String nextUser,String transition) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		try {
			// 更新审批信息
			MfPleRepoApproveHis mfPleRepoApproveHis = new MfPleRepoApproveHis();
			FormData formplerepoapprove0001 = formService.getFormData("plerepoapprove0001");
			Map<String, Object> jsonDatas = getMapByJson(ajaxData);
			getFormValue(formplerepoapprove0001, jsonDatas);
			setObjValue(formplerepoapprove0001, mfPleRepoApproveHis);
			dataMap = getMapByJson(ajaxData);
			String opinionType = String.valueOf(dataMap.get("opinionType"));
			String approvalOpinion = String.valueOf(dataMap.get("approvalOpinion"));
			mfPleRepoApproveHis.setCurrentSessionOrgNo(User.getOrgNo(request));
			dataMap.put("mfPleRepoApproveHis",mfPleRepoApproveHis);
			Result res = mfPleRepoApplyFeign.doCommitApprove(taskId, id, opinionType, approvalOpinion, transition,
					User.getRegNo(request), nextUser, dataMap);
			if (res.isSuccess()) {
				dataMap.put("flag", "success");
				if (res.isEndSts()) {
					dataMap.put("msg", res.getMsg());
				} else {
					dataMap.put("msg", res.getMsg());
				}
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.FAILED_SUBMIT.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAjaxByOne")
	@ResponseBody public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formrepoapply0002 = formService.getFormData("repoapply0002");
		getFormValue(formrepoapply0002, getMapByJson(ajaxData));
		MfPleRepoApply mfPleRepoApplyJsp = new MfPleRepoApply();
		setObjValue(formrepoapply0002, mfPleRepoApplyJsp);
		MfPleRepoApply mfPleRepoApply = mfPleRepoApplyFeign.getById(mfPleRepoApplyJsp);
		if (mfPleRepoApply != null) {
			try {
				mfPleRepoApply = (MfPleRepoApply) EntityUtil.reflectionSetVal(mfPleRepoApply, mfPleRepoApplyJsp,
						getMapByJson(ajaxData));
				mfPleRepoApplyFeign.update(mfPleRepoApply);
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
	 * AJAX更新保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAjax")
	@ResponseBody public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formrepoaffirm0002 = formService.getFormData("repoaffirm0002");
			getFormValue(formrepoaffirm0002, getMapByJson(ajaxData));
			if (this.validateFormData(formrepoaffirm0002)) {
				MfPleRepoApply mfPleRepoApply = new MfPleRepoApply();
				setObjValue(formrepoaffirm0002, mfPleRepoApply);
				mfPleRepoApplyFeign.updateRepoAffirm(mfPleRepoApply);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_REPO_AFFIRM.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述： 反转让确认保存
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-5-23 上午11:05:45
	 */
	@RequestMapping("/updateRepoAffirmAjax")
	@ResponseBody public Map<String, Object> updateRepoAffirmAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formrepoaffirm0002 = formService.getFormData("repoaffirm0002");
			getFormValue(formrepoaffirm0002, getMapByJson(ajaxData));
			if (this.validateFormData(formrepoaffirm0002)) {
				MfPleRepoApply mfPleRepoApply = new MfPleRepoApply();
				setObjValue(formrepoaffirm0002, mfPleRepoApply);
				mfPleRepoApplyFeign.updateRepoAffirm(mfPleRepoApply);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX获取查看
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getByIdAjax")
	@ResponseBody public Map<String, Object> getByIdAjax(String repoAppId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formrepoapply0002 = formService.getFormData("repoapply0002");
		MfPleRepoApply mfPleRepoApply = new MfPleRepoApply();
		mfPleRepoApply.setRepoAppId(repoAppId);
		mfPleRepoApply = mfPleRepoApplyFeign.getById(mfPleRepoApply);
		getObjValue(formrepoapply0002, mfPleRepoApply, formData);
		if (mfPleRepoApply != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 *
	 * 方法描述 根据账款的id获取账款反转让信息
	 * @param [receId]
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 * @author zhs
	 * @date 2018/9/5 18:10
	 */
	@RequestMapping("/getByReceIdAjax")
	@ResponseBody public Map<String, Object> getByReceIdAjax(String receId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfPleRepoApply mfPleRepoApply = new MfPleRepoApply();
		mfPleRepoApply.setBusPleId(receId);
		mfPleRepoApply = mfPleRepoApplyFeign.getById(mfPleRepoApply);
		if (mfPleRepoApply != null) {
			dataMap.put("repoAppId",mfPleRepoApply.getRepoAppId());
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		return dataMap;
	}

	/**
	 * Ajax异步删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData,String repoAppId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfPleRepoApply mfPleRepoApply = new MfPleRepoApply();
		mfPleRepoApply.setRepoAppId(repoAppId);
		try {
			mfPleRepoApplyFeign.delete(mfPleRepoApply);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw e;
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
	public String input(Model model,String receId,String appId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		MfPleRepoApply mfPleRepoApply = new MfPleRepoApply();
		FormData formrepoapply0002 = formService.getFormData("repoapply0002");
		mfPleRepoApply = mfPleRepoApplyFeign.getMfPleRepoApplyInit(appId,receId);
		mfPleRepoApply.setBusPleId(receId);
		getObjValue(formrepoapply0002, mfPleRepoApply);
		model.addAttribute("query", "");
		model.addAttribute("formrepoapply0002", formrepoapply0002);
		return "/component/collateral/MfPleRepoApply_Insert";
	}

	/**
	 * 
	 * 方法描述： 反转让确认
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author 沈浩兵
	 * @date 2017-5-23 上午9:47:53
	 */
	@RequestMapping("/inputAffirm")
	public String inputAffirm(Model model,String receId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		MfPleRepoApply mfPleRepoApply = new MfPleRepoApply();
		FormData formrepoaffirm0002 = formService.getFormData("repoaffirm0002");
		mfPleRepoApply.setBusPleId(receId);
		mfPleRepoApply.setAppSts("2");
		mfPleRepoApply = mfPleRepoApplyFeign.getById(mfPleRepoApply);
		getObjValue(formrepoaffirm0002, mfPleRepoApply);
		model.addAttribute("formrepoaffirm0002", formrepoaffirm0002);
		model.addAttribute("query", "");
		return "/component/collateral/MfReceRepoAffirm_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/insert")
	public String insert(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formrepoapply0002 = formService.getFormData("repoapply0002");
		getFormValue(formrepoapply0002);
		MfPleRepoApply mfPleRepoApply = new MfPleRepoApply();
		setObjValue(formrepoapply0002, mfPleRepoApply);
		mfPleRepoApplyFeign.insert(mfPleRepoApply);
		getObjValue(formrepoapply0002, mfPleRepoApply);
		this.addActionMessage(model, "保存成功");
		List<MfPleRepoApply> mfPleRepoApplyList = (List<MfPleRepoApply>) mfPleRepoApplyFeign
				.findByPage(this.getIpage(), mfPleRepoApply).getResult();
		model.addAttribute("mfPleRepoApplyList", mfPleRepoApplyList);
		model.addAttribute("formrepoapply0002", formrepoapply0002);
		model.addAttribute("query", "");
		return "/component/collateral/MfPleRepoApply_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model,String busPleId) throws Exception {
		ActionContext.initialize(request, response);FormService formService = new FormService();
		FormData  formrepoaffirm0001 = formService.getFormData("repoaffirm0001");
		getFormValue(formrepoaffirm0001);
		MfPleRepoApply mfPleRepoApply = new MfPleRepoApply();
		mfPleRepoApply.setBusPleId(busPleId);
		mfPleRepoApply = mfPleRepoApplyFeign.getById(mfPleRepoApply);
		getObjValue(formrepoaffirm0001, mfPleRepoApply);
		model.addAttribute("formrepoaffirm0001", formrepoaffirm0001);
		model.addAttribute("query", "");
		return "/component/collateral/MfPleRepoApply_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(Model model,String repoAppId,String busPleId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		MfPleRepoApply mfPleRepoApply = new MfPleRepoApply();
		mfPleRepoApply.setRepoAppId(repoAppId);
		mfPleRepoApplyFeign.delete(mfPleRepoApply);
		return getListPage(model, busPleId);
	}

	private MfPleRepoApproveHis getMfPleRepoAppHisData(MfPleRepoApply mfPleRepoApply) throws Exception {
		MfPleRepoApproveHis mfPleRepoApproveHis = new MfPleRepoApproveHis();
		mfPleRepoApproveHis.setId(WaterIdUtil.getWaterId());
		mfPleRepoApproveHis.setRepoAppId(mfPleRepoApply.getRepoAppId());
		mfPleRepoApproveHis.setApplyDate(mfPleRepoApply.getApplyDate());
		mfPleRepoApproveHis.setTransferReason(mfPleRepoApply.getTransferReason());
		mfPleRepoApproveHis.setFincPrepayBal(mfPleRepoApply.getFincPrepayBal());
		mfPleRepoApproveHis.setAccruedInterest(mfPleRepoApply.getAccruedInterest());
		mfPleRepoApproveHis.setApproveResult("1");
		mfPleRepoApproveHisFeign.insert(mfPleRepoApproveHis);
		return mfPleRepoApproveHis;
	}

}
