package app.component.pss.fund.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pss.fund.entity.PssCancelVerificationBill;
import app.component.pss.fund.entity.PssSourceDetailBill;
import app.util.toolkit.Ipage;

/**
 * Title: PssCancelVerificationBillBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Sep 27 14:24:51 CST 2017
 **/

@FeignClient("mftcc-platform-factor")
public interface PssCancelVerificationBillFeign {

	@RequestMapping(value = "/pssCancelVerificationBill/insert")
	public Map<String, String> insert(@RequestBody PssCancelVerificationBill pssCancelVerificationBill, @RequestParam("bdTableList") List<PssSourceDetailBill> bdTableList, @RequestParam("rdTableList") List<PssSourceDetailBill> rdTableList) throws Exception;

	@RequestMapping(value = "/pssCancelVerificationBill/delete")
	public void delete(@RequestBody PssCancelVerificationBill pssCancelVerificationBill) throws Exception;

	// @RequestMapping(value = "/pssCancelVerificationBill/insert")public void
	// update(@RequestBody
	// PssCancelVerificationBill pssCancelVerificationBill,
	// List<PssSourceDetailBill> bdTableList, List<PssSourceDetailBill>
	// rdTableList) throws
	// Exception;

	@RequestMapping(value = "/pssCancelVerificationBill/update")
	public Map<String, String> update(@RequestBody PssCancelVerificationBill pssCancelVerificationBill, @RequestParam("bdTableList") List<PssSourceDetailBill> bdTableList, @RequestParam("rdTableList") List<PssSourceDetailBill> rdTableList) throws Exception;

	@RequestMapping(value = "/pssCancelVerificationBill/getById")
	public PssCancelVerificationBill getById(@RequestBody PssCancelVerificationBill pssCancelVerificationBill) throws Exception;

	@RequestMapping(value = "/pssCancelVerificationBill/getByIdOrNo")
	public PssCancelVerificationBill getByIdOrNo(@RequestBody PssCancelVerificationBill pssCancelVerificationBill) throws Exception;

	@RequestMapping(value = "/pssCancelVerificationBill/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("pssCancelVerificationBill") PssCancelVerificationBill pssCancelVerificationBill) throws Exception;

	@RequestMapping(value = "/pssCancelVerificationBill/getAll")
	public List<PssCancelVerificationBill> getAll(@RequestBody PssCancelVerificationBill pssCancelVerificationBill) throws Exception;

	// 批量删除
	@RequestMapping(value = "/pssCancelVerificationBill/deleteCBBatch")
	public Map<String, String> deleteCBBatch(@RequestBody Map<String, String> formMap) throws Exception;
}
