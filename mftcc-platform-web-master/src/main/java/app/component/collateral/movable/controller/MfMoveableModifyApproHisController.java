package  app.component.collateral.movable.controller;
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

import app.component.collateral.movable.entity.MfMoveableModifyApproHis;
import app.component.collateral.movable.feign.MfMoveableModifyApproHisFeign;
import app.component.common.EntityUtil;
import app.util.toolkit.Ipage;

/**
 * Title: MfMoveableModifyApproHisAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Mon Jun 12 20:27:07 CST 2017
 **/
@Controller
@RequestMapping("/mfMoveableModifyApproHis")
public class MfMoveableModifyApproHisController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfMoveableModifyApproHisBo
	@Autowired
	private MfMoveableModifyApproHisFeign mfMoveableModifyApproHisFeign;
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
		 return "/component/collateral/movable/MfMoveableModifyApproHis_List";
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
		MfMoveableModifyApproHis mfMoveableModifyApproHis = new MfMoveableModifyApproHis();
		try {
			mfMoveableModifyApproHis.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfMoveableModifyApproHis.setCriteriaList(mfMoveableModifyApproHis, ajaxData);//我的筛选
			//mfMoveableModifyApproHis.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfMoveableModifyApproHis,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = mfMoveableModifyApproHisFeign.findByPage(ipage, mfMoveableModifyApproHis);
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
		FormData 	formmovablemodifyhis0002 = formService.getFormData("movablemodifyhis0002");
			getFormValue(formmovablemodifyhis0002, getMapByJson(ajaxData));
			if(this.validateFormData(formmovablemodifyhis0002)){
		MfMoveableModifyApproHis mfMoveableModifyApproHis = new MfMoveableModifyApproHis();
				setObjValue(formmovablemodifyhis0002, mfMoveableModifyApproHis);
				mfMoveableModifyApproHisFeign.insert(mfMoveableModifyApproHis);
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
		FormData formmovablemodifyhis0002 = formService.getFormData("movablemodifyhis0002");
		getFormValue(formmovablemodifyhis0002, getMapByJson(ajaxData));
		MfMoveableModifyApproHis mfMoveableModifyApproHisJsp = new MfMoveableModifyApproHis();
		setObjValue(formmovablemodifyhis0002, mfMoveableModifyApproHisJsp);
		MfMoveableModifyApproHis mfMoveableModifyApproHis = mfMoveableModifyApproHisFeign.getById(mfMoveableModifyApproHisJsp);
		if(mfMoveableModifyApproHis!=null){
			try{
				mfMoveableModifyApproHis = (MfMoveableModifyApproHis)EntityUtil.reflectionSetVal(mfMoveableModifyApproHis, mfMoveableModifyApproHisJsp, getMapByJson(ajaxData));
				mfMoveableModifyApproHisFeign.update(mfMoveableModifyApproHis);
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
		try{
		FormData 	formmovablemodifyhis0002 = formService.getFormData("movablemodifyhis0002");
			getFormValue(formmovablemodifyhis0002, getMapByJson(ajaxData));
			if(this.validateFormData(formmovablemodifyhis0002)){
		MfMoveableModifyApproHis mfMoveableModifyApproHis = new MfMoveableModifyApproHis();
				setObjValue(formmovablemodifyhis0002, mfMoveableModifyApproHis);
				mfMoveableModifyApproHisFeign.update(mfMoveableModifyApproHis);
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
	 * @param modifyApproHisId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData, String modifyApproHisId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formmovablemodifyhis0002 = formService.getFormData("movablemodifyhis0002");
		MfMoveableModifyApproHis mfMoveableModifyApproHis = new MfMoveableModifyApproHis();
		mfMoveableModifyApproHis.setModifyApproHisId(modifyApproHisId);
		mfMoveableModifyApproHis = mfMoveableModifyApproHisFeign.getById(mfMoveableModifyApproHis);
		getObjValue(formmovablemodifyhis0002, mfMoveableModifyApproHis,formData);
		if(mfMoveableModifyApproHis!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @param modifyApproHisId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String modifyApproHisId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfMoveableModifyApproHis mfMoveableModifyApproHis = new MfMoveableModifyApproHis();
		mfMoveableModifyApproHis.setModifyApproHisId(modifyApproHisId);
		try {
			mfMoveableModifyApproHisFeign.delete(mfMoveableModifyApproHis);
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
		FormData formmovablemodifyhis0002 = formService.getFormData("movablemodifyhis0002");
		model.addAttribute("formmovablemodifyhis0002", formmovablemodifyhis0002);
		model.addAttribute("query", "");
		 return "/component/collateral/movable/MfMoveableModifyApproHis_Insert";
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
		FormData  formmovablemodifyhis0002 = formService.getFormData("movablemodifyhis0002");
		 getFormValue(formmovablemodifyhis0002);
		MfMoveableModifyApproHis  mfMoveableModifyApproHis = new MfMoveableModifyApproHis();
		 setObjValue(formmovablemodifyhis0002, mfMoveableModifyApproHis);
		 mfMoveableModifyApproHisFeign.insert(mfMoveableModifyApproHis);
		 getObjValue(formmovablemodifyhis0002, mfMoveableModifyApproHis);
		 this.addActionMessage(model, "保存成功");
		 List<MfMoveableModifyApproHis> mfMoveableModifyApproHisList = (List<MfMoveableModifyApproHis>)mfMoveableModifyApproHisFeign.findByPage(this.getIpage(), mfMoveableModifyApproHis).getResult();
		model.addAttribute("mfMoveableModifyApproHisList", mfMoveableModifyApproHisList);
		model.addAttribute("formmovablemodifyhis0002", formmovablemodifyhis0002);
		model.addAttribute("query", "");
		 return "/component/collateral/movable/MfMoveableModifyApproHis_Insert";
	}
	/**
	 * 查询
	 * @param modifyApproHisId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData, String modifyApproHisId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formmovablemodifyhis0001 = formService.getFormData("movablemodifyhis0001");
		 getFormValue(formmovablemodifyhis0001);
		MfMoveableModifyApproHis  mfMoveableModifyApproHis = new MfMoveableModifyApproHis();
		mfMoveableModifyApproHis.setModifyApproHisId(modifyApproHisId);
		 mfMoveableModifyApproHis = mfMoveableModifyApproHisFeign.getById(mfMoveableModifyApproHis);
		 getObjValue(formmovablemodifyhis0001, mfMoveableModifyApproHis);
		model.addAttribute("formmovablemodifyhis0001", formmovablemodifyhis0001);
		model.addAttribute("query", "");
		 return "/component/collateral/movable/MfMoveableModifyApproHis_Detail";
	}
	/**
	 * 删除
	 * @param modifyApproHisId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String ajaxData, String modifyApproHisId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		MfMoveableModifyApproHis mfMoveableModifyApproHis = new MfMoveableModifyApproHis();
		mfMoveableModifyApproHis.setModifyApproHisId(modifyApproHisId);
		mfMoveableModifyApproHisFeign.delete(mfMoveableModifyApproHis);
		return getListPage(model);
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
		FormData  formmovablemodifyhis0002 = formService.getFormData("movablemodifyhis0002");
		 getFormValue(formmovablemodifyhis0002);
		 boolean validateFlag = this.validateFormData(formmovablemodifyhis0002);
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
		FormData  formmovablemodifyhis0002 = formService.getFormData("movablemodifyhis0002");
		 getFormValue(formmovablemodifyhis0002);
		 boolean validateFlag = this.validateFormData(formmovablemodifyhis0002);
	}
	
	
}
