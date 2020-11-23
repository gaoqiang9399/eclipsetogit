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

import app.component.collateral.movable.entity.MfMoveablePatrolInvenApproHis;
import app.component.collateral.movable.feign.MfMoveablePatrolInvenApproHisFeign;
import app.component.common.EntityUtil;
import app.util.toolkit.Ipage;

/**
 * Title: MfMoveablePatrolInvenApproHisAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Tue Jun 13 20:29:52 CST 2017
 **/
@Controller
@RequestMapping("/mfMoveablePatrolInvenApproHis")
public class MfMoveablePatrolInvenApproHisController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfMoveablePatrolInvenApproHisBo
	@Autowired
	private MfMoveablePatrolInvenApproHisFeign mfMoveablePatrolInvenApproHisFeign;
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
		 return "/component/collateral/movable/MfMoveablePatrolInvenApproHis_List";
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
		MfMoveablePatrolInvenApproHis mfMoveablePatrolInvenApproHis = new MfMoveablePatrolInvenApproHis();
		try {
			mfMoveablePatrolInvenApproHis.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfMoveablePatrolInvenApproHis.setCriteriaList(mfMoveablePatrolInvenApproHis, ajaxData);//我的筛选
			//mfMoveablePatrolInvenApproHis.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfMoveablePatrolInvenApproHis,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = mfMoveablePatrolInvenApproHisFeign.findByPage(ipage, mfMoveablePatrolInvenApproHis);
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
		FormData 	formmovablepatrolppro0002 = formService.getFormData("movablepatrolppro0002");
			getFormValue(formmovablepatrolppro0002, getMapByJson(ajaxData));
			if(this.validateFormData(formmovablepatrolppro0002)){
		MfMoveablePatrolInvenApproHis mfMoveablePatrolInvenApproHis = new MfMoveablePatrolInvenApproHis();
				setObjValue(formmovablepatrolppro0002, mfMoveablePatrolInvenApproHis);
				mfMoveablePatrolInvenApproHisFeign.insert(mfMoveablePatrolInvenApproHis);
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
		FormData formmovablepatrolppro0002 = formService.getFormData("movablepatrolppro0002");
		getFormValue(formmovablepatrolppro0002, getMapByJson(ajaxData));
		MfMoveablePatrolInvenApproHis mfMoveablePatrolInvenApproHisJsp = new MfMoveablePatrolInvenApproHis();
		setObjValue(formmovablepatrolppro0002, mfMoveablePatrolInvenApproHisJsp);
		MfMoveablePatrolInvenApproHis mfMoveablePatrolInvenApproHis = mfMoveablePatrolInvenApproHisFeign.getById(mfMoveablePatrolInvenApproHisJsp);
		if(mfMoveablePatrolInvenApproHis!=null){
			try{
				mfMoveablePatrolInvenApproHis = (MfMoveablePatrolInvenApproHis)EntityUtil.reflectionSetVal(mfMoveablePatrolInvenApproHis, mfMoveablePatrolInvenApproHisJsp, getMapByJson(ajaxData));
				mfMoveablePatrolInvenApproHisFeign.update(mfMoveablePatrolInvenApproHis);
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
		FormData 	formmovablepatrolppro0002 = formService.getFormData("movablepatrolppro0002");
			getFormValue(formmovablepatrolppro0002, getMapByJson(ajaxData));
			if(this.validateFormData(formmovablepatrolppro0002)){
		MfMoveablePatrolInvenApproHis mfMoveablePatrolInvenApproHis = new MfMoveablePatrolInvenApproHis();
				setObjValue(formmovablepatrolppro0002, mfMoveablePatrolInvenApproHis);
				mfMoveablePatrolInvenApproHisFeign.update(mfMoveablePatrolInvenApproHis);
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
	 * @param patrolApproHis 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData, String patrolApproHis) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formmovablepatrolppro0002 = formService.getFormData("movablepatrolppro0002");
		MfMoveablePatrolInvenApproHis mfMoveablePatrolInvenApproHis = new MfMoveablePatrolInvenApproHis();
		mfMoveablePatrolInvenApproHis.setPatrolApproHis(patrolApproHis);
		mfMoveablePatrolInvenApproHis = mfMoveablePatrolInvenApproHisFeign.getById(mfMoveablePatrolInvenApproHis);
		getObjValue(formmovablepatrolppro0002, mfMoveablePatrolInvenApproHis,formData);
		if(mfMoveablePatrolInvenApproHis!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @param patrolApproHis 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String patrolApproHis) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfMoveablePatrolInvenApproHis mfMoveablePatrolInvenApproHis = new MfMoveablePatrolInvenApproHis();
		mfMoveablePatrolInvenApproHis.setPatrolApproHis(patrolApproHis);
		try {
			mfMoveablePatrolInvenApproHisFeign.delete(mfMoveablePatrolInvenApproHis);
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
		FormData formmovablepatrolppro0002 = formService.getFormData("movablepatrolppro0002");
		model.addAttribute("formmovablepatrolppro0002", formmovablepatrolppro0002);
		model.addAttribute("query", "");
		 return "/component/collateral/movable/MfMoveablePatrolInvenApproHis_Insert";
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
		FormData  formmovablepatrolppro0002 = formService.getFormData("movablepatrolppro0002");
		 getFormValue(formmovablepatrolppro0002);
		MfMoveablePatrolInvenApproHis  mfMoveablePatrolInvenApproHis = new MfMoveablePatrolInvenApproHis();
		 setObjValue(formmovablepatrolppro0002, mfMoveablePatrolInvenApproHis);
		 mfMoveablePatrolInvenApproHisFeign.insert(mfMoveablePatrolInvenApproHis);
		 getObjValue(formmovablepatrolppro0002, mfMoveablePatrolInvenApproHis);
		 this.addActionMessage(model, "保存成功");
		 List<MfMoveablePatrolInvenApproHis> mfMoveablePatrolInvenApproHisList = (List<MfMoveablePatrolInvenApproHis>)mfMoveablePatrolInvenApproHisFeign.findByPage(this.getIpage(), mfMoveablePatrolInvenApproHis).getResult();
		model.addAttribute("mfMoveablePatrolInvenApproHisList", mfMoveablePatrolInvenApproHisList);
		model.addAttribute("formmovablepatrolppro0002", formmovablepatrolppro0002);
		model.addAttribute("query", "");
		 return "/component/collateral/movable/MfMoveablePatrolInvenApproHis_Insert";
	}
	/**
	 * 查询
	 * @param patrolApproHis 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData, String patrolApproHis) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formmovableapatrolppro0001 = formService.getFormData("movableapatrolppro0001");
		 getFormValue(formmovableapatrolppro0001);
		MfMoveablePatrolInvenApproHis  mfMoveablePatrolInvenApproHis = new MfMoveablePatrolInvenApproHis();
		mfMoveablePatrolInvenApproHis.setPatrolApproHis(patrolApproHis);
		 mfMoveablePatrolInvenApproHis = mfMoveablePatrolInvenApproHisFeign.getById(mfMoveablePatrolInvenApproHis);
		 getObjValue(formmovableapatrolppro0001, mfMoveablePatrolInvenApproHis);
		model.addAttribute("formmovableapatrolppro0001", formmovableapatrolppro0001);
		model.addAttribute("query", "");
		 return "/component/collateral/movable/MfMoveablePatrolInvenApproHis_Detail";
	}
	/**
	 * 删除
	 * @param patrolApproHis 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String ajaxData, String patrolApproHis) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		MfMoveablePatrolInvenApproHis mfMoveablePatrolInvenApproHis = new MfMoveablePatrolInvenApproHis();
		mfMoveablePatrolInvenApproHis.setPatrolApproHis(patrolApproHis);
		mfMoveablePatrolInvenApproHisFeign.delete(mfMoveablePatrolInvenApproHis);
		return getListPage(model);
	}
}
