package app.component.pact.feign;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.pact.entity.MfBusFincApp;
import app.component.pact.entity.MfBusFincAppAccountRecord;
import app.util.toolkit.Ipage;

import java.util.List;

/**
 * Title: MfBusFincAppBoImplImpl.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue May 31 18:10:07 CST 2016
 **/
@FeignClient("mftcc-platform-factor")
public interface MfBusFincAppAccountRecordFeign {

	@RequestMapping(value = "/mfBusFincAppAccountRecord/insert")
	public void insert(@RequestBody MfBusFincAppAccountRecord mfBusFincAppAccountRecord) throws ServiceException;

	@RequestMapping(value = "/mfBusFincAppAccountRecord/delete")
	public void delete(@RequestBody MfBusFincAppAccountRecord mfBusFincAppAccountRecord) throws ServiceException;

	@RequestMapping(value = "/mfBusFincAppAccountRecord/update")
	public void update(@RequestBody MfBusFincAppAccountRecord mfBusFincAppAccountRecord) throws ServiceException;

	@RequestMapping(value = "/mfBusFincAppAccountRecord/getById")
	public MfBusFincAppAccountRecord getById(@RequestBody MfBusFincAppAccountRecord mfBusFincAppAccountRecord) throws ServiceException;

	@RequestMapping(value = "/mfBusFincAppAccountRecord/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;
	/**
	 *  
	 * 方法描述 用于记录还款账号变更的历史记录
	 * @param mfBusFincAppAccountRecord
	 * @author fc
	 * @date 2018/8/2 17:35
	 */
	@RequestMapping(value = "/mfBusFincAppAccountRecord/updateRefundHistory")
	public void updateRefundHistory (@RequestBody MfBusFincAppAccountRecord mfBusFincAppAccountRecord) throws Exception;

	/**
	 *
	 * 方法描述 根据合同号查询最新修改的银行卡信息
	 * @param mfBusFincAppAccountRecord
	 * @author fc
	 * @date 2018/9/20 11:35
	 */
	@RequestMapping(value = "/mfBusFincAppAccountRecord/getByNewest")
	public MfBusFincAppAccountRecord getByNewest (@RequestBody MfBusFincAppAccountRecord mfBusFincAppAccountRecord) throws Exception;
}
