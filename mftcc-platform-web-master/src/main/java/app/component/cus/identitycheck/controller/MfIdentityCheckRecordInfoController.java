package  app.component.cus.identitycheck.controller;
import app.component.common.EntityUtil;
import app.component.cus.identitycheck.entity.MfIdentityCheckRecordInfo;
import app.component.cus.identitycheck.feign.MfIdentityCheckRecordInfoFeign;
import app.util.toolkit.Ipage;
import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: MfIdentityCheckRecordInfoAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed Jan 24 15:19:26 CST 2018
 **/
@Controller
@RequestMapping("/mfIdentityCheckRecordInfo")
public class MfIdentityCheckRecordInfoController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	//注入MfIdentityCheckRecordInfoBo
	@Autowired
	private MfIdentityCheckRecordInfoFeign mfIdentityCheckRecordInfoFeign;
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
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		model.addAttribute("query", "");
		return "/component/cus/identitycheck/MfIdentityCheckRecordInfo_List";
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
		MfIdentityCheckRecordInfo mfIdentityCheckRecordInfo = new MfIdentityCheckRecordInfo();
		try {
			mfIdentityCheckRecordInfo.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfIdentityCheckRecordInfo.setCriteriaList(mfIdentityCheckRecordInfo, ajaxData);//我的筛选
			//mfIdentityCheckRecordInfo.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfIdentityCheckRecordInfo,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage.setParams(this.setIpageParams("mfIdentityCheckRecordInfo", mfIdentityCheckRecordInfo));
			ipage = mfIdentityCheckRecordInfoFeign.findByPage(ipage);
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
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		FormData 	formidentitycheck0002 = formService.getFormData("identitycheck0002");
			getFormValue(formidentitycheck0002, getMapByJson(ajaxData));
			if(this.validateFormData(formidentitycheck0002)){
		MfIdentityCheckRecordInfo mfIdentityCheckRecordInfo = new MfIdentityCheckRecordInfo();
				setObjValue(formidentitycheck0002, mfIdentityCheckRecordInfo);
				mfIdentityCheckRecordInfoFeign.insert(mfIdentityCheckRecordInfo);
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
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formidentitycheck0002 = formService.getFormData("identitycheck0002");
		getFormValue(formidentitycheck0002, getMapByJson(ajaxData));
		MfIdentityCheckRecordInfo mfIdentityCheckRecordInfoJsp = new MfIdentityCheckRecordInfo();
		setObjValue(formidentitycheck0002, mfIdentityCheckRecordInfoJsp);
		MfIdentityCheckRecordInfo mfIdentityCheckRecordInfo = mfIdentityCheckRecordInfoFeign.getById(mfIdentityCheckRecordInfoJsp);
		if(mfIdentityCheckRecordInfo!=null){
			try{
				mfIdentityCheckRecordInfo = (MfIdentityCheckRecordInfo)EntityUtil.reflectionSetVal(mfIdentityCheckRecordInfo, mfIdentityCheckRecordInfoJsp, getMapByJson(ajaxData));
				mfIdentityCheckRecordInfoFeign.update(mfIdentityCheckRecordInfo);
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
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
		FormData 	formidentitycheck0002 = formService.getFormData("identitycheck0002");
			getFormValue(formidentitycheck0002, getMapByJson(ajaxData));
			if(this.validateFormData(formidentitycheck0002)){
		MfIdentityCheckRecordInfo mfIdentityCheckRecordInfo = new MfIdentityCheckRecordInfo();
				setObjValue(formidentitycheck0002, mfIdentityCheckRecordInfo);
				mfIdentityCheckRecordInfoFeign.update(mfIdentityCheckRecordInfo);
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
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}
	
	/**
	 * AJAX获取查看
	 * @param checkId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData, String checkId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formidentitycheck0002 = formService.getFormData("identitycheck0002");
		MfIdentityCheckRecordInfo mfIdentityCheckRecordInfo = new MfIdentityCheckRecordInfo();
		mfIdentityCheckRecordInfo.setCheckId(checkId);
		mfIdentityCheckRecordInfo = mfIdentityCheckRecordInfoFeign.getById(mfIdentityCheckRecordInfo);
		getObjValue(formidentitycheck0002, mfIdentityCheckRecordInfo,formData);
		if(mfIdentityCheckRecordInfo!=null){
			dataMap.put("flag", "success");
		}else{
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}
	/**
	 * Ajax异步删除
	 * @param checkId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String checkId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfIdentityCheckRecordInfo mfIdentityCheckRecordInfo = new MfIdentityCheckRecordInfo();
		mfIdentityCheckRecordInfo.setCheckId(checkId);
		try {
			mfIdentityCheckRecordInfoFeign.delete(mfIdentityCheckRecordInfo);
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
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData formidentitycheck0002 = formService.getFormData("identitycheck0002");
		model.addAttribute("formidentitycheck0002", formidentitycheck0002);
		model.addAttribute("query", "");
		return "/component/cus/identitycheck/MfIdentityCheckRecordInfo_Insert";
	}
	/**
	 * 查询
	 * @param checkId 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String ajaxData, String checkId) throws Exception{
		FormService formService = new FormService();
		ActionContext.initialize(request,response);
		FormData  formidentitycheck0001 = formService.getFormData("identitycheck0001");
		 getFormValue(formidentitycheck0001);
		MfIdentityCheckRecordInfo  mfIdentityCheckRecordInfo = new MfIdentityCheckRecordInfo();
		mfIdentityCheckRecordInfo.setCheckId(checkId);
		 mfIdentityCheckRecordInfo = mfIdentityCheckRecordInfoFeign.getById(mfIdentityCheckRecordInfo);
		 getObjValue(formidentitycheck0001, mfIdentityCheckRecordInfo);
		model.addAttribute("formidentitycheck0001", formidentitycheck0001);
		model.addAttribute("query", "");
		return "/component/cus/identitycheck/MfIdentityCheckRecordInfo_Detail";
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
		FormData  formidentitycheck0002 = formService.getFormData("identitycheck0002");
		 getFormValue(formidentitycheck0002);
		 boolean validateFlag = this.validateFormData(formidentitycheck0002);
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
		FormData  formidentitycheck0002 = formService.getFormData("identitycheck0002");
		 getFormValue(formidentitycheck0002);
		 boolean validateFlag = this.validateFormData(formidentitycheck0002);
	}
	/**
	 * 
	 * 方法描述： 身份核查
	 * @return
	 * @throws Exception
	 * String
	 * @author 沈浩兵
	 * @param cusName 
	 * @param idNum 
	 * @date 2018-1-24 下午3:20:23
	 */
	@RequestMapping(value = "/doIdentityCheckAjax")
	@ResponseBody
	public Map<String, Object> doIdentityCheckAjax(String ajaxData, String cusName, String idNum) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			MfIdentityCheckRecordInfo mfIdentityCheckRecordInfo = new MfIdentityCheckRecordInfo();
			mfIdentityCheckRecordInfo.setCusName(cusName);
			mfIdentityCheckRecordInfo.setIdNum(idNum);
			dataMap = mfIdentityCheckRecordInfoFeign.doIdentityCheck(mfIdentityCheckRecordInfo);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", "身份核查失败");
			throw new Exception(e.getMessage());
		}
		return dataMap;
	}

}
