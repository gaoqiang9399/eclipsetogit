package app.component.pact.protect.feign;

import java.util.Map;

import app.component.pact.protect.entity.MfAssetProtectDizhai;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pact.protect.entity.MfAssetProtectRecord;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

/**
 * Title: MfAssetProtectRecordBoImplImpl.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Aug 11 18:17:12 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface MfAssetProtectRecordFeign{

	@RequestMapping(value = "/mfAssetProtectRecord/insert")
	public void insert(@RequestBody MfAssetProtectRecord mfAssetProtectRecord) throws Exception;

	@RequestMapping(value = "/mfAssetProtectRecord/delete")
	public void delete(@RequestBody MfAssetProtectRecord mfAssetProtectRecord) throws Exception;

	@RequestMapping(value = "/mfAssetProtectRecord/update")
	public void update(@RequestBody MfAssetProtectRecord mfAssetProtectRecord) throws Exception;

	@RequestMapping(value = "/mfAssetProtectRecord/getById")
	public MfAssetProtectRecord getById(@RequestBody MfAssetProtectRecord mfAssetProtectRecord) throws Exception;

	@RequestMapping(value = "/mfAssetProtectRecord/getByDizhai")
	public MfAssetProtectDizhai getByDizhai(@RequestBody MfAssetProtectDizhai mfAssetProtectDizhai) throws Exception;
	@RequestMapping(value = "/mfAssetProtectRecord/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage)
			throws Exception;

	@RequestMapping(value = "/mfAssetProtectRecord/initInputData", method = RequestMethod.POST)
	public Map<String, Object> initInputData() throws Exception;

	@RequestMapping(value = "/mfAssetProtectRecord/insertAssetProtect")
	public void insertAssetProtect(@RequestBody MfAssetProtectRecord mfAssetProtectRecord) throws Exception;

	@RequestMapping(value = "/mfAssetProtectRecord/updateAssetProtect")
	public void updateAssetProtect(@RequestBody MfAssetProtectRecord mfAssetProtectRecord) throws Exception;
	@RequestMapping(value = "/mfAssetProtectRecord/saveOrUpdateAssetDizhai")
	public void saveOrUpdateAssetDizhai(@RequestBody MfAssetProtectDizhai mfAssetProtectDizhai) throws Exception;
	@RequestMapping(value = "/mfAssetProtectRecord/initApproveData")
	public Map<String, Object> initApproveData(@RequestBody String protectId) throws Exception;

	@RequestMapping(value = "/mfAssetProtectRecord/submitProcess")
	public MfAssetProtectRecord submitProcess(@RequestBody String protectId,@RequestParam("regNo") String regNo, @RequestParam("regName") String regName, @RequestParam("orgNo") String orgNo) throws Exception;

	@RequestMapping(value = "/mfAssetProtectRecord/doCommit")
	public Result doCommit(@RequestBody Map<String, Object> dataMap) throws Exception;

	@RequestMapping(value = "/mfAssetProtectRecord/initAssetChangeData")
	public Map<String, Object> initAssetChangeData(@RequestBody String protectId) throws Exception;

	@RequestMapping(value = "/mfAssetProtectRecord/getAssetProtectDetailData")
	public Map<String, Object> getAssetProtectDetailData(@RequestBody String protectId) throws Exception;

}
