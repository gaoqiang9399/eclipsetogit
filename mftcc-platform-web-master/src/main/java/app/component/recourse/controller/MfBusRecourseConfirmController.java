package  app.component.recourse.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.compensatory.entity.MfBusCompensatoryApply;
import app.component.compensatory.entity.MfBusCompensatoryApplyDetail;
import app.component.compensatory.entity.MfBusCompensatoryDoc;
import app.component.compensatory.feign.MfBusCompensatoryApplyDetailFeign;
import app.component.compensatory.feign.MfBusCompensatoryApplyFeign;
import app.component.msgconf.entity.PliWarning;
import app.component.msgconf.feign.PliWarningFeign;
import app.component.nmd.entity.ParmDic;
import app.component.pact.entity.MfBusPact;
import app.component.pact.feign.MfBusPactFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.StringUtil;

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

import app.component.pact.entity.MfBusFincApp;
import app.component.pact.feign.MfBusFincAppFeign;
import app.component.recourse.entity.MfBusRecourseApply;
import app.component.recourse.entity.MfBusRecourseConfirm;
import app.component.recourse.feign.MfBusRecourseApplyFeign;
import app.component.recourse.feign.MfBusRecourseConfirmFeign;
import cn.mftcc.common.MessageEnum;

/**
 * Title: MfBusRecourseConfirmAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed May 16 19:43:59 CST 2018
 **/
