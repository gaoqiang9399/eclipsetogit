package app.component.thirdservice.cloudmftcc.controller;

import java.util.ArrayList;
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
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import com.google.gson.Gson;

import app.component.app.entity.MfBusApply;
import app.component.appinterface.AppInterfaceFeign;
import app.component.assureinterface.AssureInterfaceFeign;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.cus.courtinfo.entity.MfCusCourtInfo;
import app.component.cus.courtinfo.feign.MfCusCourtInfoFeign;
import app.component.cus.dishonestinfo.entity.MfCusDishonestInfo;
import app.component.cus.dishonestinfo.feign.MfCusDishonestInfoFeign;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFamilyInfo;
import app.component.cus.entity.MfCusTable;
import app.component.cus.execnotice.entity.MfCusExecNotice;
import app.component.cus.execnotice.feign.MfCusExecNoticeFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusTableFeign;
import app.component.cus.judgment.entity.MfCusJudgment;
import app.component.cus.judgment.feign.MfCusJudgmentFeign;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.nmd.entity.ParmDic;
import app.component.thirdservice.cloudmftcc.entity.MfThirdMftccHighcourt;
import app.component.thirdservice.cloudmftcc.feign.MfThirdMftccHighcourtFeign;
import app.component.thirdservice.cloudmftcc.feign.MftccCloudInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.StringUtil;
import config.YmlConfig;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: MfThirdMftccHighcourtAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Jan 18 11:50:34 CST 2018
 **/
