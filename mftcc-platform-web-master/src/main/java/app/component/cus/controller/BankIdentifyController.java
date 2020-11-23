package  app.component.cus.controller;
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

import app.component.common.EntityUtil;
import app.component.cus.entity.BankIdentify;
import app.component.cus.feign.BankIdentifyFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: BankIdentifyAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed Sep 28 17:31:46 CST 2016
 **/
@Controller
@RequestMapping("/bankIdentify")
public class BankIdentifyController extends BaseFormBean{
	@Autowired  
	private HttpServletRequest request;
	@Autowired  
	private HttpServletResponse response;
	@Autowired 
	private BankIdentifyFeign bankIdentifyFeign;
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request,response);
		return "/component/cus/BankIdentify_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String,Object> findByPageAjax(String ajaxData,int pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		BankIdentify bankIdentify = new BankIdentify();
		try {
			bankIdentify.setCustomQuery(ajaxData);//自定义查询参数赋值
			bankIdentify.setCriteriaList(bankIdentify, ajaxData);//我的筛选
			//this.getRoleConditions(bankIdentify,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Feign方法
			ipage.setParams(this.setIpageParams("bankIdentify", bankIdentify));
			ipage = bankIdentifyFeign.findByPage(ipage);
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
	 * AJAX新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String,Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		try{
			FormData formbankidentify0002 = formService.getFormData("bankidentify0002");
			getFormValue(formbankidentify0002, getMapByJson(ajaxData));
			if(this.validateFormData(formbankidentify0002)){
				BankIdentify bankIdentify = new BankIdentify();
				setObjValue(formbankidentify0002, bankIdentify);
				bankIdentifyFeign.insert(bankIdentify);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
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
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String,Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>();
		FormData formbankidentify0002 = formService.getFormData("bankidentify0002");
		getFormValue(formbankidentify0002, getMapByJson(ajaxData));
		BankIdentify bankIdentifyJsp = new BankIdentify();
		setObjValue(formbankidentify0002, bankIdentifyJsp);
		BankIdentify bankIdentify = bankIdentifyFeign.getById(bankIdentifyJsp);
		if(bankIdentify!=null){
			try{
				bankIdentify = (BankIdentify)EntityUtil.reflectionSetVal(bankIdentify, bankIdentifyJsp, getMapByJson(ajaxData));
				bankIdentifyFeign.update(bankIdentify);
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
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String,Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		BankIdentify bankIdentify = new BankIdentify();
		try{
			FormData formbankidentify0002 = formService.getFormData("bankidentify0002");
			getFormValue(formbankidentify0002, getMapByJson(ajaxData));
			if(this.validateFormData(formbankidentify0002)){
				bankIdentify = new BankIdentify();
				setObjValue(formbankidentify0002, bankIdentify);
				bankIdentifyFeign.update(bankIdentify);
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
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String,Object> getByIdAjax(String identifyNumber) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		BankIdentify bankIdentify = new BankIdentify();
		bankIdentify.setIdentifyNumber(identifyNumber);
		dataMap = bankIdentifyFeign.getDataMapById(bankIdentify);
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String,Object> deleteAjax(String identifyNumber) throws Exception{
		ActionContext.initialize(request,response);
		Map<String,Object> dataMap = new HashMap<String, Object>(); 
		BankIdentify bankIdentify = new BankIdentify();
		bankIdentify.setIdentifyNumber(identifyNumber);
		try {
			bankIdentifyFeign.delete(bankIdentify);
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
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formbankidentify0002 = formService.getFormData("bankidentify0002");
		model.addAttribute("formbankidentify0002", formbankidentify0002);
		model.addAttribute("query", "");
		return "/component/cus/BankIdentify_Insert";
	}
	/***
	 * 新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception{
		ActionContext.initialize(request,response);
		 FormService formService = new FormService();
		 FormData formbankidentify0002 = formService.getFormData("bankidentify0002");
		 getFormValue(formbankidentify0002);
		 BankIdentify bankIdentify = new BankIdentify();
		 setObjValue(formbankidentify0002, bankIdentify);
		 bankIdentifyFeign.insert(bankIdentify);
		 getObjValue(formbankidentify0002, bankIdentify);
		 this.addActionMessage(model, "保存成功");
		 Ipage ipage = this.getIpage();
		 ipage.setParams(this.setIpageParams("bankIdentify", bankIdentify));
		 List<BankIdentify> bankIdentifyList = (List<BankIdentify>)bankIdentifyFeign.findByPage(ipage).getResult();
		 model.addAttribute("bankIdentifyList",bankIdentifyList); 
		 model.addAttribute("query", "");
		 return "/component/cus/BankIdentify_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model,String identifyNumber) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formbankidentify0001 = formService.getFormData("bankidentify0001");
		 getFormValue(formbankidentify0001);
		 BankIdentify bankIdentify = new BankIdentify();
		 bankIdentify.setIdentifyNumber(identifyNumber);
		 bankIdentify = bankIdentifyFeign.getById(bankIdentify);
		 getObjValue(formbankidentify0001, bankIdentify);
		 model.addAttribute("formbankidentify0001", formbankidentify0001);
		 model.addAttribute("query", "");
		return "/component/cus/BankIdentify_Detail";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model,String identifyNumber) throws Exception {
		ActionContext.initialize(request,response);
		BankIdentify bankIdentify = new BankIdentify();
		bankIdentify.setIdentifyNumber(identifyNumber);
		bankIdentifyFeign.delete(bankIdentify);
		return getListPage(model);
	}
	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	public void validateInsert() throws Exception{
		 ActionContext.initialize(request,response );
		 FormService formService = new FormService();
		 FormData formbankidentify0002 = formService.getFormData("bankidentify0002");
		 getFormValue(formbankidentify0002);
		 boolean validateFlag = this.validateFormData(formbankidentify0002);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	public void validateUpdate() throws Exception{
		 ActionContext.initialize(request,response);
		 FormService formService = new FormService();
		 FormData formbankidentify0002 = formService.getFormData("bankidentify0002");
		 getFormValue(formbankidentify0002);
		 boolean validateFlag = this.validateFormData(formbankidentify0002);
	}
}
