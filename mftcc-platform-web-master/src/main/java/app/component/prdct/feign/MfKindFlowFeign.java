package app.component.prdct.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.prdct.entity.MfKindFlow;
import app.util.toolkit.Ipage;
import net.sf.json.JSONArray;

/**
 * Title: MfKindFlowBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Jun 30 11:02:44 CST 2017
 **/

@FeignClient("mftcc-platform-factor")
public interface MfKindFlowFeign {

	@RequestMapping(value = "/mfKindFlow/insert", method = RequestMethod.POST)
	public void insert(@RequestBody MfKindFlow mfKindFlow) throws Exception;

	@RequestMapping(value = "/mfKindFlow/delete", method = RequestMethod.POST)
	public void delete(@RequestBody MfKindFlow mfKindFlow) throws Exception;

	@RequestMapping(value = "/mfKindFlow/update", method = RequestMethod.POST)
	public void update(@RequestBody MfKindFlow mfKindFlow) throws Exception;

	@RequestMapping(value = "/mfKindFlow/getById", method = RequestMethod.POST)
	public MfKindFlow getById(@RequestBody MfKindFlow mfKindFlow) throws Exception;

	@RequestMapping(value = "/mfKindFlow/findByPage", method = RequestMethod.POST)
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("mfKindFlow") MfKindFlow mfKindFlow) throws Exception;

	/**
	 * 
	 * 方法描述： 获取产品下的审批流程列表
	 * 
	 * @param mfKindFlow
	 * @return
	 * @throws Exception
	 *             List<MfKindFlow>
	 * @author zhs
	 * @date 2017-7-5 下午7:25:50
	 */
	@RequestMapping(value = "/mfKindFlow/getKindFlowList", method = RequestMethod.POST)
	public List<MfKindFlow> getKindFlowList(@RequestBody MfKindFlow mfKindFlow) throws Exception;

	/**
	 * 
	 * 方法描述：
	 * 
	 * @return
	 * @throws Exception
	 *             JSONArray
	 * @author zhs
	 * @date 2017-8-9 下午3:08:33
	 */
	@RequestMapping(value = "/mfKindFlow/getFlowNodeArray", method = RequestMethod.POST)
	public JSONArray getFlowNodeArray() throws Exception;

}
