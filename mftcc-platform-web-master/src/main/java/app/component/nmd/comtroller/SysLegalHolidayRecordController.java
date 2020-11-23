package app.component.nmd.comtroller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import app.base.User;
import app.component.common.EntityUtil;
import app.component.nmd.entity.SysLegalHoliday;
import app.component.nmd.entity.SysLegalHolidayEvent;
import app.component.nmd.entity.SysLegalHolidayJsonObject;
import app.component.nmd.entity.SysLegalHolidayRecord;
import app.component.nmd.feign.SysLegalHolidayRecordFeign;
import app.component.nmd.util.HolidayException;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;

/**
 * Title: SysLegalHolidayRecordAction.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu May 11 17:06:31 CST 2017
 **/
@Controller
@RequestMapping("/sysLegalHolidayRecord")
public class SysLegalHolidayRecordController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private SysLegalHolidayRecordFeign sysLegalHolidayRecordFeign;

	/**
	 * 列表打开页面请求
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListPage")
	public String getListPage(Model model) throws Exception {
		ActionContext.initialize(request, response);
		model.addAttribute("query", "");
		return "component/nmd/SysLegalHolidayRecord_List";
	}

	/***
	 * 列表数据查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPageAjax")
	@ResponseBody
	public Map<String, Object> findByPageAjax(Integer pageNo, Integer pageSize, String tableId, String tableType,
			String ajaxData) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		SysLegalHolidayRecord sysLegalHolidayRecord = new SysLegalHolidayRecord();
		sysLegalHolidayRecord.setHolidayYear("2017");

		try {
			sysLegalHolidayRecord.setCustomQuery(ajaxData);// 自定义查询参数赋值
			// sysLegalHolidayRecord.setCustomSorts(ajaxData);//自定义排序参数赋值
			// sysLegalHolidayRecord.setCriteriaList(sysLegalHolidayRecord, ajaxData);//我的筛选
			// this.getRoleConditions(sysLegalHolidayRecord,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage.setParams(this.setIpageParams("sysLegalHolidayRecord", sysLegalHolidayRecord));
			ipage = sysLegalHolidayRecordFeign.findByPage(ipage);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", e.getMessage());
			throw e;
		}
		return dataMap;
	}

	/**
	 * AJAX新增
	 * 
	 * @param holidayYear
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData, String holidayYear) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formdoliday0002 = formService.getFormData("doliday0002");
			getFormValue(formdoliday0002, getMapByJson(ajaxData));
			if (this.validateFormData(formdoliday0002)) {
				SysLegalHoliday sysLegalHoliday = new SysLegalHoliday();
				// 设置操作人员编号
				String userName = User.getRegNo(request);
				sysLegalHoliday.setEdituser(userName);
				setObjValue(formdoliday0002, sysLegalHoliday);
				sysLegalHoliday.setHolidayYear(holidayYear);
				sysLegalHoliday.setHolidayYear(sysLegalHoliday.getEndDate().substring(0, 4));
				sysLegalHolidayRecordFeign.insert(sysLegalHoliday);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.ERROR.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			if (e instanceof HolidayException) {
				dataMap.put("msg", e.getMessage());
			} else {
				// Log.error("插入节假日出错", e);
				throw e;
			}
		}

		return dataMap;

	}

	/**
	 * ajax 异步 单个字段或多个字段更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjaxByOne")
	@ResponseBody
	public Map<String, Object> updateAjaxByOne(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formdoliday0002 = formService.getFormData("doliday0002");
		getFormValue(formdoliday0002, getMapByJson(ajaxData));
		SysLegalHolidayRecord sysLegalHolidayRecordJsp = new SysLegalHolidayRecord();
		setObjValue(formdoliday0002, sysLegalHolidayRecordJsp);
		SysLegalHolidayRecord sysLegalHolidayRecord = sysLegalHolidayRecordFeign.getById(sysLegalHolidayRecordJsp);
		if (sysLegalHolidayRecord != null) {
			try {
				sysLegalHolidayRecord = (SysLegalHolidayRecord) EntityUtil.reflectionSetVal(sysLegalHolidayRecord,
						sysLegalHolidayRecordJsp, getMapByJson(ajaxData));
				dataMap.put("flag", "success");
				dataMap.put("msg", "保存成功");
			} catch (Exception e) {
				e.printStackTrace();
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
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAjax")
	@ResponseBody
	public Map<String, Object> updateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formdoliday0002 = formService.getFormData("doliday0002");
			getFormValue(formdoliday0002, getMapByJson(ajaxData));
			if (this.validateFormData(formdoliday0002)) {
				SysLegalHoliday sysLegalHoliday = new SysLegalHoliday();
				setObjValue(formdoliday0002, sysLegalHoliday);
				sysLegalHolidayRecordFeign.update(sysLegalHoliday);
				dataMap.put("flag", "success");
				dataMap.put("msg", "更新成功");
			} else {
				dataMap.put("flag", "error");
				dataMap.put("msg", this.getFormulavaliErrorMsg());
			}
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			if (e instanceof HolidayException) {
				dataMap.put("msg", e.getMessage());
			} else {
				// Log.error("更新节假日出错", e);
				throw e;
			}
			return dataMap;
		}
		return dataMap;
	}

	/**
	 * AJAX获取查看
	 * 
	 * @param endDate
	 * @param eventDate
	 * @param title
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getByIdAjax")
	@ResponseBody
	public Map<String, Object> getByIdAjax(String ajaxData, String endDate, String eventDate, Object title)
			throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		// 获取参数
		if (endDate != null && endDate.length() != 0) {
			endDate = endDate.substring(4, 15);
		} else {
			endDate = eventDate;
			endDate = endDate.substring(4, 15);
		}
		eventDate = eventDate.substring(4, 15);
		DateFormat df = new SimpleDateFormat("MMM dd yyyy", Locale.ENGLISH);
		DateFormat df2 = new SimpleDateFormat("yyyyMMdd");
		String endString = df2.format(df.parse(endDate));
		String eventDateString = df2.format(df.parse(eventDate));
		endString = DateUtil.StringToString(endString, "yyyyMMdd", "yyyy-MM-dd");
		eventDateString = DateUtil.StringToString(eventDateString, "yyyyMMdd", "yyyy-MM-dd");
		formData.put("eventDate", eventDateString);
		formData.put("endDate", endString);
		formData.put("title", title);
		dataMap.put("formData", formData);
		dataMap.put("flag", "success");
		return dataMap;
	}

	/**
	 * Ajax异步删除
	 * 
	 * @param holidayYear
	 * @param holidayName
	 * @param beginDate
	 * @param endDate
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAjax")
	@ResponseBody
	public Map<String, Object> deleteAjax(String holidayYear, String holidayName, String beginDate, String endDate)
			throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			SysLegalHoliday sysLegalHoliday = new SysLegalHoliday();
			sysLegalHoliday.setHolidayYear(holidayYear);
			sysLegalHoliday.setHolidayName(holidayName);
			sysLegalHoliday.setBeginDate(beginDate);
			sysLegalHoliday.setEndDate(endDate);
			sysLegalHolidayRecordFeign.delete(sysLegalHoliday);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED.getMessage());
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			// Log.error("删除节假日失败", e);
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
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formdoliday0002 = formService.getFormData("doliday0002");
		model.addAttribute("formdoliday0002", formdoliday0002);
		model.addAttribute("query", "");
		return "component/nmd/SysLegalHolidayRecord_Insert";
	}

	/***
	 * 新增
	 * 
	 * @param sysLegalHolidayRecord
	 * 
	 * @return
	 * @throws Exception
	 *             sysLegalHoliday
	 */
	@RequestMapping(value = "/insert")
	public String insert(SysLegalHolidayRecord sysLegalHolidayRecord, Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formdoliday0002 = formService.getFormData("doliday0002");
		getFormValue(formdoliday0002);
		SysLegalHoliday sysLegalHoliday = new SysLegalHoliday();
		setObjValue(formdoliday0002, sysLegalHoliday);
		sysLegalHolidayRecordFeign.insert(sysLegalHoliday);
		getObjValue(formdoliday0002, sysLegalHoliday);
		this.addActionMessage(model, "保存成功");
		List<SysLegalHolidayRecord> sysLegalHolidayRecordList = (List<SysLegalHolidayRecord>) sysLegalHolidayRecordFeign
				.findByPage(this.getIpage()).getResult();
		model.addAttribute("sysLegalHoliday",sysLegalHoliday );
		model.addAttribute("formdoliday0002",formdoliday0002 );
		model.addAttribute("sysLegalHolidayRecordList",sysLegalHolidayRecordList );
		model.addAttribute("query", "");
		return "component/nmd/SysLegalHolidayRecord_Insert";
	}

	/**
	 * 新增假日
	 * 
	 * 
	 */

	/**
	 * 查询
	 * 
	 * @param endDate
	 * @param holidayYear
	 * @param holidayName
	 * @param firstworkDay
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String beginDate, String endDate, String holidayYear, String holidayName,
			String firstworkDay) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formholiday0001 = formService.getFormData("holiday0001");
		getFormValue(formholiday0001);
		SysLegalHoliday slh = new SysLegalHoliday();
		slh.setBeginDate(beginDate);
		slh.setEndDate(endDate);
		slh.setHolidayYear(holidayYear);
		slh.setHolidayName(holidayName);
		slh.setFirstworkDay(firstworkDay);
		getObjValue(formholiday0001, slh);
		model.addAttribute("formholiday0001", formholiday0001);
		model.addAttribute("slh", slh);
		model.addAttribute("query", "");
		return "component/nmd/SysLegalHolidayRecord_Detail";
	}

	/**
	 * 删除
	 * 
	 * @param sysLegalHoliday
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(@RequestBody SysLegalHoliday sysLegalHoliday, @RequestParam String holidayNo, Model model)
			throws Exception {
		ActionContext.initialize(request, response);
		SysLegalHolidayRecord sysLegalHolidayRecord = new SysLegalHolidayRecord();
		sysLegalHolidayRecord.setHolidayNo(holidayNo);
		sysLegalHolidayRecordFeign.delete(sysLegalHoliday);
		model.addAttribute("sysLegalHolidayRecord", sysLegalHolidayRecord);
		model.addAttribute("sysLegalHoliday", sysLegalHoliday);
		return getListPage(model);
	}

	/**
	 * 新增校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateInsert")
	public void validateInsert(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formdoliday0002 = formService.getFormData("doliday0002");
		getFormValue(formdoliday0002);
		boolean validateFlag = this.validateFormData(formdoliday0002);
	}

	/**
	 * 修改校验
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/validateUpdate")
	public void validateUpdate(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formdoliday0002 = formService.getFormData("doliday0002");
		getFormValue(formdoliday0002);
		boolean validateFlag = this.validateFormData(formdoliday0002);
	}

	/**
	 * 跳转到日历界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/goCalendar")
	public String goCalendar(Model model) {
		model.addAttribute("query", "");
		return "component/nmd/FullCalendar_holiday_month_list";
	}

	/**
	 * 获取节假日事件返回json
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/fullCalendarmonthlistAjax")
	public Map<String, Object> fullCalendarmonthlistAjax(String ajaxData) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			// 获取所有的日历事件
			List<SysLegalHolidayEvent> eventList = sysLegalHolidayRecordFeign.getAllHolidayEvent();
			// 转换JSON
			String jsonString = new Gson().toJson(eventList);
			// 赋值
			// String event_all = jsonString;
			dataMap.put("eventAll", jsonString);
			dataMap.put("holidays", null);
		} catch (Exception e) {
			// Log.error("查询所有事件,Ajax处理错误", e);
		}
		return dataMap;
	}

	/**
	 * 处理新增操作
	 * 
	 * @param event_all
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/fullCalendarInsertAjax")
	public Map<String, Object> fullCalendarInsertAjax(String ajaxData, Object event_all) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			// 将这个Json数据转换成javaBean
			List<SysLegalHolidayJsonObject> lil = new ArrayList<SysLegalHolidayJsonObject>();
			Object fromJson = new Gson().fromJson(ajaxData, lil.getClass());
			// 创建一个Gson对象
			Gson gson = new Gson();
			// 创建一个JsonParser
			JsonParser parser = new JsonParser();
			// 通过JsonParser对象可以把json格式的字符串解析成一个JsonElement对象
			JsonElement el = parser.parse(ajaxData);
			// 把JsonElement对象转换成JsonObject
			JsonObject jsonObj = null;
			if (el.isJsonObject()) {
				jsonObj = el.getAsJsonObject();
			}
			// 把JsonElement对象转换成JsonArray
			JsonArray jsonArray = null;
			if (el.isJsonArray()) {
				jsonArray = el.getAsJsonArray();
			}
			List<SysLegalHolidayJsonObject> syslist = new ArrayList<SysLegalHolidayJsonObject>();
			// 遍历JsonArray对象
			Iterator it = jsonArray.iterator();
			while (it.hasNext()) {
				JsonElement e = (JsonElement) it.next();
				SysLegalHolidayJsonObject eventDemo = gson.fromJson(e, SysLegalHolidayJsonObject.class);
				syslist.add(eventDemo);
			}
			Map<String, String> map = new HashMap<String, String>();
			for (SysLegalHolidayJsonObject sle : syslist) {
				map.put(sle.getName(), sle.getValue());
			}
			// 对map的数据进行处理
			SysLegalHoliday sysLegalHoliday = new SysLegalHoliday();
			sysLegalHoliday.setBeginDate(map.get("eventDate"));
			sysLegalHoliday.setHolidayName(map.get("title"));
			sysLegalHoliday.setEndDate(map.get("endDate"));
			sysLegalHoliday.setEdituser(map.get("makeMan"));
			// 调用业务层代码
			sysLegalHolidayRecordFeign.insert(sysLegalHoliday);
			// 设置返回值
			dataMap.put("event_all", event_all);
			dataMap.put("flag", "success");
			dataMap.put("msg", "新增成功");
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
			if (e instanceof HolidayException) {
				dataMap.put("msg", e.getMessage());
			} else {
				// logger.error("添加节假日出错", e);
			}
		}
		return dataMap;

	}

	/**
	 * 日历界面专属的更新提交处理
	 * 
	 * @return
	 */
	@RequestMapping(value = "/fullCalendarUpdateAjax")
	@ResponseBody
	public Map<String, Object> fullCalendarUpdateAjax(String ajaxData) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			// 将这个Json数据转换成javaBean
			List<SysLegalHolidayJsonObject> lil = new ArrayList<SysLegalHolidayJsonObject>();
			// Object fromJson = new Gson().fromJson(ajaxData, lil.getClass());
			// 创建一个Gson对象
			Gson gson = new Gson();
			// 创建一个JsonParser
			JsonParser parser = new JsonParser();
			// 通过JsonParser对象可以把json格式的字符串解析成一个JsonElement对象
			JsonElement el = parser.parse(ajaxData);
			// 把JsonElement对象转换成JsonObject
			JsonObject jsonObj = null;
			if (el.isJsonObject()) {
				jsonObj = el.getAsJsonObject();
			}
			// 把JsonElement对象转换成JsonArray
			JsonArray jsonArray = null;
			if (el.isJsonArray()) {
				jsonArray = el.getAsJsonArray();
			}
			List<SysLegalHolidayJsonObject> syslist = new ArrayList<SysLegalHolidayJsonObject>();
			// 遍历JsonArray对象
			Iterator it = jsonArray.iterator();
			while (it.hasNext()) {
				JsonElement e = (JsonElement) it.next();
				SysLegalHolidayJsonObject eventDemo = gson.fromJson(e, SysLegalHolidayJsonObject.class);
				syslist.add(eventDemo);
			}
			Map<String, String> map = new HashMap<String, String>();
			for (SysLegalHolidayJsonObject sle : syslist) {
				map.put(sle.getName(), sle.getValue());
			}
			// 对map的数据进行处理
			SysLegalHoliday sysLegalHoliday = new SysLegalHoliday();
			sysLegalHoliday.setBeginDate(map.get("eventDate"));
			sysLegalHoliday.setHolidayName(map.get("title"));
			sysLegalHoliday.setEndDate(map.get("endDate"));
			sysLegalHoliday.setEdituser(map.get("makeMan"));

			// 调用bo层的更新操作
			sysLegalHolidayRecordFeign.update(sysLegalHoliday);
			// 页面回复
			dataMap.put("flag", "success");
			dataMap.put("msg", "更新成功");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
		}
		return dataMap;
	}

	/**
	 * 日历界面的删除功能
	 * 
	 * @param title
	 * @param endDate
	 * @param eventDate
	 * 
	 * @return title=ASfd&eventDate=2017-05-10&endDate=2017-05-17
	 */
	@RequestMapping(value = "/fullCalendarDeleteAjax")
	@ResponseBody
	public Map<String, Object> fullCalendarDeleteAjax(String title, String endDate, String eventDate) {
		SysLegalHoliday holiday = new SysLegalHoliday();
		holiday.setHolidayName(title);
		holiday.setHolidayYear(endDate.substring(0, 4));
		holiday.setBeginDate(eventDate);
		holiday.setEndDate(endDate);
		// 调用Service层的删除功能
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			sysLegalHolidayRecordFeign.delete(holiday);
			dataMap.put("flag", "success");
			dataMap.put("msg", "删除成功");
		} catch (Exception e) {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.ERROR.getMessage());
		}
		return dataMap;
	}

}
