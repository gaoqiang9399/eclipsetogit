package  app.component.hzey.coupon.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.hzey.coupon.entity.MfDiscountManage;
import app.util.toolkit.Ipage;

/**
* Title: MfDiscountManageBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Sun Jul 23 10:28:46 CST 2017
**/
@FeignClient("mftcc-platform-factor")
public interface MfDiscountManageFeign{
	
	@RequestMapping(value = "/mfDiscountManage/insert")
	public void insert(@RequestBody  MfDiscountManage mfDiscountManage) throws Exception;

	@RequestMapping(value = "/mfDiscountManage/insertList")
	public void insertList(@RequestBody  List<MfDiscountManage> mfDiscountManages) throws Exception;

	@RequestMapping(value = "/mfDiscountManage/delete")
	public void delete(@RequestBody  MfDiscountManage mfDiscountManage) throws Exception;

	@RequestMapping(value = "/mfDiscountManage/update")
	public void update(@RequestBody  MfDiscountManage mfDiscountManage) throws Exception;

	@RequestMapping(value = "/mfDiscountManage/getById")
	public MfDiscountManage getById(@RequestBody  MfDiscountManage mfDiscountManage) throws Exception;

	@RequestMapping(value = "/mfDiscountManage/findByPage")
	public Ipage findByPage(@RequestBody  Ipage ipage,@RequestParam("mfDiscountManage") MfDiscountManage mfDiscountManage) throws Exception;

	@RequestMapping(value = "/mfDiscountManage/getMfDiscountManageListByCusNo")
	public List<MfDiscountManage> getMfDiscountManageListByCusNo(@RequestBody  MfDiscountManage mfDiscountManage) throws Exception;

	@RequestMapping(value = "/mfDiscountManage/getDiscountManageListByCus")
	public Ipage getDiscountManageListByCus(@RequestBody  Ipage ipage) throws Exception;

	@RequestMapping(value = "/mfDiscountManage/getMaxDiscountAmtByCusNo")
	public Map<String, Object> getMaxDiscountAmtByCusNo(@RequestBody  String cusNo) throws Exception;
	
	
}
