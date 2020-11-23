package app.component.finance.paramset.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.finance.paramset.entity.CwVchRuleMstPlate;
import app.util.toolkit.Ipage;

/**
 * Title: CwVchRuleMstPlateBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Mar 07 14:23:55 CST 2017
 **/
@FeignClient("mftcc-platform-fiscal")
public interface CwVchRuleMstPlateFeign {

	@RequestMapping(value = "/cwVchRuleMstPlate/insert", method = RequestMethod.POST)
	public void insert(@RequestBody CwVchRuleMstPlate cwVchRuleMstPlate) throws Exception;

	@RequestMapping(value = "/cwVchRuleMstPlate/delete", method = RequestMethod.POST)
	public void delete(@RequestBody CwVchRuleMstPlate cwVchRuleMstPlate) throws Exception;

	@RequestMapping(value = "/cwVchRuleMstPlate/update", method = RequestMethod.POST)
	public void update(@RequestBody CwVchRuleMstPlate cwVchRuleMstPlate) throws Exception;

	@RequestMapping(value = "/cwVchRuleMstPlate/getById", method = RequestMethod.POST)
	public CwVchRuleMstPlate getById(@RequestBody CwVchRuleMstPlate cwVchRuleMstPlate) throws Exception;

	@RequestMapping(value = "/cwVchRuleMstPlate/findByPage", method = RequestMethod.POST)
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("finBooks") String finBooks) throws Exception;

}
