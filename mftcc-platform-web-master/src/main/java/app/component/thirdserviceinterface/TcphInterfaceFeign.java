package app.component.thirdserviceinterface;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.thirdservice.lmxd.entity.MfThirdServiceRecord;
import app.component.thirdservice.lmxd.entity.MfThirdServiceRecordParm;

@FeignClient("mftcc-platform-factor")
public interface TcphInterfaceFeign {
	@RequestMapping(value = "/tcphInterface/getMfThirdServiceRecordById")
	MfThirdServiceRecordParm getMfThirdServiceRecordById(@RequestBody MfThirdServiceRecord mfThirdServiceRecord)throws Exception;
}
