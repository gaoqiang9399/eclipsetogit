package app.component.pss.fund.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pss.fund.entity.PssReceiptDetailBill;
import app.util.toolkit.Ipage;

/**
 * Title: PssReceiptDetailBillBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Sep 20 17:57:27 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface PssReceiptDetailBillFeign {

	@RequestMapping(value = "/pssReceiptDetailBill/insert")
	public void insert(@RequestBody PssReceiptDetailBill pssReceiptDetailBill) throws Exception ;

	@RequestMapping(value = "/pssReceiptDetailBill/delete")
	public void delete(@RequestBody PssReceiptDetailBill pssReceiptDetailBill) throws Exception ;

	@RequestMapping(value = "/pssReceiptDetailBill/update")
	public void update(@RequestBody PssReceiptDetailBill pssReceiptDetailBill) throws Exception ;

	@RequestMapping(value = "/pssReceiptDetailBill/getById")
	public PssReceiptDetailBill getById(@RequestBody PssReceiptDetailBill pssReceiptDetailBill) throws Exception ;

	@RequestMapping(value = "/pssReceiptDetailBill/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,
			@RequestParam("pssReceiptDetailBill") PssReceiptDetailBill pssReceiptDetailBill) throws Exception ;

	@RequestMapping(value = "/pssReceiptDetailBill/getAll")
	public List<PssReceiptDetailBill> getAll(@RequestBody PssReceiptDetailBill pssReceiptDetailBill) throws Exception ;
}
