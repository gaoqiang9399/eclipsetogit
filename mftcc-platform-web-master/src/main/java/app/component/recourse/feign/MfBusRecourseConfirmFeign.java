package  app.component.recourse.feign;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.recourse.entity.MfBusRecourseConfirm;
import app.util.toolkit.Ipage;

/**
 * Title: MfBusRecourseConfirmAction.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed May 16 19:43:59 CST 2018
 **/
@FeignClient("mftcc-platform-factor")
public interface MfBusRecourseConfirmFeign{

	@RequestMapping(value = "/mfBusRecourseConfirm/getById")
	public MfBusRecourseConfirm getById(@RequestBody MfBusRecourseConfirm mfBusRecourseConfirm);

	@RequestMapping(value = "/mfBusRecourseConfirm/insert")
	public void insert(@RequestBody MfBusRecourseConfirm mfBusRecourseConfirm);

	@RequestMapping(value = "/mfBusRecourseConfirm/getByFincId")
	public List<MfBusRecourseConfirm> getByFincId(@RequestBody MfBusRecourseConfirm mfBusRecourseConfirm);
	
	@RequestMapping(value = "/mfBusRecourseConfirm/getMfBusRecourseConfirmList")
	public List<MfBusRecourseConfirm> getMfBusRecourseConfirmList(@RequestBody MfBusRecourseConfirm mfBusRecourseConfirm);

	@RequestMapping(value = "/mfBusRecourseConfirm/getByRecourseId")
    List<MfBusRecourseConfirm> getByRecourseId(@RequestBody MfBusRecourseConfirm mfBusRecourseConfirm);

	@RequestMapping(value = "/mfBusRecourseConfirm/getRecourseExpiresPage")
	public Ipage getRecourseExpiresPage(Ipage ipage);
}
