package app.component.pact.receaccount.controller;

import app.base.User;
import app.component.app.entity.MfBusApply;
import app.component.common.BizPubParm;
import app.component.pact.entity.MfBusPact;
import app.component.pact.feign.MfBusPactFeign;
import app.component.pact.receaccount.entity.MfBusReceTransfer;
import app.component.pact.receaccount.entity.MfBusReceTransferMain;
import app.component.pact.receaccount.feign.MfBusReceTransferFeign;
import app.component.pact.receaccount.feign.MfBusReceTransferMainFeign;
import app.component.prdct.entity.MfKindFlow;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.component.wkf.entity.Result;
import app.tech.oscache.CodeUtils;
import app.util.DataUtil;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: MfBusReceTransferMainController.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Aug 11 18:19:17 CST 2017
 **/
@Controller
@RequestMapping("/mfBusReceTransferMain")
public class MfBusReceTransferMainController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfBusReceTransferMainFeign mfBusReceTransferMainFeign;
	@Autowired
	private MfBusPactFeign mfBusPactFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;
	@Autowired
	private MfBusReceTransferFeign mfBusReceTransferFeign;

	/**
	 * 方法描述：跳转至转让列表页面
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception{
		ActionContext.initialize(request, response);
		JSONArray transStsJsonArray = new CodeUtils().getJSONArrayByKeyName("RECE_TRANS_STS");
		model.addAttribute("transStsJsonArray", transStsJsonArray);
		return "/component/pact/receaccount/MfBusReceTransferMain_List";
	}

	/**
	 * 方法描述：获取转让列表数据
	 * @param pageNo
	 * @param pageSize
	 * @param tableId
	 * @param tableType
	 * @param ajaxData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String,Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,String ajaxData,String transSts) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusReceTransferMain mfBusReceTransferMain = new MfBusReceTransferMain();
		try {
			mfBusReceTransferMain.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfBusReceTransferMain.setCustomSorts(ajaxData);// 自定义排序参数赋值
			mfBusReceTransferMain.setCriteriaList(mfBusReceTransferMain, ajaxData);// 我的筛选
			mfBusReceTransferMain.setTransSts(transSts);
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfBusReceTransferMain", mfBusReceTransferMain));
			ipage = mfBusReceTransferMainFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		}catch (Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}


	/**
	 * 方法描述：获取可以账款转让的合同列表
	 * @param pageNo
	 * @param ajaxData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPactListAjax")
	@ResponseBody
	public Map<String,Object> getPactListAjax(Integer pageNo,String ajaxData) throws Exception{
		ActionContext.initialize(request, response);
		Map<String,Object> dataMap = new HashMap<String,Object>();
		MfBusPact mfBusPact = new MfBusPact();
		try {
			mfBusPact.setCustomQuery(ajaxData);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfBusPact", mfBusPact));
			ipage = mfBusPactFeign.getPactListForReceTrans(ipage);
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
	 *
	 * 方法描述 保存转让主表信息
	 * @param [pactId]
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 * @author zhs
	 * @date 2018/8/16 16:24
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String,Object> insertAjax(String pactId) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfBusPact mfBusPact = new MfBusPact();
			mfBusPact.setPactId(pactId);
			mfBusPact= mfBusPactFeign.getById(mfBusPact);
			//获取该合同下待提交的应收账款
			MfBusReceTransfer mfBusReceTransfer = new MfBusReceTransfer();
			mfBusReceTransfer.setAppId(mfBusPact.getAppId());
			mfBusReceTransfer.setTransSts("0");//待提交
			List<MfBusReceTransfer> mfBusReceTransferList = mfBusReceTransferFeign.getUnSubmitTransferList(mfBusReceTransfer);
			if(mfBusReceTransferList==null ||mfBusReceTransferList.size()==0){
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.FIRST_OPERATION.getMessage("账款转让"));

			}else{
				MfBusReceTransferMain mfBusReceTransferMain = new MfBusReceTransferMain();
				mfBusReceTransferMain.setPactId(pactId);
				mfBusReceTransferMain = mfBusReceTransferMainFeign.insert(mfBusReceTransferMain);
				dataMap.put("transProcessId",mfBusReceTransferMain.getTransProcessId());
				dataMap.put("transMainId",mfBusReceTransferMain.getTransMainId());
				if(StringUtil.isNotEmpty(mfBusReceTransferMain.getTransProcessId())){
					Map<String,String> parmMap = new HashMap<String,String>();
					parmMap.put("content","保存成功");
					parmMap.put("operation","提交审批");
					dataMap.put("msg", MessageEnum.CONFIRM_DETAIL_OPERATION.getMessage(parmMap));
					dataMap.put("ifApproval","1");
				}else{
					dataMap.put("ifApproval","0");
					dataMap.put("msg",MessageEnum.SUCCEED_SAVE.getMessage());
				}
				dataMap.put("flag", "success");
			}

		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SUBMIT_CONTENT.getMessage(e.getMessage()));
			throw e;
		}
		return dataMap;
	}

	/**
	 *
	 * 方法描述 更新转让主表信息
	 * @param [pactId]
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 * @author zhs
	 * @date 2018/8/16 16:24
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String,Object> updateAjax(String pactId,String transMainId) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfBusReceTransferMain mfBusReceTransferMain = new MfBusReceTransferMain();
			mfBusReceTransferMain.setPactId(pactId);
			mfBusReceTransferMain.setTransMainId(transMainId);
			mfBusReceTransferMain = mfBusReceTransferMainFeign.update(mfBusReceTransferMain);
			dataMap.put("transProcessId",mfBusReceTransferMain.getTransProcessId());
			dataMap.put("transMainId",mfBusReceTransferMain.getTransMainId());
			if(StringUtil.isNotEmpty(mfBusReceTransferMain.getTransProcessId())){
				Map<String,String> parmMap = new HashMap<String,String>();
				parmMap.put("content","保存成功");
				parmMap.put("operation","提交审批");
				dataMap.put("msg", MessageEnum.CONFIRM_DETAIL_OPERATION.getMessage(parmMap));
				dataMap.put("ifApproval","1");
			}else{
				dataMap.put("ifApproval","0");
				dataMap.put("msg",MessageEnum.SUCCEED_SAVE.getMessage());
			}
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("flag", MessageEnum.FAILED_SUBMIT_CONTENT.getMessage(e.getMessage()));
			throw e;
		}
		return dataMap;
	}


	/**
	 *
	 * 方法描述  提交转让审批流程
	 * @param [pactId, transMainId]
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 * @author zhs
	 * @date 2018/8/16 16:24
	 */
	@RequestMapping("/submitProcess")
	@ResponseBody
	public Map<String,Object> submitProcess(String pactId,String transMainId) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfBusReceTransferMain mfBusReceTransferMain = new MfBusReceTransferMain();
			mfBusReceTransferMain.setTransMainId(transMainId);
			mfBusReceTransferMain.setPactId(pactId);
			dataMap = mfBusReceTransferMainFeign.submitProcess(mfBusReceTransferMain);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			throw e;
		}
		return dataMap;
	}

	/**
	 *
	 * 方法描述 跳转至账款转让审批页面
	 * @param [model, ajaxData, appId, transMainId, taskId, hideOpinionType, activityType]
	 * @return java.lang.String
	 * @author zhs
	 * @date 2018/8/17 11:25
	 */
	@RequestMapping(value = "/getViewPoint")
	public String getViewPoint(Model model, String ajaxData, String appId,String transMainId, String taskId, String hideOpinionType, String activityType) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		TaskImpl taskAppro = wkfInterfaceFeign.getTask(transMainId, taskId);// 当前审批节点task
		//项目信息
		MfBusPact mfBusPact = mfBusPactFeign.getByAppId(appId);

		MfBusReceTransfer mfBusReceTransfer = new MfBusReceTransfer();
		mfBusReceTransfer.setTransMainId(transMainId);
		List<MfBusReceTransfer> mfBusReceTransferList = mfBusReceTransferFeign.getReceTransferList(mfBusReceTransfer);
		//求和统计
		Double receInvoiceAmt =0.00;//合同/票面总额
		Double receAmt =0.00;//应收账款总额
		Double receTransAmt =0.00;//转让总额
		for(MfBusReceTransfer mfBusReceTransferTmp:mfBusReceTransferList){
			mfBusReceTransferTmp.setAppId(appId);
			if(mfBusReceTransferTmp.getReceInvoiceAmt()==null){
				mfBusReceTransferTmp.setReceInvoiceAmt(0d);
			}
			receInvoiceAmt = receInvoiceAmt+mfBusReceTransferTmp.getReceInvoiceAmt();
			if(mfBusReceTransferTmp.getReceAmt()==null){
				mfBusReceTransferTmp.setReceAmt(0d);
			}
			receAmt = receAmt + mfBusReceTransferTmp.getReceAmt();
			if(mfBusReceTransferTmp.getReceTransAmt()==null){
				mfBusReceTransferTmp.setReceTransAmt(0d);
			}
			receTransAmt = receTransAmt+mfBusReceTransferTmp.getReceTransAmt();
		}

		mfBusReceTransfer.setAppId(appId);
		mfBusReceTransfer.setReceInvoiceAmt(receInvoiceAmt);
		mfBusReceTransfer.setReceAmt(receAmt);
		mfBusReceTransfer.setReceTransAmt(receTransAmt);

		FormData formrecetransinfo = formService.getFormData("receTransApprovalBase");
		getObjValue(formrecetransinfo, mfBusPact);
		getObjValue(formrecetransinfo, mfBusReceTransfer);

		CodeUtils codeUtils = new CodeUtils();
		// 处理审批意见类型
		Map<String, String> opinionTypeMap = new HashMap<String, String>();
		opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
		opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
		opinionTypeMap.put("nodeNo", taskAppro.getActivityName());// 当前节点编号
		List<OptionsList> opinionTypeList = codeUtils.getOpinionTypeList(activityType, taskAppro.getCouldRollback(), opinionTypeMap);
		this.changeFormProperty(formrecetransinfo, "opinionType", "optionArray", opinionTypeList);
		//获得当前审批岗位前面审批过得岗位信息
		JSONArray befNodesjsonArray = new JSONArray();
		befNodesjsonArray = wkfInterfaceFeign.getBefNodes1(taskId, User.getRegNo(request));
		request.setAttribute("befNodesjsonArray", befNodesjsonArray);
		request.setAttribute("appId", appId);
		request.setAttribute("cusNo", mfBusPact.getCusNo());
		request.setAttribute("mfBusReceTransfer", mfBusReceTransfer);
		request.setAttribute("ajaxData", ajaxData);
		request.setAttribute("transMainId", transMainId);
		request.setAttribute("nodeNo", taskAppro.getActivityName());
		model.addAttribute("formrecetransinfo", formrecetransinfo);
		model.addAttribute("query", "");
		model.addAttribute("mfBusReceTransferList", mfBusReceTransferList);
		return "/component/pact/receaccount/ReceTransWkfViewPoint";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/submitUpdateAjax")
	@ResponseBody
	public Map<String, Object> submitUpdateAjax(String ajaxData, String taskId, String appId, String transMainId, String transition, String nextUser) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusReceTransfer mfBusReceTransfer = new MfBusReceTransfer();
		dataMap = getMapByJson(ajaxData);
		FormData formrecetransinfo = formService.getFormData((String) dataMap.get("formId"));
		getFormValue(formrecetransinfo, dataMap);
		setObjValue(formrecetransinfo, mfBusReceTransfer);
		String opinionType = String.valueOf(dataMap.get("opinionType"));
		String approvalOpinion = String.valueOf(dataMap.get("approvalOpinion"));
		try {

			dataMap.put("mfBusReceTransfer", mfBusReceTransfer);
			Result res = mfBusReceTransferMainFeign.doCommit(taskId, appId,transMainId, opinionType, approvalOpinion, transition, User.getRegNo(request), nextUser, dataMap);
			if (res.isSuccess()) {
				dataMap.put("flag", "success");
				dataMap.put("msg", res.getMsg());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", res.getMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SUBMIT_CONTENT.getMessage(e.getMessage()));
			throw e;
		}
		return dataMap;


	}

}
