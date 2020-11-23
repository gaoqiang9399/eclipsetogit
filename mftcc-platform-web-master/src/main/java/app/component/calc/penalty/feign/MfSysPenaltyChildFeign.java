package  app.component.calc.penalty.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.calc.penalty.entity.MfSysPenaltyChild;
import app.util.toolkit.Ipage;

/**
* Title: MfSysPenaltyChildBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat Jul 01 17:27:43 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfSysPenaltyChildFeign {
	
	@RequestMapping(value = "/mfSysPenaltyChild/insert")
	public void insert(@RequestBody MfSysPenaltyChild mfSysPenaltyChild) throws Exception;
	
	@RequestMapping(value = "/mfSysPenaltyChild/delete")
	public void delete(@RequestBody MfSysPenaltyChild mfSysPenaltyChild) throws Exception;
	
	@RequestMapping(value = "/mfSysPenaltyChild/update")
	public void update(@RequestBody MfSysPenaltyChild mfSysPenaltyChild) throws Exception;
	
	@RequestMapping(value = "/mfSysPenaltyChild/getById")
	public MfSysPenaltyChild getById(@RequestBody MfSysPenaltyChild mfSysPenaltyChild) throws Exception;
	
	@RequestMapping(value = "/mfSysPenaltyChild/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfSysPenaltyChild") MfSysPenaltyChild mfSysPenaltyChild) throws Exception;
	
	@RequestMapping(value = "/mfSysPenaltyChild/getPenaltyChildList")
	public List<MfSysPenaltyChild> getPenaltyChildList(@RequestBody MfSysPenaltyChild mfSysPenaltyChild) throws Exception;
	
	@RequestMapping(value = "/mfSysPenaltyChild/getPenaltyChildListPage")
	public Ipage  getPenaltyChildListPage(@RequestBody Ipage ipg,@RequestParam("paramMap")  Map<String, String> paramMap) throws ServiceException;
	
}
