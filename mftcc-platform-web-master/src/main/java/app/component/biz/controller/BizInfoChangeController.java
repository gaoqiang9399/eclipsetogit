package app.component.biz.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

import app.component.biz.entity.BizInfoChange;
import app.component.biz.entity.BizInfoChangeSub;
import app.component.biz.feign.BizInfoChangeFeign;
import app.component.biz.feign.BizInfoChangeSubFeign;
import cn.mftcc.common.MessageEnum;
import net.sf.json.JSONObject;

/**
 * Title: BizInfoChangeAction.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Wed Apr 20 06:08:57 GMT 2016
 **/
@Controller
@RequestMapping("/bizInfoChange")
public class BizInfoChangeController extends BaseFormBean {
	// 注入业务层
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private BizInfoChangeFeign bizInfoChangeFeign;
	@Autowired
	private BizInfoChangeSubFeign bizInfoChangeSubFeign;

	/**
	 * 获取列表数据
	 * 
	 * @return
	 * @throws Exception
	 */
	private void getTableData(Map<String, Object> dataMap, String tableId, BizInfoChange bizInfoChange)
			throws Exception {
		JsonTableUtil jtu = new JsonTableUtil();
		String tableHtml = jtu.getJsonStr(tableId, "tableTag", bizInfoChangeFeign.getAll(bizInfoChange), null, true);
		dataMap.put("tableData", tableHtml);
	}

