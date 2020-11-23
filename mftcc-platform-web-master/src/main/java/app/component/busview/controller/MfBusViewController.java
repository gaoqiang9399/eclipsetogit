package  app.component.busview.controller;
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

import app.component.busview.entity.MfBusView;
import app.component.busview.feign.MfBusViewFeign;
import app.component.common.EntityUtil;
import app.util.toolkit.Ipage;

/**
 * Title: MfBusViewAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed Jul 26 15:18:44 CST 2017
 **/
@Controller
@RequestMapping("/mfBusView")
public class MfBusViewController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfBusViewBo
	@Autowired
	private MfBusViewFeign mfBusViewFeign;
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		return "/component/busview/MfBusView_List";
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
		MfBusView mfBusView = new MfBusView();
		try {
			mfBusView.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfBusView.setCriteriaList(mfBusView, ajaxData);//我的筛选
			//this.getRoleConditions(mfBusView,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = mfBusViewFeign.findByPage(ipage, mfBusView);
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
			throw new Exception(e.getMessage());
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
		FormData 	formbusview0002 = formService.getFormData("busview0002");
			getFormValue(formbusview0002, getMapByJson(ajaxData));
			if(this.validateFormData(formbusview0002)){
		MfBusView mfBusView = new MfBusView();
				setObjValue(formbusview0002, mfBusView);
				mfBusViewFeign.insert(mfBusView);
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
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formbusview0002 = formService.getFormData("busview0002");
		getFormValue(formbusview0002, getMapByJson(ajaxData));
		MfBusView mfBusViewJsp = new MfBusView();
		setObjValue(formbusview0002, mfBusViewJsp);
		MfBusView mfBusView = mfBusViewFeign.getById(mfBusViewJsp);
		if(mfBusView!=null){
			try{
				mfBusView = (MfBusView)EntityUtil.reflectionSetVal(mfBusView, mfBusViewJsp, getMapByJson(ajaxData));
				mfBusViewFeign.update(mfBusView);
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
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		FormData 	formbusview0002 = formService.getFormData("busview0002");
			getFormValue(formbusview0002, getMapByJson(ajaxData));
			if(this.validateFormData(formbusview0002)){
		MfBusView mfBusView = new MfBusView();
				setObjValue(formbusview0002, mfBusView);
				mfBusViewFeign.update(mfBusView);
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
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String busViewId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formbusview0002 = formService.getFormData("busview0002");
		MfBusView mfBusView = new MfBusView();
		mfBusView.setBusViewId(busViewId);
		mfBusView = mfBusViewFeign.getById(mfBusView);
		getObjValue(formbusview0002, mfBusView,formData);
		if(mfBusView!=null){
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
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String busViewId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfBusView mfBusView = new MfBusView();
		mfBusView.setBusViewId(busViewId);
		try {
			mfBusViewFeign.delete(mfBusView);
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
	public String input(Model model) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData formbusview0002 = formService.getFormData("busview0002");
		model.addAttribute("formbusview0002", formbusview0002);
		model.addAttribute("query", "");
		return "/component/busview/MfBusView_Insert";
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
		FormData  formbusview0002 = formService.getFormData("busview0002");
		 getFormValue(formbusview0002);
		MfBusView  mfBusView = new MfBusView();
		 setObjValue(formbusview0002, mfBusView);
		 mfBusViewFeign.insert(mfBusView);
		 getObjValue(formbusview0002, mfBusView);
		 this.addActionMessage(model, "保存成功");
		 List<MfBusView> mfBusViewList = (List<MfBusView>)mfBusViewFeign.findByPage(this.getIpage(), mfBusView).getResult();
		model.addAttribute("formbusview0002", formbusview0002);
		model.addAttribute("query", "");
		return "/component/busview/MfBusView_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String busViewId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formbusview0001 = formService.getFormData("busview0001");
		 getFormValue(formbusview0001);
		MfBusView  mfBusView = new MfBusView();
		mfBusView.setBusViewId(busViewId);
		 mfBusView = mfBusViewFeign.getById(mfBusView);
		 getObjValue(formbusview0001, mfBusView);
		model.addAttribute("formbusview0001", formbusview0001);
		model.addAttribute("query", "");
		return "/component/busview/MfBusView_Detail";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String busViewId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		MfBusView mfBusView = new MfBusView();
		mfBusView.setBusViewId(busViewId);
		mfBusViewFeign.delete(mfBusView);
		return getListPage(model);
	}
	
}
