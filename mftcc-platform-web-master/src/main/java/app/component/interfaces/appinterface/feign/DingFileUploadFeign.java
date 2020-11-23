package app.component.interfaces.appinterface.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.doc.entity.DocManage;

@FeignClient("mftcc-platform-factor")
public interface DingFileUploadFeign {

	@RequestMapping(value = "/dingFileUpload/FileUpload")
	String FileUpload(@RequestBody Map<String, String> paramMap,@RequestParam("docManage") DocManage docManage) throws Exception;
}
