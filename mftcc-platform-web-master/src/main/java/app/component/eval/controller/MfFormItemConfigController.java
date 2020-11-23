package  app.component.eval.controller;
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

import app.component.common.EntityUtil;
import app.component.eval.entity.MfFormItemConfig;
import app.component.eval.feign.MfFormItemConfigFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;

/**
 * Title: MfFormItemConfigAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Sat Nov 05 15:54:18 CST 2016
 **/
@Controller
@RequestMapping("/mfFormItemConfig")
public class MfFormItemConfigController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfFormItemConfigBo
	@Autowired
	private MfFormItemConfigFeign mfFormItemConfigFeign;
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		return "/component/eval/MfFormItemConfig_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfFormItemConfig mfFormItemConfig = new MfFormItemConfig();
		try {
			mfFormItemConfig.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfFormItemConfig.setCriteriaList(mfFormItemConfig, ajaxData);//我的筛选
			//this.getRoleConditions(mfFormItemConfig,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = mfFormItemConfigFeign.findByPage(ipage, mfFormItemConfig);
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
		FormData 	formevalitem0002 = formService.getFormData("evalitem0002");
			getFormValue(formevalitem0002, getMapByJson(ajaxData));
			if(this.validateFormData(formevalitem0002)){
		MfFormItemConfig mfFormItemConfig = new MfFormItemConfig();
				setObjValue(formevalitem0002, mfFormItemConfig);
				mfFormItemConfigFeign.insert(mfFormItemConfig);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR_INSERT.getMessage());
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
		FormData formevalitem0002 = formService.getFormData("evalitem0002");
		getFormValue(formevalitem0002, getMapByJson(ajaxData));
		MfFormItemConfig mfFormItemConfigJsp = new MfFormItemConfig();
		setObjValue(formevalitem0002, mfFormItemConfigJsp);
		MfFormItemConfig mfFormItemConfig = mfFormItemConfigFeign.getById(mfFormItemConfigJsp);
		if(mfFormItemConfig!=null){
			try{
				mfFormItemConfig = (MfFormItemConfig)EntityUtil.reflectionSetVal(mfFormItemConfig, mfFormItemConfigJsp, getMapByJson(ajaxData));
				mfFormItemConfigFeign.update(mfFormItemConfig);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			}catch(Exception e){
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg",MessageEnum.ERROR_INSERT.getMessage());
				throw e;
			}
		}else{
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
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
		FormData 	formevalitem0002 = formService.getFormData("evalitem0002");
			getFormValue(formevalitem0002, getMapByJson(ajaxData));
			if(this.validateFormData(formevalitem0002)){
		MfFormItemConfig mfFormItemConfig = new MfFormItemConfig();
				setObjValue(formevalitem0002, mfFormItemConfig);
				mfFormItemConfigFeign.update(mfFormItemConfig);
				dataMap.put("flag", "success");
				dataMap.put("msg",MessageEnum.SUCCEED_UPDATE.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
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
	public Map<String, Object> getByIdAjax(String ajaxData,String itemId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formevalitem0002 = formService.getFormData("evalitem0002");
		MfFormItemConfig mfFormItemConfig = new MfFormItemConfig();
		mfFormItemConfig.setItemId(itemId);
		mfFormItemConfig = mfFormItemConfigFeign.getById(mfFormItemConfig);
		getObjValue(formevalitem0002, mfFormItemConfig,formData);
		if(mfFormItemConfig!=null){
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
	public Map<String, Object> deleteAjax(String ajaxData,String itemId) throws Exception{
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfFormItemConfig mfFormItemConfig = new MfFormItemConfig();
		mfFormItemConfig.setItemId(itemId);
		try {
			mfFormItemConfigFeign.delete(mfFormItemConfig);
			dataMap.put("flag", "success");
			dataMap.put("msg",MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.ERROR.getMessage());
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
		FormData formevalitem0002 = formService.getFormData("evalitem0002");
		model.addAttribute("formevalitem0002", formevalitem0002);
		model.addAttribute("query", "");
		return "/component/eval/MfFormItemConfig_Insert";
	}
	/***
	 * 新增
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formevalitem0002 = formService.getFormData("evalitem0002");
		 getFormValue(formevalitem0002);
		MfFormItemConfig  mfFormItemConfig = new MfFormItemConfig();
		 setObjValue(formevalitem0002, mfFormItemConfig);
		 mfFormItemConfigFeign.insert(mfFormItemConfig);
		 getObjValue(formevalitem0002, mfFormItemConfig);
		 this.addActionMessage(model, "保存成功");
		 List<MfFormItemConfig> mfFormItemConfigList = (List<MfFormItemConfig>)mfFormItemConfigFeign.findByPage(this.getIpage(), mfFormItemConfig).getResult();
		model.addAttribute("mfFormItemConfigList", mfFormItemConfigList);
		model.addAttribute("formevalitem0002", formevalitem0002);
		model.addAttribute("query", "");
		return "/component/eval/MfFormItemConfig_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String itemId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formevalitem0001 = formService.getFormData("evalitem0001");
		 getFormValue(formevalitem0001);
		MfFormItemConfig  mfFormItemConfig = new MfFormItemConfig();
		mfFormItemConfig.setItemId(itemId);
		 mfFormItemConfig = mfFormItemConfigFeign.getById(mfFormItemConfig);
		 getObjValue(formevalitem0001, mfFormItemConfig);
		model.addAttribute("formevalitem0001", formevalitem0001);
		model.addAttribute("query", "");
		return "/component/eval/MfFormItemConfig_Detail";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String itemId) throws Exception {
		ActionContext.initialize(request,
				response);
		MfFormItemConfig mfFormItemConfig = new MfFormItemConfig();
		mfFormItemConfig.setItemId(itemId);
		mfFormItemConfigFeign.delete(mfFormItemConfig);
		return getListPage(model);
	}
}
