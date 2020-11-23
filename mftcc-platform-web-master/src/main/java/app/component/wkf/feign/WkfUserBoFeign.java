package app.component.wkf.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.wkf.entity.AppUser;

@FeignClient("mftcc-platform-factor")
public interface WkfUserBoFeign {

	@RequestMapping(value = "/wkfUserBo/list")
	public List<AppUser> list(@RequestBody Map map) throws Exception;

}
