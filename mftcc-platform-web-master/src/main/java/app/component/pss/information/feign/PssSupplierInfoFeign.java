package app.component.pss.information.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.pss.information.entity.PssSupplierContacts;
import app.component.pss.information.entity.PssSupplierInfo;
import app.util.toolkit.Ipage;

/**
 * Title: PssSupplierInfoBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Dec 01 16:25:37 SGT 2017
 **/

@FeignClient("mftcc-platform-factor")
public interface PssSupplierInfoFeign {

	@RequestMapping("/pssSupplierInfo/insert")
	public void insert(@RequestBody PssSupplierInfo pssSupplierInfo) throws ServiceException;

	@RequestMapping("/pssSupplierInfo/delete")
	public void delete(@RequestBody PssSupplierInfo pssSupplierInfo) throws ServiceException;

	@RequestMapping("/pssSupplierInfo/update")
	public void update(@RequestBody PssSupplierInfo pssSupplierInfo) throws ServiceException;

	@RequestMapping("/pssSupplierInfo/getById")
	public PssSupplierInfo getById(@RequestBody PssSupplierInfo pssSupplierInfo) throws ServiceException;

	@RequestMapping("/pssSupplierInfo/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping("/pssSupplierInfo/getAll")
	public List<PssSupplierInfo> getAll(@RequestBody PssSupplierInfo pssSupplierInfo) throws ServiceException;

	@RequestMapping("/pssSupplierInfo/generateSupplierNo")
	public String generateSupplierNo() throws ServiceException;

	@RequestMapping("/pssSupplierInfo/doSaveSupplierInfo")
	public boolean doSaveSupplierInfo(@RequestBody PssSupplierInfo pssSupplierInfo, @RequestParam("pssSupplierContactsList") List<PssSupplierContacts> pssSupplierContactsList, @RequestParam("originalAccountsPayed") String originalAccountsPayed,
			@RequestParam("originalDepositPayed") String originalDepositPayed, @RequestParam("saveOrEditFlag") String saveOrEditFlag) throws Exception;

	@RequestMapping("/pssSupplierInfo/doCloseSupplierInfo")
	public boolean doCloseSupplierInfo(@RequestBody PssSupplierInfo pssSupplierInfo) throws Exception;

	@RequestMapping("/pssSupplierInfo/doEnableSupplierInfo")
	public boolean doEnableSupplierInfo(@RequestBody PssSupplierInfo pssSupplierInfo) throws Exception;

	@RequestMapping("/pssSupplierInfo/deleteSupplierInfo")
	public boolean deleteSupplierInfo(@RequestBody PssSupplierInfo pssSupplierInfo) throws Exception;

	@RequestMapping("/pssSupplierInfo/getAllEnabledSupplierList")
	public List<PssSupplierInfo> getAllEnabledSupplierList() throws Exception;

	@RequestMapping("/pssSupplierInfo/updateAccountsPayedBalanceBySupplierId")
	public boolean updateAccountsPayedBalanceBySupplierId(@RequestParam("supplierId") String supplierId, @RequestParam("payedBalance") Double payedBalance) throws Exception;

	/** 校验供应商是否禁用 */
	@RequestMapping("/pssSupplierInfo/doForbiddenValidate")
	public Map<String, String> doForbiddenValidate(@RequestBody String supplierId) throws Exception;

}
