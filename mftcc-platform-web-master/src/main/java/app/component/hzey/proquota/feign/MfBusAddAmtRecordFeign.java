package  app.component.hzey.proquota.feign;



import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.hzey.proquota.entity.MfBusAddAmtRecord;
import app.util.toolkit.Ipage;

/**
* Title: MfBusAddAmtRecordBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Nov 30 15:14:39 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfBusAddAmtRecordFeign{
	
	@RequestMapping(value = "/mfBusAddAmtRecord/insert")
	public void insert(@RequestBody  MfBusAddAmtRecord mfBusAddAmtRecord) throws Exception;

	@RequestMapping(value = "/mfBusAddAmtRecord/delete")
	public void delete(@RequestBody  MfBusAddAmtRecord mfBusAddAmtRecord) throws Exception;

	@RequestMapping(value = "/mfBusAddAmtRecord/update")
	public void update(@RequestBody  MfBusAddAmtRecord mfBusAddAmtRecord) throws Exception;

	@RequestMapping(value = "/mfBusAddAmtRecord/getById")
	public MfBusAddAmtRecord getById(@RequestBody  MfBusAddAmtRecord mfBusAddAmtRecord) throws Exception;

	@RequestMapping(value = "/mfBusAddAmtRecord/findByPage")
	public Ipage findByPage(@RequestBody  Ipage ipage) throws Exception;

	@RequestMapping(value = "/mfBusAddAmtRecord/insertBatch")
	public void insertBatch(@RequestBody  String addAmt,@RequestParam("pactNoList") String pactNoList) throws Exception;
	
	
}
