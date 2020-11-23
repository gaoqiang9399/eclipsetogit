package  app.component.cus.cooperating.controller;

import app.component.auth.entity.MfCusCreditApply;
import app.component.authinterface.CreditApplyInterfaceFeign;
import app.component.busviewinterface.BusViewInterfaceFeign;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.cus.cooperating.entity.MfCusCooperativeAgency;
import app.component.cus.cooperating.feign.MfCusCooperativeAgencyFeign;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusTable;
import app.component.cus.entity.MfCusType;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusTableFeign;
import app.component.cus.feign.MfCusTypeFeign;
import app.component.msgconfinterface.MsgConfInterfaceFeign;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.feign.MfBusFincAppFeign;
import app.component.pactinterface.PactInterfaceFeign;
import app.component.pledge.entity.MfBusFund;
import app.component.pledge.entity.MfBusFundDetail;
import app.component.pledge.entity.MfBusFundPurchaseHis;
import app.component.pledge.feign.MfBusFundDetailFeign;
import app.component.pledge.feign.MfBusFundFeign;
import app.component.pledge.feign.MfBusFundPurchaseHisFeign;
import app.component.sys.entity.SysOrg;
import app.component.sys.entity.SysUser;
import app.component.sys.feign.SysOrgFeign;
import app.component.sys.feign.SysUserFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.HttpClientUtil;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.PropertiesUtil;
import cn.mftcc.util.WaterIdUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.google.gson.Gson;
import config.YmlConfig;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
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
 * Title: MfCusCooperativeAgencyAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Thu Jun 02 10:07:33 CST 2016
 **/
