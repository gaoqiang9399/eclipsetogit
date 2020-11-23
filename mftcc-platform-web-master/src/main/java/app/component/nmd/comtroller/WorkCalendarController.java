package app.component.nmd.comtroller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.domain.screen.FormData;
import com.core.service.screen.FormService;
import com.core.struts.ActionContext;
import com.core.struts.BaseFormBean;
import com.core.struts.taglib.JsonTableUtil;

import app.base.User;
import app.component.common.CalendarConstant;
import app.component.common.EntityUtil;
import app.component.common.PasConstant;
import app.component.nmd.entity.ValueBean;
import app.component.nmd.entity.WorkCalendar;
import app.component.nmd.feign.WorkCalendarFeign;
import app.component.sysTaskInfoInterface.SysTaskInfoInterfaceFeign;
import app.util.toolkit.Ipage;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.DateUtil;

/**
 * Title: WorkCalendarAction.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Mon May 23 08:18:16 GMT 2016
 **/
@Controller
@RequestMapping("/workCalendar")
public class WorkCalendarController extends BaseFormBean {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	// 注入WorkCalendarBo
	@Autowired
	private WorkCalendarFeign workCalendarFeign;
	@Autowired
	private SysTaskInfoInterfaceFeign sysTaskInfoInterfaceFeign;

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
		return "component/nmd/WorkCalendar_List";
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
		WorkCalendar workCalendar = new WorkCalendar();
		try {
			workCalendar.setCustomQuery(ajaxData);// 自定义查询参数赋值
			workCalendar.setCriteriaList(workCalendar, ajaxData);// 我的筛选
			// this.getRoleConditions(workCalendar,"1000000001");//记录级权限控制方法
			Ipage ipage = this.getIpage();
			ipage.setPageSize(pageSize);
			ipage.setPageNo(pageNo);// 异步传页面翻页参数
			// 自定义查询Bo方法
			ipage = workCalendarFeign.findByPage(ipage, workCalendar);
			JsonTableUtil jtu = new JsonTableUtil();
			String tableHtml = jtu.getJsonStr(tableId, tableType, (List) ipage.getResult(), ipage, true);
			ipage.setResult(tableHtml);
			dataMap.put("ipage", ipage);
			/**
			 * ipage.setResult(tableHtml); dataMap.put("ipage",ipage); 需要改进的方法
			 * dataMap.put("tableData",tableHtml);
			 */
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
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertAjax")
	@ResponseBody
	public Map<String, Object> insertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formnmd3002 = formService.getFormData("nmd3002");
			getFormValue(formnmd3002, getMapByJson(ajaxData));
			if (this.validateFormData(formnmd3002)) {
				WorkCalendar workCalendar = new WorkCalendar();
				setObjValue(formnmd3002, workCalendar);
				workCalendarFeign.insert(workCalendar);
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
		FormData formnmd3002 = formService.getFormData("nmd3002");
		getFormValue(formnmd3002, getMapByJson(ajaxData));
		WorkCalendar workCalendarJsp = new WorkCalendar();
		setObjValue(formnmd3002, workCalendarJsp);
		WorkCalendar workCalendar = workCalendarFeign.getById(workCalendarJsp);
		if (workCalendar != null) {
			try {
				workCalendar = (WorkCalendar) EntityUtil.reflectionSetVal(workCalendar, workCalendarJsp,
						getMapByJson(ajaxData));
				workCalendarFeign.update(workCalendar);
				dataMap.put("flag", "success");
				dataMap.put("msg", MessageEnum.SUCCEED_SAVE.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				dataMap.put("flag", "error");
				dataMap.put("msg", MessageEnum.ERROR_INSERT.getMessage());
				throw e;
			}
		} else {
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_SAVE_CONTENT.getMessage("编号不存在"));
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
			FormData formnmd3002 = formService.getFormData("nmd3002");
			getFormValue(formnmd3002, getMapByJson(ajaxData));
			if (this.validateFormData(formnmd3002)) {
				WorkCalendar workCalendar = new WorkCalendar();
				setObjValue(formnmd3002, workCalendar);
				workCalendarFeign.update(workCalendar);
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
	public Map<String, Object> getByIdAjax(String calendarNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		FormData formnmd3002 = formService.getFormData("nmd3002");
		WorkCalendar workCalendar = new WorkCalendar();
		workCalendar.setCalendarNo(calendarNo);
		workCalendar = workCalendarFeign.getById(workCalendar);
		getObjValue(formnmd3002, workCalendar, formData);
		if (workCalendar != null) {
			dataMap.put("flag", "success");
		} else {
			dataMap.put("flag", "error");
		}
		dataMap.put("formData", formData);
		return dataMap;
	}

	/**
	 * AJAX获取查看
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sendMsgAjax")
	@ResponseBody
	public Map<String, Object> sendMsgAjax(String calendarNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> formData = new HashMap<String, Object>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		WorkCalendar workCalendar = new WorkCalendar();
		workCalendar.setCalendarNo(calendarNo);
		workCalendar = workCalendarFeign.getById(workCalendar);
		if (workCalendar != null) {
			sysTaskInfoInterfaceFeign.insertMessage(PasConstant.PAS_SUB_TYPE_SYS_MSG, workCalendar.getTitle(),
					workCalendar.getEventDesc(), workCalendar.getMakeMan(), PasConstant.PAS_IMPORT_LEV_1,
					PasConstant.PAS_IS_REPLY_NO, User.getRegName(this.request));
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
	public Map<String, Object> deleteAjax(String calendarNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		WorkCalendar workCalendar = new WorkCalendar();
		workCalendar.setCalendarNo(calendarNo);
		try {
			workCalendarFeign.delete(workCalendar);
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
	 * 新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/input")
	public String input(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd3002 = formService.getFormData("nmd3002");
		model.addAttribute("formnmd3002", formnmd3002);
		model.addAttribute("query", "");
		return "component/nmd/WorkCalendar_Insert";
	}

	/***
	 * 新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insert")
	public String insert(Model model) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd3002 = formService.getFormData("nmd3002");
		getFormValue(formnmd3002);
		WorkCalendar workCalendar = new WorkCalendar();
		setObjValue(formnmd3002, workCalendar);
		workCalendar.setCurrentSessionOrgNo(User.getOrgNo(request));
		workCalendarFeign.insert(workCalendar);
		getObjValue(formnmd3002, workCalendar);
		this.addActionMessage(model, "保存成功");
		List<WorkCalendar> workCalendarList = (List<WorkCalendar>) workCalendarFeign
				.findByPage(this.getIpage(), workCalendar).getResult();
		model.addAttribute("formnmd3002", formnmd3002);
		model.addAttribute("workCalendar", workCalendar);
		model.addAttribute("workCalendarList", workCalendarList);
		model.addAttribute("query", "");
		return "component/nmd/WorkCalendar_Detail";
	}

	/**
	 * 查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getById")
	public String getById(Model model, String calendarNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formnmd3001 = formService.getFormData("nmd3001");
		getFormValue(formnmd3001);
		WorkCalendar workCalendar = new WorkCalendar();
		workCalendar.setCalendarNo(calendarNo);
		workCalendar = workCalendarFeign.getById(workCalendar);
		getObjValue(formnmd3001, workCalendar);
		model.addAttribute("formnmd3001", formnmd3001);
		model.addAttribute("workCalendar", workCalendar);
		model.addAttribute("query", "");
		return "component/nmd/WorkCalendar_Detail";
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	public String delete(Model model, String calendarNo) throws Exception {
		ActionContext.initialize(request, response);
		WorkCalendar workCalendar = new WorkCalendar();
		workCalendar.setCalendarNo(calendarNo);
		workCalendarFeign.delete(workCalendar);
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
		FormData formnmd3002 = formService.getFormData("nmd3002");
		getFormValue(formnmd3002);
		boolean validateFlag = this.validateFormData(formnmd3002);
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
		FormData formnmd3002 = formService.getFormData("nmd3002");
		getFormValue(formnmd3002);
		boolean validateFlag = this.validateFormData(formnmd3002);
	}

	/* 移植代码start */
	/**
	 * 按月查询
	 * @param //wait 
	 * 
	 * @return
	 */
	@RequestMapping(value = "/monthlist")
	public String monthlist(Model model, String month, String wait) {
		List<WorkCalendar> list = new ArrayList<WorkCalendar>();
		Map<String, String> map = new HashMap<String, String>();
		if (month != null) {
			//month = request.getParameter("month");
			month = String.valueOf(Integer.parseInt(month) + 1);
		} else {
			month = (User.getTime().substring(4, 6));
		}
		if (month.length() == 1) {
			month = "0" + month;
		}
		String year = User.getTime().substring(0, 4);
		String oneday = "01";
		String lastday = "31";
		map.put("make_date1", year + month + oneday);
		map.put("make_date2", year + month + lastday);
		map.put("make_man", User.getRegNo(request));
		//wait = request.getParameter("wait");// 是否显示未办
													// 1:
													// 显示未办
		if (wait.equals(CalendarConstant.WORKCALENDAR_WAIT_Y)) {
			map.put("end_sts", CalendarConstant.WORKCALENDAR_END_STS_N);
			list = this.workCalendarFeign.waitmonthlist(map);
		} else {
			list = this.workCalendarFeign.monthlist(map);
		}
		model.addAttribute("list", list);
		model.addAttribute("wait", wait);
		model.addAttribute("query", "");
		return "component/nmd/Calendar_month_list";
	}

	/**
	 * 按周查询
	 * @param firstday 
	 * @param lastday 
	 * @param wait 
	 * 
	 * @return
	 */
	@RequestMapping(value = "/weeklist")
	public String weeklist(Model model, String ajaxData, String firstday, String lastday, String wait) {
		// firstday = request.getParameter("firstday");// 一周起始日期
		 //lastday = request.getParameter("lastday");// 一周结束日期
		// wait = request.getParameter("wait");// 是否显示未办
													// 1:
													// 显示未办
		String[] weeks = { "星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };

		List<WorkCalendar> list = new ArrayList<WorkCalendar>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("make_date1", firstday);
		map.put("make_date2", lastday);
		map.put("make_man", User.getRegNo(request));
		if (wait.equals(CalendarConstant.WORKCALENDAR_WAIT_Y)) {
			map.put("end_sts", CalendarConstant.WORKCALENDAR_END_STS_N);
			list = this.workCalendarFeign.waitweeklist(map);
		} else {
			list = this.workCalendarFeign.monthlist(map);
		}
		Integer fday = Integer.parseInt(firstday);
		Integer lday = Integer.parseInt(lastday);
		List<ValueBean> weekList = new ArrayList<ValueBean>();
		int weeked = 0;
		if (!(firstday.substring(4, 6).equals(lastday.substring(4, 6)))) {
			int day01 = DateUtil.getDaysOfMonth(firstday);
			Integer onelaseday = Integer.parseInt(firstday.substring(0, 6) + String.valueOf(day01));// 前一月份最后一天
			Integer onebeginday = Integer.parseInt(lastday.substring(0, 6) + "01");

			for (int i = fday; i <= onelaseday; i++) {
				ValueBean week = new ValueBean();
				week.setName1(String.valueOf(i));
				week.setName2(weeks[weeked++]);
				weekList.add(week);
			}
			for (int i = onebeginday; i <= lday; i++) {
				ValueBean week = new ValueBean();
				week.setName1(String.valueOf(i));
				week.setName2(weeks[weeked++]);
				weekList.add(week);
			}
		} else {
			for (int i = fday; i <= lday; i++) {
				ValueBean week = new ValueBean();
				week.setName1(String.valueOf(i));
				week.setName2(weeks[weeked++]);
				weekList.add(week);
			}
		}

		model.addAttribute("weekList", weekList);
		model.addAttribute("list", list);
		model.addAttribute("firstday", firstday);
		model.addAttribute("lastday", lastday);
		model.addAttribute("weekList", weekList);
		model.addAttribute("query", "");
		return "component/nmd/Calendar_week_list";
	}

	/**
	 * 按日查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/daylist")
	public String daylist(Model model, String wait,String riqi) {
		List<WorkCalendar> list = new ArrayList<WorkCalendar>();
		//String wait = request.getParameter("wait");// 是否显示未办
													// 1:
													// 显示未办
		WorkCalendar calendar = new WorkCalendar();
		calendar.setMakeDate(riqi);
		calendar.setMakeMan(User.getRegNo(request));
		if (wait.equals(CalendarConstant.WORKCALENDAR_WAIT_Y)) {
			calendar.setEndSts(CalendarConstant.WORKCALENDAR_END_STS_N);
			list = this.workCalendarFeign.waitdaylist(calendar);
		} else {
			list = this.workCalendarFeign.daylist(calendar);
		}

		model.addAttribute("list", list);
		model.addAttribute("query", "");
		return "component/nmd/Calendar_day";
	}

	/**
	 * FullCalendar按月查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/fullCalendarmonthlist")
	public String fullCalendarmonthlist(Model model, String wait) {
		List<WorkCalendar> list = new ArrayList<WorkCalendar>();
		//String wait = request.getParameter("wait");// 是否显示未办
		Map<String, String> map = new HashMap<String, String>();
		map.put("make_man", User.getRegNo(request));
		list = this.workCalendarFeign.FullCalendarmonthlist(map);
		String event_all = fullCalendarJsonArray(list);
		model.addAttribute("event_all", event_all);
		model.addAttribute("wait", wait);
		model.addAttribute("query", "");
		return "component/nmd/FullCalendar_month_list";
	}

	@ResponseBody
	@RequestMapping(value = "/fullCalendarmonthlistAjax")
	public Map<String, Object> fullCalendarmonthlistAjax(String wait) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			// holidayList = accInterfaceFeign.AcComHolidayGetBylist(month);
			List<WorkCalendar> list = new ArrayList<WorkCalendar>();
			Map<String, String> map = new HashMap<String, String>();
			map.put("make_man", User.getRegNo(request));
			list = this.workCalendarFeign.FullCalendarmonthlist(map);
			String event_all = fullCalendarJsonArray(list);
			dataMap.put("wait", wait);
			dataMap.put("eventAll", event_all);
			List holidayList = null;
			dataMap.put("holidays", holidayList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dataMap;
	}

	@RequestMapping(value = "/fullCalendarGetHolidayslistAjax")
	@ResponseBody
	public Map<String, Object> fullCalendarGetHolidayslistAjax(String wait) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List holidayList = null;
		try {
			// holidayList = accInterfaceFeign.AcComHolidayGetBylist(event_date);
			dataMap.put("wait", wait);
			dataMap.put("holidays", holidayList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dataMap;
	}

	private String fullCalendarJsonArray(List<WorkCalendar> list) {
		String eventTitle; // 事件标题
		String eventDate; // 事件开始日期
		String eventTime; // 事件开始时间
		String eventTime2; // 事件日期，时间拼接
		String calendarNo; // 日历编号
		String endDate; // 事件截止日期
		String endTime; // 事件截止时间
		String event_all = "[";
		for (int i = 0; i < list.size(); i++) {
			eventTitle = list.get(i).getTitle();
			calendarNo = list.get(i).getCalendarNo();
			eventTime = list.get(i).getEventTime();
			eventDate = list.get(i).getEventDate();
			endDate = list.get(i).getEndDate();
			endTime = list.get(i).getEndTime();

			if (eventTime == null || "".equals(eventTime)) {
				eventTime2 = DateUtil.getDiyDate(eventDate, "yyyy-MM-dd");
			} else {
				eventTime2 = DateUtil.getDiyDate(eventDate, "yyyy-MM-dd") + "T" + eventTime + ":00";
			}
			if (endDate == null || "".equals(endDate)) {
				event_all = event_all + "{id:" + calendarNo + ",title:'" + eventTitle + "',start:'" + eventTime2
						+ "'},";
			} else if ((endDate != null && !"".equals(endDate)) && (endTime == null || "".equals(endTime))) {
				endDate = DateUtil.getDiyDate(endDate, "yyyy-MM-dd");
				event_all = event_all + "{id:" + calendarNo + ",title:'" + eventTitle + "',start:'" + eventTime2
						+ "',end:'" + endDate + "'},";
			} else if ((endDate != null && !"".equals(endDate)) && (endTime != null && !"".equals(endTime))) {
				endDate = DateUtil.getDiyDate(endDate, "yyyy-MM-dd") + "T" + endTime + ":00";
				event_all = event_all + "{id:" + calendarNo + ",title:'" + eventTitle + "',start:'" + eventTime2
						+ "',end:'" + endDate + "'},";
			}else {
			}
		}
		if (!"[".equals(event_all)) {
			event_all = event_all.substring(0, event_all.length() - 1) + "]";

		} else {
			event_all = event_all + "]";
		}

		return event_all;
	}

	/**
	 * fullCalendar新增或修改页面
	 * 
	 * @param workCalendar
	 */
	@RequestMapping(value = "/fullCalendarInput")
	public String fullCalendarInput(Model model, @RequestBody WorkCalendar workCalendar) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formhom2012 = formService.getFormData("hom2012");
		workCalendar.setMakeMan(User.getRegNo(request));
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		workCalendar.setMakeDate(sdf.format(date));
		getObjValue(formhom2012, workCalendar);
		model.addAttribute("calendar", workCalendar);
		model.addAttribute("formhom2012", formhom2012);
		model.addAttribute("query", "");
		return "component/nmd/FullCalendar_insert";
	}

	/**
	 * FullCalendar按日程编号查询
	 * 
	 * @param calendarNo
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/fullCalendargetById")
	public String fullCalendargetById(Model model, String calendarNo) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formhom2013 = formService.getFormData("hom2013");
		WorkCalendar workCalendar = new WorkCalendar();
		workCalendar.setCalendarNo(calendarNo);
		workCalendar = workCalendarFeign.getById(workCalendar);
		getObjValue(formhom2013, workCalendar);
		model.addAttribute("formhom2013", formhom2013);
		model.addAttribute("query", "");
		return "component/nmd/fullCalendar_detail";
	}

	/**
	 * fullCalendar拖动与拉伸实时存储
	 * 
	 * @param event_date
	 * @param calendarNo
	 * @param end_date
	 * 
	 * @return
	 */
	@RequestMapping(value = "/drag")
	public Map<String, Object> drag(String event_date, String calendarNo, String end_date) {
		ActionContext.initialize(request, response);
		WorkCalendar workCalendar = new WorkCalendar();
		workCalendar.setCalendarNo(calendarNo);
		workCalendar = workCalendarFeign.getById(workCalendar);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String time; // 事件开始时间
		String start; // 事件开始日期
		String endTime; // 事件结束日期
		String end; // 事件结束日期
		if (event_date != null && event_date.length() == 10) { // 拖动事件（无精确事件时间）
			start = event_date.substring(0, 4) + event_date.substring(5, 7) + event_date.substring(8, 10);
			workCalendar.setEventDate(start);
		} else if (event_date != null && event_date.length() == 19) { // 拖动事件（有确事件时间）
			start = event_date.substring(0, 4) + event_date.substring(5, 7) + event_date.substring(8, 10);
			time = event_date.substring(11, 16);
			workCalendar.setEventDate(start);
			workCalendar.setEventTime(time);
		}else {
		}
		if (end_date != null && end_date.length() == 10) { // 拖动事件（无精确事件时间）
			end = end_date.substring(0, 4) + end_date.substring(5, 7) + end_date.substring(8, 10);
			workCalendar.setEndDate(end);
		} else if (end_date != null && end_date.length() == 19) { // 拖动事件（有确事件时间）
			end = end_date.substring(0, 4) + end_date.substring(5, 7) + end_date.substring(8, 10);
			endTime = end_date.substring(11, 16);
			workCalendar.setEndTime(endTime);
			workCalendar.setEndDate(end);
		}else {
		}
		workCalendarFeign.update(workCalendar);
		String sysDate = User.getCurrDate();
		if (sysDate.equals(workCalendar.getWarnDate())) {
			dataMap.put("workCalendar", workCalendar);
		}
		return dataMap;
	}

	/**
	 * fullCalendar新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/fullCalendarInsert")
	public String fullCalendarInsert(Model model, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formhom2012 = formService.getFormData("hom2012");
		getFormValue(formhom2012);
		WorkCalendar workCalendar = new WorkCalendar();
		setObjValue(formhom2012, workCalendar);
		this.workCalendarFeign.insert(workCalendar);
		// 以下操作是为了跳转到详情查看页面
		getObjValue(formhom2012, workCalendar);
		String calendarNo = workCalendar.getCalendarNo();
		model.addAttribute("formhom2012", formhom2012);
		model.addAttribute("workCalendar", workCalendar);
		model.addAttribute("calendarNo", calendarNo);
		model.addAttribute("query", "query");
		return "component/nmd/FullCalendar_detail1";
	}

	/**
	 * AJAX新增
	 * 
	 * @param event_all
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/fullCalendarInsertAjax")
	@ResponseBody
	public Map<String, Object> fullCalendarInsertAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formnmd3002 = formService.getFormData("nmd3002");
			getFormValue(formnmd3002, getMapByJson(ajaxData));
			Map<String, Object> ajaxDataMap = getMapByJson(ajaxData);
			if (this.validateFormData(formnmd3002)) {
				WorkCalendar workCalendar = new WorkCalendar();
				setObjValue(formnmd3002, workCalendar);
				workCalendar.setEndSts(CalendarConstant.WORKCALENDAR_END_STS_N);
				workCalendar.setEventDate( DateUtil.getYYYYMMDD(String.valueOf(ajaxDataMap.get("eventDate"))));
				workCalendar.setEndDate(DateUtil.getYYYYMMDD(String.valueOf(ajaxDataMap.get("endDate"))));
				workCalendarFeign.insert(workCalendar);
				Map<String, String> map = new HashMap<String, String>();
				map.put("make_man", User.getRegNo(request));
				List<WorkCalendar> list = new ArrayList<WorkCalendar>();
				list = workCalendarFeign.FullCalendarmonthlist(map);
				String event_all = fullCalendarJsonArray(list);
				String sysDate = User.getCurrDate();
				if (sysDate.equals(workCalendar.getWarnDate())) {
					dataMap.put("workCalendar", workCalendar);
				}
				dataMap.put("event_all", event_all);
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
	 * FullCalendar更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/fullCalendarUpdate")
	public String fullCalendarUpdate(Model model, String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		FormData formhom2013 = formService.getFormData("hom2013");
		getFormValue(formhom2013);
		WorkCalendar workCalendar = new WorkCalendar();
		setObjValue(formhom2013, workCalendar);
		this.workCalendarFeign.update(workCalendar);
		// 以下操作是为了跳转到详情查看页面
		getObjValue(formhom2013, workCalendar);
		String calendarNo = workCalendar.getCalendarNo();
		this.addActionMessage(model, "操作成功!");
		model.addAttribute("workCalendar", workCalendar);
		model.addAttribute("calendarNo", calendarNo);
		model.addAttribute("formhom2013", formhom2013);
		model.addAttribute("query", "");
		return "component/nmd/fullCalendar_detail";
	}

	/**
	 * AJAX更新保存
	 * 
	 * @param event_all
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/fullCalendarUpdateAjax")
	@ResponseBody
	public Map<String, Object> fullCalendarUpdateAjax(String ajaxData) throws Exception {
		FormService formService = new FormService();
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			FormData formhom2013 = formService.getFormData("hom2013");
			getFormValue(formhom2013, getMapByJson(ajaxData));
			if (this.validateFormData(formhom2013)) {
				WorkCalendar workCalendar = new WorkCalendar();
				setObjValue(formhom2013, workCalendar);
				workCalendarFeign.update(workCalendar);
				Map<String, String> map = new HashMap<String, String>();
				map.put("make_man", User.getRegNo(request));
				List<WorkCalendar> list = new ArrayList<WorkCalendar>();
				list = this.workCalendarFeign.FullCalendarmonthlist(map);
				String event_all = fullCalendarJsonArray(list);
				String sysDate = User.getCurrDate();
				if (sysDate.equals(workCalendar.getWarnDate())) {
					dataMap.put("workCalendar", workCalendar);
				}
				dataMap.put("event_all", event_all);
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
	 * 按日程编号删除
	 * 
	 * @param workCalendar
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/del")
	public String del(@RequestBody WorkCalendar workCalendar, Model model) throws Exception {
		FormData formhom2013 = new FormService().getFormData("hom2013");
		getFormValue(formhom2013);
		setObjValue(formhom2013, workCalendar);
		workCalendarFeign.delete(workCalendar);
		this.addActionMessage(model, "删除成功!");
		model.addAttribute("formhom2013", formhom2013);
		model.addAttribute("query", "");
		return "component/nmd/fullCalendar_detail";
	}

	/**
	 * Ajax异步删除
	 * 
	 * @param event_all
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/fullCalendarDeleteAjax")
	@ResponseBody
	public Map<String, Object> fullCalendarDeleteAjax(String calendarNo) throws Exception {
		ActionContext.initialize(request, response);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		WorkCalendar workCalendar = new WorkCalendar();
		workCalendar.setCalendarNo(calendarNo);
		try {
			workCalendarFeign.delete(workCalendar);
			Map<String, String> map = new HashMap<String, String>();
			map.put("make_man", User.getRegNo(request));
			List<WorkCalendar> list = new ArrayList<WorkCalendar>();
			list = this.workCalendarFeign.FullCalendarmonthlist(map);
			String event_all = fullCalendarJsonArray(list);
			dataMap.put("event_all", event_all);
			dataMap.put("flag", "success");
			dataMap.put("msg", MessageEnum.SUCCEED_DELETE.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			dataMap.put("flag", "error");
			dataMap.put("msg", MessageEnum.FAILED_DELETE.getMessage());
			throw e;
		}
		return dataMap;
	}
	/* 移植代码end */

}
