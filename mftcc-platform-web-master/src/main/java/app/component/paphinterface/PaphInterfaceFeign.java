/**
 * Copyright (@RequestBody C) DXHM 版权所有
 * 文件名： PaphInterface.java
 * 包名： app.component.paphinterface
 * 说明：
 * @author 沈浩兵
 * @date 2017-11-4 下午4:48:51
 * @version V1.0
 */
package app.component.paphinterface;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import app.component.paph.entity.ApiBusRecord;
import app.component.paph.entity.ApiReturnRecord;
import app.component.paph.entity.MfPolicyInfo;

/**
 * 类名： PaphInterface 描述：
 * 
 * @author 沈浩兵
 * @date 2017-11-4 下午4:48:51
 *
 *
 */
@FeignClient("mftcc-platform-factor")
public interface PaphInterfaceFeign {

	@RequestMapping(value = "/paphInterface/doBusinessByBusRecord", method = RequestMethod.POST)
	public Map<String, Object> doBusinessByBusRecord() throws Exception;

	@RequestMapping(value = "/paphInterface/getById", method = RequestMethod.POST)
	public MfPolicyInfo getById(@RequestBody MfPolicyInfo mfPolicyInfo) throws Exception ;

	@RequestMapping(value = "/paphInterface/getMfPolicyInfoList", method = RequestMethod.POST)
	public List<MfPolicyInfo> getMfPolicyInfoList(@RequestBody MfPolicyInfo mfPolicyInfo) throws Exception;

	@RequestMapping(value = "/paphInterface/getApiBusRecordList", method = RequestMethod.POST)
	public List<ApiBusRecord> getApiBusRecordList(@RequestBody ApiBusRecord apiBusRecord) throws Exception;

	@RequestMapping(value = "/paphInterface/update", method = RequestMethod.POST)
	public void update(@RequestBody ApiBusRecord apiBusRecord) throws Exception ;

	@RequestMapping(value = "/paphInterface/getById1", method = RequestMethod.POST)
	public ApiBusRecord getById1(@RequestBody ApiBusRecord apiBusRecord) throws Exception ;

	@RequestMapping(value = "/paphInterface/insert", method = RequestMethod.POST)
	public void insert(@RequestBody ApiReturnRecord apiReturnRecord) throws Exception ;

	@RequestMapping(value = "/paphInterface/getReturnByNotSupply", method = RequestMethod.POST)
	public List<ApiReturnRecord> getReturnByNotSupply(@RequestBody ApiReturnRecord apiReturnRecord) throws Exception ;

	@RequestMapping(value = "/paphInterface/getReturnByUntreated", method = RequestMethod.POST)
	public List<ApiReturnRecord> getReturnByUntreated(@RequestBody String orderNo) throws Exception ;

	@RequestMapping(value = "/paphInterface/update1", method = RequestMethod.POST)
	public void update1(@RequestBody ApiReturnRecord apiReturnRecord) throws Exception ;

	@RequestMapping(value = "/paphInterface/insertBusRecord", method = RequestMethod.POST)
	public void insertBusRecord(@RequestBody ApiBusRecord apiBusRecord) throws Exception ;

	@RequestMapping(value = "/paphInterface/getApiReturnRecord", method = RequestMethod.POST)
	public ApiReturnRecord getApiReturnRecord(@RequestBody ApiReturnRecord apiReturnRecord) throws Exception ;

}
