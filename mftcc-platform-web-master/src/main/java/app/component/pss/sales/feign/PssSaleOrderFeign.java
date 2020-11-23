package  app.component.pss.sales.feign;
import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pss.sales.entity.PssSaleOrder;
import app.component.pss.sales.entity.PssSaleOrderDetail;
import app.util.toolkit.Ipage;

/**
* Title: PssSaleOrderBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Aug 31 16:53:40 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface PssSaleOrderFeign {
	
	@RequestMapping(value = "/pssSaleOrder/insert")
	public void insert(@RequestBody PssSaleOrder pssSaleOrder) throws Exception;
	
	@RequestMapping(value = "/pssSaleOrder/delete")
	public void delete(@RequestBody PssSaleOrder pssSaleOrder) throws Exception;
	
	@RequestMapping(value = "/pssSaleOrder/update")
	public void update(@RequestBody PssSaleOrder pssSaleOrder) throws Exception;
	
	@RequestMapping(value = "/pssSaleOrder/getById")
	public PssSaleOrder getById(@RequestBody PssSaleOrder pssSaleOrder) throws Exception;
	
	@RequestMapping(value = "/pssSaleOrder/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("pssSaleOrder") PssSaleOrder pssSaleOrder) throws Exception;

	@RequestMapping(value = "/pssSaleOrder/getAll")
	public List<PssSaleOrder> getAll(@RequestBody PssSaleOrder pssSaleOrder) throws Exception;

	/**
	 * 保存销货订单
	 * @param pssSaleOrder 销货订单
	 * @param pssSaleOrderDetailList 销货订单明细集合
	 * @return 结果
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssSaleOrder/doSaveSaleOrder")
	public boolean doSaveSaleOrder(@RequestBody PssSaleOrder pssSaleOrder, @RequestParam("pssSaleOrderDetailLists")List<PssSaleOrderDetail> pssSaleOrderDetailList) throws Exception;
	
	/**
	 * 审核销货订单
	 * @param pssSaleOrder 销货订单
	 * @return 结果
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssSaleOrder/doAuditSaleOrder")
	public boolean doAuditSaleOrder(@RequestBody PssSaleOrder pssSaleOrder) throws Exception;
	
	/**
	 * 审核销货订单
	 * @param pssSaleOrder 销货订单
	 * @param pssSaleOrderDetailList 销货订单明细集合
	 * @return 结果
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssSaleOrder/doAuditSaleOrder1")
	public boolean doAuditSaleOrder(@RequestBody PssSaleOrder pssSaleOrder, @RequestParam("pssSaleOrderDetailList")List<PssSaleOrderDetail> pssSaleOrderDetailList) throws Exception;

	/**
	 * 反审核销货订单
	 * @param pssSaleOrder 销货订单
	 * @return 结果
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssSaleOrder/doReverseAuditSaleOrder")
	public boolean doReverseAuditSaleOrder(@RequestBody PssSaleOrder pssSaleOrder) throws Exception;

	/**
	 * 关闭销货订单
	 * @param pssSaleOrder 销货订单
	 * @return 结果
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssSaleOrder/doCloseSaleOrder")
	public boolean doCloseSaleOrder(@RequestBody PssSaleOrder pssSaleOrder) throws Exception;

	/**
	 * 启用销货订单
	 * @param pssSaleOrder 销货订单
	 * @return 结果
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssSaleOrder/doEnableSaleOrder")
	public boolean doEnableSaleOrder(@RequestBody PssSaleOrder pssSaleOrder) throws Exception;
	
	/**
	 * 锁定销货订单
	 * @param pssSaleOrder 销货订单
	 * @return 结果
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssSaleOrder/doLockSaleOrder")
	public boolean doLockSaleOrder(@RequestBody PssSaleOrder pssSaleOrder) throws Exception;

	/**
	 * 解锁销货订单
	 * @param pssSaleOrder 销货订单
	 * @return 结果
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssSaleOrder/doUnlockSaleOrder")
	public boolean doUnlockSaleOrder(@RequestBody PssSaleOrder pssSaleOrder) throws Exception;
	
	/**
	 * 删除销货订单
	 * @param pssSaleOrder 销货订单
	 * @return 结果
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssSaleOrder/deleteSaleOrder")
	public boolean deleteSaleOrder(@RequestBody PssSaleOrder pssSaleOrder) throws Exception;
	
	/**
	 * 检查并更新销货订单的订单状态
	 * @param saleOrderNo 销货订单号
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssSaleOrder/checkAndUpdateOrderState")
	public void checkAndUpdateOrderState(@RequestBody String saleOrderNo) throws Exception;

	/**
	 * 检查是否可以生成销货单或销货退货单
	 * @param pssSaleOrder
	 */
	@RequestMapping(value = "/pssSaleOrder/beforeGenerateBill")
	public void beforeGenerateBill(@RequestBody PssSaleOrder pssSaleOrder) throws Exception;
	
	/**
	 * 根据销货订单号查询销货订单
	 * @param pssSaleOrder 销货订单
	 * @return
	 */
	@RequestMapping(value = "/pssSaleOrder/getBySaleOrderNo")
	public PssSaleOrder getBySaleOrderNo(@RequestBody PssSaleOrder pssSaleOrder) throws Exception;

	/**
	 * 方法描述： 销货订单模版导入
	 * @param file
	 * @return
	 * @throws Exception
	 * String
	 * @author HGX
	 * @date 2018-01-23 下午16:25:03
	 */
	@RequestMapping(value = "/pssSaleOrder/uploadExcel")
	public String uploadExcel(@RequestBody File file,@RequestParam("type")  String type) throws Exception;
	
	/**
	 * 
	 * 方法描述： 销货订单导出Excel
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * HSSFWorkbook
	 * @author hgx
	 * @date 2018-01-23 下午16:43:03
	 */
	@RequestMapping(value = "/pssSaleOrder/downloadToExcel")
	public HSSFWorkbook downloadToExcel(@RequestBody Map<String, String> paramMap) throws Exception;
}
