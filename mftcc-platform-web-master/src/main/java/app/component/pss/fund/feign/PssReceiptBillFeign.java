package app.component.pss.fund.feign;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pss.fund.entity.PssReceiptBill	;
import app.component.pss.fund.entity.PssReceiptDetailBill;
import app.component.pss.fund.entity.PssSourceDetailBill;
import app.util.toolkit.Ipage;

/**
 * Title: PssReceiptBillBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Sep 20 13:28:45 CST 2017
 **/

@FeignClient("mftcc-platform-factor")
public interface PssReceiptBillFeign {

	// @RequestMapping(value = "/pssReceiptBill/insert")public void
	// insert(@RequestBody
	// PssReceiptBill pssReceiptBill, List<PssReceiptDetailBill> bdTableList,
	// List<PssSourceDetailBill> rdTableList) throws
	// Exception;

	@RequestMapping(value = "/pssReceiptBill/insert")
	public Map<String, String> insert(@RequestBody PssReceiptBill pssReceiptBill,
			@RequestParam("bdTableList") List<PssReceiptDetailBill> bdTableList,
			@RequestParam("rdTableList") List<PssSourceDetailBill> rdTableList) throws Exception ;

	@RequestMapping(value = "/pssReceiptBill/delete")
	public void delete(@RequestBody PssReceiptBill pssReceiptBill) throws Exception ;

	// @RequestMapping(value = "/pssReceiptBill/insert")public void
	// update(@RequestBody
	// PssReceiptBill pssReceiptBill, List<PssReceiptDetailBill> bdTableList,
	// List<PssSourceDetailBill> rdTableList) throws
	// Exception;

	@RequestMapping(value = "/pssReceiptBill/update")
	public Map<String, String> update(@RequestBody PssReceiptBill pssReceiptBill,
			@RequestParam("bdTableList") List<PssReceiptDetailBill> bdTableList,
			@RequestParam("rdTableList") List<PssSourceDetailBill> rdTableList) throws Exception ;

	@RequestMapping(value = "/pssReceiptBill/getById")
	public PssReceiptBill getById(@RequestBody PssReceiptBill pssReceiptBill) throws Exception ;

	@RequestMapping(value = "/pssReceiptBill/getByIdOrNo")
	public PssReceiptBill getByIdOrNo(@RequestBody PssReceiptBill pssReceiptBill) throws Exception ;

	@RequestMapping(value = "/pssReceiptBill/getByRBNo")
	public PssReceiptBill getByRBNo(@RequestBody PssReceiptBill pssReceiptBill) throws Exception ;

	@RequestMapping(value = "/pssReceiptBill/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("pssReceiptBill") PssReceiptBill pssReceiptBill)
			throws Exception ;

	@RequestMapping(value = "/pssReceiptBill/getAll")
	public List<PssReceiptBill> getAll(@RequestBody PssReceiptBill pssReceiptBill) throws Exception ;

	// 批量删除
	@RequestMapping(value = "/pssReceiptBill/deleteRBBatch")
	public Map<String, String> deleteRBBatch(@RequestBody Map<String, String> formMap) throws Exception ;

	// 批量审核
	@RequestMapping(value = "/pssReceiptBill/auditBatch")
	public Map<String, String> auditBatch(@RequestBody Map<String, String> formMap) throws Exception ;

	// 批量反审核
	@RequestMapping(value = "/pssReceiptBill/reAuditBatch")
	public Map<String, String> reAuditBatch(@RequestBody Map<String, String> formMap) throws Exception ;

	// 审核
	@RequestMapping(value = "/pssReceiptBill/updateChk")
	public Map<String, String> updateChk(@RequestBody PssReceiptBill pssReceiptBill,
			@RequestParam("bdTableList") List<PssReceiptDetailBill> bdTableList,
			@RequestParam("rdTableList") List<PssSourceDetailBill> rdTableList) throws Exception ;

	// 反审核
	@RequestMapping(value = "/pssReceiptBill/updateChk1")
	public Map<String, String> updateChk(@RequestBody PssReceiptBill pssReceiptBill) throws Exception ;

	/** 客户欠款 */
	@RequestMapping(value = "/pssReceiptBill/selectShouldRecCusAmt")
	public BigDecimal selectShouldRecCusAmt() throws Exception ;

}
