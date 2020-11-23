package app.component.pss.fund.feign;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pss.fund.entity.PssPaymentBill;
import app.component.pss.fund.entity.PssPaymentDetailBill;
import app.component.pss.fund.entity.PssSourceDetailBill;
import app.util.toolkit.Ipage;

/**
 * Title: PssPaymentBillBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Sep 26 14:11:34 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface PssPaymentBillFeign {

	// @RequestMapping(value = "/pssPaymentBill/insert")public void insert(@RequestBody
	// PssPaymentBill pssPaymentBill, List<PssPaymentDetailBill> bdTableList,
	// List<PssSourceDetailBill> rdTableList) throws
	// Exception;

	@RequestMapping(value = "/pssPaymentBill/insert")
	public Map<String, String> insert(@RequestBody PssPaymentBill pssPaymentBill,
			@RequestParam("bdTableList") List<PssPaymentDetailBill> bdTableList,
			@RequestParam("rdTableList") List<PssSourceDetailBill> rdTableList) throws Exception ;

	@RequestMapping(value = "/pssPaymentBill/delete")
	public void delete(@RequestBody PssPaymentBill pssPaymentBill) throws Exception ;

	// @RequestMapping(value = "/pssPaymentBill/insert")public void update(@RequestBody
	// PssPaymentBill pssPaymentBill, List<PssPaymentDetailBill> bdTableList,
	// List<PssSourceDetailBill> rdTableList) throws
	// Exception;

	@RequestMapping(value = "/pssPaymentBill/update")
	public Map<String, String> update(@RequestBody PssPaymentBill pssPaymentBill,
			@RequestParam("bdTableList") List<PssPaymentDetailBill> bdTableList,
			@RequestParam("rdTableList") List<PssSourceDetailBill> rdTableList) throws Exception ;

	@RequestMapping(value = "/pssPaymentBill/getById")
	public PssPaymentBill getById(@RequestBody PssPaymentBill pssPaymentBill) throws Exception ;

	@RequestMapping(value = "/pssPaymentBill/getByIdOrNo")
	public PssPaymentBill getByIdOrNo(@RequestBody PssPaymentBill pssPaymentBill) throws Exception ;

	@RequestMapping(value = "/pssPaymentBill/getByPBNo")
	public PssPaymentBill getByPBNo(@RequestBody PssPaymentBill pssPaymentBill) throws Exception ;

	@RequestMapping(value = "/pssPaymentBill/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("pssPaymentBill") PssPaymentBill pssPaymentBill)
			throws Exception ;

	@RequestMapping(value = "/pssPaymentBill/getAll")
	public List<PssPaymentBill> getAll(@RequestBody PssPaymentBill pssPaymentBill) throws Exception ;

	// 批量删除
	@RequestMapping(value = "/pssPaymentBill/deletePBBatch")
	public Map<String, String> deletePBBatch(@RequestBody Map<String, String> formMap) throws Exception ;

	/** 批量审核 */
	@RequestMapping(value = "/pssPaymentBill/auditBatch")
	public Map<String, String> auditBatch(@RequestBody Map<String, String> formMap) throws Exception ;

	/** 批量反审核 */
	@RequestMapping(value = "/pssPaymentBill/reAuditBatch")
	public Map<String, String> reAuditBatch(@RequestBody Map<String, String> formMap) throws Exception ;

	/** 审核 */
	@RequestMapping(value = "/pssPaymentBill/updateChk")
	public Map<String, String> updateChk(@RequestBody PssPaymentBill pssPaymentBill,
			@RequestParam("bdTableList") List<PssPaymentDetailBill> bdTableList,
			@RequestParam("rdTableList") List<PssSourceDetailBill> rdTableList) throws Exception ;

	/** 反审核 */
	@RequestMapping(value = "/pssPaymentBill/updateChk1")
	public Map<String, String> updateChk(@RequestBody PssPaymentBill pssPaymentBill) throws Exception ;

	/** 供应商欠款 */
	@RequestMapping(value = "/pssPaymentBill/insert")
	public BigDecimal selectShouldPaySuppAmt() throws Exception ;

}
