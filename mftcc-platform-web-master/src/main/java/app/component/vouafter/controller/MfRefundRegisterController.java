package app.component.vouafter.controller;

import app.component.common.EntityUtil;
import app.component.vouafter.entity.MfRefundRegister;
import app.component.vouafter.feign.MfRefundRegisterFeign;
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
 * Title: MfRefundRegisterController.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Tue Jul 07 19:03:05 CST 2020
 **/
@Controller
@RequestMapping(value = "/mfRefundRegister")
public class MfRefundRegisterController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfRefundRegisterFeign mfRefundRegisterFeign;

	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/vouafter/MfRefundRegister_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/findByPageAjax")
	public Map<String, Object> findByPageAjax(String ajaxData, String tableId, String tableType, Integer pageNo,Integer pageSize) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfRefundRegister mfRefundRegister = new MfRefundRegister();
		try {
			mfRefundRegister.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfRefundRegister.setCriteriaList(mfRefundRegister, ajaxData);//我的筛选
			mfRefundRegister.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfRefundRegister,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setParams(this.setIpageParams("mfRefundRegister", mfRefundRegister));
			//自定义查询Feign方法
			ipage = mfRefundRegisterFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getConfirmListPage")
	public String getConfirmListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/vouafter/MfRefundRegister_ConfirmList";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/findConfirmByPageAjax")
	public Map<String, Object> findConfirmByPageAjax(String ajaxData, String tableId, String tableType, Integer pageNo,Integer pageSize) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfRefundRegister mfRefundRegister = new MfRefundRegister();
		try {
			mfRefundRegister.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfRefundRegister.setCriteriaList(mfRefundRegister, ajaxData);//我的筛选
			mfRefundRegister.setCustomSorts(ajaxData);//自定义排序
			mfRefundRegister.setQueryType("1");//查询状态为1和2的
			//this.getRoleConditions(mfRefundRegister,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setParams(this.setIpageParams("mfRefundRegister", mfRefundRegister));
			//自定义查询Feign方法
			ipage = mfRefundRegisterFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
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
	@RequestMapping("/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData,String from) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = getMapByJson(ajaxData);
		try {
			FormData formRefundRegisterbase = formService .getFormData((String)paramMap.get("formId"));
			getFormValue(formRefundRegisterbase, paramMap);
			if (this.validateFormData(formRefundRegisterbase)) {
				MfRefundRegister mfRefundRegister = new MfRefundRegister();
				String showName = "退费登记";
				if("confirm".equals(from)){
					mfRefundRegister.setRefundSts("2");
					showName = "退费确认";
				}else{
					mfRefundRegister.setRefundSts("1");
				}
				setObjValue(formRefundRegisterbase, mfRefundRegister);
				mfRefundRegisterFeign.update(mfRefundRegister);
				dataMap.put("flag", "success");
				dataMap.put("msg", showName + "成功！");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
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
	@RequestMapping("/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formvouafterbase = formService.getFormData("vouafterbase");
		getFormValue(formvouafterbase, getMapByJson(ajaxData));
		MfRefundRegister mfRefundRegisterJsp = new MfRefundRegister();
		setObjValue(formvouafterbase, mfRefundRegisterJsp);
		MfRefundRegister mfRefundRegister = mfRefundRegisterFeign.getById(mfRefundRegisterJsp);
		if(mfRefundRegister!=null){
			try{
				mfRefundRegister = (MfRefundRegister)EntityUtil.reflectionSetVal(mfRefundRegister, mfRefundRegisterJsp, getMapByJson(ajaxData));
				mfRefundRegisterFeign.update(mfRefundRegister);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			}catch(Exception e){
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
	@RequestMapping("/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfRefundRegister mfRefundRegister = new MfRefundRegister();
		try{
			FormData formvouafterbase = formService.getFormData("vouafterbase");
			getFormValue(formvouafterbase, getMapByJson(ajaxData));
			if(this.validateFormData(formvouafterbase)){
				mfRefundRegister = new MfRefundRegister();
				setObjValue(formvouafterbase, mfRefundRegister);
				mfRefundRegisterFeign.update(mfRefundRegister);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX获取查看
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String id) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formvouafterbase = formService.getFormData("vouafterbase");
		MfRefundRegister mfRefundRegister = new MfRefundRegister();
		mfRefundRegister.setId(id);
		mfRefundRegister = mfRefundRegisterFeign.getById(mfRefundRegister);
		getObjValue(formvouafterbase, mfRefundRegister,formData);
		if(mfRefundRegister!=null){
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
	@RequestMapping("/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String id) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfRefundRegister mfRefundRegister = new MfRefundRegister();
		mfRefundRegister.setId(id);
		try {
			mfRefundRegisterFeign.delete(mfRefundRegister);
			dataMap.put("flag", "success");
			dataMap.put("msg", "成功");
		} catch (Exception e) {
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
	@RequestMapping("/input")
	public String input(Model model,String id,String from) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		MfRefundRegister mfRefundRegister = new MfRefundRegister();
		mfRefundRegister.setId(id);
		mfRefundRegister = mfRefundRegisterFeign.getById(mfRefundRegister);
		String formId = "RefundRegisterbase";
		if("confirm".equals(from)){
			formId = "RefundRegisterbase_Confirm";
		}
		mfRefundRegister.setConfirmRefundAmt(mfRefundRegister.getActualRefundAmt());
		FormData formRefundRegisterbase = formService.getFormData(formId);
		getObjValue(formRefundRegisterbase, mfRefundRegister);
		model.addAttribute("formRefundRegisterbase", formRefundRegisterbase);
		model.addAttribute("id", id);
		model.addAttribute("refundId", mfRefundRegister.getRefundId());
		model.addAttribute("query", "");
		model.addAttribute("mfRefundRegister", mfRefundRegister);
		model.addAttribute("from", from);
		model.addAttribute("refundQueryFile", "query");
		String nodeNo = "refundManage";
		model.addAttribute("nodeNo", nodeNo);
		model.addAttribute("relNo", mfRefundRegister.getRefundId());
		String temParm1 = "cusNo=" + mfRefundRegister.getCusNo() +
				"&appId=" + mfRefundRegister.getAppId() + "&pactId=" +
				mfRefundRegister.getPactId() + "&refundId=" + mfRefundRegister.getRefundId() + "&nodeNo=refundManage";
		model.addAttribute("temParm1", temParm1);
		return "/component/vouafter/MfRefundRegister_Insert";
	}

	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model,String id,String from) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		String formId = "RefundRegisterdetail";
		if("confirm".equals(from)){
			formId = "RefundRegisterdetail_Confirm";
		}
		FormData formRefundRegisterdetail = formService.getFormData(formId);
		getFormValue(formRefundRegisterdetail);
		MfRefundRegister mfRefundRegister = new MfRefundRegister();
		mfRefundRegister.setId(id);
		mfRefundRegister = mfRefundRegisterFeign.getById(mfRefundRegister);
		getObjValue(formRefundRegisterdetail, mfRefundRegister);
		model.addAttribute("formRefundRegisterdetail", formRefundRegisterdetail);
		model.addAttribute("refundId", mfRefundRegister.getRefundId());
		model.addAttribute("relNo", mfRefundRegister.getRefundId());
		model.addAttribute("query", "query");
		model.addAttribute("refundQueryFile", "query");
		model.addAttribute("from", from);
		model.addAttribute("nodeNo", "refundManage");
		model.addAttribute("mfRefundRegister", mfRefundRegister);
		String temParm1 = "cusNo=" + mfRefundRegister.getCusNo() +
				"&appId=" + mfRefundRegister.getAppId() + "&pactId=" +
				mfRefundRegister.getPactId() + "&refundId=" + mfRefundRegister.getRefundId() + "&nodeNo=refundManage";
		model.addAttribute("temParm1", temParm1);
		return "/component/vouafter/MfRefundRegister_Detail";
	}

	/**
	 * 新增校验
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void validateInsert() throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formvouafterbase = formService.getFormData("vouafterbase");
		getFormValue(formvouafterbase);
		boolean validateFlag = this.validateFormData(formvouafterbase);
	}
	/**
	 * 修改校验
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void validateUpdate() throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formvouafterbase = formService.getFormData("vouafterbase");
		getFormValue(formvouafterbase);
		boolean validateFlag = this.validateFormData(formvouafterbase);
	}
}
