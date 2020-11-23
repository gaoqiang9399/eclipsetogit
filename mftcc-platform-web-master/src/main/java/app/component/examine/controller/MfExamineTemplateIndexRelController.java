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
import app.component.examine.entity.MfExamineTemplateIndexRel;
import app.component.examine.feign.MfExamineTemplateIndexRelFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: MfExamineTemplateIndexRelAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Mon Jul 24 14:29:55 CST 2017
 **/
@Controller
@RequestMapping(value="/mfExamineTemplateIndexRel")
public class MfExamineTemplateIndexRelController extends BaseFormBean{
	//注入MfExamineTemplateIndexRelBo
	@Autowired
	private MfExamineTemplateIndexRelFeign mfExamineTemplateIndexRelFeign;

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
		return "MfExamineTemplateIndexRel_List";
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
		MfExamineTemplateIndexRel mfExamineTemplateIndexRel = new MfExamineTemplateIndexRel();
		try {
			mfExamineTemplateIndexRel.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfExamineTemplateIndexRel.setCriteriaList(mfExamineTemplateIndexRel, ajaxData);//我的筛选
			//mfExamineTemplateIndexRel.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfExamineTemplateIndexRel,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = mfExamineTemplateIndexRelFeign.findByPage(ipage, mfExamineTemplateIndexRel);
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
			FormData formexamindexrel0002 = formService.getFormData("examindexrel0002");
			getFormValue(formexamindexrel0002, getMapByJson(ajaxData));
			if(this.validateFormData(formexamindexrel0002)){
				MfExamineTemplateIndexRel mfExamineTemplateIndexRel = new MfExamineTemplateIndexRel();
				setObjValue(formexamindexrel0002, mfExamineTemplateIndexRel);
				//mfExamineTemplateIndexRelFeign.insert(mfExamineTemplateIndexRel);
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
	
	@ResponseBody
	@RequestMapping(value="/insertIndexRelAjax")
	public Map<String,Object> insertIndexRelAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		dataMap = new HashMap<String, Object>(); 
		try{
			JSONObject indexRelJsonObj = JSONObject.fromObject(ajaxData);
			dataMap=mfExamineTemplateIndexRelFeign.insert(indexRelJsonObj);
			dataMap.put("entityData", dataMap.get("mfExamineTemplateIndexRel"));
			dataMap.put("indexReloList", dataMap.get("indexReloList"));
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	@ResponseBody
	@RequestMapping(value="/updateIndexRelAjax")
	public Map<String,Object> updateIndexRelAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		dataMap = new HashMap<String, Object>(); 
		MfExamineTemplateIndexRel mfExamineTemplateIndexRel = new MfExamineTemplateIndexRel();
		try{
			JSONObject indexRelJsonObj = JSONObject.fromObject(ajaxData);
			dataMap=mfExamineTemplateIndexRelFeign.update(indexRelJsonObj);
			dataMap.put("entityData", dataMap.get("mfExamineTemplateIndexRel"));
			dataMap.put("indexReloList", dataMap.get("indexReloList"));
			dataMap.put("flag", "success");
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
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
		FormData formexamindexrel0002 = formService.getFormData("examindexrel0002");
		getFormValue(formexamindexrel0002, getMapByJson(ajaxData));
		MfExamineTemplateIndexRel mfExamineTemplateIndexRelJsp = new MfExamineTemplateIndexRel();
		setObjValue(formexamindexrel0002, mfExamineTemplateIndexRelJsp);
		MfExamineTemplateIndexRel mfExamineTemplateIndexRel = mfExamineTemplateIndexRelFeign.getById(mfExamineTemplateIndexRelJsp);
		if(mfExamineTemplateIndexRel!=null){
			try{
				mfExamineTemplateIndexRel = (MfExamineTemplateIndexRel)EntityUtil.reflectionSetVal(mfExamineTemplateIndexRel, mfExamineTemplateIndexRelJsp, getMapByJson(ajaxData));
				//mfExamineTemplateIndexRelFeign.update(mfExamineTemplateIndexRel);
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
		MfExamineTemplateIndexRel mfExamineTemplateIndexRel = new MfExamineTemplateIndexRel();
		try{
			FormData formexamindexrel0002 = formService.getFormData("examindexrel0002");
			getFormValue(formexamindexrel0002, getMapByJson(ajaxData));
			if(this.validateFormData(formexamindexrel0002)){
				mfExamineTemplateIndexRel = new MfExamineTemplateIndexRel();
				setObjValue(formexamindexrel0002, mfExamineTemplateIndexRel);
				//mfExamineTemplateIndexRelFeign.update(mfExamineTemplateIndexRel);
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
	public Map<String,Object> getByIdAjax(String templateId,String indexId) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		dataMap = new HashMap<String, Object>(); 
		FormData formexamindexrel0002 = formService.getFormData("examindexrel0002");
		MfExamineTemplateIndexRel mfExamineTemplateIndexRel = new MfExamineTemplateIndexRel();
		mfExamineTemplateIndexRel.setExamTemplateId(templateId);
		mfExamineTemplateIndexRel.setIndexId(indexId);
		mfExamineTemplateIndexRel = mfExamineTemplateIndexRelFeign.getById(mfExamineTemplateIndexRel);
		getObjValue(formexamindexrel0002, mfExamineTemplateIndexRel,formData);
		if(mfExamineTemplateIndexRel!=null){
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
	public Map<String,Object> deleteAjax(String templateId,String indexId) throws Exception{
		ActionContext.initialize(request,
				response);
		dataMap = new HashMap<String, Object>(); 
		MfExamineTemplateIndexRel mfExamineTemplateIndexRel = new MfExamineTemplateIndexRel();
		mfExamineTemplateIndexRel.setExamTemplateId(templateId);
		mfExamineTemplateIndexRel.setIndexId(indexId);
		try {
			mfExamineTemplateIndexRelFeign.delete(mfExamineTemplateIndexRel);
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
		FormData formexamindexrel0002 = formService.getFormData("examindexrel0002");
		model.addAttribute("formexamindexrel0002", formexamindexrel0002);
		return "MfExamineTemplateIndexRel_Insert";
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
		 FormData formexamindexrel0002 = formService.getFormData("examindexrel0002");
		 getFormValue(formexamindexrel0002);
		 MfExamineTemplateIndexRel mfExamineTemplateIndexRel = new MfExamineTemplateIndexRel();
		 setObjValue(formexamindexrel0002, mfExamineTemplateIndexRel);
		 //mfExamineTemplateIndexRelFeign.insert(mfExamineTemplateIndexRel);
		 getObjValue(formexamindexrel0002, mfExamineTemplateIndexRel);
		 this.addActionMessage(model,"保存成功");
		 List<MfExamineTemplateIndexRel> mfExamineTemplateIndexRelList = (List<MfExamineTemplateIndexRel>)mfExamineTemplateIndexRelFeign.findByPage(this.getIpage(), mfExamineTemplateIndexRel).getResult();
		 model.addAttribute("formexamindexrel0002", formexamindexrel0002);
		 model.addAttribute("mfExamineTemplateIndexRelList", mfExamineTemplateIndexRelList);
		 
		return "MfExamineTemplateIndexRel_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getById")
	public String getById(Model model,String templateId,String indexId) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formexamindexrel0001 = formService.getFormData("examindexrel0001");
		 getFormValue(formexamindexrel0001);
		 MfExamineTemplateIndexRel mfExamineTemplateIndexRel = new MfExamineTemplateIndexRel();
		mfExamineTemplateIndexRel.setExamTemplateId(templateId);
		mfExamineTemplateIndexRel.setIndexId(indexId);
		 mfExamineTemplateIndexRel = mfExamineTemplateIndexRelFeign.getById(mfExamineTemplateIndexRel);
		 getObjValue(formexamindexrel0001, mfExamineTemplateIndexRel);
		 model.addAttribute("formexamindexrel0001", formexamindexrel0001);
		return "MfExamineTemplateIndexRel_Detail";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public String delete(String templateId,String indexId) throws Exception {
		ActionContext.initialize(request,
				response);
		MfExamineTemplateIndexRel mfExamineTemplateIndexRel = new MfExamineTemplateIndexRel();
		mfExamineTemplateIndexRel.setExamTemplateId(templateId);
		mfExamineTemplateIndexRel.setIndexId(indexId);
		mfExamineTemplateIndexRelFeign.delete(mfExamineTemplateIndexRel);
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
		 FormData formexamindexrel0002 = formService.getFormData("examindexrel0002");
		 getFormValue(formexamindexrel0002);
		 boolean validateFlag = this.validateFormData(formexamindexrel0002);
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
		 FormData formexamindexrel0002 = formService.getFormData("examindexrel0002");
		 getFormValue(formexamindexrel0002);
		 boolean validateFlag = this.validateFormData(formexamindexrel0002);
	}
	
	
}
