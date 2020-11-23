package app.component.thirdservice.lmxd.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.paph.entity.ApiReturnRecord;
import app.component.paph.feign.ApiReturnRecordFeign;
import app.component.thirdserviceinterface.TcphInterfaceFeign;
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
import com.google.gson.Gson;
import app.component.thirdservice.util.FormBeanUtil;
import app.component.app.entity.MfBusApply;
import app.component.app.feign.MfBusApplyFeign;
import app.component.appinterface.AppInterfaceFeign;
import app.component.collateralinterface.CollateralInterfaceFeign;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cusinterface.CusInterfaceFeign;
import app.component.nmd.entity.ParmDic;
import app.component.thirdservice.lmxd.entity.MfThirdServiceRecord;
import app.component.thirdservice.lmxd.feign.MfThirdServiceRecordFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.StringUtil;
import config.YmlConfig;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import app.component.thirdservice.lmxd.entity.MfThirdServiceRecordParm;
/**
 * Title: MfThirdServiceRecordAction.java Description:
 *
 * @author:kaifa@dhcc.com.cn
 * @Tue Dec 19 15:46:55 CST 2017
 **/
@Controller
@RequestMapping("/mfThirdServiceRecord")
public class MfThirdServiceRecordController extends BaseFormBean {
	// 注入MfThirdServiceRecordFeign
	@Autowired
	private MfThirdServiceRecordFeign mfThirdServiceRecordFeign;
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;
	@Autowired
	private CusInterfaceFeign cusInterfaceFeign;
	@Autowired
	private CollateralInterfaceFeign collateralInterfaceFeign;
	@Autowired
	private MfBusApplyFeign mfBusApplyFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private YmlConfig ymlConfig;

