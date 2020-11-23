package app.component.vouafter.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import app.component.common.EntityUtil;
import app.component.vouafter.feign.MfVouAfterTrackHisFeign;
import app.component.vouafter.entity.MfVouAfterTrackHis;
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

/**
 * Title: MfVouAfterTrackHisController.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Tue Jul 07 19:04:25 CST 2020
 **/
@Controller
@RequestMapping(value = "/mfVouAfterTrackHis")
public class MfVouAfterTrackHisController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfVouAfterTrackHisFeign mfVouAfterTrackHisFeign;

	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/vouafter/MfVouAfterTrackHis_List";
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
		MfVouAfterTrackHis mfVouAfterTrackHis = new MfVouAfterTrackHis();
		try {
			mfVouAfterTrackHis.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfVouAfterTrackHis.setCriteriaList(mfVouAfterTrackHis, ajaxData);//我的筛选
			//mfVouAfterTrackHis.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfVouAfterTrackHis,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setParams(this.setIpageParams("mfVouAfterTrackHis", mfVouAfterTrackHis));
			//自定义查询Feign方法
			ipage = mfVouAfterTrackHisFeign.findByPage(ipage);
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
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		try{
			FormData formvouafterhisbase = formService.getFormData("vouafterhisbase");
			getFormValue(formvouafterhisbase, getMapByJson(ajaxData));
			if(this.validateFormData(formvouafterhisbase)){
				MfVouAfterTrackHis mfVouAfterTrackHis = new MfVouAfterTrackHis();
				setObjValue(formvouafterhisbase, mfVouAfterTrackHis);
				mfVouAfterTrackHisFeign.insert(mfVouAfterTrackHis);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			}else{
				dataMap.put("flag", "error");
				dataMap.put("msg",this.getFormulavaliErrorMsg());
			}
		}catch(Exception e){
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
	@RequestMapping("/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formvouafterhisbase = formService.getFormData("vouafterhisbase");
		getFormValue(formvouafterhisbase, getMapByJson(ajaxData));
		MfVouAfterTrackHis mfVouAfterTrackHisJsp = new MfVouAfterTrackHis();
		setObjValue(formvouafterhisbase, mfVouAfterTrackHisJsp);
		MfVouAfterTrackHis mfVouAfterTrackHis = mfVouAfterTrackHisFeign.getById(mfVouAfterTrackHisJsp);
		if(mfVouAfterTrackHis!=null){
			try{
				mfVouAfterTrackHis = (MfVouAfterTrackHis)EntityUtil.reflectionSetVal(mfVouAfterTrackHis, mfVouAfterTrackHisJsp, getMapByJson(ajaxData));
				mfVouAfterTrackHisFeign.update(mfVouAfterTrackHis);
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
		MfVouAfterTrackHis mfVouAfterTrackHis = new MfVouAfterTrackHis();
		try{
			FormData formvouafterhisbase = formService.getFormData("vouafterhisbase");
			getFormValue(formvouafterhisbase, getMapByJson(ajaxData));
			if(this.validateFormData(formvouafterhisbase)){
				mfVouAfterTrackHis = new MfVouAfterTrackHis();
				setObjValue(formvouafterhisbase, mfVouAfterTrackHis);
				mfVouAfterTrackHisFeign.update(mfVouAfterTrackHis);
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
	public Map<String, Object> getByIdAjax(String hisId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String,Object> formData = new HashMap<String,Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		FormData formvouafterhisbase = formService.getFormData("vouafterhisbase");
		MfVouAfterTrackHis mfVouAfterTrackHis = new MfVouAfterTrackHis();
		mfVouAfterTrackHis.setHisId(hisId);
		mfVouAfterTrackHis = mfVouAfterTrackHisFeign.getById(mfVouAfterTrackHis);
		getObjValue(formvouafterhisbase, mfVouAfterTrackHis,formData);
		if(mfVouAfterTrackHis!=null){
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
	public Map<String, Object> deleteAjax(String hisId) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfVouAfterTrackHis mfVouAfterTrackHis = new MfVouAfterTrackHis();
		mfVouAfterTrackHis.setHisId(hisId);
		try {
			mfVouAfterTrackHisFeign.delete(mfVouAfterTrackHis);
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
	public String input(Model model) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formvouafterhisbase = formService.getFormData("vouafterhisbase");
		model.addAttribute("formvouafterhisbase", formvouafterhisbase);
		model.addAttribute("query", "");
		return "/component/vouafter/MfVouAfterTrackHis_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model,String hisId) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formvouafterhisdetail = formService.getFormData("vouafterhisdetail");
		getFormValue(formvouafterhisdetail);
		MfVouAfterTrackHis mfVouAfterTrackHis = new MfVouAfterTrackHis();
		mfVouAfterTrackHis.setHisId(hisId);
		mfVouAfterTrackHis = mfVouAfterTrackHisFeign.getById(mfVouAfterTrackHis);
		getObjValue(formvouafterhisdetail, mfVouAfterTrackHis);
		model.addAttribute("formvouafterhisdetail", formvouafterhisdetail);
		model.addAttribute("query", "");
		return "/component/vouafter/MfVouAfterTrackHis_Detail";
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
		FormData formvouafterhisbase = formService.getFormData("vouafterhisbase");
		getFormValue(formvouafterhisbase);
		boolean validateFlag = this.validateFormData(formvouafterhisbase);
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
		FormData formvouafterhisbase = formService.getFormData("vouafterhisbase");
		getFormValue(formvouafterhisbase);
		boolean validateFlag = this.validateFormData(formvouafterhisbase);
	}
}
