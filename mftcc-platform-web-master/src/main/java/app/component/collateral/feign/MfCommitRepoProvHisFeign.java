package  app.component.collateral.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.collateral.entity.MfCommitRepoProvHis;
import app.util.toolkit.Ipage;

/**
* Title: MfCommitRepoProvHisBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Fri May 05 20:49:02 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface MfCommitRepoProvHisFeign {
	
	@RequestMapping(value = "/mfCommitRepoProvHis/insert")
	public void insert(@RequestBody MfCommitRepoProvHis mfCommitRepoProvHis) throws Exception;
	
	@RequestMapping(value = "/mfCommitRepoProvHis/delete")
	public void delete(@RequestBody MfCommitRepoProvHis mfCommitRepoProvHis) throws Exception;
	
	@RequestMapping(value = "/mfCommitRepoProvHis/update")
	public void update(@RequestBody MfCommitRepoProvHis mfCommitRepoProvHis) throws Exception;
	
	@RequestMapping(value = "/mfCommitRepoProvHis/getById")
	public MfCommitRepoProvHis getById(@RequestBody MfCommitRepoProvHis mfCommitRepoProvHis) throws Exception;
	
	@RequestMapping(value = "/mfCommitRepoProvHis/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfCommitRepoProvHis") MfCommitRepoProvHis mfCommitRepoProvHis) throws Exception;
	
}
