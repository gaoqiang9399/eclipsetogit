package  app.component.biz.feign;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.biz.entity.BizInfoChangeSub;
import app.util.toolkit.Ipage;

/**
* Title: BizInfoChangeSubBo.java
* Description:
* @author:@dhcc.com.cn
* @Wed Apr 20 06:09:29 GMT 2016
**/

@FeignClient("mftcc-platform-factor")
public interface BizInfoChangeSubFeign {
	
	@RequestMapping(value = "/bizInfoChangeSub/insert")
	public void insert(@RequestBody BizInfoChangeSub bizInfoChangeSub) throws ServiceException;
	
	@RequestMapping(value = "/bizInfoChangeSub/delete")
	public void delete(@RequestBody BizInfoChangeSub bizInfoChangeSub) throws ServiceException;
	
	@RequestMapping(value = "/bizInfoChangeSub/update")
	public void update(@RequestBody BizInfoChangeSub bizInfoChangeSub) throws ServiceException;
	
	@RequestMapping(value = "/bizInfoChangeSub/getById")
	public BizInfoChangeSub getById(@RequestBody BizInfoChangeSub bizInfoChangeSub) throws ServiceException;
	/**
	 * 
	 * 方法描述： 根据变更编号获得变更详情
	 * @param bizInfoChangeSub
	 * @return
	 * @throws ServiceException
	 * BizInfoChangeSub
	 * @author 沈浩兵
	 * @date 2017-3-1 下午4:08:00
	 */
	@RequestMapping(value = "/bizInfoChangeSub/getByChangeNo")
	public BizInfoChangeSub getByChangeNo(@RequestBody BizInfoChangeSub bizInfoChangeSub) throws ServiceException;
	
	@RequestMapping(value = "/bizInfoChangeSub/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("bizInfoChangeSub") BizInfoChangeSub bizInfoChangeSub) throws ServiceException;

	@RequestMapping(value = "/bizInfoChangeSub/getAll")
	public List<BizInfoChangeSub> getAll(@RequestBody BizInfoChangeSub bizInfoChangeSub) throws ServiceException;
}
