package app.component.tools.charge.controller;
import java.io.File;
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
import com.core.struts.taglib.JsonTableUtil;

import app.component.tools.charge.entity.AllCloudService;
import app.component.tools.charge.entity.BasicCifAccount;
import app.component.tools.charge.entity.DxUsedView;
import app.component.tools.charge.entity.SfUsedView;
import app.component.tools.charge.feign.AccountPayFeign;
import app.util.toolkit.Ipage;
@Controller
@RequestMapping("/accountPay")
public class AccountPayController extends BaseFormBean {
	
//	private static Logger logger = LoggerFactory.getLogger(AccountPayAction.class);
	//注入业务处理层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private AccountPayFeign accountPayFeign;
	//异步参数
	//表单变量
	
	
	/**
	 * 获取公司基本信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDetilPage")
	public Map<String, Object> getDetilPage(Model model, String ajaxData) throws Exception{
		Map<String, Object> dataMap = new HashMap<String,Object>();
		try {
//			Map<String,String> flagMap=accountPayFeign.judgeCifByLicenseNo();
//			
//			String flag=flagMap.get("flag");
//			String flagMsg=flagMap.get("flagMsg");
//			String cifAccount=flagMap.get("cifAccount");
//			request.setAttribute("flagMsg", flagMsg);
//			
//			if(StringUtil.isNotEmpty(flag)){
//				if("0".equals(flag)){//写到本地账号密码，再进系统
//					//写到本地配置文件  云平台账号密码
//				}else if("1".equals(flag)){//直接进系统
//					//进系统
//				}else{//2.3.4提示本地配置不正确
//					return "/component/tools/charge/cloudRegister_cifError";
//				}
//			}else{
//				logger.error("系统错误：getDetilPage方法出错！");
//				throw e;
//			}
//			cifAccount="mftcc";
//			manageDetil = accountPayFeign.getManageCloudJson(cifAccount);
//			manageDetil=new HashMap<String, Object>();
//			manageDetil.put("cifAccount", "cloud_mftccmmmm");
//			super.getHttpRequest().getSession().setAttribute("manageDetil", manageDetil);//把信息存入session
			FormService formService = new FormService();
//			ActionContext.initialize(request, response);
//			Map<String, Object> accountMap=(Map<String, Object>) ActionContext.getActionContext().getSessionMap().get("manageDetil");
//			String licenseNo = (String) accountMap.get("licenseNo");
//			ServiceCloudUsedList=accountPayFeign.getUseCloudJson(cifAccount, licenseNo);
//			openServiceList=accountPayFeign.getServiceCloudJson(cifAccount);
//			ActionContext.initialize(request, response);
//			ActionContext.getActionContext().getRequest().setAttribute("flagMsg", "正在开发中，敬请期待！");
//			String url = PropertiesUtil.getCloudProperty("cloud.ip")+"/servicemanage/index.html";
			String url = "/servicemanage/index.html";
			dataMap.put("flag", "success");
			dataMap.put("url", url);
		} catch (Exception e) {
//			logger.error("获取公司基本信息出错", e);
			dataMap.put("flag", "error");
			dataMap.put("msg", "获取账户页面失败!");
			throw e;
		}
		
		return dataMap;
	}
	/**
	 * 保存用户申请信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertCifAccount")
	public String insertCifAccount(Model model, String ajaxData,File file1,String file1FileName) throws Exception{
		FormService formService = new FormService();
		FormData  formcifapplyInfo0002 =null;
		try {
			BasicCifAccount bc=new BasicCifAccount();
			String companyname=request.getParameter("companyname");
			String licenseNo=request.getParameter("licenseNo");
			String contactName=request.getParameter("contactName");
			String contactTel=request.getParameter("contactTel");
			String eMail=request.getParameter("eMail");
			String companyAds=request.getParameter("companyAds");
			String remarkState=request.getParameter("remarkState");
			bc.setCompanyname(companyname);
			bc.setLicenseNo(licenseNo);
			bc.setContactName(contactName);
			bc.setContactTel(contactTel);
			bc.seteMail(eMail);
			bc.setCompanyAds(companyAds);
			bc.setRemarkState(remarkState);
			String code=accountPayFeign.insertCifAccount(bc,file1,file1FileName);//执行代码
			if("success".equalsIgnoreCase(code)){//保存成功
				formcifapplyInfo0002 = formService.getFormData("cifapplyInfo0002");
				 getFormValue(formcifapplyInfo0002);
				 BasicCifAccount auBc=new BasicCifAccount();
				 auBc=accountPayFeign.getAuditInfo();
				 getObjValue(formcifapplyInfo0002, auBc);
				 request.setAttribute("code", accountPayFeign.getCodeInit());
				 String flag=request.getParameter("flag");
				 request.setAttribute("flag", flag);
		model.addAttribute("formcifapplyInfo0002", formcifapplyInfo0002);
		model.addAttribute("query", "");
				 return "/component/tools/charge/cifApplyInfo";
			}//失败 :密码错误，或已是正常账号 已抛出异常
		} catch (Exception e) {
//			logger.error("insertCifAccount方法出错，执行action层失败，", e);
			throw e;
		}
		model.addAttribute("formcifapplyInfo0002", formcifapplyInfo0002);
		model.addAttribute("query", "");
		return "/component/tools/charge/cifApplyInfo";
	}
	/**
	 * 获取用户的图片转为base64
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getBase64Img")
	public Map<String, Object> getBase64Img(Model model, String ajaxData) throws Exception{
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			
			String code=request.getParameter("code");
			String str=accountPayFeign.getBase64Img();
			dataMap.put("str", str);
		} catch (Exception e) {
//			logger.error("getBase64Img方法出错，执行action层失败，", e);
			throw e;
		}
		return dataMap;
	}
	/**
	 * 跳转二维码页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getBarcode")
	public String getBarcode(Model model, String payValue) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		ActionContext.getActionContext().getRequest().setAttribute("payValue",payValue);
		model.addAttribute("query", "");
		return "/component/tools/charge/WXPay";
	}
	/**
	 * 获取主页左边开通服务列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/ServiceAjaxLeft")
	@ResponseBody
	public Map<String, Object> ServiceAjaxLeft(String tableId,String tableType,int pageSize,int pageNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> accountMap=(Map<String, Object>) super.getHttpRequest().getSession().getAttribute("manageDetil");
		//Map<String, Object> accountMap=(Map<String, Object>) ActionContext.getActionContext().getSessionMap().get("manageDetil");
		String cifAccount = (String) accountMap.get("cifAccount");
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage = accountPayFeign.getServiceCloudJson(ipage,cifAccount);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
			/**
			 	ipage.setResult(tableHtml);
				dataMap.put("ipage",ipage);
				需要改进的方法
				dataMap.put("tableData",tableHtml);
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
	 * 获取主页右边使用一览列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/UseViewAjaxRight")
	@ResponseBody
	public Map<String, Object> UseViewAjaxRight(String tableId,String tableType,int pageSize,int pageNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		Map<String, Object> accountMap=(Map<String, Object>) ActionContext.getActionContext().getSessionMap().get("manageDetil");
		String cifAccount = (String) accountMap.get("cifAccount");
		String licenseNo = (String) accountMap.get("licenseNo");
		try {
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage = accountPayFeign.getUseCloudJson(ipage,cifAccount,licenseNo);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(),ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
			
			/**
			 	ipage.setResult(tableHtml);
				dataMap.put("ipage",ipage);
				需要改进的方法
				dataMap.put("tableData",tableHtml);
			 */
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	@RequestMapping(value = "/getDxUseList")
	public String getDxUseList(Model model, String ajaxData) throws Exception {
		return "/component/tools/charge/DxUsedList";
	}
	/**
	 * 获取短信使用记录
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDxUsedViewAjax")
	@ResponseBody
	public Map<String, Object> getDxUsedViewAjax(String ajaxData,String tableId,String tableType,int pageSize,int pageNo,String itemNo) throws Exception {
			ActionContext.initialize(request,
					response);
			Map<String, Object> dataMap = new HashMap<String, Object>(); 
			DxUsedView used = new DxUsedView();
			Map<String, Object> accountMap=(Map<String, Object>) ActionContext.getActionContext().getSessionMap().get("manageDetil");
			String cifAccount = (String) accountMap.get("cifAccount");
			try {
				used.setCustomQuery(ajaxData);//自定义查询参数赋值
				used.setCriteriaList(used, ajaxData);//我的筛选
				//this.getRoleConditions(demo,"1000000001");//记录级权限控制方法
				Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
				ipage.setPageNo(pageNo);//异步传页面翻页参数
	/*			ipage.setPageSize(pageSize);*/
				ipage = accountPayFeign.getDxUseRecordList(ipage,used,cifAccount,itemNo);
				//返回相应的HTML方法
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
	/**
	 * 获取身份认证使用记录
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSfUsedViewAjax")
	@ResponseBody
	public Map<String, Object> getSfUsedViewAjax(String ajaxData,String tableId,String tableType,int pageSize,int pageNo,String itemNo) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		SfUsedView used = new SfUsedView();
		@SuppressWarnings("unchecked")
		Map<String, Object> accountMap=(Map<String, Object>) ActionContext.getActionContext().getSessionMap().get("manageDetil");
		String cifAccount = (String) accountMap.get("cifAccount");
		try {
			used.setCustomQuery(ajaxData);//自定义查询参数赋值
			used.setCriteriaList(used, ajaxData);//我的筛选
			//this.getRoleConditions(demo,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage = accountPayFeign.getSfUseRecordList(ipage,used,cifAccount,itemNo);
			//返回相应的HTML方法
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
	/**
	 * 短信使用详情页面跳转
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getUseView")
	public String getUseView(Model model, String ajaxData) throws Exception {
			return "/component/tools/charge/usedView";
	}
	/**
	 * 服务使用详情页面跳转
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSfUseView")
	public String getSfUseView(Model model, String ajaxData,String itemNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		request.setAttribute("itemNo", itemNo);
		return "/component/tools/charge/SfUsedView";
	}
	/**
	 * 开通服务页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getServicePage")
	public String getServicePage(Model model, String ajaxData) throws Exception {
		
		return "/component/tools/charge/servicePage";
	}
	/**
	 * 充值页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSendPayPage")
	public String getSendPayPage(Model model) throws Exception {
		return "/component/tools/charge/sendPayPage";
	}
	/**
	 *  微信支付页面
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/getgetWXPay")
	public String getgetWXPay(Model model, String payValue) throws Exception {
		ActionContext.getActionContext().getRequest().setAttribute("operAmt", payValue);
		return "WXPay";
	}
	
	/**
	 * 获取所有服务 数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getServiceAjax")
	@ResponseBody
	public Map<String, Object> getServiceAjax(String ajaxData,String tableId,String tableType,int pageSize,int pageNo,String itemNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		AllCloudService used = new AllCloudService();
		Map<String, Object> accountMap=(Map<String, Object>) ActionContext.getActionContext().getSessionMap().get("manageDetil");
		String cifAccount = (String) accountMap.get("cifAccount");
		try {
			used.setCustomQuery(ajaxData);//自定义查询参数赋值
			used.setCriteriaList(used, ajaxData);//我的筛选
			//this.getRoleConditions(demo,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage = accountPayFeign.getAllService(ipage,used,cifAccount);
			//返回相应的HTML方法
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
	/**
	 * 开通(禁用)服务
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/openServiceAjax")
	@ResponseBody
	public Map<String, Object> openServiceAjax(String itemNo,String sts,String smsSuffix) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		Map<String, Object> accountMap=(Map<String, Object>) ActionContext.getActionContext().getSessionMap().get("manageDetil");
		String cifAccount = (String) accountMap.get("cifAccount");
		Map<String, String> result = accountPayFeign.openService(cifAccount, itemNo , sts , smsSuffix);
		//自定义查询Bo方法
		dataMap.put("result",result);
		return dataMap;
	}
	/**
	 * 获取服务页面头部筛选框内容
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getUseServiceAjax")
	@ResponseBody
	public Map<String, Object> getUseServiceAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		Map<String, Object> accountMap=(Map<String, Object>) ActionContext.getActionContext().getSessionMap().get("manageDetil");
		String cifAccount = (String) accountMap.get("cifAccount");
		String result = accountPayFeign.getServiceCheck(cifAccount);
		//自定义查询Bo方法
		dataMap.put("result",result);
		return dataMap;
	}
	
	
	
}
