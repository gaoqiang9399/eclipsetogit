package app.component.pss.fund.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pss.fund.entity.PssOtherPayBill;
import app.component.pss.fund.entity.PssOtherPayDetailBill;
import app.util.toolkit.Ipage;

/**
 * Title: PssOtherPayBillBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Nov 15 14:57:11 CST 2017
 **/

@FeignClient("mftcc-platform-factor")
public interface PssOtherPayBillFeign {

	@RequestMapping(value = "/pssOtherPayBill/insert")
	public Map<String, String> insert(@RequestBody PssOtherPayBill pssOtherPayBill,
			@RequestParam("tableList") List<PssOtherPayDetailBill> tableList) throws Exception ;

	@RequestMapping(value = "/pssOtherPayBill/delete")
	public void delete(@RequestBody PssOtherPayBill pssOtherPayBill) throws Exception ;

	@RequestMapping(value = "/pssOtherPayBill/update")
	public Map<String, String> update(@RequestBody PssOtherPayBill pssOtherPayBill,
			@RequestParam("tableList") List<PssOtherPayDetailBill> tableList) throws Exception ;

	@RequestMapping(value = "/pssOtherPayBill/getById")
	public PssOtherPayBill getById(@RequestBody PssOtherPayBill pssOtherPayBill) throws Exception ;

	@RequestMapping(value = "/pssOtherPayBill/getByIdOrNo")
	public PssOtherPayBill getByIdOrNo(@RequestBody PssOtherPayBill pssOtherPayBill) throws Exception ;

	@RequestMapping(value = "/pssOtherPayBill/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("pssOtherPayBill") PssOtherPayBill pssOtherPayBill)
			throws Exception ;

	@RequestMapping(value = "/pssOtherPayBill/getAll")
	public List<PssOtherPayBill> getAll(@RequestBody PssOtherPayBill pssOtherPayBill) throws Exception ;

	/** 审核 */
	@RequestMapping(value = "/pssOtherPayBill/updateChk")
	public Map<String, String> updateChk(@RequestBody PssOtherPayBill pssOtherPayBill,
			@RequestParam("tableList") List<PssOtherPayDetailBill> tableList) throws Exception ;

	/** 反审核 */
	@RequestMapping(value = "/pssOtherPayBill/updateChk1")
	public Map<String, String> updateChk(@RequestBody PssOtherPayBill pssOtherPayBill) throws Exception ;

	/** 批量删除 */
	@RequestMapping(value = "/pssOtherPayBill/deleteOPBBatch")
	public Map<String, String> deleteOPBBatch(@RequestBody Map<String, String> formMap) throws Exception ;

	/** 批量审核 */
	@RequestMapping(value = "/pssOtherPayBill/auditBatch")
	public Map<String, String> auditBatch(@RequestBody Map<String, String> formMap) throws Exception ;

	/** 批量反审核 */
	@RequestMapping(value = "/pssOtherPayBill/reAuditBatch")
	public Map<String, String> reAuditBatch(@RequestBody Map<String, String> formMap) throws Exception ;

}
