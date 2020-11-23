package app.component.finance.assets.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.finance.assets.entity.CwAssetsClass;
import app.component.finance.util.R;
import app.util.toolkit.Ipage;

/**
 * Title: CwAssetsClassBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Apr 14 17:21:08 CST 2017
 **/
@FeignClient("mftcc-platform-fiscal")
public interface CwAssetsClassFeign {

	@RequestMapping(value = "/cwAssetsClass/insert", method = RequestMethod.POST)
	public R insert(@RequestBody CwAssetsClass cwAssetsClass,@RequestParam("finBooks") String finBooks) throws Exception;

	@RequestMapping(value = "/cwAssetsClass/delete", method = RequestMethod.POST)
	public R delete(@RequestBody CwAssetsClass cwAssetsClass,@RequestParam("finBooks") String finBooks) throws Exception;

	@RequestMapping(value = "/cwAssetsClass/update", method = RequestMethod.POST)
	public void update(@RequestBody CwAssetsClass cwAssetsClass,@RequestParam("finBooks") String finBooks) throws Exception;

	@RequestMapping(value = "/cwAssetsClass/getById", method = RequestMethod.POST)
	public CwAssetsClass getById(@RequestBody CwAssetsClass cwAssetsClass,@RequestParam("finBooks") String finBooks) throws Exception;

	@RequestMapping(value = "/cwAssetsClass/findByPage", method = RequestMethod.POST)
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("finBooks") String finBooks) throws Exception;

}
