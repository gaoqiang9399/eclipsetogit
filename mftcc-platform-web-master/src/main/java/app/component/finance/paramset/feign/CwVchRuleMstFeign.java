package app.component.finance.paramset.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.finance.paramset.entity.CwProofWords;
import app.component.finance.paramset.entity.CwVchRuleDetail;
import app.component.finance.paramset.entity.CwVchRuleMst;
import app.component.finance.paramset.entity.CwVchRuleMstPlate;
import app.util.toolkit.Ipage;

/**
 * Title: CwVchRuleMstBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Mar 08 14:53:10 CST 2017
 **/
@FeignClient("mftcc-platform-fiscal")
public interface CwVchRuleMstFeign {

	@RequestMapping(value = "/cwVchRuleMst/insert", method = RequestMethod.POST)
	public void insert(@RequestBody CwVchRuleMst cwVchRuleMst,@RequestParam("finBooks") String finBooks) throws Exception;

	@RequestMapping(value = "/cwVchRuleMst/delete", method = RequestMethod.POST)
	public void delete(@RequestBody CwVchRuleMst cwVchRuleMst,@RequestParam("finBooks") String finBooks) throws Exception;

	@RequestMapping(value = "/cwVchRuleMst/update", method = RequestMethod.POST)
	public void update(@RequestBody CwVchRuleMst cwVchRuleMst,@RequestParam("finBooks") String finBooks) throws Exception;

	@RequestMapping(value = "/cwVchRuleMst/getById", method = RequestMethod.POST)
	public CwVchRuleMst getById(@RequestBody CwVchRuleMst cwVchRuleMst,@RequestParam("finBooks") String finBooks) throws Exception;

	@RequestMapping(value = "/cwVchRuleMst/findByPage", method = RequestMethod.POST)
	public Ipage findByPage(@RequestBody Ipage ipage,
			@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 获取数据主表的数据
	 * 
	 * @param bean
	 * @return
	 * @throws Exception
	 *             List<CwVchRuleMst>
	 * @author lzshuai
	 * @date 2017-3-9 下午2:11:03
	 */
	@RequestMapping(value = "/cwVchRuleMst/getCwvchRuleMstData", method = RequestMethod.POST)
	public List<CwVchRuleMst> getCwvchRuleMstData(@RequestBody CwVchRuleMst bean,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 获取模板主表的的数据
	 * 
	 * @return
	 * @throws Exception
	 *             List<CwVchRuleMstPlate>
	 * @author lzshuai
	 * @date 2017-3-9 下午3:37:07
	 */
	@RequestMapping(value = "/cwVchRuleMst/getCwRulePlageList", method = RequestMethod.POST)
	public List<CwVchRuleMstPlate> getCwRulePlageList() throws Exception;

	/**
	 * 
	 * 方法描述： 获取凭证字信息
	 * 
	 * @return
	 * @throws Exception
	 *             List<CwProofWords>
	 * @author lzshuai
	 * @date 2017-3-9 下午7:30:55
	 */
	@RequestMapping(value = "/cwVchRuleMst/getProofWordsList", method = RequestMethod.POST)
	public List<CwProofWords> getProofWordsList(@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 获取数据
	 * 
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @author lzshuai
	 * @date 2017-3-10 上午10:27:11
	 */
	@RequestMapping(value = "/cwVchRuleMst/getMapObject", method = RequestMethod.POST)
	public Map<String, Object> getMapObject() throws Exception;

	/**
	 * 
	 * 方法描述： 新增业务凭证记账数据
	 * 
	 * @param formMap
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-3-10 下午6:57:27
	 */
	@RequestMapping(value = "/cwVchRuleMst/addCwVchRuleMstData", method = RequestMethod.POST)
	public String addCwVchRuleMstData(@RequestBody Map<String, Object> formMap,@RequestParam ("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 获取数据分录表的内容
	 * 
	 * @param cwVchdetail
	 * @return
	 * @throws Exception
	 *             List<CwVchRuleDetail>
	 * @author lzshuai
	 * @date 2017-3-13 下午2:46:57
	 */
	@RequestMapping(value = "/cwVchRuleMst/getCwVchRuleDetailList", method = RequestMethod.POST)
	public List<CwVchRuleDetail> getCwVchRuleDetailList(@RequestBody CwVchRuleDetail cwVchdetail,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 修改业务记账的详情信息
	 * 
	 * @param formMap
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-3-14 下午8:15:35
	 */
	@RequestMapping(value = "/cwVchRuleMst/updateCwVchRuleMstData", method = RequestMethod.POST)
	public String updateCwVchRuleMstData(@RequestBody Map<String, Object> formMap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 业务凭证中获取取值的问题
	 * 
	 * @return
	 * @throws Exception
	 *             Map<String,String>
	 * @author lzshuai
	 * @date 2017-4-20 下午2:23:55
	 */
	@RequestMapping(value = "/cwVchRuleMst/getCwPayItems", method = RequestMethod.POST)
	public List<Map<String, String>> getCwPayItems() throws Exception;

	/**
	 * 
	 * 方法描述： 获取模版的内容
	 * 
	 * @param dataMap
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-8-26 下午2:25:14
	 */
	@RequestMapping(value = "/cwVchRuleMst/getRulePlateAjax", method = RequestMethod.POST)
	public Map<String, Object> getRulePlateAjax(@RequestBody Map<String, Object> dataMap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 根据业务id获取产品名称
	 * 
	 * @param dataMap
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-9-2 下午4:31:37
	 */
	@RequestMapping(value = "/cwVchRuleMst/dochangeProduct", method = RequestMethod.POST)
	public Map<String,Object> dochangeProduct(@RequestBody Map<String, Object> dataMap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 获取价税分离的方式
	 * 
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-9-12 下午5:26:45
	 */
	@RequestMapping(value = "/cwVchRuleMst/getMonPriceTaxShow", method = RequestMethod.POST)
	public String getMonPriceTaxShow(@RequestParam("finBooks") String finBooks) throws Exception;

}
