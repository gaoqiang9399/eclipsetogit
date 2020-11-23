package  app.component.invoicemanage.controller;

import app.base.User;
import app.component.app.entity.MfBusApply;
import app.component.app.entity.MfBusApplyHis;
import app.component.app.feign.MfBusApplyFeign;
import app.component.app.feign.MfBusApplyHisFeign;
import app.component.calc.core.entity.MfRepayHistory;
import app.component.calc.core.entity.MfRepayPlan;
import app.component.calc.core.feign.MfRepayHistoryFeign;
import app.component.calccoreinterface.CalcRepaymentInterfaceFeign;
import app.component.clean.entity.MfDataClean;
import app.component.collateral.entity.PledgeBaseInfoBill;
import app.component.common.BizPubParm;
import app.component.cus.entity.MfCusBankAccManage;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.feign.MfCusBankAccManageFeign;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.infochange.entity.MfCusInfoChange;
import app.component.invoicemanage.entity.MfBusCancelInvoicemanage;
import app.component.invoicemanage.entity.MfBusInvoicemanage;
import app.component.invoicemanage.feign.MfBusInvoicemanageFeign;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.entity.MfBusFincAppHis;
import app.component.pact.feign.MfBusFincAppFeign;
import app.component.wkf.entity.Result;
import app.component.wkfinterface.WkfInterfaceFeign;
import app.tech.oscache.CodeUtils;
import app.tech.upload.FeignSpringFormEncoder;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.WaterIdUtil;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.PropertiesUtil;
import com.core.domain.screen.FormData;
import com.core.domain.screen.OptionsList;
import com.core.report.ExpExclUtil;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.dhcc.workflow.pvm.internal.task.TaskImpl;
import com.google.gson.Gson;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.alibaba.fastjson.JSON;
import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;

/**
 * Title: MfBusInvoicemanageAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Mon Jul 09 15:00:26 CST 2018
 **/
