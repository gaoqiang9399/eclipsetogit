package  app.component.interfaces.mobileinterface.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.interfaces.mobileinterface.entity.MfCusRecommend;
import app.util.toolkit.Ipage;

/**
* Title: MfCusRecommendBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Fri Sep 15 15:33:18 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfCusRecommendFeign{
	
	@RequestMapping(value = "/mfCusRecommend/insert")
	public void insert(@RequestBody  MfCusRecommend mfCusRecommend) throws Exception;

	@RequestMapping(value = "/mfCusRecommend/delete")
	public void delete(@RequestBody  MfCusRecommend mfCusRecommend) throws Exception;

	@RequestMapping(value = "/mfCusRecommend/update")
	public void update(@RequestBody  MfCusRecommend mfCusRecommend) throws Exception;

	@RequestMapping(value = "/mfCusRecommend/getById")
	public MfCusRecommend getById(@RequestBody  MfCusRecommend mfCusRecommend) throws Exception;

	@RequestMapping(value = "/mfCusRecommend/findByPage")
	public Ipage findByPage(@RequestBody  Ipage ipage,@RequestParam("mfCusRecommend") MfCusRecommend mfCusRecommend) throws Exception;

	@RequestMapping(value = "/mfCusRecommend/findCommentByPage")
	public Ipage findCommentByPage(@RequestBody  Ipage ipage) throws Exception;

	@RequestMapping(value = "/mfCusRecommend/findWxByPage")
	public Ipage findWxByPage(@RequestBody  Ipage ipage,@RequestParam("mfCusRecommend") MfCusRecommend mfCusRecommend) throws Exception;

	@RequestMapping(value = "/mfCusRecommend/findWxCommentByPage")
	public Ipage findWxCommentByPage(@RequestBody  Ipage ipage,@RequestParam("mfCusRecommend") MfCusRecommend mfCusRecommend) throws Exception;

	@RequestMapping(value = "/mfCusRecommend/getperCountAndLoanAmt")
	public Map<String, Object> getperCountAndLoanAmt(@RequestBody  MfCusRecommend mfCusRecommend) throws Exception;

	@RequestMapping(value = "/mfCusRecommend/getPutoutAmtAndNum")
	public Map<String, Object> getPutoutAmtAndNum(@RequestBody  MfCusRecommend mfCusRecommend) throws Exception;

	@RequestMapping(value = "/mfCusRecommend/remittanceMoney")
	public Map<String, Object> remittanceMoney(@RequestBody  MfCusRecommend mfCusRecommend) throws Exception;

	@RequestMapping(value = "/mfCusRecommend/getCusRecommendList")
	public Map<String, Object> getCusRecommendList(@RequestBody  MfCusRecommend mfCusRecommend) throws Exception;

	@RequestMapping(value = "/mfCusRecommend/getCusRecommends")
	public List<MfCusRecommend> getCusRecommends(@RequestBody  MfCusRecommend mfCusRecommend) throws Exception;

	@RequestMapping(value = "/mfCusRecommend/getCusRecommendListByPage")
	public Map<String, Object> getCusRecommendListByPage(@RequestBody  Ipage ipage,@RequestParam("mfCusRecommend") MfCusRecommend mfCusRecommend) throws Exception;

	@RequestMapping(value = "/mfCusRecommend/getPerCountAndLoanAmtByCusTel")
	public Map<String, Object> getPerCountAndLoanAmtByCusTel(@RequestBody  String rdCusTel) throws Exception;
	
}
