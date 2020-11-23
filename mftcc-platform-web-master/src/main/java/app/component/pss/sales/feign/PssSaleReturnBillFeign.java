package  app.component.pss.sales.feign;
import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pss.fund.entity.PssBuySaleExpBill;
import app.component.pss.sales.entity.PssSaleReturnBill;
import app.component.pss.sales.entity.PssSaleReturnBillDetail;
import app.util.toolkit.Ipage;

/**
* Title: PssSaleReturnBillBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Sep 14 15:30:38 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface PssSaleReturnBillFeign {
	
	@RequestMapping(value = "/pssSaleReturnBill/insert")
	public void insert(@RequestBody PssSaleReturnBill pssSaleReturnBill) throws Exception;
	
	@RequestMapping(value = "/pssSaleReturnBill/delete")
	public void delete(@RequestBody PssSaleReturnBill pssSaleReturnBill) throws Exception;
	
	@RequestMapping(value = "/pssSaleReturnBill/update")
	public void update(@RequestBody PssSaleReturnBill pssSaleReturnBill) throws Exception;
	
	@RequestMapping(value = "/pssSaleReturnBill/getById")
	public PssSaleReturnBill getById(@RequestBody PssSaleReturnBill pssSaleReturnBill) throws Exception;
	
	@RequestMapping(value = "/pssSaleReturnBill/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("pssSaleReturnBill") PssSaleReturnBill pssSaleReturnBill) throws Exception;

	@RequestMapping(value = "/pssSaleReturnBill/getAll")
	public List<PssSaleReturnBill> getAll(@RequestBody PssSaleReturnBill pssSaleReturnBill) throws Exception;
	
	/**
	 * 保存销货退货单
	 * @param pssSaleReturnBill 销货退货单
	 * @param pssSaleReturnBillDetailList 销货退货单明细
	 * @param pssBuySaleExpBillList 采购销售费用清单集合
	 * @return 结果
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssSaleReturnBill/doSaveSaleReturnBill")
	public boolean doSaveSaleReturnBill(@RequestBody PssSaleReturnBill pssSaleReturnBill,
			@RequestParam("pssSaleReturnBillDetailList") List<PssSaleReturnBillDetail> pssSaleReturnBillDetailList,
			@RequestParam("pssBuySaleExpBillList") List<PssBuySaleExpBill> pssBuySaleExpBillList) throws Exception;

	/**
	 * 审核销货退货单
	 * @param pssSaleReturnBill 销货退货单
	 * @param pssSaleReturnBillDetailList 销货退货单明细
	 * @param pssBuySaleExpBillList 采购销售费用清单集合
	 * @return 结果
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssSaleReturnBill/doAuditSaleReturnBill1")
	public boolean doAuditSaleReturnBill1(@RequestBody PssSaleReturnBill pssSaleReturnBill,
			@RequestParam("pssSaleReturnBillDetailList") List<PssSaleReturnBillDetail> pssSaleReturnBillDetailList,
			@RequestParam("pssBuySaleExpBillList") List<PssBuySaleExpBill> pssBuySaleExpBillList) throws Exception;

	/**
	 * 审核销货退货单
	 * @param pssSaleReturnBill 销货退货单
	 * @return 结果
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssSaleReturnBill/doAuditSaleReturnBill")
	public boolean doAuditSaleReturnBill(@RequestBody PssSaleReturnBill pssSaleReturnBill) throws Exception;

	/**
	 * 反审核销货退货单
	 * @param pssSaleReturnBill 销货退货单
	 * @return 结果
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssSaleReturnBill/doReverseAuditSaleReturnBill")
	public boolean doReverseAuditSaleReturnBill(@RequestBody PssSaleReturnBill pssSaleReturnBill) throws Exception;

	/**
	 * 删除销货退货单
	 * @param pssSaleReturnBill 销货退货单
	 * @return 结果
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssSaleReturnBill/deleteSaleReturnBill")
	public boolean deleteSaleReturnBill(@RequestBody PssSaleReturnBill pssSaleReturnBill) throws Exception;

	/**
	 * 根据销货退货单号查询销货退货单
	 * @param pssSaleBill 销货退货单
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssSaleReturnBill/getBySaleReturnBillNo")
	public PssSaleReturnBill getBySaleReturnBillNo(@RequestBody PssSaleReturnBill pssSaleReturnBill) throws Exception;
	
	/**
	 * 核销已退款并更新退款状态
	 * @param saleReturnBillNo 销货退货单号
	 * @param verificationAmount 核销金额
	 * @return 错误信息
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssSaleReturnBill/verifyRefunded")
	public String verifyRefunded(@RequestBody String saleReturnBillNo,@RequestParam("verificationAmount")  Double verificationAmount) throws Exception;
	
	/**
	 * 方法描述： 销货退货单模版导入
	 * @param file
	 * @return
	 * @throws Exception
	 * String
	 * @author HGX
	 * @date 2018-01-24 下午14:25:03
	 */
	@RequestMapping(value = "/pssSaleReturnBill/uploadExcel")
	public String uploadExcel(@RequestBody File file,@RequestParam("type")  String type) throws Exception;
	
	/**
	 * 
	 * 方法描述： 销货退货单导出Excel
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * HSSFWorkbook
	 * @author hgx
	 * @date 2018-01-24 下午14:43:03
	 */
	@RequestMapping(value = "/pssSaleReturnBill/downloadToExcel")
	public HSSFWorkbook downloadToExcel(@RequestBody Map<String, String> paramMap) throws Exception;
}
