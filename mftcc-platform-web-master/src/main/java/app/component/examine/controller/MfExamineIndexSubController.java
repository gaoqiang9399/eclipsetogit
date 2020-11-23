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
import app.component.examine.entity.MfExamineIndexSub;
import app.component.examine.feign.MfExamineIndexSubFeign;
import app.util.toolkit.Ipage;

/**
 * Title: MfExamineIndexSubAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Mon Jul 24 14:27:45 CST 2017
 **/
@Controller
@RequestMapping(value="/MfExamineIndexSubController")
public class MfExamineIndexSubController extends BaseFormBean{
	//注入MfExamineIndexSubBo
	@Autowired
	private MfExamineIndexSubFeign mfExamineIndexSubFeign;
	//全局变量
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//异步参数
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
		return "MfExamineIndexSub_List";
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
		MfExamineIndexSub mfExamineIndexSub = new MfExamineIndexSub();
		try {
			mfExamineIndexSub.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfExamineIndexSub.setCriteriaList(mfExamineIndexSub, ajaxData);//我的筛选
			//mfExamineIndexSub.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfExamineIndexSub,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = mfExamineIndexSubFeign.findByPage(ipage, mfExamineIndexSub);
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
			FormData formexamindexsub0002 = formService.getFormData("examindexsub0002");
			getFormValue(formexamindexsub0002, getMapByJson(ajaxData));
			if(this.validateFormData(formexamindexsub0002)){
				MfExamineIndexSub mfExamineIndexSub = new MfExamineIndexSub();
				setObjValue(formexamindexsub0002, mfExamineIndexSub);
				mfExamineIndexSubFeign.insert(mfExamineIndexSub);
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
		FormData formexamindexsub0002 = formService.getFormData("examindexsub0002");
		getFormValue(formexamindexsub0002, getMapByJson(ajaxData));
		MfExamineIndexSub mfExamineIndexSubJsp = new MfExamineIndexSub();
		setObjValue(formexamindexsub0002, mfExamineIndexSubJsp);
		MfExamineIndexSub mfExamineIndexSub = mfExamineIndexSubFeign.getById(mfExamineIndexSubJsp);
		if(mfExamineIndexSub!=null){
			try{
				mfExamineIndexSub = (MfExamineIndexSub)EntityUtil.reflectionSetVal(mfExamineIndexSub, mfExamineIndexSubJsp, getMapByJson(ajaxData));
				mfExamineIndexSubFeign.update(mfExamineIndexSub);
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
		MfExamineIndexSub mfExamineIndexSub = new MfExamineIndexSub();
		try{
			FormData formexamindexsub0002 = formService.getFormData("examindexsub0002");
			getFormValue(formexamindexsub0002, getMapByJson(ajaxData));
			if(this.validateFormData(formexamindexsub0002)){
				mfExamineIndexSub = new MfExamineIndexSub();
				setObjValue(formexamindexsub0002, mfExamineIndexSub);
				mfExamineIndexSubFeign.update(mfExamineIndexSub);
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
	public Map<String,Object> getByIdAjax(String subId) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		dataMap = new HashMap<String, Object>(); 
		FormData formexamindexsub0002 = formService.getFormData("examindexsub0002");
		MfExamineIndexSub mfExamineIndexSub = new MfExamineIndexSub();
		mfExamineIndexSub.setSubId(subId);
		mfExamineIndexSub = mfExamineIndexSubFeign.getById(mfExamineIndexSub);
		getObjValue(formexamindexsub0002, mfExamineIndexSub,formData);
		if(mfExamineIndexSub!=null){
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
	public Map<String,Object> deleteAjax(String subId) throws Exception{
		ActionContext.initialize(request,
				response);
		
		dataMap = new HashMap<String, Object>(); 
		MfExamineIndexSub mfExamineIndexSub = new MfExamineIndexSub();
		mfExamineIndexSub.setSubId(subId);
		try {
			mfExamineIndexSubFeign.delete(mfExamineIndexSub);
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
		FormData formexamindexsub0002 = formService.getFormData("examindexsub0002");
		model.addAttribute("formexamindexsub0002", formexamindexsub0002);
		return "MfExamineIndexSub_Insert";
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
		 FormData formexamindexsub0002 = formService.getFormData("examindexsub0002");
		 getFormValue(formexamindexsub0002);
		 MfExamineIndexSub mfExamineIndexSub = new MfExamineIndexSub();
		 setObjValue(formexamindexsub0002, mfExamineIndexSub);
		 mfExamineIndexSubFeign.insert(mfExamineIndexSub);
		 getObjValue(formexamindexsub0002, mfExamineIndexSub);
		 this.addActionMessage(model,"保存成功");
		 List<MfExamineIndexSub> mfExamineIndexSubList = (List<MfExamineIndexSub>)mfExamineIndexSubFeign.findByPage(this.getIpage(), mfExamineIndexSub).getResult();
		 model.addAttribute("formexamindexsub0002", formexamindexsub0002);
		 model.addAttribute("mfExamineIndexSubList", mfExamineIndexSubList);
		return "MfExamineIndexSub_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getById")
	public String getById(Model model,String subId) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		 FormData formexamindexsub0001 = formService.getFormData("examindexsub0001");
		 getFormValue(formexamindexsub0001);
		 MfExamineIndexSub mfExamineIndexSub = new MfExamineIndexSub();
		mfExamineIndexSub.setSubId(subId);
		 mfExamineIndexSub = mfExamineIndexSubFeign.getById(mfExamineIndexSub);
		 getObjValue(formexamindexsub0001, mfExamineIndexSub);
		 model.addAttribute("formexamindexsub0001", formexamindexsub0001);
		return "MfExamineIndexSub_Detail";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public String delete(String subId) throws Exception {
		ActionContext.initialize(request,
				response);
		MfExamineIndexSub mfExamineIndexSub = new MfExamineIndexSub();
		mfExamineIndexSub.setSubId(subId);
		mfExamineIndexSubFeign.delete(mfExamineIndexSub);
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
		 FormData formexamindexsub0002 = formService.getFormData("examindexsub0002");
		 getFormValue(formexamindexsub0002);
		 boolean validateFlag = this.validateFormData(formexamindexsub0002);
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
		 FormData formexamindexsub0002 = formService.getFormData("examindexsub0002");
		 getFormValue(formexamindexsub0002);
		 boolean validateFlag = this.validateFormData(formexamindexsub0002);
	}
	
	
}
