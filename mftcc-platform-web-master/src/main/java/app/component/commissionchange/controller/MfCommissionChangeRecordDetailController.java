package app.component.commissionchange.controller;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.report.ExpExclUtil;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;

import app.component.commissionchange.entity.MfCommissionChangeRecord;
import app.component.commissionchange.entity.MfCommissionChangeRecordDetail;
import app.component.commissionchange.feign.MfCommissionChangeRecordDetailFeign;
import app.component.commissionchange.feign.MfCommissionChangeRecordFeign;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.cus.trenchsubsidiary.entity.MfShareProfitConfig;
import app.component.cus.trenchsubsidiary.feign.MfShareProfitConfigFeign;
import app.component.pact.entity.MfBusFincApp;
import app.util.toolkit.Ipage;

/**
 * 类名： MfCommissionChangeRecordDetailController 描述：分润记录
 * 
 * @author 仇招
 * @date 2018年9月4日 下午9:48:13
 */
@Controller
@RequestMapping("/mfCommissionChangeRecordDetail")
public class MfCommissionChangeRecordDetailController extends BaseFormBean {
	@Autowired
	private MfCommissionChangeRecordDetailFeign mfCommissionChangeRecordDetailFeign;
	@Autowired
	private MfCommissionChangeRecordFeign mfCommissionChangeRecordFeign;
	@Autowired
	private MfShareProfitConfigFeign mfShareProfitConfigFeign;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage(Model model,String cusNo,String commissionChangeId) throws Exception {
		ActionContext.initialize(request, response);
		MfCommissionChangeRecord mfCommissionChangeRecord = new MfCommissionChangeRecord();
		mfCommissionChangeRecord.setCommissionChangeId(commissionChangeId);
		mfCommissionChangeRecord = mfCommissionChangeRecordFeign.getById(mfCommissionChangeRecord);
		String showName = "客户名称";
		String tableId = "tableCommissionChangeRecordDetailList";
		String calcBase = BizPubParm.MATCH_TYPE_1;
		if(mfCommissionChangeRecord != null){
			if(!BizPubParm.MATCH_TYPE_1.equals(mfCommissionChangeRecord.getCalcBase())){
				showName = "客户名称/项目名称";
				tableId = "tableCommissionChangeRecordDetailList_Finc";
				calcBase = mfCommissionChangeRecord.getCalcBase();
			}
		}
		model.addAttribute("calcBase", calcBase);
		model.addAttribute("commissionChangeId", commissionChangeId);
		model.addAttribute("showName", showName);
		model.addAttribute("tableId", tableId);
		model.addAttribute("cusNo", cusNo);
		return "/component/commissionchange/MfCommissionChangeRecordDetail_List";
	}
	/**
	 * @方法描述： 查询分润记录根据客户号
	 * @param model
	 * @param cusNo
	 * @return
	 * @throws Exception
	 * String
	 * @author 仇招
	 * @date 2018年9月17日 下午9:56:21
	 */
	@RequestMapping("/getListPageByCusNo")
	public String getListPageByCusNo(Model model,String cusNo) throws Exception {
		ActionContext.initialize(request, response);
		MfShareProfitConfig mfShareProfitConfig = new MfShareProfitConfig();
		mfShareProfitConfig.setCusNo(cusNo);
		mfShareProfitConfig = mfShareProfitConfigFeign.getById(mfShareProfitConfig);
		String showName = "客户名称";
		String tableId = "tableCommissionChangeRecordDetailList";
		String calcBase = BizPubParm.MATCH_TYPE_1;
		if(mfShareProfitConfig != null){
			if(!BizPubParm.MATCH_TYPE_1.equals(mfShareProfitConfig.getCalcBase())){
				showName = "客户名称/项目名称";
				tableId = "tableCommissionChangeRecordDetailList_Finc";
				calcBase = mfShareProfitConfig.getCalcBase();
			}
		}
		model.addAttribute("showName", showName);
		model.addAttribute("tableId", tableId);
		model.addAttribute("cusNo", cusNo);
		model.addAttribute("calcBase", calcBase);
		return "/component/commissionchange/MfCommissionChangeRecordDetail_List";
	}

