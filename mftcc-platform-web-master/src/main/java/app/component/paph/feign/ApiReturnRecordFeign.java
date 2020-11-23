package app.component.paph.feign;

import app.component.paph.entity.ApiReturnRecord;
import app.component.thirdservice.autoapproval.entity.MfAutoApprovalRecord;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
* Title: ApiReturnRecordBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Wed Aug 01 16:40:11 CST 2018
**/
@FeignClient("mftcc-platform-factor")
public interface ApiReturnRecordFeign {

	@RequestMapping(value = "/apiReturnRecord/insert")
	public void insert(@RequestBody ApiReturnRecord apiReturnRecord) throws Exception;

	@RequestMapping(value = "/apiReturnRecord/delete")
	public void delete(@RequestBody ApiReturnRecord apiReturnRecord) throws Exception;

	@RequestMapping(value = "/apiReturnRecord/update")
	public void update(@RequestBody ApiReturnRecord apiReturnRecord) throws Exception;

	@RequestMapping(value = "/apiReturnRecord/getById")
	public ApiReturnRecord getById(@RequestBody ApiReturnRecord apiReturnRecord) throws Exception;
	@RequestMapping(value = "/apiReturnRecord/getApiReturnRecordList")
	public List<ApiReturnRecord> getApiReturnRecordList(@RequestBody ApiReturnRecord apiReturnRecord) throws Exception;

	@RequestMapping(value = "/apiReturnRecord/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/apiReturnRecord/getResultHtml")
	public Map<String,Object> getResultHtml(@RequestBody ApiReturnRecord apiReturnRecord) throws Exception;

	@RequestMapping(value = "/apiReturnRecord/getThirdSelect")
    public Map<String,Object> getThirdSelect(@RequestBody Map<String,String> paramMap) throws Exception;

	@RequestMapping(value = "/apiReturnRecord/getApprovalFlagByAppId")
	public MfAutoApprovalRecord getApprovalFlagByAppId(@RequestBody MfAutoApprovalRecord mfAutoApprovalRecord) throws Exception;

	@RequestMapping(value = "/apiReturnRecord/assuerfindBypage")
	public List<ApiReturnRecord> assuerfindBypage(@RequestBody ApiReturnRecord apiReturnRecord) throws Exception;

	@RequestMapping(value = "/apiReturnRecord/getvoidinfo")
	public List<ApiReturnRecord> getvoidinfo(@RequestBody ApiReturnRecord apiReturnRecord) throws Exception;

	@RequestMapping(value = "/apiReturnRecord/getapiinfo")
	public List<ApiReturnRecord> getapiinfo(@RequestBody ApiReturnRecord apiReturnRecord) throws Exception;

	@RequestMapping("/apiReturnRecord/getRiskOutcomeList")
	public List<ApiReturnRecord> getRiskOutcomeList(@RequestBody String cusNo)throws Exception;
}
