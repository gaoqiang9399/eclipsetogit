package app.component.assetsmanage.feign;

import app.util.toolkit.Ipage;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.assetsmanage.entity.MfAssetsDisposal;
import app.component.wkf.entity.Result;

/**
 * Title: MfAssetsDisposalBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Sep 27 20:09:45 CST 2018
 **/
@FeignClient("mftcc-platform-factor")
public interface MfAssetsDisposalFeign {
	@RequestMapping(value = "/mfAssetsDisposal/insert")
	public void insert(@RequestBody MfAssetsDisposal mfAssetsDisposal) throws Exception;

	@RequestMapping(value = "/mfAssetsDisposal/delete")
	public void delete(@RequestBody MfAssetsDisposal mfAssetsDisposal) throws Exception;

	@RequestMapping(value = "/mfAssetsDisposal/update")
	public void update(@RequestBody MfAssetsDisposal mfAssetsDisposal) throws Exception;

	@RequestMapping(value = "/mfAssetsDisposal/getById")
	public MfAssetsDisposal getById(@RequestBody MfAssetsDisposal mfAssetsDisposal) throws Exception;

	@RequestMapping(value = "/mfAssetsDisposal/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/mfAssetsDisposal/submitProcess")
	public MfAssetsDisposal submitProcess(@RequestBody MfAssetsDisposal mfAssetsDisposal) throws Exception;

	@RequestMapping(value = "/mfAssetsDisposal/doCommit")
	public Result doCommit(@RequestParam("taskId") String taskId, @RequestParam("transition") String transition,
			@RequestParam("nextUser") String nextUser, @RequestBody Map<String, Object> formDataMap) throws Exception;
	
	@RequestMapping(value = "/mfAssetsDisposal/getByAssetsManageId")
	public List<MfAssetsDisposal> getByAssetsManageId(@RequestBody MfAssetsDisposal mfAssetsDisposal) throws Exception;

}
