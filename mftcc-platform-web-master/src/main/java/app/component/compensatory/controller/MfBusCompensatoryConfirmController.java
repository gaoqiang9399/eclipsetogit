package  app.component.compensatory.controller;

import app.component.compensatory.entity.MfBusCompensatoryApply;
import app.component.compensatory.entity.MfBusCompensatoryApplyDetail;
import app.component.compensatory.entity.MfBusCompensatoryConfirm;
import app.component.compensatory.entity.MfBusCompensatoryDoc;
import app.component.compensatory.feign.MfBusCompensatoryApplyDetailFeign;
import app.component.compensatory.feign.MfBusCompensatoryApplyFeign;
import app.component.compensatory.feign.MfBusCompensatoryConfirmFeign;
import app.component.msgconf.entity.PliWarning;
import app.component.msgconf.feign.PliWarningFeign;
import app.component.nmd.entity.ParmDic;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.entity.MfBusPact;
import app.component.pact.feign.MfBusFincAppFeign;
import app.component.pact.feign.MfBusPactFeign;
import app.tech.oscache.CodeUtils;
import app.util.JsonStrHandling;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.StringUtil;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: MfBusCompensatoryConfirmAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed May 09 19:02:24 CST 2018
 **/
@Controller
@RequestMapping("/mfBusCompensatoryConfirm")
public class MfBusCompensatoryConfirmController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfBusCompensatoryConfirmFeign mfBusCompensatoryConfirmFeign;
	@Autowired
	private MfBusFincAppFeign mfBusFincAppFeign;
	@Autowired
	private MfBusPactFeign mfBusPactFeign;
	@Autowired
	private MfBusCompensatoryApplyDetailFeign mfBusCompensatoryApplyDetailFeign;
	@Autowired
	private MfBusCompensatoryApplyFeign mfBusCompensatoryApplyFeign;
	@Autowired
	private PliWarningFeign pliWarningFeign;
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/input")
	public String input(Model model,String compensatoryId,String fincId) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formcompensatoryConfirm0001 = formService.getFormData("compensatoryConfirm0001");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusFincApp mfBusFincApp = new MfBusFincApp();
		mfBusFincApp.setFincId(fincId);
		mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
		mfBusFincApp = mfBusFincAppFeign.disProcessDataForFincShow(mfBusFincApp);
		MfBusCompensatoryConfirm mfBusCompensatoryConfirm = new MfBusCompensatoryConfirm();
		mfBusCompensatoryConfirm.setCompensatoryId(compensatoryId);
		dataMap = mfBusCompensatoryConfirmFeign.getCompensatoryConfirmData(mfBusCompensatoryConfirm);
		List<MfBusCompensatoryApplyDetail> mfBusCompensatoryApplyDetailList = (List<MfBusCompensatoryApplyDetail>) dataMap.get("mfBusCompensatoryApplyDetailList");
		MfBusCompensatoryApply mfBusCompensatoryApply=(MfBusCompensatoryApply)JsonStrHandling.handlingStrToBean(dataMap.get("mfBusCompensatoryApply"),MfBusCompensatoryApply.class);
		getObjValue(formcompensatoryConfirm0001, mfBusCompensatoryApply);
		getObjValue(formcompensatoryConfirm0001, mfBusFincApp);
		List<ParmDic> parmDicList = new CodeUtils().getCacheByKeyName("COMPENSATORY_DOC");
		List<MfBusCompensatoryDoc> mfBusCompensatoryDocList = new ArrayList<MfBusCompensatoryDoc>();
		for (int i = 0; i < parmDicList.size(); i++) {
			ParmDic parmDic = parmDicList.get(i);
			MfBusCompensatoryDoc mfBusCompensatoryDoc = new MfBusCompensatoryDoc();
			mfBusCompensatoryDoc.setCompensatoryId(compensatoryId);
			mfBusCompensatoryDoc.setDocCode(parmDic.getOptCode());
			mfBusCompensatoryDoc.setDocName(parmDic.getOptName());
			mfBusCompensatoryDocList.add(mfBusCompensatoryDoc);
		}
		model.addAttribute("mfBusCompensatoryDocList", mfBusCompensatoryDocList);
		model.addAttribute("compensatoryId", compensatoryId);
		model.addAttribute("formcompensatoryConfirm0001", formcompensatoryConfirm0001);
		model.addAttribute("query", "");
		model.addAttribute("mfBusCompensatoryApply", mfBusCompensatoryApply);
		model.addAttribute("mfBusCompensatoryApplyDetailList", mfBusCompensatoryApplyDetailList);
		return "component/compensatory/MfBusCompensatoryConfirm_List";
	}
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getDetail")
	public String getDetail(Model model,String compensatoryId,String fincId) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formcompensatoryapply0003 = formService.getFormData("compensatoryapply0003");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusFincApp mfBusFincApp = new MfBusFincApp();
		mfBusFincApp.setFincId(fincId);
		mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
		mfBusFincApp = mfBusFincAppFeign.disProcessDataForFincShow(mfBusFincApp);
		MfBusCompensatoryConfirm mfBusCompensatoryConfirm = new MfBusCompensatoryConfirm();
		mfBusCompensatoryConfirm.setCompensatoryId(compensatoryId);
		dataMap = mfBusCompensatoryConfirmFeign.getCompensatoryConfirmData(mfBusCompensatoryConfirm);
		List<MfBusCompensatoryApplyDetail> mfBusCompensatoryApplyDetailList = (List<MfBusCompensatoryApplyDetail>) dataMap.get("mfBusCompensatoryApplyDetailList");
		mfBusCompensatoryConfirm = JsonStrHandling.handlingStrToBean(dataMap.get("mfBusCompensatoryConfirm"),MfBusCompensatoryConfirm.class);
		MfBusCompensatoryApply mfBusCompensatoryApply = new MfBusCompensatoryApply();
		mfBusCompensatoryApply.setCompensatoryId(compensatoryId);
		mfBusCompensatoryApply = mfBusCompensatoryApplyFeign.getById(mfBusCompensatoryApply);
		getObjValue(formcompensatoryapply0003, mfBusCompensatoryApply);
		getObjValue(formcompensatoryapply0003, mfBusFincApp);
		model.addAttribute("compensatoryId", compensatoryId);
		model.addAttribute("formcompensatoryConfirm0001", formcompensatoryapply0003);
		model.addAttribute("query", "");
		model.addAttribute("mfBusCompensatoryConfirm", mfBusCompensatoryApply);
		model.addAttribute("mfBusCompensatoryApplyDetailList", mfBusCompensatoryApplyDetailList);
		return "component/compensatory/MfBusCompensatoryApplyDetail_List";
	}
	/**
	 * 保存代偿确认信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertAjax")
	@ResponseBody
	public Map<String,Object> insertAjax(String ajaxData,String ajaxDataList,String compensatoryId)throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		try {
			Map<String, Object> map = getMapByJson(ajaxData);
			String formId = map.get("formId").toString();
			FormData formcompensatoryConfirm0001 = formService.getFormData(formId);
			getFormValue(formcompensatoryConfirm0001, getMapByJson(ajaxData));
			if(this.validateFormData(formcompensatoryConfirm0001)){
				MfBusCompensatoryConfirm mfBusCompensatoryConfirm = new MfBusCompensatoryConfirm();
				setObjValue(formcompensatoryConfirm0001, mfBusCompensatoryConfirm);
				mfBusCompensatoryConfirm.setCompensatoryDetailListStr(ajaxDataList);
				mfBusCompensatoryConfirm.setCompensatoryId(compensatoryId);
				mfBusCompensatoryConfirmFeign.insertCompensatoryConfirm(mfBusCompensatoryConfirm);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "保存失败");
			throw new Exception(e.getMessage());
		}
		
		return dataMap;
	}
	
	@RequestMapping("/getCompensatoryHistoryListAjax")
	@ResponseBody
	public Map<String,Object> getCompensatoryHistoryListAjax(String fincId,String tableId) throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String,Object>();
		String  tableHtml = "";
		MfBusCompensatoryApplyDetail mfBusCompensatoryApplyDetail = new MfBusCompensatoryApplyDetail();
		mfBusCompensatoryApplyDetail.setFincId(fincId);
		mfBusCompensatoryApplyDetail.setDetailedType("3");
		List<MfBusCompensatoryApplyDetail> mfCompensatoryHistoryList = mfBusCompensatoryApplyDetailFeign.getDetailList(mfBusCompensatoryApplyDetail);
		if(mfCompensatoryHistoryList.size()>0){
			JsonTableUtil jtu = new JsonTableUtil();
			tableHtml = jtu.getJsonStr(tableId, "tableTag", mfCompensatoryHistoryList, null ,true);
		}
		dataMap.put("htmlStr", tableHtml);
		return dataMap;
	}

	//根据id查询代偿确认信息
	@RequestMapping("/getByFincId")
	@ResponseBody
	public Map<String,Object> getByFincId(String fincId) throws Exception{
		Map<String,Object> dataMap = new HashMap<>();
		MfBusCompensatoryConfirm mfBusCompensatoryConfirm = new MfBusCompensatoryConfirm();
		try {
			mfBusCompensatoryConfirm.setFincId(fincId);
			List<MfBusCompensatoryConfirm> list = mfBusCompensatoryConfirmFeign.getByFincId(mfBusCompensatoryConfirm);
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
	 * 获得代偿提醒列表页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCompensatoryExpiresPage")
	public String getCompensatoryExpiresPage(Model model, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
			
		String cuslendDays = "0";			
		PliWarning querypliWarning = new PliWarning();
		querypliWarning.setPliWarnNo("COMPENSATORY_CONFIRM_EXPIRES");
		PliWarning pliWarning = pliWarningFeign.getById(querypliWarning);//获得代偿提醒预警
		if (null != pliWarning) {
			if ("1".equals(pliWarning.getFlag())) {
				cuslendDays = String.valueOf(pliWarning.getPliDays());
			}
		}
		model.addAttribute("cuslendDays", cuslendDays);
		model.addAttribute("query", "");
		return "/component/compensatory/MfCusCompensatoryExpires_List";
	}
	
	/**
	 * 获得代偿提醒列表数据请求
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getCompensatoryExpiresAjax")
	@ResponseBody
	public Map<String, Object> getCompensatoryExpiresAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData,Ipage ipage) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusCompensatoryConfirm mfBusCompensatoryConfirm = new MfBusCompensatoryConfirm();
		String cuslendDays = request.getParameter("cuslendDays");// 提前几天预警
		
		// 根据预警天数计算---结束日期
		String endDate = DateUtil.getDate();
		if (StringUtil.isNotEmpty(endDate)) {
			endDate = DateUtil.addByDay(Integer.parseInt(cuslendDays));
		}
		try {			
			mfBusCompensatoryConfirm.setExt1(DateUtil.getDate());//查询开始日期
			mfBusCompensatoryConfirm.setExt2(endDate);////查询结束日期
			
			mfBusCompensatoryConfirm.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfBusCompensatoryConfirm.setCriteriaList(mfBusCompensatoryConfirm, ajaxData);// 我的筛选
			mfBusCompensatoryConfirm.setCustomSorts(ajaxData);
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("mfBusCompensatoryConfirm", mfBusCompensatoryConfirm);			
			
			ipage.setParams(this.setIpageParams("paramMap", paramMap));
			// 自定义查询Bo方法
			ipage = mfBusCompensatoryConfirmFeign.getCompensatoryExpiresPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_OPERATION.getMessage("获取代偿提醒列表失败"));
		}
		return dataMap;
	}

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
		return "/component/compensatory/MfBusResoureRegister_List";
	}
	/***
	 * 列表数据查询
	 *
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/findRegisterByPageAjax")
	public Map<String, Object> findRegisterByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
													 String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusCompensatoryConfirm mfBusCompensatoryConfirm = new MfBusCompensatoryConfirm();
		try {
			mfBusCompensatoryConfirm.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfBusCompensatoryConfirm.setCustomSorts(ajaxData);// 自定义排序参数赋值
			mfBusCompensatoryConfirm.setCriteriaList(mfBusCompensatoryConfirm, ajaxData);// 我的筛选
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfBusCompensatoryConfirm", mfBusCompensatoryConfirm));
			ipage = mfBusCompensatoryConfirmFeign.findRegisterByPageAjax(ipage);
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
}
