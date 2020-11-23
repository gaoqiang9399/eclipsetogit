package app.component.ncfgroup.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.ncfgroup.entity.BizWhitelist;
import app.util.toolkit.Ipage;

/**
 * Title: BizWhitelistBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Dec 14 12:28:45 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface BizWhitelistFeign {

	@RequestMapping("/bizWhitelist/insert")
	public void insert(@RequestBody BizWhitelist bizWhitelist) throws Exception;

	@RequestMapping("/bizWhitelist/delete")
	public void delete(@RequestBody BizWhitelist bizWhitelist) throws Exception;

	@RequestMapping("/bizWhitelist/update")
	public void update(@RequestBody BizWhitelist bizWhitelist) throws Exception;

	@RequestMapping("/bizWhitelist/getByCusTelAndKindNo")
	public Map<String, Object> getByCusTelAndKindNo(@RequestBody BizWhitelist bizWhitelist) throws Exception;

	@RequestMapping("/bizWhitelist/getByConditions")
	public BizWhitelist getByConditions(@RequestBody BizWhitelist bizWhitelist) throws Exception;

	@RequestMapping("/bizWhitelist/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("bizWhitelist") BizWhitelist bizWhitelist) throws Exception;

}
