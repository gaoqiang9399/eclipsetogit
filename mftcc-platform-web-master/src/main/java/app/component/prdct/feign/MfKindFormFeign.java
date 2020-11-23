package app.component.prdct.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.common.BizPubParm.WKF_NODE;
import app.component.prdct.entity.MfKindForm;
import app.util.toolkit.Ipage;

/**
 * Title: MfKindFormBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Sat Jul 01 12:01:13 CST 2017
 **/

@FeignClient("mftcc-platform-factor")
public interface MfKindFormFeign {

	@RequestMapping(value = "/mfKindForm/insert", method = RequestMethod.POST)
	public void insert(@RequestBody MfKindForm mfKindForm) throws Exception;

	@RequestMapping(value = "/mfKindForm/delete", method = RequestMethod.POST)
	public void delete(@RequestBody MfKindForm mfKindForm) throws Exception;

	@RequestMapping(value = "/mfKindForm/update", method = RequestMethod.POST)
	public void update(@RequestBody MfKindForm mfKindForm) throws Exception;

	@RequestMapping(value = "/mfKindForm/getById", method = RequestMethod.POST)
	public MfKindForm getById(@RequestBody MfKindForm mfKindForm) throws Exception;

	@RequestMapping(value = "/mfKindForm/findByPage", method = RequestMethod.POST)
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("mfKindForm") MfKindForm mfKindForm) throws Exception;

	/**
	 * 方法描述： 获取配置的表单
	 * 
	 * @param kindNo
	 *            产品种类编号
	 * @param nodeNo
	 *            节点编号
	 * @return
	 * @throws Exception
	 *             List<MfKindForm>
	 * @author YuShuai
	 * @date 2017-7-1 下午3:25:42
	 */
	@RequestMapping(value = "/mfKindForm/getInitKindFormList", method = RequestMethod.POST)
	public List<MfKindForm> getInitKindFormList(@RequestBody String kindNo, @RequestParam("nodeNo") String nodeNo, @RequestParam("defFalg") String defFalg)
			throws Exception;

	/**
	 * 方法描述： 获取单个表单的Id
	 * 
	 * @param kindNo
	 * @param node
	 * @param appId
	 * @param fincId
	 * @return String
	 * @author YuShuai
	 * @date 2017-7-11 上午10:57:59
	 */
	@RequestMapping(value = "/mfKindForm/getFormId", method = RequestMethod.POST)
	public String getFormId(@RequestParam("kindNo") String kindNo, @RequestBody WKF_NODE node, @RequestParam("appId") String appId, @RequestParam("fincId") String fincId,@RequestParam("regNo") String regNo) throws Exception;

	/**
	 * 方法描述： 获取单个配置的表单
	 * 
	 * @param kindNo
	 *            产品种类编号
	 * @param nodeNo
	 *            节点编号
	 * @return
	 * @throws Exception
	 *             MfKindForm
	 * @author YuShuai
	 * @date 2017-7-1 下午3:25:42
	 */
	@RequestMapping(value = "/mfKindForm/getInitKindForm", method = RequestMethod.POST)
	public MfKindForm getInitKindForm(@RequestBody String kindNo, @RequestParam("nodeNo") String nodeNo, @RequestParam("defFalg") String defFalg) throws Exception;

	/**
	 * 方法描述：
	 * 
	 * @param mfKindForm
	 * @return
	 * @throws Exception
	 *             MfKindForm
	 * @author YuShuai
	 * @date 2017-11-23 下午7:20:41
	 */
	@RequestMapping(value = "/mfKindForm/getKindForm", method = RequestMethod.POST)
	public MfKindForm getKindForm(@RequestBody MfKindForm mfKindForm) throws Exception;

}
