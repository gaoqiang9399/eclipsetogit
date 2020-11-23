package app.component.report.controller;

import app.base.User;
import app.component.query.entity.MfQueryItem;
import app.component.query.feign.MfQueryItemFeign;
import app.component.report.entity.MfReportFilter;
import app.component.report.feign.MfReportFilterFeign;
import app.component.sys.entity.SysUser;
import app.component.sysInterface.SysInterfaceFeign;
import app.tech.oscache.CodeUtils;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.PropertiesUtil;
import cn.mftcc.util.WaterIdUtil;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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


@Controller
@RequestMapping("/mfReportFilter")
public class MfReportFilterController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private MfReportFilterFeign mfReportFilterFeign;
	// 台账查询的基础报表数据
	// 统计报表的基础报表数据
	// 分析报表列表数据
	// 监测报表列表数据
	@Autowired
	private MfQueryItemFeign mfQueryItemFeign;
	@Autowired
	private SysInterfaceFeign sysInterfaceFeign;

	@RequestMapping(value = "/getById")
	@ResponseBody
	public Map<String, Object> getById(Model model, String ajaxData, String id) throws Exception {

		ActionContext.initialize(request, response);
		MfReportFilter mfReportFilter = new MfReportFilter();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			mfReportFilter.setId(id);
			mfReportFilter = mfReportFilterFeign.getById(mfReportFilter);
			dataMap.put("query_content", mfReportFilter.getQueryContent());
			dataMap.put("query_name", mfReportFilter.getQueryName());
			dataMap.put("query_class", mfReportFilter.getQueryClass());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return dataMap;
	}

	/**
	 * 插入查询条件
	 * 
	 * @param typeClass
	 * @param filterName
	 * @param filterDesc
	 * @param filterContent
	 * @param useFlag
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	@ResponseBody
	public Map<String, Object> insert(Model model, String typeClass, String filterName, String filterDesc, String filterContent,
			String useFlag) throws Exception {

		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfReportFilter mfReportFilter = new MfReportFilter();
		try {
			String queryClass = "";
			// jdk版本太低，不支持switch(String),1.7以上才支持
			if ("1".equals(typeClass)) {
				queryClass = "cus";
			} else if ("2".equals(typeClass)) {
				queryClass = "bus";
			} else if ("3".equals(typeClass)) {
				queryClass = "finc";
			} else if ("4".equals(typeClass)) {
				queryClass = "pledge";
			}else {
			}
			String thisId = WaterIdUtil.getWaterId("Que");
			mfReportFilter.setId(thisId);
			mfReportFilter.setOpNo(User.getRegNo(request));
			mfReportFilter.setQueryName(filterName);
			mfReportFilter.setQueryDescript(filterDesc);
			mfReportFilter.setQueryContent(filterContent);
			mfReportFilter.setUseFlag(useFlag);
			mfReportFilter.setQueryClass(queryClass);
			mfReportFilter.setLstModTime(DateUtil.getDateTime());
			mfReportFilterFeign.insert(mfReportFilter);
			dataMap.put("thisId", thisId);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("query", "");
		return dataMap;
	}

	@RequestMapping(value = "/delete")
	@ResponseBody
	public Map<String, Object> delete(Model model, String ajaxData, String id) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ActionContext.initialize(request, response);
		MfReportFilter mfReportFilter = new MfReportFilter();
		try {
			mfReportFilter.setId(id);
			mfReportFilterFeign.delete(mfReportFilter);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return dataMap;
	}

	/**
	 * 专门用来更新是否显示
	 * 
	 * @param id
	 * @param useFlag
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateUseFlag")
	@ResponseBody
	public Map<String, Object> updateUseFlag(Model model, String ajaxData, String id, String useFlag) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		ActionContext.initialize(request, response);
		MfReportFilter mfReportFilter = new MfReportFilter();
		try {
			mfReportFilter.setId(id);
			mfReportFilter.setUseFlag(useFlag);
			mfReportFilterFeign.updateUseFlag(mfReportFilter);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return dataMap;
	}

	/**
	 * 弹出查询设置页面调用
	 * 
	 * @param useFlag
	 * @param funcType
	 * @param mfReportFliterList
	 * @param mfBaseItemList
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getFilter")
	public String getFilter(Model model, String ajaxData, String useFlag, String funcType) throws Exception {

		ActionContext.initialize(request, response);
		MfReportFilter mfReportFilter = new MfReportFilter();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<MfReportFilter> mfReportFliterList =null;
		List<MfQueryItem> mfBaseItemList = null;
		try {
			mfReportFilter.setUseFlag(useFlag);
			mfReportFliterList = mfReportFilterFeign.getFilter(mfReportFilter);
			MfQueryItem mfQueryItem = new MfQueryItem();
			mfQueryItem.setFuncType(funcType);
			mfQueryItem.setOpNo(User.getRegNo(request));
			dataMap = mfQueryItemFeign.getReportItem(mfQueryItem);
			JSONObject json = new JSONObject();
			JSONArray unattentionarray = JSONArray.fromObject(dataMap.get("unattention"));
			json.put("mfReportItemList", unattentionarray);
			request.setAttribute("json", json);
			// 获取台账和统计查询的报表基础数据
			Map<String, Object> reportMap = mfQueryItemFeign.getQueryItemList();
			if ("1".equals(funcType)) {
				mfBaseItemList = (List<MfQueryItem>) reportMap.get("base");
			} else {
				mfBaseItemList = (List<MfQueryItem>) reportMap.get("report");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("mfReportFliterList", mfReportFliterList);
		model.addAttribute("mfBaseItemList", mfBaseItemList);
		model.addAttribute("query", "");
		return "/component/report/MfReport_queryAdd";
	}

	/**
	 * 进入报表菜单时调用
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {

		ActionContext.initialize(request, response);
		MfReportFilter mfReportFilter = new MfReportFilter();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<MfQueryItem> mfBaseItemList;
		List<MfQueryItem> mfReportBaseItemList;
		List<MfQueryItem> analysisResultList;
		List<MfQueryItem> watchResultList;
		List<MfQueryItem> largeResultList;
		CodeUtils codeUtils = new CodeUtils();
		try {
			mfReportFilter.setUseFlag("1");// 只显示flag=1
//			List<MfReportFilter> mfReportFliterList = mfReportFilterFeign.getFilter(mfReportFilter);
//			request.setAttribute("dataList", mfReportFliterList);
			MfQueryItem mfQueryItem = new MfQueryItem();
			mfQueryItem.setOpNo(User.getRegNo(request));
			dataMap = mfQueryItemFeign.getReportItemAll(mfQueryItem);
			JSONObject json = new JSONObject();
			// 台账和统计报表总的列表
			JSONArray arrayTatol = JSONArray.fromObject(dataMap.get("tatol"));
			json.put("mfReportItemListTatol", arrayTatol);
			request.setAttribute("json", json);
			// 获取台账和统计查询的报表基础数据
			Map<String, Object> reportMap = mfQueryItemFeign.getQueryItemList();
			mfBaseItemList = (List<MfQueryItem>) reportMap.get("base");
			mfReportBaseItemList = (List<MfQueryItem>) reportMap.get("report");
			analysisResultList = (List<MfQueryItem>) reportMap.get("analysis");
			watchResultList = (List<MfQueryItem>) reportMap.get("watch");
			largeResultList = (List<MfQueryItem>) reportMap.get("large");
			SysUser sysUser = sysInterfaceFeign.getUserNoBySysUser(mfQueryItem.getOpNo());
			request.setAttribute("sysUser", sysUser);
			String currDate = DateUtil.getDate();
			String yesterday = DateUtil.addByDay(-1);
			String currMonth = currDate.substring(0, 6);
			String lastMonth = DateUtil.getLastMonth(currMonth);
			String reduceThirtyOne = DateUtil.addByDay(-31);
			dataMap.put("currDate", currDate);
			dataMap.put("yesterday", yesterday);
			dataMap.put("currMonth", currMonth);
			dataMap.put("lastMonth", lastMonth);
			dataMap.put("reduceThirtyOne", reduceThirtyOne);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		model.addAttribute("dataMap", dataMap);
		model.addAttribute("mfBaseItemList", mfBaseItemList);
		model.addAttribute("mfReportBaseItemList", mfReportBaseItemList);
		model.addAttribute("analysisResultList", analysisResultList);
		model.addAttribute("watchResultList", watchResultList);
		model.addAttribute("largeResultList", largeResultList);
		model.addAttribute("reportUrl", PropertiesUtil.getWebServiceProperty("reportURL"));
		model.addAttribute("reportProjectFlag", codeUtils.getSingleValByKey("REPORT_PROJECT_FLAG"));
		return "/component/report/MfReportEntrance";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getListPageAjax")
	@ResponseBody
	public Map<String, Object> getListPageAjax(String ajaxData, String funcType) throws Exception {

		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MfReportFilter mfReportFilter = new MfReportFilter();
		try {
			mfReportFilter.setUseFlag("1");// 只显示flag=1
			MfQueryItem mfQueryItem = new MfQueryItem();
			mfQueryItem.setFuncType(funcType);
			mfQueryItem.setOpNo(User.getRegNo(request));
			dataMap = mfQueryItemFeign.getReportItem(mfQueryItem);
			dataMap.put("flag", "success");
			dataMap.put("mfReportFliterList", "mfReportFliterList");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", "更新失败");
			throw e;
		}
		return dataMap;
	}

}
