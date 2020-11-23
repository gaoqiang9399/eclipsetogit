package app.component.assetsmanage.controller;

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
import app.component.assetsmanage.entity.MfAssetsDisposal;
import app.component.assetsmanage.entity.MfAssetsManage;
import app.component.assetsmanage.feign.MfAssetsDisposalFeign;
import app.component.assetsmanage.feign.MfAssetsManageFeign;
import app.component.collateral.entity.PledgeBaseInfo;
import app.component.collateral.feign.PledgeBaseInfoFeign;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.tech.upload.FeignSpringFormEncoder;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.WaterIdUtil;
import net.sf.json.JSONArray;

/**
 * Title: MfAssetsDisposalController.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Sep 27 20:09:45 CST 2018
 **/
@Controller
@RequestMapping(value = "/mfAssetsDisposal")
public class MfAssetsDisposalController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfAssetsDisposalFeign mfAssetsDisposalFeign;
	@Autowired
	private MfAssetsManageFeign mfAssetsManageFeign;
	@Autowired
	private PledgeBaseInfoFeign pledgeBaseInfoFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/assetsmanage/MfAssetsDisposal_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/findByPageAjax")
	public Map<String, Object> findByPageAjax(String ajaxData, String tableId, String tableType, Integer pageNo,
			Integer pageSize) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfAssetsDisposal mfAssetsDisposal = new MfAssetsDisposal();
		try {
			mfAssetsDisposal.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfAssetsDisposal.setCriteriaList(mfAssetsDisposal, ajaxData);// 我的筛选
			mfAssetsDisposal.setCustomSorts(ajaxData);// 自定义排序
			// this.getRoleConditions(mfAssetsDisposal,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setParams(this.setIpageParams("mfAssetsDisposal", mfAssetsDisposal));
			// 自定义查询Feign方法
			ipage = mfAssetsDisposalFeign.findByPage(ipage);
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
	@RequestMapping("/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData, String handleType) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String formId = "AssetsDisposalBase";
			if (handleType != null) {
				switch (handleType) {
				case BizPubParm.ASSET_HANDLE_TYPE_AUCTION:
					formId = "AssetsDisposalBase_Auction";
					break;
				case BizPubParm.ASSET_HANDLE_TYPE_LEASE:
					formId = "AssetsDisposalBase_Lease";
					break;
					case BizPubParm.ASSET_HANDLE_TYPE_NEGOTIATE:
						formId = "AssetsDisposalBase_Auction";
						break;
				default:
					break;
				}

			}
			FormData formAssetsDisposalBase = formService.getFormData(formId);
			getFormValue(formAssetsDisposalBase, getMapByJson(ajaxData));
			if (this.validateFormData(formAssetsDisposalBase)) {
				MfAssetsDisposal mfAssetsDisposal = new MfAssetsDisposal();
				setObjValue(formAssetsDisposalBase, mfAssetsDisposal);
				mfAssetsDisposalFeign.insert(mfAssetsDisposal);
				mfAssetsDisposal = mfAssetsDisposalFeign.submitProcess(mfAssetsDisposal);
                mfAssetsDisposal.setHandleType(BizPubParm.ASSET_HANDLE_TYPE_AUCTION);//显示拍卖
                List<MfAssetsDisposal> mfAssetsDisposalList = mfAssetsDisposalFeign.getByAssetsManageId(mfAssetsDisposal);

                mfAssetsDisposal.setHandleType(BizPubParm.ASSET_HANDLE_TYPE_NEGOTIATE);//显示协议处置
                List<MfAssetsDisposal> mfAssetNegotiateList = mfAssetsDisposalFeign.getByAssetsManageId(mfAssetsDisposal);
                mfAssetsDisposalList.addAll(mfAssetNegotiateList);
                JsonTableUtil jtu = new JsonTableUtil();
                String auctionHtml = jtu.getJsonStr("tableAssetsDisposalList", "tableTag",
                        mfAssetsDisposalList, null, true);

                mfAssetsDisposal.setHandleType(BizPubParm.ASSET_HANDLE_TYPE_LEASE);//显示租赁
                List<MfAssetsDisposal> mfAssetLeaseList = mfAssetsDisposalFeign.getByAssetsManageId(mfAssetsDisposal);
                String leaseHtml = jtu.getJsonStr("tableAssetsDisposalList", "tableTag",
                        mfAssetLeaseList, null, true);
                mfAssetsDisposal.setHandleType(null);//显示全部
                List<MfAssetsDisposal> allList = mfAssetsDisposalFeign.getByAssetsManageId(mfAssetsDisposal);
                String allHtml = jtu.getJsonStr("tableAssetsDisposalList", "tableTag",
                        allList, null, true);

                dataMap.put("allHtml", allHtml);
                dataMap.put("auctionHtml", auctionHtml);
                dataMap.put("leaseHtml", leaseHtml);
                CodeUtils cu = new CodeUtils();
                Map<String,String> appStsMap = cu.getMapByKeyName("APP_STS");
                if(mfAssetsDisposalList.size()>0){

                    dataMap.put("mfAssetsDisposal", mfAssetsDisposalList.get(0));
                    dataMap.put("appSts", appStsMap.get(mfAssetsDisposalList.get(0).getApplySts()));
                }else {
                    dataMap.put("mfAssetsDisposal", mfAssetLeaseList.get(0));
                    dataMap.put("appSts", appStsMap.get(mfAssetLeaseList.get(0).getApplySts()));
                }
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("userRole", mfAssetsDisposal.getApproveNodeName());
				paramMap.put("opNo", mfAssetsDisposal.getApprovePartName());
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
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formAssetsDisposalBase = formService.getFormData("AssetsDisposalBase");
		getFormValue(formAssetsDisposalBase, getMapByJson(ajaxData));
		MfAssetsDisposal mfAssetsDisposalJsp = new MfAssetsDisposal();
		setObjValue(formAssetsDisposalBase, mfAssetsDisposalJsp);
		MfAssetsDisposal mfAssetsDisposal = mfAssetsDisposalFeign.getById(mfAssetsDisposalJsp);
		if (mfAssetsDisposal != null) {
			try {
				mfAssetsDisposal = (MfAssetsDisposal) EntityUtil.reflectionSetVal(mfAssetsDisposal, mfAssetsDisposalJsp,
						getMapByJson(ajaxData));
				mfAssetsDisposalFeign.update(mfAssetsDisposal);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} catch (Exception e) {
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
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfAssetsDisposal mfAssetsDisposal = new MfAssetsDisposal();
		try {
			FormData formAssetsDisposalBase = formService.getFormData("AssetsDisposalBase");
			getFormValue(formAssetsDisposalBase, getMapByJson(ajaxData));
			if (this.validateFormData(formAssetsDisposalBase)) {
				mfAssetsDisposal = new MfAssetsDisposal();
				setObjValue(formAssetsDisposalBase, mfAssetsDisposal);
				mfAssetsDisposalFeign.update(mfAssetsDisposal);
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
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String disposalId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formAssetsDisposalBase = formService.getFormData("AssetsDisposalBase");
		MfAssetsDisposal mfAssetsDisposal = new MfAssetsDisposal();
		mfAssetsDisposal.setDisposalId(disposalId);
		mfAssetsDisposal = mfAssetsDisposalFeign.getById(mfAssetsDisposal);
		getObjValue(formAssetsDisposalBase, mfAssetsDisposal, formData);
		if (mfAssetsDisposal != null) {
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
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String disposalId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfAssetsDisposal mfAssetsDisposal = new MfAssetsDisposal();
		mfAssetsDisposal.setDisposalId(disposalId);
		try {
			mfAssetsDisposalFeign.delete(mfAssetsDisposal);
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
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/input")
	public String input(Model model, String handleType, String assetsManageId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		CodeUtils cu = new CodeUtils();
		Map<String, String> handleTypeMap = cu.getMapByKeyName("HANDLE_TYPE");
		String formId = "AssetsDisposalBase";
		String showName = "处置";
		if (handleType != null) {
			showName = handleTypeMap.get(handleType);
			switch (handleType) {
			case BizPubParm.ASSET_HANDLE_TYPE_AUCTION:
				formId = "AssetsDisposalBase_Auction";
				break;
			case BizPubParm.ASSET_HANDLE_TYPE_LEASE:
				formId = "AssetsDisposalBase_Lease";
				break;
			default:
				break;
			}

		}
		FormData formAssetsDisposalBase = formService.getFormData(formId);
		MfAssetsDisposal mfAssetsDisposal = new MfAssetsDisposal();
		mfAssetsDisposal.setAssetsManageId(assetsManageId);
		mfAssetsDisposal.setHandleType(handleType);
		String disposalId = WaterIdUtil.getWaterId();
		mfAssetsDisposal.setDisposalId(disposalId);
		MfAssetsManage mfAssetsManage = new MfAssetsManage();
		mfAssetsManage.setAssetsManageId(assetsManageId);
		mfAssetsManage = mfAssetsManageFeign.getById(mfAssetsManage);
		if (mfAssetsManage != null) {
			mfAssetsDisposal.setAssessAmt(mfAssetsManage.getAssessAmt());
			mfAssetsDisposal.setDebtAmt(mfAssetsManage.getDebtAmt());
			mfAssetsDisposal.setDebtBal(mfAssetsManage.getDebtBal());
			mfAssetsDisposal.setCusNo(mfAssetsManage.getCusNo());
			mfAssetsDisposal.setCusName(mfAssetsManage.getCusName());
			mfAssetsDisposal.setAssetNo(mfAssetsManage.getPledgeNo());
			mfAssetsDisposal.setAssetName(mfAssetsManage.getPledgeName());
			PledgeBaseInfo pledgeBaseInfo = new PledgeBaseInfo();
			pledgeBaseInfo.setPledgeNo(mfAssetsManage.getPledgeNo());
			pledgeBaseInfo = pledgeBaseInfoFeign.getById(pledgeBaseInfo);
			if (pledgeBaseInfo != null) {
				mfAssetsDisposal.setAssetType(pledgeBaseInfo.getClassSecondName());

			}
		}
		getObjValue(formAssetsDisposalBase, mfAssetsDisposal);
		model.addAttribute("formAssetsDisposalBase", formAssetsDisposalBase);
		model.addAttribute("disposalId", disposalId);
		model.addAttribute("handleType", handleType);
		model.addAttribute("showName", showName);
		model.addAttribute("assetsManageId", assetsManageId);
		model.addAttribute("query", "");
		return "/component/assetsmanage/MfAssetsDisposal_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model, String disposalId,String handleType,String entryFlag) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		CodeUtils cu = new CodeUtils();
		Map<String, String> handleTypeMap = cu.getMapByKeyName("HANDLE_TYPE");
		String formId = "AssetsDisposalDetail";
		String showName = "处置";
		if (handleType != null) {
			showName = handleTypeMap.get(handleType);
			switch (handleType) {
			case BizPubParm.ASSET_HANDLE_TYPE_AUCTION:
				formId = "AssetsDisposalDetail_Auction";
				break;
			case BizPubParm.ASSET_HANDLE_TYPE_LEASE:
				formId = "AssetsDisposalDetail_Lease";
				break;
				case BizPubParm.ASSET_HANDLE_TYPE_NEGOTIATE:
					formId = "AssetsDisposalDetail_Auction";
					break;
			default:
				break;
			}

		}
		FormData formAssetsDisposalDetail = formService.getFormData(formId);
		getFormValue(formAssetsDisposalDetail);
		MfAssetsDisposal mfAssetsDisposal = new MfAssetsDisposal();
		mfAssetsDisposal.setDisposalId(disposalId);
		mfAssetsDisposal = mfAssetsDisposalFeign.getById(mfAssetsDisposal);
		getObjValue(formAssetsDisposalDetail, mfAssetsDisposal);
		model.addAttribute("formAssetsDisposalDetail", formAssetsDisposalDetail);
		model.addAttribute("mfAssetsDisposal", mfAssetsDisposal);
		model.addAttribute("showName", showName);
		model.addAttribute("handleType", handleType);
		model.addAttribute("assetsManageId", mfAssetsDisposal.getAssetsManageId());
		model.addAttribute("disposalId", disposalId);
		model.addAttribute("entryFlag", entryFlag);
		model.addAttribute("query", "");
		return "/component/assetsmanage/MfAssetsDisposal_Detail";
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void validateInsert() throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formAssetsDisposalBase = formService.getFormData("AssetsDisposalBase");
		getFormValue(formAssetsDisposalBase);
		boolean validateFlag = this.validateFormData(formAssetsDisposalBase);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void validateUpdate() throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formAssetsDisposalBase = formService.getFormData("AssetsDisposalBase");
		getFormValue(formAssetsDisposalBase);
		boolean validateFlag = this.validateFormData(formAssetsDisposalBase);
	}

	/**
	 * @方法描述： 审批页面
	 * 
	 * @param model
	 * @param disposalId
	 * @param hideOpinionType
	 * @param taskId
	 * @param activityType
	 * @return
	 * @throws Exception
	 *             String
	 * @author 仇招
	 * @date 2018年9月28日 上午10:57:40
	 */
	@RequestMapping("/getViewPoint")
	public String getViewPoint(Model model, String disposalId, String hideOpinionType, String taskId,
			String activityType) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		MfAssetsDisposal mfAssetsDisposal = new MfAssetsDisposal();
		mfAssetsDisposal.setDisposalId(disposalId);
		mfAssetsDisposal = mfAssetsDisposalFeign.getById(mfAssetsDisposal);
		String handleType = mfAssetsDisposal.getHandleType();
		CodeUtils cu = new CodeUtils();
		Map<String, String> handleTypeMap = cu.getMapByKeyName("HANDLE_TYPE");
		String formId = "AssetsDisposalApprove";
		String showName = "处置";
		if (handleType != null) {
			showName = handleTypeMap.get(handleType);
			switch (handleType) {
			case BizPubParm.ASSET_HANDLE_TYPE_AUCTION:
				formId = "AssetsDisposalApprove_Auction";
				break;
			case BizPubParm.ASSET_HANDLE_TYPE_LEASE:
				formId = "AssetsDisposalApprove_Lease";
				break;
				case BizPubParm.ASSET_HANDLE_TYPE_NEGOTIATE:
					formId = "AssetsDisposalApprove_Auction";
					break;
			default:
				break;
			}

		}
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(disposalId, null);// 当前审批节点task
		// 初始化基本信息表单、工作经历表单
		FormData formAssetsDisposalApprove = formService.getFormData(formId);
		// 实体对象放到表单对象中
		getObjValue(formAssetsDisposalApprove, mfAssetsDisposal);
		// 处理审批意见类型
		Map<String, String> opinionTypeMap = new HashMap<String, String>();
		opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
		String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号
		opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
		opinionTypeMap.put("nodeNo", nodeNo);// 当前节点编号
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), opinionTypeMap);
		this.changeFormProperty(formAssetsDisposalApprove, "opinionType", "optionArray", opinionTypeList);
		// 获得当前审批岗位前面审批过得岗位信息
		JSONArray befNodesjsonArray = new JSONArray();
		befNodesjsonArray = wkfInterfaceFeign.getBefNodes1(taskId, User.getRegNo(request));
		request.setAttribute("befNodesjsonArray", befNodesjsonArray);
		model.addAttribute("disposalId", disposalId);
		model.addAttribute("mfAssetsDisposal", mfAssetsDisposal);
		model.addAttribute("formAssetsDisposalApprove", formAssetsDisposalApprove);
		model.addAttribute("taskId", taskId);
		model.addAttribute("handleType", handleType);
		model.addAttribute("activityType", activityType);
		model.addAttribute("showName", showName);
		model.addAttribute("query", "");
		return "/component/assetsmanage/WkfAssetsDisposalViewPoint";
	}

	/**
	 * @方法描述： 审批提交
	 * 
	 * @param ajaxData
	 * @param appNo
	 * @param taskId
	 * @param transition
	 * @param nextUser
	 * @param handleType
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @author 仇招
	 * @date 2018年9月28日 上午10:58:14
	 */
	@RequestMapping("/submitUpdateAjax")
	@ResponseBody
	public Map<String, Object> submitUpdateAjax(String ajaxData, String appNo, String taskId, String transition,
			String nextUser, String handleType) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> formDataMap = getMapByJson(ajaxData);
		// 初始化基本信息表单、工作经历表单
		String formId = "AssetsDisposalApprove";
		if (handleType != null) {
			switch (handleType) {
			case BizPubParm.ASSET_HANDLE_TYPE_AUCTION:
				formId = "AssetsDisposalApprove_Auction";
				break;
			case BizPubParm.ASSET_HANDLE_TYPE_LEASE:
				formId = "AssetsDisposalApprove_Lease";
				break;
				case BizPubParm.ASSET_HANDLE_TYPE_NEGOTIATE:
					formId = "AssetsDisposalApprove_Auction";
					break;
			default:
				break;
			}

		}
		FormData formAssetsDisposalApprove = formService.getFormData(formId);
		getFormValue(formAssetsDisposalApprove, formDataMap);
		MfAssetsDisposal mfAssetsDisposal = new MfAssetsDisposal();
		setObjValue(formAssetsDisposalApprove, mfAssetsDisposal);
		Result res;
		try {
			new FeignSpringFormEncoder().addParamsToBaseDomain(mfAssetsDisposal);
			formDataMap.put("mfAssetsDisposal", mfAssetsDisposal);
			res = mfAssetsDisposalFeign.doCommit(taskId, transition, nextUser, formDataMap);
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

}