	/**
	 * 列表有翻页(承租人)
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPageForRenter")
	public String getListPageForRenter(Model model, String relNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		BizInfoChange bizInfoChange = new BizInfoChange();
		// relNo = "cusNo-"+relNo;
		bizInfoChange.setRelNo(relNo);
		List<BizInfoChange> bizInfoChangeList = bizInfoChangeFeign.getAll(bizInfoChange);
		this.getBizInfo(bizInfoChangeList);
		model.addAttribute("bizInfoChangeList", bizInfoChangeList);
		return "/component/biz/BizInfoChange_List";
	}

	/**
	 * 列表有翻页(经销商)
	 * @param relNo 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPageForProxy")
	public String getListPageForProxy(Model model, String ajaxData, String relNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		BizInfoChange bizInfoChange = new BizInfoChange();
		relNo = "proxyNo-" + relNo;
		bizInfoChange.setRelNo(relNo);
		List<BizInfoChange> bizInfoChangeList = bizInfoChangeFeign.getAll(bizInfoChange);
		this.getBizInfo(bizInfoChangeList);
		model.addAttribute("bizInfoChangeList", bizInfoChangeList);
		return "/component/biz/BizInfoChange_List";
	}

	/**
	 * 列表有翻页(供货商)
	 * @param relNo 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPageForSupplier")
	public String getListPageForSupplier(Model model, String ajaxData, String relNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		BizInfoChange bizInfoChange = new BizInfoChange();
		relNo = "supplierNo-" + relNo;
		bizInfoChange.setRelNo(relNo);
		List<BizInfoChange> bizInfoChangeList = bizInfoChangeFeign.getAll(bizInfoChange);
		this.getBizInfo(bizInfoChangeList);
		model.addAttribute("bizInfoChangeList", bizInfoChangeList);
		return "/component/biz/BizInfoChange_List";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getBizInfo")
	public void getBizInfo(List<BizInfoChange> bizInfoChangeList) {
		// 将年、月、日、分别放进一个map
		TreeMap<String, String> yearMap = new TreeMap<String, String>(new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				Integer a = Integer.parseInt((String) o1);
				Integer b = Integer.parseInt((String) o2);
				return b - a;
			}
		});
		TreeMap<String, String> monthMap = new TreeMap<String, String>(new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				Integer a = Integer.parseInt((String) o1);
				Integer b = Integer.parseInt((String) o2);
				return b - a;
			}
		});
		TreeMap<String, String> dayMap = new TreeMap<String, String>(new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				Integer a = Integer.parseInt((String) o1);
				Integer b = Integer.parseInt((String) o2);
				return b - a;
			}
		});
		for (BizInfoChange change : bizInfoChangeList) {
			String dateTmp = change.getDate().replaceAll("-", "");
			String year = dateTmp.substring(0, 4);
			String month = dateTmp.substring(0, 6);
			String day = dateTmp;
			String desc = change.getCont();
			if (!yearMap.containsKey(year)) {
				yearMap.put(year, year);
			}
			if (!monthMap.containsKey(month)) {
				monthMap.put(month, month);
			}
			if (!dayMap.containsKey(day)) {
				dayMap.put(day,
						change.getChangeNo() + "@" + desc + "@" + change.getChangeType() + "@" + change.getTime());
			} else {
				dayMap.put(day, dayMap.get(day) + ";" + change.getChangeNo() + "@" + desc + "@" + change.getChangeType()
						+ "@" + change.getTime());
			}
		}
		// 循环map 将日放进月的子集 将月放进年的子集
		List<BizInfoChange> yearList = new ArrayList<BizInfoChange>();
		List<BizInfoChange> monthList = null;
		List<BizInfoChange> dayList = null;
		String dateStr = "";
		for (String yearKey : yearMap.keySet()) {
			BizInfoChange yearChange = new BizInfoChange();
			yearChange.setDate(yearKey);
			dateStr += yearKey + "@";
			monthList = new ArrayList<BizInfoChange>();
			for (String monthkey : monthMap.keySet()) {
				if (monthkey.substring(0, 4).equals(yearKey)) {
					BizInfoChange monthChange = new BizInfoChange();
					monthChange.setDate(monthkey);
					dateStr += monthkey + "@";
					dayList = new ArrayList<BizInfoChange>();
					for (String dayKey : dayMap.keySet()) {
						if (dayKey.subSequence(0, 6).equals(monthkey)) {
							String[] values = dayMap.get(dayKey).split(";");
							for (int i = 0; i < values.length; i++) {
								BizInfoChange dayChange = new BizInfoChange();
								if (i == 0) {
									dayChange.setDate(dayKey);
								}
								dayChange.setChangeNo(values[i].split("@")[0]);
								dayChange.setCont(values[i].split("@")[1]);
								dayChange.setChangeType(values[i].split("@")[2]);
								dayChange.setTime(values[i].split("@")[3]);
								dayList.add(dayChange);
							}
						}
					}
					monthChange.setCont(dayList.get(0).getDate() + "@" + dayList.get(0).getCont() + "@"
							+ dayList.get(0).getChangeNo() + "@" + dayList.get(0).getChangeType() + "@"
							+ dayList.get(0).getTime());
					dayList.remove(0);
					monthChange.setSubList(dayList);
					monthList.add(monthChange);
				}
			}
			yearChange.setSubList(monthList);
			yearList.add(yearChange);
		}
		if (!"".equals(dateStr)) {
			dateStr = dateStr.substring(0, dateStr.lastIndexOf("@"));
		}
		this.getHttpRequest().setAttribute("dateStr", dateStr);
		this.getHttpRequest().setAttribute("yearList", yearList);
		if (null != bizInfoChangeList && bizInfoChangeList.size() != 0) {
			BizInfoChangeSub bizInfoChangeSub = new BizInfoChangeSub();
			bizInfoChangeSub.setChangeNo(bizInfoChangeList.get(0).getChangeNo());
			List<BizInfoChangeSub> bizInfoChangeSubList = bizInfoChangeSubFeign.getAll(bizInfoChangeSub);
			this.getHttpRequest().setAttribute("bizInfoChangeSubList", bizInfoChangeSubList);
		}
	}

	/**
	 * 列表全部无翻页
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListAll")
	public String getListAll(Model model, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formbiz0002 = formService.getFormData("biz0002");
		BizInfoChange bizInfoChange = new BizInfoChange();
		List<BizInfoChange> bizInfoChangeList = bizInfoChangeFeign.getAll(bizInfoChange);
		model.addAttribute("formbiz0002", formbiz0002);
		model.addAttribute("bizInfoChangeList", bizInfoChangeList);
		model.addAttribute("query", "");
		return "/component/biz/BizInfoChange_List";
	}

	/**
	 * AJAX新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData, String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formbiz0002 = formService.getFormData("biz0002");
			getFormValue(formbiz0002, getMapByJson(ajaxData));
			if (this.validateFormData(formbiz0002)) {
				BizInfoChange bizInfoChange = new BizInfoChange();
				setObjValue(formbiz0002, bizInfoChange);
				bizInfoChangeFeign.insert(bizInfoChange);
				getTableData(dataMap, tableId, bizInfoChange);// 获取列表
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_INSERT.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX更新保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData, String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		BizInfoChange bizInfoChange = new BizInfoChange();
		try {
			FormData formbiz0002 = formService.getFormData("biz0002");
			getFormValue(formbiz0002, getMapByJson(ajaxData));
			if (this.validateFormData(formbiz0002)) {
				bizInfoChange = new BizInfoChange();
				setObjValue(formbiz0002, bizInfoChange);
				bizInfoChangeFeign.update(bizInfoChange);
				getTableData(dataMap, tableId, bizInfoChange);// 获取列表
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_UPDATE.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_UPDATE.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX获取查看
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String changeNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formbiz0002 = formService.getFormData("biz0002");
		BizInfoChange bizInfoChange = new BizInfoChange();
		bizInfoChange.setChangeNo(changeNo);
		bizInfoChange = bizInfoChangeFeign.getById(bizInfoChange);
		getObjValue(formbiz0002, bizInfoChange, formData);
		if (bizInfoChange != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}

	/**
	 * Ajax异步删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String ajaxData, String changeNo, String tableId) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		BizInfoChange bizInfoChange = new BizInfoChange();
		bizInfoChange.setChangeNo(changeNo);
		try {
			JSONObject jb = JSONObject.fromObject(ajaxData);
			bizInfoChange = (BizInfoChange) JSONObject.toBean(jb, BizInfoChange.class);
			bizInfoChangeFeign.delete(bizInfoChange);
			getTableData(dataMap, tableId, bizInfoChange);// 获取列表
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * 
	 * 方法描述：获取客户历史变更信息的前几条
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author zhs
	 * @date 2017-3-28 下午2:51:30
	 */
	@RequestMapping(value = "/getTopListAjax")
	@ResponseBody
	public Map<String, Object> getTopListAjax(String relNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("relNo", "cusNo-" + relNo);
			List<BizInfoChange> bizInfoChangeList = bizInfoChangeFeign.getTopList(paramMap);
			dataMap.put("bizInfoChangeList", bizInfoChangeList);
			dataMap.put("flag", "success");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR_SELECT.getMessage());
			throw e;
		}
		return dataMap;
	}

}
