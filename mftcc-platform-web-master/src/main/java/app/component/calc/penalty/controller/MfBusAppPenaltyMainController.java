package  app.component.calc.penalty.controller;
import java.util.HashMap;
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

import app.component.calc.penalty.entity.MfBusAppPenaltyMain;
import app.component.calc.penalty.feign.MfBusAppPenaltyMainFeign;
import app.component.common.EntityUtil;

/**
 * Title: MfBusAppPenaltyMainAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Sat Jul 01 17:31:56 CST 2017
 **/
@Controller
@RequestMapping("/mfBusAppPenaltyMain")
public class MfBusAppPenaltyMainController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfBusAppPenaltyMainBo
	@Autowired
	private MfBusAppPenaltyMainFeign mfBusAppPenaltyMainFeign;
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
		return "/component/calc/penalty/MfBusAppPenaltyMain_List";
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
		FormData 	formbuspenalty0002 = formService.getFormData("buspenalty0002");
			getFormValue(formbuspenalty0002, getMapByJson(ajaxData));
			if(this.validateFormData(formbuspenalty0002)){
		MfBusAppPenaltyMain mfBusAppPenaltyMain = new MfBusAppPenaltyMain();
				setObjValue(formbuspenalty0002, mfBusAppPenaltyMain);
				mfBusAppPenaltyMainFeign.insert(mfBusAppPenaltyMain);
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
		FormData formbuspenalty0002 = formService.getFormData("buspenalty0002");
		getFormValue(formbuspenalty0002, getMapByJson(ajaxData));
		MfBusAppPenaltyMain mfBusAppPenaltyMainJsp = new MfBusAppPenaltyMain();
		setObjValue(formbuspenalty0002, mfBusAppPenaltyMainJsp);
		MfBusAppPenaltyMain mfBusAppPenaltyMain = mfBusAppPenaltyMainFeign.getById(mfBusAppPenaltyMainJsp);
		if(mfBusAppPenaltyMain!=null){
			try{
				mfBusAppPenaltyMain = (MfBusAppPenaltyMain)EntityUtil.reflectionSetVal(mfBusAppPenaltyMain, mfBusAppPenaltyMainJsp, getMapByJson(ajaxData));
				mfBusAppPenaltyMainFeign.update(mfBusAppPenaltyMain);
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
		MfBusAppPenaltyMain mfBusAppPenaltyMain = new MfBusAppPenaltyMain();
		try{
		FormData 	formbuspenalty0002 = formService.getFormData("buspenalty0002");
			getFormValue(formbuspenalty0002, getMapByJson(ajaxData));
			if(this.validateFormData(formbuspenalty0002)){
		MfBusAppPenaltyMain mfBusAppPenaltyMain1 = new MfBusAppPenaltyMain();
				setObjValue(formbuspenalty0002, mfBusAppPenaltyMain1);
				mfBusAppPenaltyMainFeign.update(mfBusAppPenaltyMain1);
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
		FormData formbuspenalty0002 = formService.getFormData("buspenalty0002");
		MfBusAppPenaltyMain mfBusAppPenaltyMain = new MfBusAppPenaltyMain();
		mfBusAppPenaltyMain.setId(id);
		mfBusAppPenaltyMain = mfBusAppPenaltyMainFeign.getById(mfBusAppPenaltyMain);
		getObjValue(formbuspenalty0002, mfBusAppPenaltyMain,formData);
		if(mfBusAppPenaltyMain!=null){
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
		MfBusAppPenaltyMain mfBusAppPenaltyMain = new MfBusAppPenaltyMain();
		mfBusAppPenaltyMain.setId(id);
		try {
			mfBusAppPenaltyMainFeign.delete(mfBusAppPenaltyMain);
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
		FormData formbuspenalty0002 = formService.getFormData("buspenalty0002");
		model.addAttribute("formbuspenalty0002", formbuspenalty0002);
		model.addAttribute("query", "");
		return "/component/calc/penalty/MfBusAppPenaltyMain_Insert";
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
		FormData  formbuspenalty0001 = formService.getFormData("buspenalty0001");
		 getFormValue(formbuspenalty0001);
		MfBusAppPenaltyMain  mfBusAppPenaltyMain = new MfBusAppPenaltyMain();
		mfBusAppPenaltyMain.setId(id);
		 mfBusAppPenaltyMain = mfBusAppPenaltyMainFeign.getById(mfBusAppPenaltyMain);
		 getObjValue(formbuspenalty0001, mfBusAppPenaltyMain);
		model.addAttribute("formbuspenalty0001", formbuspenalty0001);
		model.addAttribute("query", "");
		return "/component/calc/penalty/MfBusAppPenaltyMain_Detail";
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
		FormData  formbuspenalty0002 = formService.getFormData("buspenalty0002");
		 getFormValue(formbuspenalty0002);
		 boolean validateFlag = this.validateFormData(formbuspenalty0002);
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
		FormData  formbuspenalty0002 = formService.getFormData("buspenalty0002");
		 getFormValue(formbuspenalty0002);
		 boolean validateFlag = this.validateFormData(formbuspenalty0002);
	}

}
