package  app.component.prdct.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.model.entity.MfSysTemplate;
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

import app.component.prdct.entity.MfKindNodeTemplate;
import app.component.prdct.feign.MfKindNodeTemplateFeign;
import app.util.toolkit.Ipage;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Title: MfKindNodeTemplateAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Tue Jul 04 12:30:02 CST 2017
 **/
@Controller
@RequestMapping("/mfKindNodeTemplate")
public class MfKindNodeTemplateController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfKindNodeTemplateBo
	@Autowired
	private MfKindNodeTemplateFeign mfKindNodeTemplateFeign;
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
		ActionContext.initialize(request,
				response);
		model.addAttribute("query", "");
		return "/component/prdct/MfKindNodeTemplate_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfKindNodeTemplate mfKindNodeTemplate = new MfKindNodeTemplate();
		try {
			mfKindNodeTemplate.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfKindNodeTemplate.setCriteriaList(mfKindNodeTemplate, ajaxData);//我的筛选
			//mfKindNodeTemplate.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfKindNodeTemplate,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = mfKindNodeTemplateFeign.findByPage(ipage, mfKindNodeTemplate);
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
	 * @描述 展期节点配置模板保存
	 * @参数 [kindNo, nodeNo, templateNo]
	 * @返回值 java.util.Map<java.lang.String,java.lang.Object>
	 * @创建人  shenhaobing
	 * @创建时间 2019/9/23
	 * @修改人和其它信息
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String kindNo,String nodeNo,String templateNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try{
			MfKindNodeTemplate 	mfKindNodeTemplate = new MfKindNodeTemplate();
			mfKindNodeTemplate.setKindNo(kindNo);
			mfKindNodeTemplate.setNodeNo(nodeNo);
			mfKindNodeTemplate.setTemplateNo(templateNo);
			dataMap = mfKindNodeTemplateFeign.insert(mfKindNodeTemplate);
			dataMap.put("flag", "success");
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * @描述 产品配置中节点配置模板保存
	 * @参数 [ajaxData]
	 * @返回值 java.util.Map<java.lang.String,java.lang.Object>
	 * @创建人  shenhaobing
	 * @创建时间 2019/9/23
	 * @修改人和其它信息
	 */
	@RequestMapping(value = "/insertForKindAjax")
	@ResponseBody
	public Map<String, Object> insertForKindAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormService formService = new FormService();
		try{
			FormData formnodetemplate0001 = formService.getFormData("nodetemplate0001");
			getFormValue(formnodetemplate0001, getMapByJson(ajaxData));
			if(this.validateFormData(formnodetemplate0001)){
				MfKindNodeTemplate 	mfKindNodeTemplate = new MfKindNodeTemplate();
				setObjValue(formnodetemplate0001, mfKindNodeTemplate);
				dataMap = mfKindNodeTemplateFeign.insertForKind(mfKindNodeTemplate);
				dataMap.put("flag", "success");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
			dataMap.put("flag", "success");
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
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
		MfKindNodeTemplate mfKindNodeTemplate = new MfKindNodeTemplate();
		try{
			JSONObject jb = JSONObject.fromObject(ajaxData);
			mfKindNodeTemplate = (MfKindNodeTemplate)JSONObject.toBean(jb, MfKindNodeTemplate.class);
			mfKindNodeTemplateFeign.update(mfKindNodeTemplate);
			dataMap.put("flag", "success");
			dataMap.put("msg", "更新成功");
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}


	/**
	 * Ajax异步删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String kindNodeTemplateId) throws Exception{
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfKindNodeTemplate mfKindNodeTemplate = new MfKindNodeTemplate();
		mfKindNodeTemplate.setKindNodeTemplateId(kindNodeTemplateId);
		try {
			mfKindNodeTemplateFeign.delete(mfKindNodeTemplate);
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
	@RequestMapping(value = "/input")
	public String input(Model model,String kindNo, String busModel,String nodeNo) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		MfKindNodeTemplate mfKindNodeTemplate = new MfKindNodeTemplate();
		mfKindNodeTemplate.setKindNo(kindNo);
		mfKindNodeTemplate.setBusModel(busModel);
		mfKindNodeTemplate.setNodeNo(nodeNo);
		FormData formnodetemplate0001 = formService.getFormData("nodetemplate0001");
		getObjValue(formnodetemplate0001, mfKindNodeTemplate);
		model.addAttribute("formnodetemplate0001", formnodetemplate0001);
		model.addAttribute("kindNo", kindNo);
		model.addAttribute("busModel", busModel);
		model.addAttribute("query", "");
		return "/component/prdct/MfKindNodeTemplate_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String kindNodeTemplateId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formnodetemplate0001 = formService.getFormData("nodetemplate0001");
		getFormValue(formnodetemplate0001);
		MfKindNodeTemplate mfKindNodeTemplate = new MfKindNodeTemplate();
		mfKindNodeTemplate.setKindNodeTemplateId(kindNodeTemplateId);
		mfKindNodeTemplate = mfKindNodeTemplateFeign.getById(mfKindNodeTemplate);
		getObjValue(formnodetemplate0001, mfKindNodeTemplate);
		model.addAttribute("formnodetemplate0001", formnodetemplate0001);
		model.addAttribute("query", "");
		return "/component/prdct/MfKindNodeTemplate_Detail";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String kindNodeTemplateId) throws Exception {
		ActionContext.initialize(request,
				response);
		MfKindNodeTemplate mfKindNodeTemplate = new MfKindNodeTemplate();
		mfKindNodeTemplate.setKindNodeTemplateId(kindNodeTemplateId);
		mfKindNodeTemplateFeign.delete(mfKindNodeTemplate);
		return getListPage(model);
	}


	@RequestMapping(value = "/getTemplateForNodeList")
	public String getTemplateForNodeList(Model model,String ajaxData,String kindNo,String nodeNo) throws Exception{
		ActionContext.initialize(request,response);
		List<MfSysTemplate> lindTemplateList = mfKindNodeTemplateFeign.getTemplateForNodeList(kindNo,nodeNo);
		JSONArray jsonArray = JSONArray.fromObject(lindTemplateList);
		for(int index=0;index<jsonArray.size();index++){
			jsonArray.getJSONObject(index).put("id", jsonArray.getJSONObject(index).getString("templateNo"));
			jsonArray.getJSONObject(index).put("name", jsonArray.getJSONObject(index).getString("templateNameZh"));
		}
		ajaxData = jsonArray.toString();
		model.addAttribute("query", "");
		model.addAttribute("ajaxData",ajaxData);
		model.addAttribute("jsonArray",jsonArray);
		return "/component/prdct/MfKindNode_List";
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
		FormData  formnodetemplate0002 = formService.getFormData("nodetemplate0002");
		getFormValue(formnodetemplate0002);
		boolean validateFlag = this.validateFormData(formnodetemplate0002);
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
		FormData  formnodetemplate0002 = formService.getFormData("nodetemplate0002");
		getFormValue(formnodetemplate0002);
		boolean validateFlag = this.validateFormData(formnodetemplate0002);
	}

}
