package  app.component.pss.purchases.feign;
import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pss.purchases.entity.PssBuyOrder;
import app.component.pss.purchases.entity.PssBuyOrderDetail;
import app.util.toolkit.Ipage;

/**
* Title: PssBuyOrderBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Aug 10 13:26:18 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface PssBuyOrderFeign {
	
	@RequestMapping(value = "/pssBuyOrder/insert")
	public void insert(@RequestBody PssBuyOrder pssBuyOrder) throws Exception;
	
	@RequestMapping(value = "/pssBuyOrder/delete")
	public void delete(@RequestBody PssBuyOrder pssBuyOrder) throws Exception;
	
	@RequestMapping(value = "/pssBuyOrder/update")
	public void update(@RequestBody PssBuyOrder pssBuyOrder) throws Exception;
	
	@RequestMapping(value = "/pssBuyOrder/getById")
	public PssBuyOrder getById(@RequestBody PssBuyOrder pssBuyOrder) throws Exception;
	
	@RequestMapping(value = "/pssBuyOrder/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("pssBuyOrder") PssBuyOrder pssBuyOrder) throws Exception;

	@RequestMapping(value = "/pssBuyOrder/getAll")
	public List<PssBuyOrder> getAll(@RequestBody PssBuyOrder pssBuyOrder) throws Exception;

	/**
	 * 保存购货订单
	 * @param pssBuyOrder 购货订单
	 * @param pssBuyOrderDetailList 购货订单明细集合
	 * @return 结果
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssBuyOrder/doSaveBuyOrder")
	public boolean doSaveBuyOrder(@RequestBody PssBuyOrder pssBuyOrder, @RequestParam("pssBuyOrderDetailList")List<PssBuyOrderDetail> pssBuyOrderDetailList) throws Exception;

	/**
	 * 审核购货订单
	 * @param pssBuyOrder 购货订单
	 * @return 结果
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssBuyOrder/doAuditBuyOrder")
	public boolean doAuditBuyOrder(@RequestBody PssBuyOrder pssBuyOrder) throws Exception;
	
	/**
	 * 审核购货订单
	 * @param pssBuyOrder 购货订单
	 * @param pssBuyOrderDetailList 购货订单明细集合
	 * @return 结果
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssBuyOrder/doAuditBuyOrder1")
	public boolean doAuditBuyOrder(@RequestBody PssBuyOrder pssBuyOrder, @RequestParam("pssBuyOrderDetailList")List<PssBuyOrderDetail> pssBuyOrderDetailList) throws Exception;

	/**
	 * 反审核购货订单
	 * @param pssBuyOrder 购货订单
	 * @return 结果
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssBuyOrder/doReverseAuditBuyOrder")
	public boolean doReverseAuditBuyOrder(@RequestBody PssBuyOrder pssBuyOrder) throws Exception;

	/**
	 * 关闭购货订单
	 * @param pssBuyOrder 购货订单
	 * @return 结果
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssBuyOrder/doCloseBuyOrder")
	public boolean doCloseBuyOrder(@RequestBody PssBuyOrder pssBuyOrder) throws Exception;

	/**
	 * 启用购货订单
	 * @param pssBuyOrder 购货订单
	 * @return 结果
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssBuyOrder/doEnableBuyOrder")
	public boolean doEnableBuyOrder(@RequestBody PssBuyOrder pssBuyOrder) throws Exception;
	
	/**
	 * 删除购货订单
	 * @param pssBuyOrder 购货订单
	 * @return 结果
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssBuyOrder/deleteBuyOrder")
	public boolean deleteBuyOrder(@RequestBody PssBuyOrder pssBuyOrder) throws Exception;

	/**
	 * 检查并更新购货订单的订单状态
	 * @param buyOrderNo 购货订单号
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssBuyOrder/checkAndUpdateOrderState")
	public void checkAndUpdateOrderState(@RequestBody String buyOrderNo) throws Exception;

	/**
	 * 检查是否可以生成购货单或购货退货单
	 * @param pssBuyOrder 购货订单
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssBuyOrder/beforeGenerateBill")
	public void beforeGenerateBill(@RequestBody PssBuyOrder pssBuyOrder) throws Exception;

	/**
	 * 根据购货订单号查询购货订单
	 * @param pssBuyOrder 购货订单
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssBuyOrder/getByBuyOrderNo")
	public PssBuyOrder getByBuyOrderNo(@RequestBody PssBuyOrder pssBuyOrder) throws Exception;
	
	/**
	 * 方法描述： 购货订单模版导入
	 * @param file
	 * @return
	 * @throws Exception
	 * String
	 * @author HGX
	 * @date 2018-01-18 下午15:25:03
	 */
	@RequestMapping(value = "/pssBuyOrder/uploadExcel")
	public String uploadExcel(@RequestBody File file,@RequestParam("type")  String type) throws Exception;
	
	/**
	 * 
	 * 方法描述： 购货订单导出Excel
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * HSSFWorkbook
	 * @author hgx
	 * @date 2018-01-18 下午15:05:03
	 */
	@RequestMapping(value = "/pssBuyOrder/downloadToExcel")
	public HSSFWorkbook downloadToExcel(@RequestBody Map<String, String> paramMap) throws Exception;

}
