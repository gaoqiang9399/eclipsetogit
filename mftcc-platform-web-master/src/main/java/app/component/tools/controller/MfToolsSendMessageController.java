package  app.component.tools.controller;
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
import app.component.sysextendinterface.SysExtendInterfaceFeign;
import app.component.tools.entity.MfToolsSendMessage;
import app.component.tools.feign.MfToolsSendMessageFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.StringUtil;
import net.sf.json.JSONArray;

/**
 * Title: MfToolsSendMessageAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Sat Oct 08 14:57:44 CST 2016
 **/
/**
 * 类名： MfToolsSendMessageAction
 * 描述：
 * @author谢静霞
 * @date 2017-1-16 上午11:17:58
 * 
 *
 */
@Controller
@RequestMapping(value = "/mfToolsSendMessage")
public class MfToolsSendMessageController extends BaseFormBean{
	
	//注入MfToolsSendMessageBo
	@Autowired
	private MfToolsSendMessageFeign mfToolsSendMessageFeign;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	
	@Autowired
	private SysExtendInterfaceFeign sysExtendInterfaceFeign;
	
	/*
	//全局变量
	private MfToolsSendMessage mfToolsSendMessage;
	private List<MfToolsSendMessage> mfToolsSendMessageList;
	private String id;		
	private String tableType;
	private String tableId;
	private int pageNo;
	private String query;
	private String cusTel;
	private String sendMsg;
	private String sendMsgType;
	//接口

	public SysExtendInterface getSysExtendInterface() {
		return sysExtendInterface;
	}
  
	public void setSysExtendInterface(SysExtendInterface sysExtendInterface) {
		this.sysExtendInterface = sysExtendInterface;
	}
	//异步参数
	private String ajaxData;
	private Map<String,Object> dataMap;
	//表单变量
	private FormData formtools0001;
	private FormData formtools0002;
	private FormData formtools00002;
	
	private FormService formService = new FormService();
	
	public MfToolsSendMessageController(){
		query = "";
	}
	*/
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request,response);
		MfToolsSendMessage mfToolsSendMessage = new MfToolsSendMessage();
		List<MfToolsSendMessage> mfToolsSendMessageList = (List<MfToolsSendMessage>)mfToolsSendMessageFeign.findByPage(this.getIpage(), mfToolsSendMessage).getResult();
		model.addAttribute("mfToolsSendMessageList", mfToolsSendMessageList);
		model.addAttribute("query", "");
		return "/component/tools/MfToolsSendMessage_List";
	}
	/***
	 * 列表数据查询 
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/findByPageAjax")
	public Map<String, Object> findByPageAjax(String ajaxData,int pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfToolsSendMessage mfToolsSendMessage = new MfToolsSendMessage();
		try {
			mfToolsSendMessage.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfToolsSendMessage.setCriteriaList(mfToolsSendMessage, ajaxData);//我的筛选
			//this.getRoleConditions(mfToolsSendMessage,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = mfToolsSendMessageFeign.findByPage(ipage, mfToolsSendMessage);
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
	@RequestMapping(value = "/insertAjax")
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			FormData formtools0001 = formService.getFormData("tools0001");
			getFormValue(formtools0001, getMapByJson(ajaxData));
			if (this.validateFormData(formtools0001)) {
				MfToolsSendMessage mfToolsSendMessage = new MfToolsSendMessage();
				setObjValue(formtools0001, mfToolsSendMessage);
				mfToolsSendMessage = mfToolsSendMessageFeign.insert(mfToolsSendMessage);
				if ("1".equals(mfToolsSendMessage.getSendSts())) {
					dataMap.put("flag", "success");
					dataMap.put("msg",MessageEnum.SUCCEED_MSG_SEND.getMessage());
					dataMap.put("sendMessage", mfToolsSendMessage);
				} else  {
					dataMap.put("flag", "failed");
					dataMap.put("msg", mfToolsSendMessage.getSendResultMsg());
					dataMap.put("sendMessage", mfToolsSendMessage);
				}
			}else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
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
	@RequestMapping(value = "/updateAjaxByOne")
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formtools0002 = formService.getFormData("tools0002");
		getFormValue(formtools0002, getMapByJson(ajaxData));
		MfToolsSendMessage mfToolsSendMessageJsp = new MfToolsSendMessage();
		setObjValue(formtools0002, mfToolsSendMessageJsp);
		MfToolsSendMessage mfToolsSendMessage = mfToolsSendMessageFeign.getById(mfToolsSendMessageJsp);
		if(mfToolsSendMessage!=null){
			try{
				mfToolsSendMessage = (MfToolsSendMessage)EntityUtil.reflectionSetVal(mfToolsSendMessage, mfToolsSendMessageJsp, getMapByJson(ajaxData));
				mfToolsSendMessageFeign.update(mfToolsSendMessage);
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
	@RequestMapping(value = "/updateAjax")
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfToolsSendMessage mfToolsSendMessage = new MfToolsSendMessage();
		try{
			FormData formtools0002 = formService.getFormData("tools0002");
			getFormValue(formtools0002, getMapByJson(ajaxData));
			if(this.validateFormData(formtools0002)){
				mfToolsSendMessage = new MfToolsSendMessage();
				setObjValue(formtools0002, mfToolsSendMessage);
				mfToolsSendMessageFeign.update(mfToolsSendMessage);
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
	@ResponseBody
	@RequestMapping(value = "/getByIdAjax")
	public Map<String, Object> getByIdAjax(String id) throws Exception {
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formtools0002 = formService.getFormData("tools0002");
		MfToolsSendMessage mfToolsSendMessage = new MfToolsSendMessage();
		mfToolsSendMessage.setId(id);
		mfToolsSendMessage = mfToolsSendMessageFeign.getById(mfToolsSendMessage);
		getObjValue(formtools0002, mfToolsSendMessage,formData);
		if(mfToolsSendMessage!=null){
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
	@RequestMapping(value = "/deleteAjax")
	public Map<String, Object> deleteAjax(String id) throws Exception{
		ActionContext.initialize(request,response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfToolsSendMessage mfToolsSendMessage = new MfToolsSendMessage();
		mfToolsSendMessage.setId(id);
		try {
			mfToolsSendMessageFeign.delete(mfToolsSendMessage);
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
	public String input(Model model,String cusTel,String sendMsgType) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		MfToolsSendMessage mfToolsSendMessage = new MfToolsSendMessage();
		List<MfToolsSendMessage> mfToolsSendMessageList = mfToolsSendMessageFeign.getAllList(mfToolsSendMessage);
		FormData formtools0001 = null;
		if(StringUtil.isNotEmpty(cusTel)&&StringUtil.isNotEmpty(sendMsgType)){
			 formtools0001 = formService.getFormData("tools0001");
			this.changeFormProperty(formtools0001, "msgTel", "initValue", "("+cusTel+")");
			this.changeFormProperty(formtools0001, "msgTel", "readonly", "1");
			this.changeFormProperty(formtools0001, "sendMsgType", "initValue", sendMsgType);
			this.changeFormProperty(formtools0001, "sendMsgType", "readonly", "1");
		}else{
			 formtools0001 = formService.getFormData("tools0001");
		}
		for(MfToolsSendMessage mfToolsSendMessage1:mfToolsSendMessageList){
			mfToolsSendMessage1.setSendTime(DateUtil.getShowDateTime(mfToolsSendMessage1.getSendTime()));
		}
		model.addAttribute("mfToolsSendMessageList", mfToolsSendMessageList);
		model.addAttribute("formtools0001", formtools0001);
		model.addAttribute("query", "");
		return "/component/tools/MfToolsSendMessage_Insert";
	}
	/***
	 * 新增
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "insert")
	public String insert(Model model) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		 FormData formtools0002 = formService.getFormData("tools0002");
		 getFormValue(formtools0002);
		 MfToolsSendMessage mfToolsSendMessage = new MfToolsSendMessage();
		 setObjValue(formtools0002, mfToolsSendMessage);
		 mfToolsSendMessageFeign.insert(mfToolsSendMessage);
		 getObjValue(formtools0002, mfToolsSendMessage);
		 this.addActionMessage(model, "保存成功");
		 List<MfToolsSendMessage> mfToolsSendMessageList = (List<MfToolsSendMessage>)mfToolsSendMessageFeign.findByPage(this.getIpage(), mfToolsSendMessage).getResult();
		 model.addAttribute("formtools0002", formtools0002);
		 model.addAttribute("mfToolsSendMessageList", mfToolsSendMessageList);
		 model.addAttribute("query", "");
		return "/component/tools/MfToolsSendMessage_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getById")
	public String getById(Model model,String id) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		 FormData formtools00002 = formService.getFormData("tools00002");
		 getFormValue(formtools00002);
		 MfToolsSendMessage mfToolsSendMessage = new MfToolsSendMessage();
		 mfToolsSendMessage.setId(id);
		 mfToolsSendMessage = mfToolsSendMessageFeign .getById(mfToolsSendMessage);
		 if ("0".equals(mfToolsSendMessage.getSendSts())) {
			 mfToolsSendMessage.setSendSts("失败");
		}else{
			 mfToolsSendMessage.setSendSts("成功");			
		}
		 getObjValue(formtools00002, mfToolsSendMessage);
		 model.addAttribute("formtools00002", formtools00002);
		 model.addAttribute("query", "");
		return "/component/tools/MfToolsSendMessage_Detail";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "delete")
	public String delete(Model model,String id) throws Exception {
		ActionContext.initialize(request,response);
		MfToolsSendMessage mfToolsSendMessage = new MfToolsSendMessage();
		mfToolsSendMessage.setId(id);
		mfToolsSendMessageFeign.delete(mfToolsSendMessage);
		return getListPage(model);
	}
	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert() throws Exception{
		 ActionContext.initialize(request, response);
		 FormService formService = new FormService();
		 FormData formtools0002 = formService.getFormData("tools0002");
		 getFormValue(formtools0002);
		 boolean validateFlag = this.validateFormData(formtools0002);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate() throws Exception{
		 ActionContext.initialize(request, response);
		 FormService formService = new FormService();
		 FormData formtools0002 = formService.getFormData("tools0002");
		 getFormValue(formtools0002);
		 boolean validateFlag = this.validateFormData(formtools0002);
	}
	
	
	 /**
	 * 方法描述： 获得员工手机号
	 * @return
	 * @throws Exception
	 * String
	 * @author 谢静霞
	 * @date 2017-1-11 上午9:50:25
	 */
	@RequestMapping(value = "/getSysUserListPage")
	public String getSysUserListPage(Model model) throws Exception {
			ActionContext.initialize(request,response);
			JSONArray orgArray = JSONArray.fromObject(sysExtendInterfaceFeign.getAllOrgs());
			JSONArray userArray = JSONArray.fromObject(sysExtendInterfaceFeign.getAllUsers());
	 		for( int i=0;i<orgArray.size();i++){
				orgArray.getJSONObject(i).put("id", orgArray.getJSONObject(i).getString("brNo"));
				orgArray.getJSONObject(i).put("name", orgArray.getJSONObject(i).getString("brName"));
				orgArray.getJSONObject(i).put("pId","0");
				orgArray.getJSONObject(i).put("open",false);	
				orgArray.getJSONObject(i).put("isParent",true);
			}
	 		for( int i=0;i<userArray.size();i++){
	 			userArray.getJSONObject(i).put("id", userArray.getJSONObject(i).getString("opNo"));
	 			userArray.getJSONObject(i).put("name", userArray.getJSONObject(i).getString("opName")+"("+userArray.getJSONObject(i).getString("mobile")+")");
	 			userArray.getJSONObject(i).put("pId",userArray.getJSONObject(i).getString("brNo"));
	 			userArray.getJSONObject(i).put("open",true);
	 			orgArray.add(userArray.getJSONObject(i));
			}	
			String ajaxData = orgArray.toString();
			model.addAttribute("ajaxData", ajaxData);
			return "/component/tools/SysUserTel";
		 }
	@RequestMapping(value = "/inputForTel")
	public String inputForTel(Model model,String cusTel,String sendMsgType) throws Exception{
		ActionContext.initialize(request,response);
		FormService formService = new FormService();
		MfToolsSendMessage mfToolsSendMessage = new MfToolsSendMessage();
		List<MfToolsSendMessage> mfToolsSendMessageList = mfToolsSendMessageFeign.getAllList(mfToolsSendMessage);
		FormData formtools0001 = null;
		if(StringUtil.isNotEmpty(cusTel)&&StringUtil.isNotEmpty(sendMsgType)){
			formtools0001 = formService.getFormData("tools0001");
			this.changeFormProperty(formtools0001, "msgTel", "initValue", "("+cusTel+")");
			this.changeFormProperty(formtools0001, "msgTel", "readonly", "1");
			this.changeFormProperty(formtools0001, "sendMsgType", "initValue", sendMsgType);
			this.changeFormProperty(formtools0001, "sendMsgType", "readonly", "1");
		}else{
			formtools0001 = formService.getFormData("tools0001");
		}
		for(MfToolsSendMessage mfToolsSendMessage1:mfToolsSendMessageList){
			mfToolsSendMessage1.setSendTime(DateUtil.getShowDateTime(mfToolsSendMessage1.getSendTime()));
		}
		model.addAttribute("mfToolsSendMessageList", mfToolsSendMessageList);
		model.addAttribute("formtools0001", formtools0001);
		model.addAttribute("query", "");
		return "/component/tools/MfToolsSendMessage_Insert";
	}
}