@Controller
@RequestMapping("/mfThirdMftccHighcourt")
public class MfThirdMftccHighcourtController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入MfThirdMftccHighcourtBo
	@Autowired
	private MfThirdMftccHighcourtFeign mfThirdMftccHighcourtFeign;
	@Autowired
	private CusInterfaceFeign cusInterfaceFeign;
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;
	@Autowired
	private MftccCloudInterfaceFeign mftccCloudInterfaceFeign;
	@Autowired
	private AssureInterfaceFeign assureInterfaceFeign;
	@Autowired
	private MfCusTableFeign mfCusTableFeign;
	@Autowired
	private MfCusExecNoticeFeign mfCusExecNoticeFeign;
	@Autowired
	private MfCusJudgmentFeign mfCusJudgmentFeign;
	@Autowired
	private MfCusCourtInfoFeign mfCusCourtInfoFeign;
	@Autowired
	private MfCusDishonestInfoFeign mfCusDishonestInfoFeign;
	@Autowired
	private YmlConfig ymlConfig;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	
	
	
	
	
	
	

	// 全局变量

	// 异步参数
	// 表单变量

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "MfThirdMftccHighcourt_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @param cusNo
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData, String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfThirdMftccHighcourt mfThirdMftccHighcourt = new MfThirdMftccHighcourt();
		try {
			mfThirdMftccHighcourt.setRelId(cusNo);
			mfThirdMftccHighcourt.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfThirdMftccHighcourt.setCriteriaList(mfThirdMftccHighcourt, ajaxData);// 我的筛选
			// mfThirdMftccHighcourt.setCustomSorts(ajaxData);//自定义排序
			// this.getRoleConditions(mfThirdMftccHighcourt,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfThirdMftccHighcourt",mfThirdMftccHighcourt));
			// 自定义查询Bo方法
			ipage = mfThirdMftccHighcourtFeign.findByPage(ipage, mfThirdMftccHighcourt);
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
	 * 
	 * 方法描述： 根据客户号获得法执情况
	 * 
	 * @param cusNo
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 */
	@RequestMapping(value = "/getLawEnforCountByCusNoAjax")
	@ResponseBody
	public Map<String, Object> getLawEnforCountByCusNoAjax(String ajaxData, String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfThirdMftccHighcourt mfThirdMftccHighcourt = new MfThirdMftccHighcourt();
		try {
			int count = mfThirdMftccHighcourtFeign.getLawEnforCountByCusNo(cusNo);
			if (count > 0) {
				dataMap.put("flag", "success");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", "根据客户号获得最新的征信报告结果失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "根据客户号获得最新的征信报告结果失败");
			throw new Exception(e.getMessage());
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
			FormData formlawenforcementDetail = formService.getFormData("lawenforcementDetail");
			getFormValue(formlawenforcementDetail, getMapByJson(ajaxData));
			if (this.validateFormData(formlawenforcementDetail)) {
				MfThirdMftccHighcourt mfThirdMftccHighcourt = new MfThirdMftccHighcourt();
				setObjValue(formlawenforcementDetail, mfThirdMftccHighcourt);
				mfThirdMftccHighcourtFeign.insert(mfThirdMftccHighcourt);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
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
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formlawenforcementDetail = formService.getFormData("lawenforcementDetail");
		getFormValue(formlawenforcementDetail, getMapByJson(ajaxData));
		MfThirdMftccHighcourt mfThirdMftccHighcourtJsp = new MfThirdMftccHighcourt();
		setObjValue(formlawenforcementDetail, mfThirdMftccHighcourtJsp);
		MfThirdMftccHighcourt mfThirdMftccHighcourt = mfThirdMftccHighcourtFeign.getById(mfThirdMftccHighcourtJsp);
		if (mfThirdMftccHighcourt != null) {
			try {
				mfThirdMftccHighcourt = (MfThirdMftccHighcourt) EntityUtil.reflectionSetVal(mfThirdMftccHighcourt,
						mfThirdMftccHighcourtJsp, getMapByJson(ajaxData));
				mfThirdMftccHighcourtFeign.update(mfThirdMftccHighcourt);
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
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfThirdMftccHighcourt mfThirdMftccHighcourt = new MfThirdMftccHighcourt();
		try {
			FormData formlawenforcementDetail = formService.getFormData("lawenforcementDetail");
			getFormValue(formlawenforcementDetail, getMapByJson(ajaxData));
			if (this.validateFormData(formlawenforcementDetail)) {
				setObjValue(formlawenforcementDetail, mfThirdMftccHighcourt);
				mfThirdMftccHighcourtFeign.update(mfThirdMftccHighcourt);
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
	 * @param busId
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData, String busId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formlawenforcementDetail = formService.getFormData("lawenforcementDetail");
		MfThirdMftccHighcourt mfThirdMftccHighcourt = new MfThirdMftccHighcourt();
		mfThirdMftccHighcourt.setBusId(busId);
		mfThirdMftccHighcourt = mfThirdMftccHighcourtFeign.getById(mfThirdMftccHighcourt);
		getObjValue(formlawenforcementDetail, mfThirdMftccHighcourt, formData);
		if (mfThirdMftccHighcourt != null) {
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
	 * @param busId
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String busId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfThirdMftccHighcourt mfThirdMftccHighcourt = new MfThirdMftccHighcourt();
		mfThirdMftccHighcourt.setBusId(busId);
		try {
			mfThirdMftccHighcourtFeign.delete(mfThirdMftccHighcourt);
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
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formlawenforcementDetail = formService.getFormData("lawenforcementDetail");
		model.addAttribute("formlawenforcementDetail", formlawenforcementDetail);
		model.addAttribute("query", "");
		return "MfThirdMftccHighcourt_Insert";
	}

	/**
	 * 查询
	 * 
	 * @param busId
	 * @param cusNo
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData, String busId, String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfThirdMftccHighcourt mfThirdMftccHighcourt = new MfThirdMftccHighcourt();
		mfThirdMftccHighcourt.setBusId(busId);
		mfThirdMftccHighcourt.setRelId(cusNo);
		mfThirdMftccHighcourt = mfThirdMftccHighcourtFeign.getById(mfThirdMftccHighcourt);
		model.addAttribute("mfThirdMftccHighcourt", mfThirdMftccHighcourt);
		model.addAttribute("busName", mfThirdMftccHighcourt.getBusName());
		model.addAttribute("busNumber", mfThirdMftccHighcourt.getBusNumber());
		model.addAttribute("result", mfThirdMftccHighcourt.getResult());
		model.addAttribute("query", "");
		return "/component/thirdservice/cloudmftcc/MfThirdMftccHighcourt_Detail";
	}

	/**
	 * 
	 * 方法描述： 获得法执情况客户信息
	 * 
	 * @param cusNo
	 * @param relType
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 */
	@RequestMapping(value = "/getLawQueryCusInfoAjax")
	@ResponseBody
	public Map<String, Object> getLawQueryCusInfoAjax(String ajaxData, String cusNo, String relType) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = mfThirdMftccHighcourtFeign.getLawQueryCusInfo(cusNo, relType);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	@RequestMapping(value = "/lawEnforQueryAjax")
	@ResponseBody
	public Map<String, Object> lawEnforQueryAjax(String ajaxData, String isRequest,String thirdFlag) throws Exception {
		FormService formService = new FormService();
		Gson gson=new Gson();
		ActionContext.initialize(request, response);
		// Map<String, Object> dataMap = new HashMap<String, Object>();
		HashMap<String, String> paramMap = new HashMap<String, String>();
		Map<String, Object> dataMap = getMapByJson(ajaxData);
		String cusNo = (String) dataMap.get("cusNo");
		try {
			paramMap.put("cusNo", cusNo);
			paramMap.put("realtionType", (String) dataMap.get("busType"));
			paramMap.put("idNum", (String) dataMap.get("busNumber"));
			paramMap.put("relId", (String) dataMap.get("relId"));
			String relId = (String) dataMap.get("relId");
			String cusName ="";
			if ("2".equals((String) dataMap.get("busType")) || "1".equals((String) dataMap.get("busType"))) {
				MfCusCustomer mfCusCustomer = new MfCusCustomer();
				mfCusCustomer.setCusNo(relId);
				mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
				cusName = mfCusCustomer.getCusName();
				paramMap.put("cusName", cusName);

			} else if ("3".equals((String) dataMap.get("busType"))) {
				MfCusFamilyInfo mfCusFamilyInfo = new MfCusFamilyInfo();
				mfCusFamilyInfo.setId(relId);
				mfCusFamilyInfo = cusInterfaceFeign.getCusFamilyInfo(mfCusFamilyInfo);
				cusName = mfCusFamilyInfo.getRelName();
				paramMap.put("cusName", cusName);

			}else {
			} /*else {
				if(cusName==null ||"".equals(cusName)){
					paramMap.put("busName", (String) dataMap.get("busName"));
				}else{
					
					paramMap.put("cusName", (String) dataMap.get("cusName"));
				}

			}*/
			paramMap.put("isRequest", isRequest);
			paramMap.put("thirdFlag", thirdFlag);
			
			//String highcourtP = mftccCloudInterfaceFeign.highcourtP(paramMap);
			MfCusCustomer mfCusCustomer = new MfCusCustomer();
			mfCusCustomer.setCusNo(cusNo);
			mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
			Map<String,String> lawEnforcement = null;
			if(BizPubParm.CUS_BASE_TYPE_CORP.equals(mfCusCustomer.getCusBaseType())){
				lawEnforcement = mftccCloudInterfaceFeign.corpLawEnforcement(paramMap);
			}else{
				
				lawEnforcement = mftccCloudInterfaceFeign.personLawEnforcement(paramMap);
			}
			//Map<String,String> map=gson.fromJson(lawEnforcement, Map.class); 

			JSONObject js = JSONObject.fromObject(lawEnforcement.get("resultString"));
			if (js.containsKey("errorcode") && "11111".equals((String) js.get("errorcode"))) {
				dataMap.put("flag", "success");
				String id = String.valueOf(lawEnforcement.get("id"));
				dataMap.put("id", id);
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", js.get("errormessage"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "法执情况查询失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	@RequestMapping(value = "/getResultById")
	public String getResultById(Model model, String ajaxData, String cusNo, String appId,String thirdFlag) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formlawenforcementBase = formService.getFormData("lawenforcementBase");
		getFormValue(formlawenforcementBase);
		MfThirdMftccHighcourt mfThirdMftccHighcourt = new MfThirdMftccHighcourt();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
		String cusName = mfCusCustomer.getCusName();
		if (StringUtil.isNotEmpty(appId)) {
			MfBusApply mfBusApply = new MfBusApply();
			mfBusApply = appInterfaceFeign.getMfBusApplyByAppId(appId);
			mfBusApply.setFincAmt(MathExtend.moneyStr(mfBusApply.getAppAmt() / 10000));
			mfBusApply = appInterfaceFeign.processDataForApply(mfBusApply);// 处理利率格式
			getObjValue(formlawenforcementBase, mfBusApply);
			// 处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
			Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
			String rateUnit = rateTypeMap.get(mfBusApply.getRateType()).getRemark();
			this.changeFormProperty(formlawenforcementBase, "fincRate", "unit", rateUnit);
			// 处理期限的展示单位。
			Map<String, ParmDic> termTypeMap = new CodeUtils().getMapObjByKeyName("TERM_TYPE");
			String termUnit = termTypeMap.get(mfBusApply.getTermType()).getRemark();
			this.changeFormProperty(formlawenforcementBase, "term", "unit", termUnit);
			dataMap.put("termUnit", termUnit);
			dataMap.put("rateUnit", rateUnit);
			model.addAttribute("dataMap", dataMap);
			model.addAttribute("mfBusApply", mfBusApply);
		}
		JSONObject json = new JSONObject();
		JSONArray jsonArray = mfThirdMftccHighcourtFeign.getLawCusJSONArray(cusNo, appId);
		json.put("lawCusArr", jsonArray);
		ajaxData = json.toString();
		model.addAttribute("formlawenforcementBase", formlawenforcementBase);
		model.addAttribute("mfCusCustomer", mfCusCustomer);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("appId", appId);
		model.addAttribute("thirdFlag", thirdFlag);
		model.addAttribute("lawEnfo", ymlConfig.getCloud().get("lawEnfo"));
		model.addAttribute("query", "");
		return "component/thirdservice/cloudmftcc/LawEnforContent";
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
		FormData formlawenforcementDetail = formService.getFormData("lawenforcementDetail");
		getFormValue(formlawenforcementDetail);
		boolean validateFlag = this.validateFormData(formlawenforcementDetail);
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
		FormData formlawenforcementDetail = formService.getFormData("lawenforcementDetail");
		getFormValue(formlawenforcementDetail);
		boolean validateFlag = this.validateFormData(formlawenforcementDetail);
	}
	/**
	 * 
	 * 方法描述： 获取动态表单html
	 * @param cusNo
	 * @param relNo
	 * @return
	 * @throws Exception
	 * List<MfCusTable>
	 * @author ldy
	 * @param query 
	 * @date 2018-6-19
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getLawEnforcement")
	@ResponseBody
	public Map<String, Object> getLawEnforcement(String cusNo,String relNo, String query,Ipage ipage,Model model) throws Exception{
		if(StringUtil.isEmpty(relNo)){
			relNo = cusNo;
		}
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		List<MfCusTable> cusTableList = new ArrayList<MfCusTable>();
		List<MfCusTable> tableList = new ArrayList<MfCusTable>();
		// 查询已经录入信息的表单
		MfCusTable mfCusTable = new MfCusTable();
		mfCusTable.setCusNo(relNo);
		cusTableList = mfCusTableFeign.getList(mfCusTable);
		
		JsonFormUtil jsonFormUtil = new JsonFormUtil();
		JsonTableUtil jtu = new JsonTableUtil();
		for (int i = 0; i < cusTableList.size(); i++) {
			if ("0".equals(cusTableList.get(i).getDataFullFlag())) {
				continue;
			}
			String action = cusTableList.get(i).getAction();
			String htmlStr = "";
			FormData formcommon;
			 if ("MfCusExecNoticeAction".equals(action)) {//执行公告
				
				MfCusExecNotice mfCusExecNotice = new MfCusExecNotice();
				mfCusExecNotice.setCusNo(cusNo);
				mfCusExecNotice.setRelNo(relNo);
				ipage.setParams(this.setIpageParams("mfCusExecNotice",mfCusExecNotice));
				String tableFormId="tablecusExecNoticeListBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId,"tableTag", mfCusExecNoticeFeign.getAllList(mfCusExecNotice), null,true);
			}
			else if ("MfCusJudgmentAction".equals(action)) {//裁判文书
				
				MfCusJudgment mfCusJudgment = new MfCusJudgment();
				mfCusJudgment.setCusNo(cusNo);
				mfCusJudgment.setRelNo(relNo);
				ipage.setParams(this.setIpageParams("mfCusJudgment",mfCusJudgment));
				String tableFormId="tablecusJudgmentListBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId,"tableTag", mfCusJudgmentFeign.getAllList(mfCusJudgment), null,true);
				
			}else if ("MfCusCourtInfoAction".equals(action)) {//法院信息
				
				MfCusCourtInfo mfCusCourtInfo = new MfCusCourtInfo();
				mfCusCourtInfo.setCusNo(cusNo);
				ipage.setParams(this.setIpageParams("mfCusCourtInfo",mfCusCourtInfo));
				String tableFormId="tablecusCourtInfoListBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId,"tableTag", mfCusCourtInfoFeign.getAllList(mfCusCourtInfo), null,true);
				
			}else if ("MfCusDishonestInfoAction".equals(action)) {//失信公告

				MfCusDishonestInfo mfCusDishonestInfo = new MfCusDishonestInfo();
				mfCusDishonestInfo.setCusNo(cusNo);
				ipage.setParams(this.setIpageParams("mfCusDishonestInfo",mfCusDishonestInfo));
				String tableFormId="tablecusDishonestInfoListBase";
				if(StringUtil.isNotEmpty(cusTableList.get(i).getListModelDef())){
					tableFormId="table"+cusTableList.get(i).getListModelDef();
				}
				htmlStr = jtu.getJsonStr(tableFormId,"tableTag", mfCusDishonestInfoFeign.getAllList(mfCusDishonestInfo), null,true);
			}else {
			 }

			
				 
				 cusTableList.get(i).setHtmlStr(htmlStr);
				 if(htmlStr!=null && !"".equals(htmlStr)){
					 
					 tableList.add(cusTableList.get(i));
				 }
			
		}
		dataMap.put("tableList", tableList);
		JSONObject jb = JSONObject.fromObject(dataMap);
		dataMap = jb;
		model.addAttribute("dataMap", dataMap);
			return dataMap;
	}

}
