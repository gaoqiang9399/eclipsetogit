package  app.component.pss.sales.feign;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pss.sales.entity.PssSaleOrderDetail;
import app.util.toolkit.Ipage;

/**
* Title: PssSaleOrderDetailBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Aug 31 16:55:14 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface PssSaleOrderDetailFeign {
	
	@RequestMapping(value = "/pssSaleOrderDetail/insert")
	public void insert(@RequestBody PssSaleOrderDetail pssSaleOrderDetail) throws Exception;
	
	@RequestMapping(value = "/pssSaleOrderDetail/delete")
	public void delete(@RequestBody PssSaleOrderDetail pssSaleOrderDetail) throws Exception;
	
	@RequestMapping(value = "/pssSaleOrderDetail/update")
	public void update(@RequestBody PssSaleOrderDetail pssSaleOrderDetail) throws Exception;
	
	@RequestMapping(value = "/pssSaleOrderDetail/getById")
	public PssSaleOrderDetail getById(@RequestBody PssSaleOrderDetail pssSaleOrderDetail) throws Exception;
	
	@RequestMapping(value = "/pssSaleOrderDetail/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("pssSaleOrderDetail") PssSaleOrderDetail pssSaleOrderDetail) throws Exception;

	@RequestMapping(value = "/pssSaleOrderDetail/getAll")
	public List<PssSaleOrderDetail> getAll(@RequestBody PssSaleOrderDetail pssSaleOrderDetail) throws Exception;

	/**
	 * 获取以销定购列表数据
	 * @param ipage
	 * @param pssSaleOrderDetail 销货订单明细
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssSaleOrderDetail/findToBuyOrderByPage")
	public Ipage findToBuyOrderByPage(@RequestBody Ipage ipage,@RequestParam("pssSaleOrderDetail")  PssSaleOrderDetail pssSaleOrderDetail) throws Exception;

	@RequestMapping(value = "/pssSaleOrderDetail/getCount")
	public int getCount(@RequestBody PssSaleOrderDetail pssSaleOrderDetail) throws Exception;
	
//	@RequestMapping(value = "/pssSaleOrderDetail/beforeGenerateBuyOrder")
	/*public void beforeGenerateBuyOrder(@RequestBody String saleOrderDetailDatas) throws Exception;*/

}
