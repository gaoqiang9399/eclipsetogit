package app.component.pss.information.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.pss.information.entity.PssCustomerContacts;
import app.component.pss.information.entity.PssCustomerInfo;
import app.util.toolkit.Ipage;

/**
 * Title: PssCustomerInfoBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Dec 01 16:13:22 SGT 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface PssCustomerInfoFeign {

	@RequestMapping("/pssCustomerInfo/insert") 
	public void insert(@RequestBody PssCustomerInfo pssCustomerInfo) throws ServiceException;

	@RequestMapping("/pssCustomerInfo/delete")
	public void delete(@RequestBody PssCustomerInfo pssCustomerInfo) throws ServiceException;

	@RequestMapping("/pssCustomerInfo/update")
	public void update(@RequestBody PssCustomerInfo pssCustomerInfo) throws ServiceException;

	@RequestMapping("/pssCustomerInfo/getById") 
	public PssCustomerInfo getById(@RequestBody PssCustomerInfo pssCustomerInfo) throws ServiceException;

	@RequestMapping("/pssCustomerInfo/findByPage") 
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping("/pssCustomerInfo/getAll") 
	public List<PssCustomerInfo> getAll(@RequestBody PssCustomerInfo pssCustomerInfo) throws ServiceException;

	@RequestMapping("/pssCustomerInfo/generateCusCode") 
	public String generateCusCode() throws ServiceException;

	@RequestMapping("/pssCustomerInfo/doSaveCustomerInfo")
	public boolean doSaveCustomerInfo(@RequestBody PssCustomerInfo pssCustomerInfo,
			@RequestParam("pssCustomerContactsList") List<PssCustomerContacts> pssCustomerContactsList, @RequestParam("originalAccountsReceived")String originalAccountsReceived,
			@RequestParam("originalDepositReceived")String originalDepositReceived, @RequestParam("saveOrEditFlag")String saveOrEditFlag) throws Exception;

	@RequestMapping("/pssCustomerInfo/doCloseCustomerInfo")
	public boolean doCloseCustomerInfo(@RequestBody PssCustomerInfo pssCustomerInfo) throws Exception;

	@RequestMapping("/pssCustomerInfo/doEnableCustomerInfo")
	public boolean doEnableCustomerInfo(@RequestBody PssCustomerInfo pssCustomerInfo) throws Exception;

	@RequestMapping("/pssCustomerInfo/deleteCustomerInfo")
	public boolean deleteCustomerInfo(@RequestBody PssCustomerInfo pssCustomerInfo) throws Exception;

	@RequestMapping("/pssCustomerInfo/getAllEnabledCusList") 
	public List<PssCustomerInfo> getAllEnabledCusList() throws Exception;

	@RequestMapping("/pssCustomerInfo/updateAccountsReceivedBalanceByCusNo")
	public boolean updateAccountsReceivedBalanceByCusNo(@RequestParam("cusNo") String cusNo, @RequestParam("receivedBalance")Double receivedBalance)
			throws Exception;

	/** 校验客户是否禁用 */
	@RequestMapping("/pssCustomerInfo/doForbiddenValidate")
	public Map<String, String> doForbiddenValidate(@RequestBody String cusNo) throws Exception;

}
