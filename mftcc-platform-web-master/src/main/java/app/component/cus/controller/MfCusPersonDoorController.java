package  app.component.cus.controller;
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
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusPersonDoor;
import app.component.cus.entity.MfCusShareholder;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusPersonDoorFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: MfCusPersonDoorAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Mon May 30 09:01:58 CST 2016
 **/
@Controller
@RequestMapping("/mfCusPersonDoor")
public class MfCusPersonDoorController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfCusPersonDoorBo
	@Autowired
	private MfCusPersonDoorFeign mfCusPersonDoorFeign;
	//全局变量
	
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	//异步参数
	//表单变量
	
	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		return "/component/cus/MfCusPersonDoor_List";
	}
	/**
	 * 
	 * 方法描述： 获得大表单中的经营信息
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @date 2016-5-30 上午10:57:41
	 */
	@RequestMapping(value = "/getListPageBig")
	public String getListPageBig(Model model, String cusNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		FormData 	formcusdoor00001 = null;
		List<MfCusPersonDoor> mfCusPersonDoorList = new ArrayList<MfCusPersonDoor>();
		try {
		 	formcusdoor00001 = formService.getFormData("cusdoor00001");
		MfCusPersonDoor 	mfCusPersonDoor = new MfCusPersonDoor();
			mfCusPersonDoor.setCusNo(cusNo);
			Ipage ipage = this.getIpage();
			ipage.setParams(this.setIpageParams("mfCusPersonDoor", mfCusPersonDoor));
			mfCusPersonDoorList = (List<MfCusPersonDoor>)mfCusPersonDoorFeign.findByPage(ipage).getResult();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		model.addAttribute("formcusdoor00001", formcusdoor00001);
		model.addAttribute("mfCusPersonDoorList", mfCusPersonDoorList);
		model.addAttribute("query", "");
		return "/component/cus/MfCusPersonDoor_ListBig";
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
		MfCusPersonDoor mfCusPersonDoor = new MfCusPersonDoor();
		try {
			mfCusPersonDoor.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfCusPersonDoor.setCriteriaList(mfCusPersonDoor, ajaxData);//我的筛选
			//this.getRoleConditions(mfCusPersonDoor,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfCusPersonDoor", mfCusPersonDoor));
			ipage = mfCusPersonDoorFeign.findByPage(ipage);
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
	public Map<String, Object> insertAjax(String ajaxData,String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		FormData 	formcusdoor00001 = formService.getFormData("cusdoor00001");
			getFormValue(formcusdoor00001, getMapByJson(ajaxData));
			if(this.validateFormData(formcusdoor00001)){
		MfCusPersonDoor mfCusPersonDoor = new MfCusPersonDoor();
				setObjValue(formcusdoor00001, mfCusPersonDoor);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
				String cusNo = mfCusPersonDoor.getCusNo();
				mfCusCustomer.setCusNo(cusNo);
				mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
				String cusName = mfCusCustomer.getCusName();
				mfCusPersonDoor.setCusName(cusName);
				mfCusPersonDoorFeign.insert(mfCusPersonDoor);
				getTableData(cusNo,tableId,dataMap);
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
		FormData formcusdoor00002 = formService.getFormData("cusdoor00002");
		getFormValue(formcusdoor00002, getMapByJson(ajaxData));
		MfCusPersonDoor mfCusPersonDoorJsp = new MfCusPersonDoor();
		setObjValue(formcusdoor00002, mfCusPersonDoorJsp);
		MfCusPersonDoor mfCusPersonDoor = mfCusPersonDoorFeign.getById(mfCusPersonDoorJsp);
		if(mfCusPersonDoor!=null){
			try{
				mfCusPersonDoor = (MfCusPersonDoor)EntityUtil.reflectionSetVal(mfCusPersonDoor, mfCusPersonDoorJsp, getMapByJson(ajaxData));
				mfCusPersonDoorFeign.update(mfCusPersonDoor);
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
	public Map<String, Object> updateAjax(String ajaxData,String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusPersonDoor mfCusPersonDoor = new MfCusPersonDoor();
		try{
		FormData 	formcusdoor00001 = formService.getFormData("cusdoor00001");
			getFormValue(formcusdoor00001, getMapByJson(ajaxData));
			if(this.validateFormData(formcusdoor00001)){
				setObjValue(formcusdoor00001, mfCusPersonDoor);
				mfCusPersonDoorFeign.update(mfCusPersonDoor);
				String cusNo = mfCusPersonDoor.getCusNo();
				getTableData(cusNo,tableId,dataMap);
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
	public Map<String, Object> getByIdAjax(String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formcusdoor00002 = formService.getFormData("cusdoor00002");
		MfCusPersonDoor mfCusPersonDoor = new MfCusPersonDoor();
		mfCusPersonDoor.setId(id);
		mfCusPersonDoor = mfCusPersonDoorFeign.getById(mfCusPersonDoor);
		getObjValue(formcusdoor00002, mfCusPersonDoor,formData);
		if(mfCusPersonDoor!=null){
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
	public Map<String, Object> deleteAjax(String id,String ajaxData,String tableId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfCusPersonDoor mfCusPersonDoor = new MfCusPersonDoor();
		mfCusPersonDoor.setId(id);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			mfCusPersonDoor = (MfCusPersonDoor)JSONObject.toBean(jb, MfCusPersonDoor.class);
			mfCusPersonDoorFeign.delete(mfCusPersonDoor);
			String cusNo = mfCusPersonDoor.getCusNo();
			getTableData(cusNo, tableId,dataMap);
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
		FormData formcusdoor00002 = formService.getFormData("cusdoor00002");
		model.addAttribute("formcusdoor00002", formcusdoor00002);
		model.addAttribute("query", "");
		return "/component/cus/MfCusPersonDoor_Insert";
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
		FormData  formcusdoor00002 = formService.getFormData("cusdoor00002");
		 getFormValue(formcusdoor00002);
		MfCusPersonDoor  mfCusPersonDoor = new MfCusPersonDoor();
		 setObjValue(formcusdoor00002, mfCusPersonDoor);
		 mfCusPersonDoorFeign.insert(mfCusPersonDoor);
		 getObjValue(formcusdoor00002, mfCusPersonDoor);
		 this.addActionMessage(model, "保存成功");
		 Ipage ipage = this.getIpage();
		 List<MfCusPersonDoor> mfCusPersonDoorList = (List<MfCusPersonDoor>)mfCusPersonDoorFeign.findByPage(this.getIpage()).getResult();
		model.addAttribute("formcusdoor00002", formcusdoor00002);
		model.addAttribute("mfCusPersonDoorList", mfCusPersonDoorList);
		model.addAttribute("query", "");
		return "/component/cus/MfCusPersonDoor_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String id) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formcusdoor00001 = formService.getFormData("cusdoor00001");
		 getFormValue(formcusdoor00001);
		MfCusPersonDoor  mfCusPersonDoor = new MfCusPersonDoor();
		mfCusPersonDoor.setId(id);
		 mfCusPersonDoor = mfCusPersonDoorFeign.getById(mfCusPersonDoor);
		 getObjValue(formcusdoor00001, mfCusPersonDoor);
		model.addAttribute("formcusdoor00001", formcusdoor00001);
		model.addAttribute("query", "");
		return "/component/cus/MfCusPersonDoor_Detail";
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String id) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		MfCusPersonDoor mfCusPersonDoor = new MfCusPersonDoor();
		mfCusPersonDoor.setId(id);
		mfCusPersonDoorFeign.delete(mfCusPersonDoor);
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
		FormData  formcusdoor00002 = formService.getFormData("cusdoor00002");
		 getFormValue(formcusdoor00002);
		 boolean validateFlag = this.validateFormData(formcusdoor00002);
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
		FormData  formcusdoor00002 = formService.getFormData("cusdoor00002");
		 getFormValue(formcusdoor00002);
		 boolean validateFlag = this.validateFormData(formcusdoor00002);
	}
	private void getTableData(String cusNo,String tableId,Map<String,Object>dataMap) throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		MfCusPersonDoor mfCusPersonDoor = new MfCusPersonDoor();
		mfCusPersonDoor.setCusNo(cusNo);
		Ipage ipage = this.getIpage();
		ipage.setParams(this.setIpageParams("mfCusPersonDoor", mfCusPersonDoor));
		String  tableHtml = jtu.getJsonStr(tableId,"tableTag", (List<MfCusShareholder>)mfCusPersonDoorFeign.findByPage(ipage).getResult(), null,true);
		dataMap.put("tableData",tableHtml);
	}

	
}
