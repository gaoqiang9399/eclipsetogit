package  app.component.calc.penalty.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.calc.penalty.entity.MfSysPenaltyMain;
import app.util.toolkit.Ipage;

/**
* Title: MfSysPenaltyMainBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue Jun 27 09:04:25 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfSysPenaltyMainFeign {
	
	@RequestMapping(value = "/mfSysPenaltyMain/insert")
	public void insert(@RequestBody MfSysPenaltyMain mfSysPenaltyMain) throws Exception;
	
	@RequestMapping(value = "/mfSysPenaltyMain/delete")
	public void delete(@RequestBody MfSysPenaltyMain mfSysPenaltyMain) throws Exception;
	
	@RequestMapping(value = "/mfSysPenaltyMain/update")
	public void update(@RequestBody MfSysPenaltyMain mfSysPenaltyMain) throws Exception;
	
	@RequestMapping(value = "/mfSysPenaltyMain/getById")
	public MfSysPenaltyMain getById(@RequestBody MfSysPenaltyMain mfSysPenaltyMain) throws Exception;
	
	@RequestMapping(value = "/mfSysPenaltyMain/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfSysPenaltyMain") MfSysPenaltyMain mfSysPenaltyMain) throws Exception;
	
	@RequestMapping(value = "/mfSysPenaltyMain/getAllList")
	public List<MfSysPenaltyMain> getAllList(@RequestBody MfSysPenaltyMain mfSysPenaltyMain)  throws Exception;
	
}
