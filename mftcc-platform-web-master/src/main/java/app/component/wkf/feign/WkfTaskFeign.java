package app.component.wkf.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface WkfTaskFeign {

	@RequestMapping(value = "/wkfTask/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/wkfTask/findByPageForBack")
	public Ipage findByPageForBack(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/wkfTask/findByPageForSecRefund")
	public Ipage findByPageForSecRefund(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/wkfTask/findByPageForIqpCont")
	public Ipage findByPageForIqpCont(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/wkfTask/findByPageForTransfer")
	public Ipage findByPageForTransfer(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/wkfTask/findByPageForCancel")
	public Ipage findByPageForCancel(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/wkfTask/findByPageForSecAmtOffset")
	public Ipage findByPageForSecAmtOffset(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/wkfTask/findByPageForActiveBuyBack")
	public Ipage findByPageForActiveBuyBack(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/wkfTask/findByPageForPassiveBuyBack")
	public Ipage findByPageForPassiveBuyBack(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/wkfTask/findByPageForRepayAdv")
	public Ipage findByPageForRepayAdv(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/wkfTask/findByPageForAuth")
	public Ipage findByPageForAuth(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/wkfTask/findByPageForExtend")
	public Ipage findByPageForExtend(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/wkfTask/findByPageForDealer")
	public Ipage findByPageForDealer(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/wkfTask/findByPageForExtendDealer")
	public Ipage findByPageForExtendDealer(@RequestBody Ipage ipage) throws Exception;

}
