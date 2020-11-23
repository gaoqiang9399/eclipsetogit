package app.component.lawsuit.feign;

import app.base.ServiceException;
import app.component.lawsuit.entity.MfLawsuitFollow;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import app.component.lawsuit.entity.MfLawsuitPerformReg;


import java.util.List;

/**
 * Title: MfLawsuitFollowBo.java
 * Description:
 * @author:kaifa@dhcc.com.cn
 * @Wed Feb 22 21:21:30 CST 2017
 **/

@FeignClient("mftcc-platform-factor")
public interface MfLawsuitPerformRegFeign {

	@RequestMapping(value = "/mfLawsuitPerformReg/insert")
	public void insert(@RequestBody MfLawsuitPerformReg mfLawsuitPerformReg) throws ServiceException;

	@RequestMapping(value = "/mfLawsuitPerformReg/delete")
	public void delete(@RequestBody MfLawsuitPerformReg mfLawsuitPerformReg) throws ServiceException;

	@RequestMapping(value = "/mfLawsuitPerformReg/update")
	public void update(@RequestBody MfLawsuitPerformReg mfLawsuitPerformReg) throws ServiceException;

	@RequestMapping(value = "/mfLawsuitPerformReg/getById")
	public MfLawsuitPerformReg getById(@RequestBody MfLawsuitPerformReg mfLawsuitPerformReg) throws ServiceException;

	@RequestMapping(value = "/mfLawsuitPerformReg/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping(value = "/mfLawsuitPerformReg/getPerformRegList")
	public List<MfLawsuitPerformReg> getPerformRegList(@RequestBody MfLawsuitPerformReg mfLawsuitPerformReg)throws ServiceException;
}
