package app.component.pact.feign;
import app.component.pact.entity.MfBusPact;
import app.component.pact.entity.MfBusPactExtend;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * Title: MfBusPactBoImplImpl.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri May 27 14:34:25 CST 2016
 **/
@FeignClient("mftcc-platform-factor")
public interface MfBusPactExtendFeign {
	@RequestMapping(value = "/mfBusPactExtend/getAllListForCredit")
	public List<MfBusPactExtend> getAllListForCredit(@RequestBody MfBusPactExtend mfBusPactExtend) throws Exception;
	@RequestMapping(value = "/mfBusPactExtend/getAllListForApply")
	public List<MfBusPactExtend> getAllListForApply(@RequestBody MfBusPactExtend mfBusPactExtend) throws Exception;
	@RequestMapping(value = "/mfBusPactExtend/getAllListForStamp")
	public List<MfBusPactExtend> getAllListForStamp(@RequestBody MfBusPactExtend mfBusPactExtend) throws Exception;

	@RequestMapping(value = "/mfBusPactExtend/initTemplate")
	public Map<String,Object> initTemplate(@RequestParam(name="creditAppId") String  creditAppId, @RequestParam(name="templateId") String  templateId) throws Exception;
	@RequestMapping(value = "/mfBusPactExtend/initTemplateForApp")
	public Map<String,Object> initTemplateForApp(@RequestParam(name="appId") String  appId, @RequestParam(name="templateId") String  templateId) throws Exception;
	@RequestMapping(value = "/mfBusPactExtend/insertAjax")
	public void insertAjax(@RequestBody MfBusPactExtend mfBusPactExtend) throws Exception;
	@RequestMapping(value = "/mfBusPactExtend/updateExtendAjax")
	public void updateExtendAjax(@RequestBody MfBusPactExtend mfBusPactExtend) throws Exception;
	@RequestMapping(value = "/mfBusPactExtend/deleteExtendAjax")
	public void deleteExtendAjax(@RequestBody MfBusPactExtend mfBusPactExtend) throws Exception;
	@RequestMapping(value = "/mfBusPactExtend/getClientAjax")
	public String  getClientAjax(@RequestParam(name="creditAppId") String  creditAppId, @RequestParam(name="templateId") String  templateId,@RequestParam(name="agenciesId")String agenciesId) throws Exception;
	@RequestMapping(value = "/mfBusPactExtend/getById")
	public MfBusPactExtend getById(@RequestBody MfBusPactExtend mfBusPactExtend) throws Exception;

	@RequestMapping(value = "/mfBusPactExtend/getClientApplyAjax")
	public String  getClientApplyAjax(@RequestParam(name="appId") String  appId, @RequestParam(name="templateId") String  templateId) throws Exception;
}

