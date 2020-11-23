package app.component.ncfgroup.feign;

import java.io.File;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by dark on 2017/9/6.
 */
@FeignClient("mftcc-platform-factor")
public interface NcfLinkFaceFeign {
	@RequestMapping("/bizWhitelist/isRealInPerson")
	public boolean isRealInPerson(@RequestBody String cusNo, @RequestParam("uploadFile") File uploadFile,
			@RequestParam("fileName") String fileName, @RequestParam("fileType") String fileType)
			throws Exception;

	@RequestMapping("/bizWhitelist/hasRealInPerson")
	public boolean hasRealInPerson(@RequestBody String cusNo) throws Exception;
}
