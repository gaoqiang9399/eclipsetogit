package app.component.pact.repay.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pact.repay.entity.MfFincRepayDetail;
import app.util.toolkit.Ipage;

/**
 * Title: MfFincRepayDetailBoImplImpl.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Jun 01 09:31:07 CST 2016
 **/
@FeignClient("mftcc-platform-factor")
public interface MfFincRepayDetailFeign {

	@RequestMapping(value = "/mfFincRepayDetail/insert")
	public void insert(@RequestBody MfFincRepayDetail mfFincRepayDetail) throws Exception;

	@RequestMapping(value = "/mfFincRepayDetail/delete")
	public void delete(@RequestBody MfFincRepayDetail mfFincRepayDetail) throws Exception;

	@RequestMapping(value = "/mfFincRepayDetail/update")
	public void update(@RequestBody MfFincRepayDetail mfFincRepayDetail) throws Exception;

	@RequestMapping(value = "/mfFincRepayDetail/getById")
	public MfFincRepayDetail getById(@RequestBody MfFincRepayDetail mfFincRepayDetail) throws Exception;

	@RequestMapping(value = "/mfFincRepayDetail/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("mfFincRepayDetail") MfFincRepayDetail mfFincRepayDetail)
			throws Exception;

	@RequestMapping(value = "/mfFincRepayDetail/getList")
	public List<MfFincRepayDetail> getList(@RequestBody MfFincRepayDetail mfFincRepayDetail) throws Exception;

	@RequestMapping(value = "/mfFincRepayDetail/getFincDetailList")
	public List<MfFincRepayDetail> getFincDetailList(@RequestBody MfFincRepayDetail mfFincRepayDetail) throws Exception;

}
