package  app.component.pss.purchases.feign;
import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pss.fund.entity.PssBuySaleExpBill;
import app.component.pss.purchases.entity.PssBuyReturnBill;
import app.component.pss.purchases.entity.PssBuyReturnBillDetail;
import app.util.toolkit.Ipage;

/**
* Title: PssBuyReturnBillBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Sep 14 14:19:26 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface PssBuyReturnBillFeign {
	
	@RequestMapping(value = "/pssBuyReturnBill/insert")
	public void insert(@RequestBody PssBuyReturnBill pssBuyReturnBill) throws Exception;
	
	@RequestMapping(value = "/pssBuyReturnBill/delete")
	public void delete(@RequestBody PssBuyReturnBill pssBuyReturnBill) throws Exception;
	
	@RequestMapping(value = "/pssBuyReturnBill/update")
	public void update(@RequestBody PssBuyReturnBill pssBuyReturnBill) throws Exception;
	
	@RequestMapping(value = "/pssBuyReturnBill/getById")
	public PssBuyReturnBill getById(@RequestBody PssBuyReturnBill pssBuyReturnBill) throws Exception;
	
	@RequestMapping(value = "/pssBuyReturnBill/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("pssBuyReturnBill") PssBuyReturnBill pssBuyReturnBill) throws Exception;

	@RequestMapping(value = "/pssBuyReturnBill/getAll")
	public List<PssBuyReturnBill> getAll(@RequestBody PssBuyReturnBill pssBuyReturnBill) throws Exception;

	/**
	 * 保存购货退货单
	 * @param pssBuyReturnBill 购货退货单
	 * @param pssBuyReturnBillDetailList 购货退货单明细
	 * @param pssBuySaleExpBillList 采购销售费用清单集合
	 * @return 结果
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssBuyReturnBill/doSaveBuyReturnBill")
	public boolean doSaveBuyReturnBill(@RequestBody PssBuyReturnBill pssBuyReturnBill,
			@RequestParam("pssBuyReturnBillDetailList") List<PssBuyReturnBillDetail> pssBuyReturnBillDetailList,
			@RequestParam("pssBuySaleExpBillList") List<PssBuySaleExpBill> pssBuySaleExpBillList) throws Exception;

	/**
	 * 审核购货退货单
	 * @param pssBuyReturnBill 购货退货单
	 * @param pssBuyReturnBillDetailList 购货退货单明细
	 * @param pssBuySaleExpBillList 采购销售费用清单集合
	 * @return 结果
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssBuyReturnBill/doAuditBuyReturnBill1")
	public boolean doAuditBuyReturnBill1(@RequestBody PssBuyReturnBill pssBuyReturnBill,
			@RequestParam("pssBuyReturnBillDetailList") List<PssBuyReturnBillDetail> pssBuyReturnBillDetailList,
			@RequestParam("pssBuySaleExpBillList") List<PssBuySaleExpBill> pssBuySaleExpBillList) throws Exception;

	/**
	 * 审核购货退货单
	 * @param pssBuyReturnBill 购货退货单
	 * @return 结果
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssBuyReturnBill/doAuditBuyReturnBill")
	public boolean doAuditBuyReturnBill(@RequestBody PssBuyReturnBill pssBuyReturnBill) throws Exception;

	/**
	 * 反审核购货退货单
	 * @param pssBuyReturnBill 购货退货单
	 * @return 结果
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssBuyReturnBill/doReverseAuditBuyReturnBill")
	public boolean doReverseAuditBuyReturnBill(@RequestBody PssBuyReturnBill pssBuyReturnBill) throws Exception;

	/**
	 * 删除购货退货单
	 * @param pssBuyReturnBill 购货退货单
	 * @return 结果
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssBuyReturnBill/deleteBuyReturnBill")
	public boolean deleteBuyReturnBill(@RequestBody PssBuyReturnBill pssBuyReturnBill) throws Exception;

	/**
	 * 根据购货退货单号查询购货退货单
	 * @param pssBuyBill 购货退货单
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssBuyReturnBill/getByBuyReturnBillNo")
	public PssBuyReturnBill getByBuyReturnBillNo(@RequestBody PssBuyReturnBill pssBuyReturnBill) throws Exception;
	
	/**
	 * 核销已退款并更新退款状态
	 * @param buyReturnBillNo 购货退货单号
	 * @param verificationAmount 核销金额
	 * @return 错误信息
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssBuyReturnBill/verifyRefunded")
	public String verifyRefunded(@RequestBody String buyReturnBillNo,@RequestParam("verificationAmount")  Double verificationAmount) throws Exception;
	
	/**
	 * 方法描述： 购货退货单模版导入
	 * @param file
	 * @return
	 * @throws Exception
	 * String
	 * @author HGX
	 * @date 2018-01-23 下午14:25:03
	 */
	@RequestMapping(value = "/pssBuyReturnBill/uploadExcel")
	public String uploadExcel(@RequestBody File file,@RequestParam("type")  String type) throws Exception;
	
	/**
	 * 
	 * 方法描述： 购货退货单导出Excel
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * HSSFWorkbook
	 * @author hgx
	 * @date 2018-01-23 下午15:05:03
	 */
	@RequestMapping(value = "/pssBuyReturnBill/downloadToExcel")
	public HSSFWorkbook downloadToExcel(@RequestBody Map<String, String> paramMap) throws Exception;
}
