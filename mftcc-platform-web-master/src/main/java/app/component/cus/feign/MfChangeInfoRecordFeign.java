package app.component.cus.feign;

import app.component.cus.entity.MfBusAgenciesConfig;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("mftcc-platform-factor")
public interface MfChangeInfoRecordFeign {
	@RequestMapping(value = "/mfChangeInfoRecord/handleUpdateDoc")
	public void handleUpdateDoc(@RequestParam(name="cusNo") String cusNo, @RequestParam(name="docSplitNo") String docSplitNo, @RequestParam(name="docBizNo") String docBizNo, @RequestParam(name="docName")String docName, @RequestParam(name="opNo")String opNo, @RequestParam(name="opType")String opType) throws Exception;
	@RequestMapping(value = "/mfChangeInfoRecord/findChangeInfoRecordList")
	public Ipage findChangeInfoRecordList(@RequestBody Ipage ipage) throws Exception;
}
