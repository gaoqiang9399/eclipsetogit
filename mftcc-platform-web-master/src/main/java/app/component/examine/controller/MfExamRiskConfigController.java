package  app.component.examine.controller;
import java.util.ArrayList;
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
import app.component.examine.entity.MfExamRiskConfig;
import app.component.examine.feign.MfExamRiskConfigFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: MfExamRiskConfigAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Thu Feb 16 15:01:00 CST 2017
 **/
@Controller
@RequestMapping(value="/mfExamRiskConfig")
public class MfExamRiskConfigController extends BaseFormBean{
	//注入MfExamRiskConfigBo
	@Autowired
	private MfExamRiskConfigFeign mfExamRiskConfigFeign;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
	private Map<String,Object> dataMap;
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request,
				response);
		return "MfExamRiskConfig_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/findByPageAjax")
	public Map<String,Object> findByPageAjax(String ajaxData,int pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request,
				response);
		dataMap = new HashMap<String, Object>(); 
		MfExamRiskConfig mfExamRiskConfig = new MfExamRiskConfig();
		try {
			mfExamRiskConfig.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfExamRiskConfig.setCriteriaList(mfExamRiskConfig, ajaxData);//我的筛选
			//this.getRoleConditions(mfExamRiskConfig,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = mfExamRiskConfigFeign.findByPage(ipage, mfExamRiskConfig);
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
	@ResponseBody
	@RequestMapping(value="/insertAjax")
	public Map<String,Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		dataMap = new HashMap<String, Object>(); 
		try{
			FormData formexamrisk0002 = formService.getFormData("examrisk0002");
			getFormValue(formexamrisk0002, getMapByJson(ajaxData));
			if(this.validateFormData(formexamrisk0002)){
				MfExamRiskConfig mfExamRiskConfig = new MfExamRiskConfig();
				setObjValue(formexamrisk0002, mfExamRiskConfig);
				mfExamRiskConfigFeign.insert(mfExamRiskConfig);
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
	@ResponseBody
	@RequestMapping(value="/updateAjaxByOne")
	public Map<String,Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		dataMap = new HashMap<String, Object>();
		FormData formexamrisk0002 = formService.getFormData("examrisk0002");
		getFormValue(formexamrisk0002, getMapByJson(ajaxData));
		MfExamRiskConfig mfExamRiskConfigJsp = new MfExamRiskConfig();
		setObjValue(formexamrisk0002, mfExamRiskConfigJsp);
		MfExamRiskConfig mfExamRiskConfig = mfExamRiskConfigFeign.getById(mfExamRiskConfigJsp);
		if(mfExamRiskConfig!=null){
			try{
				mfExamRiskConfig = (MfExamRiskConfig)EntityUtil.reflectionSetVal(mfExamRiskConfig, mfExamRiskConfigJsp, getMapByJson(ajaxData));
				mfExamRiskConfigFeign.update(mfExamRiskConfig);
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
	@ResponseBody
	@RequestMapping(value="/updateAjax")
	public Map<String,Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		dataMap = new HashMap<String, Object>(); 
		MfExamRiskConfig mfExamRiskConfig = new MfExamRiskConfig();
		try{
			FormData formexamrisk0002 = formService.getFormData("examrisk0002");
			getFormValue(formexamrisk0002, getMapByJson(ajaxData));
			if(this.validateFormData(formexamrisk0002)){
				mfExamRiskConfig = new MfExamRiskConfig();
				setObjValue(formexamrisk0002, mfExamRiskConfig);
				mfExamRiskConfigFeign.update(mfExamRiskConfig);
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
	 * 
	 * 方法描述：更新风险模板状态 
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2017-2-17 下午9:02:49
	 */
	@ResponseBody
	@RequestMapping(value="/updateStsAjax")
	public Map<String,Object> updateStsAjax(String ajaxData,String tableId) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		dataMap = new HashMap<String, Object>(); 
		MfExamRiskConfig mfExamRiskConfig = new MfExamRiskConfig();
		try{
			JSONObject jobj = JSONObject.fromObject(ajaxData);
			FormData formexamrisk0002 = formService.getFormData("examrisk0002");
			getFormValue(formexamrisk0002, jobj);
			setObjValue(formexamrisk0002, mfExamRiskConfig);
			int count = mfExamRiskConfigFeign.updateSts(mfExamRiskConfig);
			if(count>0){
				mfExamRiskConfig = mfExamRiskConfigFeign.getById(mfExamRiskConfig);
				List<MfExamRiskConfig> mfExamRiskConfigList = new ArrayList<MfExamRiskConfig>();
				mfExamRiskConfigList.add(mfExamRiskConfig);
				getTableData(mfExamRiskConfigList, tableId);
				dataMap.put("flag", "success");
				dataMap.put("msg",MessageEnum.SUCCEED_UPDATE.getMessage());
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg",MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}
	
	private void getTableData(List<MfExamRiskConfig> list,String tableId) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String  tableHtml = jtu.getJsonStr(tableId,"thirdTableTag", list, null,true);
		dataMap.put("tableData",tableHtml);
	}
	/**
	 * 
	 * 方法描述： 获得可用的风险模型
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2017-2-17 上午9:57:13
	 */
	@ResponseBody
	@RequestMapping(value="/getExamRiskConfigAjax")
	public Map<String,Object> getExamRiskConfigAjax() throws Exception {
		ActionContext.initialize(request,
				response);
		dataMap = new HashMap<String, Object>(); 
		MfExamRiskConfig mfExamRiskConfig = new MfExamRiskConfig();
		try{
			mfExamRiskConfig.setUseFlag("1");
			List<MfExamRiskConfig> mfExamRiskConfigList = mfExamRiskConfigFeign.getExamRiskConfigList(mfExamRiskConfig);
			if(mfExamRiskConfigList!=null&&mfExamRiskConfigList.size()>0){
				dataMap.put("riskConfigList", mfExamRiskConfigList);
				dataMap.put("flag", "success");
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
	@ResponseBody
	@RequestMapping(value="/getByIdAjax")
	public Map<String,Object> getByIdAjax(String modelId) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		dataMap = new HashMap<String, Object>(); 
		FormData formexamrisk0002 = formService.getFormData("examrisk0002");
		MfExamRiskConfig mfExamRiskConfig = new MfExamRiskConfig();
		mfExamRiskConfig.setModelId(modelId);
		mfExamRiskConfig = mfExamRiskConfigFeign.getById(mfExamRiskConfig);
		getObjValue(formexamrisk0002, mfExamRiskConfig,formData);
		if(mfExamRiskConfig!=null){
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
	@ResponseBody
	@RequestMapping(value="/deleteAjax")
	public Map<String,Object> deleteAjax(String modelId) throws Exception{
		ActionContext.initialize(request,
				response);
		
		dataMap = new HashMap<String, Object>(); 
		MfExamRiskConfig mfExamRiskConfig = new MfExamRiskConfig();
		mfExamRiskConfig.setModelId(modelId);
		try {
			mfExamRiskConfigFeign.delete(mfExamRiskConfig);
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
	@RequestMapping(value="/input")
	public String input(Model model) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formexamrisk0002 = formService.getFormData("examrisk0002");
		model.addAttribute("formexamrisk0002", formexamrisk0002);
		return "MfExamRiskConfig_Insert";
	}
	/***
	 * 新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/insert")
	public String insert(Model model) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formexamrisk0002 = formService.getFormData("examrisk0002");
		 getFormValue(formexamrisk0002);
		 MfExamRiskConfig mfExamRiskConfig = new MfExamRiskConfig();
		 setObjValue(formexamrisk0002, mfExamRiskConfig);
		 mfExamRiskConfigFeign.insert(mfExamRiskConfig);
		 getObjValue(formexamrisk0002, mfExamRiskConfig);
		 this.addActionMessage(model,"保存成功");
		 List<MfExamRiskConfig> mfExamRiskConfigList = (List<MfExamRiskConfig>)mfExamRiskConfigFeign.findByPage(this.getIpage(), mfExamRiskConfig).getResult();
		 model.addAttribute("formexamrisk0002", formexamrisk0002);
		 model.addAttribute("mfExamRiskConfigList", mfExamRiskConfigList);
		return "MfExamRiskConfig_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getById")
	public String getById(Model model,String modelId) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		 FormData formexamrisk0002 = formService.getFormData("examrisk0002");
		 getFormValue(formexamrisk0002);
		 MfExamRiskConfig mfExamRiskConfig = new MfExamRiskConfig();
		mfExamRiskConfig.setModelId(modelId);
		 mfExamRiskConfig = mfExamRiskConfigFeign.getById(mfExamRiskConfig);
		 getObjValue(formexamrisk0002, mfExamRiskConfig);
		 model.addAttribute("formexamrisk0002", formexamrisk0002);
		return "MfExamRiskConfig_Detail";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public String delete(String modelId) throws Exception {
		ActionContext.initialize(request,
				response);
		MfExamRiskConfig mfExamRiskConfig = new MfExamRiskConfig();
		mfExamRiskConfig.setModelId(modelId);
		mfExamRiskConfigFeign.delete(mfExamRiskConfig);
		return getListPage();
	}
	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/validateInsert")
	public void validateInsert() throws Exception{
		 ActionContext.initialize(request, response);
		 FormService formService = new FormService();
		 FormData formexamrisk0002 = formService.getFormData("examrisk0002");
		 getFormValue(formexamrisk0002);
		 boolean validateFlag = this.validateFormData(formexamrisk0002);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/validateUpdate")
	public void validateUpdate() throws Exception{
		 ActionContext.initialize(request, response);
		 FormService formService = new FormService();
		 FormData formexamrisk0002 = formService.getFormData("examrisk0002");
		 getFormValue(formexamrisk0002);
		 boolean validateFlag = this.validateFormData(formexamrisk0002);
	}
	
	
}
