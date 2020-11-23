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
import app.component.examine.entity.MfExamRemindConfig;
import app.component.examine.feign.MfExamRemindConfigFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: MfExamRemindConfigAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Fri Feb 10 14:50:22 CST 2017
 **/
@Controller
@RequestMapping(value="/mfExamRemindConfig")
public class MfExamRemindConfigController extends BaseFormBean{
	//注入MfExamRemindConfigBo
	@Autowired
	private MfExamRemindConfigFeign mfExamRemindConfigFeign;
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
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
		return "MfExamRemindConfig_List";
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
		MfExamRemindConfig mfExamRemindConfig = new MfExamRemindConfig();
		try {
			mfExamRemindConfig.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfExamRemindConfig.setCriteriaList(mfExamRemindConfig, ajaxData);//我的筛选
			//this.getRoleConditions(mfExamRemindConfig,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = mfExamRemindConfigFeign.findByPage(ipage, mfExamRemindConfig);
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
			FormData formexamrem0001 = formService.getFormData("examrem0001");
			getFormValue(formexamrem0001, getMapByJson(ajaxData));
			if(this.validateFormData(formexamrem0001)){
				MfExamRemindConfig mfExamRemindConfig = new MfExamRemindConfig();
				setObjValue(formexamrem0001, mfExamRemindConfig);
				mfExamRemindConfigFeign.insert(mfExamRemindConfig);
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
		FormData formexamrem0002 = formService.getFormData("examrem0002");
		getFormValue(formexamrem0002, getMapByJson(ajaxData));
		MfExamRemindConfig mfExamRemindConfigJsp = new MfExamRemindConfig();
		setObjValue(formexamrem0002, mfExamRemindConfigJsp);
		MfExamRemindConfig mfExamRemindConfig = mfExamRemindConfigFeign.getById(mfExamRemindConfigJsp);
		if(mfExamRemindConfig!=null){
			try{
				mfExamRemindConfig = (MfExamRemindConfig)EntityUtil.reflectionSetVal(mfExamRemindConfig, mfExamRemindConfigJsp, getMapByJson(ajaxData));
				mfExamRemindConfigFeign.update(mfExamRemindConfig);
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
		MfExamRemindConfig mfExamRemindConfig = new MfExamRemindConfig();
		try{
			FormData formexamrem0001 = formService.getFormData("examrem0001");
			getFormValue(formexamrem0001, getMapByJson(ajaxData));
			if(this.validateFormData(formexamrem0001)){
				mfExamRemindConfig = new MfExamRemindConfig();
				setObjValue(formexamrem0001, mfExamRemindConfig);
				mfExamRemindConfigFeign.update(mfExamRemindConfig);
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
	 * 方法描述： 修改禁用启用状态
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2017-2-13 上午10:03:59
	 */
	@ResponseBody
	@RequestMapping(value="/updateStsAjax")
	public Map<String,Object> updateStsAjax(String ajaxData,String tableId) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		dataMap = new HashMap<String, Object>(); 
		MfExamRemindConfig mfExamRemindConfig = new MfExamRemindConfig();
		try{
			JSONObject jobj = JSONObject.fromObject(ajaxData);
			FormData formexamrem0001 = formService.getFormData("examrem0001");
			getFormValue(formexamrem0001, jobj);
			setObjValue(formexamrem0001, mfExamRemindConfig);
			int count = mfExamRemindConfigFeign.updateSts(mfExamRemindConfig);
			if(count>0){
				mfExamRemindConfig = mfExamRemindConfigFeign.getById(mfExamRemindConfig);
				ArrayList<MfExamRemindConfig> mfExamRemindConfigList = new ArrayList<MfExamRemindConfig>();
				mfExamRemindConfigList.add(mfExamRemindConfig);
				getTableData(mfExamRemindConfigList, tableId);
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
	
	private void getTableData(List<MfExamRemindConfig> list,String tableId) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String  tableHtml = jtu.getJsonStr(tableId,"thirdTableTag", list, null,true);
		dataMap.put("tableData",tableHtml);
	}
	/**
	 * AJAX获取查看
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/getByIdAjax")
	public Map<String,Object> getByIdAjax(String remindId) throws Exception {
		ActionContext.initialize(request,
				response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		dataMap = new HashMap<String, Object>(); 
		FormData formexamrem0002 = formService.getFormData("examrem0002");
		MfExamRemindConfig mfExamRemindConfig = new MfExamRemindConfig();
		mfExamRemindConfig.setRemindId(remindId);
		mfExamRemindConfig = mfExamRemindConfigFeign.getById(mfExamRemindConfig);
		getObjValue(formexamrem0002, mfExamRemindConfig,formData);
		if(mfExamRemindConfig!=null){
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
	public Map<String,Object> deleteAjax(String remindId) throws Exception{
		ActionContext.initialize(request,
				response);
		dataMap = new HashMap<String, Object>(); 
		MfExamRemindConfig mfExamRemindConfig = new MfExamRemindConfig();
		mfExamRemindConfig.setRemindId(remindId);
		try {
			mfExamRemindConfigFeign.delete(mfExamRemindConfig);
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
		FormData formexamrem0001 = formService.getFormData("examrem0001");
		model.addAttribute("formexamrem0001", formexamrem0001);
		return "MfExamRemindConfig_Insert";
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
		FormData formexamrem0002 = formService.getFormData("examrem0002");
		 getFormValue(formexamrem0002);
		 MfExamRemindConfig mfExamRemindConfig = new MfExamRemindConfig();
		 setObjValue(formexamrem0002, mfExamRemindConfig);
		 mfExamRemindConfigFeign.insert(mfExamRemindConfig);
		 getObjValue(formexamrem0002, mfExamRemindConfig);
		 this.addActionMessage(model,"保存成功");
		 List<MfExamRemindConfig> mfExamRemindConfigList = (List<MfExamRemindConfig>)mfExamRemindConfigFeign.findByPage(this.getIpage(), mfExamRemindConfig).getResult();
		 model.addAttribute("formexamrem0002", formexamrem0002);
		 model.addAttribute("mfExamRemindConfigList", mfExamRemindConfigList);
		return "MfExamRemindConfig_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getById")
	public String getById(Model model,String remindId) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		FormData formexamrem0001 = formService.getFormData("examrem0001");
		 getFormValue(formexamrem0001);
		 MfExamRemindConfig mfExamRemindConfig = new MfExamRemindConfig();
		mfExamRemindConfig.setRemindId(remindId);
		 mfExamRemindConfig = mfExamRemindConfigFeign.getById(mfExamRemindConfig);
		 getObjValue(formexamrem0001, mfExamRemindConfig);
		 model.addAttribute("formexamrem0001", formexamrem0001);
		return "MfExamRemindConfig_Detail";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public String delete(String remindId) throws Exception {
		ActionContext.initialize(request,
				response);
		MfExamRemindConfig mfExamRemindConfig = new MfExamRemindConfig();
		mfExamRemindConfig.setRemindId(remindId);
		mfExamRemindConfigFeign.delete(mfExamRemindConfig);
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
		 FormData formexamrem0002 = formService.getFormData("examrem0002");
		 getFormValue(formexamrem0002);
		 boolean validateFlag = this.validateFormData(formexamrem0002);
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
		 FormData formexamrem0002 = formService.getFormData("examrem0002");
		 getFormValue(formexamrem0002);
		 boolean validateFlag = this.validateFormData(formexamrem0002);
	}
	
	
}
