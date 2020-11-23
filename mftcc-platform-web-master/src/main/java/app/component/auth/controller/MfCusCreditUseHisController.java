package  app.component.auth.controller;
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

import app.component.auth.entity.MfCusCreditUseHis;
import app.component.auth.feign.MfCusCreditUseHisFeign;
import app.component.common.EntityUtil;
import app.util.toolkit.Ipage;

/**
 * Title: MfCusCreditUseHisAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Tue Jun 27 22:28:51 CST 2017
 **/
@Controller
@RequestMapping("/mfCusCreditUseHis")
public class MfCusCreditUseHisController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfCusCreditUseHisBo
	@Autowired
	private MfCusCreditUseHisFeign mfCusCreditUseHisFeign;
	//全局变量
	//异步参数
	//表单变量
	
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		return "/component/auth/MfCusCreditUseHis_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusCreditUseHis mfCusCreditUseHis = new MfCusCreditUseHis();
		try {
			mfCusCreditUseHis.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusCreditUseHis.setCriteriaList(mfCusCreditUseHis, ajaxData);//我的筛选
			//mfCusCreditUseHis.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfCusCreditUseHis,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = mfCusCreditUseHisFeign.findByPage(ipage, mfCusCreditUseHis);
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
		FormData 	formcreditusehis0002 = formService.getFormData("creditusehis0002");
			getFormValue(formcreditusehis0002, getMapByJson(ajaxData));
			if(this.validateFormData(formcreditusehis0002)){
		MfCusCreditUseHis mfCusCreditUseHis = new MfCusCreditUseHis();
				setObjValue(formcreditusehis0002, mfCusCreditUseHis);
				mfCusCreditUseHisFeign.insert(mfCusCreditUseHis);
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
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formcreditusehis0002 = formService.getFormData("creditusehis0002");
		getFormValue(formcreditusehis0002, getMapByJson(ajaxData));
		MfCusCreditUseHis mfCusCreditUseHisJsp = new MfCusCreditUseHis();
		setObjValue(formcreditusehis0002, mfCusCreditUseHisJsp);
		MfCusCreditUseHis mfCusCreditUseHis = mfCusCreditUseHisFeign.getById(mfCusCreditUseHisJsp);
		if(mfCusCreditUseHis!=null){
			try{
				mfCusCreditUseHis = (MfCusCreditUseHis)EntityUtil.reflectionSetVal(mfCusCreditUseHis, mfCusCreditUseHisJsp, getMapByJson(ajaxData));
				mfCusCreditUseHisFeign.update(mfCusCreditUseHis);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			}catch(Exception e){
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw e;
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
		FormData 	formcreditusehis0002 = formService.getFormData("creditusehis0002");
			getFormValue(formcreditusehis0002, getMapByJson(ajaxData));
			if(this.validateFormData(formcreditusehis0002)){
		MfCusCreditUseHis mfCusCreditUseHis = new MfCusCreditUseHis();
				setObjValue(formcreditusehis0002, mfCusCreditUseHis);
				mfCusCreditUseHisFeign.update(mfCusCreditUseHis);
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
	
	/**
	 * AJAX获取查看
	 * @param creditUseHisId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData, String creditUseHisId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formcreditusehis0002 = formService.getFormData("creditusehis0002");
		MfCusCreditUseHis mfCusCreditUseHis = new MfCusCreditUseHis();
		mfCusCreditUseHis.setCreditUseHisId(creditUseHisId);
		mfCusCreditUseHis = mfCusCreditUseHisFeign.getById(mfCusCreditUseHis);
		getObjValue(formcreditusehis0002, mfCusCreditUseHis,formData);
		if(mfCusCreditUseHis!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @param creditUseHisId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String creditUseHisId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusCreditUseHis mfCusCreditUseHis = new MfCusCreditUseHis();
		mfCusCreditUseHis.setCreditUseHisId(creditUseHisId);
		try {
			mfCusCreditUseHisFeign.delete(mfCusCreditUseHis);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
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
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData formcreditusehis0002 = formService.getFormData("creditusehis0002");
		model.addAttribute("formcreditusehis0002", formcreditusehis0002);
		model.addAttribute("query", "");
		return "/component/auth/MfCusCreditUseHis_Insert";
	}
	/***
	 * 新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formcreditusehis0002 = formService.getFormData("creditusehis0002");
		 getFormValue(formcreditusehis0002);
		MfCusCreditUseHis  mfCusCreditUseHis = new MfCusCreditUseHis();
		 setObjValue(formcreditusehis0002, mfCusCreditUseHis);
		 mfCusCreditUseHisFeign.insert(mfCusCreditUseHis);
		 getObjValue(formcreditusehis0002, mfCusCreditUseHis);
		 this.addActionMessage(model, "保存成功");
		 List<MfCusCreditUseHis> mfCusCreditUseHisList = (List<MfCusCreditUseHis>)mfCusCreditUseHisFeign.findByPage(this.getIpage(), mfCusCreditUseHis).getResult();
		model.addAttribute("formcreditusehis0002", formcreditusehis0002);
		model.addAttribute("query", "");
		return "/component/auth/MfCusCreditUseHis_Insert";
	}
	/**
	 * 查询
	 * @param creditUseHisId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData, String creditUseHisId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formcreditusehis0001 = formService.getFormData("creditusehis0001");
		 getFormValue(formcreditusehis0001);
		MfCusCreditUseHis  mfCusCreditUseHis = new MfCusCreditUseHis();
		mfCusCreditUseHis.setCreditUseHisId(creditUseHisId);
		 mfCusCreditUseHis = mfCusCreditUseHisFeign.getById(mfCusCreditUseHis);
		 getObjValue(formcreditusehis0001, mfCusCreditUseHis);
		model.addAttribute("formcreditusehis0001", formcreditusehis0001);
		model.addAttribute("query", "");
		return "/component/auth/MfCusCreditUseHis_Detail";
	}
	/**
	 * 删除
	 * @param creditUseHisId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String ajaxData, String creditUseHisId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		MfCusCreditUseHis mfCusCreditUseHis = new MfCusCreditUseHis();
		mfCusCreditUseHis.setCreditUseHisId(creditUseHisId);
		mfCusCreditUseHisFeign.delete(mfCusCreditUseHis);
		return getListPage(model);
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
		FormData  formcreditusehis0002 = formService.getFormData("creditusehis0002");
		 getFormValue(formcreditusehis0002);
		 boolean validateFlag = this.validateFormData(formcreditusehis0002);
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
		FormData  formcreditusehis0002 = formService.getFormData("creditusehis0002");
		 getFormValue(formcreditusehis0002);
		 boolean validateFlag = this.validateFormData(formcreditusehis0002);
	}
	
}
