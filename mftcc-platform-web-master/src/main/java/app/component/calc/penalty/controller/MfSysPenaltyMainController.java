package  app.component.calc.penalty.controller;
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

import app.component.calc.penalty.entity.MfSysPenaltyMain;
import app.component.calc.penalty.feign.MfSysPenaltyMainFeign;
import app.component.common.EntityUtil;
import app.util.toolkit.Ipage;

/**
 * Title: MfSysPenaltyMainAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Tue Jun 27 09:04:25 CST 2017
 **/
@Controller
@RequestMapping("/mfSysPenaltyMain")
public class MfSysPenaltyMainController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfSysPenaltyMainBo
	@Autowired
	private MfSysPenaltyMainFeign mfSysPenaltyMainFeign;
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
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		return "/component/calc/penalty/MfSysPenaltyMain_List";
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
		MfSysPenaltyMain mfSysPenaltyMain = new MfSysPenaltyMain();
		try {
			mfSysPenaltyMain.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfSysPenaltyMain.setCriteriaList(mfSysPenaltyMain, ajaxData);//我的筛选
			//mfSysPenaltyMain.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfSysPenaltyMain,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = mfSysPenaltyMainFeign.findByPage(ipage, mfSysPenaltyMain);
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
		FormData 	formpenalty0002 = formService.getFormData("penalty0002");
			getFormValue(formpenalty0002, getMapByJson(ajaxData));
			if(this.validateFormData(formpenalty0002)){
		MfSysPenaltyMain mfSysPenaltyMain = new MfSysPenaltyMain();
				setObjValue(formpenalty0002, mfSysPenaltyMain);
				mfSysPenaltyMainFeign.insert(mfSysPenaltyMain);
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
		FormData formpenalty0002 = formService.getFormData("penalty0002");
		getFormValue(formpenalty0002, getMapByJson(ajaxData));
		MfSysPenaltyMain mfSysPenaltyMainJsp = new MfSysPenaltyMain();
		setObjValue(formpenalty0002, mfSysPenaltyMainJsp);
		MfSysPenaltyMain mfSysPenaltyMain = mfSysPenaltyMainFeign.getById(mfSysPenaltyMainJsp);
		if(mfSysPenaltyMain!=null){
			try{
				mfSysPenaltyMain = (MfSysPenaltyMain)EntityUtil.reflectionSetVal(mfSysPenaltyMain, mfSysPenaltyMainJsp, getMapByJson(ajaxData));
				mfSysPenaltyMainFeign.update(mfSysPenaltyMain);
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
		MfSysPenaltyMain mfSysPenaltyMain = new MfSysPenaltyMain();
		try{
		FormData 	formpenalty0002 = formService.getFormData("penalty0002");
			getFormValue(formpenalty0002, getMapByJson(ajaxData));
			if(this.validateFormData(formpenalty0002)){
		MfSysPenaltyMain mfSysPenaltyMain1 = new MfSysPenaltyMain();
				setObjValue(formpenalty0002, mfSysPenaltyMain1);
				mfSysPenaltyMainFeign.update(mfSysPenaltyMain1);
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
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData,String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formpenalty0002 = formService.getFormData("penalty0002");
		MfSysPenaltyMain mfSysPenaltyMain = new MfSysPenaltyMain();
		mfSysPenaltyMain.setId(id);
		mfSysPenaltyMain = mfSysPenaltyMainFeign.getById(mfSysPenaltyMain);
		getObjValue(formpenalty0002, mfSysPenaltyMain,formData);
		if(mfSysPenaltyMain!=null){
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
	public Map<String, Object> deleteAjax(String ajaxData,String id) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfSysPenaltyMain mfSysPenaltyMain = new MfSysPenaltyMain();
		mfSysPenaltyMain.setId(id);
		try {
			mfSysPenaltyMainFeign.delete(mfSysPenaltyMain);
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
		FormData formpenalty0002 = formService.getFormData("penalty0002");
		model.addAttribute("formpenalty0002", formpenalty0002);
		model.addAttribute("query", "");
		return "/component/calc/penalty/MfSysPenaltyMain_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData,String id) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formpenalty0001 = formService.getFormData("penalty0001");
		 getFormValue(formpenalty0001);
		MfSysPenaltyMain  mfSysPenaltyMain = new MfSysPenaltyMain();
		mfSysPenaltyMain.setId(id);
		 mfSysPenaltyMain = mfSysPenaltyMainFeign.getById(mfSysPenaltyMain);
		 getObjValue(formpenalty0001, mfSysPenaltyMain);
		model.addAttribute("formpenalty0001", formpenalty0001);
		model.addAttribute("query", "");
		return "/component/calc/penalty/MfSysPenaltyMain_Detail";
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
		FormData  formpenalty0002 = formService.getFormData("penalty0002");
		 getFormValue(formpenalty0002);
		 boolean validateFlag = this.validateFormData(formpenalty0002);
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
		FormData  formpenalty0002 = formService.getFormData("penalty0002");
		 getFormValue(formpenalty0002);
		 boolean validateFlag = this.validateFormData(formpenalty0002);
	}
	
}
