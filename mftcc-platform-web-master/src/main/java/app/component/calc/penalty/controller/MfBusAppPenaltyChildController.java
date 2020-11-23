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

import app.component.calc.penalty.entity.MfBusAppPenaltyChild;
import app.component.calc.penalty.feign.MfBusAppPenaltyChildFeign;
import app.component.common.EntityUtil;
import app.util.toolkit.Ipage;

/**
 * Title: MfBusAppPenaltyChildAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Sat Jul 01 17:33:08 CST 2017
 **/
@Controller
@RequestMapping("/mfBusAppPenaltyChild")
public class MfBusAppPenaltyChildController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfBusAppPenaltyChildBo
	@Autowired
	private MfBusAppPenaltyChildFeign mfBusAppPenaltyChildFeign;
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
		return "/component/calc/penalty/MfBusAppPenaltyChild_List";
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
		MfBusAppPenaltyChild mfBusAppPenaltyChild = new MfBusAppPenaltyChild();
		try {
			mfBusAppPenaltyChild.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfBusAppPenaltyChild.setCriteriaList(mfBusAppPenaltyChild, ajaxData);//我的筛选
			//mfBusAppPenaltyChild.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfBusAppPenaltyChild,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = mfBusAppPenaltyChildFeign.findByPage(ipage, mfBusAppPenaltyChild);
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
		FormData 	formbuschildpenalty0002 = formService.getFormData("buschildpenalty0002");
			getFormValue(formbuschildpenalty0002, getMapByJson(ajaxData));
			if(this.validateFormData(formbuschildpenalty0002)){
		MfBusAppPenaltyChild mfBusAppPenaltyChild = new MfBusAppPenaltyChild();
				setObjValue(formbuschildpenalty0002, mfBusAppPenaltyChild);
				mfBusAppPenaltyChildFeign.insert(mfBusAppPenaltyChild);
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
		FormData formbuschildpenalty0002 = formService.getFormData("buschildpenalty0002");
		getFormValue(formbuschildpenalty0002, getMapByJson(ajaxData));
		MfBusAppPenaltyChild mfBusAppPenaltyChildJsp = new MfBusAppPenaltyChild();
		setObjValue(formbuschildpenalty0002, mfBusAppPenaltyChildJsp);
		MfBusAppPenaltyChild mfBusAppPenaltyChild = mfBusAppPenaltyChildFeign.getById(mfBusAppPenaltyChildJsp);
		if(mfBusAppPenaltyChild!=null){
			try{
				mfBusAppPenaltyChild = (MfBusAppPenaltyChild)EntityUtil.reflectionSetVal(mfBusAppPenaltyChild, mfBusAppPenaltyChildJsp, getMapByJson(ajaxData));
				mfBusAppPenaltyChildFeign.update(mfBusAppPenaltyChild);
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
		MfBusAppPenaltyChild mfBusAppPenaltyChild = new MfBusAppPenaltyChild();
		try{
		FormData 	formbuschildpenalty0002 = formService.getFormData("buschildpenalty0002");
			getFormValue(formbuschildpenalty0002, getMapByJson(ajaxData));
			if(this.validateFormData(formbuschildpenalty0002)){
		MfBusAppPenaltyChild mfBusAppPenaltyChild1 = new MfBusAppPenaltyChild();
				setObjValue(formbuschildpenalty0002, mfBusAppPenaltyChild1);
				mfBusAppPenaltyChildFeign.update(mfBusAppPenaltyChild1);
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
		FormData formbuschildpenalty0002 = formService.getFormData("buschildpenalty0002");
		MfBusAppPenaltyChild mfBusAppPenaltyChild = new MfBusAppPenaltyChild();
		mfBusAppPenaltyChild.setId(id);
		mfBusAppPenaltyChild = mfBusAppPenaltyChildFeign.getById(mfBusAppPenaltyChild);
		getObjValue(formbuschildpenalty0002, mfBusAppPenaltyChild,formData);
		if(mfBusAppPenaltyChild!=null){
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
		MfBusAppPenaltyChild mfBusAppPenaltyChild = new MfBusAppPenaltyChild();
		mfBusAppPenaltyChild.setId(id);
		try {
			mfBusAppPenaltyChildFeign.delete(mfBusAppPenaltyChild);
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
		FormData formbuschildpenalty0002 = formService.getFormData("buschildpenalty0002");
		model.addAttribute("formbuschildpenalty0002", formbuschildpenalty0002);
		model.addAttribute("query", "");
		return "/component/calc/penalty/MfBusAppPenaltyChild_Insert";
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
		FormData  formbuschildpenalty0001 = formService.getFormData("buschildpenalty0001");
		 getFormValue(formbuschildpenalty0001);
		MfBusAppPenaltyChild  mfBusAppPenaltyChild = new MfBusAppPenaltyChild();
		mfBusAppPenaltyChild.setId(id);
		 mfBusAppPenaltyChild = mfBusAppPenaltyChildFeign.getById(mfBusAppPenaltyChild);
		 getObjValue(formbuschildpenalty0001, mfBusAppPenaltyChild);
		model.addAttribute("formbuschildpenalty0001", formbuschildpenalty0001);
		model.addAttribute("query", "");
		return "/component/calc/penalty/MfBusAppPenaltyChild_Detail";
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
		FormData  formbuschildpenalty0002 = formService.getFormData("buschildpenalty0002");
		 getFormValue(formbuschildpenalty0002);
		 boolean validateFlag = this.validateFormData(formbuschildpenalty0002);
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
		FormData  formbuschildpenalty0002 = formService.getFormData("buschildpenalty0002");
		 getFormValue(formbuschildpenalty0002);
		 boolean validateFlag = this.validateFormData(formbuschildpenalty0002);
	}
	
}