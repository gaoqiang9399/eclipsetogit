package app.component.pss.fund.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pss.fund.entity.PssPaymentDetailBill;
import app.util.toolkit.Ipage;

/**
 * Title: PssPaymentDetailBillBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Sep 26 14:17:26 CST 2017
 **/

@FeignClient("mftcc-platform-factor")
public interface PssPaymentDetailBillFeign {

	@RequestMapping(value = "/pssPaymentDetailBill/insert")
	public void insert(@RequestBody PssPaymentDetailBill pssPaymentDetailBill) throws Exception ;

	@RequestMapping(value = "/pssPaymentDetailBill/delete")
	public void delete(@RequestBody PssPaymentDetailBill pssPaymentDetailBill) throws Exception ;

	@RequestMapping(value = "/pssPaymentDetailBill/update")
	public void update(@RequestBody PssPaymentDetailBill pssPaymentDetailBill) throws Exception ;

	@RequestMapping(value = "/pssPaymentDetailBill/getById")
	public PssPaymentDetailBill getById(@RequestBody PssPaymentDetailBill pssPaymentDetailBill) throws Exception ;

	@RequestMapping(value = "/pssPaymentDetailBill/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,
			@RequestParam("pssPaymentDetailBill") PssPaymentDetailBill pssPaymentDetailBill) throws Exception ;

	@RequestMapping(value = "/pssPaymentDetailBill/getAll")
	public List<PssPaymentDetailBill> getAll(@RequestBody PssPaymentDetailBill pssPaymentDetailBill) throws Exception ;
}
