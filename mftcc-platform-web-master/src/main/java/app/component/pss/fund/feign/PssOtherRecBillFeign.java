package app.component.pss.fund.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pss.fund.entity.PssOtherRecBill;
import app.component.pss.fund.entity.PssOtherRecDetailBill;
import app.util.toolkit.Ipage;

/**
 * Title: PssOtherRecBillBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Nov 15 15:12:57 CST 2017
 **/

@FeignClient("mftcc-platform-factor")
public interface PssOtherRecBillFeign {

	@RequestMapping(value = "/pssOtherRecBill/insert")
	public void insert(@RequestBody PssOtherRecBill pssOtherRecBill) throws Exception ;

	@RequestMapping(value = "/pssOtherRecBill/delete")
	public void delete(@RequestBody PssOtherRecBill pssOtherRecBill) throws Exception ;

	@RequestMapping(value = "/pssOtherRecBill/update")
	public void update(@RequestBody PssOtherRecBill pssOtherRecBill) throws Exception ;

	@RequestMapping(value = "/pssOtherRecBill/getById")
	public PssOtherRecBill getById(@RequestBody PssOtherRecBill pssOtherRecBill) throws Exception ;

	@RequestMapping(value = "/pssOtherRecBill/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("pssOtherRecBill") PssOtherRecBill pssOtherRecBill)
			throws Exception ;

	@RequestMapping(value = "/pssOtherRecBill/getAll")
	public List<PssOtherRecBill> getAll(@RequestBody PssOtherRecBill pssOtherRecBill) throws Exception ;

	@RequestMapping(value = "/pssOtherRecBill/doSaveOtherRecBill")
	public boolean doSaveOtherRecBill(@RequestBody PssOtherRecBill pssOtherRecBill,
			@RequestParam("pssOtherRecDetailBillList") List<PssOtherRecDetailBill> pssOtherRecDetailBillList)
			throws Exception ;

	@RequestMapping(value = "/pssOtherRecBill/doAuditOtherRecBill")
	public boolean doAuditOtherRecBill(@RequestBody PssOtherRecBill pssOtherRecBill,
			@RequestParam("pssOtherRecDetailBillList") List<PssOtherRecDetailBill> pssOtherRecDetailBillList)
			throws Exception ;

	@RequestMapping(value = "/pssOtherRecBill/doReverseAuditOtherRecBill")
	public boolean doReverseAuditOtherRecBill(@RequestBody PssOtherRecBill pssOtherRecBill) throws Exception ;

	@RequestMapping(value = "/pssOtherRecBill/doAuditOtherRecBill")
	public boolean doAuditOtherRecBill(@RequestBody PssOtherRecBill pssOtherRecBill) throws Exception ;

	@RequestMapping(value = "/pssOtherRecBill/deleteOtherRecBill")
	public boolean deleteOtherRecBill(@RequestBody PssOtherRecBill pssOtherRecBill) throws Exception ;

	@RequestMapping(value = "/pssOtherRecBill/getByOtherRecNo")
	public PssOtherRecBill getByOtherRecNo(@RequestBody PssOtherRecBill pssOtherRecBill) throws Exception ;
}
