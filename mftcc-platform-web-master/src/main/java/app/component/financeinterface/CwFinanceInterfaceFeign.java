/**
 * Copyright (@RequestBody C) DXHM 版权所有
 * 文件名： CwFinanceInterface.java
 * 包名： app.component.financeinterface
 * 说明：
 * @author lzshuai
 * @date 2017-8-11 下午3:30:09
 * @version V1.0
 */ 
package app.component.financeinterface;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import app.component.finance.voucher.entity.CwReviewBusiness;

/**
 * 类名： CwFinanceInterface
 * 描述：财务对外接口，用于获取复核数据
 * @author lzshuai
 * @date 2017-8-11 下午3:30:09
 *
 *
 */
@FeignClient("mftcc-platform-fiscal")
public interface CwFinanceInterfaceFeign {
	
	/**
	 * 
	 * 方法描述： 插入一条复核数据
	 * @param reviewbean
	 * @return
	 * String
	 * @author lzshuai
	 * @date 2017-8-11 下午3:43:22
	 */
	@RequestMapping(value = "/cwFinanceInterface/insertCwReviewBusinessBean", method = RequestMethod.POST)
	public String insertCwReviewBusinessBean(@RequestBody CwReviewBusiness reviewbean) throws Exception;
	/**
	 * 
	 * 方法描述： 批量插入复核数据
	 * @param reviewList
	 * @return
	 * @throws Exception
	 * String
	 * @author lzshuai
	 * @date 2017-8-11 下午3:46:19
	 */
	@RequestMapping(value = "/cwFinanceInterface/insertBatchRevicwBuinessList", method = RequestMethod.POST)
	public String insertBatchRevicwBuinessList(@RequestBody List<CwReviewBusiness> reviewList) throws Exception;
	
}
