package app.component.nmd.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.nmd.entity.WorkCalendar;
import app.util.toolkit.Ipage;

/**
 * Title: WorkCalendarBo.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Mon May 23 08:18:16 GMT 2016
 **/

@FeignClient("mftcc-platform-factor")
public interface WorkCalendarFeign {
	@RequestMapping("/workCalendar/getScheduleCountBySelf")
	public int getScheduleCountBySelf(@RequestParam("opNo") String opNo) throws ServiceException;

	@RequestMapping("/workCalendar/insert")
	public void insert(WorkCalendar workCalendar) throws ServiceException;

	@RequestMapping("/workCalendar/delete")
	public void delete(WorkCalendar workCalendar) throws ServiceException;

	@RequestMapping("/workCalendar/update")
	public void update(WorkCalendar workCalendar) throws ServiceException;

	@RequestMapping("/workCalendar/getById")
	public WorkCalendar getById(WorkCalendar workCalendar) throws ServiceException;

	@RequestMapping("/workCalendar/findByPage")
	public Ipage findByPage(Ipage ipage, @RequestParam("workCalendar") WorkCalendar workCalendar) throws ServiceException;

	/**
	 * 按月列表
	 * 
	 * @param map
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping("/workCalendar/monthlist")
	public List<WorkCalendar> monthlist(Map map) throws ServiceException;

	/**
	 * 月未完成查询
	 * 
	 * @param map
	 * @return
	 * @throws DAOException
	 */
	@RequestMapping("/workCalendar/waitmonthlist")
	public List<WorkCalendar> waitmonthlist(Map map) throws ServiceException;

	/**
	 * 按周查询
	 * 
	 * @param map
	 * @return
	 * @throws DAOException
	 */
	@RequestMapping("/workCalendar/weeklist")
	public List<WorkCalendar> weeklist(Map map) throws ServiceException;

	/**
	 * 周未完成
	 * 
	 * @param map
	 * @return
	 * @throws DAOException
	 */
	@RequestMapping("/workCalendar/waitweeklist")
	public List<WorkCalendar> waitweeklist(Map map) throws ServiceException;

	/**
	 * 按日查询
	 * 
	 * @param calendar
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping("/workCalendar/daylist")
	public List<WorkCalendar> daylist(WorkCalendar calendar) throws ServiceException;

	/**
	 * 日未完成
	 * 
	 * @param map
	 * @return
	 * @throws DAOException
	 */
	@RequestMapping("/workCalendar/waitdaylist")
	public List<WorkCalendar> waitdaylist(WorkCalendar workCalendar) throws ServiceException;

	/**
	 * FullCalendar按月列表
	 * 
	 * @param map
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping("/workCalendar/fullCalendarmonthlist")
	public List<WorkCalendar> FullCalendarmonthlist(Map map) throws ServiceException;

	/**
	 * FullCalendar按日列表
	 * 
	 * @param map
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping("/workCalendar/FullCalendarDaylist")
	public List<WorkCalendar> FullCalendarDaylist(Map map) throws ServiceException;
}
