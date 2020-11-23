package app.component.finance.paramset.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.finance.paramset.entity.CwJitiItem;
import app.util.toolkit.Ipage;

/**
 * Title: CwJitiItemBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Aug 22 09:34:09 CST 2017
 **/
@FeignClient("mftcc-platform-fiscal")
public interface CwJitiItemFeign {

	@RequestMapping(value = "/cwJitiItem/insert", method = RequestMethod.POST)
	public void insert(@RequestBody CwJitiItem cwJitiItem,@RequestParam("finBooks") String finBooks) throws Exception;

	@RequestMapping(value = "/cwJitiItem/delete", method = RequestMethod.POST)
	public void delete(@RequestBody CwJitiItem cwJitiItem,@RequestParam("finBooks") String finBooks) throws Exception;

	@RequestMapping(value = "/cwJitiItem/update", method = RequestMethod.POST)
	public void update(@RequestBody CwJitiItem cwJitiItem,@RequestParam("finBooks") String finBooks) throws Exception;

	@RequestMapping(value = "/cwJitiItem/getById", method = RequestMethod.POST)
	public CwJitiItem getById(@RequestBody CwJitiItem cwJitiItem,@RequestParam("finBooks") String finBooks) throws Exception;

	@RequestMapping(value = "/cwJitiItem/findByPage", method = RequestMethod.POST)
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("finBooks") String finBooks) throws Exception;

}
