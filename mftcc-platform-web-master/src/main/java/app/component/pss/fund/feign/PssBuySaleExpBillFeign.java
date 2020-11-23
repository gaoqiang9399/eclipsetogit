package app.component.pss.fund.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pss.fund.entity.PssBuySaleExpBill;
import app.component.pss.purchases.entity.PssBuyBill;
import app.component.pss.purchases.entity.PssBuyReturnBill;
import app.component.pss.sales.entity.PssSaleBill;
import app.component.pss.sales.entity.PssSaleReturnBill;
import app.util.toolkit.Ipage;

/**
 * Title: PssBuySaleExpBillBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Nov 09 15:53:42 CST 2017
 **/

@FeignClient("mftcc-platform-factor")
public interface PssBuySaleExpBillFeign {

	@RequestMapping(value = "/pssBuySaleExpBill/insert")
	public void insert(@RequestBody PssBuySaleExpBill pssBuySaleExpBill) throws Exception ;

	@RequestMapping(value = "/pssBuySaleExpBill/delete")
	public void delete(@RequestBody PssBuySaleExpBill pssBuySaleExpBill) throws Exception ;

	@RequestMapping(value = "/pssBuySaleExpBill/update")
	public void update(@RequestBody PssBuySaleExpBill pssBuySaleExpBill) throws Exception ;

	@RequestMapping(value = "/pssBuySaleExpBill/getById")
	public PssBuySaleExpBill getById(@RequestBody PssBuySaleExpBill pssBuySaleExpBill) throws Exception ;

	@RequestMapping(value = "/pssBuySaleExpBill/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,
			@RequestParam("pssBuySaleExpBill") PssBuySaleExpBill pssBuySaleExpBill) throws Exception ;

	@RequestMapping(value = "/pssBuySaleExpBill/getAll")
	public List<PssBuySaleExpBill> getAll(@RequestBody PssBuySaleExpBill pssBuySaleExpBill) throws Exception ;

	@RequestMapping(value = "/pssBuySaleExpBill/findByPageBySBNo")
	public Ipage findByPageBySBNo(@RequestBody Ipage ipage,
			@RequestParam("pssBuySaleExpBill") PssBuySaleExpBill pssBuySaleExpBill) throws Exception ;

	/**
	 * 采购费用清单接口（保存 or 审核） parm 购货单主信息pssBuyBill， 费用list， 是否分摊isApportion
	 * 
	 */
	@RequestMapping(value = "/pssBuySaleExpBill/doPssBuyExp")
	public String doPssBuyExp(@RequestBody PssBuyBill pssBuyBill, @RequestParam("list") List<PssBuySaleExpBill> list,
			@RequestParam("isApportion") boolean isApportion) throws Exception ;

	/**
	 * 销售费用清单接口（保存 or 审核） parm 购货单主信息PssSaleBill， 费用list
	 * 
	 */
	@RequestMapping(value = "/pssBuySaleExpBill/doPssSaleExp")
	public String doPssSaleExp(@RequestBody PssSaleBill pssSaleBill, @RequestParam("list") List<PssBuySaleExpBill> list)
			throws Exception ;

	/**
	 * 购货退货费用清单接口（保存 or 审核） parm 购货单主信息pssBuyReturnBill， 费用list
	 * 
	 */
	@RequestMapping(value = "/pssBuySaleExpBill/doPssBuyReExp")
	public String doPssBuyReExp(@RequestBody PssBuyReturnBill pssBuyReturnBill,
			@RequestParam("list") List<PssBuySaleExpBill> list) throws Exception ;

	/**
	 * 销货退货费用清单接口（保存 or 审核） parm 购货单主信息pssSaleReturnBill， 费用list
	 * 
	 */
	@RequestMapping(value = "/pssBuySaleExpBill/doPssSaleReExp")
	public String doPssSaleReExp(@RequestBody PssSaleReturnBill pssSaleReturnBill,
			@RequestParam("list") List<PssBuySaleExpBill> list) throws Exception ;

