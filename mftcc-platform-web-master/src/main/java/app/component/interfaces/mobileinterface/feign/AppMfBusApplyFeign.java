package app.component.interfaces.mobileinterface.feign;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.app.entity.MfBusApply;
import app.component.pact.entity.MfBusFincApp;
import app.util.toolkit.Ipage;

/**
 * 
 * 处理融资申请的Bo类
 * @author zhang_dlei
 *
 */
@FeignClient("mftcc-platform-factor")
public interface AppMfBusApplyFeign{
	
	@RequestMapping(value = "/appMfBusApply/insert")
	public Map<String, Object> insert(@RequestBody  MfBusApply mfBusApply,@RequestParam("cusTel") String cusTel,@RequestParam("type") String type,@RequestParam("httpServletRequest")
			HttpServletRequest httpServletRequest) throws Exception;

	@RequestMapping(value = "/appMfBusApply/insertNew")
	public Map<String, Object> insertNew(@RequestBody  MfBusApply mfBusApply,@RequestParam("type") String type,@RequestParam("httpServletRequest") HttpServletRequest httpServletRequest)
			throws Exception;

	@RequestMapping(value = "/appMfBusApply/getById")
	public Map<String, Object> getById(@RequestBody  String appId,@RequestParam("regNo") String regNo) throws Exception;

	@RequestMapping(value = "/appMfBusApply/findByPage")
	public Map<String, Object> findByPage(@RequestBody  Ipage ipage,@RequestParam("cusNo") String cusNo,@RequestParam("cusTel") String cusTel,@RequestParam("stage") String stage) throws Exception;

	@RequestMapping(value = "/appMfBusApply/validateApplyAble")
	public Map<String, Object> validateApplyAble(@RequestBody  String cusTel) throws Exception;

	@RequestMapping(value = "/appMfBusApply/validateApplyAbleNew")
	public Map<String, Object> validateApplyAbleNew(@RequestBody  String cusNo) throws Exception;

	@RequestMapping(value = "/appMfBusApply/insertOrUpdateBankInfo")
	public Map<String, Object> insertOrUpdateBankInfo(@RequestBody  Map<String, Object> map,@RequestParam("httpServletRequest") HttpServletRequest httpServletRequest)
			throws Exception;

	@RequestMapping(value = "/appMfBusApply/insertOrUpdateBankInfoNew")
	public Map<String, Object> insertOrUpdateBankInfoNew(@RequestBody  Map<String, Object> map,@RequestParam("httpServletRequest") HttpServletRequest httpServletRequest)
			throws Exception;

	@RequestMapping(value = "/appMfBusApply/getBankCardInfo")
	public Map<String, Object> getBankCardInfo(@RequestBody  Ipage ipage,@RequestParam("cusTel") String cusTel) throws Exception;

	@RequestMapping(value = "/appMfBusApply/getBankCardInfoNew")
	public Map<String, Object> getBankCardInfoNew(@RequestBody  Ipage ipage,@RequestParam("cusNo") String cusNo) throws Exception;

	@RequestMapping(value = "/appMfBusApply/getBusFincAppInfo")
	public Map<String, Object> getBusFincAppInfo(@RequestBody  String cusTel) throws Exception;

	@RequestMapping(value = "/appMfBusApply/getCusMngNameById")
	public Map<String, Object> getCusMngNameById(@RequestBody  String cusNo,@RequestParam("cusMngNo") String cusMngNo) throws Exception;

	@RequestMapping(value = "/appMfBusApply/getAppSetting")
	public Map<String, Object> getAppSetting() throws Exception;

	@RequestMapping(value = "/appMfBusApply/getBusCreditInfo")
	public Map<String, Object> getBusCreditInfo(@RequestBody  String cusTel,@RequestParam("pactNo") String pactNo) throws Exception;

	@RequestMapping(value = "/appMfBusApply/getBusCreditInfoNew")
	public Map<String, Object> getBusCreditInfoNew(@RequestBody  String cusTel,@RequestParam("pactNo") String pactNo) throws Exception;

	@RequestMapping(value = "/appMfBusApply/getMfBusPactListByCusNo")
	public Map<String, Object> getMfBusPactListByCusNo(@RequestBody  String cusNo) throws Exception;

	@RequestMapping(value = "/appMfBusApply/insertApplyWithdraw")
	public Map<String, Object> insertApplyWithdraw(@RequestBody  MfBusFincApp insertApplyWithdraw,@RequestParam("httpServletRequest") HttpServletRequest httpServletRequest)
			throws Exception;

	@RequestMapping(value = "/appMfBusApply/getLoanIsApproveBo")
	public Map<String, Object> getLoanIsApproveBo(@RequestBody  MfBusFincApp mfBusFincApp) throws Exception;

	@RequestMapping(value = "/appMfBusApply/getBusFee")
	public Map<String, Object> getBusFee(@RequestBody  String appId,@RequestParam("amt") String amt,@RequestParam("term") String term,@RequestParam("termType") String termType) throws Exception;

	@RequestMapping(value = "/appMfBusApply/getFeeInfo")
	public Map<String, Object> getFeeInfo(@RequestBody  String kindNo) throws Exception;

	@RequestMapping(value = "/appMfBusApply/getCusBankAccManageById")
	public Map<String, Object> getCusBankAccManageById(@RequestBody  String relId) throws ServiceException;
	

	
}
