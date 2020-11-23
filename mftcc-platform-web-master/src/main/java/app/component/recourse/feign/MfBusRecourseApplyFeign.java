package  app.component.recourse.feign;
import java.util.List;

import app.base.ServiceException;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.recourse.entity.MfBusRecourseApply;

/**
 * Title: MfBusRecourseApplyAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed May 16 19:39:17 CST 2018
 **/
@FeignClient("mftcc-platform-factor")
public interface MfBusRecourseApplyFeign{

	@RequestMapping(value = "/mfBusRecourseApply/getByCompensatoryId")
	public MfBusRecourseApply getByCompensatoryId(@RequestBody MfBusRecourseApply mfBusRecourseApply);

	@RequestMapping(value = "/mfBusRecourseApply/insert")
	public MfBusRecourseApply insert(@RequestBody MfBusRecourseApply mfBusRecourseApply);

	@RequestMapping(value = "/mfBusRecourseApply/insertRec")
	public MfBusRecourseApply insertRec(@RequestBody MfBusRecourseApply mfBusRecourseApply);

	@RequestMapping(value = "/mfBusRecourseApply/getById")
	public MfBusRecourseApply getById(@RequestBody MfBusRecourseApply mfBusRecourseApply);

	@RequestMapping(value = "/mfBusRecourseApply/getRecourseList")
	public List<MfBusRecourseApply> getRecourseList(@RequestBody MfBusRecourseApply mfBusRecourseApply);

	@RequestMapping(value = "/mfBusRecourseApply/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping(value = "/mfBusRecourseApply/getRecourseAmtSum")
	public Double getRecourseAmtSum(@RequestBody MfBusRecourseApply mfBusRecourseApply);

	@RequestMapping(value = "/mfBusRecourseApply/update")
	public void update(@RequestBody MfBusRecourseApply mfBusRecourseApply);

	@RequestMapping(value = "/mfBusRecourseApply/findByAppName")
	public Ipage findByAppName(@RequestBody Ipage ipage) throws ServiceException;

}
