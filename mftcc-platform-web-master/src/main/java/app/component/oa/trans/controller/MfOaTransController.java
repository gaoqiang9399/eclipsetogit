package app.component.oa.trans.controller;

import app.base.User;
import app.component.app.entity.MfBusApply;
import app.component.app.entity.MfCusAndApply;
import app.component.appinterface.AppInterfaceFeign;
import app.component.auth.entity.MfCusCreditApply;
import app.component.authinterface.CreditApplyInterfaceFeign;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCustomer;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.nmd.entity.ParmDic;
import app.component.oa.trans.entity.MfOaTrans;
import app.component.oa.trans.entity.MfOaTransHis;
import app.component.oa.trans.feign.MfOaTransFeign;
import app.component.wkf.entity.Result;
import app.component.wkf.entity.WkfApprovalOpinion;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.tech.upload.FeignSpringFormEncoder;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import com.alibaba.fastjson.JSONObject;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: MfOaTransAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Apr 25 15:20:00 CST 2017
 **/
@Controller
@RequestMapping(value = "/mfOaTrans")
public class MfOaTransController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfOaTransFeign mfOaTransFeign;
	@Autowired
	public WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;
	@Autowired
	private CusInterfaceFeign cusInterfaceFeign;
	@Autowired
	private CreditApplyInterfaceFeign creditApplyInterfaceFeign;
	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "/component/oa/trans/MfOaTrans_List";
	}

	@RequestMapping("/getTransAppHis")
	public String getTransAppHis(Model model) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "/component/oa/trans/MfOaTrans_getTransAppHis";
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
		try {
			MfOaTrans mfOaTrans = new MfOaTrans();
			mfOaTrans.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfOaTrans.setCriteriaList(mfOaTrans, ajaxData);// 我的筛选
			// this.getRoleConditions(mfOaTrans,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfOaTrans", mfOaTrans));
			ipage = mfOaTransFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
			/**
			 * ipage.setResult(tableHtml); dataMap.put("ipage",ipage); 需要改进的方法
			 * dataMap.put("tableData",tableHtml);
			 */
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	@RequestMapping("/getByTransId")
	public String getByTransId(String transId, Model model) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("transId",transId);
		model.addAttribute("query", "");
		return "/component/oa/trans/MfOaTrans_DetailList";
	}

	/**
	 * AJAX新增
	 * 
	 * @param ajaxData
	 * @param cusTransNo
	 * @param appTransNo
	 * @param pactTransNo
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/insertAjax")
	public Map<String, Object> insertAjax(String ajaxData, String cusTransNo, String appTransNo, String pactTransNo)
			throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = getMapByJson(ajaxData);
			String formId = (String) dataMap.get("formId");
			FormData formoatrans0002 = new FormService().getFormData(formId);
			getFormValue(formoatrans0002, dataMap);
			if (this.validateFormData(formoatrans0002)) {
				MfOaTrans mfOaTrans = new MfOaTrans();
				setObjValue(formoatrans0002, mfOaTrans);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("mfOaTrans", mfOaTrans);
				mfOaTrans = mfOaTransFeign.insert(map);
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("userRole", mfOaTrans.getApproveNodeName());
				paramMap.put("opNo", mfOaTrans.getApprovePartName());
				dataMap.put("flag", "success");
				dataMap.put("msg", "移交成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("移交"));
		}
		return dataMap;
	}

	/**
	 * AJAX获取客户和业务信息
	 * 
	 * @param model
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getCustomerList")
	public String getCustomerList(Model model,String cusMngNo) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		model.addAttribute("cusMngNo", cusMngNo);
		return "/component/oa/trans/MfOaTrans_CusList";
	}

	@ResponseBody
	@RequestMapping("/getCustomerListAjax")
	public Map<String, Object> getCustomerListAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData, String cusMngNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		try {
			mfCusCustomer.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusCustomer.setCustomSorts(ajaxData);//自定义排序参数赋值
			mfCusCustomer.setCriteriaList(mfCusCustomer, ajaxData);//我的筛选
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			mfCusCustomer.setCusMngNo(cusMngNo);
			dataMap.put("mfCusCustomer",mfCusCustomer);
			ipage.setParams(dataMap);
			ipage = cusInterfaceFeign.getByCusMngNo(ipage);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}

		return dataMap;
	}

	@RequestMapping("/getBusList")
	public String getBusList(String cusMngNo, Model model) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
//			List<MfCusAndApply> cusAndAppList = appInterfaceFeign.getByCusMngNo(cusMngNo);
//			model.addAttribute("cusAndAppList", cusAndAppList);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "查询失败");
			throw e;
		}
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("query", "");
		return "/component/oa/trans/MfOaTrans_BusList";
	}


	@ResponseBody
	@RequestMapping("/getBusTransListAjax")
	public Map<String, Object> getBusTransListAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,String ajaxData, String cusMngNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusApply mfBusApply = new MfBusApply();
		try {
			mfBusApply.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfBusApply.setCustomSorts(ajaxData);//自定义排序参数赋值
			mfBusApply.setCriteriaList(mfBusApply, ajaxData);//我的筛选
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			mfBusApply.setManageOpNo1(cusMngNo);
			dataMap.put("mfBusApply",mfBusApply);
			ipage.setParams(dataMap);
			ipage = appInterfaceFeign.getBusTransList(ipage);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX获取操作员下客户与项目的数目
	 * 
	 * @param cusMngNo
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getCusAndAppNumAjax")
	public Map<String, Object> getCusAndAppNumAjax(String cusMngNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfCusCustomer mfCusCustomer = new MfCusCustomer();
			mfCusCustomer.setCusMngNo(cusMngNo);
			MfBusApply mfBusApply = new MfBusApply();
			mfBusApply.setManageOpNo1(cusMngNo);
			int cusCount = cusInterfaceFeign.findCusNumByMngNo(mfCusCustomer);
			int busCount = appInterfaceFeign.getBusCountByCusMngNo(mfBusApply);
			MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
			mfCusCreditApply.setOpNo(cusMngNo);
			int creditCount = creditApplyInterfaceFeign.getCreditCountByCusMngNo(mfCusCreditApply);
			dataMap.put("flag", "success");
			dataMap.put("cusCount", cusCount);
			dataMap.put("busCount", busCount);
			dataMap.put("creditCount", creditCount);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("查询"));
			throw e;
		}
		return dataMap;
	}

	/**
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @param ajaxData
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/updateAjaxByOne")
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formoatrans0002 = new FormService().getFormData("oatrans0002");
		getFormValue(formoatrans0002, getMapByJson(ajaxData));
		MfOaTrans mfOaTransJsp = new MfOaTrans();
		setObjValue(formoatrans0002, mfOaTransJsp);
		MfOaTrans mfOaTrans = mfOaTransFeign.getById(mfOaTransJsp);
		if (mfOaTrans != null) {
			try {
				mfOaTrans = (MfOaTrans) EntityUtil.reflectionSetVal(mfOaTrans, mfOaTransJsp, getMapByJson(ajaxData));
				mfOaTransFeign.update(mfOaTrans);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage("保存"));
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("新增"));
				throw e;
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("编号不存在，保存"));
		}
		return dataMap;
	}

	/**
	 * AJAX更新保存
	 * 
	 * @param ajaxData
	 * @param taskId
	 * @param id
	 * @param transition
	 * @param nextUser
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/updateAjax")
	public Map<String, Object> updateAjax(String ajaxData, String taskId, String id, String transition, String nextUser)
			throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfOaTrans mfOaTrans = new MfOaTrans();
		try {
			FormData formoatrans0003 = new FormService().getFormData("oatrans0003");
			getFormValue(formoatrans0003, getMapByJson(ajaxData));
			if (this.validateFormData(formoatrans0003)) {
				mfOaTrans = new MfOaTrans();
				setObjValue(formoatrans0003, mfOaTrans);
				dataMap = getMapByJson(ajaxData);
				String opinionType = String.valueOf(dataMap.get("opinionType"));
				String approvalOpinion = String.valueOf(dataMap.get("approvalOpinion"));
				new FeignSpringFormEncoder().addParamsToBaseDomain(mfOaTrans);
				dataMap.put("mfOaTrans", mfOaTrans);

				Result res = mfOaTransFeign.doCommit(taskId, id, opinionType, approvalOpinion, transition,
						User.getRegNo(this.request), nextUser, dataMap);
				if (!res.isSuccess()) {

				} else {
					dataMap.put("flag", "success");
					dataMap.put("msg", res.getMsg());
				}
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("更新"));
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX获取查看
	 * 
	 * @param id
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/getByIdAjax")
	public Map<String, Object> getByIdAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> formData = new HashMap<String, Object>();
		FormData formoatrans0002 = new FormService().getFormData("oatrans0002");
		MfOaTrans mfOaTrans = new MfOaTrans();
		mfOaTrans.setId(id);
		mfOaTrans = mfOaTransFeign.getById(mfOaTrans);
		getObjValue(formoatrans0002, mfOaTrans, formData);
		if (mfOaTrans != null) {
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
	 * @param id
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/deleteAjax")
	public Map<String, Object> deleteAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfOaTrans mfOaTrans = new MfOaTrans();
		mfOaTrans.setId(id);
		try {
			mfOaTransFeign.delete(mfOaTrans);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_OPERATION.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage());
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
		String view =request.getParameter("view");

		FormData formoatrans0002 = new FormService().getFormData("oatrans0002");
		model.addAttribute("formoatrans0002", formoatrans0002);
		model.addAttribute("query", "");
		model.addAttribute("processId", "2017427145850");
		model.addAttribute("view", view);
		return "/component/oa/trans/MfOaTrans_Insert";
	}


	/**
	 * 查询
	 * 
	 * @param id
	 * @param model
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(String id, Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormData formoatrans0001 = new FormService().getFormData("oatrans0001");
		getFormValue(formoatrans0001);
		MfOaTrans mfOaTrans = new MfOaTrans();
		mfOaTrans.setId(id);
		mfOaTrans = mfOaTransFeign.getById(mfOaTrans);
		getObjValue(formoatrans0001, mfOaTrans);
		model.addAttribute("formoatrans0001", formoatrans0001);
		model.addAttribute("mfOaTrans", mfOaTrans);
		model.addAttribute("query", "");
		return "/component/oa/trans/MfOaTrans_Detail";
	}

	/**
	 * 删除
	 * 
	 * @param id,Model
	 *            model
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(String id, Model model) throws Exception {
		ActionContext.initialize(request, response);
		MfOaTrans mfOaTrans = new MfOaTrans();
		mfOaTrans.setId(id);
		mfOaTransFeign.delete(mfOaTrans);
		return getListPage(model);
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
		FormData formoatrans0002 = new FormService().getFormData("oatrans0002");
		getFormValue(formoatrans0002);
		boolean validateFlag = this.validateFormData(formoatrans0002);
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
		FormData formoatrans0002 = new FormService().getFormData("oatrans0002");
		getFormValue(formoatrans0002);
		boolean validateFlag = this.validateFormData(formoatrans0002);
	}

	@RequestMapping("/getViewPoint")
	public String getViewPoint(String id, String taskId, String activityType, Model model,String hideOpinionType) throws Exception {
		ActionContext.initialize(request, response);
		MfOaTrans mfOaTrans = new MfOaTrans();
		mfOaTrans.setId(id);
		mfOaTrans = mfOaTransFeign.getById(mfOaTrans);
		FormData formoatrans0003 = new FormService().getFormData("oatrans0003");
		getObjValue(formoatrans0003, mfOaTrans);

		List<MfOaTrans> mfOaTransList = mfOaTransFeign.getByTransId(mfOaTrans);
		// 获得审批节点信息
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(id, null);
		// 处理审批意见类型
		 Map<String, String> opinionTypeMap = new HashMap<String, String>();
		opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
		opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
		opinionTypeMap.put("nodeNo", taskAppro.getActivityName());// 当前节点编号
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,taskAppro.getCouldRollback(), opinionTypeMap);
		this.changeFormProperty(formoatrans0003, "opinionType", "optionArray", opinionTypeList);
		// 获得当前审批岗位前面审批过得岗位信息
		JSONArray befNodesjsonArray = new JSONArray();
		befNodesjsonArray = wkfInterfaceFeign.getBefNodes1(taskId,User.getRegNo(request));
		model.addAttribute("formoatrans0003", formoatrans0003);
		model.addAttribute("mfOaTrans", mfOaTrans);
		model.addAttribute("mfOaTransList", mfOaTransList);
		model.addAttribute("befNodesjsonArray", befNodesjsonArray);
		model.addAttribute("activityType", activityType);
		model.addAttribute("taskId", taskId);
		model.addAttribute("id", id);
		model.addAttribute("query", "");
		return "/component/oa/trans/MfOaTrans_getViewPoint";
	}

	@ResponseBody
	@RequestMapping("/getApplyApprovalOpinionList")
	public Map<String, Object> getApplyApprovalOpinionList(String id) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		CodeUtils cu = new CodeUtils();
		List<ParmDic> pdList = (List<ParmDic>) cu.getCacheByKeyName("APPROVAL_RESULT");
		List<WkfApprovalOpinion> ideaList = wkfInterfaceFeign.getWkfTaskHisList(id);
		// if(ideaList.size()>1){
		// ideaList.remove(ideaList.size()-1);
		// }
		JSONArray zTreeNodes = JSONArray.fromObject(ideaList);
		for (int i = 0; i < zTreeNodes.size(); i++) {
			zTreeNodes.getJSONObject(i).put("id", zTreeNodes.getJSONObject(i).getString("execution"));
			zTreeNodes.getJSONObject(i).put("name", zTreeNodes.getJSONObject(i).getString("description"));
			zTreeNodes.getJSONObject(i).put("pId", "0");
			for (ParmDic parmDic : pdList) {
				if (parmDic.getOptCode().equals(zTreeNodes.getJSONObject(i).getString("result"))) {
					zTreeNodes.getJSONObject(i).put("optName", parmDic.getOptName());
					break;
				}
			}
		}
		dataMap.put("zTreeNodes", zTreeNodes);
		return dataMap;
	}

	//移交历史
	@RequestMapping("/getHisListPage")
	public String getHisListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		String typeFlag = request.getParameter("typeFlag");//1-查询 2-客户详情
		String cusNo = "";
		if("2".equals(typeFlag)){
			cusNo = request.getParameter("cusNo");
		}
		model.addAttribute("query", "");
		model.addAttribute("typeFlag",typeFlag);
		model.addAttribute("cusNo", cusNo);
		return "/component/oa/trans/MfOaTrans_ListHis";
	}

	/***
	 * 历史列表数据查询
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/findByPageHisAjax")
	public Map<String, Object> findByPageHisAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
												 String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String cusNo = request.getParameter("cusNo");
			MfOaTransHis mfOaTransHis = new MfOaTransHis();
			mfOaTransHis.setCusNo(cusNo);
			mfOaTransHis.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfOaTransHis.setCriteriaList(mfOaTransHis, ajaxData);// 我的筛选
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfOaTransHis", mfOaTransHis));
			ipage = mfOaTransFeign.findByPageHis(ipage);
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
	 * @Description 获取移交明细页面列表数据
	 * @Author zhaomingguang
	 * @DateTime 2019/10/23 18:17
	 * @Param [pageNo, pageSize, tableId, tableType, ajaxData, transId]
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	@RequestMapping("/findMfOaTransDetailListByPageAjax")
	@ResponseBody
	public Map<String, Object> findMfOaTransDetailListByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
												   String ajaxData,String transId )throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfOaTrans mfOaTrans = new MfOaTrans();
			mfOaTrans.setTransId(transId);
			mfOaTrans.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfOaTrans.setCriteriaList(mfOaTrans, ajaxData);// 我的筛选
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfOaTrans", mfOaTrans));
			ipage = mfOaTransFeign.findMfOaTransDetailListByPageAjax(ipage);
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
	 * @Description 获取页面回显展示列表数据
	 * @Author zhaomingguang
	 * @DateTime 2019/10/31 9:59
	 * @Param [ajaxData]
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	@RequestMapping(value = "/oaTransListData")
	@ResponseBody
	public Map<String,Object> oaTransListData(String ajaxData)throws Exception{
		ActionContext.initialize(request, response);
		Map<String,Object> dataMap = new HashMap<>();
		try {
			Map<String,String> paraMap = JSONObject.parseObject(ajaxData,Map.class);
			String htmlStr = "";
			String tableFormId = "tablecusOaTransList";
			JsonTableUtil jtu = new JsonTableUtil();
			if("2".equals(paraMap.get("transType"))){
				tableFormId = "tablebusOaTransList";
				List<MfCusAndApply> busOaTransListData = mfOaTransFeign.getBusOaTransListData(paraMap);
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag", busOaTransListData, null, true);
			}else{
				List<MfCusCustomer> cusOaTransListData = mfOaTransFeign.getCusOaTransListData(paraMap);
				htmlStr = jtu.getJsonStr(tableFormId, "tableTag", cusOaTransListData, null, true);
			}
			dataMap.put("htmlStr", htmlStr);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * @Description 获取授信移交列表数据
	 * @Author zhaomingguang
	 * @DateTime 2019/11/1 17:44
	 * @Param [pageNo, pageSize, tableId, tableType, ajaxData, cusMngNo]
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	@ResponseBody
	@RequestMapping("/getCreditCusListAjax")
	public Map<String, Object> getCreditCusListAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
												   String ajaxData, String cusMngNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		try {
			mfCusCustomer.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusCustomer.setCustomSorts(ajaxData);//自定义排序参数赋值
			mfCusCustomer.setCriteriaList(mfCusCustomer, ajaxData);//我的筛选
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			mfCusCustomer.setCusMngNo(cusMngNo);
			dataMap.put("mfCusCustomer",mfCusCustomer);
			ipage.setParams(dataMap);
			ipage = cusInterfaceFeign.getCreditCusByCusMngNo(ipage);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
}