@Controller
@RequestMapping("/mfBusInvoicemanage")
public class MfBusInvoicemanageController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfBusInvoicemanageFeign mfBusInvoicemanageFeign;
	@Autowired
	private MfBusFincAppFeign mfBusFincAppFeign;
	@Autowired
	private CalcRepaymentInterfaceFeign calcRepaymentInterfaceFeign;

	@Autowired
	private app.component.pact.repayplan.feign.MfRepayPlanFeign mfRepayPlanFeign;// 注入还款计划
	@Autowired
	private MfCusBankAccManageFeign mfCusBankAccManageFeign;
	@Autowired
	private MfBusApplyHisFeign mfBusApplyHisFeign;
	@Autowired
	private MfRepayHistoryFeign mfRepayHistoryFeign;
	@Autowired
	private WkfInterfaceFeign wkfInterfaceFeign;

	/**
	 * 列表打开页面请求
	 * 打开发票管理页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage(Model model, String fincId) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("fincId",fincId);
		return "/component/invoicemanage/MfBusInvoicemanage_List";
	}
	/***
	 * 列表数据查询
	 * 获取发票列表
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
											  String ajaxData, String cusNo, String fincId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusInvoicemanage mfBusInvoicemanage = new MfBusInvoicemanage();
		try {
			mfBusInvoicemanage.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfBusInvoicemanage.setCriteriaList(mfBusInvoicemanage, ajaxData);// 我的筛选
			mfBusInvoicemanage.setCustomSorts(ajaxData);// 自定义排序
			mfBusInvoicemanage.setCusNo(cusNo);
			mfBusInvoicemanage.setFincId(fincId);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);// 异步传页面翻页尺寸
			ipage.setParams(this.setIpageParams("mfBusInvoicemanage", mfBusInvoicemanage));
			ipage = mfBusInvoicemanageFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
//			dataMap.put("tableHtml",tableHtml);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}

	/**
	 * 列表打开页面请求
	 * 打开借据页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getFincListPage")
	public String getFincListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		CodeUtils codeUtils = new CodeUtils();
		JSONArray invoiceTypeJsonArray = codeUtils.getJSONArrayByKeyName("INVOICE_SHOWTYPE");
		model.addAttribute("invoiceTypeJsonArray", invoiceTypeJsonArray);
		JSONArray invoiceRetypeJsonArray = codeUtils.getJSONArrayByKeyName("INVOICE_NOWTYPE");
		model.addAttribute("invoiceRetypeJsonArray", invoiceRetypeJsonArray);
		JSONArray invoiceStatusJsonArray = codeUtils.getJSONArrayByKeyName("INVOICE_STATUS");
		model.addAttribute("invoiceStatusJsonArray", invoiceStatusJsonArray);
		return "/component/invoicemanage/MfBusInvoicemanageFinc_List";
	}

	/***
	 * 贷后合同列表数据查询
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findFincByPageAjax")
	@ResponseBody
	public Map<String, Object> findFincByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
											  String ajaxData, String cusNo) throws Exception {
 		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusFincApp mfBusFincApp = new MfBusFincApp();
		try {
			mfBusFincApp.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfBusFincApp.setCustomSorts(ajaxData);// 自定义排序参数赋值
			mfBusFincApp.setCriteriaList(mfBusFincApp, ajaxData);// 我的筛选
			mfBusFincApp.setCusNo(cusNo);
			// mfBusFincApp.setPactSts(pactSts);
			// this.getRoleConditions(mfBusPact,"1000000001");//记录级权限控制方法
			String isOpen = PropertiesUtil.getCloudProperty("cloud.loanafter_filter");
			// 默认关闭
			if ("1".equals(isOpen)) {// 开启
				String roleNo = PropertiesUtil.getCloudProperty("cloud.loanafter_roleno");
				if (StringUtil.isNotEmpty(roleNo)) {
					String[] roleNoArray = User.getRoleNo(request);
					for (String role : roleNoArray) {
						if (roleNo.equals(role)) {
							mfBusFincApp.setOverdueSts(BizPubParm.OVERDUE_STS_1);
						}
					}
				}
			}
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfBusFincApp", mfBusFincApp));
			ipage = mfBusInvoicemanageFeign.findFincByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
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
	 * 打开新增发票页面请求
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getInvoicePage")
	public String getInvoicePage(Model model,String fincId,String appId) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("fincId",fincId);
		model.addAttribute("appId",appId);
		return "/component/invoicemanage/MfBusInvoicemanage_Insert";
	}

	/***
	 *	获取发票新增页面需要的数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findInvoiceByPageAjax")
	@ResponseBody
	public Map<String,Object> findInvoiceByPageAjax(Integer pageSize,String fincId,String ajaxData, Integer pageNo, String tableId, String tableType,String cusNo) throws Exception{

 		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		// 获取收益计划 信息
		MfRepayPlan mfRepayPlan = new MfRepayPlan();
		mfRepayPlan.setFincId(fincId);
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfRepayPlan", mfRepayPlan));
			ipage = mfBusInvoicemanageFeign.getRepayPlan(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
 			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
			dataMap.put("tableHtml", tableHtml);

		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;

	}


	/***
	 *	保存发票信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveInvoiceByPageAjax")
	@ResponseBody
	public Map<String,Object> saveInvoiceByPageAjax(String fincId,String appId,double invoiceAmount,String invoiceTerm,String invoiceType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfBusInvoicemanage mfBusInvoicemanage = new MfBusInvoicemanage();
			mfBusInvoicemanage.setInvoiceName(invoiceTerm + "期发票");
			mfBusInvoicemanage.setInvoiceAmount(invoiceAmount);
			mfBusInvoicemanage.setInvoiceTermnum(invoiceTerm);
			mfBusInvoicemanage.setInvoiceType(invoiceType);
			mfBusInvoicemanage.setInvoiceStatus("2");
			MfBusFincApp mfBusFincApp = new MfBusFincApp();
			mfBusFincApp.setFincId(fincId);
			mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
			mfBusInvoicemanage.setFincId(fincId);
			mfBusInvoicemanage.setCusNo(mfBusFincApp.getCusNo());
			mfBusInvoicemanage.setCusName(mfBusFincApp.getCusName());
			mfBusInvoicemanage.setAppId(appId);
			mfBusInvoicemanage.setAppName(mfBusFincApp.getAppName());
			mfBusInvoicemanage.setPactId(mfBusFincApp.getPactId());
			mfBusInvoicemanage.setPactNo(mfBusFincApp.getPactNo());
			mfBusInvoicemanageFeign.insert(mfBusInvoicemanage);
			/*//获取当前操作员
			String opNum = User.getRegNo(request);
			String opName = User.getRegName(request);
			dataMap.put("opNum",opNum);
			dataMap.put("opName",opName);*/
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

	/**
	 * 打开发票详情页
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getDetailPage")
	public String getInvoicePage(Model model,String invoiceId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String,String> map = new HashMap<>();
		MfBusInvoicemanage mfBusInvoicemanage = new MfBusInvoicemanage();
		mfBusInvoicemanage.setInvoiceId(invoiceId);
		mfBusInvoicemanage = mfBusInvoicemanageFeign.getById(mfBusInvoicemanage);
		//获取还款帐号
		MfBusFincApp mfBusFincApp = new MfBusFincApp();
		mfBusFincApp.setFincId(mfBusInvoicemanage.getFincId());
		mfBusFincApp = mfBusFincAppFeign.getById(mfBusFincApp);
		MfCusBankAccManage mfCusBankAccManage = new MfCusBankAccManage();
		if (StringUtil.isNotEmpty(mfBusFincApp.getRepayAccId())){
			mfCusBankAccManage.setId(mfBusFincApp.getRepayAccId());
			mfCusBankAccManage = mfCusBankAccManageFeign.getById(mfCusBankAccManage);
			if (mfCusBankAccManage != null){
				map.put("bankAndNumber",(mfCusBankAccManage.getBank() + "	" + mfCusBankAccManage.getAccountNo()));
			}
		}
		//获取当前操作员,部门信息
		String opNum = User.getRegNo(request);
		String opName = User.getRegName(request);
		mfBusInvoicemanage.setOpName(opName);
		String brName = User.getOrgName(request);
		mfBusInvoicemanage.setBrName(brName);
		if (!"2".equals(mfBusInvoicemanage.getInvoiceStatus())){
			//获取业务审批职位
			MfBusApplyHis mfBusApplyHis = new MfBusApplyHis();
			mfBusApplyHis.setAppId(mfBusInvoicemanage.getAppId());
			List<MfBusApplyHis> list = mfBusApplyHisFeign.findByAppId(mfBusApplyHis);
			for (MfBusApplyHis his:list){
				if ("1508213284024".equals(his.getApproveNodeNo())){
					mfBusInvoicemanage.setExt6(his.getApprovePartName());//分公司部门经理
				}else if ("node7489469624".equals(his.getApproveNodeNo())){
					mfBusInvoicemanage.setExt3(his.getApprovePartName());//总部业务分管副总
				}else if ("1525849059607".equals(his.getApproveNodeNo())){
					mfBusInvoicemanage.setExt8(his.getApprovePartName());//总部总经理
				}else {
				}
			}
			//获取借据审批职位
			MfBusFincAppHis mfBusFincAppHis = new MfBusFincAppHis();
			mfBusFincAppHis.setFincId(mfBusInvoicemanage.getFincId());
			List<MfBusFincAppHis> listFincHis = mfBusInvoicemanageFeign.getFincHisByFincId(mfBusFincAppHis);
			for (MfBusFincAppHis his:listFincHis){
				if (mfBusInvoicemanage.getExt8() == null && "1525864255477".equals(his.getApproveNodeNo())){
					mfBusInvoicemanage.setExt8(his.getApprovePartName());//总部总经理
				}
			}
		}
		model.addAttribute("map",map);
		model.addAttribute("mfBusInvoicemanage",mfBusInvoicemanage);
		return "/component/invoicemanage/MfBusInvoicemanage_Detail";
	}


	/***
	 *	保存发票已开状态
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveInvoiceDetailByPageAjax")
	@ResponseBody
	public Map<String,Object> invoiceId(String invoiceId,String ext2,String ext3,String ext4,String ext5,String ext7,String ext8,String ext9) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfBusInvoicemanage mfBusInvoicemanage = new MfBusInvoicemanage();
			mfBusInvoicemanage.setInvoiceId(invoiceId);
			mfBusInvoicemanage.setInvoiceStatus("2");
			mfBusInvoicemanage.setExt2(ext2);
			mfBusInvoicemanage.setExt3(ext3);
			mfBusInvoicemanage.setExt4(ext4);
			mfBusInvoicemanage.setExt5(ext5);
			mfBusInvoicemanage.setExt7(ext7);
			mfBusInvoicemanage.setExt8(ext8);
			mfBusInvoicemanage.setExt9(ext9);
			mfBusInvoicemanageFeign.update(mfBusInvoicemanage);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
		}catch (Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "保存失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}


	/**
	 * 新增页面
	 * @param cusType
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getForm")
	public String getForm(Model model,String cusType,String from) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		String fromId="formMfBusInvoicemanage";
		FormData forminvoicemanage = formService.getFormData(fromId);
		String invoiceId=WaterIdUtil.getWaterId("10");
        model.addAttribute("invoiceId",invoiceId);
		model.addAttribute("query", "");
		model.addAttribute("formMfBusInvoicemanage", forminvoicemanage);
		return "/component/invoicemanage/MfBusInvoicemanage_Form";
	}

	/**
	 * 发票作废申请页面
	 * @param cusType
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getFormOut")
	public String getFormOut(Model model,String invoiceId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfBusInvoicemanage mfBusInvoicemanage=new MfBusInvoicemanage();
		mfBusInvoicemanage.setInvoiceId(invoiceId);
		mfBusInvoicemanage=mfBusInvoicemanageFeign.getById(mfBusInvoicemanage);
		mfBusInvoicemanage.setApproveResult("1");
		mfBusInvoicemanage.setApproveRemark("");

		String term=mfBusInvoicemanage.getInvoiceTermnum();
		String[] str=term.split("-");
		List<MfRepayHistory> showList = new ArrayList<>();
		for (int i = 0; i <str.length ; i++) {
			MfRepayHistory mfRepayHistory=new MfRepayHistory();
			mfRepayHistory.setRepayId(str[i]);
			mfRepayHistory=mfRepayHistoryFeign.getById(mfRepayHistory);
			mfRepayHistory.setInvoiceStatus(mfBusInvoicemanage.getInvoiceStatus());
			showList.add(mfRepayHistory);
		}
		String fromId="MfBusInvoiceCheckout";
		FormData forminvoicemanage = formService.getFormData(fromId);
		getObjValue(forminvoicemanage,mfBusInvoicemanage);
		model.addAttribute("invoiceId",invoiceId);
		model.addAttribute("showList",showList);
		model.addAttribute("query", "");
		model.addAttribute("formMfBusInvoiceCheckout", forminvoicemanage);
		return "/component/invoicemanage/MfBusInvoiceOutmanage_Form";
	}
	/**
	 *还款历史详情页面
	 * @param cusType
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getNumberDetail")
	public String getNumberDetail(Model model,String invoiceId) throws Exception {
		ActionContext.initialize(request, response);
		MfBusInvoicemanage mfBusInvoicemanage=new MfBusInvoicemanage();
		mfBusInvoicemanage.setInvoiceId(invoiceId);
		mfBusInvoicemanage=mfBusInvoicemanageFeign.getById(mfBusInvoicemanage);
		String term=mfBusInvoicemanage.getInvoiceTermnum();
		String[] str=term.split("-");
		List<MfRepayHistory> showList = new ArrayList<>();
		for (int i = 0; i <str.length ; i++) {
			MfRepayHistory mfRepayHistory=new MfRepayHistory();
			mfRepayHistory.setRepayId(str[i]);
			mfRepayHistory=mfRepayHistoryFeign.getById(mfRepayHistory);
			mfRepayHistory.setInvoiceStatus(mfBusInvoicemanage.getInvoiceStatus());
			showList.add(mfRepayHistory);
		}
		model.addAttribute("invoiceId",invoiceId);
		model.addAttribute("showList",showList);
		return "/component/invoicemanage/MfBusInvoicemanage_NumberDetailList";
	}

	/**
	 * 发票操作详情页面
	 * @param cusType
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getFormOutCz")
	public String getFormOutCz(Model model,String invoiceId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		MfBusInvoicemanage mfBusInvoicemanage=new MfBusInvoicemanage();
		mfBusInvoicemanage.setInvoiceId(invoiceId);
		mfBusInvoicemanage=mfBusInvoicemanageFeign.getById(mfBusInvoicemanage);
		String term=mfBusInvoicemanage.getInvoiceTermnum();
		String[] str=term.split("-");
		List<MfRepayHistory> showList = new ArrayList<>();
		for (int i = 0; i <str.length ; i++) {
			MfRepayHistory mfRepayHistory=new MfRepayHistory();
			mfRepayHistory.setRepayId(str[i]);
			mfRepayHistory=mfRepayHistoryFeign.getById(mfRepayHistory);
			mfRepayHistory.setInvoiceStatus(mfBusInvoicemanage.getInvoiceStatus());
			showList.add(mfRepayHistory);
		}
		String fromId="mfbusInvoiceOutDetail";
		FormData forminvoicemanage = formService.getFormData(fromId);
		getObjValue(forminvoicemanage,mfBusInvoicemanage);
		model.addAttribute("invoiceId",invoiceId);
		model.addAttribute("showList",showList);
		model.addAttribute("query", "");
		model.addAttribute("formMfBusInvoiceCheckout", forminvoicemanage);
		return "/component/invoicemanage/MfBusInvoicemanage_Coment";
	}



	/**
	 * 方法描述： 查询所有客户信息
	 * @return
	 * @throws Exception String
	 * @author cd
	 * @date 2019-3-18 下午12:20:30
	 */
	@RequestMapping(value = "/selectCusInfo")
	@ResponseBody
	public Map<String, Object> selectCusInfo(int pageNo, String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCustomQuery(ajaxData);// 自定义查询参数赋值
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage.setParams(this.setIpageParams("mfCusCustomer",mfCusCustomer));
			ipage =mfBusInvoicemanageFeign.findCustomerByPage(ipage);
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
	 * 方法描述： 查询所有业务信息
	 * @return
	 * @throws Exception String
	 * @author cd
	 * @date 2019-3-18 下午12:20:30
	 */
	@RequestMapping(value = "/selectCusPro")
	@ResponseBody
	public Map<String, Object> selectCusPro(int pageNo, String ajaxData,String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusFincApp mfBusFincApp=new MfBusFincApp();
		mfBusFincApp.setCustomQuery(ajaxData);
		mfBusFincApp.setCusNo(cusNo);
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage.setParams(this.setIpageParams("mfBusFincApp",mfBusFincApp));
			ipage =mfBusInvoicemanageFeign.findCusProByPage(ipage);
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
	 * 方法描述： 展示客户业务列表信息
	 *
	 * @return
	 * @throws Exception String
	 * @author cd
	 * @date 2019-3-13 下午19:00:30
	 */
	@RequestMapping(value = "/cusBusinessInfo")
	@ResponseBody
	public Map<String, Object> cusBusinessInfo(String ajaxData,String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		try {
			String htmlStr = "";
			JsonTableUtil jtu = new JsonTableUtil();
			List<MfRepayHistory> showList = new ArrayList<>();
			MfRepayHistory mfRepayHistory=new MfRepayHistory();
			mfRepayHistory.setCusNo(cusNo);
			showList= mfRepayHistoryFeign.getRepayHistoryListByCusNo(mfRepayHistory);
			htmlStr = jtu.getJsonStr("tableInvoiceManage", "tableTag", showList, null, true);
			dataMap.put("htmlStr", htmlStr);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 方法描述： 展示客户业务列表信息
	 *
	 * @return
	 * @throws Exception String
	 * @author cd
	 * @date 2019-3-13 下午19:00:30
	 */
	@RequestMapping(value = "/cusBusinessInfoppp")
	@ResponseBody
	public Map<String, Object> cusBusinessInfoppp(int pageNo,String ajaxData,String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfRepayHistory mfRepayHistory=new MfRepayHistory();
			mfRepayHistory.setCusNo(cusNo);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);
			ipage.setParams(this.setIpageParams("mfRepayHistory",mfRepayHistory));
			ipage =mfBusInvoicemanageFeign.getRepayHistoryListByCusNo(ipage);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 *
	 * 方法描述： 获取还款历史
	 *
	 * @return
	 * @throws Exception
	 *             String
	 * @author zyf
	 * @date 2017-6-14 下午8:31:50
	 */
	@RequestMapping("/getRepayHistory")
	@ResponseBody
	public Map<String, Object> getRepayHistory(String ajaxData, String fincId,String cusNo, String appId, String tableId, String pledgeBillSts,String isDealing) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			JsonTableUtil jtu = new JsonTableUtil();
			List<MfRepayHistory> showList = new ArrayList<>();
			MfRepayHistory mfRepayHistory=new MfRepayHistory();
			mfRepayHistory.setFincId(fincId);
			mfRepayHistory.setCusNo(cusNo);
			showList= mfRepayHistoryFeign.getListInvoice(mfRepayHistory);
			for (int i = 0; i <showList.size() ; i++) {
				showList.get(i).setInvoiceStatus("0");
			}
			if (showList!=null&&showList.size()>0){
				String tableHtml = jtu.getJsonStr(tableId, "tableTag", showList, null, true);
				dataMap.put("tableData", tableHtml);
				dataMap.put("flag", "success");
			}else {
				dataMap.put("flag", "error");
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}


/**
 * 方法描述：保存发票申请表单
 *
 * @return
 * @throws Exception String
 * @author zyf
 * @date 2019-3-13 下午19:00:30
 */
@RequestMapping("/insertInvoiceAjax")
@ResponseBody
public Map<String, Object> insertInvoiceAjax(String ajaxData,String invoiceIdOne) throws Exception {
	ActionContext.initialize(request, response);
	FormService formService = new FormService();
	Map<String, Object> dataMap = new HashMap<String, Object>();
	try {
		FormData forminvoicemanage = formService.getFormData("formMfBusInvoicemanage");
		getFormValue(forminvoicemanage, getMapByJson(ajaxData));
		if (this.validateFormData(forminvoicemanage)) {
			MfBusInvoicemanage mfBusInvoicemanage=new MfBusInvoicemanage();
			setObjValue(forminvoicemanage, mfBusInvoicemanage);
			String date =DateUtil.getDate();
			double chinese=mfBusInvoicemanage.getInvoiceTotaltaxprice();
			String chinesePrice=MathExtend.numberToChinese(chinese);
			mfBusInvoicemanage.setInvoiceAmountUpperCase(chinesePrice);
			mfBusInvoicemanage.setInvoiceApplydate(date);
			mfBusInvoicemanage.setInvoiceId(invoiceIdOne);
			mfBusInvoicemanage.setInvoiceStatus("0");
			MfBusFincApp mfBusFincApp=new MfBusFincApp();
			mfBusFincApp.setFincId(mfBusInvoicemanage.getFincId());
			mfBusFincApp=mfBusFincAppFeign.getById(mfBusFincApp);
			mfBusInvoicemanage.setAppName(mfBusFincApp.getAppName());
			mfBusInvoicemanage.setPactNo(mfBusFincApp.getPactNo());
			mfBusInvoicemanage.setAppId(mfBusFincApp.getAppId());
			mfBusInvoicemanage.setPactId(mfBusFincApp.getPactId());
			mfBusInvoicemanage.setFincShowId(mfBusFincApp.getFincShowId());
			mfBusInvoicemanageFeign.insert(mfBusInvoicemanage);
			mfBusInvoicemanage= mfBusInvoicemanageFeign.submitProcess(mfBusInvoicemanage);
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("userRole", mfBusInvoicemanage.getApproveNodeName());
			paramMap.put("opNo", mfBusInvoicemanage.getApprovePartName());
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
	 * 方法描述：保存发票申请表单
	 *
	 * @return
	 * @throws Exception String
	 * @author zyf
	 * @date 2019-3-13 下午19:00:30
	 */
	@RequestMapping("/insertInvoiceOutAjax")
	@ResponseBody
	public Map<String, Object> insertInvoiceOutAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData forminvoicemanage = formService.getFormData("MfBusInvoiceCheckout");
			getFormValue(forminvoicemanage, getMapByJson(ajaxData));
			if (this.validateFormData(forminvoicemanage)) {
				MfBusInvoicemanage mfBusInvoicemanage=new MfBusInvoicemanage();
				setObjValue(forminvoicemanage, mfBusInvoicemanage);
				MfBusCancelInvoicemanage mfBusCancelInvoicemanage= new MfBusCancelInvoicemanage();
				String invoiceOutId=WaterIdUtil.getWaterId("20");

				mfBusCancelInvoicemanage.setInvoiceoutId(invoiceOutId);
				mfBusCancelInvoicemanage.setInvoiceId(mfBusInvoicemanage.getInvoiceId());
				mfBusCancelInvoicemanage.setInvoiceName(mfBusInvoicemanage.getCusName());
                mfBusCancelInvoicemanage.setInvoiceStatus(mfBusInvoicemanage.getInvoiceStatus());
                mfBusCancelInvoicemanage.setFincId(mfBusInvoicemanage.getFincId());
                mfBusCancelInvoicemanage.setOpNo(mfBusInvoicemanage.getOpNo());
                mfBusCancelInvoicemanage.setOpName(mfBusInvoicemanage.getOpName());
                mfBusCancelInvoicemanage.setBrNo(mfBusInvoicemanage.getBrNo());
                mfBusCancelInvoicemanage.setBrName(mfBusInvoicemanage.getBrName());

                mfBusCancelInvoicemanage.setApplyProcessid(mfBusInvoicemanage.getApplyProcessid());
                mfBusCancelInvoicemanage.setApproveNodeNo(mfBusInvoicemanage.getApproveNodeNo());
                mfBusCancelInvoicemanage.setApproveNodeName(mfBusInvoicemanage.getApproveNodeName());
                mfBusCancelInvoicemanage.setApprovePartName(mfBusInvoicemanage.getApprovePartName());
                mfBusCancelInvoicemanage.setApprovePartNo(mfBusInvoicemanage.getApprovePartNo());
                mfBusCancelInvoicemanage.setApproveRemark(mfBusInvoicemanage.getApproveRemark());
                mfBusCancelInvoicemanage.setApproveResult(mfBusInvoicemanage.getApproveResult());
				mfBusCancelInvoicemanage.setInvoiceoutDate(mfBusInvoicemanage.getExt9());
				mfBusCancelInvoicemanage.setInvoiceoutquetion(mfBusInvoicemanage.getExt10());
				mfBusInvoicemanageFeign.insertOut(mfBusCancelInvoicemanage);
				mfBusCancelInvoicemanage= mfBusInvoicemanageFeign.submitProcessOut(mfBusCancelInvoicemanage);
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("userRole", mfBusCancelInvoicemanage.getApproveNodeName());
				paramMap.put("opNo", mfBusCancelInvoicemanage.getApprovePartName());
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
	 * 方法描述：表单配置跳转发票申请审批页面
	 *
	 * @return
	 * @throws Exception String
	 * @author 仇招
	 * @date 2017-12-12下午20:09:27
	 */
	@RequestMapping("/getViewPoint")
	public String getViewPoint(Model model, String invoiceId, String hideOpinionType, String taskId, String activityType)
			throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		request.setAttribute("ifBizManger", "3");
		MfBusInvoicemanage mfBusInvoicemanage=new MfBusInvoicemanage();
		mfBusInvoicemanage.setInvoiceId(invoiceId);
		mfBusInvoicemanage=mfBusInvoicemanageFeign.getById(mfBusInvoicemanage);
		mfBusInvoicemanage.setApprovePartNo(null);
		mfBusInvoicemanage.setApprovePartName(null);
		mfBusInvoicemanage.setRegTime(DateUtil.getShowDateTime(mfBusInvoicemanage.getRegTime()));

		TaskImpl taskAppro = wkfInterfaceFeign.getTask(invoiceId, null);// 当前审批节点task
		if ("supplement_data".equals(taskAppro.getName())) {
			model.addAttribute("ifEdit", BizPubParm.YES_NO_Y);
		} else {
			model.addAttribute("ifEdit", BizPubParm.YES_NO_N);
		}
		// 初始化基本信息表单、工作经历表单
		FormData forminvoicemanage = formService.getFormData("mfBusinvoicecheck");
		// 实体对象放到表单对象中
		getObjValue(forminvoicemanage, mfBusInvoicemanage);
		// 处理审批意见类型
		Map<String, String> opinionTypeMap = new HashMap<String, String>();
		opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
		String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号
		opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
		opinionTypeMap.put("nodeNo", nodeNo);// 当前节点编号
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), opinionTypeMap);
		this.changeFormProperty(forminvoicemanage, "opinionType", "optionArray", opinionTypeList);
		// 获得当前审批岗位前面审批过得岗位信息
		JSONArray befNodesjsonArray = new JSONArray();
		befNodesjsonArray = wkfInterfaceFeign.getBefNodes1(taskId, User.getRegNo(request));
		request.setAttribute("befNodesjsonArray", befNodesjsonArray);

		String term=mfBusInvoicemanage.getInvoiceTermnum();
		String[] str=term.split("-");
		List<MfRepayHistory> showList = new ArrayList<>();
		for (int i = 0; i <str.length ; i++) {
			MfRepayHistory mfRepayHistory=new MfRepayHistory();
			mfRepayHistory.setRepayId(str[i]);
			mfRepayHistory=mfRepayHistoryFeign.getById(mfRepayHistory);
			mfRepayHistory.setInvoiceStatus(mfBusInvoicemanage.getInvoiceStatus());
			showList.add(mfRepayHistory);
		}
		model.addAttribute("showList",showList);
		model.addAttribute("invoiceId", invoiceId);
		model.addAttribute("mfBusInvoicemanage", mfBusInvoicemanage);
		model.addAttribute("mfBusinvoicecheck", forminvoicemanage);
		model.addAttribute("taskId", taskId);
		model.addAttribute("activityType", activityType);
		model.addAttribute("query", "");
		return "/component/invoicemanage/WkfInvoiceViewPoint";
	}

	/**
	 * 方法描述：表单配置跳转发票申请审批页面
	 *
	 * @return
	 * @throws Exception String
	 * @author zyf
	 * @date 2017-12-12下午20:09:27
	 */
	@RequestMapping("/getViewPointOut")
	public String getViewPointOut(Model model, String invoiceoutId, String hideOpinionType, String taskId, String activityType)
			throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		request.setAttribute("ifBizManger", "3");
		MfBusCancelInvoicemanage mfBusCancelInvoicemanage=new MfBusCancelInvoicemanage();
		mfBusCancelInvoicemanage.setInvoiceoutId(invoiceoutId);
		mfBusCancelInvoicemanage =mfBusInvoicemanageFeign.getByinvoiceoutId(mfBusCancelInvoicemanage);

		MfBusInvoicemanage mfBusInvoicemanage=new MfBusInvoicemanage();
		mfBusInvoicemanage.setInvoiceId(mfBusCancelInvoicemanage.getInvoiceId());
		mfBusInvoicemanage=mfBusInvoicemanageFeign.getById(mfBusInvoicemanage);
		mfBusInvoicemanage.setApproveResult("1");
		mfBusInvoicemanage.setApproveRemark("");
		mfBusInvoicemanage.setRegTime(DateUtil.getShowDateTime(mfBusInvoicemanage.getRegTime()));

		TaskImpl taskAppro = wkfInterfaceFeign.getTask(invoiceoutId, null);// 当前审批节点task
		if ("supplement_data".equals(taskAppro.getName())) {
			model.addAttribute("ifEdit", BizPubParm.YES_NO_Y);
		} else {
			model.addAttribute("ifEdit", BizPubParm.YES_NO_N);
		}
		// 初始化基本信息表单、工作经历表单
		String fromId="mfBusinvoicecheck";
		FormData forminvoicemanage = formService.getFormData(fromId);		// 实体对象放到表单对象中
		getObjValue(forminvoicemanage, mfBusInvoicemanage);
		// 处理审批意见类型
		Map<String, String> opinionTypeMap = new HashMap<String, String>();
		opinionTypeMap.put("hideOpinionType", hideOpinionType); // 隐藏审批类型
		String nodeNo = taskAppro.getActivityName();// 审批流程中当前审批节点编号
		opinionTypeMap.put("processDefinitionId", taskAppro.getProcessDefinitionId());// 流程id
		opinionTypeMap.put("nodeNo", nodeNo);// 当前节点编号
		List<OptionsList> opinionTypeList = new CodeUtils().getOpinionTypeList(activityType,
				taskAppro.getCouldRollback(), opinionTypeMap);
		this.changeFormProperty(forminvoicemanage, "opinionType", "optionArray", opinionTypeList);
		// 获得当前审批岗位前面审批过得岗位信息
		JSONArray befNodesjsonArray = new JSONArray();
		befNodesjsonArray = wkfInterfaceFeign.getBefNodes1(taskId, User.getRegNo(request));
		request.setAttribute("befNodesjsonArray", befNodesjsonArray);

		String term=mfBusInvoicemanage.getInvoiceTermnum();
		String[] str=term.split("-");
		List<MfRepayHistory> showList = new ArrayList<>();
		for (int i = 0; i <str.length ; i++) {
			MfRepayHistory mfRepayHistory=new MfRepayHistory();
			mfRepayHistory.setRepayId(str[i]);
			mfRepayHistory=mfRepayHistoryFeign.getById(mfRepayHistory);
			mfRepayHistory.setInvoiceStatus(mfBusInvoicemanage.getInvoiceStatus());
			showList.add(mfRepayHistory);
		}
		model.addAttribute("showList",showList);
		model.addAttribute("invoiceoutId", invoiceoutId);
		model.addAttribute("invoiceId", mfBusCancelInvoicemanage.getInvoiceId());
		model.addAttribute("mfBusInvoicemanage", mfBusInvoicemanage);
		model.addAttribute("mfBusinvoicecheck", forminvoicemanage);
		model.addAttribute("taskId", taskId);
		model.addAttribute("activityType", activityType);
		model.addAttribute("query", "");
		return "/component/invoicemanage/WkfInvoiceOutViewPoint";
	}


	/**
	 * 方法描述： 审批意见保存提交
	 *
	 * @return
	 * @throws Exception String
	 * @author zyf
	 * @date 2017-12-13 上午10:09:47
	 */
	@RequestMapping("/submitUpdateAjax")
	@ResponseBody
	public Map<String, Object> submitUpdateAjax(String ajaxData, String appNo, String taskId, String transition,
												String nextUser) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> formDataMap = getMapByJson(ajaxData);
		// 初始化基本信息表单、工作经历表单
		FormData forminvoicemanage = formService.getFormData("mfBusinvoicecheck");
		getFormValue(forminvoicemanage, formDataMap);
		MfBusInvoicemanage mfBusInvoicemanage=new MfBusInvoicemanage();
		setObjValue(forminvoicemanage, mfBusInvoicemanage);
		Result res;
		try {
			new FeignSpringFormEncoder().addParamsToBaseDomain(mfBusInvoicemanage);
			formDataMap.put("mfBusInvoicemanage", mfBusInvoicemanage);
			res = mfBusInvoicemanageFeign.doCommit(taskId, transition, nextUser, formDataMap);
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

	/**
	 * 方法描述： 审批意见保存提交
	 *
	 * @return
	 * @throws Exception String
	 * @author zyf
	 * @date 2017-12-13 上午10:09:47
	 */
	@RequestMapping("/submitUpdateOutAjax")
	@ResponseBody
	public Map<String, Object> submitUpdateOutAjax(String ajaxData, String appNo, String taskId, String transition,
												String nextUser) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> formDataMap = getMapByJson(ajaxData);
		// 初始化基本信息表单、工作经历表单
		FormData forminvoicemanage = formService.getFormData("mfBusinvoicecheck");
		getFormValue(forminvoicemanage, formDataMap);
		MfBusInvoicemanage mfBusInvoicemanage=new MfBusInvoicemanage();
		setObjValue(forminvoicemanage, mfBusInvoicemanage);
		MfBusCancelInvoicemanage mfBusCancelInvoicemanage=new MfBusCancelInvoicemanage();
		mfBusCancelInvoicemanage.setInvoiceId(mfBusInvoicemanage.getInvoiceId());
		mfBusCancelInvoicemanage.setInvoiceoutId(appNo);
		mfBusCancelInvoicemanage.setApproveResult(mfBusInvoicemanage.getApproveResult());
		Result res;
		try {
			new FeignSpringFormEncoder().addParamsToBaseDomain(mfBusCancelInvoicemanage);
			formDataMap.put("mfBusCancelInvoicemanage", mfBusCancelInvoicemanage);
			res = mfBusInvoicemanageFeign.doCommitOut(taskId, transition, nextUser, formDataMap);
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

	@RequestMapping(value = "/getDownload")
	@ResponseBody
	public void getDownload() throws Exception{
		//读取出文件的所有字节,并将所有字节写出给客户端
		ActionContext.initialize(request, response);
		MfBusInvoicemanage mfBusInvoicemanage=new MfBusInvoicemanage();
		String tableId="mfbusinvoicemanage";
		try {
			List<MfBusInvoicemanage>mfBusInvoicemanageList=mfBusInvoicemanageFeign.getInvoiceList();
			ExpExclUtil eu = new ExpExclUtil();
			HSSFWorkbook wb = eu.expExclTableForList(tableId, mfBusInvoicemanageList,null,false,"");
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/x-download; charset=utf-8");
			response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("发票详情.xls", "UTF-8"));
			OutputStream stream = response.getOutputStream();
			wb.write(stream);
			wb.close();// HSSFWorkbook关闭
			stream.flush();
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
