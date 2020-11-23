package app.component.param.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.param.	entity.ParmCorpInfo;
import app.component.sys.entity.SysOrg;
import app.util.toolkit.Ipage;

/**
 * Title: ParmCorpInfoBoImplImpl.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Thu Aug 11 05:40:43 GMT 2016
 **/
@FeignClient("mftcc-platform-factor")
public interface ParmCorpInfoFeign {

	@RequestMapping(value = "/parmCorpInfo/insert")
	public void insert(@RequestBody ParmCorpInfo parmCorpInfo) throws Exception;

	@RequestMapping(value = "/parmCorpInfo/delete")
	public void delete(@RequestBody ParmCorpInfo parmCorpInfo) throws Exception;

	@RequestMapping(value = "/parmCorpInfo/update")
	public void update(@RequestBody ParmCorpInfo parmCorpInfo) throws Exception;

	@RequestMapping(value = "/parmCorpInfo/getById")
	public ParmCorpInfo getById(@RequestBody ParmCorpInfo parmCorpInfo) throws Exception;

	@RequestMapping(value = "/parmCorpInfo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("parmCorpInfo") ParmCorpInfo parmCorpInfo) throws Exception;

	@RequestMapping(value = "/parmCorpInfo/getByOrgNo")
	public ParmCorpInfo getByOrgNo(@RequestBody SysOrg sysOrg) throws Exception;

}
