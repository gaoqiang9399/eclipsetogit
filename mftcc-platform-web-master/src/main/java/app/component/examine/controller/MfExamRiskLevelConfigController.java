package  app.component.examine.controller;
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
import app.component.examine.entity.MfExamRiskLevelConfig;
import app.component.examine.feign.MfExamRiskLevelConfigFeign;
import app.util.toolkit.Ipage;

/**
 * Title: MfExamRiskLevelConfigAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed Jul 26 09:56:36 CST 2017
 **/
@Controller
@RequestMapping(value="/mfExamRiskLevelConfig")
public class MfExamRiskLevelConfigController extends BaseFormBean{
	private static final long serialVersionUID = 9196454891709523438L;
	//注入MfExamRiskLevelConfigBo
	@Autowired
	private MfExamRiskLevelConfigFeign mfExamRiskLevelConfigFeign;
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//全局变量
	private Map<String,Object> dataMap;
	//表单变量
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request,
				response);
		return "component/examine/MfExamRiskLevelConfig_List";
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
		MfExamRiskLevelConfig mfExamRiskLevelConfig = new MfExamRiskLevelConfig();
		try {
			mfExamRiskLevelConfig.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfExamRiskLevelConfig.setCriteriaList(mfExamRiskLevelConfig, ajaxData);//我的筛选
			//mfExamRiskLevelConfig.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfExamRiskLevelConfig,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = mfExamRiskLevelConfigFeign.findByPage(ipage, mfExamRiskLevelConfig);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw new Exception(e.getMessage());
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
			FormData formexamrisklevel0001 = formService.getFormData("examrisklevel0001");
			getFormValue(formexamrisklevel0001, getMapByJson(ajaxData));
			if(this.validateFormData(formexamrisklevel0001)){
				MfExamRiskLevelConfig mfExamRiskLevelConfig = new MfExamRiskLevelConfig();
				setObjValue(formexamrisklevel0001, mfExamRiskLevelConfig);
				mfExamRiskLevelConfigFeign.insert(mfExamRiskLevelConfig);
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
			throw new Exception(e.getMessage());
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
		FormData formexamrisklevel0002 = formService.getFormData("examrisklevel0002");
		getFormValue(formexamrisklevel0002, getMapByJson(ajaxData));
		MfExamRiskLevelConfig mfExamRiskLevelConfigJsp = new MfExamRiskLevelConfig();
		setObjValue(formexamrisklevel0002, mfExamRiskLevelConfigJsp);
		MfExamRiskLevelConfig mfExamRiskLevelConfig = mfExamRiskLevelConfigFeign.getById(mfExamRiskLevelConfigJsp);
		if(mfExamRiskLevelConfig!=null){
			try{
				mfExamRiskLevelConfig = (MfExamRiskLevelConfig)EntityUtil.reflectionSetVal(mfExamRiskLevelConfig, mfExamRiskLevelConfigJsp, getMapByJson(ajaxData));
				mfExamRiskLevelConfigFeign.update(mfExamRiskLevelConfig);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			}catch(Exception e){
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw new Exception(e.getMessage());
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
	@ResponseBody
	@RequestMapping(value="/updateAjax")
	public Map<String,Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		dataMap = new HashMap<String, Object>(); 
		MfExamRiskLevelConfig mfExamRiskLevelConfig = new MfExamRiskLevelConfig();
		try{
			FormData formexamrisklevel0001 = formService.getFormData("examrisklevel0001");
			getFormValue(formexamrisklevel0001, getMapByJson(ajaxData));
			if(this.validateFormData(formexamrisklevel0001)){
				mfExamRiskLevelConfig = new MfExamRiskLevelConfig();
				setObjValue(formexamrisklevel0001, mfExamRiskLevelConfig);
				mfExamRiskLevelConfig=mfExamRiskLevelConfigFeign.update(mfExamRiskLevelConfig);
				dataMap.put("examRiskLevelConfig", mfExamRiskLevelConfig);
				dataMap.put("flag", "success");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw new Exception(e.getMessage());
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
	public Map<String,Object> getByIdAjax(String configId) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		dataMap = new HashMap<String, Object>(); 
		FormData formexamrisklevel0002 = formService.getFormData("examrisklevel0002");
		MfExamRiskLevelConfig mfExamRiskLevelConfig = new MfExamRiskLevelConfig();
		mfExamRiskLevelConfig.setConfigId(configId);
		mfExamRiskLevelConfig = mfExamRiskLevelConfigFeign.getById(mfExamRiskLevelConfig);
		getObjValue(formexamrisklevel0002, mfExamRiskLevelConfig,formData);
		if(mfExamRiskLevelConfig!=null){
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
	public Map<String,Object> deleteAjax(String configId) throws Exception{
		ActionContext.initialize(request,
				response);
		dataMap = new HashMap<String, Object>(); 
		MfExamRiskLevelConfig mfExamRiskLevelConfig = new MfExamRiskLevelConfig();
		mfExamRiskLevelConfig.setConfigId(configId);
		try {
			mfExamRiskLevelConfigFeign.delete(mfExamRiskLevelConfig);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", "失败");
			throw new Exception(e.getMessage());
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
		FormData formexamrisklevel0001 = formService.getFormData("examrisklevel0001");
		model.addAttribute("formexamrisklevel0001", formexamrisklevel0001);
		return "component/examine/MfExamRiskLevelConfig_Insert";
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
		FormData formexamrisklevel0002 = formService.getFormData("examrisklevel0002");
		 getFormValue(formexamrisklevel0002);
		 MfExamRiskLevelConfig mfExamRiskLevelConfig = new MfExamRiskLevelConfig();
		 setObjValue(formexamrisklevel0002, mfExamRiskLevelConfig);
		 mfExamRiskLevelConfigFeign.insert(mfExamRiskLevelConfig);
		 getObjValue(formexamrisklevel0002, mfExamRiskLevelConfig);
		 this.addActionMessage(model,"保存成功");
		 List<MfExamRiskLevelConfig> mfExamRiskLevelConfigList = (List<MfExamRiskLevelConfig>)mfExamRiskLevelConfigFeign.findByPage(this.getIpage(), mfExamRiskLevelConfig).getResult();
		 model.addAttribute("formexamrisklevel0002", formexamrisklevel0002);
		 model.addAttribute("mfExamRiskLevelConfigList", mfExamRiskLevelConfigList);
		return "component/examine/MfExamRiskLevelConfig_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getById")
	public String getById(Model model,String configId) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formexamrisklevel0001 = formService.getFormData("examrisklevel0001");
		 getFormValue(formexamrisklevel0001);
		 MfExamRiskLevelConfig mfExamRiskLevelConfig = new MfExamRiskLevelConfig();
		mfExamRiskLevelConfig.setConfigId(configId);
		 mfExamRiskLevelConfig = mfExamRiskLevelConfigFeign.getById(mfExamRiskLevelConfig);
		 getObjValue(formexamrisklevel0001, mfExamRiskLevelConfig);
		 model.addAttribute("formexamrisklevel0001", formexamrisklevel0001);
		 model.addAttribute("query", "");
		return "component/examine/MfExamRiskLevelConfig_Detail";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public String delete(Model model,String configId) throws Exception {
		ActionContext.initialize(request,
				response);
		MfExamRiskLevelConfig mfExamRiskLevelConfig = new MfExamRiskLevelConfig();
		mfExamRiskLevelConfig.setConfigId(configId);
		mfExamRiskLevelConfigFeign.delete(mfExamRiskLevelConfig);
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
		 FormData formexamrisklevel0002 = formService.getFormData("examrisklevel0002");
		 getFormValue(formexamrisklevel0002);
		 boolean validateFlag = this.validateFormData(formexamrisklevel0002);
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
		 FormData formexamrisklevel0002 = formService.getFormData("examrisklevel0002");
		 getFormValue(formexamrisklevel0002);
		 boolean validateFlag = this.validateFormData(formexamrisklevel0002);
	}
	
}
