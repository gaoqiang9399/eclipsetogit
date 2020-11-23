package app.component.pledge.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.pledge.entity.MfBusFundPurchaseHis;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface MfBusFundPurchaseHisFeign {
	@RequestMapping(value = "/mfBusFundPurchaseHisController/insert")
	public MfBusFundPurchaseHis insert(@RequestBody MfBusFundPurchaseHis mfBusFundPurchaseHis) throws Exception ;
	
	@RequestMapping(value = "/mfBusFundPurchaseHisController/update")
	public String update(@RequestBody MfBusFundPurchaseHis mfBusFundPurchaseHis) throws Exception ;
	
	@RequestMapping(value = "/mfBusFundPurchaseHisController/delete")
	public void delete(@RequestBody MfBusFundPurchaseHis mfBusFundPurchaseHis) throws Exception ;
	
	@RequestMapping(value = "/mfBusFundPurchaseHisController/getMfBusFundPurchaseHisById")
	public MfBusFundPurchaseHis getMfBusFundPurchaseHisById(@RequestBody MfBusFundPurchaseHis mfBusFundPurchaseHis) throws Exception ;
	
	@RequestMapping(value = "/mfBusFundPurchaseHisController/getMfBusFundPurchaseHisList")
	public List<MfBusFundPurchaseHis> getMfBusFundPurchaseHisList(@RequestBody MfBusFundPurchaseHis mfBusFundPurchaseHis) throws Exception ;
	
	@RequestMapping(value = "/mfBusFundPurchaseHisController/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception ;
}
