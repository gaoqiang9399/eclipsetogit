package app.component.ncfgroup.appinterface.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.cus.entity.MfCusPersBaseInfo;
import app.component.cus.entity.MfCusPersonJob;
import app.component.interfaces.mobileinterface.entity.VmCifCustomerDTO;

@FeignClient("mftcc-platform-factor")
public interface WxMfCusPersBaseInfoFeign {

	/**
	 * 更新个人基本信息 通过cusNo ,直接更新
	 * 
	 * @param wxMfCusPersBaseInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/wxMfCusPersBaseInfo/updateBaseInfo")
	public Map<String, Object> updateBaseInfo(@RequestBody VmCifCustomerDTO cifcus, @RequestParam("verifyNum") String verifyNum)
			throws Exception;

	/**
	 * 更新个人基本信息(@RequestBody 新接口) 通过cusNo ,直接更新
	 * 
	 * @param wxMfCusPersBaseInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/wxMfCusPersBaseInfo/updateBaseInfoNew")
	public Map<String, Object> updateBaseInfoNew(@RequestBody VmCifCustomerDTO cifcus, @RequestParam("verifyNum") String verifyNum)
			throws Exception;

	/**
	 * 更新个人基本信息(@RequestBody 无三方验证) 通过cusNo ,直接更新
	 * 
	 * @param wxMfCusPersBaseInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/wxMfCusPersBaseInfo/updateBaseInfoForNoValidation")
	public Map<String, Object> updateBaseInfoForNoValidation(@RequestBody VmCifCustomerDTO cifcus,
			@RequestParam("verifyNum") String verifyNum) throws Exception;

	/**
	 * 查询个人基本信息
	 * 
	 * @param cusNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/wxMfCusPersBaseInfo/getBaseInfo")
	public Map<String, Object> getBaseInfo(@RequestBody String cusNo) throws Exception;

	/**
	 * 插入或更新个人工作信息 通过cusNo ,直接更新
	 * 
	 * @param mfCusPersonJob
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/wxMfCusPersBaseInfo/updateJobInfo")
	public Map<String, Object> updateJobInfo(@RequestBody MfCusPersonJob mfCusPersonJob) throws Exception;

	/**
	 * 查询个人工作信息
	 * 
	 * @param cusNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/wxMfCusPersBaseInfo/getJobInfo")
	public Map<String, Object> getJobInfo(@RequestBody String cusNo) throws Exception;

	/**
	 * 插入或更新信用卡（东风贷）
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/wxMfCusPersBaseInfo/updateCreditCardInfo")
	public Map<String, Object> updateCreditCardInfo(@RequestBody Map<String, Object> map) throws Exception;

	/**
	 * 插入或更新信用卡（汇达贷）
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/wxMfCusPersBaseInfo/updateCreditCardInfoForHd")
	public Map<String, Object> updateCreditCardInfoForHd(@RequestBody Map<String, Object> map) throws Exception;

	/**
	 * 插入或更新个家庭信息(@RequestBody 资产)
	 * 
	 * @param ajaxData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/wxMfCusPersBaseInfo/updateAssetsInfo")
	public Map<String, Object> updateAssetsInfo(@RequestBody String ajaxData) throws Exception;

	/**
	 * 通过客户号获取家庭信息(@RequestBody 资产)
	 * 
	 * @param cusNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/wxMfCusPersBaseInfo/getAssetsInfo")
	public Map<String, Object> getAssetsInfo(@RequestBody String cusNo) throws Exception;

	/**
	 * 更新学历信息
	 * 
	 * @param mfCusPersBaseInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/wxMfCusPersBaseInfo/updateEducationInfo")
	public Map<String, Object> updateEducationInfo(@RequestBody MfCusPersBaseInfo mfCusPersBaseInfo) throws Exception;

	/**
	 * 通过客户号获取学历信息
	 * 
	 * @param cusNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/wxMfCusPersBaseInfo/getEducationInfo")
	public Map<String, Object> getEducationInfo(@RequestBody String cusNo) throws Exception;

	/**
	 * 运营商三要素认证是否通过
	 * 
	 * @param cusNo
	 * @param cusName
	 * @param phone
	 * @param idNum
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/wxMfCusPersBaseInfo/verifyMobileInfo3")
	public Map<String, Object> verifyMobileInfo3(@RequestBody String cusNo, @RequestParam("cusName") String cusName,
			@RequestParam("phone") String phone, @RequestParam("idNum") String idNum) throws Exception;

	/**
	 * 验证芝麻信用、运营商、公积金等是否验证通过
	 * 
	 * @param cusNo
	 * @param method
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/wxMfCusPersBaseInfo/checkMfThirdServiceInfo")
	public Map<String, Object> checkMfThirdServiceInfo(@RequestBody String cusNo, @RequestParam("method") String method)
			throws Exception;
}
