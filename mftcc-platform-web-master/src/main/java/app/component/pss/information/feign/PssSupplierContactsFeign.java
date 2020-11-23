package app.component.pss.information.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.pss.information.entity.PssSupplierContacts;
import app.util.toolkit.Ipage;

/**
 * Title: PssSupplierContactsBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Dec 01 16:32:54 SGT 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface PssSupplierContactsFeign {

	@RequestMapping("/pssSupplierContacts/insert")
	public void insert(@RequestBody PssSupplierContacts pssSupplierContacts) throws ServiceException ;

	@RequestMapping("/pssSupplierContacts/delete")
	public void delete(@RequestBody PssSupplierContacts pssSupplierContacts) throws ServiceException ;

	@RequestMapping("/pssSupplierContacts/update")
	public void update(@RequestBody PssSupplierContacts pssSupplierContacts) throws ServiceException ;

	@RequestMapping("/pssSupplierContacts/getById")
	public PssSupplierContacts getById(@RequestBody PssSupplierContacts pssSupplierContacts) throws ServiceException ;

	@RequestMapping("/pssSupplierContacts/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException ;

	@RequestMapping("/pssSupplierContacts/getAll")
	public List<PssSupplierContacts> getAll(@RequestBody PssSupplierContacts pssSupplierContacts)
			throws ServiceException ;
}
