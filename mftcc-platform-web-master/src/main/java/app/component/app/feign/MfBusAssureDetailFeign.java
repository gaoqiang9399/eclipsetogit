package app.component.app.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.app.entity.MfBusAssureDetail;
import app.component.assure.entity.MfAssureInfo;
import app.component.cus.entity.MfCusAssureCompany;
import app.util.toolkit.Ipage;

import java.util.Map;

/**
 * Title: MfBusAssureDetailBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Oct 25 11:38:10 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface MfBusAssureDetailFeign {

	@RequestMapping(value = "/mfBusAssureDetail/insert")
	public void insert(@RequestBody MfBusAssureDetail mfBusAssureDetail) throws Exception;

	@RequestMapping(value = "/mfBusAssureDetail/delete")
	public void delete(@RequestBody MfBusAssureDetail mfBusAssureDetail) throws Exception;

	@RequestMapping(value = "/mfBusAssureDetail/update")
	public void update(@RequestBody MfBusAssureDetail mfBusAssureDetail) throws Exception;

	@RequestMapping(value = "/mfBusAssureDetail/getById")
	public MfBusAssureDetail getById(@RequestBody MfBusAssureDetail mfBusAssureDetail) throws Exception;

	@RequestMapping(value = "/mfBusAssureDetail/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	/**
	 * 添加担保额度占用明细
	 * 
	 * @param appId
	 * @param mfAssureInfo
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-10-25 下午1:55:54
	 */
	@RequestMapping(value = "/mfBusAssureDetail/addAssureDetail")
	public void addAssureDetail(@RequestBody String appId,@RequestParam("mfAssureInfo")  MfAssureInfo mfAssureInfo) throws Exception;

	/**
	 * 担保额度占用更新<br>
	 * 提交至签约、合同审批结束时，更新担保公司担保额度占用，同时更新担保详情及押品总担保额度
	 * 
	 * @param appId
	 * @param pactId
	 * @param pactAmt
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-10-25 下午8:03:56
	 */
	@RequestMapping(value = "/mfBusAssureDetail/updateAssureAmt")
	public void updateAssureAmt(@RequestBody String appId,@RequestParam("pactId")  String pactId,@RequestParam("pactAmt")  Double pactAmt) throws Exception;

	/**
	 * 完全恢复担保额度占用
	 * 
	 * @param appId
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-10-25 下午3:32:04
	 */
	@RequestMapping(value = "/mfBusAssureDetail/recoveryAllAssureAmt")
	public void recoveryAllAssureAmt(@RequestBody String appId) throws Exception;

	/**
	 * 恢复担保额度占用
	 * 
	 * @param appId
	 * @param repayAmt
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-10-25 下午5:07:00
	 */
	@RequestMapping(value = "/mfBusAssureDetail/recoveryAssureAmt")
	public void recoveryAssureAmt(@RequestBody String appId,@RequestParam("repayAmt")  Double repayAmt) throws Exception;

	/**
	 * 查询已占用担保额度
	 * 
	 * @param assureCompanyId {@link MfCusAssureCompany#assureCompanyId}
	 * @return
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-11-2 下午3:25:48
	 */
	@RequestMapping(value = "/mfBusAssureDetail/getOccupyAmt")
	public Double getOccupyAmt(@RequestBody String assureCompanyId) throws Exception;

	/**
	 * 获取历史数据统计
	 * @param assureCompanyId
	 * @return
	 */
	@RequestMapping(value = "/mfBusAssureDetail/getTotalData")
	public Map<String, Object> getTotalData(@RequestParam("assureCompanyId") String assureCompanyId);
}
