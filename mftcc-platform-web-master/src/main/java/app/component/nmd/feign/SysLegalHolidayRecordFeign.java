package app.component.nmd.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.nmd.entity.SysLegalHoliday;
import app.component.nmd.entity.SysLegalHolidayEvent;
import app.component.nmd.entity.SysLegalHolidayRecord;
import app.util.toolkit.Ipage;

/**
 * Title: SysLegalHolidayRecordBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu May 11 17:06:31 CST 2017
 **/

@FeignClient("mftcc-platform-factor")
public interface SysLegalHolidayRecordFeign {

	/**
	 * 根据节假日名称 开始时间 结束时间添加节假日
	 * 
	 * @param sysLegalHoliday
	 * @throws ServiceException
	 * @throws Exception
	 */
	@RequestMapping("/sysLegalHolidayRecord/insert")
	public void insert(@RequestBody SysLegalHoliday sysLegalHoliday) throws Exception;

	@RequestMapping("/sysLegalHolidayRecord/delete")
	public void delete(@RequestBody SysLegalHoliday sysLegalHoliday) throws Exception;

	@RequestMapping("/sysLegalHolidayRecord/update")
	public void update(@RequestBody SysLegalHoliday sysLegalHoliday) throws Exception;

	@RequestMapping("/sysLegalHolidayRecord/getById")
	public SysLegalHolidayRecord getById(@RequestBody SysLegalHolidayRecord sysLegalHolidayRecord) throws Exception;

	@RequestMapping("/sysLegalHolidayRecord/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/sysLegalHolidayRecord/findSysLegalHoliday")
	public Ipage findSysLegalHoliday(@RequestBody Ipage ipg, @RequestParam("sysLegalHolidayRecord") SysLegalHolidayRecord sysLegalHolidayRecord)
			throws Exception;

	// 获取所有节假日事件
	@RequestMapping("/sysLegalHolidayRecord/getAllHolidayEvent")
	public List<SysLegalHolidayEvent> getAllHolidayEvent();

	/**
	 * 根据传入的日期(yyyyMMdd)进行判断这天是否是节假日
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/sysLegalHolidayRecord/isHoliday")
	public boolean isHoliday(@RequestBody String date) throws Exception;

	/**
	 * 
	 * 方法描述：通过年 日期 获取节假日信息
	 * 
	 * @param holidayRecord
	 * @return
	 * @throws Exception
	 *             SysLegalHolidayRecord
	 * @author WD
	 * @date 2017-10-30 下午8:41:41
	 */
	@RequestMapping("/sysLegalHolidayRecord/getSysLegalHolidayRecordByDate")
	public SysLegalHolidayRecord getSysLegalHolidayRecordByDate(@RequestBody SysLegalHolidayRecord holidayRecord) throws Exception;

	/**
	 * 
	 * 方法描述： 根据月份查询节假日信息
	 * 
	 * @param sysLegalHolidayRecord
	 * @return
	 * @throws Exception
	 *             List<SysLegalHolidayRecord>
	 * @author YaoWenHao
	 * @date 2018-1-18 上午11:29:18
	 */
	@RequestMapping("/sysLegalHolidayRecord/getSysLegalHolidayRecordByMonth")
	public List<SysLegalHolidayRecord> getSysLegalHolidayRecordByMonth(@RequestBody SysLegalHolidayRecord sysLegalHolidayRecord)
			throws Exception;
}
