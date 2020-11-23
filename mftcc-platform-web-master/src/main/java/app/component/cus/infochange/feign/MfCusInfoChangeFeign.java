package  app.component.cus.infochange.feign;

import app.component.cus.infochange.entity.MfCusInfoChangeChild;
import app.util.toolkit.Ipage;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.cus.infochange.entity.MfCusInfoChange;
import app.component.wkf.entity.Result;

/**
 * 类名： MfCusInfoChangeFeign
 * 描述：客户信息变更申请
 * @author 仇招
 * @date 2018年5月29日 上午11:18:43
 */
@FeignClient("mftcc-platform-factor")
public interface MfCusInfoChangeFeign {
	
	@RequestMapping("/mfCusInfoChange/insert")
	public void insert(@RequestBody MfCusInfoChange mfCusInfoChange) throws Exception;
	
	@RequestMapping("/mfCusInfoChange/delete")
	public void delete(@RequestBody MfCusInfoChange mfCusInfoChange) throws Exception;
	
	@RequestMapping("/mfCusInfoChange/update")
	public void update(@RequestBody MfCusInfoChange mfCusInfoChange) throws Exception;
	
	@RequestMapping("/mfCusInfoChange/getById")
	public MfCusInfoChange getById(@RequestBody MfCusInfoChange mfCusInfoChange) throws Exception;
	
	@RequestMapping("/mfCusInfoChange/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
	@RequestMapping("/mfCusInfoChange/submitProcess")
	public MfCusInfoChange submitProcess(@RequestBody MfCusInfoChange mfCusInfoChange)throws Exception;

	@RequestMapping("/mfCusInfoChange/doCommit")
	public Result doCommit(@RequestParam("taskId") String taskId, @RequestParam("transition") String transition,@RequestParam("nextUser") String nextUser,@RequestBody Map<String, Object> formDataMap);

	@RequestMapping("/mfCusInfoChange/getIfHaveUnfinish")
	public String getIfHaveUnfinish(@RequestBody MfCusInfoChange mfCusInfoChange)throws Exception;

	@RequestMapping("/mfCusInfoChange/fieldUpdateList")
	public List<MfCusInfoChangeChild> fieldUpdateList(@RequestBody MfCusInfoChangeChild mfCusInfoChangeChild)throws Exception;

	@RequestMapping("/mfCusInfoChange/getMfCusInfoChangeList")
	public List<MfCusInfoChange> getMfCusInfoChangeList(@RequestBody MfCusInfoChange mfCusInfoChange)throws Exception;
}
