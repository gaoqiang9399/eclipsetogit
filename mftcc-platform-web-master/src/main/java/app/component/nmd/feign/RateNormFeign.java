package app.component.nmd.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.nmd.entity.RateNorm;
import app.util.toolkit.Ipage;

/**
 * Title: RateNormBo.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Tue May 17 03:02:13 GMT 2016
 **/

@FeignClient("mftcc-platform-factor")
public interface RateNormFeign {

	@RequestMapping("/rateNorm/insert")
	public void insert(RateNorm rateNorm) throws ServiceException;

	@RequestMapping("/rateNorm/delete")
	public void delete(RateNorm rateNorm) throws ServiceException;

	@RequestMapping("/rateNorm/update")
	public void update(RateNorm rateNorm) throws ServiceException;

	@RequestMapping("/rateNorm/getById")
	public RateNorm getById(RateNorm rateNorm) throws ServiceException;

	@RequestMapping("/rateNorm/findByPage")
	public Ipage findByPage(Ipage ipage, @RequestParam("rateNorm") RateNorm rateNorm) throws ServiceException;

	@RequestMapping("/rateNorm/getNormalRate")
	public String getNormalRate(String month) throws ServiceException;

	@RequestMapping("/rateNorm/getRateNormForLoan")
	public RateNorm getRateNormForLoan(RateNorm rateNorm) throws ServiceException;

}
