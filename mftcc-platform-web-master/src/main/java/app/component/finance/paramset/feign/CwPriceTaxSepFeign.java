package app.component.finance.paramset.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.finance.paramset.entity.CwPriceTaxSep;
import app.component.finance.util.R;
import app.util.toolkit.Ipage;

/**
 * Title: CwPriceTaxSepBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Sep 06 14:41:38 CST 2017
 **/
@FeignClient("mftcc-platform-fiscal")
public interface CwPriceTaxSepFeign {

	@RequestMapping(value = "/cwPriceTaxSep/insert", method = RequestMethod.POST)
	public void insert(@RequestBody CwPriceTaxSep cwPriceTaxSep) throws Exception;

	@RequestMapping(value = "/cwPriceTaxSep/delete", method = RequestMethod.POST)
	public R delete(@RequestBody CwPriceTaxSep cwPriceTaxSep,@RequestParam("finBooks") String finBooks) throws Exception;

	@RequestMapping(value = "/cwPriceTaxSep/update", method = RequestMethod.POST)
	public void update(@RequestBody CwPriceTaxSep cwPriceTaxSep) throws Exception;

	@RequestMapping(value = "/cwPriceTaxSep/getById", method = RequestMethod.POST)
	public CwPriceTaxSep getById(@RequestBody CwPriceTaxSep cwPriceTaxSep) throws Exception;

	@RequestMapping(value = "/cwPriceTaxSep/findByPage", method = RequestMethod.POST)
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 获取财务基础信息
	 * 
	 * @param dataMap
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @author lzshuai
	 * @date 2017-9-7 上午11:00:26
	 */
	@RequestMapping(value = "/cwPriceTaxSep/getCwMessageData", method = RequestMethod.POST)
	public Map<String, Object> getCwMessageData(@RequestBody Map<String, Object> dataMap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述：
	 * 
	 * @param dataMap
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @author lzshuai
	 * @date 2017-9-7 下午3:40:47
	 */
	@RequestMapping(value = "/cwPriceTaxSep/getAccAmtByWeeks", method = RequestMethod.POST)
	public Map<String, Object> getAccAmtByWeeks(@RequestBody Map<String, Object> dataMap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 获取金额的总和
	 * 
	 * @param formMap
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @author lzshuai
	 * @date 2017-9-7 下午8:29:54
	 */
	@RequestMapping(value = "/cwPriceTaxSep/addCalTotalAmt", method = RequestMethod.POST)
	public Map<String, Object> addCalTotalAmt(@RequestBody Map<String, Object> formMap) throws Exception;

	/**
	 * 
	 * 方法描述： 保存按月价税的功能
	 * 
	 * @param formMap
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @author lzshuai
	 * @date 2017-9-8 上午9:57:15
	 */
	@RequestMapping(value = "/cwPriceTaxSep/insertCwPriceTaxData", method = RequestMethod.POST)
	public R insertCwPriceTaxData(@RequestBody Map<String, Object> formMap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 获取价税分离的数据
	 * 
	 * @param dataMap
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @author lzshuai
	 * @date 2017-9-8 下午4:15:55
	 */
	@RequestMapping(value = "/cwPriceTaxSep/getPriceTaxDataByUid", method = RequestMethod.POST)
	public Map<String, Object> getPriceTaxDataByUid(@RequestBody Map<String, Object> dataMap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述：获取价税分离功能是否显示 
	 * @return
	 * @throws Exception
	 * String
	 * @author lzshuai
	 * @date 2017-9-12 下午5:07:18
	 */
	@RequestMapping(value = "/cwPriceTaxSep/getMonPriceTaxShow", method = RequestMethod.POST)
	public String getMonPriceTaxShow(@RequestParam("finBooks") String finBooks) throws Exception;

}
