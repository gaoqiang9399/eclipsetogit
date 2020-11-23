package app.component.ncfgroup.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("mftcc-platform-factor")
public interface TcLoanReviewFeign {
	/**
	 * 根据客户号查询基本信息 调用--注册开户&上标接口
	 * 
	 * @param map
	 *            loanAmt 借据金额</br>
	 *            fincId 借据号</br>
	 *            guaranteeRateY 担保费率</br>
	 *            consultationRateY 咨询费率</br>
	 *            unionConsultationRateY 联合咨询费率</br>
	 *            platformRateY 平台后置手续费率
	 * @param cusNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/bizWhitelist/loanReview")
	public Map<String, Object> loanReview(@RequestBody Map<String, String> map, @RequestParam("cusNo") String cusNo)
			throws Exception;
}