@Controller
@RequestMapping("/mfCusCooperativeAgency")
public class MfCusCooperativeAgencyController extends BaseFormBean{
	@Autowired  
	private HttpServletRequest request;
	@Autowired  
	private HttpServletResponse response;
	@Autowired
	private MfBusFincAppFeign mfBusFincAppFeign;
	@Autowired
	private MfCusCooperativeAgencyFeign mfCusCooperativeAgencyFeign;
	@Autowired
	private MfCusTypeFeign mfCusTypeFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfCusTableFeign mfCusTableFeign;
	@Autowired
	private BusViewInterfaceFeign busViewInterfaceFeign;
	@Autowired
	private SysUserFeign sysUserFeign;
	@Autowired
	private SysOrgFeign sysOrgFeign;
	@Autowired
	private MsgConfInterfaceFeign msgConfInterfaceFeign;
	@Autowired
	private PactInterfaceFeign pactInterfaceFeign;
	@Autowired
	private CreditApplyInterfaceFeign creditApplyInterfaceFeign;
	@Autowired
    private YmlConfig ymlConfig;
	@Autowired
	private MfBusFundFeign mfBusFundFeign;
	@Autowired 
	private MfBusFundDetailFeign mfBusFundDetailFeign;
	@Autowired
	private MfBusFundPurchaseHisFeign mfBusFundPurchaseHisFeign;

	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request,response);
		model.addAttribute("query", "");
		return "/component/cus/cooperating/MfCusCooperativeAgency_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(String ajaxData,int pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusCooperativeAgency mfCusCooperativeAgency = new MfCusCooperativeAgency();
		try {
			mfCusCooperativeAgency.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusCooperativeAgency.setCriteriaList(mfCusCooperativeAgency, ajaxData);//我的筛选
			//this.getRoleConditions(mfCusCooperativeAgency,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfCusCooperativeAgency", mfCusCooperativeAgency));
			ipage = mfCusCooperativeAgencyFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findSearchByPageAjax")
	@ResponseBody
	public Map<String, Object> findSearchByPageAjax(String ajaxData,int pageNo,int pageSize) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCooperativeAgency mfCusCooperativeAgency = new MfCusCooperativeAgency();
		try {
			mfCusCooperativeAgency.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusCooperativeAgency.setCriteriaList(mfCusCooperativeAgency, ajaxData);//我的筛选
			//this.getRoleConditions(mfCusCooperativeAgency,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setPageSize(pageSize);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfCusCooperativeAgency", mfCusCooperativeAgency));
			ipage = mfCusCooperativeAgencyFeign.findByPage(ipage);
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
	 * AJAX新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<>();
		try{
			FormService formService = new FormService();
			FormData formcuscoop00002 = formService.getFormData("cuscoop00002");
			getFormValue(formcuscoop00002, getMapByJson(ajaxData));
			if(this.validateFormData(formcuscoop00002)){
				MfCusCooperativeAgency mfCusCooperativeAgency = new MfCusCooperativeAgency();
				setObjValue(formcuscoop00002, mfCusCooperativeAgency);
				if(null != mfCusCooperativeAgency.getSocialCreditCode() && StringUtils.isNotBlank(mfCusCooperativeAgency.getSocialCreditCode())){
					MfCusCooperativeAgency mfCusCooperativeAgencyTemp = mfCusCooperativeAgencyFeign.getBySocialCreditCode(mfCusCooperativeAgency);
					if(null != mfCusCooperativeAgencyTemp){
						dataMap.put("flag", "error");
						dataMap.put("msg","社会信用代码已存在，不能重复添加！");
						return dataMap;
					}
				}
				mfCusCooperativeAgency.setOrgaNo(WaterIdUtil.getWaterId("Age"));
				mfCusCooperativeAgencyFeign.insert(mfCusCooperativeAgency);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * ajax 异步 单个字段或多个字段更新
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		FormData formcuscoop00002 = formService.getFormData("cuscoop00002");
		getFormValue(formcuscoop00002, getMapByJson(ajaxData));
		MfCusCooperativeAgency mfCusCooperativeAgencyJsp = new MfCusCooperativeAgency();
		setObjValue(formcuscoop00002, mfCusCooperativeAgencyJsp);
		MfCusCooperativeAgency mfCusCooperativeAgency = mfCusCooperativeAgencyFeign.getById(mfCusCooperativeAgencyJsp);
		if(mfCusCooperativeAgency!=null){
			try{
				mfCusCooperativeAgency = (MfCusCooperativeAgency)EntityUtil.reflectionSetVal(mfCusCooperativeAgency, mfCusCooperativeAgencyJsp, getMapByJson(ajaxData));
				mfCusCooperativeAgencyFeign.update(mfCusCooperativeAgency);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			}catch(Exception e){
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg",MessageEnum.ERROR_INSERT.getMessage());
				throw e;
			}
		}else{
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
		}
		return dataMap;
	}
	/**
	 * AJAX更新保存
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			FormService formService = new FormService();
			FormData formcuscoop00003 = formService.getFormData("cuscoop00003");
			getFormValue(formcuscoop00003, getMapByJson(ajaxData));
			if(this.validateFormData(formcuscoop00003)){
				MfCusCooperativeAgency mfCusCooperativeAgency = new MfCusCooperativeAgency();
				setObjValue(formcuscoop00003, mfCusCooperativeAgency);
				mfCusCooperativeAgencyFeign.update(mfCusCooperativeAgency);
				dataMap.put("flag", "success");
				dataMap.put("msg",MessageEnum.SUCCEED_UPDATE.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	/**
	 * AJAX获取查看
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String orgaNo) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormService formService = new FormService();
		FormData formcuscoop00002 = formService.getFormData("cuscoop00002");
		MfCusCooperativeAgency mfCusCooperativeAgency = new MfCusCooperativeAgency();
		mfCusCooperativeAgency.setOrgaNo(orgaNo);
		mfCusCooperativeAgency = mfCusCooperativeAgencyFeign.getById(mfCusCooperativeAgency);
		getObjValue(formcuscoop00002, mfCusCooperativeAgency,formData);
		if(mfCusCooperativeAgency!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String orgaNo) throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusCooperativeAgency mfCusCooperativeAgency = new MfCusCooperativeAgency();
		mfCusCooperativeAgency.setOrgaNo(orgaNo);
		try {
			mfCusCooperativeAgencyFeign.delete(mfCusCooperativeAgency);
			dataMap.put("flag", "success");
			dataMap.put("msg",MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * 新增页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/input")
	public String input(Model model) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formcuscoop00002 = formService.getFormData("cuscoop00002");
		model.addAttribute("formcuscoop00002", formcuscoop00002);
		model.addAttribute("query", "");
		return "/component/cus/cooperating/MfCusCooperativeAgency_Insert";
	}
	/***
	 * 新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insert")
	public String insert(Model model) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formcuscoop00002 = formService.getFormData("cuscoop00002");
		 getFormValue(formcuscoop00002);
		 MfCusCooperativeAgency mfCusCooperativeAgency = new MfCusCooperativeAgency();
		 setObjValue(formcuscoop00002, mfCusCooperativeAgency);
		 mfCusCooperativeAgencyFeign.insert(mfCusCooperativeAgency);
		 getObjValue(formcuscoop00002, mfCusCooperativeAgency);
		 this.addActionMessage(model,"保存成功");
		 Ipage ipage =this.getIpage();
		ipage.setParams(this.setIpageParams("mfCusCooperativeAgency", mfCusCooperativeAgency));
		 List<MfCusCooperativeAgency> mfCusCooperativeAgencyList = (List<MfCusCooperativeAgency>)mfCusCooperativeAgencyFeign.findByPage(ipage).getResult();
		 model.addAttribute("formcuscoop00002", formcuscoop00002);
		 model.addAttribute("mfCusCooperativeAgencyList", mfCusCooperativeAgencyList);
		model.addAttribute("query", "");
		 return "/component/cus/cooperating/MfCusCooperativeAgency_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model,String orgaNo) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formcuscoop00003 = formService.getFormData("cuscoop00003");
		 getFormValue(formcuscoop00003);
		 MfCusCooperativeAgency mfCusCooperativeAgency = new MfCusCooperativeAgency();
		mfCusCooperativeAgency.setOrgaNo(orgaNo);
		 mfCusCooperativeAgency = mfCusCooperativeAgencyFeign.getById(mfCusCooperativeAgency);
		 getObjValue(formcuscoop00003, mfCusCooperativeAgency);
		 model.addAttribute("formcuscoop00003", formcuscoop00003);
		model.addAttribute("query", "");
		return "/component/cus/cooperating/MfCusCooperativeAgency_Detail";
	}


	@RequestMapping(value = "/getCusInfoByName")
	@ResponseBody
	public Map<String,Object> getCusInfoByName(String orgaName) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<>();
		try{
			MfCusCooperativeAgency  mfCusCooperativeAgency = mfCusCooperativeAgencyFeign.getCusInfoByName(orgaName);
			if(mfCusCooperativeAgency!=null){
				dataMap.put("flag", "success");
				dataMap.put("data", mfCusCooperativeAgency);
				dataMap.put("msg", "联网核查成功");
			}else {
				dataMap.put("flag", "error");
				dataMap.put("msg", "未查询到信息");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "联网核查失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * 合作机构视角<br>
	 * assurecompany0001<br>
	 * MfCusCustomerAction_getById.action;cusNo-cusNo;onClick-MfCusAssureCompany_List.getDetailPage(this);
	 *
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getCooperativeAgencyView")
	public String getCooperativeAgencyView(Model model, String orgaNo,String opNo, String busEntrance) throws Exception {
		ActionContext.initialize(request, response);
		try {
			//查询合作社信息
			MfCusCooperativeAgency mfCusCooperativeAgency = new MfCusCooperativeAgency();
			mfCusCooperativeAgency.setOrgaNo(orgaNo);
			mfCusCooperativeAgency = mfCusCooperativeAgencyFeign.getById(mfCusCooperativeAgency);
			//查询合作社人数
			MfCusCustomer mfCusCustomer = new MfCusCustomer();
			mfCusCustomer.setOpNo(opNo);
			int cntByOpNo = mfCusCustomerFeign.getCntByOpNo(mfCusCustomer);
			//头部信息显示内容
			Map<String, Object> dataMap = new HashMap<String, Object>();
			SysUser sysUser = new SysUser();
			CodeUtils codeUtils = new CodeUtils();
			sysUser.setOpNo(opNo);
			sysUser = sysUserFeign.getById(sysUser);
			Map<String,String> map = codeUtils.getMapByKeyName("DUTIES");
			sysUser.setJob(map.get(sysUser.getJob()));
			SysOrg sysOrg = new SysOrg();
			sysOrg.setBrNo(sysUser.getBrNo());
			sysOrg = sysOrgFeign.getById(sysOrg);
			sysUser.setBrNo(sysOrg.getBrName());
			//业务经理相关统计信息
			String faUrl = PropertiesUtil.getSysParamsProperty("sys.project.fa.url");
			//String postJson = HttpClientUtil.sendPostJson(opNo, faUrl+"/pactInterface/getMfBusPactByCusmngNo");
			String postJson = HttpClientUtil.sendPostJson(opNo, faUrl+"/pactInterface/getMfBusPactByCusmngNo");
			Gson gson = new Gson();
			dataMap = gson.fromJson(postJson, Map.class);
			String cusNo =orgaNo;
			String generalClass = "cus";
			Map<String, String> parmMap = new HashMap<String, String>();
			//检查是否授信
			String creditAppId = "";
			MfCusCreditApply mfCusCreditApply = new MfCusCreditApply();
			mfCusCreditApply.setCusNo(cusNo);
			String mfCusCreditApplyJson = HttpClientUtil.sendPostJson(gson.toJson(mfCusCreditApply), faUrl+"/creditApplyInterface/getByCusNoAndOrederFirst");
			mfCusCreditApply = gson.fromJson(mfCusCreditApplyJson, MfCusCreditApply.class);
			if(mfCusCreditApply!=null){
				creditAppId = mfCusCreditApply.getCreditAppId();
			}
			//还款预警消息
			MfBusFincApp mfBusFincApp = new MfBusFincApp();
			mfBusFincApp.setOverdueSts("1");
			int overCount = mfBusFincAppFeign.getCount(mfBusFincApp);
			String cusSubTypeName = mfCusCooperativeAgency.getExt4();
			parmMap.put("cusType", mfCusCooperativeAgency.getExt4());
			parmMap.put("cusNo", cusNo);
			parmMap.put("creditAppId", creditAppId);
			parmMap.put("operable", "operable");// 底部显示待完善信息块
			parmMap.put("docParm", "cusNo=" + cusNo + "&relNo=" + cusNo + "&scNo=" + BizPubParm.SCENCE_TYPE_DOC_CUS);
			parmMap.put("baseType", "C");
			Map<String, Object> cusViewMap = busViewInterfaceFeign.getCommonViewMap(generalClass, busEntrance, parmMap);
			if(dataMap==null){
				dataMap = new HashMap<String,Object>();
			}
			dataMap.put("cusNo", cusNo);
			dataMap.put("baseType","C");
			dataMap.putAll(cusViewMap);
			dataMap.put("cntByOpNo",cntByOpNo);
			dataMap.put("overCount",overCount);
			dataMap.put("cusType", mfCusCooperativeAgency.getExt4());
			dataMap.put("opNo", opNo);
			model.addAttribute("param", parmMap);
			model.addAttribute("sysUser",sysUser);
			model.addAttribute("busEntrance", busEntrance);
			model.addAttribute("mfCusCooperativeAgency", mfCusCooperativeAgency);
			model.addAttribute("creditAppId", creditAppId);
			model.addAttribute("dataMap", dataMap);
			model.addAttribute("query", "");
			model.addAttribute("cusNo", cusNo);
			model.addAttribute("cusType", mfCusCooperativeAgency.getExt4());
			model.addAttribute("orgaNo", orgaNo);

			JsonTableUtil jtu ;
			//基金开户明细
			MfBusFund mfBusFund = new MfBusFund();
			mfBusFund.setCusNo(cusNo);
			List<MfBusFund> mfBusFundList = mfBusFundFeign.getMfBusFundList(mfBusFund);
			jtu = new JsonTableUtil();
			String addMfBusFundListHtml = jtu.getJsonStr("tablemfbusfund0001","thirdTableTag",mfBusFundList, null,true);
			model.addAttribute("addMfBusFundListHtml", addMfBusFundListHtml);
			model.addAttribute("mfBusFundList", mfBusFundList.size());

			String mfBusFundIdStr = "";
			for(int i = 0; i < mfBusFundList.size(); i++){
				MfBusFund mfBusFundQuery = mfBusFundList.get(i);
				if(i == mfBusFundList.size()-1){
					mfBusFundIdStr = mfBusFundIdStr + "'" + mfBusFundQuery.getId() + "'";
				}else{
					mfBusFundIdStr = mfBusFundIdStr + "'" + mfBusFundQuery.getId() + "'" + ",";
				}
			}
			//基金认购明细
			MfBusFundDetail mfBusFundDetail = new MfBusFundDetail();
			mfBusFundDetail.setFk(mfBusFundIdStr);
			List<MfBusFundDetail> mfBusFundDetailList = mfBusFundDetailFeign.getMfBusFundDetailList(mfBusFundDetail);
			jtu = new JsonTableUtil();
			String addMfBusFundDetailListHtml = jtu.getJsonStr("tablemfbusfunddetail0001","thirdTableTag",mfBusFundDetailList, null,true);
			model.addAttribute("addMfBusFundDetailListHtml", addMfBusFundDetailListHtml);
			model.addAttribute("mfBusFundDetailList", mfBusFundDetailList.size());

		} catch (Exception e) {
			throw e;
		}
		return "/component/cus/commonview/MfCusCustomer_CooperativeView";
	}
	/**
	 * 担保公司视角<br>
	 * assurecompany0001<br>
	 * MfCusCustomerAction_getById.action;cusNo-cusNo;onClick-MfCusAssureCompany_List.getDetailPage(this);
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cooperativeAgencyView")
	public String cooperativeAgencyView(Model model, String orgaNo, String busEntrance) throws Exception {
		ActionContext.initialize(request, response);
		try {
			String cusNo =orgaNo;
			MfCusCustomer mfCusCustomer = new MfCusCustomer();
			mfCusCustomer.setCusNo(cusNo);
			mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
			MfCusType mfCusType = new MfCusType();
			mfCusType.setTypeNo(mfCusCustomer.getCusType());
			mfCusType = mfCusTypeFeign.getById(mfCusType);
			String cusSubTypeName = new CodeUtils().getMapByKeyName("CUS_TYPE").get(mfCusCustomer.getCusType());
			String generalClass = "cus";
			// String busClass = mfCusCustomer.getCusBaseType();

			Map<String, String> parmMap = new HashMap<String, String>();
			String baseType = mfCusType.getBaseType();
			parmMap.put("baseType", baseType);
			parmMap.put("cusType", mfCusCustomer.getCusType());
			// parmMap.put("busClass", busClass);
			parmMap.put("cusNo", cusNo);
			parmMap.put("operable", "operable");// 底部显示待完善信息块
			parmMap.put("cusSubTypeName", cusSubTypeName);
			parmMap.put("docParm", "cusNo=" + cusNo + "&relNo=" + cusNo + "&scNo=" + BizPubParm.SCENCE_TYPE_DOC_CUS);
			Map<String, Object> cusViewMap = busViewInterfaceFeign.getCommonViewMap(generalClass, busEntrance, parmMap);

			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("cusNo", cusNo);
			dataMap.put("baseType", baseType);
			dataMap.putAll(cusViewMap);
			model.addAttribute("dataMap", dataMap);
			model.addAttribute("param", parmMap);
			model.addAttribute("cusNo", cusNo);
			model.addAttribute("orgaNo", orgaNo);
			model.addAttribute("cusType", mfCusCustomer.getCusType());
			model.addAttribute("baseType", baseType);
			model.addAttribute("query", "");
		} catch (Exception e) {
			throw e;
		}
		return "/component/cus/commonview/MfCusCustomer_ComView";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(Model model,String orgaNo) throws Exception {
		ActionContext.initialize(request,response);
		MfCusCooperativeAgency mfCusCooperativeAgency = new MfCusCooperativeAgency();
		mfCusCooperativeAgency.setOrgaNo(orgaNo);
		mfCusCooperativeAgencyFeign.delete(mfCusCooperativeAgency);
		return getListPage(model);
	}
	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	public void validateInsert() throws Exception{
		 ActionContext.initialize(request,response);;
		 FormService formService = new FormService();
		FormData formcuscoop00002 = formService.getFormData("cuscoop00002");
		 getFormValue(formcuscoop00002);
		 boolean validateFlag = this.validateFormData(formcuscoop00002);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	public void validateUpdate() throws Exception{
		 ActionContext.initialize(request,response);;
		 FormService formService = new FormService();
		 FormData formcuscoop00002 = formService.getFormData("cuscoop00002");
		 getFormValue(formcuscoop00002);
		 boolean validateFlag = this.validateFormData(formcuscoop00002);
	}
	
	@RequestMapping(value = "/inputOpenFundAccount")
	public String inputOpenFundAccount(Model model,String cusNo) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formmfbusfund0001 = formService.getFormData("mfbusfund0001");
		getFormValue(formmfbusfund0001);
		
		MfBusFund mfBusFund = new MfBusFund();
		mfBusFund.setCusNo(cusNo);
		getObjValue(formmfbusfund0001,mfBusFund);
		
		MfCusCooperativeAgency mfCusCooperativeAgency = new MfCusCooperativeAgency();
		mfCusCooperativeAgency.setOrgaNo(cusNo);
		mfCusCooperativeAgency = mfCusCooperativeAgencyFeign.getById(mfCusCooperativeAgency);
		String cusName = "";
		String cusTel = "";
		String address = "";
		String idNum = "";
		if(mfCusCooperativeAgency != null) {
			cusName = mfCusCooperativeAgency.getOrgaName();
			address = mfCusCooperativeAgency.getExt1();
			cusTel = mfCusCooperativeAgency.getExt2();
			idNum = mfCusCooperativeAgency.getLegalIdNum();
		}
		model.addAttribute("formmfbusfund0001", formmfbusfund0001);
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("cusName", cusName);
		model.addAttribute("cusTel", cusTel);
		model.addAttribute("address", address);
		model.addAttribute("idNum", idNum);
		model.addAttribute("query", "");
		return "component/cus/commonview/MfBusFund_input";
	}
	
	@RequestMapping(value = "/inputFundPurchaseInfo")
	public String inputFundPurchaseInfo(Model model,String cusNo) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formmfbusfunddetail0001 = formService.getFormData("mfbusfunddetail0001");
		getFormValue(formmfbusfunddetail0001);
		
		MfBusFundDetail mfBusFundDetail = new MfBusFundDetail();
		getObjValue(formmfbusfunddetail0001,mfBusFundDetail);
		
		MfBusFund mfBusFund = new MfBusFund();
		mfBusFund.setCusNo(cusNo);
		List<MfBusFund> mfBusFundList = mfBusFundFeign.getMfBusFundList(mfBusFund);
		
		JSONObject json = new JSONObject();
		JSONArray newfundCompanyListArray = new JSONArray();
		JSONObject clearFundCompanyObject = null;
		for(int i = 0;i < mfBusFundList.size(); i++){
			clearFundCompanyObject = new JSONObject();
			clearFundCompanyObject.put("id", mfBusFundList.get(i).getFundCompanyId());
			clearFundCompanyObject.put("name", mfBusFundList.get(i).getFundCompanyName());
			newfundCompanyListArray.add(clearFundCompanyObject);
		}
		json.put("fundCompanyName", newfundCompanyListArray);
		
		model.addAttribute("formmfbusfunddetail0001", formmfbusfunddetail0001);
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("fundCompanyNameArray", json.toString());
		model.addAttribute("query", "");
		return "component/cus/commonview/MfBusFundDetail_input";
	}
	
	@RequestMapping(value = "/appendMfBusFundDetail")
	public String appendMfBusFundDetail(Model model,String mfBusFundDetailId) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formmfbusfundpurchasehis0001 = formService.getFormData("mfbusfundpurchasehis0001");
		getFormValue(formmfbusfundpurchasehis0001);
		
		MfBusFundPurchaseHis mfBusFundPurchaseHis = new MfBusFundPurchaseHis();
		getObjValue(formmfbusfundpurchasehis0001,mfBusFundPurchaseHis);
		
		MfBusFundDetail mfBusFundDetail = new MfBusFundDetail();
		mfBusFundDetail.setId(mfBusFundDetailId);
		mfBusFundDetail = mfBusFundDetailFeign.getMfBusFundDetailById(mfBusFundDetail);
		
		String fundType = new CodeUtils().getMapByKeyName("FUND_TYPE").get(mfBusFundDetail.getFundType());
		
		model.addAttribute("formmfbusfundpurchasehis0001", formmfbusfundpurchasehis0001);
		model.addAttribute("fundCode", mfBusFundDetail.getFundCode());
		model.addAttribute("fundSimpleName", mfBusFundDetail.getFundSimpleName());
		model.addAttribute("fundType", fundType);
		model.addAttribute("bankName", mfBusFundDetail.getBankName());
		model.addAttribute("mfBusFundDetailId", mfBusFundDetailId);
		model.addAttribute("query", "");
		return "component/cus/commonview/MfBusFundPurchaseHis_input";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/insertMfBusFundPurchaseHisAjax")
	@ResponseBody
	public Map<String, Object> insertMfBusFundPurchaseHisAjax(String ajaxData,String mfBusFundDetailId) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String ,Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String,Object> map=getMapByJson(ajaxData) == null ? new HashMap() : getMapByJson(ajaxData);
			String formId=String.valueOf(map.get("formId"));
			FormData formmfbusfundpurchasehis0001 = formService.getFormData(formId);
			getFormValue(formmfbusfundpurchasehis0001, getMapByJson(ajaxData));
			
			if(this.validateFormData(formmfbusfundpurchasehis0001)) {
				MfBusFundPurchaseHis mfBusFundPurchaseHis = new MfBusFundPurchaseHis();
				setObjValue(formmfbusfundpurchasehis0001,mfBusFundPurchaseHis);
				
				mfBusFundPurchaseHisFeign.insert(mfBusFundPurchaseHis);
				
				MfBusFundDetail mfBusFundDetail = new MfBusFundDetail();
				mfBusFundDetail.setId(mfBusFundPurchaseHis.getFk());
				mfBusFundDetail = mfBusFundDetailFeign.getMfBusFundDetailById(mfBusFundDetail);
				
				mfBusFundDetail.setApplyPurchaseRate(mfBusFundPurchaseHis.getApplyPurchaseRate());
				mfBusFundDetail.setApplyPurchaseAmount(MathExtend.add(mfBusFundDetail.getApplyPurchaseAmount(), mfBusFundPurchaseHis.getApplyPurchaseAmount()));
				mfBusFundDetail.setApplyPurchaseQuantity(mfBusFundDetail.getApplyPurchaseQuantity()+mfBusFundPurchaseHis.getApplyPurchaseQuantity());
				mfBusFundDetail.setDealQuantity(mfBusFundDetail.getDealQuantity()+mfBusFundPurchaseHis.getDealQuantity());
				
				if(!mfBusFundDetail.getPledgeStatus().equals(BizPubParm.PLEDGE_STATUS_01)) {
					if(mfBusFundDetail.getDealQuantity() - mfBusFundDetail.getPledgeQuantity() > 0) {//部分质押
						mfBusFundDetail.setPledgeStatus(BizPubParm.PLEDGE_STATUS_03);
					}
					if(mfBusFundDetail.getDealQuantity() - mfBusFundDetail.getPledgeQuantity() == 0) {//全部质押
						mfBusFundDetail.setPledgeStatus(BizPubParm.PLEDGE_STATUS_02);
					}
				}
				
				if(!mfBusFundDetail.getRedeemStatus().equals(BizPubParm.REDEEM_STATUS_01)) {//原纪录的状态不是"未赎回"状态时,追加基金后,状态更新为"部分赎回"
					mfBusFundDetail.setRedeemStatus(BizPubParm.REDEEM_STATUS_03);
				}
				
				mfBusFundDetailFeign.update(mfBusFundDetail);
				
				dataMap.put("flag", "success");
				dataMap.put("msg", "追加成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
			
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
		}
		return dataMap;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/insertMfBusFundDetailAjax")
	@ResponseBody
	public Map<String ,Object> insertMfBusFundDetailAjax(String ajaxData,String cusNo,String mfBusFundId) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String ,Object> dataMap = new HashMap<String, Object>();
		try{
			Map<String,Object> map=getMapByJson(ajaxData) == null ? new HashMap() : getMapByJson(ajaxData);
			String formId=String.valueOf(map.get("formId"));
			FormData formmfbusfunddetail0001 = formService.getFormData(formId);
			getFormValue(formmfbusfunddetail0001, getMapByJson(ajaxData));
			
			String fundCompanyId = (String)map.get("fundCompanyId");
			//String fundCompanyName = (String)map.get("fundCompanyName");
			
			if(this.validateFormData(formmfbusfunddetail0001)){
				MfBusFundDetail mfBusFundDetail = new MfBusFundDetail();
				setObjValue(formmfbusfunddetail0001,mfBusFundDetail);
				
				MfBusFund mfBusFund = new MfBusFund();
				mfBusFund.setFundCompanyId(fundCompanyId);
				List<MfBusFund> mfBusFundList = mfBusFundFeign.getMfBusFundList(mfBusFund);
				mfBusFund = mfBusFundList.get(0);
				
				MfBusFundDetail query = new MfBusFundDetail();
				query.setFk(mfBusFund.getId());
				query.setFundCode(mfBusFundDetail.getFundCode());
				List<MfBusFundDetail> list = mfBusFundDetailFeign.getMfBusFundDetailList(mfBusFundDetail);
				if(list != null && list.size() > 0){
					dataMap.put("id", "");
					dataMap.put("flag", "error");
					dataMap.put("msg", "同一只基金不可以重复认购");
				}else{
					if(mfBusFundDetail.getPledgeProp() == 0.00) {
						mfBusFundDetail.setPledgeProp(0.80);//默认质押比例80%
					}
					mfBusFundDetail.setFk(mfBusFund.getId());
					mfBusFundDetail = mfBusFundDetailFeign.insert(mfBusFundDetail);
					
					MfBusFundPurchaseHis mfBusFundPurchaseHis = new MfBusFundPurchaseHis();
					mfBusFundPurchaseHis.setFk(mfBusFundDetail.getId());
					mfBusFundPurchaseHis.setApplyPurchaseRate(mfBusFundDetail.getApplyPurchaseRate());
					mfBusFundPurchaseHis.setApplyPurchaseAmount(mfBusFundDetail.getApplyPurchaseAmount());
					mfBusFundPurchaseHis.setApplyPurchaseQuantity(mfBusFundDetail.getApplyPurchaseQuantity());
					mfBusFundPurchaseHis.setDealQuantity(mfBusFundDetail.getDealQuantity());
					mfBusFundPurchaseHis = mfBusFundPurchaseHisFeign.insert(mfBusFundPurchaseHis);
					
					dataMap.put("mfBusFundDetailId", mfBusFundDetail.getId());
					dataMap.put("mfBusFundDetailFk", mfBusFundDetail.getFk());
					dataMap.put("mfBusFundPurchaseHisId", mfBusFundPurchaseHis.getId());
					dataMap.put("flag", "success");
					dataMap.put("msg", "新增成功");
				}
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
			
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
		}
		return dataMap;
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/insertMfBusFundAjax")
	@ResponseBody
	public Map<String ,Object> insertMfBusFundAjax(String ajaxData,String cusNo) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String ,Object> dataMap = new HashMap<String, Object>();
		try{
			Map<String,Object> map=getMapByJson(ajaxData) == null ? new HashMap() : getMapByJson(ajaxData);
			String formId=String.valueOf(map.get("formId"));
			FormData formmfbusfund0001 = formService.getFormData(formId);
			getFormValue(formmfbusfund0001, getMapByJson(ajaxData));
			
			if(this.validateFormData(formmfbusfund0001)){
				MfBusFund mfBusFund = new MfBusFund();
				setObjValue(formmfbusfund0001,mfBusFund);
				
				MfBusFund query = new MfBusFund();
				query.setCusNo(cusNo);
				query.setFundCompanyId(mfBusFund.getFundCompanyId());
				List<MfBusFund> list = mfBusFundFeign.getMfBusFundList(query);
				if(list != null && list.size() > 0){
					dataMap.put("id", "");
					dataMap.put("flag", "error");
					dataMap.put("msg", "不可以在同一基金公司重复开户");
				}else{
					mfBusFund = mfBusFundFeign.insert(mfBusFund);
					dataMap.put("mfBusFundId", mfBusFund.getId());
					dataMap.put("flag", "success");
					dataMap.put("msg", "新增成功");
				}
				
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
			
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
		}
		return dataMap;
	}
	
	@RequestMapping(value = "/deleteMfBusFundAjax")
	@ResponseBody
	public Map<String,Object> deleteMfBusFundAjax(String mfBusFundId) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		MfBusFund mfBusFund = new MfBusFund();
		mfBusFund.setId(mfBusFundId);
		try{
			
			MfBusFundDetail mfBusFundDetail = new MfBusFundDetail();
			mfBusFundDetail.setFk("'"+mfBusFundId+"'");
			List<MfBusFundDetail> mfBusFundDetailList = mfBusFundDetailFeign.getMfBusFundDetailList(mfBusFundDetail);
			if(mfBusFundDetailList != null && mfBusFundDetailList.size()>0) {
				dataMap.put("flag", "success");
				dataMap.put("msg", "该机构下存在基金明细，不可以删除");
			}else {
				mfBusFundFeign.delete(mfBusFund);
				dataMap.put("flag", "success");
				dataMap.put("msg", "删除成功");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "删除失败");
			throw e;
		}
		return dataMap;
	}
	
	@RequestMapping(value = "/deleteMfBusFundDetailAjax")
	@ResponseBody
	public Map<String,Object> deleteMfBusFundDetailAjax(String mfBusFundDetailId) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		MfBusFundDetail mfBusFundDetail = new MfBusFundDetail();
		mfBusFundDetail.setId(mfBusFundDetailId);
		try{
			mfBusFundDetailFeign.delete(mfBusFundDetail);
			dataMap.put("flag", "success");
			dataMap.put("msg", "删除成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "删除失败");
			throw e;
		}
		return dataMap;
	}

	@RequestMapping(value = "/getMfBusFundById")
	public String getMfBusFundById(Model model,String mfBusFundId,String cusNo) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formmfbusfund0001 = formService.getFormData("mfbusfund0001");
		getFormValue(formmfbusfund0001);
		
		MfBusFund mfBusFund = new MfBusFund();
		mfBusFund.setId(mfBusFundId);
		mfBusFund = mfBusFundFeign.getMfBusFundById(mfBusFund);
		getObjValue(formmfbusfund0001, mfBusFund);
		
		//dataMap.put("mfBusFundId", mfBusFund.getId());
		
		model.addAttribute("coopCusNo", "cusNo");
		model.addAttribute("mfBusFundId" ,mfBusFund.getId());
		model.addAttribute("formmfbusfund0001", formmfbusfund0001);
		model.addAttribute("query", "");

		return "component/cus/commonview/MfBusFund_detail";
	}
	
	@RequestMapping(value = "/getMfBusFundDetailById")
	public String getMfBusFundDetailById(Model model,String mfBusFundDetailId,String cusNo) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formmfbusfunddetail0003 = formService.getFormData("mfbusfunddetail0003");
		getFormValue(formmfbusfunddetail0003);
		
		MfBusFundDetail mfBusFundDetail = new MfBusFundDetail();
		mfBusFundDetail.setId(mfBusFundDetailId);
		mfBusFundDetail = mfBusFundDetailFeign.getMfBusFundDetailById(mfBusFundDetail);
		
		
		MfBusFund mfBusFund = new MfBusFund();
		mfBusFund.setId(mfBusFundDetail.getFk());
		List<MfBusFund> mfBusFundList = mfBusFundFeign.getMfBusFundList(mfBusFund);
		cusNo = mfBusFundList.get(0).getCusNo();
		String fundCompanyId = mfBusFundList.get(0).getFundCompanyId();
		String fundCompanyName = mfBusFundList.get(0).getFundCompanyName();
		mfBusFundDetail.setFundCompanyId(fundCompanyId);
		mfBusFundDetail.setFundCompanyName(fundCompanyName);
		
		mfBusFund = new MfBusFund();
		mfBusFund.setCusNo(cusNo);
		mfBusFundList = mfBusFundFeign.getMfBusFundList(mfBusFund);
		
		getObjValue(formmfbusfunddetail0003,mfBusFundDetail);
		
		JSONObject json = new JSONObject();
		JSONArray newfundCompanyListArray = new JSONArray();
		JSONObject clearFundCompanyObject = null;
		for(int i = 0;i < mfBusFundList.size(); i++){
			clearFundCompanyObject = new JSONObject();
			clearFundCompanyObject.put("id", mfBusFundList.get(i).getFundCompanyId());
			clearFundCompanyObject.put("name", mfBusFundList.get(i).getFundCompanyName());
			newfundCompanyListArray.add(clearFundCompanyObject);
		}
		json.put("fundCompanyName", newfundCompanyListArray);
		
		//dataMap.put("mfBusFundDetailId", mfBusFundDetail.getId());
		
		model.addAttribute("coopCusNo", "cusNo");
		model.addAttribute("fundCompanyNameArray", json.toString());
		model.addAttribute("mfBusFundDetailId" ,mfBusFundDetail.getId());
		model.addAttribute("formmfbusfunddetail0003", formmfbusfunddetail0003);
		model.addAttribute("fundCompanyId", fundCompanyId);
		model.addAttribute("fundCompanyName", fundCompanyName);
		model.addAttribute("query", "");

		return "component/cus/commonview/MfBusFundDetail_detail";
	}

	@RequestMapping(value = "/getMfBusFundRedeemDetailById")
	public String getMfBusFundRedeemDetailById(Model model,String mfBusFundDetailId,String cusNo) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formmfbusfunddetail0002 = formService.getFormData("mfbusfunddetail0002");
		getFormValue(formmfbusfunddetail0002);

		MfBusFundDetail mfBusFundDetail = new MfBusFundDetail();
		mfBusFundDetail.setId(mfBusFundDetailId);
		mfBusFundDetail = mfBusFundDetailFeign.getMfBusFundDetailById(mfBusFundDetail);


		MfBusFund mfBusFund = new MfBusFund();
		mfBusFund.setId(mfBusFundDetail.getFk());
		List<MfBusFund> mfBusFundList = mfBusFundFeign.getMfBusFundList(mfBusFund);
		cusNo = mfBusFundList.get(0).getCusNo();
		String fundCompanyId = mfBusFundList.get(0).getFundCompanyId();
		String fundCompanyName = mfBusFundList.get(0).getFundCompanyName();
		mfBusFundDetail.setFundCompanyId(fundCompanyId);
		mfBusFundDetail.setFundCompanyName(fundCompanyName);

		mfBusFund = new MfBusFund();
		mfBusFund.setCusNo(cusNo);
		mfBusFundList = mfBusFundFeign.getMfBusFundList(mfBusFund);

		getObjValue(formmfbusfunddetail0002,mfBusFundDetail);

		JSONObject json = new JSONObject();
		JSONArray newfundCompanyListArray = new JSONArray();
		JSONObject clearFundCompanyObject = null;
		for(int i = 0;i < mfBusFundList.size(); i++){
			clearFundCompanyObject = new JSONObject();
			clearFundCompanyObject.put("id", mfBusFundList.get(i).getFundCompanyId());
			clearFundCompanyObject.put("name", mfBusFundList.get(i).getFundCompanyName());
			newfundCompanyListArray.add(clearFundCompanyObject);
		}
		json.put("fundCompanyName", newfundCompanyListArray);

		//dataMap.put("mfBusFundDetailId", mfBusFundDetail.getId());

		model.addAttribute("coopCusNo", "cusNo");
		model.addAttribute("fundCompanyNameArray", json.toString());
		model.addAttribute("mfBusFundDetailId" ,mfBusFundDetail.getId());
		model.addAttribute("formmfbusfunddetail0002", formmfbusfunddetail0002);
		model.addAttribute("fundCompanyId", fundCompanyId);
		model.addAttribute("fundCompanyName", fundCompanyName);
		model.addAttribute("query", "");

		return "component/cus/commonview/MfBusFundDetail_redeem";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/updateMfBusFundByIdAjax")
	@ResponseBody
	public Map<String ,Object> updateMfBusFundByIdAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String ,Object> dataMap = new HashMap<String, Object>();
		try{
			Map<String,Object> map=getMapByJson(ajaxData)==null?new HashMap():getMapByJson(ajaxData);
			String formId=String.valueOf(map.get("formId"));
			FormData formmfbusfund0001 = formService.getFormData(formId);
			getFormValue(formmfbusfund0001, getMapByJson(ajaxData));
			
			if(this.validateFormData(formmfbusfund0001)){
				MfBusFund mfBusFund = new MfBusFund();
				setObjValue(formmfbusfund0001, mfBusFund);
				mfBusFundFeign.update(mfBusFund);
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
			throw e;
		}
		return dataMap;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/updateMfBusFundDetailByIdAjax")
	@ResponseBody
	public Map<String ,Object> updateMfBusFundDetailByIdAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String ,Object> dataMap = new HashMap<String, Object>();
		try{
			Map<String,Object> map=getMapByJson(ajaxData)==null?new HashMap():getMapByJson(ajaxData);
			String formId=String.valueOf(map.get("formId"));
			FormData formmfbusfunddetail0001 = formService.getFormData(formId);
			getFormValue(formmfbusfunddetail0001, getMapByJson(ajaxData));
			
			if(this.validateFormData(formmfbusfunddetail0001)){
				MfBusFundDetail mfBusFundDetail = new MfBusFundDetail();
				setObjValue(formmfbusfunddetail0001, mfBusFundDetail);
				mfBusFundDetailFeign.update(mfBusFundDetail);
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
			throw e;
		}
		return dataMap;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/updateMfBusFundRedeemDetailByIdAjax")
	@ResponseBody
	public Map<String ,Object> updateMfBusFundRedeemDetailByIdAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String ,Object> dataMap = new HashMap<String, Object>();
		try{
			Map<String,Object> map=getMapByJson(ajaxData)==null?new HashMap():getMapByJson(ajaxData);
			String formId=String.valueOf(map.get("formId"));
			FormData formmfbusfunddetail0002 = formService.getFormData(formId);
			getFormValue(formmfbusfunddetail0002, getMapByJson(ajaxData));
			
			if(this.validateFormData(formmfbusfunddetail0002)){
				MfBusFundDetail mfBusFundDetail = new MfBusFundDetail();
				setObjValue(formmfbusfunddetail0002, mfBusFundDetail);
				
				MfBusFundDetail query = new MfBusFundDetail();
				query.setId(mfBusFundDetail.getId());
				query = mfBusFundDetailFeign.getMfBusFundDetailById(query);
				
				int queryRedeemQuantity = query.getRedeemQuantity() == null ? 0 : query.getRedeemQuantity().intValue();
				
				if(mfBusFundDetail.getApplyPurchaseQuantity().intValue() == (mfBusFundDetail.getRedeemQuantity().intValue() + queryRedeemQuantity)) {
					mfBusFundDetail.setRedeemStatus(BizPubParm.REDEEM_STATUS_02);
				}
				if(mfBusFundDetail.getApplyPurchaseQuantity().intValue() - (mfBusFundDetail.getRedeemQuantity().intValue() + queryRedeemQuantity) > 0) {
					mfBusFundDetail.setRedeemStatus(BizPubParm.REDEEM_STATUS_03);
				}
				
				Double redeemAmount = mfBusFundDetail.getRedeemAmount();
				Double queryRedeemAmount = query.getRedeemAmount() == null ? 0.00 : query.getRedeemAmount();
				mfBusFundDetail.setRedeemAmount(MathExtend.add(redeemAmount, queryRedeemAmount));
				int redeemQuantity = mfBusFundDetail.getRedeemQuantity().intValue();
				mfBusFundDetail.setRedeemQuantity(mfBusFundDetail.getRedeemQuantity().intValue() + queryRedeemQuantity);
				
				mfBusFundDetailFeign.update(mfBusFundDetail);

				dataMap.put("flag", "success");
				dataMap.put("msg", "赎回成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
			
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}
	
	@RequestMapping(value = "/getMfBusFundListHtmlByCusNoAjax")
	@ResponseBody
	public Map<String ,Object> getMfBusFundListHtmlByCusNoAjax(String cusNo) throws Exception {
		ActionContext.initialize(request,response);
		Map<String ,Object> dataMap = new HashMap<String, Object>();
		try{
			JsonTableUtil jtu ;
			//基金开户明细
			MfBusFund mfBusFund = new MfBusFund();
			mfBusFund.setCusNo(cusNo);
			List<MfBusFund> mfBusFundList = mfBusFundFeign.getMfBusFundList(mfBusFund);
			jtu = new JsonTableUtil();
			String mfBusFundListHtml = jtu.getJsonStr("tablemfbusfund0001","thirdTableTag",mfBusFundList, null,true);
			dataMap.put("flag", "success");
			dataMap.put("mfBusFundListHtml", mfBusFundListHtml);
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "查询失败");
			throw e;
		}
		return dataMap;
	}
	
	@RequestMapping(value = "/getMfBusFundDetailListHtmlByCusNoAjax")
	@ResponseBody
	public Map<String ,Object> getMfBusFundListDetailHtmlByCusNoAjax(String cusNo) throws Exception {
		ActionContext.initialize(request,response);
		Map<String ,Object> dataMap = new HashMap<String, Object>();
		try{
			JsonTableUtil jtu ;
			//基金开户明细
			MfBusFund mfBusFund = new MfBusFund();
			mfBusFund.setCusNo(cusNo);
			List<MfBusFund> mfBusFundList = mfBusFundFeign.getMfBusFundList(mfBusFund);
			jtu = new JsonTableUtil();
			String mfBusFundIdStr = "";
			for(int i = 0; i < mfBusFundList.size(); i++){
				MfBusFund mfBusFundQuery = mfBusFundList.get(i);
				if(i == mfBusFundList.size()-1){
					mfBusFundIdStr = mfBusFundIdStr + "'" + mfBusFundQuery.getId() + "'";
				}else{
					mfBusFundIdStr = mfBusFundIdStr + "'" + mfBusFundQuery.getId() + "'" + ",";
				}
			}
			//基金认购明细
			MfBusFundDetail mfBusFundDetail = new MfBusFundDetail();
			mfBusFundDetail.setFk(mfBusFundIdStr);
			List<MfBusFundDetail> mfBusFundDetailList = mfBusFundDetailFeign.getMfBusFundDetailList(mfBusFundDetail);
			jtu = new JsonTableUtil();
			String mfBusFundDetailListHtml = jtu.getJsonStr("tablemfbusfunddetail0001","thirdTableTag",mfBusFundDetailList, null,true);
			dataMap.put("flag", "success");
			dataMap.put("mfBusFundDetailListHtml", mfBusFundDetailListHtml);
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "查询失败");
			throw e;
		}
		return dataMap;
	}
	@RequestMapping(value = "/checkIdNumForCoopAgencyAjax")
	@ResponseBody
	public Map<String ,Object> checkIdNumForCoopAgencyAjax(String legalIdNum) throws Exception {
		ActionContext.initialize(request,response);
		Map<String ,Object> dataMap = new HashMap<String, Object>();
		MfCusCooperativeAgency mfCusCooperativeAgency = new MfCusCooperativeAgency();
		try{
			mfCusCooperativeAgency.setLegalIdNum(legalIdNum);
			mfCusCooperativeAgency = mfCusCooperativeAgencyFeign.getBylegalIdNum(mfCusCooperativeAgency);
			if (mfCusCooperativeAgency != null){
				dataMap.put("mfCusCooperativeAgency",mfCusCooperativeAgency);
				dataMap.put("flag","success");
				dataMap.put("msg","查询成功");
			}
		}catch (Exception e ){
		e.printStackTrace();
			dataMap.put("flag","error");
			dataMap.put("msg","查询失败");
		}
		return dataMap;
	}
}
