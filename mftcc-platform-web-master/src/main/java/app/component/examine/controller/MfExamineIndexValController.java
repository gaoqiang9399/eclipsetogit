package  app.component.examine.controller;
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
import app.component.examine.entity.MfExamineIndexVal;
import app.component.examine.feign.MfExamineIndexValFeign;
import app.util.toolkit.Ipage;

/**
 * Title: MfExamineIndexValAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed Jul 26 23:52:34 CST 2017
 **/
@Controller
@RequestMapping(value="/MfExamineIndexValController")
public class MfExamineIndexValController extends BaseFormBean{
	//注入MfExamineIndexValBo
	@Autowired
	private MfExamineIndexValFeign mfExamineIndexValFeign;
	//全局变量
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
	private Map<String,Object> dataMap;
	
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request,
				response);
		return "MfExamineIndexVal_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/findByPageAjax")
	public Map<String,Object> findByPageAjax(String ajaxData,int pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request,
				response);
		dataMap = new HashMap<String, Object>(); 
		MfExamineIndexVal mfExamineIndexVal = new MfExamineIndexVal();
		try {
			mfExamineIndexVal.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfExamineIndexVal.setCriteriaList(mfExamineIndexVal, ajaxData);//我的筛选
			//mfExamineIndexVal.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfExamineIndexVal,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = mfExamineIndexValFeign.findByPage(ipage, mfExamineIndexVal);
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
	@ResponseBody
	@RequestMapping(value="/insertAjax")
	public Map<String,Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		dataMap = new HashMap<String, Object>(); 
		try{
			FormData formexamindexval0002 = formService.getFormData("examindexval0002");
			getFormValue(formexamindexval0002, getMapByJson(ajaxData));
			if(this.validateFormData(formexamindexval0002)){
				MfExamineIndexVal mfExamineIndexVal = new MfExamineIndexVal();
				setObjValue(formexamindexval0002, mfExamineIndexVal);
				mfExamineIndexValFeign.insert(mfExamineIndexVal);
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
	@ResponseBody
	@RequestMapping(value="/updateAjaxByOne")
	public Map<String,Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		dataMap = new HashMap<String, Object>();
		FormData formexamindexval0002 = formService.getFormData("examindexval0002");
		getFormValue(formexamindexval0002, getMapByJson(ajaxData));
		MfExamineIndexVal mfExamineIndexValJsp = new MfExamineIndexVal();
		setObjValue(formexamindexval0002, mfExamineIndexValJsp);
		MfExamineIndexVal mfExamineIndexVal = mfExamineIndexValFeign.getById(mfExamineIndexValJsp);
		if(mfExamineIndexVal!=null){
			try{
				mfExamineIndexVal = (MfExamineIndexVal)EntityUtil.reflectionSetVal(mfExamineIndexVal, mfExamineIndexValJsp, getMapByJson(ajaxData));
				mfExamineIndexValFeign.update(mfExamineIndexVal);
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
	@ResponseBody
	@RequestMapping(value="/updateAjax")
	public Map<String,Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		dataMap = new HashMap<String, Object>(); 
		MfExamineIndexVal mfExamineIndexVal = new MfExamineIndexVal();
		try{
			FormData formexamindexval0002 = formService.getFormData("examindexval0002");
			getFormValue(formexamindexval0002, getMapByJson(ajaxData));
			if(this.validateFormData(formexamindexval0002)){
				mfExamineIndexVal = new MfExamineIndexVal();
				setObjValue(formexamindexval0002, mfExamineIndexVal);
				mfExamineIndexValFeign.update(mfExamineIndexVal);
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
	@ResponseBody
	@RequestMapping(value="/getByIdAjax")
	public Map<String,Object> getByIdAjax(String indexValId) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		dataMap = new HashMap<String, Object>(); 
		FormData formexamindexval0002 = formService.getFormData("examindexval0002");
		MfExamineIndexVal mfExamineIndexVal = new MfExamineIndexVal();
		mfExamineIndexVal.setIndexValId(indexValId);
		mfExamineIndexVal = mfExamineIndexValFeign.getById(mfExamineIndexVal);
		getObjValue(formexamindexval0002, mfExamineIndexVal,formData);
		if(mfExamineIndexVal!=null){
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
	@ResponseBody
	@RequestMapping(value="/deleteAjax")
	public Map<String,Object> deleteAjax(String indexValId) throws Exception{
		ActionContext.initialize(request,
				response);
		dataMap = new HashMap<String, Object>(); 
		MfExamineIndexVal mfExamineIndexVal = new MfExamineIndexVal();
		mfExamineIndexVal.setIndexValId(indexValId);
		try {
			mfExamineIndexValFeign.delete(mfExamineIndexVal);
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
	@RequestMapping(value="/input")
	public String input(Model model) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formexamindexval0002 = formService.getFormData("examindexval0002");
		model.addAttribute("formexamindexval0002", formexamindexval0002);
		return "MfExamineIndexVal_Insert";
	}
	/***
	 * 新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/insert")
	public String insert(Model model) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formexamindexval0002 = formService.getFormData("examindexval0002");
		 getFormValue(formexamindexval0002);
		 MfExamineIndexVal mfExamineIndexVal = new MfExamineIndexVal();
		 setObjValue(formexamindexval0002, mfExamineIndexVal);
		 mfExamineIndexValFeign.insert(mfExamineIndexVal);
		 getObjValue(formexamindexval0002, mfExamineIndexVal);
		 this.addActionMessage(model,"保存成功");
		 List<MfExamineIndexVal> mfExamineIndexValList = (List<MfExamineIndexVal>)mfExamineIndexValFeign.findByPage(this.getIpage(), mfExamineIndexVal).getResult();
		 model.addAttribute("formexamindexval0002", formexamindexval0002);
		 model.addAttribute("mfExamineIndexValList", mfExamineIndexValList);
		return "MfExamineIndexVal_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getById")
	public String getById(Model model,String indexValId) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		 FormData formexamindexval0001 = formService.getFormData("examindexval0001");
		 getFormValue(formexamindexval0001);
		 MfExamineIndexVal mfExamineIndexVal = new MfExamineIndexVal();
		mfExamineIndexVal.setIndexValId(indexValId);
		 mfExamineIndexVal = mfExamineIndexValFeign.getById(mfExamineIndexVal);
		 getObjValue(formexamindexval0001, mfExamineIndexVal);
		 model.addAttribute("formexamindexval0001", formexamindexval0001);
		return "MfExamineIndexVal_Detail";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public String delete(Model model,String indexValId) throws Exception {
		ActionContext.initialize(request,
				response);
		MfExamineIndexVal mfExamineIndexVal = new MfExamineIndexVal();
		mfExamineIndexVal.setIndexValId(indexValId);
		mfExamineIndexValFeign.delete(mfExamineIndexVal);
		return getListPage();
	}
	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/validateInsert")
	public void validateInsert() throws Exception{
		 ActionContext.initialize(request, response);
		 FormService formService = new FormService();
		 FormData formexamindexval0002 = formService.getFormData("examindexval0002");
		 getFormValue(formexamindexval0002);
		 boolean validateFlag = this.validateFormData(formexamindexval0002);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/validateUpdate")
	public void validateUpdate() throws Exception{
		 ActionContext.initialize(request, response);
		 FormService formService = new FormService();
		 FormData formexamindexval0002 = formService.getFormData("examindexval0002");
		 getFormValue(formexamindexval0002);
		 boolean validateFlag = this.validateFormData(formexamindexval0002);
	}
	
	
	
}
