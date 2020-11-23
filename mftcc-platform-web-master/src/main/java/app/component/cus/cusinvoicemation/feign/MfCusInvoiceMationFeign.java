package app.component.cus.cusinvoicemation.feign;

import app.base.ServiceException;
import app.component.cus.cusInvoicemation.entity.MfCusInvoiceMation;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@FeignClient("mftcc-platform-factor")
public interface MfCusInvoiceMationFeign {
	@RequestMapping(value = "/mfCusInvoiceMation/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;
	
	@RequestMapping(value = "/mfCusInvoiceMation/getById")
	public MfCusInvoiceMation getById(@RequestBody MfCusInvoiceMation mfCusInvoiceMation) throws ServiceException;
	
	@RequestMapping(value = "/mfCusInvoiceMation/insert")
	public Map<String, Object> insert(@RequestBody MfCusInvoiceMation mfCusInvoiceMation) throws ServiceException;
	
	@RequestMapping(value = "/mfCusInvoiceMation/update")
	public void update(@RequestBody MfCusInvoiceMation mfCusInvoiceMation) throws ServiceException;
	
	@RequestMapping(value = "/mfCusInvoiceMation/delete")
	public void delete(@RequestBody MfCusInvoiceMation mfCusInvoiceMation) throws Exception;

	@RequestMapping(value = "/mfCusInvoiceMation/getAllList")
	public List<MfCusInvoiceMation> getAllList(@RequestBody MfCusInvoiceMation mfCusInvoiceMation) throws Exception;

}
