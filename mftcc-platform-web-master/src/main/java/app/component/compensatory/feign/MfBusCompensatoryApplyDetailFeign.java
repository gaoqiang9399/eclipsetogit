package  app.component.compensatory.feign;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.compensatory.entity.MfBusCompensatoryApplyDetail;

/**
 * Title: MfBusCompensatoryApplyDetailFeign.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed May 09 18:58:56 CST 2018
 **/
@FeignClient("mftcc-platform-factor")
public interface MfBusCompensatoryApplyDetailFeign{

	@RequestMapping(value = "/mfBusCompensatoryApplyDetail/findByPageAjax")
	public List<MfBusCompensatoryApplyDetail> findByPageAjax(@RequestBody MfBusCompensatoryApplyDetail mfBusCompensatoryApplyDetail);

	@RequestMapping(value = "/mfBusCompensatoryApplyDetail/getById")
	public MfBusCompensatoryApplyDetail getById(@RequestBody MfBusCompensatoryApplyDetail mfBusCompensatoryApplyDetail);

	@RequestMapping(value = "/mfBusCompensatoryApplyDetail/getDetailList")
	public List<MfBusCompensatoryApplyDetail> getDetailList(
			MfBusCompensatoryApplyDetail mfBusCompensatoryApplyDetail);

}