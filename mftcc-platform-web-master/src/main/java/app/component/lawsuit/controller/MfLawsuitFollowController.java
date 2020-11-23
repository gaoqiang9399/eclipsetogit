package app.component.lawsuit.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.common.BizPubParm;
import app.component.lawsuit.entity.MfLawsuit;
import app.component.lawsuit.feign.MfLawsuitFeign;
import app.component.pact.assetmanage.entity.MfLitigationExpenseApply;
import cn.mftcc.util.MathExtend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;

import app.component.common.EntityUtil;
import app.component.lawsuit.entity.MfLawsuitFollow;
import app.component.lawsuit.feign.MfLawsuitFollowFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.StringUtil;

/**
 * Title: MfLawsuitFollowAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Feb 22 21:21:30 CST 2017
 **/
@Controller
@RequestMapping("/mfLawsuitFollow")
public class MfLawsuitFollowController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfLawsuitFollowBo
	@Autowired
	private MfLawsuitFollowFeign mfLawsuitFollowFeign;
	@Autowired
	private MfLawsuitFeign mfLawsuitFeign;
	// 全局变量
	// 异步参数

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		return "/component/lawsuit/MfLawsuitFollow_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfLawsuitFollow mfLawsuitFollow = new MfLawsuitFollow();
		try {
			mfLawsuitFollow.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfLawsuitFollow.setCriteriaList(mfLawsuitFollow, ajaxData);// 我的筛选
			// this.getRoleConditions(mfLawsuitFollow,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = mfLawsuitFollowFeign.findByPage(ipage, mfLawsuitFollow);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
			/**
			 * ipage.setResult(tableHtml); dataMap.put("ipage",ipage); 需要改进的方法
			 * dataMap.put("tableData",tableHtml);
			 */
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * 获取案件跟进列表列表
	 */

	@RequestMapping(value = "/getMfLawsuitFollowList")
	@ResponseBody
	public Map<String, Object> getMfLawsuitFollowList(String caseId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfLawsuitFollow mfLawsuitFollow = new MfLawsuitFollow();
		try {
			mfLawsuitFollow.setCaseId(caseId);
			List<MfLawsuitFollow> mfLawsuitFollowList=mfLawsuitFollowFeign.getFollowListByCaseId(mfLawsuitFollow);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr("tablelawsuitfollow0001", "tableTag", mfLawsuitFollowList, null, true);
			List<MfLawsuitFollow> executionRecoveryList = new ArrayList<MfLawsuitFollow>();
			double actAmt = 0.00;
			if(mfLawsuitFollowList.size()>0 && mfLawsuitFollowList != null){
				for(MfLawsuitFollow mlfList:mfLawsuitFollowList){//如果存在执行中的跟进记录同步更新执行回收情况块
					if("4".equals(mlfList.getFollowType())){
						executionRecoveryList.add(mlfList);
					}//如果是判决或者调解,和解的情况
					else if("3".equals(mlfList.getFollowType()) || "8".equals(mlfList.getFollowType())|| "9".equals(mlfList.getFollowType())) {
						JsonFormUtil jsonFormUtil = new JsonFormUtil();
						FormData formdecisionMediateDetail = formService.getFormData("decisionMediateDetail");
						dataMap.put("mfLawsuitFollow", mlfList);
						getObjValue(formdecisionMediateDetail, mlfList);
						String htmlStrDecision = jsonFormUtil.getJsonStr(formdecisionMediateDetail, "propertySeeTag", "");
						dataMap.put("htmlStrDecision", htmlStrDecision);
					}else {
					}
					if("4".equals(mlfList.getFollowType())){//详情页面获取执行金额
						actAmt = MathExtend.add(actAmt,mlfList.getActAmt());
						if(actAmt<=0){
							actAmt=0.00;
							}

					}
				}
				JsonTableUtil j = new JsonTableUtil();
				String execRecovHtml = jtu.getJsonStr("tableExecutionRecoveryList", "tableTag", executionRecoveryList, null, true);
				dataMap.put("execRecovHtml", execRecovHtml);
			}
			//刷新法律诉讼详情表单
			MfLawsuit mfLawsuit = new MfLawsuit();
			mfLawsuit.setCaseId(caseId);
			String scNo = BizPubParm.SCENCE_TYPE_DOC_LAWSUIT;
			mfLawsuit = mfLawsuitFeign.getById(mfLawsuit);
			if(mfLawsuit != null){
				JsonFormUtil ju = new JsonFormUtil();
				FormData lawsuit0003 = formService.getFormData("lawsuit0003");
				getObjValue(lawsuit0003,mfLawsuit);
				String lawsuitHtml = ju.getJsonStr(lawsuit0003,"propertySeeTag","");
				dataMap.put("lawsuitHtml", lawsuitHtml);

			}

			dataMap.put("htmlStr", tableHtml);
			dataMap.put("actAmt", MathExtend.moneyStr(actAmt));

		} catch (Exception e) {
			e.printStackTrace();
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
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formlawsuitfollow0002 = formService.getFormData("lawsuitfollow0002");
			getFormValue(formlawsuitfollow0002, getMapByJson(ajaxData));
			dataMap=getMapByJson(ajaxData);
			String caseId = (String)dataMap.get("caseId");
			if (this.validateFormData(formlawsuitfollow0002)) {
				MfLawsuitFollow mfLawsuitFollow = new MfLawsuitFollow();
				setObjValue(formlawsuitfollow0002, mfLawsuitFollow);
				mfLawsuitFollowFeign.insert(mfLawsuitFollow);
				MfLawsuit mfLawsuit = new MfLawsuit();
				mfLawsuit.setCaseId(caseId);
				mfLawsuit = mfLawsuitFeign.getById(mfLawsuit);
				if("4".equals(mfLawsuitFollow.getFollowType())){//执行回收时同步更新法律诉讼表执行回收金额
					if(mfLawsuit !=null && mfLawsuit.getCost()!=null){//
						mfLawsuit.setRecoverableAmount(MathExtend.add(mfLawsuit.getRecoverableAmount(),mfLawsuitFollow.getActAmt()));
						mfLawsuitFeign.update(mfLawsuit);
					}
				}
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
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
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formlawsuitfollow0002 = formService.getFormData("lawsuitfollow0002");
		getFormValue(formlawsuitfollow0002, getMapByJson(ajaxData));
		MfLawsuitFollow mfLawsuitFollowJsp = new MfLawsuitFollow();
		setObjValue(formlawsuitfollow0002, mfLawsuitFollowJsp);
		MfLawsuitFollow mfLawsuitFollow = mfLawsuitFollowFeign.getById(mfLawsuitFollowJsp);
		if (mfLawsuitFollow != null) {
			try {
				mfLawsuitFollow = (MfLawsuitFollow) EntityUtil.reflectionSetVal(mfLawsuitFollow, mfLawsuitFollowJsp,
						getMapByJson(ajaxData));
				mfLawsuitFollowFeign.update(mfLawsuitFollow);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
				throw e;
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
		}
		return dataMap;
	}

	/**
	 * AJAX更新保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formlawsuitfollow0002 = formService.getFormData("lawsuitfollow0002");
			getFormValue(formlawsuitfollow0002, getMapByJson(ajaxData));
			if (this.validateFormData(formlawsuitfollow0002)) {
				MfLawsuitFollow mfLawsuitFollow = new MfLawsuitFollow();
				setObjValue(formlawsuitfollow0002, mfLawsuitFollow);
				mfLawsuitFollowFeign.update(mfLawsuitFollow);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
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
	 * AJAX获取查看
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String followId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		String query = "query";
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formlawsuitfollow0002 = formService.getFormData("lawsuitfollow0001");
		MfLawsuitFollow mfLawsuitFollow = new MfLawsuitFollow();
		mfLawsuitFollow.setFollowId(followId);
		mfLawsuitFollow = mfLawsuitFollowFeign.getById(mfLawsuitFollow);
		getObjValue(formlawsuitfollow0002, mfLawsuitFollow, formData);
		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		String htmlStr = jsonFormUtil.getJsonStr(formlawsuitfollow0002, "bootstarpTag", query);
		dataMap.put("htmlStr", htmlStr);
		if (mfLawsuitFollow != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("query", query);
		dataMap.put("formData", formData);
		return dataMap;
	}

	/**
	 * Ajax异步删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String followId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfLawsuitFollow mfLawsuitFollow = new MfLawsuitFollow();
		mfLawsuitFollow.setFollowId(followId);
		try {
			String followDate = mfLawsuitFollowFeign.delete(mfLawsuitFollow);
			dataMap.put("followDate", DateUtil.getShowDateTime(followDate));
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_DELETE.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
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
	@RequestMapping(value = "/input")
	public String input(Model model,String caseId,String followType) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfLawsuitFollow mfLawsuitFollow = new MfLawsuitFollow();
		String formId ="";
		//默认跟进类型是受理
		if("".equals(followType) || StringUtil.isEmpty(followType) ||"1".equals(followType)){
			followType = "1";
		}
		//默认跟进类型是受理
		switch(followType){
		case "1":
			formId = "lawsuitfollowaccept";
			break;
		case "2":
			//审理表单
			formId = "lawsuitfollowtrial";
			break;
		case "3":
			//判决
			formId = "lawsuitfollowsentence";
			break;
		case "4":
			//执行
			formId = "lawsuitfollowperform";
			break;
		case "5":
			//撤诉
			formId = "lawsuitfollownolleprosequi";
			break;
		case "6":
			//终结
			formId = "lawsuitfollowend";
			break;
		case "8":
			//和解
			formId = "lawsuitfollowsettlement";
			break;
		case "9":
			//调解
			formId = "lawsuitfollowmediation";
			break;

		case "10":
			//破产清算
			formId = "lawsuitfollowbankruptcy";
			break;
		case "11":
			//执行程序转破产清算
			formId = "lawsuitfollowperformbankruptcy";
			break;
		case "12":
			//结案
			formId = "lawsuitfollowcaseend";
			break;
		default://其他
			formId = "lawsuitfollowother";
			break;
		
		}
		FormData formlawsuitfollow0002 = formService.getFormData(formId);
		mfLawsuitFollow.setFollowType(followType);
		getObjValue(formlawsuitfollow0002, mfLawsuitFollow);
		model.addAttribute("formlawsuitfollow0002", formlawsuitfollow0002);
		model.addAttribute("caseId", caseId);
		model.addAttribute("query", "");
		return "/component/lawsuit/MfLawsuitFollow_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formlawsuitfollow0002 = formService.getFormData("lawsuitfollow0002");
		getFormValue(formlawsuitfollow0002);
		MfLawsuitFollow mfLawsuitFollow = new MfLawsuitFollow();
		setObjValue(formlawsuitfollow0002, mfLawsuitFollow);
		mfLawsuitFollowFeign.insert(mfLawsuitFollow);
		getObjValue(formlawsuitfollow0002, mfLawsuitFollow);
		this.addActionMessage(model, "保存成功");
		List<MfLawsuitFollow> mfLawsuitFollowList = (List<MfLawsuitFollow>) mfLawsuitFollowFeign.findByPage(this.getIpage(), mfLawsuitFollow)
				.getResult();
		model.addAttribute("mfLawsuitFollowList", mfLawsuitFollowList);
		model.addAttribute("formlawsuitfollow0002", formlawsuitfollow0002);
		model.addAttribute("query", "");
		return "/component/lawsuit/MfLawsuitFollow_Insert";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String followId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formlawsuitfollow0002 = formService.getFormData("lawsuitfollow0001");
		getFormValue(formlawsuitfollow0002);
		MfLawsuitFollow mfLawsuitFollow = new MfLawsuitFollow();
		mfLawsuitFollow.setFollowId(followId);
		mfLawsuitFollow = mfLawsuitFollowFeign.getById(mfLawsuitFollow);
		getObjValue(formlawsuitfollow0002, mfLawsuitFollow);
		model.addAttribute("mfLawsuitFollow", mfLawsuitFollow);
		model.addAttribute("formlawsuitfollow0002", formlawsuitfollow0002);
		model.addAttribute("query", "");
		return "/component/lawsuit/MfLawsuitFollow_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String followId) throws Exception {
		ActionContext.initialize(request, response);
		MfLawsuitFollow mfLawsuitFollow = new MfLawsuitFollow();
		mfLawsuitFollow.setFollowId(followId);
		mfLawsuitFollowFeign.delete(mfLawsuitFollow);
		return getListPage(model);
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formlawsuitfollow0002 = formService.getFormData("lawsuitfollow0002");
		getFormValue(formlawsuitfollow0002);
		boolean validateFlag = this.validateFormData(formlawsuitfollow0002);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formlawsuitfollow0002 = formService.getFormData("lawsuitfollow0002");
		getFormValue(formlawsuitfollow0002);
		boolean validateFlag = this.validateFormData(formlawsuitfollow0002);
	}
	/**
	 * 单子段编辑额回调处理
	 *
	 * @param
	 * @return dataMap
	 * @throws Exception
	 */
	@RequestMapping(value = "/dealFollowPerformAjax")
	@ResponseBody
	public Map<String, Object> dealFollowPerformAjax(String caseId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		MfLawsuitFollow mfLawsuitFollow = new MfLawsuitFollow();
		try {
			mfLawsuitFollow.setCaseId(caseId);
			List<MfLawsuitFollow> mfLawsuitFollowList = mfLawsuitFollowFeign.getFollowListByCaseId(mfLawsuitFollow);
			MfLawsuit mfLawsuit = new MfLawsuit();
			mfLawsuit.setCaseId(caseId);
			Double sum =0.00;
			for (MfLawsuitFollow follows : mfLawsuitFollowList){
				if("4".equals(follows.getFollowType())){//执行回收时同步更新法律诉讼表执行回收金额
					sum = MathExtend.add(sum,follows.getActAmt());
				}
			}
			mfLawsuit.setRecoverableAmount(sum);
			mfLawsuitFeign.update(mfLawsuit);

			dataMap.put("flag", "success");
			dataMap.put("recoverableAmount", MathExtend.moneyStr(sum));
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}

}
