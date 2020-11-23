package app.component.report.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("mftcc-platform-factor")
public interface MfPieVouTypeFeign {
	@RequestMapping(value = "/mfPieVouType/List<Object>getVouTypeList")
	public  List<Object>getVouTypeList(@RequestBody Map<String, String>parmMap)throws Exception;

	/**
	 * 获取期限类型列表
	 * @param parmMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mfPieVouType/List<Object>getTermTypeList")
	public  List<Object>getTermTypeList(@RequestBody Map<String, String>parmMap)throws Exception;
}
