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
import app.component.pss.sales.entity.PssSaleBill;
import app.component.pss.sales.entity.PssSaleBillDetail;
import app.util.toolkit.Ipage;

/**
* Title: PssSaleBillBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Tue Sep 05 15:01:53 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface PssSaleBillFeign {
	
	@RequestMapping(value = "/pssSaleBill/insert")
	public void insert(@RequestBody PssSaleBill pssSaleBill) throws Exception;
	
	@RequestMapping(value = "/pssSaleBill/delete")
	public void delete(@RequestBody PssSaleBill pssSaleBill) throws Exception;
	
	@RequestMapping(value = "/pssSaleBill/update")
	public void update(@RequestBody PssSaleBill pssSaleBill) throws Exception;
	
	@RequestMapping(value = "/pssSaleBill/getById")
	public PssSaleBill getById(@RequestBody PssSaleBill pssSaleBill) throws Exception;
	
	@RequestMapping(value = "/pssSaleBill/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("pssSaleBill") PssSaleBill pssSaleBill) throws Exception;

	@RequestMapping(value = "/pssSaleBill/getAll")
	public List<PssSaleBill> getAll(@RequestBody PssSaleBill pssSaleBill) throws Exception;
	
	/**
	 * 保存销货单
	 * @param pssSaleBill 销货单
	 * @param pssSaleBillDetailList 销货单明细集合
	 * @param pssBuySaleExpBillList 采购销售费用清单集合
	 * @return 结果
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssSaleBill/doSaveSaleBill")
	public boolean doSaveSaleBill(@RequestBody PssSaleBill pssSaleBill,
			@RequestParam("pssSaleBillDetailList") List<PssSaleBillDetail> pssSaleBillDetailList,
			@RequestParam("pssBuySaleExpBillList") List<PssBuySaleExpBill> pssBuySaleExpBillList) throws Exception;
	
	/**
	 * 审核销货单
	 * @param pssSaleBill 销货单
	 * @return 结果
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssSaleBill/doAuditSaleBill")
	public boolean doAuditSaleBill(@RequestBody PssSaleBill pssSaleBill) throws Exception;
	
	/**
	 * 审核销货单
	 * @param pssSaleBill 销货单
	 * @param pssSaleBillDetailList 销货单明细集合
	 * @param pssBuySaleExpBillList 采购销售费用清单集合
	 * @return 结果
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssSaleBill/doAuditSaleBill1")
	public boolean doAuditSaleBill1(@RequestBody PssSaleBill pssSaleBill,
			@RequestParam("pssSaleBillDetailList") List<PssSaleBillDetail> pssSaleBillDetailList,
			@RequestParam("pssBuySaleExpBillList") List<PssBuySaleExpBill> pssBuySaleExpBillList) throws Exception;
	
	/**
	 * 反审核销货单
	 * @param pssSaleBill 销货单
	 * @return 结果
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssSaleBill/doReverseAuditSaleBill")
	public boolean doReverseAuditSaleBill(@RequestBody PssSaleBill pssSaleBill) throws Exception;
	
	/**
	 * 删除销货单
	 * @param pssSaleBill 销货单
	 * @return 结果
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssSaleBill/deleteSaleBill")
	public boolean deleteSaleBill(@RequestBody PssSaleBill pssSaleBill) throws Exception;

	/**
	 * 检查是否可以生成销货退货单
	 * @param pssSaleBill 销货单
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssSaleBill/beforeGenerateSaleReturnBill")
	public void beforeGenerateSaleReturnBill(@RequestBody PssSaleBill pssSaleBill) throws Exception;
	
	/**
	 * 根据销货单号查询销货单
	 * @param pssSaleBill 销货单
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssSaleBill/getBySaleBillNo")
	public PssSaleBill getBySaleBillNo(@RequestBody PssSaleBill pssSaleBill) throws Exception;
	
	/**
	 * 核销已收款并更新收款状态
	 * @param saleBillNo 销货单号
	 * @param verificationAmount 核销金额
	 * @return 错误信息
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssSaleBill/verifyReceived")
	public String verifyReceived(@RequestBody String saleBillNo,@RequestParam("verificationAmount")  Double verificationAmount) throws Exception;

	/**
	 * 方法描述： 销货单模版导入
	 * @param file
	 * @return
	 * @throws Exception
	 * String
	 * @author HGX
	 * @date 2018-01-24 上午10:25:03
	 */
	@RequestMapping(value = "/pssSaleBill/uploadExcel")
	public String uploadExcel(@RequestBody File file,@RequestParam("type")  String type) throws Exception;
	
	/**
	 * 
	 * 方法描述： 销货单导出Excel
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * HSSFWorkbook
	 * @author hgx
	 * @date 2018-01-24 上午10:43:03
	 */
	@RequestMapping(value = "/pssSaleBill/downloadToExcel")
	public HSSFWorkbook downloadToExcel(@RequestBody Map<String, String> paramMap) throws Exception;
}
