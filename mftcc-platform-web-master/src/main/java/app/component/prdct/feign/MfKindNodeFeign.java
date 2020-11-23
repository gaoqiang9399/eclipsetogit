package app.component.prdct.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import app.component.prdct.entity.MfKindNode;

/**
 * Title: MfKindNodeBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Sun Jul 02 16:48:46 CST 2017
 **/

@FeignClient("mftcc-platform-factor")
public interface MfKindNodeFeign {

	@RequestMapping(value = "/mfKindNode/insert", method = RequestMethod.POST)
	public Map<String, Object> insert(@RequestBody MfKindNode mfKindNode) throws Exception;

	@RequestMapping(value = "/mfKindNode/delete", method = RequestMethod.POST)
	public void delete(@RequestBody MfKindNode mfKindNode) throws Exception;

	@RequestMapping(value = "/mfKindNode/update", method = RequestMethod.POST)
	public Map<String, Object> update(@RequestBody MfKindNode mfKindNode) throws Exception;

	@RequestMapping(value = "/mfKindNode/getById", method = RequestMethod.POST)
	public MfKindNode getById(@RequestBody MfKindNode mfKindNode) throws Exception;

	@RequestMapping(value = "/mfKindNode/getKindNodeList", method = RequestMethod.POST)
	public List<MfKindNode> getKindNodeList(@RequestBody MfKindNode mfKindNode) throws Exception;

	/**
	 * 
	 * 方法描述：获取产品设置页面中的节点的设置挂载情况
	 * 
	 * @param mfKindNode
	 * @return
	 * @throws Exception
	 *             List<MfKindNode>
	 * @author zhs
	 * @date 2017-7-4 下午6:05:24
	 */
	@RequestMapping(value = "/mfKindNode/getNodeSetList", method = RequestMethod.POST)
	public List<MfKindNode> getNodeSetList(@RequestBody MfKindNode mfKindNode) throws Exception;

	/**
	 * 
	 * 方法描述： 根据产品编号获得所有节点
	 * 
	 * @param mfKindNode
	 * @return
	 * @throws Exception
	 *             List<MfKindNode>
	 * @author 沈浩兵
	 * @date 2017-9-8 下午5:54:22
	 */
	@RequestMapping(value = "/mfKindNode/getKindNodeByKindNoList", method = RequestMethod.POST)
	public List<MfKindNode> getKindNodeByKindNoList(@RequestBody MfKindNode mfKindNode) throws Exception;
	/**
	 * 
	 * 方法描述： 产品下费用、影像、模板、表单配置定制节点保存
	 * @param mfKindNode
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author zhs
	 * @date 2018年5月7日 上午11:08:56
	 */
	@RequestMapping(value = "/mfKindNode/insertForPrdct", method = RequestMethod.POST)
	public Map<String, Object> insertForPrdct(@RequestBody MfKindNode mfKindNode) throws Exception;


	@RequestMapping(value = "/mfKindNode/getCreditNodeList", method = RequestMethod.POST)
	public List<MfKindNode> getCreditNodeList(@RequestBody MfKindNode mfKindNode) throws Exception;

	@RequestMapping(value = "/mfKindNode/insertForCredit", method = RequestMethod.POST)
	public Map<String, Object> insertForCredit(@RequestBody MfKindNode mfKindNode) throws Exception;
}
