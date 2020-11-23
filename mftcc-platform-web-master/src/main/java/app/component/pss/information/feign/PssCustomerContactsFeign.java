package app.component.pss.information.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.pss.information.entity.PssCustomerContacts;
import app.util.toolkit.Ipage;

/**
 * Title: PssCustomerContactsBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Dec 01 16:24:00 SGT 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface PssCustomerContactsFeign {

	@RequestMapping("/pssCustomerContacts/insert")
	public void insert(@RequestBody PssCustomerContacts pssCustomerContacts) throws ServiceException ;

	@RequestMapping("/pssCustomerContacts/delete")
	public void delete(@RequestBody PssCustomerContacts pssCustomerContacts) throws ServiceException ;

	@RequestMapping("/pssCustomerContacts/update")
	public void update(@RequestBody PssCustomerContacts pssCustomerContacts) throws ServiceException ;

	@RequestMapping("/pssCustomerContacts/getById")
	public PssCustomerContacts getById(@RequestBody PssCustomerContacts pssCustomerContacts) throws ServiceException ;

	@RequestMapping("/pssCustomerContacts/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException ;

	@RequestMapping("/pssCustomerContacts/mfQueryDisagree")
	public List<PssCustomerContacts> getAll(@RequestBody PssCustomerContacts pssCustomerContacts)
			throws ServiceException ;
}
