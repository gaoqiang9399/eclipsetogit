package app.component.accnt.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.accnt.entity.MfAccntTransferHis;
import app.util.toolkit.Ipage;

/**
 * Title: MfAccntTransferHisBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri May 27 09:20:34 CST 2016
 **/
@FeignClient("mftcc-platform-factor")
public interface MfAccntTransferHisFeign {
	@RequestMapping(value = "/mfAccntTransferHis/insert")
	public void insert(@RequestBody MfAccntTransferHis mfAccntTransferHis) throws ServiceException;

	@RequestMapping(value = "/mfAccntTransferHis/delete")
	public void delete(@RequestBody MfAccntTransferHis mfAccntTransferHis) throws ServiceException;

	@RequestMapping(value = "/mfAccntTransferHis/update")
	public void update(@RequestBody MfAccntTransferHis mfAccntTransferHis) throws ServiceException;

	@RequestMapping(value = "/mfAccntTransferHis/getById")
	public MfAccntTransferHis getById(@RequestBody MfAccntTransferHis mfAccntTransferHis) throws ServiceException;

	@RequestMapping(value = "/mfAccntTransferHis/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfAccntTransferHis") MfAccntTransferHis mfAccntTransferHis) throws ServiceException;

}
