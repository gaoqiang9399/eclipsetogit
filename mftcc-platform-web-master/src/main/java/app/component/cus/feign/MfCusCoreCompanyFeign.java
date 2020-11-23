/**
 * Copyright (C) DXHM 版权所有
 * 文件名： MfCusCoreCompanyFeign.java
 * 包名： app.component.cus.feign
 * 说明：
 * @author 沈浩兵
 * @date 2018年4月16日 下午8:58:12
 * @version V1.0
 */ 
package app.component.cus.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.cus.entity.MfCusCoreCompany;
import app.util.toolkit.Ipage;

/**
 * 类名： MfCusCoreCompanyFeign
 * 描述：
 * @author 沈浩兵
 * @date 2018年4月16日 下午8:58:12
 *
 *
 */
@FeignClient("mftcc-platform-factor")
public interface MfCusCoreCompanyFeign {
	@RequestMapping("/mfCusCoreCompany/insert")
	public void insert(@RequestBody MfCusCoreCompany mfCusCoreCompany) throws Exception;
	
	@RequestMapping("/mfCusCoreCompany/delete")
	public void delete(@RequestBody MfCusCoreCompany mfCusCoreCompany) throws Exception;
	
	@RequestMapping("/mfCusCoreCompany/update")
	public void update(@RequestBody MfCusCoreCompany mfCusCoreCompany) throws Exception;
	
	@RequestMapping("/mfCusCoreCompany/getById")
	public MfCusCoreCompany getById(@RequestBody MfCusCoreCompany mfCusCoreCompany) throws Exception;
	
	@RequestMapping("/mfCusCoreCompany/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
	
	@RequestMapping("/mfCusCoreCompany/getCoreCompanyBusHisAjax")
	public Map<String, String> getCoreCompanyBusHisAjax(@RequestParam("cusNo") String cusNo) throws Exception;
	
	/**
	
	 *@描述 根据客户号获得核心企业信息
	
	 *@参数  
	
	 *@返回值  
	
	 *@创建人  shenhaobing
	
	 *@创建时间  2018/7/24
	
	 *@修改人和其它信息
	
	 */
	@RequestMapping("/mfCusCoreCompany/getByCoreCompanyUid")
	public MfCusCoreCompany getByCoreCompanyUid(@RequestBody MfCusCoreCompany mfCusCoreCompany) throws Exception;
	
	@RequestMapping(value = "/mfCusCoreCompany/getByOpNo")
	public MfCusCoreCompany getByOpNo(@RequestParam("opNo") String opNo)throws Exception;
}
