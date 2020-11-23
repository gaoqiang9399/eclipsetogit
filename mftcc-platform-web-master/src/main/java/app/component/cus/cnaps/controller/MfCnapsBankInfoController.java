package  app.component.cus.cnaps.controller;
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
import app.component.cus.cnaps.entity.MfCnapsBankInfo;
import app.component.cus.cnaps.feign.MfCnapsBankInfoFeign;
import app.util.toolkit.Ipage;

/**
 * Title: MfCnapsBankInfoAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Thu Dec 07 14:51:12 CST 2017
 **/
@Controller
@RequestMapping("/mfCnapsBankInfo")
public class MfCnapsBankInfoController extends BaseFormBean{
	private static final long serialVersionUID = 9196454891709523438L;
	@Autowired  
	private HttpServletRequest request;
	@Autowired  
	private HttpServletResponse response;
	@Autowired
	private MfCnapsBankInfoFeign mfCnapsBankInfoFeign;
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request,response);
		model.addAttribute("query", "");
		return "/component/cus/cnaps/MfCnapsBankInfo_List";
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
		MfCnapsBankInfo mfCnapsBankInfo = new MfCnapsBankInfo();
		try {
			mfCnapsBankInfo.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCnapsBankInfo.setCriteriaList(mfCnapsBankInfo, ajaxData);//我的筛选
			//mfCnapsBankInfo.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfCnapsBankInfo,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfCnapsBankInfo", mfCnapsBankInfo));
			ipage = mfCnapsBankInfoFeign.findByPage(ipage);
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
	@RequestMapping("/getCnapsListAjax")
	@ResponseBody
	public Map<String, Object> getCnapsListAjax(String ajaxData,int pageNo) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCnapsBankInfo mfCnapsBankInfo = new MfCnapsBankInfo();
		try {
			mfCnapsBankInfo.setCustomQuery(ajaxData);//自定义查询参数赋值
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setParams(this.setIpageParams("mfCnapsBankInfo", mfCnapsBankInfo));
			//自定义查询Bo方法
			ipage = mfCnapsBankInfoFeign.findByPage(ipage);
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
	@RequestMapping("/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			FormService formService = new FormService();
			FormData formcnaps0002 = formService.getFormData("cnaps0002");
			getFormValue(formcnaps0002, getMapByJson(ajaxData));
			if(this.validateFormData(formcnaps0002)){
				MfCnapsBankInfo mfCnapsBankInfo = new MfCnapsBankInfo();
				setObjValue(formcnaps0002, mfCnapsBankInfo);
				mfCnapsBankInfoFeign.insert(mfCnapsBankInfo);
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
	@RequestMapping("/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		FormData formcnaps0002 = formService.getFormData("cnaps0002");
		getFormValue(formcnaps0002, getMapByJson(ajaxData));
		MfCnapsBankInfo mfCnapsBankInfoJsp = new MfCnapsBankInfo();
		setObjValue(formcnaps0002, mfCnapsBankInfoJsp);
		MfCnapsBankInfo mfCnapsBankInfo = mfCnapsBankInfoFeign.getById(mfCnapsBankInfoJsp);
		if(mfCnapsBankInfo!=null){
			try{
				mfCnapsBankInfo = (MfCnapsBankInfo)EntityUtil.reflectionSetVal(mfCnapsBankInfo, mfCnapsBankInfoJsp, getMapByJson(ajaxData));
				mfCnapsBankInfoFeign.update(mfCnapsBankInfo);
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
	@RequestMapping("/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			FormService formService = new FormService();
			FormData formcnaps0002 = formService.getFormData("cnaps0002");
			getFormValue(formcnaps0002, getMapByJson(ajaxData));
			if(this.validateFormData(formcnaps0002)){
				MfCnapsBankInfo mfCnapsBankInfo = new MfCnapsBankInfo();
				setObjValue(formcnaps0002, mfCnapsBankInfo);
				mfCnapsBankInfoFeign.update(mfCnapsBankInfo);
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
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String bankcode) throws Exception {
		ActionContext.initialize(request,response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormService formService = new FormService();
		FormData formcnaps0002 = formService.getFormData("cnaps0002");
		MfCnapsBankInfo mfCnapsBankInfo = new MfCnapsBankInfo();
		mfCnapsBankInfo.setBankcode(bankcode);
		mfCnapsBankInfo = mfCnapsBankInfoFeign.getById(mfCnapsBankInfo);
		getObjValue(formcnaps0002, mfCnapsBankInfo,formData);
		if(mfCnapsBankInfo!=null){
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
	public Map<String, Object> deleteAjax(String bankcode) throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCnapsBankInfo mfCnapsBankInfo = new MfCnapsBankInfo();
		mfCnapsBankInfo.setBankcode(bankcode);
		try {
			mfCnapsBankInfoFeign.delete(mfCnapsBankInfo);
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
	@RequestMapping("/input")
	public String input(Model model) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formcnaps0002 = formService.getFormData("cnaps0002");
		model.addAttribute("formcnaps0002", formcnaps0002);
		model.addAttribute("query", "");
		return "/component/cus/cnaps/MfCnapsBankInfo_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model,String bankcode) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		 FormData formcnaps0001 = formService.getFormData("cnaps0001");
		 getFormValue(formcnaps0001);
		 MfCnapsBankInfo mfCnapsBankInfo = new MfCnapsBankInfo();
		mfCnapsBankInfo.setBankcode(bankcode);
		 mfCnapsBankInfo = mfCnapsBankInfoFeign.getById(mfCnapsBankInfo);
		 getObjValue(formcnaps0001, mfCnapsBankInfo);
		model.addAttribute("formcnaps0001", formcnaps0001);
		model.addAttribute("query", "");	
		return "/component/cus/cnaps/MfCnapsBankInfo_Detail";
	}

	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	public void validateInsert() throws Exception{
		 ActionContext.initialize(request, response);
		 FormService formService = new FormService();
		 FormData formcnaps0002 = formService.getFormData("cnaps0002");
		 getFormValue(formcnaps0002);
		 boolean validateFlag = this.validateFormData(formcnaps0002);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	public void validateUpdate() throws Exception{
		 ActionContext.initialize(request, response);
		 FormService formService = new FormService();
		 FormData formcnaps0002 = formService.getFormData("cnaps0002");
		 getFormValue(formcnaps0002);
		 boolean validateFlag = this.validateFormData(formcnaps0002);
	}
	
}
