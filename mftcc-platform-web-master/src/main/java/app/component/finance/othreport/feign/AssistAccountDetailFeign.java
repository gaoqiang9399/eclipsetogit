/**
 * Copyright (@RequestBody C) DXHM 版权所有
 * 文件名： AssistAccountDetailBo.java
 * 包名： app.component.finance.othreport.bo
 * 说明：
 * @author 刘争帅
 * @date 2017-2-6 下午5:08:56
 * @version V1.0
 */
package app.component.finance.othreport.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.finance.util.R;
import app.util.toolkit.Ipage;

/**
 * 类名： AssistAccountDetailBo 描述：
 * 
 * @author 刘争帅
 * @date 2017-2-6 下午5:08:56
 *
 *
 */
@FeignClient("mftcc-platform-fiscal")
public interface AssistAccountDetailFeign {

	/**
	 * 方法描述： 获取辅助核算明细数据
	 * 
	 * @param formMap
	 * @return
	 *             List<AssistAccountDetailBean>
	 * @author Javelin
	 * @date 2017-3-4 上午10:18:00
	 */
	@RequestMapping(value = "/assistAccountDetail/getAccountLedgerData", method = RequestMethod.POST)
	public R getAccountLedgerData(@RequestBody Map<String, String> formMap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 获取辅助核算余额数据
	 * 
	 * @param formMap
	 * @return
	 * @throws ServiceException
	 *             List<AssistAccountDetailBean>
	 * @author Javelin
	 * @date 2017-3-4 上午10:21:43
	 */
	@RequestMapping(value = "/assistAccountDetail/getAccountBalanceData", method = RequestMethod.POST)
	public R getAccountBalanceData(@RequestBody Map<String, String> formMap,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 辅助核算余额表：获取数据列表
	 * 
	 * @param ipage
	 * @param formMap
	 * @return
	 * @throws Exception
	 *             Ipage
	 * @author lzshuai
	 * @date 2017-2-13 下午1:51:19
	 */
	@RequestMapping(value = "/assistAccountDetail/findBalanceByPage", method = RequestMethod.POST)
	public R findBalanceByPage(@RequestBody Ipage ipage,@RequestParam("finBooks") String finBooks) throws Exception;

}
