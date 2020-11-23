package app.component.cus.creditquery.feign;

import app.component.cus.creditquery.entity.MfCreditQueryRecordInfo;
import app.util.toolkit.Ipage;
import net.sf.json.JSONArray;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
* Title: MfCreditQueryRecordInfoBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Nov 30 09:15:13 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfCreditQueryRecordInfoFactorFeign {
	@RequestMapping(value = "/mfCreditQueryRecordInfo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;
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
	@RequestMapping(value = "/mfCreditQueryRecordInfo/queryCreditForBaiHang")
	public Map<String,Object> queryCreditForBaiHang(@RequestBody String cusNo) throws Exception;

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

	@RequestMapping(value = "/mfCreditQueryRecordInfo/getById")
	public MfCreditQueryRecordInfo getById(@RequestBody MfCreditQueryRecordInfo mfCreditQueryRecordInfo) throws Exception;
}
