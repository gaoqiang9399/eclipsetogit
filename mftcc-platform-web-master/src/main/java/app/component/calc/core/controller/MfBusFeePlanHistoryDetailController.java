package app.component.calc.core.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.component.common.BizPubParm;
import app.component.pact.entity.MfBusPact;
import app.component.pact.feign.MfBusPactFeign;
import cn.mftcc.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;

import app.component.calc.core.entity.MfBusFeePlanHistoryDetail;
import app.component.calc.core.feign.MfBusFeePlanHistoryDetailFeign;
import app.util.toolkit.Ipage;
@Controller
@RequestMapping(value = "/mfBusFeePlanHistoryDetail")
public class MfBusFeePlanHistoryDetailController extends BaseFormBean{
	private static final long serialVersionUID = 9196454891700023438L;
	//注入MfBusAppPenaltyChildBo
	@Autowired
	private MfBusFeePlanHistoryDetailFeign mfBusFeePlanHistoryDetailFeign;
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfBusPactFeign mfBusPactFeign;
	/**
	 * 列表打开页面请求
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request,
				response);
		return "/component/calc/penalty/MfBusAppPenaltyChild_List";
	}
	/***
	 * 列表数据查询
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/findByPageAjax")
	public Map<String, Object> findByPageAjax(String ajaxData,int pageNo,String tableId,String tableType) throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		MfBusFeePlanHistoryDetail mfBusFeePlanHistoryDetail = new MfBusFeePlanHistoryDetail();
		try {
			mfBusFeePlanHistoryDetail.setCustomQuery(ajaxData);//自定义查询参数赋值
			mfBusFeePlanHistoryDetail.setCriteriaList(mfBusFeePlanHistoryDetail, ajaxData);//我的筛选
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);//异步传页面翻页参数
			//自定义查询Bo方法
			ipage = mfBusFeePlanHistoryDetailFeign.findByPage(ipage, mfBusFeePlanHistoryDetail);
			JsonTableUtil jtu = new JsonTableUtil();
			String  tableHtml = jtu.getJsonStr(tableId,tableType,(List)ipage.getResult(), ipage,true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage",ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}
	@ResponseBody
	@RequestMapping(value = "/getMfRepayFeeHistoryListAjax")
	public Map<String, Object> getMfRepayFeeHistoryListAjax(String pactId,String fincId,String tableId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusPact mfBusPact = new MfBusPact();
		mfBusPact.setPactId(pactId);
		mfBusPact = mfBusPactFeign.getById(mfBusPact);
		if(mfBusPact != null){
			String busModel = mfBusPact.getBusModel();
			if(StringUtil.isNotEmpty(busModel) && BizPubParm.BUS_MODEL_12.equals(busModel)){
				tableId = "tableMfBusFeePlanHistoryDetail0001_GCDB";
			}
		}
		//收费历史
		String  tableHtml = "";
		MfBusFeePlanHistoryDetail mfBusFeePlanHistoryDetail = new MfBusFeePlanHistoryDetail();
		mfBusFeePlanHistoryDetail.setPactId(pactId);
		List<MfBusFeePlanHistoryDetail> mfBusFeePlanHistoryDetailList = mfBusFeePlanHistoryDetailFeign.findList(mfBusFeePlanHistoryDetail);
		if(mfBusFeePlanHistoryDetailList.size()>0){
			JsonTableUtil jtu = new JsonTableUtil();
			tableHtml = jtu.getJsonStr(tableId, "tableTag", mfBusFeePlanHistoryDetailList, null ,true);
		}
		dataMap.put("htmlStr", tableHtml);
		return dataMap;
	}
	
	/**
	 * AJAX新增
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/insertAjax")
	public Map<String, Object> insertAjax() throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		return dataMap;
	}
	/**
	 * ajax 异步 单个字段或多个字段更新
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updateAjaxByOne")
	public Map<String, Object> updateAjaxByOne() throws Exception{
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		return dataMap;
	}
	/**
	 * AJAX更新保存
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updateAjax")
	public Map<String, Object> updateAjax() throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		return dataMap;
	}
	
	/**
	 * AJAX获取查看
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getByIdAjax")
	public Map<String, Object> getByIdAjax() throws Exception {
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		Map<String,Object> formData = new HashMap<String,Object>();
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
	public Map<String, Object> deleteAjax() throws Exception{
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>(); 
		return dataMap;
	}
	/**
	 * 新增页面
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception{
		ActionContext.initialize(request,response);
		return "/component/calc/penalty/MfBusAppPenaltyChild_Insert";
	}
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getById")
	public String getById(Model model) throws Exception{
		ActionContext.initialize(request,response);
		return "/component/calc/penalty/MfBusAppPenaltyChild_Detail";
	}
	
}
