package app.component.thirdservice.adapt.controller;
import app.component.common.EntityUtil;
import app.component.thirdservice.adapt.entity.MfThirdApiAdapt;
import app.component.thirdservice.adapt.feign.MfThirdApiAdaptFeign;
import app.util.toolkit.Ipage;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.google.gson.Gson;
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
 * Title: MfThirdApiAdaptController.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed Oct 10 09:43:55 CST 2018
 **/
@Controller
@RequestMapping(value = "/mfThirdApiAdapt")
public class MfThirdApiAdaptController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfThirdApiAdaptFeign mfThirdApiAdaptFeign;

	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/thirdservice/adapt/MfThirdApiAdapt_List";
	}


	@RequestMapping(value = "/getSettingPage")
	public String getSettingPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/thirdservice/adapt/MfThirdApiAdapt_setting";
	}


	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/findByPageAjax")
	public Map<String, Object> findByPageAjax(String ajaxData, String tableId, String tableType, Integer pageNo,Integer pageSize) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfThirdApiAdapt mfThirdApiAdapt = new MfThirdApiAdapt();
		try {
			mfThirdApiAdapt.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfThirdApiAdapt.setCriteriaList(mfThirdApiAdapt, ajaxData);//我的筛选
			//mfThirdApiAdapt.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfThirdApiAdapt,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setParams(this.setIpageParams("mfThirdApiAdapt", mfThirdApiAdapt));
			//自定义查询Feign方法
			ipage = mfThirdApiAdaptFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
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
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/findListAjax")
	public Map<String, Object> findListAjax() throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfThirdApiAdapt mfThirdApiAdapt = new MfThirdApiAdapt();
		try {
			//自定义查询Feign方法
			List<MfThirdApiAdapt> list= mfThirdApiAdaptFeign.getList(mfThirdApiAdapt);
			dataMap.put("list",list);
		} catch (Exception e) {
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
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			FormData formadapt0002 = formService.getFormData("adapt0002");
			getFormValue(formadapt0002, getMapByJson(ajaxData));
			if(this.validateFormData(formadapt0002)){
				MfThirdApiAdapt mfThirdApiAdapt = new MfThirdApiAdapt();
				setObjValue(formadapt0002, mfThirdApiAdapt);
				mfThirdApiAdaptFeign.insert(mfThirdApiAdapt);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
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
	@RequestMapping("/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formadapt0002 = formService.getFormData("adapt0002");
		getFormValue(formadapt0002, getMapByJson(ajaxData));
		MfThirdApiAdapt mfThirdApiAdaptJsp = new MfThirdApiAdapt();
		setObjValue(formadapt0002, mfThirdApiAdaptJsp);
		MfThirdApiAdapt mfThirdApiAdapt = mfThirdApiAdaptFeign.getById(mfThirdApiAdaptJsp);
		if(mfThirdApiAdapt!=null){
			try{
				mfThirdApiAdapt = (MfThirdApiAdapt)EntityUtil.reflectionSetVal(mfThirdApiAdapt, mfThirdApiAdaptJsp, getMapByJson(ajaxData));
				mfThirdApiAdaptFeign.update(mfThirdApiAdapt);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			}catch(Exception e){
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
	@RequestMapping("/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfThirdApiAdapt mfThirdApiAdapt = new MfThirdApiAdapt();
		try{
			FormData formadapt0002 = formService.getFormData("adapt0002");
			getFormValue(formadapt0002, getMapByJson(ajaxData));
			if(this.validateFormData(formadapt0002)){
				mfThirdApiAdapt = new MfThirdApiAdapt();
				setObjValue(formadapt0002, mfThirdApiAdapt);
				mfThirdApiAdaptFeign.update(mfThirdApiAdapt);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * 设置保存功能
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/saveSettingAjax")
	@ResponseBody
	public Map<String, Object> saveSettingAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Gson gson = new Gson();
		Map<String,Object> paramMap = gson.fromJson(ajaxData,Map.class);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfThirdApiAdapt mfThirdApiAdapt = new MfThirdApiAdapt();
		try{

			mfThirdApiAdaptFeign.saveSetting(paramMap);
			dataMap.put("flag", "success");
			dataMap.put("msg", "更新成功");

		}catch(Exception e){
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
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
	public Map<String, Object> getByIdAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formadapt0002 = formService.getFormData("adapt0002");
		MfThirdApiAdapt mfThirdApiAdapt = new MfThirdApiAdapt();
		mfThirdApiAdapt.setId(id);
		mfThirdApiAdapt = mfThirdApiAdaptFeign.getById(mfThirdApiAdapt);
		getObjValue(formadapt0002, mfThirdApiAdapt,formData);
		if(mfThirdApiAdapt!=null){
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
	public Map<String, Object> deleteAjax(String id) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfThirdApiAdapt mfThirdApiAdapt = new MfThirdApiAdapt();
		mfThirdApiAdapt.setId(id);
		try {
			mfThirdApiAdaptFeign.delete(mfThirdApiAdapt);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
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
	@RequestMapping("/input")
	public String input(Model model) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formadapt0002 = formService.getFormData("adapt0002");
		model.addAttribute("formadapt0002", formadapt0002);
		model.addAttribute("query", "");
		return "/component/thirdservice/adapt/MfThirdApiAdapt_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model,String id) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formadapt0001 = formService.getFormData("adapt0001");
		getFormValue(formadapt0001);
		MfThirdApiAdapt mfThirdApiAdapt = new MfThirdApiAdapt();
		mfThirdApiAdapt.setId(id);
		mfThirdApiAdapt = mfThirdApiAdaptFeign.getById(mfThirdApiAdapt);
		getObjValue(formadapt0001, mfThirdApiAdapt);
		model.addAttribute("formadapt0001", formadapt0001);
		model.addAttribute("query", "");
		return "/component/thirdservice/adapt/MfThirdApiAdapt_Detail";
	}

	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void validateInsert() throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formadapt0002 = formService.getFormData("adapt0002");
		getFormValue(formadapt0002);
		boolean validateFlag = this.validateFormData(formadapt0002);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void validateUpdate() throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formadapt0002 = formService.getFormData("adapt0002");
		getFormValue(formadapt0002);
		boolean validateFlag = this.validateFormData(formadapt0002);
	}
}
