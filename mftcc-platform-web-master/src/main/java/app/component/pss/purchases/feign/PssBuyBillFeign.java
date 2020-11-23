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
import app.component.pss.purchases.entity.PssBuyBill;
import app.component.pss.purchases.entity.PssBuyBillDetail;
import app.util.toolkit.Ipage;

/**
* Title: PssBuyBillBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Aug 24 22:06:03 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface PssBuyBillFeign {
	
	@RequestMapping(value = "/pssBuyBill/insert")
	public void insert(@RequestBody PssBuyBill pssBuyBill) throws Exception;
	
	@RequestMapping(value = "/pssBuyBill/delete")
	public void delete(@RequestBody PssBuyBill pssBuyBill) throws Exception;
	
	@RequestMapping(value = "/pssBuyBill/update")
	public void update(@RequestBody PssBuyBill pssBuyBill) throws Exception;
	
	@RequestMapping(value = "/pssBuyBill/getById")
	public PssBuyBill getById(@RequestBody PssBuyBill pssBuyBill) throws Exception;
	
	@RequestMapping(value = "/pssBuyBill/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("pssBuyBill") PssBuyBill pssBuyBill) throws Exception;

	@RequestMapping(value = "/pssBuyBill/getAll")
	public List<PssBuyBill> getAll(@RequestBody PssBuyBill pssBuyBill) throws Exception;

	/**
	 * 保存购货单
	 * @param pssBuyBill 购货单
	 * @param pssBuyBillDetailList 购货单明细集合
	 * @param pssBuySaleExpBillList 采购销售费用清单集合
	 * @return 结果
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssBuyBill/doSaveBuyBill")
	public boolean doSaveBuyBill(@RequestBody PssBuyBill pssBuyBill,
			@RequestParam("pssBuyBillDetailList") List<PssBuyBillDetail> pssBuyBillDetailList,
			@RequestParam("pssBuySaleExpBillList") List<PssBuySaleExpBill> pssBuySaleExpBillList) throws Exception;

	/**
	 * 审核购货单
	 * @param pssBuyBill 购货单
	 * @return 结果
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssBuyBill/doAuditBuyBill")
	public boolean doAuditBuyBill(@RequestBody PssBuyBill pssBuyBill) throws Exception;
	
	/**
	 * 审核购货单
	 * @param pssBuyBill 购货单
	 * @param pssBuyBillDetailList 购货单明细集合
	 * @param pssBuySaleExpBillList 采购销售费用清单集合
	 * @return 结果
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssBuyBill/doAuditBuyBill1")
	public boolean doAuditBuyBill(@RequestBody PssBuyBill pssBuyBill,
			@RequestParam("pssBuyBillDetailList") List<PssBuyBillDetail> pssBuyBillDetailList,
			@RequestParam("pssBuySaleExpBillList") List<PssBuySaleExpBill> pssBuySaleExpBillList) throws Exception;
	
	/**
	 * 反审核购货单
	 * @param pssBuyBill 购货单
	 * @return 结果
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssBuyBill/doReverseAuditBuyBill")
	public boolean doReverseAuditBuyBill(@RequestBody PssBuyBill pssBuyBill) throws Exception;
	
	/**
	 * 删除购货单
	 * @param pssBuyBill 购货单
	 * @return 结果
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssBuyBill/deleteBuyBill")
	public boolean deleteBuyBill(@RequestBody PssBuyBill pssBuyBill) throws Exception;
	
	/**
	 * 检查是否可以生成购货退货单
	 * @param pssBuyBill 购货单
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssBuyBill/beforeGenerateBuyReturnBill")
	public void beforeGenerateBuyReturnBill(@RequestBody PssBuyBill pssBuyBill) throws Exception;
	
	/**
	 * 根据购货单号查询购货单
	 * @param pssBuyBill 购货单
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssBuyBill/getByBuyBillNo")
	public PssBuyBill getByBuyBillNo(@RequestBody PssBuyBill pssBuyBill) throws Exception;
	
	/**
	 * 核销已付款并更新付款状态
	 * @param buyBillNo 购货单号
	 * @param verificationAmount 核销金额
	 * @return 错误信息
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssBuyBill/verifyPaid")
	public String verifyPaid(@RequestBody String buyBillNo,@RequestParam("verificationAmount")  Double verificationAmount) throws Exception;
	
	/**
	 * 方法描述：获取导入EXCEL模板
	 * @param is
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pssBuyBill/getDownFileStream")
	public HSSFWorkbook getDownFileStream(@RequestBody String type)throws Exception;
	
	/**
	 * 方法描述： 购货单模版导入
	 * @param file
	 * @return
	 * @throws Exception
	 * String
	 * @author HGX
	 * @date 2018-01-07 上午11:33:56
	 */
	@RequestMapping(value = "/pssBuyBill/uploadExcel")
	public String uploadExcel(@RequestBody File file,@RequestParam("type")  String type) throws Exception;
	
	/**
	 * 
	 * 方法描述： 购货单导出Excel
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * HSSFWorkbook
	 * @author hgx
	 * @date 2018-01-07 下午15:05:03
	 */
	@RequestMapping(value = "/pssBuyBill/downloadToExcel")
	public HSSFWorkbook downloadToExcel(@RequestBody Map<String, String> paramMap) throws Exception;

}
