package app.component.pss.information.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.pss.information.entity.PssAccounts;
import app.util.toolkit.Ipage;

/**
 * Title: PssAccountsBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Dec 20 19:34:19 SGT 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface PssAccountsFeign {

	@RequestMapping("/pssAccounts/insert")
	public void insert(@RequestBody PssAccounts pssAccounts) throws ServiceException;

	@RequestMapping("/pssAccounts/delete")
	public void delete(@RequestBody PssAccounts pssAccounts) throws ServiceException;

	@RequestMapping("/pssAccounts/update")
	public void update(@RequestBody PssAccounts pssAccounts) throws ServiceException;

	@RequestMapping("/pssAccounts/getById")
	public PssAccounts getById(@RequestBody PssAccounts pssAccounts) throws ServiceException;

	@RequestMapping("/pssAccounts/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping("/pssAccounts/getAll")
	public List<PssAccounts> getAll(@RequestBody PssAccounts pssAccounts) throws ServiceException;

	@RequestMapping("/pssAccounts/doSaveAccounts")
	public boolean doSaveAccounts(@RequestBody PssAccounts pssAccounts) throws ServiceException;

	@RequestMapping("/pssAccounts/deleteAccount")
	public boolean deleteAccount(@RequestBody PssAccounts pssAccounts) throws Exception;
}
