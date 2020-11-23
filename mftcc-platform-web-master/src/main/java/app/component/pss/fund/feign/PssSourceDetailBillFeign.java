package app.component.pss.fund.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pss.fund.entity.PssSourceDetailBill;
import app.util.toolkit.Ipage;

/**
 * Title: PssSourceDetailBillBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Sep 20 18:03:13 CST 2017
 **/

@FeignClient("mftcc-platform-factor")
public interface PssSourceDetailBillFeign {

	@RequestMapping(value = "/pssSourceDetailBill/insert")
	public void insert(@RequestBody PssSourceDetailBill pssSourceDetailBill) throws Exception ;

	@RequestMapping(value = "/pssSourceDetailBill/delete")
	public void delete(@RequestBody PssSourceDetailBill pssSourceDetailBill) throws Exception ;

	@RequestMapping(value = "/pssSourceDetailBill/update")
	public void update(@RequestBody PssSourceDetailBill pssSourceDetailBill) throws Exception ;

	@RequestMapping(value = "/pssSourceDetailBill/getById")
	public PssSourceDetailBill getById(@RequestBody PssSourceDetailBill pssSourceDetailBill) throws Exception ;

	@RequestMapping(value = "/pssSourceDetailBill/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,
			@RequestParam("pssSourceDetailBill") PssSourceDetailBill pssSourceDetailBill) throws Exception ;

	/** 收款-源单 */
	@RequestMapping(value = "/pssSourceDetailBill/findByPageForRec")
	public Ipage findByPageForRec(@RequestBody Ipage ipage,
			@RequestParam("pssSourceDetailBill") PssSourceDetailBill pssSourceDetailBill) throws Exception ;

	@RequestMapping(value = "/pssSourceDetailBill/findByPageForRecBack")
	public Ipage findByPageForRecBack(@RequestBody Ipage ipage,
			@RequestParam("pssSourceDetailBill") PssSourceDetailBill pssSourceDetailBill) throws Exception ;

	/** 付款-源单 */
	@RequestMapping(value = "/pssSourceDetailBill/findByPageForPay")
	public Ipage findByPageForPay(@RequestBody Ipage ipage,
			@RequestParam("pssSourceDetailBill") PssSourceDetailBill pssSourceDetailBill) throws Exception ;

	@RequestMapping(value = "/pssSourceDetailBill/findByPageForPayBack")
	public Ipage findByPageForPayBack(@RequestBody Ipage ipage,
			@RequestParam("pssSourceDetailBill") PssSourceDetailBill pssSourceDetailBill) throws Exception ;

	/** 预收-源单 */
	@RequestMapping(value = "/pssSourceDetailBill/findByPageForBefRec")
	public Ipage findByPageForBefRec(@RequestBody Ipage ipage,
			@RequestParam("pssSourceDetailBill") PssSourceDetailBill pssSourceDetailBill) throws Exception ;

	@RequestMapping(value = "/pssSourceDetailBill/findByPageForBefRecBack")
	public Ipage findByPageForBefRecBack(@RequestBody Ipage ipage,
			@RequestParam("pssSourceDetailBill") PssSourceDetailBill pssSourceDetailBill) throws Exception ;

	/** 预付-源单 */
	@RequestMapping(value = "/pssSourceDetailBill/findByPageForBefPay")
	public Ipage findByPageForBefPay(@RequestBody Ipage ipage,
			@RequestParam("pssSourceDetailBill") PssSourceDetailBill pssSourceDetailBill) throws Exception ;

	@RequestMapping(value = "/pssSourceDetailBill/findByPageForBefPayBack")
	public Ipage findByPageForBefPayBack(@RequestBody Ipage ipage,
			@RequestParam("pssSourceDetailBill") PssSourceDetailBill pssSourceDetailBill) throws Exception ;

	@RequestMapping(value = "/pssSourceDetailBill/getAll")
	public List<PssSourceDetailBill> getAll(@RequestBody PssSourceDetailBill pssSourceDetailBill) throws Exception ;
}