	/**
	 * 采购费用清单接口（反审核） parm 购货单主信息pssBuyBill
	 * 
	 */
	@RequestMapping(value = "/pssBuySaleExpBill/doPssBuyExpForReAudit")
	public void doPssBuyExpForReAudit(@RequestBody PssBuyBill pssBuyBill) throws Exception ;

	/**
	 * 销货费用清单接口（反审核） parm 购货单主信息pssSaleBill
	 * 
	 */
	@RequestMapping(value = "/pssBuySaleExpBill/doPssSaleExpForReAudit")
	public void doPssSaleExpForReAudit(@RequestBody PssSaleBill pssSaleBill) throws Exception ;

	/**
	 * 购货退货费用清单接口（反审核） parm 购货单主信息pssBuyReturnBill
	 * 
	 */
	@RequestMapping(value = "/pssBuySaleExpBill/doPssBuyReExpForReAudit")
	public void doPssBuyReExpForReAudit(@RequestBody PssBuyReturnBill pssBuyReturnBill) throws Exception ;

	/**
	 * 销货退货费用清单接口（反审核） parm 购货单主信息pssSaleReturnBill
	 * 
	 */
	@RequestMapping(value = "/pssBuySaleExpBill/doPssSaleReExpForReAudit")
	public void doPssSaleReExpForReAudit(@RequestBody PssSaleReturnBill pssSaleReturnBill) throws Exception ;

	/**
	 * 采购费用清单接口（批量审核） parm 购货单主信息pssBuyBill
	 * 
	 */
	@RequestMapping(value = "/pssBuySaleExpBill/doPssBuyExpForBatchAudit")
	public void doPssBuyExpForBatchAudit(@RequestBody PssBuyBill pssBuyBill) throws Exception ;

	/**
	 * 采购费用清单接口（批量审核） parm 购货单主信息pssBuyBill
	 * 
	 */
	@RequestMapping(value = "/pssBuySaleExpBill/doPssSaleExpForBatchAudit")
	public void doPssSaleExpForBatchAudit(@RequestBody PssSaleBill pssSaleBill) throws Exception ;

	/**
	 * 采购费用清单接口（批量审核） parm 购货单主信息pssBuyBill
	 * 
	 */
	@RequestMapping(value = "/pssBuySaleExpBill/doPssBuyReExpForBatchAudit")
	public void doPssBuyReExpForBatchAudit(@RequestBody PssBuyReturnBill pssBuyReturnBill) throws Exception ;

	/**
	 * 采购费用清单接口（批量审核） parm 购货单主信息pssBuyBill
	 * 
	 */
	@RequestMapping(value = "/pssBuySaleExpBill/doPssSaleReExpForBatchAudit")
	public void doPssSaleReExpForBatchAudit(@RequestBody PssSaleReturnBill pssSaleReturnBill) throws Exception ;

	/**
	 * 采购费用清单接口（删除） parm 购货单主信息pssBuyBill
	 * 
	 */
	@RequestMapping(value = "/pssBuySaleExpBill/deletePssBuyExpBySRBillNo")
	public void deletePssBuyExpBySRBillNo(@RequestBody PssBuyBill pssBuyBill) throws Exception ;

	/**
	 * 销售费用清单接口（删除） parm 购货单主信息pssSaleBill
	 * 
	 */
	@RequestMapping(value = "/pssBuySaleExpBill/deletePssSaleExpBySRBillNo")
	public void deletePssSaleExpBySRBillNo(@RequestBody PssSaleBill pssSaleBill) throws Exception ;

	/**
	 * 购货退货费用清单接口（删除） parm 购货单主信息pssBuyReturnBill
	 * 
	 */
	@RequestMapping(value = "/pssBuySaleExpBill/deletePssBuyReExpBySRBillNo")
	public void deletePssBuyReExpBySRBillNo(@RequestBody PssBuyReturnBill pssBuyReturnBill) throws Exception ;

	/**
	 * 销货退货费用清单接口（删除） parm 购货单主信息pssSaleReturnBill
	 * 
	 */
	@RequestMapping(value = "/pssBuySaleExpBill/deletePssSaleReExpBySRBillNo")
	public void deletePssSaleReExpBySRBillNo(@RequestBody PssSaleReturnBill pssSaleReturnBill) throws Exception ;
}
