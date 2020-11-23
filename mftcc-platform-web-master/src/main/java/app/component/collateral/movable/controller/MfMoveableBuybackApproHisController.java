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

import app.component.collateral.movable.entity.MfMoveableBuybackApproHis;
import app.component.collateral.movable.feign.MfMoveableBuybackApproHisFeign;
import app.component.common.EntityUtil;
import app.util.toolkit.Ipage;

/**
 * Title: MfMoveableBuybackApproHisAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Tue Jun 20 09:01:37 CST 2017
 **/
@Controller
@RequestMapping("/mfMoveableBuybackApproHis")
public class MfMoveableBuybackApproHisController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfMoveableBuybackApproHisBo
	@Autowired
	private MfMoveableBuybackApproHisFeign mfMoveableBuybackApproHisFeign;
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
		 return "/component/collateral/movable/MfMoveableBuybackApproHis_List";
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
		MfMoveableBuybackApproHis mfMoveableBuybackApproHis = new MfMoveableBuybackApproHis();
		try {
			mfMoveableBuybackApproHis.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfMoveableBuybackApproHis.setCriteriaList(mfMoveableBuybackApproHis, ajaxData);//我的筛选
			//mfMoveableBuybackApproHis.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfMoveableBuybackApproHis,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = mfMoveableBuybackApproHisFeign.findByPage(ipage, mfMoveableBuybackApproHis);
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
		FormData 	formmovablebuybackhis0002 = formService.getFormData("movablebuybackhis0002");
			getFormValue(formmovablebuybackhis0002, getMapByJson(ajaxData));
			if(this.validateFormData(formmovablebuybackhis0002)){
		MfMoveableBuybackApproHis mfMoveableBuybackApproHis = new MfMoveableBuybackApproHis();
				setObjValue(formmovablebuybackhis0002, mfMoveableBuybackApproHis);
				mfMoveableBuybackApproHisFeign.insert(mfMoveableBuybackApproHis);
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
		FormData formmovablebuybackhis0002 = formService.getFormData("movablebuybackhis0002");
		getFormValue(formmovablebuybackhis0002, getMapByJson(ajaxData));
		MfMoveableBuybackApproHis mfMoveableBuybackApproHisJsp = new MfMoveableBuybackApproHis();
		setObjValue(formmovablebuybackhis0002, mfMoveableBuybackApproHisJsp);
		MfMoveableBuybackApproHis mfMoveableBuybackApproHis = mfMoveableBuybackApproHisFeign.getById(mfMoveableBuybackApproHisJsp);
		if(mfMoveableBuybackApproHis!=null){
			try{
				mfMoveableBuybackApproHis = (MfMoveableBuybackApproHis)EntityUtil.reflectionSetVal(mfMoveableBuybackApproHis, mfMoveableBuybackApproHisJsp, getMapByJson(ajaxData));
				mfMoveableBuybackApproHisFeign.update(mfMoveableBuybackApproHis);
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
		FormData 	formmovablebuybackhis0002 = formService.getFormData("movablebuybackhis0002");
			getFormValue(formmovablebuybackhis0002, getMapByJson(ajaxData));
			if(this.validateFormData(formmovablebuybackhis0002)){
		MfMoveableBuybackApproHis mfMoveableBuybackApproHis = new MfMoveableBuybackApproHis();
				setObjValue(formmovablebuybackhis0002, mfMoveableBuybackApproHis);
				mfMoveableBuybackApproHisFeign.update(mfMoveableBuybackApproHis);
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
	 * @param buybackApproHisId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData, String buybackApproHisId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formmovablebuybackhis0002 = formService.getFormData("movablebuybackhis0002");
		MfMoveableBuybackApproHis mfMoveableBuybackApproHis = new MfMoveableBuybackApproHis();
		mfMoveableBuybackApproHis.setBuybackApproHisId(buybackApproHisId);
		mfMoveableBuybackApproHis = mfMoveableBuybackApproHisFeign.getById(mfMoveableBuybackApproHis);
		getObjValue(formmovablebuybackhis0002, mfMoveableBuybackApproHis,formData);
		if(mfMoveableBuybackApproHis!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @param buybackApproHisId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String buybackApproHisId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfMoveableBuybackApproHis mfMoveableBuybackApproHis = new MfMoveableBuybackApproHis();
		mfMoveableBuybackApproHis.setBuybackApproHisId(buybackApproHisId);
		try {
			mfMoveableBuybackApproHisFeign.delete(mfMoveableBuybackApproHis);
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
		FormData formmovablebuybackhis0002 = formService.getFormData("movablebuybackhis0002");
		model.addAttribute("formmovablebuybackhis0002", formmovablebuybackhis0002);
		model.addAttribute("query", "");
		 return "/component/collateral/movable/MfMoveableBuybackApproHis_Insert";
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
		FormData  formmovablebuybackhis0002 = formService.getFormData("movablebuybackhis0002");
		 getFormValue(formmovablebuybackhis0002);
		MfMoveableBuybackApproHis  mfMoveableBuybackApproHis = new MfMoveableBuybackApproHis();
		 setObjValue(formmovablebuybackhis0002, mfMoveableBuybackApproHis);
		 mfMoveableBuybackApproHisFeign.insert(mfMoveableBuybackApproHis);
		 getObjValue(formmovablebuybackhis0002, mfMoveableBuybackApproHis);
		 this.addActionMessage(model, "保存成功");
		 List<MfMoveableBuybackApproHis> mfMoveableBuybackApproHisList = (List<MfMoveableBuybackApproHis>)mfMoveableBuybackApproHisFeign.findByPage(this.getIpage(), mfMoveableBuybackApproHis).getResult();
		model.addAttribute("mfMoveableBuybackApproHisList", mfMoveableBuybackApproHisList);
		model.addAttribute("formmovablebuybackhis0002", formmovablebuybackhis0002);
		model.addAttribute("query", "");
		 return "/component/collateral/movable/MfMoveableBuybackApproHis_Insert";
	}
	/**
	 * 查询
	 * @param buybackApproHisId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData, String buybackApproHisId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formmovablebuybackhis0001 = formService.getFormData("movablebuybackhis0001");
		 getFormValue(formmovablebuybackhis0001);
		MfMoveableBuybackApproHis  mfMoveableBuybackApproHis = new MfMoveableBuybackApproHis();
		mfMoveableBuybackApproHis.setBuybackApproHisId(buybackApproHisId);
		 mfMoveableBuybackApproHis = mfMoveableBuybackApproHisFeign.getById(mfMoveableBuybackApproHis);
		 getObjValue(formmovablebuybackhis0001, mfMoveableBuybackApproHis);
		model.addAttribute("formmovablebuybackhis0001", formmovablebuybackhis0001);
		model.addAttribute("query", "");
		 return "/component/collateral/movable/MfMoveableBuybackApproHis_Detail";
	}
	/**
	 * 删除
	 * @param buybackApproHisId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String ajaxData, String buybackApproHisId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		MfMoveableBuybackApproHis mfMoveableBuybackApproHis = new MfMoveableBuybackApproHis();
		mfMoveableBuybackApproHis.setBuybackApproHisId(buybackApproHisId);
		mfMoveableBuybackApproHisFeign.delete(mfMoveableBuybackApproHis);
		return getListPage(model);
	}
}
