package app.component.cus.cnaps.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.component.cus.cnaps.entity.MfCnapsBankInfo;
import app.util.toolkit.Ipage;

/**
 * Title: MfCnapsBankInfoBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Dec 07 14:51:12 CST 2017
 **/

@FeignClient("mftcc-platform-factor")
public interface MfCnapsBankInfoFeign {

	@RequestMapping(value = "/mfCnapsBankInfo/insert")
	public void insert(@RequestBody MfCnapsBankInfo mfCnapsBankInfo) throws Exception;

	@RequestMapping(value = "/mfCnapsBankInfo/delete")
	public void delete(@RequestBody MfCnapsBankInfo mfCnapsBankInfo) throws Exception;

	@RequestMapping(value = "/mfCnapsBankInfo/update")
	public void update(@RequestBody MfCnapsBankInfo mfCnapsBankInfo) throws Exception;

	@RequestMapping(value = "/mfCnapsBankInfo/getById")
	public MfCnapsBankInfo getById(@RequestBody MfCnapsBankInfo mfCnapsBankInfo) throws Exception;

	@RequestMapping(value = "/mfCnapsBankInfo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

}
