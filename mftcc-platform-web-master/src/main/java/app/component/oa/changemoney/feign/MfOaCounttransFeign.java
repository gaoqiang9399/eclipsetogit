package app.component.oa.changemoney.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.oa.changemoney.entity.MfOaCounttrans;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

/**
 * Title: MfOaCounttransBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Jun 13 15:21:27 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface MfOaCounttransFeign {
	@RequestMapping(value = "/mfOaCounttrans/insert")
	public MfOaCounttrans insert(@RequestBody MfOaCounttrans mfOaCounttrans) throws Exception;

	@RequestMapping(value = "/mfOaCounttrans/delete")
	public void delete(@RequestBody MfOaCounttrans mfOaCounttrans) throws Exception;

	@RequestMapping(value = "/mfOaCounttrans/update")
	public void update(@RequestBody MfOaCounttrans mfOaCounttrans) throws Exception;

	@RequestMapping(value = "/mfOaCounttrans/getById")
	public MfOaCounttrans getById(@RequestBody MfOaCounttrans mfOaCounttrans) throws Exception;

	@RequestMapping(value = "/mfOaCounttrans/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws Exception;

	@RequestMapping(value = "/mfOaCounttrans/updateSubmit")
	public Result updateSubmit(@RequestBody Map<String,Object> thisObjMap);

	@RequestMapping(value = "/mfOaCounttrans/updateOutChangeMoney")
	public void updateOutChangeMoney(@RequestBody MfOaCounttrans mfOaCounttrans) throws Exception;

	@RequestMapping(value = "/mfOaCounttrans/getCount")
	public int getCount() throws Exception;

}
