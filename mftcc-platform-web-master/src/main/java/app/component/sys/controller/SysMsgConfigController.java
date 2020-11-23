package app.component.sys.controller;

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
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;

import app.component.common.EntityUtil;
import app.component.sys.entity.SysMsgConfig;
import app.component.sys.feign.SysMsgConfigFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
@Controller
@RequestMapping("/sysMsgConfig")
public class SysMsgConfigController  extends BaseFormBean {

	private static final long serialVersionUID = 9196454891709523438L;
	@Autowired  
	private HttpServletRequest request;
	@Autowired  
	private HttpServletResponse response;
	@Autowired
	private SysMsgConfigFeign sysMsgConfigFeign;
	private String query;
	//表单变量
	private FormData formsys0001;
	private FormData formsys0002;
	private FormData formsys0003;
	private FormService formService = new FormService();
	
	public SysMsgConfigController(){
		query = "";
	}

	/**
	 * 获取提示消息模板的json字符串。
	 * @author LiuYF
	 * @return
	 */
	@RequestMapping(value = "/getMessageEnumJSONAjax")
	@ResponseBody
	public Map<String, Object> getMessageEnumJSONAjax() {
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		dataMap.put("messageObj", MessageEnum.getObjStrForJS());
		dataMap.put("flag", "success");
		return dataMap;
	}
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request,response);
		return "/component/sys/SysMsgConfig_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(String ajaxData,int pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		SysMsgConfig sysMsgConfig = new SysMsgConfig();
		try {
			sysMsgConfig.setCustomQuery(ajaxData);//自定义查询参数赋值
			sysMsgConfig.setCriteriaList(sysMsgConfig, ajaxData);//我的筛选
			//this.getRoleConditions(sysMsgConfig,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("sysMsgConfig", sysMsgConfig));
			ipage = sysMsgConfigFeign.findByPage(ipage);
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
	@RequestMapping(value = "/inputAjax")
	@ResponseBody
	public Map<String, Object> inputAjax() throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		SysMsgConfig sysMsgConfig = new SysMsgConfig();	
		formsys0002 = formService.getFormData("sys0002");
		getObjValue(formsys0002,sysMsgConfig);
		JsonFormUtil jfu = new JsonFormUtil();
		String  formHtml = jfu.getJsonStr(formsys0002,"bigFormTag",query);
		dataMap.put("formHtml",formHtml);
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
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			formsys0002 = formService.getFormData("sys0002");
			getFormValue(formsys0002, getMapByJson(ajaxData));
			if(this.validateFormData(formsys0002)){
				SysMsgConfig sysMsgConfig = new SysMsgConfig();
				setObjValue(formsys0002, sysMsgConfig);
				SysMsgConfig smc = null;
				smc = sysMsgConfigFeign.getById(sysMsgConfig);
				if(smc==null){
					sysMsgConfigFeign.insert(sysMsgConfig);
					dataMap.put("flag", "success");
					dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
				}else{
					dataMap.put("flag", "error");
					dataMap.put("msg", "消息编号已存在！");
				}
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
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		formsys0002 = formService.getFormData("sys0002");
		getFormValue(formsys0002, getMapByJson(ajaxData));
		SysMsgConfig sysMsgConfigJsp = new SysMsgConfig();
		setObjValue(formsys0002, sysMsgConfigJsp);
		SysMsgConfig sysMsgConfig = sysMsgConfigFeign.getById(sysMsgConfigJsp);
		if(sysMsgConfig!=null){
			try{
				sysMsgConfig = (SysMsgConfig)EntityUtil.reflectionSetVal(sysMsgConfig, sysMsgConfigJsp, getMapByJson(ajaxData));
				sysMsgConfigFeign.update(sysMsgConfig);
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
	@RequestMapping(value = "/updateInputAjax")
	@ResponseBody
	public Map<String, Object> updateInputAjax(String msgNo) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		formsys0003 = formService.getFormData("sys0003");
		SysMsgConfig sysMsgConfig = new SysMsgConfig();
		sysMsgConfig.setMsgNo(msgNo);
		sysMsgConfig = sysMsgConfigFeign.getById(sysMsgConfig);
		getObjValue(formsys0003,sysMsgConfig);
		JsonFormUtil jfu = new JsonFormUtil();
		String  formHtml = jfu.getJsonStr(formsys0003,"bigFormTag",query);
		dataMap.put("formHtml",formHtml);
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
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		SysMsgConfig sysMsgConfig = new SysMsgConfig();
		try{
			formsys0003 = formService.getFormData("sys0003");
			getFormValue(formsys0003, getMapByJson(ajaxData));
			if(this.validateFormData(formsys0003)){
				sysMsgConfig = new SysMsgConfig();
				setObjValue(formsys0003, sysMsgConfig);
				sysMsgConfigFeign.update(sysMsgConfig);
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
	public Map<String, Object> getByIdAjax(String msgNo) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		formsys0002 = formService.getFormData("sys0002");
		SysMsgConfig sysMsgConfig = new SysMsgConfig();
		sysMsgConfig.setMsgNo(msgNo);
		sysMsgConfig = sysMsgConfigFeign.getById(sysMsgConfig);
		getObjValue(formsys0002,sysMsgConfig);
		JsonFormUtil jfu = new JsonFormUtil();
		String  formHtml = jfu.getJsonStr(formsys0002,"bigFormTag",query);
		dataMap.put("formHtml",formHtml);
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String msgNo) throws Exception{
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		SysMsgConfig sysMsgConfig = new SysMsgConfig();
		sysMsgConfig.setMsgNo(msgNo);
		try {
			sysMsgConfigFeign.delete(sysMsgConfig);
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
		ActionContext.initialize(request,response);
		formsys0002 = formService.getFormData("sys0002");
		model.addAttribute("formsys0002", formsys0002);
		return "/component/sys/SysMsgConfig_Insert";
	}
	/***
	 * 新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception{
		ActionContext.initialize(request,response);
		 formsys0002 = formService.getFormData("sys0002");
		 getFormValue(formsys0002);
		 SysMsgConfig sysMsgConfig = new SysMsgConfig();
		 setObjValue(formsys0002, sysMsgConfig);
		 sysMsgConfigFeign.insert(sysMsgConfig);
		 getObjValue(formsys0002, sysMsgConfig);
		 this.addActionMessage(model,"保存成功");
		 Ipage ipage = this.getIpage();
		 ipage.setParams(this.setIpageParams("sysMsgConfig", sysMsgConfig));
		 List<SysMsgConfig> sysMsgConfigList = (List<SysMsgConfig>)sysMsgConfigFeign.findByPage(ipage).getResult();
		model.addAttribute("formsys0002", formsys0002);
		model.addAttribute("sysMsgConfigList", sysMsgConfigList);
		 return "insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(String msgNo,Model model) throws Exception{
		ActionContext.initialize(request,response);
		 formsys0001 = formService.getFormData("sys0001");
		 getFormValue(formsys0001);
		 SysMsgConfig sysMsgConfig = new SysMsgConfig();
		sysMsgConfig.setMsgNo(msgNo);
		 sysMsgConfig = sysMsgConfigFeign.getById(sysMsgConfig);
		 getObjValue(formsys0001, sysMsgConfig);
		 model.addAttribute("formsys0001", formsys0001);
		return "/component/sys/SysMsgConfig_Detail";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(String msgNo) throws Exception {
		ActionContext.initialize(request,
				response);
		SysMsgConfig sysMsgConfig = new SysMsgConfig();
		sysMsgConfig.setMsgNo(msgNo);
		sysMsgConfigFeign.delete(sysMsgConfig);
		return getListPage();
	}
	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	public void validateInsert() throws Exception{
		 ActionContext.initialize(request, response);
		 formsys0002 = formService.getFormData("sys0002");
		 getFormValue(formsys0002);
		 boolean validateFlag = this.validateFormData(formsys0002);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	public void validateUpdate() throws Exception{
		 ActionContext.initialize(request, response);
		 formsys0002 = formService.getFormData("sys0002");
		 getFormValue(formsys0002);
		 boolean validateFlag = this.validateFormData(formsys0002);
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public FormData getFormsys0002() {
		return formsys0002;
	}
	public void setFormsys0002(FormData formsys0002) {
		this.formsys0002 = formsys0002;
	}
	public FormData getFormsys0001() {
		return formsys0001;
	}
	public void setFormsys0001(FormData formsys0001) {
		this.formsys0001 = formsys0001;
	}
	public FormData getFormsys0003() {
		return formsys0003;
	}

	public void setFormsys0003(FormData formsys0003) {
		this.formsys0003 = formsys0003;
	}
	
}
