package app.component.thirdservice.esign.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.component.collateral.feign.MfBusCollateralDetailRelFeign;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.pact.entity.MfBusFollowPact;
import app.component.pact.entity.MfBusPact;
import app.component.pact.feign.MfBusPactFeign;
import app.component.thirdservice.esign.feign.MfEsignHistoryFeign;
import app.component.thirdservice.esign.entity.MfEsignHistory;
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
 * Title: MfEsignHistoryController.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Tue Mar 26 21:20:10 CST 2019
 **/
@Controller
@RequestMapping(value = "/mfEsignHistory")
public class MfEsignHistoryController extends BaseFormBean{
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfEsignHistoryFeign mfEsignHistoryFeign;
	@Autowired
	private MfBusPactFeign mfBusPactFeign;
	@Autowired
	private MfBusCollateralDetailRelFeign mfBusCollateralDetailRelFeign;

	
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model,String cusNo,String appId) throws Exception {
		ActionContext.initialize(request, response);
        model.addAttribute("cusNo",cusNo);
        model.addAttribute("appId",appId);
		return "/component/thirdservice/esign/MfEsignHistory_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/findByPageAjax")
	public Map<String, Object> findByPageAjax(String ajaxData, String tableId, String tableType, Integer pageNo,Integer pageSize,String cusNo,String appId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfEsignHistory mfEsignHistory = new MfEsignHistory();
		try {
			mfEsignHistory.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfEsignHistory.setCriteriaList(mfEsignHistory, ajaxData);//我的筛选
            mfEsignHistory.setCusNo(cusNo);
            mfEsignHistory.setAppId(appId);
			//mfEsignHistory.setCustomSorts(ajaxData);//自定义排序
			//this.getRoleConditions(mfEsignHistory,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			ipage.setPageSize(pageSize);
			ipage.setParams(this.setIpageParams("mfEsignHistory", mfEsignHistory));
			//自定义查询Feign方法
			ipage = mfEsignHistoryFeign.findByPage(ipage);
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
			FormData formEsignHistoryAdd = formService.getFormData("EsignHistoryAdd");
			getFormValue(formEsignHistoryAdd, getMapByJson(ajaxData));
			if(this.validateFormData(formEsignHistoryAdd)){
				MfEsignHistory mfEsignHistory = new MfEsignHistory();
				setObjValue(formEsignHistoryAdd, mfEsignHistory);
				mfEsignHistoryFeign.insert(mfEsignHistory);
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
		FormData formEsignHistoryAdd = formService.getFormData("EsignHistoryAdd");
		getFormValue(formEsignHistoryAdd, getMapByJson(ajaxData));
		MfEsignHistory mfEsignHistoryJsp = new MfEsignHistory();
		setObjValue(formEsignHistoryAdd, mfEsignHistoryJsp);
		MfEsignHistory mfEsignHistory = mfEsignHistoryFeign.getById(mfEsignHistoryJsp);
		if(mfEsignHistory!=null){
			try{
				mfEsignHistory = (MfEsignHistory)EntityUtil.reflectionSetVal(mfEsignHistory, mfEsignHistoryJsp, getMapByJson(ajaxData));
				mfEsignHistoryFeign.update(mfEsignHistory);
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
		MfEsignHistory mfEsignHistory = new MfEsignHistory();
		try{
			FormData formEsignHistoryAdd = formService.getFormData("EsignHistoryAdd");
			getFormValue(formEsignHistoryAdd, getMapByJson(ajaxData));
			if(this.validateFormData(formEsignHistoryAdd)){
				mfEsignHistory = new MfEsignHistory();
				setObjValue(formEsignHistoryAdd, mfEsignHistory);
				mfEsignHistoryFeign.update(mfEsignHistory);
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
		FormData formEsignHistoryAdd = formService.getFormData("EsignHistoryAdd");
		MfEsignHistory mfEsignHistory = new MfEsignHistory();
		mfEsignHistory.setId(id);
		mfEsignHistory = mfEsignHistoryFeign.getById(mfEsignHistory);
		getObjValue(formEsignHistoryAdd, mfEsignHistory,formData);
		if(mfEsignHistory!=null){
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
		MfEsignHistory mfEsignHistory = new MfEsignHistory();
		mfEsignHistory.setId(id);
		try {
			mfEsignHistoryFeign.delete(mfEsignHistory);
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
		FormData formEsignHistoryAdd = formService.getFormData("EsignHistoryAdd");
		model.addAttribute("formEsignHistoryAdd", formEsignHistoryAdd);
		model.addAttribute("query", "");
		return "/component/thirdservice/esign/MfEsignHistory_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model,String id) throws Exception{
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formEsignHistoryDetail = formService.getFormData("EsignHistoryDetail");
		getFormValue(formEsignHistoryDetail);
		MfEsignHistory mfEsignHistory = new MfEsignHistory();
		mfEsignHistory.setId(id);
		mfEsignHistory = mfEsignHistoryFeign.getById(mfEsignHistory);
		String esignType = mfEsignHistory.getEsignType();
        List<MfBusFollowPact> mfBusFollowPactList = null;
        if(BizPubParm.ESIGN_TYPE_3.equals(esignType)){
            // 获取从合同列表
            MfBusPact mfBusPact = new MfBusPact();
            mfBusPact.setPactId(mfEsignHistory.getPactId());
            mfBusPact = mfBusPactFeign.getById(mfBusPact);
            mfBusFollowPactList = mfBusCollateralDetailRelFeign.getMfBusFollowPactList(mfBusPact.getAppId(), mfBusPact.getPactAmt());
            if (mfBusFollowPactList.size() == 0) {
                mfBusFollowPactList = null;
            }
        }
		getObjValue(formEsignHistoryDetail, mfEsignHistory);
		model.addAttribute("formEsignHistoryDetail", formEsignHistoryDetail);
		model.addAttribute("query", "");
		model.addAttribute("mfEsignHistory", mfEsignHistory);
		model.addAttribute("mfBusFollowPactList", mfBusFollowPactList);
		return "/component/thirdservice/esign/MfEsignHistory_Detail";
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
		FormData formEsignHistoryAdd = formService.getFormData("EsignHistoryAdd");
		getFormValue(formEsignHistoryAdd);
		boolean validateFlag = this.validateFormData(formEsignHistoryAdd);
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
		FormData formEsignHistoryAdd = formService.getFormData("EsignHistoryAdd");
		getFormValue(formEsignHistoryAdd);
		boolean validateFlag = this.validateFormData(formEsignHistoryAdd);
	}
}
