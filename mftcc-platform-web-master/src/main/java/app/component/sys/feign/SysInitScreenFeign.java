package app.component.sys.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.core.domain.screen.OptionsList;

@FeignClient("mftcc-platform-factor")
public interface SysInitScreenFeign {
		
		@RequestMapping(value = "/sysInitScreen/getOtherOption")
		public List<OptionsList> getOtherOption(@RequestParam("sqlOption") String sqlOption) throws Exception;
}
