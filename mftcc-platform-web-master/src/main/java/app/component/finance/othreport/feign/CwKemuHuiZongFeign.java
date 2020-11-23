/**
 * Copyright (C) DXHM 版权所有
 * 文件名： CwKemuHuiZongBo.java
 * 包名： app.component.finance.othreport.bo
 * 说明：
 * @author 刘争帅
 * @date 2017-1-10 下午12:18:36
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
import app.util.toolkit.Ipage;

/**
 * 类名： CwKemuHuiZongBo
 * 描述：
 * @author 刘争帅
 * @date 2017-1-10 下午12:18:36
 *
 *
 */
@FeignClient("mftcc-platform-fiscal")
public interface CwKemuHuiZongFeign {

	/**
	 * 
	 * 方法描述： 科目汇总表获取列表数据
	 * @param ipage
	 * @param cwKemuHuiZong
	 * @return
	 * Ipage
	 * @author 刘争帅
	 * @date 2017-1-10 下午4:52:49
	 */
	@RequestMapping(value = "/cwKemuHuiZong/findByPage", method = RequestMethod.POST)
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("finBooks") String finBooks) throws Exception;
	/**
	 * 
	 * 方法描述：根据条件 获取凭证总张数，附件总张数
	 * @param formMap
	 * @return
	 * @throws ServiceException
	 * Map<String,String>
	 * @author 刘争帅
	 * @date 2017-1-13 下午3:18:56
	 */
	@RequestMapping(value = "/cwKemuHuiZong/getMapBymap", method = RequestMethod.POST)
	public Map<String, String> getMapBymap(@RequestBody Map<String, String> formMap,@RequestParam("finBooks") String finBooks)throws Exception;

}
