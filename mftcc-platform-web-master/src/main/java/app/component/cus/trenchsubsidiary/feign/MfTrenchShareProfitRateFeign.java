package app.component.cus.trenchsubsidiary.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.cus.trenchsubsidiary.entity.MfTrenchShareProfitRate;
import app.util.toolkit.Ipage;

/**
 * Title: MfTrenchShareProfitRateBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Mar 06 15:00:20 CST 2018
 **/
@FeignClient("mftcc-platform-factor")
public interface MfTrenchShareProfitRateFeign {
	@RequestMapping(value = "/mfTrenchShareProfitRate/insert")
	public MfTrenchShareProfitRate insert(@RequestBody MfTrenchShareProfitRate mfTrenchShareProfitRate) throws Exception;

	@RequestMapping(value = "/mfTrenchShareProfitRate/delete")
	public void delete(@RequestBody MfTrenchShareProfitRate mfTrenchShareProfitRate) throws Exception;

	@RequestMapping(value = "/mfTrenchShareProfitRate/update")
	public void update(@RequestBody MfTrenchShareProfitRate mfTrenchShareProfitRate) throws Exception;

	@RequestMapping(value = "/mfTrenchShareProfitRate/getById")
	public MfTrenchShareProfitRate getById(@RequestBody MfTrenchShareProfitRate mfTrenchShareProfitRate) throws Exception;

	@RequestMapping(value = "/mfTrenchShareProfitRate/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	/**
	 * 方法描述： 根据渠道编号获得分润比例列表
	 * 
	 * @param mfTrenchShareProfitRate
	 * @return
	 * @throws Exception List<MfTrenchShareProfitRate>
	 * @author 沈浩兵
	 * @date 2018-3-6 下午4:01:31
	 */
	@RequestMapping(value = "/mfTrenchShareProfitRate/getListBytrenchUid")
	public List<MfTrenchShareProfitRate> getListBytrenchUid(@RequestBody MfTrenchShareProfitRate mfTrenchShareProfitRate) throws Exception;

	/**
	 * 方法描述： 根据渠道编号、放款金额获得分润比例
	 * 
	 * @param trenchUid
	 * @param putoutAmt
	 * @return
	 * @throws Exception MfTrenchShareProfitRate
	 * @author 沈浩兵
	 * @date 2018-3-13 上午10:42:28
	 */
	@RequestMapping(value = "/mfTrenchShareProfitRate/getShareProfitRate")
	public MfTrenchShareProfitRate getShareProfitRate(@RequestBody String trenchUid, @RequestParam("putoutAmt") Double putoutAmt) throws Exception;

}
