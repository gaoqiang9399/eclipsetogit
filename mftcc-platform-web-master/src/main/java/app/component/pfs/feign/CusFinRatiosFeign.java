package app.component.pfs.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.pfs.entity.CusDuBangModel;
import app.component.pfs.entity.CusFinRatios;

@FeignClient("mftcc-platform-factor")
public interface CusFinRatiosFeign {

	@RequestMapping(value = "/cusFinRatios/getDuBangData")
	public List<CusDuBangModel> getDuBangData(@RequestBody CusFinRatios cusFinRatios) throws Exception;

}
