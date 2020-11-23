package app.component.finance.paramset.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import app.component.finance.paramset.entity.CwVchRuleDetailPlate;
import app.util.toolkit.Ipage;

/**
 * Title: CwVchRuleDetailPlateBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Mar 09 20:13:26 CST 2017
 **/
@FeignClient("mftcc-platform-fiscal")
public interface CwVchRuleDetailPlateFeign {

	@RequestMapping(value = "/cwVchRuleDetailPlate/insert", method = RequestMethod.POST)
	public void insert(@RequestBody CwVchRuleDetailPlate cwVchRuleDetailPlate) throws Exception;

	@RequestMapping(value = "/cwVchRuleDetailPlate/delete", method = RequestMethod.POST)
	public void delete(@RequestBody CwVchRuleDetailPlate cwVchRuleDetailPlate) throws Exception;

	@RequestMapping(value = "/cwVchRuleDetailPlate/update", method = RequestMethod.POST)
	public void update(@RequestBody CwVchRuleDetailPlate cwVchRuleDetailPlate) throws Exception;

	@RequestMapping(value = "/cwVchRuleDetailPlate/getById", method = RequestMethod.POST)
	public CwVchRuleDetailPlate getById(@RequestBody CwVchRuleDetailPlate cwVchRuleDetailPlate) throws Exception;

	@RequestMapping(value = "/cwVchRuleDetailPlate/findByPage", method = RequestMethod.POST)
	public Ipage findByPage(@RequestBody Ipage ipage)
			throws Exception;

}
