package app.component.tour.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonFormUtil;
import com.core.struts.taglib.JsonTableUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import app.base.User;
import app.component.common.BizPubParm;
import app.component.common.EntityUtil;
import app.component.cus.custracing.entity.MfCusTrack;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.entity.MfCusFamilyInfo;
import app.component.cus.entity.MfCusFormConfig;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.cus.feign.MfCusFamilyInfoFeign;
import app.component.cus.feign.MfCusFormConfigFeign;
import app.component.cus.institutions.entity.MfBusInstitutions;
import app.component.tour.entity.MfBusTour;
import app.component.tour.feign.MfBusTourFeign;
import app.tech.oscache.CodeUtils;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Controller
@RequestMapping("/mfBusTour")
public class MfBusTourController extends BaseFormBean {
	@Autowired
	private MfBusTourFeign mfBusTourFeign;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	@Autowired
	private MfCusFamilyInfoFeign mfCusFamilyInfoFeign;
	@Autowired
	private MfCusFormConfigFeign mfCusFormConfigFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage(Model model, String fincChildId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request,
				response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formmfbustourbase = formService.getFormData("mfbustourbase");
		MfBusTour mfBusTour = new MfBusTour();
		mfBusTour.setFincChildId(fincChildId);
		mfBusTour.setTourId(WaterIdUtil.getWaterId());
		JSONArray trackTypeArray = mfBusTourFeign.getTrackTypeArray();
		dataMap.put("trackTypeArray", trackTypeArray);
		List<MfBusTour> mfBusTourList = mfBusTourFeign.getList(mfBusTour);
//		for(MfBusTour m:mfBusTourList ){
//			m.setTourTime(DateUtil.getShowDateTime(m.getTourTime()));
//			List<MfBusTour> mfBusTourCommentList = m.getCommentList();
//			for(MfBusTour mffBusTourComment:mfBusTourCommentList){
//				mffBusTourComment.setTourTime(DateUtil.getShowDateTime(mffBusTourComment.getTourTime()));
//			}
//		}
		String newDate = DateUtil.getDate("yyyy-MM-dd"); 
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("fincChildId", fincChildId);
		model.addAttribute("newDate", newDate);
		model.addAttribute("mfBusTourList", mfBusTourList);
		model.addAttribute("formmfbustourbase", formmfbustourbase);
		model.addAttribute("query", "");
		return "/component/tour/MfBusTour_List";

	}
	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Model model,String ajaxData, Integer pageNo, Integer pageSize, String tableId,
			String tableType,String fincChildId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusTour mfBusTour = new MfBusTour();
		try {
			mfBusTour.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfBusTour.setCriteriaList(mfBusTour, ajaxData);// 我的筛选
			mfBusTour.setCustomSorts(ajaxData);// 自定义排序
			mfBusTour.setFincChildId(fincChildId);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);// 异步传页面翻页尺寸
			ipage.setParams(this.setIpageParams("mfBusTour", mfBusTour));
			ipage = mfBusTourFeign.findByPage(ipage);
			//JsonTableUtil jtu = new JsonTableUtil();
			//String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			//ipage.setResult(tableHtml);
			Gson  gson = new Gson();
			String json = gson.toJson(ipage.getResult());
			List<MfBusTour> lsm = gson.fromJson(json, new TypeToken<List<MfBusTour>>() {}.getType());
			dataMap.put("lsm", lsm);
			dataMap.put("query", "");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData,String cusName,String kindName) throws Exception {
		ActionContext.initialize(request, response);
		FormService formService = new FormService();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formmfbustourbase = formService.getFormData("mfbustourbase");
			getFormValue(formmfbustourbase, getMapByJson(ajaxData));
			if (this.validateFormData(formmfbustourbase)) {
				MfBusTour mfBusTour = new MfBusTour();
				setObjValue(formmfbustourbase, mfBusTour);
				mfBusTour.setTourTime(DateUtil.getShowDateTime(DateUtil.getDateTime()));
				mfBusTour.setTourId(WaterIdUtil.getWaterId());
				mfBusTourFeign.insert(mfBusTour);
				//mfBusInstitutions = mfBusInstitutionsFeign.submitProcess(mfBusInstitutions);
				Map<String, String> paramMap = new HashMap<String, String>();
//				paramMap.put("userRole", mfBusTour.getApproveNodeName());
//				paramMap.put("opNo", mfBusTour.getApprovePartName());
				dataMap.put("dataMap", mfBusTour);
				dataMap.put("query", "");
				dataMap.put("msg", MessageEnum.SUCCEED.getMessage(paramMap));
				dataMap.put("flag", "success");
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
	
	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/queryRecord")
	@ResponseBody
	public Map<String, Object> queryRecord(Model model,String ajaxData, Integer pageNo, Integer pageSize, String tableId,
			String tableType,String fincChildId) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusTour mfBusTour = new MfBusTour();
		try {
			mfBusTour.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfBusTour.setCriteriaList(mfBusTour, ajaxData);// 我的筛选
			mfBusTour.setCustomSorts(ajaxData);// 自定义排序
			mfBusTour.setFincChildId(fincChildId);
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);// 异步传页面翻页尺寸
			ipage.setParams(this.setIpageParams("mfBusTour", mfBusTour));
			ipage = mfBusTourFeign.queryTourContext(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
		}
		return dataMap;
	}
			
}