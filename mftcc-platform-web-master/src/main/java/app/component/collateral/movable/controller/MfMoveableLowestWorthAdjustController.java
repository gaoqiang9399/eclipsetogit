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

import app.component.collateral.movable.entity.MfMoveableLowestWorthAdjust;
import app.component.collateral.movable.feign.MfMoveableLowestWorthAdjustFeign;
import app.component.common.EntityUtil;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: MfMoveableLowestWorthAdjustAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Fri Jun 16 16:03:57 CST 2017
 **/
@Controller
@RequestMapping("/mfMoveableLowestWorthAdjust")
public class MfMoveableLowestWorthAdjustController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfMoveableLowestWorthAdjustBo
	@Autowired
	private MfMoveableLowestWorthAdjustFeign mfMoveableLowestWorthAdjustFeign;
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
		return "/component/collateral/movable/MfMoveableLowestWorthAdjust_List";
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
		MfMoveableLowestWorthAdjust mfMoveableLowestWorthAdjust = new MfMoveableLowestWorthAdjust();
		try {
			mfMoveableLowestWorthAdjust.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfMoveableLowestWorthAdjust.setCriteriaList(mfMoveableLowestWorthAdjust, ajaxData);//我的筛选
			//mfMoveableLowestWorthAdjust.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfMoveableLowestWorthAdjust,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = mfMoveableLowestWorthAdjustFeign.findByPage(ipage, mfMoveableLowestWorthAdjust);
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
		FormData 	formworthadjust0001 = formService.getFormData("worthadjust0001");
			getFormValue(formworthadjust0001, getMapByJson(ajaxData));
			if(this.validateFormData(formworthadjust0001)){
		MfMoveableLowestWorthAdjust mfMoveableLowestWorthAdjust = new MfMoveableLowestWorthAdjust();
				setObjValue(formworthadjust0001, mfMoveableLowestWorthAdjust);
				mfMoveableLowestWorthAdjustFeign.insert(mfMoveableLowestWorthAdjust);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE_CONTENT.getMessage("最低监管价值调整"));
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
		FormData formworthadjust0002 = formService.getFormData("worthadjust0002");
		getFormValue(formworthadjust0002, getMapByJson(ajaxData));
		MfMoveableLowestWorthAdjust mfMoveableLowestWorthAdjustJsp = new MfMoveableLowestWorthAdjust();
		setObjValue(formworthadjust0002, mfMoveableLowestWorthAdjustJsp);
		MfMoveableLowestWorthAdjust mfMoveableLowestWorthAdjust = mfMoveableLowestWorthAdjustFeign.getById(mfMoveableLowestWorthAdjustJsp);
		if(mfMoveableLowestWorthAdjust!=null){
			try{
				mfMoveableLowestWorthAdjust = (MfMoveableLowestWorthAdjust)EntityUtil.reflectionSetVal(mfMoveableLowestWorthAdjust, mfMoveableLowestWorthAdjustJsp, getMapByJson(ajaxData));
				mfMoveableLowestWorthAdjustFeign.update(mfMoveableLowestWorthAdjust);
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
		FormData 	formworthadjust0002 = formService.getFormData("worthadjust0002");
			getFormValue(formworthadjust0002, getMapByJson(ajaxData));
			if(this.validateFormData(formworthadjust0002)){
		MfMoveableLowestWorthAdjust mfMoveableLowestWorthAdjust = new MfMoveableLowestWorthAdjust();
				setObjValue(formworthadjust0002, mfMoveableLowestWorthAdjust);
				mfMoveableLowestWorthAdjustFeign.update(mfMoveableLowestWorthAdjust);
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
	 * @param worthAdjustId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData, String worthAdjustId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formworthadjust0002 = formService.getFormData("worthadjust0002");
		MfMoveableLowestWorthAdjust mfMoveableLowestWorthAdjust = new MfMoveableLowestWorthAdjust();
		mfMoveableLowestWorthAdjust.setWorthAdjustId(worthAdjustId);
		mfMoveableLowestWorthAdjust = mfMoveableLowestWorthAdjustFeign.getById(mfMoveableLowestWorthAdjust);
		getObjValue(formworthadjust0002, mfMoveableLowestWorthAdjust,formData);
		if(mfMoveableLowestWorthAdjust!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @param worthAdjustId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String worthAdjustId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfMoveableLowestWorthAdjust mfMoveableLowestWorthAdjust = new MfMoveableLowestWorthAdjust();
		mfMoveableLowestWorthAdjust.setWorthAdjustId(worthAdjustId);
		try {
			mfMoveableLowestWorthAdjustFeign.delete(mfMoveableLowestWorthAdjust);
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
	 * @param pledgeNo 
	 * @param busPleId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model, String pledgeNo, String busPleId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData formworthadjust0001 = formService.getFormData("worthadjust0001");
		MfMoveableLowestWorthAdjust mfMoveableLowestWorthAdjust = new MfMoveableLowestWorthAdjust();
		mfMoveableLowestWorthAdjust=mfMoveableLowestWorthAdjustFeign.initLowestWorthAdjust(busPleId, pledgeNo);
		getObjValue(formworthadjust0001, mfMoveableLowestWorthAdjust);
		model.addAttribute("formworthadjust0001", formworthadjust0001);
		model.addAttribute("query", "");
		 return "/component/collateral/movable/MfMoveableLowestWorthAdjust_Insert";
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
		FormData  formworthadjust0002 = formService.getFormData("worthadjust0002");
		 getFormValue(formworthadjust0002);
		MfMoveableLowestWorthAdjust  mfMoveableLowestWorthAdjust = new MfMoveableLowestWorthAdjust();
		 setObjValue(formworthadjust0002, mfMoveableLowestWorthAdjust);
		 mfMoveableLowestWorthAdjustFeign.insert(mfMoveableLowestWorthAdjust);
		 getObjValue(formworthadjust0002, mfMoveableLowestWorthAdjust);
		 this.addActionMessage(model, "保存成功");
		 List<MfMoveableLowestWorthAdjust> mfMoveableLowestWorthAdjustList = (List<MfMoveableLowestWorthAdjust>)mfMoveableLowestWorthAdjustFeign.findByPage(this.getIpage(), mfMoveableLowestWorthAdjust).getResult();
		model.addAttribute("formworthadjust0002", formworthadjust0002);
		model.addAttribute("query", "");
		 return "/component/collateral/movable/MfMoveableLowestWorthAdjust_Insert";
	}
	/**
	 * 查询
	 * @param worthAdjustId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData, String worthAdjustId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formworthadjust0001 = formService.getFormData("worthadjust0001");
		 getFormValue(formworthadjust0001);
		MfMoveableLowestWorthAdjust  mfMoveableLowestWorthAdjust = new MfMoveableLowestWorthAdjust();
		mfMoveableLowestWorthAdjust.setWorthAdjustId(worthAdjustId);
		 mfMoveableLowestWorthAdjust = mfMoveableLowestWorthAdjustFeign.getById(mfMoveableLowestWorthAdjust);
		 getObjValue(formworthadjust0001, mfMoveableLowestWorthAdjust);
		model.addAttribute("formworthadjust0001", formworthadjust0001);
		model.addAttribute("query", "");
		 return "/component/collateral/movable/MfMoveableLowestWorthAdjust_Detail";
	}
	/**
	 * 删除
	 * @param worthAdjustId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String ajaxData, String worthAdjustId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		MfMoveableLowestWorthAdjust mfMoveableLowestWorthAdjust = new MfMoveableLowestWorthAdjust();
		mfMoveableLowestWorthAdjust.setWorthAdjustId(worthAdjustId);
		mfMoveableLowestWorthAdjustFeign.delete(mfMoveableLowestWorthAdjust);
		return getListPage(model);
	}
	
}
