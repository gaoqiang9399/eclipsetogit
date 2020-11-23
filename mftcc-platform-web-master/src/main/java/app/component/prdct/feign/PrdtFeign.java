package app.component.prdct.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.prdct.entity.MfSysKind;

@FeignClient("mftcc-platform-fileService")
public interface PrdtFeign {
	@RequestMapping("/mfSysKind/getSysKindList")
	List<MfSysKind> getSysKindList(@RequestBody MfSysKind sysKind);

}