	@Autowired
	private TcphInterfaceFeign tcphInterfaceFeign;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private ApiReturnRecordFeign apiReturnRecordFeign;
	/**
	 * 列表打开页面请求
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request,response);
		return "/component/thirdservice/lmxd/MfThirdServiceRecord_List";
	}

	/***
	 * 列表数据查询
	 *
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData,String cusNo,String appId) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfThirdServiceRecord mfThirdServiceRecord = new MfThirdServiceRecord();
		try {
			System.out.println(cusNo);
			mfThirdServiceRecord.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfThirdServiceRecord.setCriteriaList(mfThirdServiceRecord, ajaxData);// 我的筛选
			mfThirdServiceRecord.setCusNo(cusNo);// 我的筛选
			mfThirdServiceRecord.setAppId(appId);// 我的筛选
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfThirdServiceRecord", mfThirdServiceRecord));
			ipage = mfThirdServiceRecordFeign.findByPage(ipage);
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
	/***
	 * 列表数据查询
	 *
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/findThirdServiceRecordListAjax")
	@ResponseBody
	public Map<String, Object> findThirdServiceRecordListAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData,String cusNo,String appId) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfThirdServiceRecord mfThirdServiceRecord = new MfThirdServiceRecord();
		try {
			System.out.println(cusNo);
			mfThirdServiceRecord.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfThirdServiceRecord.setCriteriaList(mfThirdServiceRecord, ajaxData);// 我的筛选
			mfThirdServiceRecord.setCusNo(cusNo);// 我的筛选
			mfThirdServiceRecord.setAppId(appId);// 我的筛选
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfThirdServiceRecord", mfThirdServiceRecord));
			ipage = mfThirdServiceRecordFeign.findThirdServiceRecordList(ipage);
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
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formtcph0002 = formService.getFormData("tcph0002");
			getFormValue(formtcph0002, getMapByJson(ajaxData));
			if (this.validateFormData(formtcph0002)) {
				MfThirdServiceRecord mfThirdServiceRecord = new MfThirdServiceRecord();
				setObjValue(formtcph0002, mfThirdServiceRecord);
				mfThirdServiceRecordFeign.insert(mfThirdServiceRecord);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR_INSERT.getMessage());
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
	@RequestMapping(value = "/getListByCusNoAjax")
	@ResponseBody
	public Map<String, Object> getListByCusNoAjax(String cusNo) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
		ApiReturnRecord apiReturnRecord = new ApiReturnRecord();
		apiReturnRecord.setReturnId(cusNo);
		List<ApiReturnRecord> recordList =apiReturnRecordFeign.getApiReturnRecordList(apiReturnRecord);
			if(recordList.size()>0 && recordList!=null){
				dataMap.put("flag", "success");
			}else{
				dataMap.put("flag", "error");
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg","根据客户号获取三方查询记录失败！");
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
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formtcph0002 = formService.getFormData("tcph0002");
		getFormValue(formtcph0002, getMapByJson(ajaxData));
		MfThirdServiceRecord mfThirdServiceRecordJsp = new MfThirdServiceRecord();
		setObjValue(formtcph0002, mfThirdServiceRecordJsp);
		MfThirdServiceRecord mfThirdServiceRecord = mfThirdServiceRecordFeign.getById(mfThirdServiceRecordJsp);
		if (mfThirdServiceRecord != null) {
			try {
				mfThirdServiceRecord = (MfThirdServiceRecord) EntityUtil.reflectionSetVal(mfThirdServiceRecord, mfThirdServiceRecordJsp, getMapByJson(ajaxData));
				mfThirdServiceRecordFeign.update(mfThirdServiceRecord);
				dataMap.put("flag", "success");
				dataMap.put("msg",MessageEnum.SUCCEED_UPDATE.getMessage());
			} catch (Exception e) {
				dataMap.put("flag", "error");
				dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
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
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfThirdServiceRecord mfThirdServiceRecord = new MfThirdServiceRecord();
		try {
			FormData formtcph0002 = formService.getFormData("tcph0002");
			getFormValue(formtcph0002, getMapByJson(ajaxData));
			if (this.validateFormData(formtcph0002)) {
				mfThirdServiceRecord = new MfThirdServiceRecord();
				setObjValue(formtcph0002, mfThirdServiceRecord);
				mfThirdServiceRecordFeign.update(mfThirdServiceRecord);
				dataMap.put("flag", "success");
				dataMap.put("msg",MessageEnum.SUCCEED_UPDATE.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
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
	public Map<String, Object> getByIdAjax(String id) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String,Object> dataMap = new HashMap<String, Object>();
		FormData formtcph0002 = formService.getFormData("tcph0002");
		MfThirdServiceRecord mfThirdServiceRecord = new MfThirdServiceRecord();
		mfThirdServiceRecord.setId(id);
		mfThirdServiceRecord = mfThirdServiceRecordFeign.getById(mfThirdServiceRecord);
		getObjValue(formtcph0002, mfThirdServiceRecord, formData);
		if (mfThirdServiceRecord != null) {
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
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String id) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfThirdServiceRecord mfThirdServiceRecord = new MfThirdServiceRecord();
		mfThirdServiceRecord.setId(id);
		try {
			mfThirdServiceRecordFeign.delete(mfThirdServiceRecord);
			dataMap.put("flag", "success");
			dataMap.put("msg",MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}


	/**
	 * 查询
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(String id) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();

		FormData formtcph0001 = formService.getFormData("tcph0001");
		getFormValue(formtcph0001);
		MfThirdServiceRecord mfThirdServiceRecord = new MfThirdServiceRecord();
		mfThirdServiceRecord.setId(id);
		mfThirdServiceRecord = mfThirdServiceRecordFeign.getById(mfThirdServiceRecord);
		System.out.println("1111");
		getObjValue(formtcph0001, mfThirdServiceRecord);
		return "/component/thirdservice/lmxd/MfThirdServiceRecord_Detail";
	}

	/**
	 *
	 * 方法描述： 风控尽调（天诚普惠使用，主要对接第三方查询数据）
	 *
	 * @return
	 */
	@RequestMapping(value = "/findList")
	public List<MfThirdServiceRecord> findList() throws Exception {
		ActionContext.initialize(request,response);
		// 三方数据查询
		MfThirdServiceRecord mfThirdServiceRecord = new MfThirdServiceRecord();
		mfThirdServiceRecord.setCusNo("");// 客户号
		mfThirdServiceRecord.setAppId("");// 申请号
		List<MfThirdServiceRecord> list = mfThirdServiceRecordFeign.findByPages(mfThirdServiceRecord);
		return list;
	}
	/**
	 *
	 * 方法描述： 获得信息报告内容
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author 王俊杰
	 * @date 2017-12-4 上午11:30:13
	 */
	@RequestMapping(value = "/getServiceRecordByCusNoAjax")
	@ResponseBody
	public Map<String, Object> getServiceRecordByCusNoAjax(String id) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			dataMap = mfThirdServiceRecordFeign.getHtmlContent(id);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "获取报告信息结果失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}
	/**
	 *
	 * 方法描述： 风控尽调（主要对接第三方查询数据）
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @date 2017-12-14 上午10:38:47
	 */
	@RequestMapping(value = "/riskReport")
	public String riskReport(Model model, String ajaxData,String cusNo,String appId) throws Exception{
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String,Object>();
		MfBusApply mfBusApply = new MfBusApply();
		if(StringUtil.isNotEmpty(appId)){

			mfBusApply.setAppId(appId);
			mfBusApply = mfBusApplyFeign.getById(mfBusApply);
			if (mfBusApply != null){
				mfBusApply.setFincAmt(MathExtend.moneyStr(mfBusApply.getAppAmt()/10000));
				mfBusApply = mfBusApplyFeign.processDataForApply(mfBusApply);//处理利率格式
				//处理利率展示,年利率展示百分位,月利率展示千分位,日利率展示万分位
				Map<String, ParmDic> rateTypeMap = new CodeUtils().getMapObjByKeyName("RATE_TYPE");
				String rateUnit = rateTypeMap.get(mfBusApply.getRateType()).getRemark();
				// 处理期限的展示单位。
				Map<String, ParmDic> termTypeMap = new CodeUtils().getMapObjByKeyName("TERM_TYPE");
				String termUnit= termTypeMap.get(mfBusApply.getTermType()).getRemark();
				dataMap.put("termUnit", termUnit);
			}
		}
		 MfCusCustomer mfCusCustomer = new  MfCusCustomer();
		mfCusCustomer.setCusNo(cusNo);
		mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
		//三方数据查询类型
		JSONArray array= new CodeUtils().getJSONArrayByKeyName("THIRD_SERVICE_TYPE");
		for (Iterator tor=array.iterator();tor.hasNext();) {
			JSONObject job = (JSONObject)tor.next();
			if(BizPubParm.CUS_BASE_TYPE_CORP.equals(mfCusCustomer.getCusBaseType())){
				if("identityCheck".equals(job.get("optCode"))){
					array.remove(job);
					break;
				}
			}else{
				if("networkCheck".equals(job.get("optCode"))){
					array.remove(job);
					break;
				}
			}
		}
        ajaxData = array.toString();
		//model.addAttribute("mfBusApply", mfBusApply);
		model.addAttribute("mfCusCustomer", mfCusCustomer);
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("ajaxData", ajaxData);
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("cusName", mfCusCustomer.getCusName());
		model.addAttribute("idNum", mfCusCustomer.getIdNum());
		model.addAttribute("contactsTel", mfCusCustomer.getContactsTel());
		model.addAttribute("commAddress", mfCusCustomer.getCommAddress());
		model.addAttribute("appId", appId);
		model.addAttribute("query", "");
		return "/component/thirdservice/lmxd/MfThirdService_RiskReport";
	}
	/**
	 *
	 * 方法描述： 进入身份核查查询表单
	 * @return
	 * @throws Exception
	 * String
	 * @author ldy
	 * @date 2018年7月5日17:48:30
	 */
	@RequestMapping(value = "/inputPersonCheck")
	public String inputPersonCheck(Model model,String cusNo,String thirdFlag) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String,Object>();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		try{
			mfCusCustomer.setCusNo(cusNo);
			mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		FormData formidentityCheckbase = formService.getFormData("identityCheckbase");
		getObjValue(formidentityCheckbase, mfCusCustomer);
		model.addAttribute("formidentityCheckbase", formidentityCheckbase);
		model.addAttribute("query", "");
		model.addAttribute("mfCusCustomer",mfCusCustomer);
		model.addAttribute("thirdFlag", thirdFlag);
		model.addAttribute("idCheck",  ymlConfig.getCloud().get("idCheck"));
		return "/component/thirdservice/lmxd/MfCusIdentityCheck";
	}
	/**
	 *
	 * 方法描述： 进入企业联网核查查询表单
	 * @return
	 * @throws Exception
	 * String
	 * @author ldy
	 * @date 2018年7月5日17:48:30
	 */
	@RequestMapping(value = "/inputCorpCheck")
	public String inputCorpCheck(Model model,String cusNo,String thirdFlag) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String,Object>();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		try{
			mfCusCustomer.setCusNo(cusNo);
			mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		FormData formnetworkCheckbase = formService.getFormData("networkCheckbase");
		getObjValue(formnetworkCheckbase, mfCusCustomer);
		model.addAttribute("formnetworkCheckbase", formnetworkCheckbase);
		model.addAttribute("query", "");
		model.addAttribute("mfCusCustomer",mfCusCustomer);
		model.addAttribute("thirdFlag", thirdFlag);
		model.addAttribute("netCheck", ymlConfig.getCloud().get("netCheck"));

		return "/component/thirdservice/lmxd/MfCusNetworkCheck";
	}
	@RequestMapping(value = "/getQueryHtmlById")
	public String getQueryHtmlById(Model model,String id) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Gson gson=new Gson();
		Map<String, Object> dataMap = new HashMap<String,Object>();
		FormData formThirdServiceReport = null;
		MfThirdServiceRecord mfThirdServiceRecord = new MfThirdServiceRecord();
		mfThirdServiceRecord.setId(id);
		mfThirdServiceRecord = mfThirdServiceRecordFeign.getById(mfThirdServiceRecord);

