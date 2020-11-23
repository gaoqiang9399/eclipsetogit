package app.component.cus.execnotice.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.cus.entity.MfCusEquityInfo;
import app.component.cus.execnotice.entity.MfCusExecNotice;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface MfCusExecNoticeFeign {
	@RequestMapping(value = "/mfCusExecNotice/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;
	
	@RequestMapping(value = "/mfCusExecNotice/getById")
	public MfCusExecNotice getById(@RequestBody MfCusExecNotice mfCusExecNotice) throws ServiceException;
	
	@RequestMapping(value = "/mfCusExecNotice/insert")
	public Map<String, Object> insert(@RequestBody MfCusExecNotice mfCusExecNotice) throws ServiceException;
	
	@RequestMapping(value = "/mfCusExecNotice/update")
	public void update(@RequestBody MfCusExecNotice mfCusExecNotice) throws ServiceException;
	
	@RequestMapping(value = "/mfCusExecNotice/delete")
	public void delete(@RequestBody MfCusExecNotice mfCusExecNotice) throws Exception;
	
	@RequestMapping(value = "/mfCusExecNotice/getAllList")
	public List<MfCusExecNotice> getAllList(@RequestBody MfCusExecNotice mfCusExecNotice) throws Exception;
}