@Controller
@RequestMapping("/mfBusRecourseConfirm")
public class MfBusRecourseConfirmController extends BaseFormBean{
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfBusFincAppFeign mfBusFincAppFeign;
	@Autowired
	private MfBusRecourseConfirmFeign mfBusRecourseConfirmFeign;
	@Autowired
	private MfBusRecourseApplyFeign mfBusRecourseApplyFeign;
	@Autowired
	private MfBusCompensatoryApplyFeign mfBusCompensatoryApplyFeign;
	@Autowired
	private MfBusPactFeign mfBusPactFeign;
	@Autowired
	private PliWarningFeign pliWarningFeign;
	@Autowired
    private MfBusCompensatoryApplyDetailFeign mfBusCompensatoryApplyDetailFeign;
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/input")
	public String input(Model model,String recourseId,String fincId) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		MfBusFincApp mfBusFincApp = new MfBusFincApp();
		MfBusRecourseConfirm mfBusRecourseConfirm = new MfBusRecourseConfirm();
		FormData formrecourseConfirm0001 = formService.getFormData("recourseConfirm0001");
		mfBusFincApp.setFincId(fincId);
		mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
		mfBusFincApp = mfBusFincAppFeign.disProcessDataForFincShow(mfBusFincApp);
		MfBusRecourseApply mfBusRecourseApply = new MfBusRecourseApply();
		mfBusRecourseApply.setRecourseId(recourseId);
		mfBusRecourseApply = mfBusRecourseApplyFeign.getById(mfBusRecourseApply);
		MfBusCompensatoryApply mfBusCompensatoryApply = new MfBusCompensatoryApply();
		mfBusCompensatoryApply.setCompensatoryId(mfBusRecourseApply.getCompensatoryId());
		mfBusCompensatoryApply = mfBusCompensatoryApplyFeign.getById(mfBusCompensatoryApply);
		getObjValue(formrecourseConfirm0001, mfBusCompensatoryApply);
		getObjValue(formrecourseConfirm0001, mfBusRecourseApply);
		getObjValue(formrecourseConfirm0001, mfBusFincApp);
		model.addAttribute("formrecourseConfirm0001", formrecourseConfirm0001);
		model.addAttribute("query", "");
		model.addAttribute("mfBusRecourseConfirm", mfBusRecourseApply);
		return "component/recourse/MfBusRecourseConfirm_Detail";
	}
	
	/**
	 * 追偿信息打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/inputInfo")
	public String inputInfo(Model model,String recourseId,String fincId) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		MfBusFincApp mfBusFincApp = new MfBusFincApp();
		MfBusRecourseConfirm mfBusRecourseConfirm = new MfBusRecourseConfirm();
		FormData formrecourseConfirm0001 = formService.getFormData("recourseConfirm0001");
		mfBusFincApp.setFincId(fincId);
		mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
		MfBusRecourseApply mfBusRecourseApply = new MfBusRecourseApply();
		mfBusRecourseApply.setRecourseId(recourseId);
		mfBusRecourseApply = mfBusRecourseApplyFeign.getById(mfBusRecourseApply);
		MfBusCompensatoryApply mfBusCompensatoryApply = new MfBusCompensatoryApply();
		mfBusCompensatoryApply.setCompensatoryId(mfBusRecourseApply.getCompensatoryId());
		mfBusCompensatoryApply = mfBusCompensatoryApplyFeign.getById(mfBusCompensatoryApply);
		MfBusCompensatoryDoc mfBusCompensatoryDoc = new MfBusCompensatoryDoc();
		mfBusCompensatoryDoc.setCompensatoryId(mfBusRecourseApply.getCompensatoryId());
		List<MfBusCompensatoryDoc> mfBusCompensatoryDocList = mfBusCompensatoryApplyFeign.getCompensatoryDocList(mfBusCompensatoryDoc);
		getObjValue(formrecourseConfirm0001, mfBusCompensatoryApply);
		getObjValue(formrecourseConfirm0001, mfBusRecourseApply);
		getObjValue(formrecourseConfirm0001, mfBusFincApp);
		model.addAttribute("formrecourseConfirm0001", formrecourseConfirm0001);
		model.addAttribute("mfBusCompensatoryDocList", mfBusCompensatoryDocList);
		model.addAttribute("recourseId", mfBusRecourseApply.getRecourseId());
		model.addAttribute("query", "");
		model.addAttribute("mfBusRecourseConfirm", mfBusRecourseConfirm);
		return "component/recourse/MfBusRecourseConfirmInfo";
	}


	/**
	 * 保存追偿确认信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertAjax")
	@ResponseBody
	public Map<String,Object> insertAjax(String ajaxData)throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		try {
			Map<String, Object> map = getMapByJson(ajaxData);
			String formId = map.get("formId").toString();
			FormData formrecourseConfirm0001 = formService.getFormData(formId);
			getFormValue(formrecourseConfirm0001, getMapByJson(ajaxData));
			if(this.validateFormData(formrecourseConfirm0001)){
				MfBusRecourseConfirm mfBusRecourseConfirm = new MfBusRecourseConfirm();
				setObjValue(formrecourseConfirm0001, mfBusRecourseConfirm);
				mfBusRecourseConfirmFeign.insert(mfBusRecourseConfirm);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
				return dataMap;
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "保存失败");
			throw new Exception(e.getMessage());
		}
		
		return dataMap;
	}
	@ResponseBody
	@RequestMapping("/getRecourseHistoryListAjax")
	public Map<String,Object> getRecourseHistoryListAjax(String fincId,String tableId) throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String,Object>();
		String  tableHtml = "";
		MfBusRecourseConfirm mfBusRecourseConfirm = new MfBusRecourseConfirm();
		mfBusRecourseConfirm.setFincId(fincId);
		List<MfBusRecourseConfirm> mfRecourseHistoryList = mfBusRecourseConfirmFeign.getByFincId(mfBusRecourseConfirm);
		if(mfRecourseHistoryList.size()>0){
			JsonTableUtil jtu = new JsonTableUtil();
			tableHtml = jtu.getJsonStr(tableId, "tableTag", mfRecourseHistoryList, null ,true);
		}
		dataMap.put("htmlStr", tableHtml);
		return dataMap;
	}

	//判断当前追偿是否确认
	@RequestMapping("/getByRecourseId")
	@ResponseBody
	public Map<String,Object> getByRecourseId(String fincId) throws Exception{
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<>();
		MfBusRecourseConfirm mfBusRecourseConfirm = new MfBusRecourseConfirm();
		try {
			mfBusRecourseConfirm.setFincId(fincId);
			List<MfBusRecourseConfirm> list = mfBusRecourseConfirmFeign.getByRecourseId(mfBusRecourseConfirm);
			if (list.size() > 0) {
				dataMap.put("flag", "success");
			}else {
				dataMap.put("flag", "error");
			}
		}catch (Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}
	
	/**
	 * 获得追偿提醒列表页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getRecourseExpiresPage")
	public String getRecourseExpiresPage(Model model, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
			
		String cuslendDays = "0";			
		PliWarning querypliWarning = new PliWarning();
		querypliWarning.setPliWarnNo("RECOURSE_CONFIRM_EXPIRES");
		PliWarning pliWarning = pliWarningFeign.getById(querypliWarning);//获得保险到期预警
		if (null != pliWarning) {
			if ("1".equals(pliWarning.getFlag())) {
				cuslendDays = String.valueOf(pliWarning.getPliDays());
			}
		}
		model.addAttribute("cuslendDays", cuslendDays);
		model.addAttribute("query", "");
		return "/component/recourse/MfCusRecourseExpires_List";
	}
	
	/**
	 * 获得追偿提醒列表数据请求
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getRecourseExpiresAjax")
	@ResponseBody
	public Map<String, Object> getRecourseExpiresAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData,Ipage ipage) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusRecourseConfirm mfBusRecourseConfirm = new MfBusRecourseConfirm();
		String cuslendDays = request.getParameter("cuslendDays");// 提前几天预警
		
		// 根据预警天数计算---结束日期
		String endDate = DateUtil.getDate();
		if (StringUtil.isNotEmpty(endDate)) {
			endDate = DateUtil.addByDay(Integer.parseInt(cuslendDays));
		}
		try {			
			mfBusRecourseConfirm.setExt1(DateUtil.getDate());//查询开始日期
			mfBusRecourseConfirm.setExt2(endDate);////查询结束日期
			
			mfBusRecourseConfirm.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfBusRecourseConfirm.setCriteriaList(mfBusRecourseConfirm, ajaxData);// 我的筛选
			mfBusRecourseConfirm.setCustomSorts(ajaxData);
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("mfBusRecourseConfirm", mfBusRecourseConfirm);			
			
			ipage.setParams(this.setIpageParams("paramMap", paramMap));
			// 自定义查询Bo方法
			ipage = mfBusRecourseConfirmFeign.getRecourseExpiresPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("获取追偿提醒列表失败"));
		}
		return dataMap;
	}
}
