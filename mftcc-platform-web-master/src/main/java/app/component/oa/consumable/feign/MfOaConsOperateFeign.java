package  app.component.oa.consumable.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.oa.consumable.entity.MfOaConsOperate;
import app.component.oa.consumable.entity.OpUserCons;
import app.util.toolkit.Ipage;

/**
* Title: MfOaConsOperateBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sat Dec 24 12:00:15 CST 2016
**/
@FeignClient("mftcc-platform-factor")
public interface MfOaConsOperateFeign {
	@RequestMapping(value = "/mfOaConsOperate/insert")
	public void insert(@RequestBody MfOaConsOperate mfOaConsOperate) throws ServiceException;
	
	@RequestMapping(value = "/mfOaConsOperate/delete")
	public void delete(@RequestBody MfOaConsOperate mfOaConsOperate) throws ServiceException;
	
	@RequestMapping(value = "/mfOaConsOperate/update")
	public void update(@RequestBody MfOaConsOperate mfOaConsOperate) throws ServiceException;
	
	@RequestMapping(value = "/mfOaConsOperate/getById")
	public MfOaConsOperate getById(@RequestBody MfOaConsOperate mfOaConsOperate) throws ServiceException;
	
	@RequestMapping(value = "/mfOaConsOperate/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping(value = "/mfOaConsOperate/findAppByPage")
	public Ipage findAppByPage(@RequestBody Ipage ipage) throws ServiceException;

	/**
	 * 获取当前操作员的资产申领信息
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/mfOaConsOperate/getOpUserCons")
	public OpUserCons getOpUserCons() throws ServiceException;

	@RequestMapping(value = "/mfOaConsOperate/getOaConsAppCount")
	public Integer getOaConsAppCount(@RequestBody MfOaConsOperate mfOaConsOperate)throws ServiceException;
	
}
