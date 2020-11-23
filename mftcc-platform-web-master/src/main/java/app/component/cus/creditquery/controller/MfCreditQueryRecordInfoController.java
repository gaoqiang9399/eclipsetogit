package  app.component.cus.creditquery.controller;

import app.base.User;
import app.component.app.entity.MfBusApply;
import app.component.appinterface.AppInterfaceFeign;
import app.component.common.BizPubParm;
import app.component.common.CoreEnum;
import app.component.common.EntityUtil;
import app.component.cus.creditquery.entity.MfCreditQueryRecordInfo;
import app.component.cus.creditquery.feign.MfCreditQueryRecordInfoFactorFeign;
import app.component.cus.creditquery.feign.MfCreditQueryRecordInfoFeign;
import app.component.cus.entity.MfCusCustomer;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.nmd.entity.ParmDic;
import app.component.thirdservice.util.PdfUtil;
import app.component.thirdservice.util.ServletFreeMarker;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.PropertiesUtil;
import cn.mftcc.util.StringUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: MfCreditQueryRecordInfoAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Thu Nov 30 09:15:13 CST 2017
 **/
@Controller
@RequestMapping("/mfCreditQueryRecordInfo")
public class MfCreditQueryRecordInfoController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfCreditQueryRecordInfoBo
	@Autowired
	private MfCreditQueryRecordInfoFeign mfCreditQueryRecordInfoFeign;
	@Autowired
	private MfCreditQueryRecordInfoFactorFeign mfCreditQueryRecordInfoFactorFeign;
	//全局变量
	//异步参数
	//表单变量
	
	@Autowired
	private CusInterfaceFeign cusInterfaceFeign;
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;
	
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request,
				response);
		model.addAttribute("query", "");
		return "/component/cus/creditquery/MfCreditQueryRecordInfo_List";
	}
	/***
	 * 列表数据查询
	 * @param cusNo 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData, String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCreditQueryRecordInfo mfCreditQueryRecordInfo = new MfCreditQueryRecordInfo();
		try {
			mfCreditQueryRecordInfo.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCreditQueryRecordInfo.setCriteriaList(mfCreditQueryRecordInfo, ajaxData);//我的筛选
			mfCreditQueryRecordInfo.setCusNo(cusNo);
			mfCreditQueryRecordInfo.setQueryState(BizPubParm.YES_NO_Y);//征信查询成功
			//mfCreditQueryRecordInfo.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfCreditQueryRecordInfo,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfCreditQueryRecordInfo",mfCreditQueryRecordInfo));
			//自定义查询Bo方法
			ipage = mfCreditQueryRecordInfoFactorFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}
	
	/**
	 * AJAX新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		FormData 	formcreditquery0002 = formService.getFormData("creditquery0002");
			getFormValue(formcreditquery0002, getMapByJson(ajaxData));
			if(this.validateFormData(formcreditquery0002)){
		MfCreditQueryRecordInfo mfCreditQueryRecordInfo = new MfCreditQueryRecordInfo();
				setObjValue(formcreditquery0002, mfCreditQueryRecordInfo);
				mfCreditQueryRecordInfoFeign.insert(mfCreditQueryRecordInfo);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}
	/**
	 * ajax 异步 单个字段或多个字段更新
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcreditquery0002 = formService.getFormData("creditquery0002");
		getFormValue(formcreditquery0002, getMapByJson(ajaxData));
		MfCreditQueryRecordInfo mfCreditQueryRecordInfoJsp = new MfCreditQueryRecordInfo();
		setObjValue(formcreditquery0002, mfCreditQueryRecordInfoJsp);
		MfCreditQueryRecordInfo mfCreditQueryRecordInfo = mfCreditQueryRecordInfoFeign.getById(mfCreditQueryRecordInfoJsp);
		if(mfCreditQueryRecordInfo!=null){
			try{
				mfCreditQueryRecordInfo = (MfCreditQueryRecordInfo)EntityUtil.reflectionSetVal(mfCreditQueryRecordInfo, mfCreditQueryRecordInfoJsp, getMapByJson(ajaxData));
				mfCreditQueryRecordInfoFeign.update(mfCreditQueryRecordInfo);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			}catch(Exception e){
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw new Exception(e.getMessage());
			}
		}else{
			dataMap.put("flag", "error");
			dataMap.put("msg", "编号不存在,保存失败");
		}
		return dataMap;
	}
	/**
	 * AJAX更新保存
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		FormData 	formcreditquery0002 = formService.getFormData("creditquery0002");
			getFormValue(formcreditquery0002, getMapByJson(ajaxData));
			if(this.validateFormData(formcreditquery0002)){
				MfCreditQueryRecordInfo mfCreditQueryRecordInfo = new MfCreditQueryRecordInfo();
				setObjValue(formcreditquery0002, mfCreditQueryRecordInfo);
				mfCreditQueryRecordInfoFeign.update(mfCreditQueryRecordInfo);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}
	
	/**
	 * AJAX获取查看
	 * @param queryId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData, String queryId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formcreditquery0002 = formService.getFormData("creditquery0002");
		MfCreditQueryRecordInfo mfCreditQueryRecordInfo = new MfCreditQueryRecordInfo();
		mfCreditQueryRecordInfo.setQueryId(queryId);
		mfCreditQueryRecordInfo = mfCreditQueryRecordInfoFeign.getById(mfCreditQueryRecordInfo);
		getObjValue(formcreditquery0002, mfCreditQueryRecordInfo,formData);
		if(mfCreditQueryRecordInfo!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @param queryId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String queryId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCreditQueryRecordInfo mfCreditQueryRecordInfo = new MfCreditQueryRecordInfo();
		mfCreditQueryRecordInfo.setQueryId(queryId);
		try {
			mfCreditQueryRecordInfoFeign.delete(mfCreditQueryRecordInfo);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}
	/**
	 * 新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData formcreditquery0002 = formService.getFormData("creditquery0002");
		model.addAttribute("formcreditquery0002", formcreditquery0002);
		model.addAttribute("query", "");
		return "/component/cus/creditquery/MfCreditQueryRecordInfo_Insert";
	}
	/**
	 * 查询
	 * @param queryId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData, String queryId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formcreditquery0001 = formService.getFormData("creditquery0001");
		 getFormValue(formcreditquery0001);
		MfCreditQueryRecordInfo  mfCreditQueryRecordInfo = new MfCreditQueryRecordInfo();
		mfCreditQueryRecordInfo.setQueryId(queryId);
		 mfCreditQueryRecordInfo = mfCreditQueryRecordInfoFeign.getById(mfCreditQueryRecordInfo);
		 getObjValue(formcreditquery0001, mfCreditQueryRecordInfo);
		model.addAttribute("formcreditquery0001", formcreditquery0001);
		model.addAttribute("query", "");
		return "/component/cus/creditquery/MfCreditQueryRecordInfo_Detail";
	}
	/**
	 * 
	 * 方法描述： 跳转征信结果详情页面
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @param cusNo 
	 * @param appId 
	 * @param creditQueryFlag 
	 * @param busEntrance 
	 * @param queryId 
	 * @date 2017-11-30 上午11:29:29
	 */
	@RequestMapping(value = "/getCreditContentById")
	public String getCreditContentById(Model model, String cusNo, String appId, String creditQueryFlag, String busEntrance, String queryId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formcreditquery0001 = formService.getFormData("creditquery0001");
		getFormValue(formcreditquery0001);
		MfCreditQueryRecordInfo mfCreditQueryRecordInfo = new MfCreditQueryRecordInfo();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer=cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
		if(StringUtil.isNotEmpty(appId)){
			MfBusApply mfBusApply = new MfBusApply();
			mfBusApply=appInterfaceFeign.getMfBusApplyByAppId(appId);
			mfBusApply.setFincAmt(MathExtend.moneyStr(mfBusApply.getAppAmt()/10000));
			mfBusApply = appInterfaceFeign.processDataForApply(mfBusApply);//处理利率格式
			getObjValue(formcreditquery0001, mfBusApply);
			//处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
			Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
			String rateUnit= rateTypeMap.get(mfBusApply.getRateType()).getRemark();
			this.changeFormProperty(formcreditquery0001, "fincRate", "unit", rateUnit);
			// 处理期限的展示单位。
			Map<String, ParmDic> termTypeMap = new CodeUtils().getMapObjByKeyName("TERM_TYPE");
			String termUnit= termTypeMap.get(mfBusApply.getTermType()).getRemark();
			this.changeFormProperty(formcreditquery0001, "term", "unit", termUnit);
			dataMap.put("termUnit", termUnit);
			dataMap.put("rateUnit", rateUnit);
			model.addAttribute("mfBusApply", mfBusApply);
		}
		mfCreditQueryRecordInfo.setCusName(mfCusCustomer.getCusName());
		mfCreditQueryRecordInfo.setOpNo(User.getRegNo(request));
		mfCreditQueryRecordInfo.setOpName(User.getRegName(request));
		mfCreditQueryRecordInfo.setBrNo(User.getOrgNo(request));
		mfCreditQueryRecordInfo.setBrName(User.getOrgName(request));
		mfCreditQueryRecordInfo.setQueryDate(DateUtil.getDate());
		getObjValue(formcreditquery0001, mfCreditQueryRecordInfo);
		JSONObject json = new JSONObject();
		String docSplitNoStr=PropertiesUtil.getCreditQueryProperty("ID_NUM_DOC_SPLIT_NO")+";"+
				PropertiesUtil.getCreditQueryProperty("SPOUSE_ID_NUM_DOC_SPLIT_NO")+";"+
				PropertiesUtil.getCreditQueryProperty("ASSURER_ID_NUM_DOC_SPLIT_NO")+";"+
				PropertiesUtil.getCreditQueryProperty("LOAN_AUTHOR_DOC_SPLIT_NO")+";"+
				PropertiesUtil.getCreditQueryProperty("SPOUSE_AUTHOR_DOC_SPLIT_NO")+";"+
				PropertiesUtil.getCreditQueryProperty("ASSURER_AUTHOR_DOC_SPLIT_NO");
		json.put("docSplitNoArr", docSplitNoStr);
		JSONArray jsonArray = mfCreditQueryRecordInfoFeign.getCreditCusJSONArray(cusNo, appId);;
		json.put("creditCusArr", jsonArray);
		String ajaxData = json.toString();
		model.addAttribute("formcreditquery0001", formcreditquery0001);
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("mfCusCustomer", mfCusCustomer);
		model.addAttribute("creditQueryFlag", creditQueryFlag);
		model.addAttribute("busEntrance", busEntrance);
		model.addAttribute("queryId", queryId);
		model.addAttribute("appId", appId);
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("query", "");
		return "/component/cus/creditquery/CreditContent";
	}
	/**
	 * 
	 * 方法描述： 跳转客户征信查询历史页面
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @param cusNo 
	 * @date 2017-12-27 上午11:48:59
	 */
	@RequestMapping(value = "/getCreditHisByCusNo")
	public String getCreditHisByCusNo(Model model, String ajaxData, String cusNo) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		MfCreditQueryRecordInfo mfCreditQueryRecordInfo = new MfCreditQueryRecordInfo();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		mfCreditQueryRecordInfo.setCusNo(cusNo);
		mfCreditQueryRecordInfo.setQueryState(BizPubParm.YES_NO_Y);
		List<MfCreditQueryRecordInfo> mfCreditQueryRecordInfoList = mfCreditQueryRecordInfoFactorFeign.getCreditQueryRecord(mfCreditQueryRecordInfo);
		model.addAttribute("mfCreditQueryRecordInfoList", mfCreditQueryRecordInfoList);
		model.addAttribute("query", "");
		model.addAttribute("cusNo", cusNo);
		return "/component/cus/creditquery/CreditResultHis";
	}

	/**
	 * 
	 * 方法描述： 跳转征信查询结果详情页面
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2017-12-28 下午6:37:25
	 */
	@RequestMapping(value = "/showCreditResultContentByQueryId")
	public String showCreditResultContentByQueryId(Model model, String ajaxData, String queryId) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		model.addAttribute("queryId", queryId);
		return "/component/cus/creditquery/CreditResultContent";
	}

	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model) throws Exception{
		FormService formService = new FormService();
		 ActionContext.initialize(request, response);
		FormData  formcreditquery0002 = formService.getFormData("creditquery0002");
		 getFormValue(formcreditquery0002);
		 boolean validateFlag = this.validateFormData(formcreditquery0002);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(Model model) throws Exception{
		FormService formService = new FormService();
		 ActionContext.initialize(request, response);
		FormData  formcreditquery0002 = formService.getFormData("creditquery0002");
		 getFormValue(formcreditquery0002);
		 boolean validateFlag = this.validateFormData(formcreditquery0002);
	}

	/**
	 * 方法描述： 征信查询
	 * @param cusNo
	 * @param appId
	 * @return
	 * @throws Exception String
	 * @author 沈浩兵
	 * @date 2017-11-30 上午9:47:09
	 */
	@RequestMapping(value = "/creditQueryAjax")
	@ResponseBody
	public Map<String, Object> creditQueryAjax(String ajaxData, String cusNo, String appId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCreditQueryRecordInfo mfCreditQueryRecordInfo = new MfCreditQueryRecordInfo();
		try {
			FormData formcreditquery0001 = formService.getFormData("creditquery0001");
			System.out.println("ajaxData: " + ajaxData);
			getFormValue(formcreditquery0001, getMapByJson(ajaxData));
			if (this.validateFormData(formcreditquery0001)) {
				setObjValue(formcreditquery0001, mfCreditQueryRecordInfo);
				mfCreditQueryRecordInfo.setCusNo(cusNo);
				mfCreditQueryRecordInfo.setAppId(appId);
				dataMap = mfCreditQueryRecordInfoFeign.doCreditQuery(mfCreditQueryRecordInfo);
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 * 方法描述： 获得征信报告内容
	 * @param queryId
	 * @return
	 * @throws Exception String
	 * @author 沈浩兵
	 * @date 2017-12-4 上午11:30:13
	 */
	@RequestMapping(value = "/getCreditContentAjax")
	@ResponseBody
	public Map<String, Object> getCreditContentAjax(String ajaxData, String queryId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCreditQueryRecordInfo mfCreditQueryRecordInfo = new MfCreditQueryRecordInfo();
		try {
			mfCreditQueryRecordInfo.setQueryId(queryId);
			mfCreditQueryRecordInfo=mfCreditQueryRecordInfoFactorFeign.getById(mfCreditQueryRecordInfo);
			String queryResult =mfCreditQueryRecordInfo.getQueryResult();
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
			Map<String,String> parmDicMap = new CodeUtils().getMapByKeyName("QUERY_REASON_BAIHANG");
			Map<String, Object> resultMap = new Gson().fromJson(queryResult, Map.class);
			Map<String, Object> firstMap = new Gson().fromJson((String)resultMap.get("data"), Map.class) ;
			if(firstMap.get("personalProfile")!=null){
				JSONObject personalProfile = JSONObject.fromObject(firstMap.get("personalProfile"));
				personalProfile.put("name",mfCreditQueryRecordInfo.getCusName());
				personalProfile.put("pid",mfCreditQueryRecordInfo.getIdNum());
				firstMap.put("personalProfile",personalProfile);
			}
			if(firstMap.get("reportHeader")!=null){
				JSONObject reportHeader = JSONObject.fromObject(firstMap.get("reportHeader"));
				reportHeader.put("queryReslut",parmDicMap.get(reportHeader.get("queryReslut")));
				firstMap.put("reportHeader",reportHeader);
			}
			if(firstMap.get("homeInfo")!=null){
				JSONArray homeInfo = JSONArray.fromObject(firstMap.get("homeInfo"));
				for (int i = 0; i < homeInfo.size(); i++) {
					JSONObject homeInfoObject = (JSONObject) homeInfo.get(i);
					homeInfoObject.put("bianhao",i+1);
				}
				firstMap.put("homeInfo",homeInfo);
			}
			if(firstMap.get("workInfo")!=null){
				JSONArray workInfo = JSONArray.fromObject(firstMap.get("workInfo"));
				for (int i = 0; i < workInfo.size(); i++) {
					JSONObject workInfoObject = (JSONObject) workInfo.get(i);
					workInfoObject.put("bianhao",i+1);
				}
				firstMap.put("workInfo",workInfo);
			}
			if(firstMap.get("queryHistory")!=null){
				JSONArray queryHistory = JSONArray.fromObject(firstMap.get("queryHistory"));
				for (int i = 0; i < queryHistory.size(); i++) {
					JSONObject queryHistoryObject = (JSONObject) queryHistory.get(i);
					queryHistoryObject.put("bianhao",i+1);
					Double reason = (Double) queryHistoryObject.get("reason");
					String reasons = String.valueOf(reason);
					queryHistoryObject.put("reason",parmDicMap.get(reasons.split("\\.")[0]));
				}
				firstMap.put("queryHistory",queryHistory);
			}
			InputStreamReader read = null;
			BufferedReader bufferedReader = null;
			// 生成html
			ServletFreeMarker sfm = new ServletFreeMarker(request.getServletContext(), "/component/thirdservice/baihang/ftl");
			queryResult = sfm.generateHtml(firstMap, "querycreditbaihang.html");
			//创建pdf文件名
			String authCode= "baihang";
			String rootPath=request.getServletContext().getRealPath("/component/thirdservice/baihang");
			System.out.print("读取相对路径成功");
			//根据生成的html生成pdf
			//PdfUtil.createAuthCodeBaiHangPdf(queryResult,authCode,rootPath);
			StringBuilder path = new StringBuilder();
			path.append(request.getServletContext().getRealPath("/component/thirdservice/baihang/ftl/")).append(authCode+".pdf");
			File file = new File(path.toString());
			if (file.exists()) {
				dataMap.put("creditContent", queryResult);
				dataMap.put("authCode", authCode);
				dataMap.put("flag", "success");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg", "征信报告不存在");
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}
	/**
	 * 方法描述： 百行征信查询
	 * @param cusNo
	 * @return
	 * @throws Exception String
	 * @author 沈浩兵
	 * @date 2017-12-4 上午11:30:13
	 */
	@RequestMapping(value = "/queryCreditForBaiHang")
	@ResponseBody
	public Map<String, Object> queryCreditForBaiHang(String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = mfCreditQueryRecordInfoFactorFeign.queryCreditForBaiHang(cusNo);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "查询失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}
	/**
	 * 
	 * 方法描述： 根据客户号获得最新的征信报告结果
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @param cusNo 
	 * @param appId 
	 * @date 2017-12-4 下午2:30:59
	 */
	@RequestMapping(value = "/getCreditQueryByCusNoAjax")
	@ResponseBody
	public Map<String, Object> getCreditQueryByCusNoAjax(String ajaxData, String cusNo, String appId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCreditQueryRecordInfo mfCreditQueryRecordInfo = new MfCreditQueryRecordInfo();
		try{
			if (CoreEnum.getThirdIsOpen()) {
				dataMap = mfCreditQueryRecordInfoFeign.getNewestCreditHtml(cusNo, appId);
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", "核心功能未启用");
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "根据客户号获得最新的征信报告结果失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}
	/**
	 * 
	 * 方法描述： 获得征信查询所需要的要件资料
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @param cusNo 
	 * @param cusBaseType 
	 * @param appId 
	 * @date 2017-12-22 下午3:47:46
	 */
	@RequestMapping(value = "/getCreditCreditDocAjax")
	@ResponseBody
	public Map<String, Object> getCreditCreditDocAjax(String ajaxData, String cusNo, String cusBaseType, String appId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCreditQueryRecordInfo mfCreditQueryRecordInfo = new MfCreditQueryRecordInfo();
		try{
			dataMap=mfCreditQueryRecordInfoFeign.getDocManageListForCredit(cusNo,cusBaseType,null, appId);
			dataMap.put("flag", "success");
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "根据客户号获得最新的征信报告结果失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}
	/**
	 * 
	 * 方法描述： 获得征信查询客户信息
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @param cusNo 
	 * @param creditCusType 
	 * @date 2017-12-26 下午8:58:57
	 */
	@RequestMapping(value = "/getCreditQueryCusInfoAjax")
	@ResponseBody
	public Map<String, Object> getCreditQueryCusInfoAjax(String ajaxData, String cusNo, String creditCusType) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			dataMap=mfCreditQueryRecordInfoFeign.getCreditQueryCusInfo(cusNo,creditCusType);
			dataMap.put("flag", "success");
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}
	/**
	 * 
	 * 方法描述： 获得查询历史html
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @param appId 
	 * @param cusNo 
	 * @date 2017-12-27 上午9:48:27
	 */
	@RequestMapping(value = "/getCreditQueryHisHtmlAjax")
	@ResponseBody
	public Map<String, Object> getCreditQueryHisHtmlAjax(String ajaxData, String appId, String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCreditQueryRecordInfo mfCreditQueryRecordInfo = new MfCreditQueryRecordInfo();
		try{
			mfCreditQueryRecordInfo.setAppId(appId);
			mfCreditQueryRecordInfo.setCusNo(cusNo);
			List<MfCreditQueryRecordInfo> mfCreditQueryRecordInfoList = mfCreditQueryRecordInfoFeign.getCreditQueryRecord(mfCreditQueryRecordInfo);
			if(mfCreditQueryRecordInfoList!=null&&mfCreditQueryRecordInfoList.size()>0){
				JsonTableUtil jtu = new JsonTableUtil();
				String htmlStr = jtu.getJsonStr("tablecreditquery0001", "tableTag", mfCreditQueryRecordInfoList, null, true);
				dataMap.put("htmlStr", htmlStr);
			}
			dataMap.put("flag", "success");
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}
	//读取pdf文件
	@RequestMapping(value = "/showbaihangpdf")
	@ResponseBody
	public Map<String,Object> showbaihangpdf(String authCode) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String,Object>();
		OutputStream out=null;
		InputStream in=null;
		try {
			String rootPath=request.getServletContext().getRealPath("/component/thirdservice/baihang");
			File f = new File(rootPath+File.separator+"ftl"+File.separator+authCode+".pdf");
			if(f!=null && f.exists()){
				response.setContentType("application/pdf");
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Content-Length",String.valueOf(f.length()));
				in = new FileInputStream(f);
				out = new BufferedOutputStream(response.getOutputStream());
				byte[] data = new byte[1024];
				int len = 0;
				while (-1 != (len=in.read(data, 0, data.length))) {
					out.write(data, 0, len);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag","error");
			dataMap.put("msg","查询出错");
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return dataMap;
	}
}
