package app.component.frontview.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.frontview.entity.MfAppCollectItem;
import app.util.toolkit.Ipage;

/**
 * Title: MfAppCollectItemBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Oct 24 12:01:38 CST 2017
 **/

@FeignClient("mftcc-platform-factor")
public interface MfAppCollectItemFeign {

	@RequestMapping("/mfAppCollectItem/insert")
	public void insert(@RequestBody MfAppCollectItem mfAppCollectItem) throws Exception;

	@RequestMapping("/mfAppCollectItem/delete")
	public void delete(@RequestBody MfAppCollectItem mfAppCollectItem) throws Exception;

	@RequestMapping("/mfAppCollectItem/update")
	public void update(@RequestBody MfAppCollectItem mfAppCollectItem) throws Exception;

	@RequestMapping("/mfAppCollectItem/getById")
	public MfAppCollectItem getById(@RequestBody MfAppCollectItem mfAppCollectItem) throws Exception;

	@RequestMapping("/mfAppCollectItem/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("mfAppCollectItem") MfAppCollectItem mfAppCollectItem) throws Exception;

}
