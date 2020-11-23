package  app.component.cus.relation.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.cus.relation.entity.MfCusRelationType;
import app.util.toolkit.Ipage;

/**
* Title: MfCusRelationTypeBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat Dec 03 17:34:38 CST 2016
**/

@FeignClient("mftcc-platform-factor")
public interface MfCusRelationTypeFeign {
	
	@RequestMapping(value = "/mfCusRelationType/insert")
	public void insert(@RequestBody MfCusRelationType mfCusRelationType) throws ServiceException;
	
	@RequestMapping(value = "/mfCusRelationType/delete")
	public void delete(@RequestBody MfCusRelationType mfCusRelationType) throws ServiceException;
	
	@RequestMapping(value = "/mfCusRelationType/update")
	public void update(@RequestBody MfCusRelationType mfCusRelationType) throws ServiceException;
	
	@RequestMapping(value = "/mfCusRelationType/getById")
	public MfCusRelationType getById(@RequestBody MfCusRelationType mfCusRelationType) throws ServiceException;
	
	@RequestMapping(value = "/mfCusRelationType/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfCusRelationType") MfCusRelationType mfCusRelationType) throws ServiceException;
	/**
	 * 得到所有的关联关系类型
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/mfCusRelationType/getRelationType")
	public List<MfCusRelationType> getRelationType() throws ServiceException;
}
