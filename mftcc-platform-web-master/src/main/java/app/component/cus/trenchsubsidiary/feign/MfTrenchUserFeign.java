/**
 * Copyright (C) DXHM 版权所有
 * 文件名： MfTrenchUserBo.java
 * 包名： app.component.cus.trenchsubsidiary.bo
 * 说明：
 * @author 沈浩兵
 * @date 2018-3-7 下午2:08:33
 * @version V1.0
 */
package app.component.cus.trenchsubsidiary.feign;

import java.util.List;
import java.util.Map;

import app.component.doc.entity.DocBizManageParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.interfaces.mobileinterface.entity.WebCusLineReg;

/**
 * 类名： MfTrenchUserBo 描述：
 * 
 * @author 沈浩兵
 * @date 2018-3-7 下午2:08:33
 */
@FeignClient("mftcc-platform-factor")
public interface MfTrenchUserFeign {
	/**
	 * 方法描述： 插入客户注册登录信息
	 * 
	 * @param webCusLineReg
	 * @return
	 * @throws Exception WebCusLineReg
	 * @author 沈浩兵
	 * @date 2018-3-7 下午2:02:15
	 */
	@RequestMapping(value = "/mfTrenchUser/insertCusLineReg")
	public WebCusLineReg insertCusLineReg(@RequestBody WebCusLineReg webCusLineReg) throws Exception;

	/**
	 * 方法描述： 修改客户注册登录信息
	 * 
	 * @param webCusLineReg
	 * @return
	 * @throws Exception WebCusLineReg
	 * @author 沈浩兵
	 * @date 2018-3-7 下午2:02:54
	 */
	@RequestMapping(value = "/mfTrenchUser/updateCusLineReg")
	public WebCusLineReg updateCusLineReg(@RequestBody WebCusLineReg webCusLineReg) throws Exception;

	/**
	 * 方法描述： 删除客户注册登录信息
	 * 
	 * @param webCusLineReg
	 * @throws Exception void
	 * @author 沈浩兵
	 * @date 2018-3-7 下午2:03:00
	 */
	@RequestMapping(value = "/mfTrenchUser/deteleCusLineReg")
	public void deteleCusLineReg(@RequestBody WebCusLineReg webCusLineReg) throws Exception;

	/**
	 * 方法描述： 根据id获得客户注册登录信息
	 * 
	 * @param webCusLineReg
	 * @return
	 * @throws Exception WebCusLineReg
	 * @author 沈浩兵
	 * @date 2018-3-7 下午3:28:05
	 */
	@RequestMapping(value = "/mfTrenchUser/getCusLineReg")
	public WebCusLineReg getCusLineReg(@RequestBody WebCusLineReg webCusLineReg) throws Exception;

	/**
	 * 方法描述： 获得客户注册登录信息列表数据
	 * 
	 * @param webCusLineReg
	 * @return
	 * @throws Exception List<WebCusLineReg>
	 * @author 沈浩兵
	 * @date 2018-3-7 下午2:26:02
	 */
	@RequestMapping(value = "/mfTrenchUser/getCusLineRegList")
	public List<WebCusLineReg> getCusLineRegList(@RequestBody WebCusLineReg webCusLineReg) throws Exception;

	/**
	 * 方法描述： 验证渠道用户登录
	 * 
	 * @param webCusLineReg
	 * @return
	 * @throws Exception Map<String,Object>
	 * @author 沈浩兵
	 * @date 2018-3-7 下午10:03:50
	 */
	@RequestMapping(value = "/mfTrenchUser/checkLogin")
	public Map<String, Object> checkLogin(@RequestBody WebCusLineReg webCusLineReg) throws Exception;

	/**
	 * 方法描述： 获得
	 * 
	 * @param trenchUid
	 * @param trenchOpNo
	 * @param dataRang
	 * @return
	 * @throws Exception Map<String,Object>
	 * @author 沈浩兵
	 * @date 2018-3-12 下午6:23:06
	 */
	@RequestMapping(value = "/mfTrenchUser/getTenchCenterData")
	public Map<String, Object> getTenchCenterData(@RequestParam("trenchUid") String trenchUid, @RequestParam("trenchOpNo") String trenchOpNo, @RequestParam("dataRang") String dataRang) throws Exception;

	/**
	 * 方法描述： 验证渠道用户登录
	 * @param webCusLineReg
	 * @return
	 * @throws Exception
	 * @author wangchao
	 * @date 2018年4月20日 上午11:41:01
	 */
	@RequestMapping(value = "/hmTrenchUser/checkHmLogin")
	public Map<String, Object> checkHmLogin(WebCusLineReg webCusLineReg) throws Exception;

	/**
	 * 方法描述： 只在渠道表增加数据(惠农贷)
	 * @param webCusLineReg
	 * @return
	 * @throws Exception
	 * @author 段泽宇
	 * @date 2018年9月18日 下午13:41:01
	 */
	@RequestMapping(value = "/mfTrenchUser/insert")
	public WebCusLineReg insert(@RequestBody WebCusLineReg webCusLineReg) throws Exception;

	/**
	 * 渠道操作员要件上传初始化
	 * @param dbmp
	 * @throws Exception
	 */
	@RequestMapping(value = "/mfTrenchUser/initiaBiz")
	public void initiaBiz(@RequestBody DocBizManageParam dbmp)throws Exception;
}
