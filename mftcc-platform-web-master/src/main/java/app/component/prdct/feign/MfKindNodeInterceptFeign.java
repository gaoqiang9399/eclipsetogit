package app.component.prdct.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.prdct.entity.MfKindNodeIntercept;
import app.util.toolkit.Ipage;

/**
 * Title: MfKindNodeInterceptBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Jul 04 10:25:37 CST 2017
 **/

@FeignClient("mftcc-platform-factor")
public interface MfKindNodeInterceptFeign {

	@RequestMapping(value = "/mfKindNodeIntercept/insert", method = RequestMethod.POST)
	public Map<String, Object> insert(@RequestBody MfKindNodeIntercept mfKindNodeIntercept) throws Exception;

	@RequestMapping(value = "/mfKindNodeIntercept/delete", method = RequestMethod.POST)
	public Map<String, Object> delete(@RequestBody MfKindNodeIntercept mfKindNodeIntercept) throws Exception;

	@RequestMapping(value = "/mfKindNodeIntercept/update", method = RequestMethod.POST)
	public void update(@RequestBody MfKindNodeIntercept mfKindNodeIntercept) throws Exception;

	@RequestMapping(value = "/mfKindNodeIntercept/getById", method = RequestMethod.POST)
	public MfKindNodeIntercept getById(@RequestBody MfKindNodeIntercept mfKindNodeIntercept) throws Exception;

	@RequestMapping(value = "/mfKindNodeIntercept/findByPage", method = RequestMethod.POST)
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	/**
	 * 方法描述： 获取节点拦截项列表
	 * 
	 * @param mfKindNodeIntercept
	 * @return
	 * @throws Exception
	 *             List<MfKindNodeIntercept>
	 * @author Javelin
	 * @date 2017-7-4 上午11:00:12
	 */
	@RequestMapping(value = "/mfKindNodeIntercept/getMfKindNodeInterceptList", method = RequestMethod.POST)
	public List<MfKindNodeIntercept> getMfKindNodeInterceptList(@RequestBody MfKindNodeIntercept mfKindNodeIntercept)
			throws Exception;

	/**
	 * 
	 * 方法描述：根据类型获取不同类别的未配置风险项列表
	 * 
	 * @param mfKindNodeIntercept
	 * @return
	 * @throws Exception
	 *             List<MfKindNodeIntercept>
	 * @author zhs
	 * @date 2017-7-7 下午12:03:27
	 */
	@RequestMapping(value = "/mfKindNodeIntercept/getRiskInterceptList", method = RequestMethod.POST)
	public List<MfKindNodeIntercept> getRiskInterceptList(@RequestBody MfKindNodeIntercept mfKindNodeIntercept)
			throws Exception;

}
