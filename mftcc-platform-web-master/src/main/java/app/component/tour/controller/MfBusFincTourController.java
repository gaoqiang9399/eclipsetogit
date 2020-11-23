package  app.component.tour.controller;
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

import app.base.User;
import app.component.cus.entity.MfCusCustomer;
import app.component.cus.feign.MfCusCustomerFeign;
import app.component.tour.entity.MfBusFincTour;
import app.component.tour.entity.MfBusTour;
import app.component.tour.feign.MfBusFincTourFeign;
import app.component.tour.feign.MfBusTourFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.util.DateUtil;
import net.sf.json.JSONObject;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Title: MfBusFincAppChildAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Sat May 26 13:09:22 CST 2018
 **/

@Controller
@RequestMapping("/mfbusFincTour")
public class MfBusFincTourController extends BaseFormBean{
	@Autowired
	private MfBusFincTourFeign mfBusFincTourFeign;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired 
	private MfBusTourFeign mfBusTourFeign;
	@Autowired
	private MfCusCustomerFeign mfCusCustomerFeign;
	
	
	
	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListPage")
	public String getListPage() throws Exception {
		ActionContext.initialize(request, response);
		return "/component/tour/MfBusFincTour_List";
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
	public Map<String, Object> findByPageAjax(String ajaxData, Integer pageNo, Integer pageSize, String tableId,
			String tableType) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfBusFincTour mfBusFincTour = new MfBusFincTour();
		try {
			mfBusFincTour.setCustomQuery(ajaxData);// 自定义查询参数赋值
			mfBusFincTour.setCriteriaList(mfBusFincTour, ajaxData);// 我的筛选
			mfBusFincTour.setCustomSorts(ajaxData);// 自定义排序
			Ipage ipage = this.getIpage();
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			ipage.setPageSize(pageSize);// 异步传页面翻页尺寸
			ipage.setParams(this.setIpageParams("mfBusFincTour", mfBusFincTour));
			ipage = mfBusFincTourFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
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
	public String input(Model model,String fincChildId) throws Exception {
		ActionContext.initialize(request, response);
		//根据子借据号查询客户号  然后根据客户好查询客户类型
		MfBusFincTour mfBusFincTour = new MfBusFincTour();
		mfBusFincTour.setFincChildId(fincChildId);
		mfBusFincTour = mfBusFincTourFeign.getById(mfBusFincTour);
		MfCusCustomer mfCusCustomer = new MfCusCustomer();
		mfCusCustomer.setCusNo(mfBusFincTour.getCusNo());
		mfCusCustomer = mfCusCustomerFeign.getById(mfCusCustomer);
		String cusBaseType = mfCusCustomer.getCusBaseType();
		String cusName = mfCusCustomer.getCusName();
		String contactsName = mfCusCustomer.getContactsName();
		FormService formService = new FormService();
		FormData formmfbustour = formService.getFormData("mfbustourbase");
		MfBusTour mfBusTour = new MfBusTour();
		mfBusTour.setOpNo(User.getRegNo(request));
		mfBusTour.setFincChildId(fincChildId);
		mfBusTour.setOpName(User.getRegName(request));
		mfBusTour.setTourTime(DateUtil.getShowDateTime(DateUtil.getDateTime()));
		mfBusTour.setCusName(cusName);
		mfBusTour.setCusBaseType(cusBaseType);
		mfBusTour.setContactsName(contactsName);
		mfBusTour.setPactAmt(mfBusFincTour.getPactAmt());
		mfBusTour.setLoanBal(mfBusFincTour.getLoanBal());
		mfBusTour.setKindName(mfBusFincTour.getKindName());
		mfBusTour.setPactBeginDate(DateUtil.getShowDateTime(mfBusFincTour.getPactBeginDate()));
		mfBusTour.setPactEndDate(DateUtil.getShowDateTime(mfBusFincTour.getPactEndDate()));
		mfBusTour.setTermMonth(mfBusFincTour.getTermMonth());
		getObjValue(formmfbustour, mfBusTour);
		
		//每次点击新增的时候都要去查询一下库 如果有历史记录展示 如果没有显示暂无数据
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Ipage ipage = this.getIpage();
		//ipage.setPageNo(pageNo);// 异步传页面翻页参数
		//ipage.setPageSize(pageSize);// 异步传页面翻页尺寸
		ipage.setParams(this.setIpageParams("mfBusTour", mfBusTour));
		ipage = mfBusTourFeign.queryTourContext(ipage);
		//JsonTableUtil jtu = new JsonTableUtil();
		//String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
		//ipage.setResult(tableHtml);
		Gson  gson = new Gson();
		String json = gson.toJson(ipage.getResult());
		List<MfBusTour> mfBusTourList = gson.fromJson(json, new TypeToken<List<MfBusTour>>() {}.getType());
		//dataMap.put("lsm", lsm);
		model.addAttribute("formmfbustour", formmfbustour);
		model.addAttribute("mfBusTour",mfBusTour);
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("mfBusTourList",mfBusTourList);
		model.addAttribute("query", "");
		return "/component/tour/MfBusTour_Insert";
	}
	
	
}
