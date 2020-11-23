package app.component.finance.paramset.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.finance.paramset.entity.CwJiti;
import app.component.finance.util.R;
import app.util.toolkit.Ipage;

/**
 * Title: CwJitiBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Aug 22 09:31:32 CST 2017
 **/
@FeignClient("mftcc-platform-fiscal")
public interface CwJitiFeign {

	@RequestMapping(value = "/cwJiti/insert", method = RequestMethod.POST)
	public void insert(@RequestBody CwJiti cwJiti,@RequestParam("finBooks") String finBooks) throws Exception;

	@RequestMapping(value = "/cwJiti/delete", method = RequestMethod.POST)
	public R delete(@RequestBody CwJiti cwJiti,@RequestParam("finBooks") String finBooks) throws Exception;

	@RequestMapping(value = "/cwJiti/update", method = RequestMethod.POST)
	public void update(@RequestBody CwJiti cwJiti,@RequestParam("finBooks") String finBooks) throws Exception;

	@RequestMapping(value = "/cwJiti/getById", method = RequestMethod.POST)
	public CwJiti getById(@RequestBody CwJiti cwJiti,@RequestParam("finBooks") String finBooks) throws Exception;

	@RequestMapping(value = "/cwJiti/findByPage", method = RequestMethod.POST)
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 获取计提新增页面中需要的信息
	 * 
	 * @param dataMap
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @author lzshuai
	 * @date 2017-8-22 下午2:41:01
	 */
	@RequestMapping(value = "/cwJiti/getJiTiMessage", method = RequestMethod.POST)
	public Map<String, Object> getJiTiMessage(@RequestBody Map<String, Object> dataMap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 获取昨日余额
	 * 
	 * @param accNo
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-8-22 下午8:03:32
	 */
	@RequestMapping(value = "/cwJiti/getkemuBalByAccno", method = RequestMethod.POST)
	public String getkemuBalByAccno(@RequestBody String accNo,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 金额乘以比例
	 * 
	 * @param resBal
	 * @param bili
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-8-23 上午11:02:22
	 */
	@RequestMapping(value = "/cwJiti/getRestJisuanAmt", method = RequestMethod.POST)
	public R getRestJisuanAmt(@RequestBody String resBal, @RequestParam("bili") String bili, @RequestParam("upJiTiBal") String upJiTiBal)
			throws Exception;

	/**
	 * 
	 * 方法描述： 获取合计的金额
	 * 
	 * @param zcResAmt
	 * @param gzResAmt
	 * @param cjResAmt
	 * @param kyResAmt
	 * @param ssResAmt
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-8-23 上午11:42:42
	 */
	@RequestMapping(value = "/cwJiti/getHeJiAmt", method = RequestMethod.POST)
	public Map<String, String> getHeJiAmt(@RequestBody String zcResAmt, @RequestParam("gzResAmt") String gzResAmt, @RequestParam("cjResAmt") String cjResAmt,
			@RequestParam("kyResAmt") String kyResAmt, @RequestParam("ssResAmt") String ssResAmt, @RequestParam("daibal") String daibal) throws Exception;

	/**
	 * 
	 * 方法描述： 保存计提数据
	 * 
	 * @param formMap
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-8-23 下午7:11:27
	 */
	@RequestMapping(value = "/cwJiti/insertJiTiData", method = RequestMethod.POST)
	public R insertJiTiData(@RequestBody Map<String, Object> formMap,@RequestParam("finBooks") String finBooks) throws Exception;

}
