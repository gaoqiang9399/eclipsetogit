package app.component.pss.fund.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pss.fund.entity.PssOtherRecDetailBill;
import app.util.toolkit.Ipage;

/**
 * Title: PssOtherRecDetailBillBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed Nov 15 15:14:07 CST 2017
 **/

@FeignClient("mftcc-platform-factor")
public interface PssOtherRecDetailBillFeign {

	@RequestMapping(value = "/pssOtherRecDetailBill/insert")
	public void insert(@RequestBody PssOtherRecDetailBill pssOtherRecDetailBill) throws Exception;

	@RequestMapping(value = "/pssOtherRecDetailBill/delete")
	public void delete(@RequestBody PssOtherRecDetailBill pssOtherRecDetailBill) throws Exception;

	@RequestMapping(value = "/pssOtherRecDetailBill/update")
	public void update(@RequestBody PssOtherRecDetailBill pssOtherRecDetailBill) throws Exception;

	@RequestMapping(value = "/pssOtherRecDetailBill/getById")
	public PssOtherRecDetailBill getById(@RequestBody PssOtherRecDetailBill pssOtherRecDetailBill) throws Exception;

	@RequestMapping(value = "/pssOtherRecDetailBill/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("pssOtherRecDetailBill") PssOtherRecDetailBill pssOtherRecDetailBill) throws Exception;

	@RequestMapping(value = "/pssOtherRecDetailBill/getAll")
	public List<PssOtherRecDetailBill> getAll(@RequestBody PssOtherRecDetailBill pssOtherRecDetailBill) throws Exception;
}
