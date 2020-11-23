package app.component.modifyinfo.controller;

import app.component.app.entity.MfBusApply;
import app.component.app.feign.MfBusApplyFeign;
import app.component.appinterface.AppInterfaceFeign;
import app.component.common.EntityUtil;
import app.component.modifyinfo.entity.MfBusModifyFincUseDes;
import app.component.modifyinfo.feign.MfBusModifyFincUseDesFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.util.StringUtil;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
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
 * Title: MfBusModifyFincUseDesController.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Fri Jul 05 16:00:24 CST 2019
 **/
@Controller
@RequestMapping(value = "/mfBusModifyFincUseDes")
public class MfBusModifyFincUseDesController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfBusModifyFincUseDesFeign mfBusModifyFincUseDesFeign;
	@Autowired
	private MfBusApplyFeign mfBusApplyFeign;
	@Autowired
	private AppInterfaceFeign appInterfaceFeign;

	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/modifyinfo/modifyFincUseDes/MfBusModifyFincUseDes_List";
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
		MfBusModifyFincUseDes mfBusModifyFincUseDes = new MfBusModifyFincUseDes();
		try {
			mfBusModifyFincUseDes.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfBusModifyFincUseDes.setCriteriaList(mfBusModifyFincUseDes, ajaxData);//我的筛选
			//mfBusModifyFincUseDes.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfBusModifyFincUseDes,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setParams(this.setIpageParams("mfBusModifyFincUseDes", mfBusModifyFincUseDes));
			//自定义查询Feign方法
			ipage = mfBusModifyFincUseDesFeign.findByPage(ipage);
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
			FormData ModifyFincUseDesDetail = formService.getFormData("ModifyFincUseDesDetail");
			getFormValue(ModifyFincUseDesDetail, getMapByJson(ajaxData));
			if(this.validateFormData(ModifyFincUseDesDetail)){
				MfBusModifyFincUseDes mfBusModifyFincUseDes = new MfBusModifyFincUseDes();
				setObjValue(ModifyFincUseDesDetail, mfBusModifyFincUseDes);
				mfBusModifyFincUseDesFeign.insert(mfBusModifyFincUseDes);
				dataMap.put("flag", "success");
				dataMap.put("msg", "修改成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			dataMap.put("flag", "error");
			dataMap.put("msg", "修改失败");
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
		FormData ModifyFincUseDesDetail = formService.getFormData("fyFincUseDesDetail");
		getFormValue(ModifyFincUseDesDetail, getMapByJson(ajaxData));
		MfBusModifyFincUseDes mfBusModifyFincUseDesJsp = new MfBusModifyFincUseDes();
		setObjValue(ModifyFincUseDesDetail, mfBusModifyFincUseDesJsp);
		MfBusModifyFincUseDes mfBusModifyFincUseDes = mfBusModifyFincUseDesFeign.getById(mfBusModifyFincUseDesJsp);
		if(mfBusModifyFincUseDes!=null){
			try{
				mfBusModifyFincUseDes = (MfBusModifyFincUseDes)EntityUtil.reflectionSetVal(mfBusModifyFincUseDes, mfBusModifyFincUseDesJsp, getMapByJson(ajaxData));
				mfBusModifyFincUseDesFeign.update(mfBusModifyFincUseDes);
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
		MfBusModifyFincUseDes mfBusModifyFincUseDes = new MfBusModifyFincUseDes();
		try{
			FormData ModifyFincUseDesDetail = formService.getFormData("fyFincUseDesDetail");
			getFormValue(ModifyFincUseDesDetail, getMapByJson(ajaxData));
			if(this.validateFormData(ModifyFincUseDesDetail)){
				mfBusModifyFincUseDes = new MfBusModifyFincUseDes();
				setObjValue(ModifyFincUseDesDetail, mfBusModifyFincUseDes);
				mfBusModifyFincUseDesFeign.update(mfBusModifyFincUseDes);
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
	 * AJAX获取查看
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String mfudId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData ModifyFincUseDesDetail = formService.getFormData("fyFincUseDesDetail");
		MfBusModifyFincUseDes mfBusModifyFincUseDes = new MfBusModifyFincUseDes();
		mfBusModifyFincUseDes.setMfudId(mfudId);
		mfBusModifyFincUseDes = mfBusModifyFincUseDesFeign.getById(mfBusModifyFincUseDes);
		getObjValue(ModifyFincUseDesDetail, mfBusModifyFincUseDes,formData);
		if(mfBusModifyFincUseDes!=null){
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
	public Map<String, Object> deleteAjax(String mfudId) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfBusModifyFincUseDes mfBusModifyFincUseDes = new MfBusModifyFincUseDes();
		mfBusModifyFincUseDes.setMfudId(mfudId);
		try {
			mfBusModifyFincUseDesFeign.delete(mfBusModifyFincUseDes);
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
		FormData ModifyFincUseDesBase = formService.getFormData("ModifyFincUseDesBase");
		model.addAttribute("ModifyFincUseDesBase", ModifyFincUseDesBase);
		model.addAttribute("query", "");
		return "/component/modifyinfo/modifyFincUseDes/MfBusModifyFincUseDes_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model,String mfudId) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData ModifyFincUseDesDetail = formService.getFormData("ModifyFincUseDesDetail");
		getFormValue(ModifyFincUseDesDetail);
		MfBusModifyFincUseDes mfBusModifyFincUseDes = new MfBusModifyFincUseDes();
		mfBusModifyFincUseDes.setMfudId(mfudId);
		mfBusModifyFincUseDes = mfBusModifyFincUseDesFeign.getById(mfBusModifyFincUseDes);
		getObjValue(ModifyFincUseDesDetail, mfBusModifyFincUseDes);
		model.addAttribute("ModifyFincUseDesDetail", ModifyFincUseDesDetail);
		model.addAttribute("query", "");
		return "/component/modifyinfo/modifyFincUseDes/MfBusModifyFincUseDes_Detail";
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
		FormData ModifyFincUseDesDetail = formService.getFormData("fyFincUseDesDetail");
		getFormValue(ModifyFincUseDesDetail);
		boolean validateFlag = this.validateFormData(ModifyFincUseDesDetail);
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
		FormData ModifyFincUseDesDetail = formService.getFormData("fyFincUseDesDetail");
		getFormValue(ModifyFincUseDesDetail);
		boolean validateFlag = this.validateFormData(ModifyFincUseDesDetail);
	}
	
	/**
	*@Description: 获取所有业务信息
	*@Author: lyb
	*@date: 2019/7/12
	*/
	@ResponseBody
	@RequestMapping("/getApplyListAjax")
	public Map<String, Object> getApplyListAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusApply mfBusApply = new MfBusApply();
		try {
			mfBusApply.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfBusApply.setCustomSorts(ajaxData);//自定义排序参数赋值
			mfBusApply.setCriteriaList(mfBusApply, ajaxData);//我的筛选
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			dataMap.put("mfBusApply",mfBusApply);
			ipage.setParams(dataMap);
			ipage = appInterfaceFeign.getApplyListAjax(ipage);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

}
