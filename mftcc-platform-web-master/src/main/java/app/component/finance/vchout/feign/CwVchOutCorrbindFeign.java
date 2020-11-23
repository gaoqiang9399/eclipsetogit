package app.component.finance.vchout.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.finance.vchout.entity.CwVchOutCorrbind;
import app.util.toolkit.Ipage;

/**
 * Title: CwVchOutCorrbindBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Dec 18 17:40:34 CST 2017
 **/
@FeignClient("mftcc-platform-fiscal")
public interface CwVchOutCorrbindFeign {

	@RequestMapping(value = "/cwVchOutCorrbind/insert", method = RequestMethod.POST)
	public void insert(@RequestBody CwVchOutCorrbind cwVchOutCorrbind) throws Exception;

	@RequestMapping(value = "/cwVchOutCorrbind/delete", method = RequestMethod.POST)
	public void delete(@RequestBody CwVchOutCorrbind cwVchOutCorrbind) throws Exception;

	@RequestMapping(value = "/cwVchOutCorrbind/update", method = RequestMethod.POST)
	public void update(@RequestBody CwVchOutCorrbind cwVchOutCorrbind) throws Exception;

	@RequestMapping(value = "/cwVchOutCorrbind/getById", method = RequestMethod.POST)
	public CwVchOutCorrbind getById(@RequestBody CwVchOutCorrbind cwVchOutCorrbind) throws Exception;

	@RequestMapping(value = "/cwVchOutCorrbind/findByPage", method = RequestMethod.POST)
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	/**
	 * 方法描述： 检查并同步
	 * 
	 * @throws Exception
	 *             void
	 * @author Javelin
	 * @date 2017-12-23 上午9:54:01
	 */
	@RequestMapping(value = "/cwVchOutCorrbind/checkCorrTypeCounts", method = RequestMethod.POST)
	public void checkCorrTypeCounts(@RequestParam ("finBooks") String finBooks) throws Exception;

	@RequestMapping(value = "/cwVchOutCorrbind/updateCorrBind", method = RequestMethod.POST)
	public void updateCorrBind(@RequestBody CwVchOutCorrbind cwVchOutCorrbind,@RequestParam("finBooks") String finBooks) throws Exception;

	@RequestMapping(value = "/cwVchOutCorrbind/getCorrBindList", method = RequestMethod.POST)
	public Ipage getCorrBindList(@RequestBody Ipage ipage, @RequestParam("ajaxData")String ajaxData,
			@RequestParam("finBooks") String finBooks)
			throws Exception;
}