		if("identityCheck".equals(mfThirdServiceRecord.getThirdFlag())){//身份核查
			Map<String, Object> str = mfThirdServiceRecordFeign.getHtmlContent(id);
			JSONObject obj = JSONObject.fromObject(str.get("content"));
			String data = obj.getString("data"); //获得返回参数
			Map<String,Object> dataMp=gson.fromJson(data, Map.class);
			dataMap.put("cusName", dataMp.get("idCardName"));
			dataMap.put("idNum", dataMp.get("idCardNum"));
			if("S".equals(dataMp.get("checkStatus"))){
				dataMap.put("result","身份证号码与姓名一致");
			}else if("D".equals(dataMp.get("checkStatus"))){
				dataMap.put("result","身份证号码与姓名不一致");
			}else if("N".equals(dataMp.get("checkStatus"))){
				dataMap.put("result","未查询到此人身份信息");

			}else{
				dataMap.put("result",dataMp.get("message"));
			}
			formThirdServiceReport = formService.getFormData("identityCheckdetail");
			//this.changeFormProperty(formidentityCheckdetail, "", "initValue", dataMap);
			getObjValue(formThirdServiceReport, dataMap);
			model.addAttribute("query", "");
			model.addAttribute("formThirdServiceReport",formThirdServiceReport);
			return "/component/thirdservice/lmxd/MfCusIdentityCheck_Detail";
		}else if("lawEnforcement".equals(mfThirdServiceRecord.getThirdFlag())){//法执情况
			MfCusCustomer mfCusCustomer = new MfCusCustomer();
			mfCusCustomer.setCusNo(mfThirdServiceRecord.getCusNo());
			mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
			Map<String, Object> str = mfThirdServiceRecordFeign.getHtmlContent(id);
			JSONObject obj = JSONObject.fromObject(str.get("content"));
			String o = obj.toString();
			model.addAttribute("busName", mfThirdServiceRecord.getCusName());
			model.addAttribute("busNumber", mfCusCustomer.getIdNum());
			model.addAttribute("result", o);

			return "/component/thirdservice/cloudmftcc/MfThirdMftccHighcourt_Detail";
		}else {
			MfCusCustomer mfCusCustomer = new MfCusCustomer();
			mfCusCustomer.setCusNo(mfThirdServiceRecord.getCusNo());
			mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
			Map<String, Object> str = mfThirdServiceRecordFeign.getHtmlContent(id);
			JSONObject obj = JSONObject.fromObject(str);
			String data = obj.getString("content"); //获得返回参数

			Map<String,Object> dataMp=gson.fromJson(data, Map.class);
			String type = mfThirdServiceRecord.getThirdFlag();
            String httpStrs = "";
            switch (type){
                case "personalCorpQuery"://个人名下企业
                    httpStrs =new FormBeanUtil().getPersonalCorpToHtml(dataMp);
                    break;
                case "idAttestation"://身份认证返照片
                    httpStrs =new FormBeanUtil().getIdAttestationHtml(dataMp);
                    break;
                case "censusDataQuery"://户籍信息查询
                    httpStrs =new FormBeanUtil().getCensusDataQueryHtml(dataMp);
                    break;
                case "badInfoQuery"://不良信息查询
                    httpStrs =new FormBeanUtil().getBadInfoQueryHtml(dataMp);
                    break;
                case "corpLawsuitsAllClass":// 企业涉诉高法全类
                    httpStrs =new FormBeanUtil().getCorpLawsuitsAllClassHtml(dataMp);
                    break;
                case "corpICData":// 企业工商数据查询
                    httpStrs =new FormBeanUtil().getCorpICDataHtml(dataMp);
                    break;
                case "loseCerditBZXRInfo":// 失信被执行人信息查询
                    httpStrs =new FormBeanUtil().getLoseCerditBZXRInfoHtml(dataMp);
                    break;
                    default:
            }
            model.addAttribute("httpStrs", httpStrs);
			return "/component/thirdservice/lmxd/MfCusThirdServiceInfoShow";
		}

	}
	/**
	 *
	 * 方法描述： 校验是否存在改报告-联网核查
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author ldy
	 * @date 2017-12-4 上午11:30:13
	 */
	@RequestMapping(value = "/getNetworkCheckAjax")
	@ResponseBody
	public Map<String, Object> getNetworkCheckAjax(String id) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfThirdServiceRecord mfThirdServiceRecord = new MfThirdServiceRecord();
			mfThirdServiceRecord.setId(id);
			mfThirdServiceRecord = mfThirdServiceRecordFeign.getById(mfThirdServiceRecord);
			if("0".equals(mfThirdServiceRecord.getResultFlag())){
				dataMap.put("flag", "success");
			}else{
				dataMap.put("flag", "success");
				dataMap.put("msg", "获取报告信息结果失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "获取报告信息结果失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 *
	 * 方法描述： 跳转至三方查询入参页面
	 * @return
	 * @throws Exception
	 * String
	 * @author zhs
	 * @date 2017-12-20 上午9:40:42
	 */
	@RequestMapping(value = "/thirdInput")
	public String thirdInput(Model model, String ajaxData,String id,String appId,String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData 	formapply0001;
		if(id == null || "".equals(id)){
			MfBusApply 	mfBusApply = new MfBusApply();
			mfBusApply.setAppId(appId);
			mfBusApply = mfBusApplyFeign.getById(mfBusApply);
			MfCusCustomer 	mfCusCustomer = new  MfCusCustomer();
			if(appId == null || "".equals(appId) ){
				mfCusCustomer.setCusNo(cusNo);
			}else{
				mfCusCustomer.setCusNo(mfBusApply.getCusNo());
			}
			mfCusCustomer = cusInterfaceFeign.getMfCusCustomerById(mfCusCustomer);
			formapply0001 = formService.getFormData("riskreportquery");
			getObjValue(formapply0001, mfBusApply);
			getObjValue(formapply0001, mfCusCustomer);
			this.changeFormProperty(formapply0001, "thirdQueryType", "initValue", ajaxData);
		}else{
			MfThirdServiceRecord mfThirdServiceRecord = new MfThirdServiceRecord();
			mfThirdServiceRecord.setId(id);
			MfThirdServiceRecordParm mfThirdServiceRecordParm = tcphInterfaceFeign.getMfThirdServiceRecordById(mfThirdServiceRecord);
			ajaxData = mfThirdServiceRecordParm.getThirdQueryType();
			formapply0001 = formService.getFormData("riskreportquery");
			getObjValue(formapply0001, mfThirdServiceRecordParm);
		}
		if(ajaxData != null && BizPubParm.THIRD_SERVICE_TYPE80.equals(ajaxData)){
			CodeUtils cu = new CodeUtils();
			JSONArray jsonArray = cu.getJSONArrayByKeyName("SEARCH_NO");
			for (int i = 0;i< jsonArray.size();i++) {
				jsonArray.getJSONObject(i).put("id", jsonArray.getJSONObject(i).getString("optCode"));
				jsonArray.getJSONObject(i).put("name", jsonArray.getJSONObject(i).getString("optName"));
			}
			JSONObject 	json = new JSONObject();
			json.put("searchNo",jsonArray);
			ajaxData = json.toString();
		}
		model.addAttribute("formapply0001", formapply0001);
		model.addAttribute("query", "");
		return "/component/app/MfBusApply_ThirdInput";
	}
	/**
	 *方法描述： 身份认证（返照片）
	 * @return
	 * @throws Exception
	 * String
	 * @author 吕春杰
	 * @date 2018-8-24 下午14:00:00
	 */
	@RequestMapping(value = "/idAttestationAjax")
	@ResponseBody
	public Map<String, Object> idAttestationAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		try {
			FormData formriskreportquery = formService.getFormData("riskreportquery");
			getFormValue(formriskreportquery, getMapByJson(ajaxData));
			if (this.validateFormDataAnchor(formriskreportquery)) {
				MfThirdServiceRecordParm mfThirdServiceRecordParm = new MfThirdServiceRecordParm();
				setObjValue(formriskreportquery, mfThirdServiceRecordParm);
				dataMap = mfThirdServiceRecordFeign.idAttestation(mfThirdServiceRecordParm);

                String httpStrs = new FormBeanUtil().getIdAttestationHtml(dataMap);
                dataMap.put("httpStrs",httpStrs);
                dataMap.put("flag", "success");
                dataMap.put("msg", MessageEnum.SUCCEED_QUERY.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
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
	 * 跳入外网查询页面
	 * @return
	 */
	@RequestMapping(value = "/jumpNetworkPage")
	public String jumpNetworkPage(Model model,String url){

		model.addAttribute("statu",url);
		return "/component/thirdservice/lmxd/networkPage";
	}
	/**
	 *
	 * 方法描述：(个人名下企业)
	 *
	 * @return
	 */
	@RequestMapping(value = "/personalCorpQueryAjax")
	@ResponseBody
	public Map<String, Object> personalCorpQueryAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		try {
			FormData formriskreportquery = formService.getFormData("riskreportquery");
			getFormValue(formriskreportquery, getMapByJson(ajaxData));
			if (this.validateFormDataAnchor(formriskreportquery)) {
				MfThirdServiceRecordParm mfThirdServiceRecordParm = new MfThirdServiceRecordParm();
				setObjValue(formriskreportquery, mfThirdServiceRecordParm);
                Map<String, Object>	resultMap = mfThirdServiceRecordFeign.personalCorpQuery(mfThirdServiceRecordParm);

				String httpStrs = new FormBeanUtil().getPersonalCorpToHtml(resultMap);

				dataMap.put("httpStrs",httpStrs);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_QUERY.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
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
	 * 方法描述：(短信服务)
	 *
	 * @return
	 */
	@RequestMapping(value = "/messageServiceAjax")
	@ResponseBody
	public Map<String, Object> messageServiceAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		try {
			FormData formriskreportquery = formService.getFormData("riskreportquery");
			getFormValue(formriskreportquery, getMapByJson(ajaxData));
			if (this.validateFormDataAnchor(formriskreportquery)) {
				MfThirdServiceRecordParm mfThirdServiceRecordParm = new MfThirdServiceRecordParm();
				setObjValue(formriskreportquery, mfThirdServiceRecordParm);
				dataMap = mfThirdServiceRecordFeign.messageService(mfThirdServiceRecordParm);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_QUERY.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
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
	 * 方法描述：(户籍信息查询)
	 *
	 * @return
	 */
	@RequestMapping(value = "/censusDataQueryAjax")
	@ResponseBody
	public Map<String, Object> censusDataQueryAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		try {
			FormData formriskreportquery = formService.getFormData("riskreportquery");
			getFormValue(formriskreportquery, getMapByJson(ajaxData));
			if (this.validateFormDataAnchor(formriskreportquery)) {
				MfThirdServiceRecordParm mfThirdServiceRecordParm = new MfThirdServiceRecordParm();
				setObjValue(formriskreportquery, mfThirdServiceRecordParm);
				dataMap = mfThirdServiceRecordFeign.censusDataQuery(mfThirdServiceRecordParm);

				String httpStrs = new FormBeanUtil().getCensusDataQueryHtml(dataMap);
				dataMap.put("httpStrs",httpStrs);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_QUERY.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
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
	 * 方法描述：(不良信息查询)
	 *
	 * @return
	 */
	@RequestMapping(value = "/badInfoQueryAjax")
	@ResponseBody
	public Map<String, Object> badInfoQueryAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		try {
			FormData formriskreportquery = formService.getFormData("riskreportquery");
			getFormValue(formriskreportquery, getMapByJson(ajaxData));
			if (this.validateFormDataAnchor(formriskreportquery)) {
				MfThirdServiceRecordParm mfThirdServiceRecordParm = new MfThirdServiceRecordParm();
				setObjValue(formriskreportquery, mfThirdServiceRecordParm);
				dataMap = mfThirdServiceRecordFeign.badInfoQuery(mfThirdServiceRecordParm);

				String httpStrs = new FormBeanUtil().getBadInfoQueryHtml(dataMap);
				dataMap.put("httpStrs",httpStrs);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_QUERY.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
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
	 * 方法描述：(企业涉诉高法全类)
	 *
	 * @return
	 */
	@RequestMapping(value = "/corpLawsuitsAllClassAjax")
	@ResponseBody
	public Map<String, Object> corpLawsuitsAllClassAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		try {
			FormData formriskreportquery = formService.getFormData("riskreportquery");
			getFormValue(formriskreportquery, getMapByJson(ajaxData));
			if (this.validateFormDataAnchor(formriskreportquery)) {
				MfThirdServiceRecordParm mfThirdServiceRecordParm = new MfThirdServiceRecordParm();
				setObjValue(formriskreportquery, mfThirdServiceRecordParm);
				dataMap = mfThirdServiceRecordFeign.corpLawsuitsAllClass(mfThirdServiceRecordParm);

				String httpStrs = new FormBeanUtil().getCorpLawsuitsAllClassHtml(dataMap);
				dataMap.put("httpStrs",httpStrs);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_QUERY.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
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
	 * 方法描述：(多重借贷查询)
	 *
	 * @return
	 */
	@RequestMapping(value = "/multipleLoansQueryAjax")
	@ResponseBody
	public Map<String, Object> multipleLoansQueryAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		try {
			FormData formriskreportquery = formService.getFormData("riskreportquery");
			getFormValue(formriskreportquery, getMapByJson(ajaxData));
			if (this.validateFormDataAnchor(formriskreportquery)) {
				MfThirdServiceRecordParm mfThirdServiceRecordParm = new MfThirdServiceRecordParm();
				setObjValue(formriskreportquery, mfThirdServiceRecordParm);
				dataMap = mfThirdServiceRecordFeign.multipleLoansQuery(mfThirdServiceRecordParm);

				String httpStrs = new FormBeanUtil().getMultipleLoansQueryHtml(dataMap);
				dataMap.put("httpStrs",httpStrs);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_QUERY.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
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
	 * 方法描述：(企业工商数据查询)
	 *
	 * @return
	 */
	@RequestMapping(value = "/corpICDataAjax")
	@ResponseBody
	public Map<String, Object> corpICDataAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		try {
			FormData formriskreportquery = formService.getFormData("riskreportquery");
			getFormValue(formriskreportquery, getMapByJson(ajaxData));
			if (this.validateFormDataAnchor(formriskreportquery)) {
				MfThirdServiceRecordParm mfThirdServiceRecordParm = new MfThirdServiceRecordParm();
				setObjValue(formriskreportquery, mfThirdServiceRecordParm);
				dataMap = mfThirdServiceRecordFeign.corpICData(mfThirdServiceRecordParm);

				String httpStrs = new FormBeanUtil().getCorpICDataHtml(dataMap);
				dataMap.put("httpStrs",httpStrs);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_QUERY.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
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
	 * 方法描述：(失信黑名单查询)
	 *
	 * @return
	 */
	@RequestMapping(value = "/loseCreditBlacklistAjax")
	@ResponseBody
	public Map<String, Object> loseCreditBlacklistAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		try {
			FormData formriskreportquery = formService.getFormData("riskreportquery");
			getFormValue(formriskreportquery, getMapByJson(ajaxData));
			if (this.validateFormDataAnchor(formriskreportquery)) {
				MfThirdServiceRecordParm mfThirdServiceRecordParm = new MfThirdServiceRecordParm();
				setObjValue(formriskreportquery, mfThirdServiceRecordParm);
				dataMap = mfThirdServiceRecordFeign.loseCreditBlacklist(mfThirdServiceRecordParm);

				String httpStrs = new FormBeanUtil().getLoseCreditBlacklistHtml(dataMap);
				dataMap.put("httpStrs",httpStrs);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_QUERY.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
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
	 * 方法描述：(失信人信息查询)
	 *
	 * @return
	 */
	@RequestMapping(value = "/loseCerditPersonInfoAjax")
	@ResponseBody
	public Map<String, Object> loseCerditPersonInfoAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		try {
			FormData formriskreportquery = formService.getFormData("riskreportquery");
			getFormValue(formriskreportquery, getMapByJson(ajaxData));
			if (this.validateFormDataAnchor(formriskreportquery)) {
				MfThirdServiceRecordParm mfThirdServiceRecordParm = new MfThirdServiceRecordParm();
				setObjValue(formriskreportquery, mfThirdServiceRecordParm);
				dataMap = mfThirdServiceRecordFeign.loseCerditPersonInfo(mfThirdServiceRecordParm);

				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_QUERY.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
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
	 * 方法描述：(网贷黑名单信息)
	 *
	 * @return
	 */
	@RequestMapping(value = "/netLoanBlacklistInfoAjax")
	@ResponseBody
	public Map<String, Object> netLoanBlacklistInfoAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		try {
			FormData formriskreportquery = formService.getFormData("riskreportquery");
			getFormValue(formriskreportquery, getMapByJson(ajaxData));
			if (this.validateFormDataAnchor(formriskreportquery)) {
				MfThirdServiceRecordParm mfThirdServiceRecordParm = new MfThirdServiceRecordParm();
				setObjValue(formriskreportquery, mfThirdServiceRecordParm);
				dataMap = mfThirdServiceRecordFeign.netLoanBlacklistInfo(mfThirdServiceRecordParm);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_QUERY.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
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
	 * 方法描述：(网贷多重借贷查询)
	 *
	 * @return
	 */
	@RequestMapping(value = "/netLoanMultipleLoansAjax")
	@ResponseBody
	public Map<String, Object> netLoanMultipleLoansAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		try {
			FormData formriskreportquery = formService.getFormData("riskreportquery");
			getFormValue(formriskreportquery, getMapByJson(ajaxData));
			if (this.validateFormDataAnchor(formriskreportquery)) {
				MfThirdServiceRecordParm mfThirdServiceRecordParm = new MfThirdServiceRecordParm();
				setObjValue(formriskreportquery, mfThirdServiceRecordParm);
				dataMap = mfThirdServiceRecordFeign.netLoanMultipleLoans(mfThirdServiceRecordParm);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_QUERY.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
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
	 * 方法描述：(行业多重借贷查询)
	 *
	 * @return
	 */
	@RequestMapping(value = "/indusMultipleLoansAjax")
	@ResponseBody
	public Map<String, Object> indusMultipleLoansAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		try {
			FormData formriskreportquery = formService.getFormData("riskreportquery");
			getFormValue(formriskreportquery, getMapByJson(ajaxData));
			if (this.validateFormDataAnchor(formriskreportquery)) {
				MfThirdServiceRecordParm mfThirdServiceRecordParm = new MfThirdServiceRecordParm();
				setObjValue(formriskreportquery, mfThirdServiceRecordParm);
				dataMap = mfThirdServiceRecordFeign.indusMultipleLoans(mfThirdServiceRecordParm);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_QUERY.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
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
	 * 方法描述：(失信被执行人信息查询)
	 *
	 * @return
	 */
	@RequestMapping(value = "/loseCerditBZXRInfoAjax")
	@ResponseBody
	public Map<String, Object> loseCerditBZXRInfoAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		try {
			FormData formriskreportquery = formService.getFormData("riskreportquery");
			getFormValue(formriskreportquery, getMapByJson(ajaxData));
			if (this.validateFormDataAnchor(formriskreportquery)) {
				MfThirdServiceRecordParm mfThirdServiceRecordParm = new MfThirdServiceRecordParm();
				setObjValue(formriskreportquery, mfThirdServiceRecordParm);
				dataMap = mfThirdServiceRecordFeign.loseCerditBZXRInfo(mfThirdServiceRecordParm);

				String httpStrs = new FormBeanUtil().getLoseCerditBZXRInfoHtml(dataMap);
				dataMap.put("httpStrs",httpStrs);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_QUERY.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
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
	 * 方法描述：(民间借贷行为验证 )
	 *
	 * @return
	 */
	@RequestMapping(value = "/folkLoansBehavVerifyAjax")
	@ResponseBody
	public Map<String, Object> folkLoansBehavVerifyAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		try {
			FormData formriskreportquery = formService.getFormData("riskreportquery");
			getFormValue(formriskreportquery, getMapByJson(ajaxData));
			if (this.validateFormDataAnchor(formriskreportquery)) {
				MfThirdServiceRecordParm mfThirdServiceRecordParm = new MfThirdServiceRecordParm();
				setObjValue(formriskreportquery, mfThirdServiceRecordParm);
				dataMap = mfThirdServiceRecordFeign.folkLoansBehavVerify(mfThirdServiceRecordParm);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_QUERY.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
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
	 * 方法描述：(非银体系逾期记录)
	 *
	 * @return
	 */
	@RequestMapping(value = "/notSilverSysOverdueAjax")
	@ResponseBody
	public Map<String, Object> notSilverSysOverdueAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		try {
			FormData formriskreportquery = formService.getFormData("riskreportquery");
			getFormValue(formriskreportquery, getMapByJson(ajaxData));
			if (this.validateFormDataAnchor(formriskreportquery)) {
				MfThirdServiceRecordParm mfThirdServiceRecordParm = new MfThirdServiceRecordParm();
				setObjValue(formriskreportquery, mfThirdServiceRecordParm);
				dataMap = mfThirdServiceRecordFeign.notSilverSysOverdue(mfThirdServiceRecordParm);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_QUERY.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
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
	 * 方法描述：(反欺诈信息查询)
	 *
	 * @return
	 */
	@RequestMapping(value = "/antiFraudInfoQueryAjax")
	@ResponseBody
	public Map<String, Object> antiFraudInfoQueryAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		try {
			FormData formriskreportquery = formService.getFormData("riskreportquery");
			getFormValue(formriskreportquery, getMapByJson(ajaxData));
			if (this.validateFormDataAnchor(formriskreportquery)) {
				MfThirdServiceRecordParm mfThirdServiceRecordParm = new MfThirdServiceRecordParm();
				setObjValue(formriskreportquery, mfThirdServiceRecordParm);
				dataMap = mfThirdServiceRecordFeign.antiFraudInfoQuery(mfThirdServiceRecordParm);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_QUERY.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
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
	 * 方法描述：(信贷申请详情)
	 *
	 * @return
	 */
	@RequestMapping(value = "/creditLoanApplyAjax")
	@ResponseBody
	public Map<String, Object> creditLoanApplyAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		try {
			FormData formriskreportquery = formService.getFormData("riskreportquery");
			getFormValue(formriskreportquery, getMapByJson(ajaxData));
			if (this.validateFormDataAnchor(formriskreportquery)) {
				MfThirdServiceRecordParm mfThirdServiceRecordParm = new MfThirdServiceRecordParm();
				setObjValue(formriskreportquery, mfThirdServiceRecordParm);
				dataMap = mfThirdServiceRecordFeign.creditLoanApply(mfThirdServiceRecordParm);

				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_QUERY.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
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
	 * 方法描述：(逾期欠款查询)
	 *
	 * @return
	 */
	@RequestMapping(value = "/overdueDebtQueryAjax")
	@ResponseBody
	public Map<String, Object> overdueDebtQueryAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		try {
			FormData formriskreportquery = formService.getFormData("riskreportquery");
			getFormValue(formriskreportquery, getMapByJson(ajaxData));
			if (this.validateFormDataAnchor(formriskreportquery)) {
				MfThirdServiceRecordParm mfThirdServiceRecordParm = new MfThirdServiceRecordParm();
				setObjValue(formriskreportquery, mfThirdServiceRecordParm);
				dataMap = mfThirdServiceRecordFeign.overdueDebtQuery(mfThirdServiceRecordParm);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_QUERY.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SUBMIT_CONTENT.getMessage(e.getMessage()));
			throw e;
		}
		return dataMap;
	}
}