	/***
	 * 列表数据查询
	 * @param pageNo 
	 * @param pageSize 
	 * @param tableId 
	 * @param tableType 
	 * @param ajaxData 
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType, String ajaxData,String commissionChangeId,String cusNo,String calcBase) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCommissionChangeRecordDetail mfCommissionChangeRecordDetail = new MfCommissionChangeRecordDetail();
		try {
			mfCommissionChangeRecordDetail.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfCommissionChangeRecordDetail.setCriteriaList(mfCommissionChangeRecordDetail, ajaxData);// 我的筛选
			mfCommissionChangeRecordDetail.setCommissionChangeId(commissionChangeId);
			mfCommissionChangeRecordDetail.setTrenchUid(cusNo);
			mfCommissionChangeRecordDetail.setCustomSorts(ajaxData);// 自定义排序
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);// 
			ipage.setParams(this.setIpageParams("mfCommissionChangeRecordDetail",mfCommissionChangeRecordDetail));
			ipage.getParams().put("calcBase", calcBase);
			// 自定义查询Bo方法
			ipage = mfCommissionChangeRecordDetailFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	@RequestMapping(value = "/findByPageAjaxExcel")
	@ResponseBody
	public void findByPageAjaxExcel(String tableId,String commissionChangeId,String cusNo,String calcBase) throws Exception{
		//读取出文件的所有字节,并将所有字节写出给客户端
		ActionContext.initialize(request, response);
		MfCommissionChangeRecordDetail mfCommissionChangeRecordDetail = new MfCommissionChangeRecordDetail();
		mfCommissionChangeRecordDetail.setTrenchUid(cusNo);
		mfCommissionChangeRecordDetail.setCommissionChangeId(commissionChangeId);
		mfCommissionChangeRecordDetail.setCalcBase(calcBase);
		try {
			List<MfCommissionChangeRecordDetail> mfCommissionChangeRecordDetailList = mfCommissionChangeRecordDetailFeign.findByPageAjaxExcel(mfCommissionChangeRecordDetail);
			ExpExclUtil eu = new ExpExclUtil();
			HSSFWorkbook wb = eu.expExclTableForList(tableId, mfCommissionChangeRecordDetailList,null,false,"");
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/x-download; charset=utf-8");
			response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("shareProfit.xls", "UTF-8"));
			OutputStream stream = response.getOutputStream();  
			wb.write(stream);  
			wb.close();// HSSFWorkbook关闭
			stream.flush();  
			stream.close(); 
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	/**
	 * AJAX新增
	 * @param ajaxData 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formCommissionChangeRecordDetailBase = formService.getFormData("CommissionChangeRecordDetailBase");
			getFormValue(formCommissionChangeRecordDetailBase, getMapByJson(ajaxData));
			if (this.validateFormData(formCommissionChangeRecordDetailBase)) {
				MfCommissionChangeRecordDetail mfCommissionChangeRecordDetail = new MfCommissionChangeRecordDetail();
				setObjValue(formCommissionChangeRecordDetailBase, mfCommissionChangeRecordDetail);
				mfCommissionChangeRecordDetailFeign.insert(mfCommissionChangeRecordDetail);
				dataMap.put("flag", "success");
				dataMap.put("msg", "新增成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "新增失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * ajax 异步 单个字段或多个字段更新
	 * @param ajaxData 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formCommissionChangeRecordDetailBase = formService.getFormData("CommissionChangeRecordDetailBase");
		getFormValue(formCommissionChangeRecordDetailBase, getMapByJson(ajaxData));
		MfCommissionChangeRecordDetail mfCommissionChangeRecordDetailJsp = new MfCommissionChangeRecordDetail();
		setObjValue(formCommissionChangeRecordDetailBase, mfCommissionChangeRecordDetailJsp);
		MfCommissionChangeRecordDetail mfCommissionChangeRecordDetail = mfCommissionChangeRecordDetailFeign.getById(mfCommissionChangeRecordDetailJsp);
		if (mfCommissionChangeRecordDetail != null) {
			try {
				mfCommissionChangeRecordDetail = (MfCommissionChangeRecordDetail) EntityUtil.reflectionSetVal(
						mfCommissionChangeRecordDetail, mfCommissionChangeRecordDetailJsp, getMapByJson(ajaxData));
				mfCommissionChangeRecordDetailFeign.update(mfCommissionChangeRecordDetail);
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} catch (Exception e) {
				dataMap.put("flag", "error");
				dataMap.put("msg", "新增失败");
				throw e;
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", "编号不存在,保存失败");
		}
		return dataMap;
	}

	/**
	 * AJAX更新保存
	 * @param ajaxData 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCommissionChangeRecordDetail mfCommissionChangeRecordDetail = new MfCommissionChangeRecordDetail();
		try {
			FormData formCommissionChangeRecordDetailBase = formService.getFormData("CommissionChangeRecordDetailBase");
			getFormValue(formCommissionChangeRecordDetailBase, getMapByJson(ajaxData));
			if (this.validateFormData(formCommissionChangeRecordDetailBase)) {
				mfCommissionChangeRecordDetail = new MfCommissionChangeRecordDetail();
				setObjValue(formCommissionChangeRecordDetailBase, mfCommissionChangeRecordDetail);
				mfCommissionChangeRecordDetailFeign.update(mfCommissionChangeRecordDetail);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX获取查看
	 * @param detailId 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String detailId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formCommissionChangeRecordDetailBase = formService.getFormData("CommissionChangeRecordDetailBase");
		MfCommissionChangeRecordDetail mfCommissionChangeRecordDetail = new MfCommissionChangeRecordDetail();
		mfCommissionChangeRecordDetail.setDetailId(detailId);
		mfCommissionChangeRecordDetail = mfCommissionChangeRecordDetailFeign.getById(mfCommissionChangeRecordDetail);
		getObjValue(formCommissionChangeRecordDetailBase, mfCommissionChangeRecordDetail, formData);
		if (mfCommissionChangeRecordDetail != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}

	/**
	 * Ajax异步删除
	 * @param detailId 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String detailId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfCommissionChangeRecordDetail mfCommissionChangeRecordDetail = new MfCommissionChangeRecordDetail();
		mfCommissionChangeRecordDetail.setDetailId(detailId);
		try {
			mfCommissionChangeRecordDetailFeign.delete(mfCommissionChangeRecordDetail);
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
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/input")
	public String input(Model model) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formCommissionChangeRecordDetailBase = formService.getFormData("CommissionChangeRecordDetailBase");
		model.addAttribute("formCommissionChangeRecordDetailBase", formCommissionChangeRecordDetailBase);
		model.addAttribute("query", "");
		return "/component/commissionchange/MfCommissionChangeRecordDetail_Insert";
	}

	/**
	 * 查询
	 * @param detailId 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	public String getById(Model model,String detailId) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formCommissionChangeRecordDetailDetail = formService.getFormData("CommissionChangeRecordDetailDetail");
		getFormValue(formCommissionChangeRecordDetailDetail);
		MfCommissionChangeRecordDetail mfCommissionChangeRecordDetail = new MfCommissionChangeRecordDetail();
		mfCommissionChangeRecordDetail.setDetailId(detailId);
		mfCommissionChangeRecordDetail = mfCommissionChangeRecordDetailFeign.getById(mfCommissionChangeRecordDetail);
		getObjValue(formCommissionChangeRecordDetailDetail, mfCommissionChangeRecordDetail);
		model.addAttribute("formCommissionChangeRecordDetailDetail", formCommissionChangeRecordDetailDetail);
		model.addAttribute("query", "");
		return "/component/commissionchange/MfCommissionChangeRecordDetail_Detail";
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void validateInsert() throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formCommissionChangeRecordDetailBase = formService.getFormData("CommissionChangeRecordDetailBase");
		getFormValue(formCommissionChangeRecordDetailBase);
		boolean validateFlag = this.validateFormData(formCommissionChangeRecordDetailBase);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public void validateUpdate() throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		FormData formCommissionChangeRecordDetailBase = formService.getFormData("CommissionChangeRecordDetailBase");
		getFormValue(formCommissionChangeRecordDetailBase);
		boolean validateFlag = this.validateFormData(formCommissionChangeRecordDetailBase);
	}
}
