package app.component.oa.attendance.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.oa.attendance.entity.MfOaAttendance;
import app.util.toolkit.Ipage;

/**
 * Title: MfOaAttendanceBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Jan 15 15:31:49 CST 2018
 **/
@FeignClient("mftcc-platform-factor")
public interface MfOaAttendanceFein {
	@RequestMapping(value = "/mfOaAttendance/insert")
	public MfOaAttendance insert(MfOaAttendance mfOaAttendance) throws Exception;

	@RequestMapping(value = "/mfOaAttendance/delete")
	public void delete(MfOaAttendance mfOaAttendance) throws Exception;

	@RequestMapping(value = "/mfOaAttendance/update")
	public void update(MfOaAttendance mfOaAttendance) throws Exception;

	@RequestMapping(value = "/mfOaAttendance/getById")
	public MfOaAttendance getById(MfOaAttendance mfOaAttendance) throws Exception;

	@RequestMapping(value = "/mfOaAttendance/findByPage")
	public Ipage findByPage(Ipage ipage) throws Exception;

	@RequestMapping(value = "/mfOaAttendance/getListByDate")
	public List<MfOaAttendance> getListByDate(MfOaAttendance mfOaAttendance) throws Exception;

}
