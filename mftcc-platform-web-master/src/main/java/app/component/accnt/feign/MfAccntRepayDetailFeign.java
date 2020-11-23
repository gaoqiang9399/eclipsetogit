package app.component.accnt.feign;

import app.base.ServiceException;
import app.component.accnt.entity.MfAccntRepayDetail;
import app.util.toolkit.Ipage;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Title: MfAccntRepayDetailBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed May 25 18:06:12 CST 2016
 **/

@FeignClient("mftcc-platform-factor")
public interface MfAccntRepayDetailFeign {

	@RequestMapping(value = "/mfAccntRepayDetail/insert")
	public void insert(@RequestBody MfAccntRepayDetail mfAccntRepayDetail) throws ServiceException;

	@RequestMapping(value = "/mfAccntRepayDetail/delete")
	public void delete(@RequestBody MfAccntRepayDetail mfAccntRepayDetail) throws ServiceException;

	@RequestMapping(value = "/mfAccntRepayDetail/update")
	public void update(@RequestBody MfAccntRepayDetail mfAccntRepayDetail) throws ServiceException;

	@RequestMapping(value = "/mfAccntRepayDetail/getById")
	public MfAccntRepayDetail getById(@RequestBody MfAccntRepayDetail mfAccntRepayDetail) throws ServiceException;

	@RequestMapping(value = "/mfAccntRepayDetail/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping(value = "/mfAccntRepayDetail/getList")
	public List<MfAccntRepayDetail> getList(@RequestBody MfAccntRepayDetail mfAccntRepayDetail) throws ServiceException;

	@RequestMapping(value = "/mfAccntRepayDetail/getAccntDetailList")
	public List<MfAccntRepayDetail> getAccntDetailList(@RequestBody MfAccntRepayDetail mfAccntRepayDetail) throws ServiceException;

}
