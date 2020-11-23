package app.component.oa.debt.feign;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.oa.debt.entity.MfOaDebt;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

/**
 * Title: MfOaDebtBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Sat Dec 17 14:00:23 CST 2016
 **/
@FeignClient("mftcc-platform-factor")
public interface MfOaDebtFeign {
	@RequestMapping(value = "/mfOaDebt/insert")
	public void insert(@RequestBody MfOaDebt mfOaDebt) throws ServiceException;

	@RequestMapping(value = "/mfOaDebt/insertForApp")
	public Map<String, String> insertForApp(@RequestBody MfOaDebt mfOaDebt) throws ServiceException;

	@RequestMapping(value = "/mfOaDebt/delete")
	public void delete(@RequestBody MfOaDebt mfOaDebt) throws ServiceException;

	@RequestMapping(value = "/mfOaDebt/update")
	public void update(@RequestBody MfOaDebt mfOaDebt) throws ServiceException;

	@RequestMapping(value = "/mfOaDebt/getById")
	public MfOaDebt getById(@RequestBody MfOaDebt mfOaDebt) throws ServiceException;

	@RequestMapping(value = "/mfOaDebt/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping(value = "/mfOaDebt/insertForSubmit")
	public Map<String, String> insertForSubmit(@RequestBody MfOaDebt mfOaDebt) throws Exception;

	@RequestMapping(value = "/mfOaDebt/insertForSubmitForApp")
	public Map<String, String> insertForSubmitForApp(@RequestBody MfOaDebt mfOaDebt) throws Exception;

	@RequestMapping(value = "/mfOaDebt/updateForSubmit1")
	public Result updateForSubmit1(@RequestParam("taskId") String taskId, @RequestParam("debtId") String debtId,
			@RequestParam("opinionType") String opinionType, @RequestParam("approvalOpinion") String approvalOpinion,
			@RequestParam("transition") String transition, @RequestParam("regNo") String regNo,
			@RequestParam("nextUser") String nextUser, @RequestBody MfOaDebt mfOaDebt);

	@RequestMapping(value = "/mfOaDebt/sumAmt")
	public BigDecimal[] sumAmt(@RequestBody String opNo) throws ServiceException;

	/**
	 * 
	 * 方法描述：获取当前登录人员借款总额
	 * 
	 * @param opNo
	 * @return
	 * @throws ServiceException
	 *             Map<String,Object>
	 * @author zhs
	 * @date 2017-3-8 下午5:31:32
	 */
	@RequestMapping(value = "/mfOaDebt/getDebtSumAmt")
	public Map<String, BigDecimal> getDebtSumAmt(@RequestBody String opNo) throws ServiceException;
}
