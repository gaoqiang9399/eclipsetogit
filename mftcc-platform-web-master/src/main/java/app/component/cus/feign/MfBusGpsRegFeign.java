package  app.component.cus.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.entity.MfBusGpsReg;
import app.util.toolkit.Ipage;

/**
* Title: MfBusGpsRegBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Mon Dec 11 14:05:05 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfBusGpsRegFeign {
	
	@RequestMapping(value = "/mfBusGpsReg/insert")
	public void insert(@RequestBody MfBusGpsReg mfBusGpsReg) throws Exception;
	
	@RequestMapping(value = "/mfBusGpsReg/insertNoSubmit")
	public void insertNoSubmit(@RequestBody MfBusGpsReg mfBusGpsReg) throws Exception;
	
	@RequestMapping(value = "/mfBusGpsReg/delete")
	public void delete(@RequestBody MfBusGpsReg mfBusGpsReg) throws Exception;
	
	@RequestMapping(value = "/mfBusGpsReg/update")
	public void update(@RequestBody MfBusGpsReg mfBusGpsReg) throws Exception;
	
	@RequestMapping(value = "/mfBusGpsReg/getById")
	public MfBusGpsReg getById(@RequestBody MfBusGpsReg mfBusGpsReg) throws Exception;
	
	@RequestMapping(value = "/mfBusGpsReg/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
	@RequestMapping(value = "/mfBusGpsReg/validateGpsInfo")
	public int validateGpsInfo(@RequestBody MfBusGpsReg mfBusGpsReg) throws Exception;

	@RequestMapping(value = "/mfBusGpsReg/getListById")
	public List<MfBusGpsReg> getListById(@RequestBody MfBusGpsReg mfBusGpsReg) throws Exception;
	
	@RequestMapping(value = "/mfBusGpsReg/getMfBusGpsRegList")
	public List<MfBusGpsReg> getMfBusGpsRegList(@RequestBody MfBusGpsReg mfBusGpsReg) throws Exception;
}
