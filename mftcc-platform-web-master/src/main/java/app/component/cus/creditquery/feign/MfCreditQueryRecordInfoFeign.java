package  app.component.cus.creditquery.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.cus.creditquery.entity.MfCreditQueryRecordInfo;
import app.util.toolkit.Ipage;
import net.sf.json.JSONArray;

/**
* Title: MfCreditQueryRecordInfoBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Nov 30 09:15:13 CST 2017
**/
@FeignClient("mftcc-platform-dhcc")
public interface MfCreditQueryRecordInfoFeign {
	
	@RequestMapping(value = "/mfCreditQueryRecordInfo/insert")
	public void insert(@RequestBody MfCreditQueryRecordInfo mfCreditQueryRecordInfo) throws Exception;
	
	@RequestMapping(value = "/mfCreditQueryRecordInfo/delete")
	public void delete(@RequestBody MfCreditQueryRecordInfo mfCreditQueryRecordInfo) throws Exception;
	
	@RequestMapping(value = "/mfCreditQueryRecordInfo/update")
	public void update(@RequestBody MfCreditQueryRecordInfo mfCreditQueryRecordInfo) throws Exception;
	
	@RequestMapping(value = "/mfCreditQueryRecordInfo/getById")
	public MfCreditQueryRecordInfo getById(@RequestBody MfCreditQueryRecordInfo mfCreditQueryRecordInfo) throws Exception;
	
	@RequestMapping(value = "/mfCreditQueryRecordInfo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfCreditQueryRecordInfo") MfCreditQueryRecordInfo mfCreditQueryRecordInfo) throws Exception;
	/**
	 * 
	 * 方法描述： 征信查询
	 * @param mfCreditQueryRecordInfo
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-11-30 上午9:49:42
	 */
	@RequestMapping(value = "/mfCreditQueryRecordInfo/doCreditQuery")
	public Map<String,Object> doCreditQuery(@RequestBody MfCreditQueryRecordInfo mfCreditQueryRecordInfo) throws Exception;

	/**
	 * 方法描述： 根据查询编号获得征信内容
	 * @param queryId
	 * @return
	 * @throws Exception Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-12-4 上午11:37:04
	 */
	@RequestMapping(value = "/mfCreditQueryRecordInfo/getCreditHtmlContent")
	public Map<String, Object> getCreditHtmlContent(@RequestParam(name = "queryId") String queryId) throws Exception;

	/**
	 * 
	 * 方法描述： 获得征信查询历史
	 * @param mfCreditQueryRecordInfo
	 * @return
	 * @throws Exception
	 * List<MfCreditQueryRecordInfo>
	 * @author 沈浩兵
	 * @date 2017-12-4 下午2:47:49
	 */
	@RequestMapping(value = "/mfCreditQueryRecordInfo/getCreditQueryRecord")
	public List<MfCreditQueryRecordInfo> getCreditQueryRecord(@RequestBody MfCreditQueryRecordInfo mfCreditQueryRecordInfo) throws Exception;
	/**
	 * 
	 * 方法描述： 通过客户号获得最新的征信报告查询结果
	 * @param cusNo
	 * @param appId
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-12-4 下午2:51:50
	 */
	@RequestMapping(value = "/mfCreditQueryRecordInfo/getNewestCreditHtml")
	public Map<String,Object> getNewestCreditHtml(@RequestParam("cusNo") String cusNo,@RequestParam("appId") String appId) throws Exception;
	/**
	 * 
	 * 方法描述： 获得征信查询所需要的要件资料
	 * @param cusNo
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-12-22 下午3:10:03
	 */
	@RequestMapping(value = "/mfCreditQueryRecordInfo/getDocManageListForCredit")
	public Map<String,Object> getDocManageListForCredit(@RequestBody String cusNo,@RequestParam("cusBaseType")  String cusBaseType,@RequestParam("queryCusType") String queryCusType,@RequestParam("appId") String appId) throws Exception;
	/**
	 * 
	 * 方法描述： 获得征信查询客户数据源
	 * @param cusNo
	 * @param appId
	 * @return
	 * @throws Exception
	 * JSONArray
	 * @author 沈浩兵
	 * @date 2017-12-26 下午6:24:34
	 */
	@RequestMapping(value = "/mfCreditQueryRecordInfo/getCreditCusJSONArray")
	public JSONArray getCreditCusJSONArray(@RequestParam("cusNo") String cusNo,@RequestParam("appId") String appId)throws Exception;

	/**
	 * 方法描述： 获得征信查询客户信息
	 * @param cusNo
	 * @param creditCusType
	 * @return
	 * @throws Exception Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-12-26 下午9:00:41
	 */
	@RequestMapping(value = "/mfCreditQueryRecordInfo/getCreditQueryCusInfo")
	public Map<String, Object> getCreditQueryCusInfo(@RequestParam(name = "cusNo") String cusNo, @RequestParam(name = "creditCusType") String creditCusType) throws Exception;

	/**
	 * 
	 * 方法描述： 根据客户号、查询客户类型、查询客户关联编号获得最新的征信结果内容
	  * @param mfCreditQueryRecordInfo 
	 * 目前包含
	 * @param cusNo 借款人客户号
	 * @param queryCusType 查询客户类型
	 * @param relId 查询客户关联编号 （查询借款人：借款人客户号，查询担保人：担保人客户号，查询配偶：配偶信息主键）
	 * @param uuid 存放安卓的IMEI或是苹果的uuid
	 * @param equipmentType 查询设置类型:mobile 移动端，pc pc端
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-12-27 下午4:49:20
	 */
	@RequestMapping(value = "/mfCreditQueryRecordInfo/getNewestCreditHtmlByRelId")
	public Map<String,Object> getNewestCreditHtmlByRelId(@RequestBody MfCreditQueryRecordInfo mfCreditQueryRecordInfo) throws Exception;
	/**
	 * 
	 * 方法描述： 获得征信查询提交时选择上传ftp要件信息
	 * @param cusNo
	 * @param appId
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2017-12-28 下午1:36:51
	 */
	@RequestMapping(value = "/mfCreditQueryRecordInfo/getSelectCreditDocDataByAppId")
	public Map<String,Object> getSelectCreditDocDataByAppId(@RequestBody String cusNo,@RequestParam("appId") String appId)throws Exception;
	/**
	 * 
	 * 方法描述： 获得当前日期查询的证件号码是否能重复查询，系统规定x天内不需要重复查询,x天数从系统配置中获得
	 * @param idNum
	 * @return
	 * @throws Exception
	 * Map<String,Object>
	 * @author 沈浩兵
	 * @date 2018-1-16 上午10:31:25
	 */
	@RequestMapping(value = "/mfCreditQueryRecordInfo/getCheckRepeatQueryFlag")
	public Map<String,Object> getCheckRepeatQueryFlag(@RequestBody String idNum) throws Exception;
	
}
