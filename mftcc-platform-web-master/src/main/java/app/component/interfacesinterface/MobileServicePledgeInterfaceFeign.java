/**
 * Copyright (C) DXHM 版权所有
 * 文件名： MobileServicePledgeInterface.java
 * 包名： app.component.interfacesinterface
 * 说明：
 * @author 沈浩兵
 * @date 2017-12-28 上午10:38:08
 * @version V1.0
 */ 
package app.component.interfacesinterface;


import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.collateral.entity.PledgeBaseInfo;

/**
 * 类名： MobileServicePledgeInterface
 * 描述：
 * @author 沈浩兵
 * @date 2017-12-28 上午10:38:08
 *
 *
 */
@FeignClient("mftcc-platform-factor")
public interface MobileServicePledgeInterfaceFeign {
	/**
	 * 
	 * 方法描述： 新增客户下押品
	 * @param parmData
	 * @return
	 * @throws Exception
	 * MfCusCustomer
	 * @author 沈浩兵
	 * @date 2017-12-28 上午10:43:52
	 */
	@RequestMapping(value = "/mobileServicePledgeInterface/insertPledgeInfo")
	public Map<String, Object> insertPledgeInfo(@RequestBody PledgeBaseInfo pledgeBaseInfo) throws Exception;
	/**
	 * 
	 * 方法描述： 修改押品信息
	 * @param parmData
	 * @return
	 * @throws Exception
	 * MfCusCustomer
	 * @author 沈浩兵
	 * @date 2017-12-28 上午11:37:36
	 */
	@RequestMapping(value = "/mobileServicePledgeInterface/updatePledgeInfo")
	public Map<String, Object> updatePledgeInfo(@RequestBody PledgeBaseInfo pledgeBaseInfo) throws Exception;
	/**
	 * 
	 * 方法描述： 删除押品信息
	 * @param pledgeNo
	 * @return
	 * @throws Exception
	 * MfCusCustomer
	 * @author 沈浩兵
	 * @date 2017-12-28 上午11:38:06
	 */
	@RequestMapping(value = "/mobileServicePledgeInterface/deletePledgeInfo")
	public Map<String, Object> deletePledgeInfo(@RequestBody String pledgeNo) throws Exception;
	/**
	 * 
	 * 方法描述： 根据押品编号获得押品详情
	 * @param pledgeNo
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-12-28 上午11:39:28
	 */
	@RequestMapping(value = "/mobileServicePledgeInterface/getPledgeInfoByPledgeNo")
	public Map<String, Object> getPledgeInfoByPledgeNo(@RequestBody String pledgeNo) throws Exception;
	/**
	 * 
	 * 方法描述： 根据获得客户号获得押品下的所有押品
	 * @param cusNo
	 * @param classId
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-12-28 上午11:40:26
	 */
	@RequestMapping(value = "/mobileServicePledgeInterface/getPledgeInfoListByCusNo")
	public Map<String, Object> getPledgeInfoListByCusNo(@RequestBody String cusNo,@RequestParam("classId") String classId) throws Exception;
	/**
	 * 
	 * 方法描述： 根据规则和客户号获得押品展示编号
	 * @param pledgeBaseInfo
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 张冬磊
	 * @date 2017-12-28 上午11:40:26
	 */
	@RequestMapping(value = "/mobileServicePledgeInterface/getPledgeShowNo")
	public Map<String, Object> getPledgeShowNo(@RequestBody PledgeBaseInfo pledgeBaseInfo) throws Exception;
}
